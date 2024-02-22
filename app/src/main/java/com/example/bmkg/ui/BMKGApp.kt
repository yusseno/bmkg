package com.example.bmkg.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bmkg.R
import com.example.bmkg.ui.screens.BmkgViewModel
import com.example.bmkg.ui.screens.HomeScreen
import com.example.bmkg.ui.theme.BmkgTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmkgApp() {
    // Scaffold digunakan sebagai layout utama aplikasi
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            // TopAppBar menampilkan bar bagian atas aplikasi
            TopAppBar(
                title = {
                    // Judul aplikasi
                    Row {
                        LogoBmkg() // Logo BMKG
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(
                            "Info Gempa Bumi (BMKG)", // Judul TopAppBar
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            )
        }
    ) {
        // Surface digunakan untuk menentukan warna latar belakang
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Green.copy(0.1F) // Warna latar belakang dengan transparansi
        ) {
            val bmkgViewModel: BmkgViewModel =
                viewModel(factory = BmkgViewModel.Factory)
            // Menampilkan layar utama
            HomeScreen(
                bmkgUiState = bmkgViewModel.bmkgUiState, // State UI
                retryAction = bmkgViewModel::getBmkg, // Aksi untuk mencoba memuat ulang data
                modifier = Modifier.fillMaxSize(), // Modifier untuk menyesuaikan tata letak
                contentPadding = it // Padding untuk konten dalam layar
            )
        }
    }
}

// Komponen untuk menampilkan logo BMKG
@Composable
fun LogoBmkg(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.bmkg), // Gambar logo BMKG
        contentDescription = "BMKG Logo", // Deskripsi konten gambar
        modifier = modifier
            .padding(start = 8.dp) // Padding pada sisi kiri
            .width(32.dp) // Lebar gambar
            .height(32.dp), // Tinggi gambar
    )
}

// Preview untuk tampilan layar loading
@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    BmkgTheme { // Tema aplikasi BMKG
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background // Warna latar belakang
        ) {
            BmkgApp() // Menampilkan aplikasi BMKG
        }
    }
}
