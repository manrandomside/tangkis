package com.kelompok.tangkis.ui.layar

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok.tangkis.data.ContohData
import com.kelompok.tangkis.data.HasilDeteksi
import com.kelompok.tangkis.data.Kategori
import com.kelompok.tangkis.ui.komponen.IkonKategori
import com.kelompok.tangkis.ui.komponen.Kartu
import com.kelompok.tangkis.ui.komponen.Pil
import com.kelompok.tangkis.ui.theme.TangkisTheme
import com.kelompok.tangkis.ui.theme.Garis
import com.kelompok.tangkis.ui.theme.Latar
import com.kelompok.tangkis.ui.theme.Permukaan
import com.kelompok.tangkis.ui.theme.TeksSamar
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

@Composable
fun LayarRiwayat(
    riwayat: List<HasilDeteksi>,
    onPilih: (HasilDeteksi) -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    var filter by remember { mutableStateOf<Kategori?>(null) }
    val tampil = riwayat.filter { filter == null || it.kategori == filter }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Latar),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = contentPadding.calculateTopPadding() + 16.dp,
            bottom = contentPadding.calculateBottomPadding() + 16.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            Column {
                Text("Riwayat", style = MaterialTheme.typography.headlineMedium, color = Tinta)
                Text(
                    "${riwayat.size} pesan sudah kamu cek",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TeksSekunder,
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    PilFilter("Semua", dipilih = filter == null) { filter = null }
                    Kategori.entries.forEach { k ->
                        PilFilter(k.label, dipilih = filter == k) { filter = k }
                    }
                }
                Spacer(Modifier.height(6.dp))
            }
        }
        items(tampil, key = { it.waktu }) { hasil ->
            KartuRiwayat(hasil) { onPilih(hasil) }
        }
        if (tampil.isEmpty()) {
            item {
                Text(
                    "Belum ada pesan di sini. Yuk cek pesan pertamamu!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TeksSamar,
                    modifier = Modifier.padding(top = 24.dp),
                )
            }
        }
    }
}

@Composable
private fun PilFilter(teks: String, dipilih: Boolean, onClick: () -> Unit) {
    Pil(
        teks,
        warnaLatar = if (dipilih) Tinta else Permukaan,
        warnaTeks = if (dipilih) Permukaan else Tinta,
        warnaGaris = if (dipilih) Tinta else Garis,
        onClick = onClick,
    )
}

@Composable
private fun KartuRiwayat(hasil: HasilDeteksi, onClick: () -> Unit) {
    Kartu(Modifier.fillMaxWidth(), onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IkonKategori(hasil.kategori)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Row {
                    Text(
                        "${hasil.kategori.label} ${(hasil.keyakinan * 100).roundToInt()}%",
                        style = MaterialTheme.typography.labelLarge,
                        color = hasil.kategori.warnaTua,
                        modifier = Modifier.weight(1f),
                    )
                    Text(formatWaktu(hasil.waktu), style = MaterialTheme.typography.labelSmall, color = TeksSamar)
                }
                Text(
                    hasil.pesan,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TeksSekunder,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

// Menampilkan jam untuk hari ini, "Kemarin", atau jumlah hari yang lalu
private fun formatWaktu(waktu: Long): String {
    val zona = ZoneId.systemDefault()
    val tanggal = Instant.ofEpochMilli(waktu).atZone(zona)
    val selisihHari = ChronoUnit.DAYS.between(tanggal.toLocalDate(), LocalDate.now(zona))
    return when (selisihHari) {
        0L -> tanggal.format(DateTimeFormatter.ofPattern("HH.mm"))
        1L -> "Kemarin"
        else -> "$selisihHari hari lalu"
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PratinjauLayarRiwayat() {
    TangkisTheme { LayarRiwayat(ContohData.riwayat(), onPilih = {}) }
}
