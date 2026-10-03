package com.kelompok.tangkis.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok.tangkis.data.ContohData
import com.kelompok.tangkis.data.HasilDeteksi
import com.kelompok.tangkis.ui.komponen.NavigasiBawah
import com.kelompok.tangkis.ui.komponen.Tab
import com.kelompok.tangkis.ui.layar.LayarBeranda
import com.kelompok.tangkis.ui.layar.LayarGantiSandi
import com.kelompok.tangkis.ui.layar.LayarHasil
import com.kelompok.tangkis.ui.layar.LayarKunci
import com.kelompok.tangkis.ui.layar.LayarMemindai
import com.kelompok.tangkis.ui.layar.LayarPengaturan
import com.kelompok.tangkis.ui.layar.LayarRiwayat
import com.kelompok.tangkis.ui.layar.LayarTentang
import com.kelompok.tangkis.ui.theme.Latar
import com.kelompok.tangkis.ui.theme.TangkisTheme

sealed interface Layar {
    data object Kunci : Layar
    data class Utama(val tab: Tab) : Layar
    data class Memindai(val pesan: String) : Layar
    data class Hasil(val hasil: HasilDeteksi) : Layar
    data object Tentang : Layar
    data object GantiSandi : Layar
}

private val RuangNavigasi = 100.dp

// Navigasi sederhana berbasis tumpukan layar, tanpa library tambahan
@Composable
fun TangkisApp(layarAwal: Layar = Layar.Kunci) {
    val tumpukan = remember { mutableStateListOf(layarAwal) }
    val riwayat = remember { mutableStateListOf<HasilDeteksi>().apply { addAll(ContohData.riwayat()) } }

    fun buka(layar: Layar) = tumpukan.add(layar)
    fun ganti(layar: Layar) {
        tumpukan.removeAt(tumpukan.lastIndex)
        tumpukan.add(layar)
    }
    fun kembali() = tumpukan.removeAt(tumpukan.lastIndex)

    BackHandler(enabled = tumpukan.size > 1) { kembali() }

    when (val layar = tumpukan.last()) {
        Layar.Kunci -> LayarKunci(
            onTerbuka = { ganti(Layar.Utama(Tab.CEK)) },
            // Penghapusan hash kata sandi tersimpan ditambahkan bersama implementasi PBKDF2
            onReset = { riwayat.clear() },
        )

        is Layar.Utama -> Box(Modifier.fillMaxSize().background(Latar)) {
            val sistem = WindowInsets.systemBars.asPaddingValues()
            // Ruang bawah tambahan agar konten terakhir tidak tertutup navigasi yang melayang
            val padding = PaddingValues(
                top = sistem.calculateTopPadding(),
                bottom = sistem.calculateBottomPadding() + RuangNavigasi,
            )
            when (layar.tab) {
                Tab.CEK -> LayarBeranda(riwayat, onPindai = { buka(Layar.Memindai(it)) }, contentPadding = padding)
                Tab.RIWAYAT -> LayarRiwayat(riwayat, onPilih = { buka(Layar.Hasil(it)) }, contentPadding = padding)
                Tab.ATUR -> LayarPengaturan(
                    jumlahRiwayat = riwayat.size,
                    onGantiSandi = { buka(Layar.GantiSandi) },
                    onHapusRiwayat = { riwayat.clear() },
                    onTentang = { buka(Layar.Tentang) },
                    contentPadding = padding,
                )
            }
            NavigasiBawah(
                aktif = layar.tab,
                onPilih = { ganti(Layar.Utama(it)) },
                modifier = Modifier.align(Alignment.BottomCenter).navigationBarsPadding(),
            )
        }

        is Layar.Memindai -> LayarMemindai(layar.pesan) { hasil ->
            riwayat.add(0, hasil)
            ganti(Layar.Hasil(hasil))
        }

        is Layar.Hasil -> LayarHasil(
            hasil = layar.hasil,
            onKembali = { kembali() },
            onCekLagi = {
                tumpukan.clear()
                tumpukan.add(Layar.Utama(Tab.CEK))
            },
        )

        Layar.Tentang -> LayarTentang(onKembali = { kembali() })

        Layar.GantiSandi -> LayarGantiSandi(onKembali = { kembali() })
    }
}

@Preview(name = "Aplikasi - Cek", showSystemUi = true)
@Composable
private fun PratinjauAplikasiCek() {
    TangkisTheme { TangkisApp(Layar.Utama(Tab.CEK)) }
}

@Preview(name = "Aplikasi - Riwayat", showSystemUi = true)
@Composable
private fun PratinjauAplikasiRiwayat() {
    TangkisTheme { TangkisApp(Layar.Utama(Tab.RIWAYAT)) }
}

@Preview(name = "Aplikasi - Atur", showSystemUi = true)
@Composable
private fun PratinjauAplikasiAtur() {
    TangkisTheme { TangkisApp(Layar.Utama(Tab.ATUR)) }
}
