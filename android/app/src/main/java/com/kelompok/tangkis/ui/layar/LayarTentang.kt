package com.kelompok.tangkis.ui.layar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok.tangkis.BuildConfig
import com.kelompok.tangkis.R
import com.kelompok.tangkis.ui.komponen.Blob
import com.kelompok.tangkis.ui.komponen.Kartu
import com.kelompok.tangkis.ui.komponen.Pil
import com.kelompok.tangkis.ui.komponen.TombolKembali
import com.kelompok.tangkis.ui.komponen.Wordmark
import com.kelompok.tangkis.ui.theme.Latar
import com.kelompok.tangkis.ui.theme.LimeLembut
import com.kelompok.tangkis.ui.theme.LimeTua
import com.kelompok.tangkis.ui.theme.TangkisTheme
import com.kelompok.tangkis.ui.theme.TeksSamar
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta

private val caraKerja = listOf(
    "Teks pesan dibersihkan, lalu setiap kata diubah menjadi angka.",
    "Jaringan Syaraf Tiruan jenis Multilayer Perceptron yang dilatih dengan Backpropagation menghitung kemungkinan pesan termasuk normal, promo, atau penipuan.",
    "Sistem pakar mencocokkan pesan dengan aturan tanda bahaya, misalnya berisi tautan atau meminta kode OTP.",
)

private val timMkbl = listOf(
    "Firman Fadilah",
    "Alpara Verriyanta",
    "Anak Agung Gde Agung Pranandita",
    "I Putu Andika Arsana Putra",
    "Adika Setyadharma Susilo",
    "I Putu Agus Wahyu Wirakusuma Putra",
)

private val timKsm = listOf(
    "Firman Fadilah",
    "I Gusti Ketut Ngurah Adi Putra",
    "Fadhilah Randri Besemah",
    "Alpara Verriyanta",
    "I Putu Agus Putra Darmawan",
    "I Gede Rama Yasa Mahendra",
)

private val lisensi = listOf(
    "Kode sumber" to "MIT License",
    "Font Poppins" to "SIL Open Font License 1.1",
    "Ikon Material Symbols" to "Apache License 2.0",
    "Dataset SMS Spam Indonesia" to "oleh agtbaskara",
)

@Composable
fun LayarTentang(onKembali: () -> Unit) {
    Box(Modifier.fillMaxSize().background(Latar)) {
        Blob(LimeLembut, ukuran = 320.dp, x = 0.dp, y = (-150).dp, align = Alignment.TopCenter)

        Column(
            Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TombolKembali(onKembali)
                Text(
                    "Tentang",
                    style = MaterialTheme.typography.titleMedium,
                    color = Tinta,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.width(44.dp))
            }

            Spacer(Modifier.height(24.dp))
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Wordmark(ukuranLogo = 56.dp)
                Spacer(Modifier.height(8.dp))
                Text("Tangkis penipuan sebelum terjadi.", style = MaterialTheme.typography.bodyLarge, color = TeksSekunder)
                Spacer(Modifier.height(12.dp))
                Pil("Versi ${BuildConfig.VERSION_NAME}", warnaTeks = LimeTua)
            }

            Spacer(Modifier.height(24.dp))
            Kartu(Modifier.fillMaxWidth()) {
                Judul("Apa itu Tangkis?")
                Text(
                    "Tangkis membantu kamu mengecek pesan SMS, WhatsApp, atau email yang mencurigakan. " +
                        "Cukup tempel isi pesannya, Tangkis akan menilai apakah pesan itu normal, promo, atau penipuan, " +
                        "lengkap dengan alasan dan saran tindakan.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TeksSekunder,
                )
            }

            Spacer(Modifier.height(12.dp))
            Kartu(Modifier.fillMaxWidth()) {
                Judul("Cara kerja")
                caraKerja.forEachIndexed { indeks, teks ->
                    Row(Modifier.padding(vertical = 6.dp)) {
                        Box(
                            Modifier.size(26.dp).clip(CircleShape).background(LimeLembut),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text("${indeks + 1}", style = MaterialTheme.typography.labelMedium, color = Tinta)
                        }
                        Spacer(Modifier.width(12.dp))
                        Text(teks, style = MaterialTheme.typography.bodyMedium, color = Tinta)
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            Kartu(Modifier.fillMaxWidth()) {
                Judul("Privasi kamu terjaga")
                BarisPrivasi(R.drawable.ic_wifi_off, "Semua proses berjalan di dalam HP, tanpa koneksi internet.")
                BarisPrivasi(R.drawable.ic_lock, "Isi pesan tidak pernah dikirim ke luar perangkat.")
                BarisPrivasi(R.drawable.ic_shield, "Mengikuti prinsip UU No. 27 Tahun 2022 tentang Perlindungan Data Pribadi.")
            }

            Spacer(Modifier.height(12.dp))
            Kartu(Modifier.fillMaxWidth()) {
                Judul("Tim pengembang")
                Text(
                    "Program Studi Informatika, FMIPA, Universitas Udayana, 2026\nDosen pengampu: I Gede Surya Rahayuda, M.Kom.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TeksSekunder,
                )
                DaftarTim("Metode Kecerdasan Buatan Lanjut", timMkbl)
                DaftarTim("Keamanan Sistem Mobile", timKsm)
            }

            Spacer(Modifier.height(12.dp))
            Kartu(Modifier.fillMaxWidth()) {
                Judul("Lisensi")
                lisensi.forEach { (nama, keterangan) ->
                    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(nama, style = MaterialTheme.typography.bodyMedium, color = Tinta)
                        Text(keterangan, style = MaterialTheme.typography.bodyMedium, color = TeksSekunder, textAlign = TextAlign.End)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Text(
                "Dibuat dengan Kotlin dan Jetpack Compose",
                style = MaterialTheme.typography.labelSmall,
                color = TeksSamar,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun Judul(teks: String) {
    Text(teks, style = MaterialTheme.typography.titleMedium, color = Tinta, modifier = Modifier.padding(bottom = 10.dp))
}

@Composable
private fun BarisPrivasi(@DrawableRes ikon: Int, teks: String) {
    Row(Modifier.padding(vertical = 6.dp)) {
        Icon(painterResource(ikon), contentDescription = null, tint = LimeTua, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(12.dp))
        Text(teks, style = MaterialTheme.typography.bodyMedium, color = Tinta)
    }
}

@Composable
private fun DaftarTim(mataKuliah: String, anggota: List<String>) {
    Spacer(Modifier.height(14.dp))
    Text(mataKuliah, style = MaterialTheme.typography.labelLarge, color = Tinta)
    Spacer(Modifier.height(4.dp))
    anggota.forEach { nama ->
        Text(nama, style = MaterialTheme.typography.bodyMedium, color = TeksSekunder, modifier = Modifier.padding(vertical = 2.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PratinjauLayarTentang() {
    TangkisTheme { LayarTentang(onKembali = {}) }
}
