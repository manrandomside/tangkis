package com.kelompok.tangkis.ui

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.kelompok.tangkis.data.ContohData
import com.kelompok.tangkis.data.HasilDeteksi
import com.kelompok.tangkis.ui.komponen.NavigasiBawah
import com.kelompok.tangkis.ui.komponen.Tab
import com.kelompok.tangkis.ui.layar.LayarBeranda
import com.kelompok.tangkis.ui.layar.LayarHasil
import com.kelompok.tangkis.ui.layar.LayarKunci
import com.kelompok.tangkis.ui.layar.LayarMemindai
import com.kelompok.tangkis.ui.layar.LayarPengaturan
import com.kelompok.tangkis.ui.layar.LayarRiwayat
import com.kelompok.tangkis.ui.theme.Latar

sealed interface Layar {
    data object Kunci : Layar
    data class Utama(val tab: Tab) : Layar
    data class Memindai(val pesan: String) : Layar
    data class Hasil(val hasil: HasilDeteksi) : Layar
}

// Navigasi sederhana berbasis tumpukan layar, tanpa library tambahan
@Composable
fun TangkisApp() {
    val tumpukan = remember { mutableStateListOf<Layar>(Layar.Kunci) }
    val riwayat = remember { mutableStateListOf<HasilDeteksi>().apply { addAll(ContohData.riwayat()) } }

    fun buka(layar: Layar) = tumpukan.add(layar)
    fun ganti(layar: Layar) {
        tumpukan.removeAt(tumpukan.lastIndex)
        tumpukan.add(layar)
    }
    fun kembali() = tumpukan.removeAt(tumpukan.lastIndex)

    BackHandler(enabled = tumpukan.size > 1) { kembali() }

    when (val layar = tumpukan.last()) {
        Layar.Kunci -> LayarKunci(onTerbuka = { ganti(Layar.Utama(Tab.CEK)) })

        is Layar.Utama -> Scaffold(
            containerColor = Latar,
            bottomBar = { NavigasiBawah(aktif = layar.tab, onPilih = { ganti(Layar.Utama(it)) }) },
        ) { padding ->
            when (layar.tab) {
                Tab.CEK -> LayarBeranda(riwayat, onPindai = { buka(Layar.Memindai(it)) }, contentPadding = padding)
                Tab.RIWAYAT -> LayarRiwayat(riwayat, onPilih = { buka(Layar.Hasil(it)) }, contentPadding = padding)
                Tab.ATUR -> LayarPengaturan(contentPadding = padding)
            }
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
    }
}
