package com.kelompok.tangkis.ui.layar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok.tangkis.ui.komponen.Kartu
import com.kelompok.tangkis.ui.theme.TangkisTheme
import com.kelompok.tangkis.ui.theme.Latar
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta

// Isi pengaturan disusun pada tahap berikutnya
@Composable
fun LayarPengaturan(contentPadding: PaddingValues = PaddingValues()) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Latar)
            .padding(contentPadding)
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Text("Pengaturan", style = MaterialTheme.typography.headlineMedium, color = Tinta)
        Spacer(Modifier.height(16.dp))
        Kartu(Modifier.fillMaxWidth()) {
            Text(
                "Ganti kata sandi, izin SMS, hapus riwayat, dan tentang aplikasi akan tersedia di sini.",
                style = MaterialTheme.typography.bodyMedium,
                color = TeksSekunder,
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PratinjauLayarPengaturan() {
    TangkisTheme { LayarPengaturan() }
}
