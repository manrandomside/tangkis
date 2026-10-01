package com.kelompok.tangkis.ui.layar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok.tangkis.R
import com.kelompok.tangkis.ui.komponen.Blob
import com.kelompok.tangkis.ui.komponen.TombolUtama
import com.kelompok.tangkis.ui.komponen.Wordmark
import com.kelompok.tangkis.ui.theme.AmanLembut
import com.kelompok.tangkis.ui.theme.TangkisTheme
import com.kelompok.tangkis.ui.theme.GarisTegas
import com.kelompok.tangkis.ui.theme.Latar
import com.kelompok.tangkis.ui.theme.Lime
import com.kelompok.tangkis.ui.theme.LimeLembut
import com.kelompok.tangkis.ui.theme.Permukaan
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta

@Composable
fun LayarKunci(onTerbuka: () -> Unit) {
    var kataSandi by remember { mutableStateOf("") }
    var terlihat by remember { mutableStateOf(false) }
    var galat by remember { mutableStateOf<String?>(null) }

    // Verifikasi hash PBKDF2 ditambahkan pada tahap implementasi keamanan
    val buka = {
        if (kataSandi.isBlank()) galat = "Kata sandi belum diisi" else onTerbuka()
    }

    Box(Modifier.fillMaxSize().background(Latar)) {
        Blob(LimeLembut, ukuran = 300.dp, x = 110.dp, y = (-110).dp, align = Alignment.TopEnd)
        Blob(AmanLembut, ukuran = 160.dp, x = (-70).dp, y = 170.dp)

        Column(
            Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .imePadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
        ) {
            Spacer(Modifier.height(32.dp))
            Wordmark(ukuranLogo = 52.dp)
            Spacer(Modifier.height(24.dp))
            Text(
                buildAnnotatedString {
                    append("Cek dulu,\nbaru ")
                    withStyle(SpanStyle(background = Lime)) { append(" percaya. ") }
                },
                style = MaterialTheme.typography.displaySmall,
                color = Tinta,
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "Masukkan kata sandi untuk membuka Tangkis.",
                style = MaterialTheme.typography.bodyLarge,
                color = TeksSekunder,
            )

            Spacer(Modifier.weight(1f))

            Text("Kata sandi", style = MaterialTheme.typography.labelMedium, color = TeksSekunder)
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = kataSandi,
                onValueChange = {
                    kataSandi = it
                    galat = null
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = galat != null,
                supportingText = galat?.let { pesan -> { Text(pesan) } },
                visualTransformation = if (terlihat) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { buka() }),
                trailingIcon = {
                    IconButton(onClick = { terlihat = !terlihat }) {
                        Icon(
                            painterResource(if (terlihat) R.drawable.ic_visibility_off else R.drawable.ic_visibility),
                            contentDescription = if (terlihat) "Sembunyikan kata sandi" else "Tampilkan kata sandi",
                            tint = TeksSekunder,
                        )
                    }
                },
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Tinta,
                    unfocusedBorderColor = GarisTegas,
                    focusedContainerColor = Permukaan,
                    unfocusedContainerColor = Permukaan,
                    errorContainerColor = Permukaan,
                ),
            )
            Spacer(Modifier.height(12.dp))
            TombolUtama("Buka aplikasi", R.drawable.ic_arrow_forward, onClick = buka)
            Spacer(Modifier.height(16.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(painterResource(R.drawable.ic_wifi_off), contentDescription = null, tint = TeksSekunder, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text(
                    "Offline, data tetap di HP ini",
                    style = MaterialTheme.typography.labelSmall,
                    color = TeksSekunder,
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PratinjauLayarKunci() {
    TangkisTheme { LayarKunci(onTerbuka = {}) }
}
