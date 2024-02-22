package com.example.bmkg.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bmkg.R
import com.example.bmkg.model.Bmkg
import com.example.bmkg.ui.theme.BmkgTheme

// Komponen ini menampilkan layar utama aplikasi BMKG
@Composable
fun HomeScreen(
    bmkgUiState: BmkgUiState, // State UI untuk menentukan tampilan yang akan ditampilkan
    retryAction: () -> Unit, // Fungsi untuk mencoba memuat ulang data saat terjadi kesalahan
    modifier: Modifier = Modifier, // Modifier untuk menyesuaikan tata letak
    contentPadding: PaddingValues = PaddingValues(0.dp) // Padding untuk konten dalam layar
) {
    // Menentukan tampilan sesuai dengan state UI
    when (bmkgUiState) {
        is BmkgUiState.Loading -> LoadingScreen(modifier = modifier.size(200.dp)) // Tampilan layar loading saat data sedang dimuat
        is BmkgUiState.Success -> {
            val infogempaList = bmkgUiState.bmkg.infogempa.listGempa
            BmkgListScreen(
                data = infogempaList,
                modifier = modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp
                    ),
                contentPadding = contentPadding
            )
        }
        else -> ErrorScreen(retryAction = retryAction, modifier = modifier) // Tampilan layar error jika gagal memuat data
    }
}

// Tampilan layar loading
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = "Loading",
        modifier = modifier
    )
}

// Tampilan layar error
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Gagal Memuat Data")
        Button(onClick = retryAction) {
            Text("Coba Lagi")
        }
    }
}

// Kartu informasi BMKG
@Composable
fun BmkgCard(data: Bmkg, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BmkgInfoRow("Tanggal \t\t\t", data.tanggal)
            BmkgInfoRow("Jam \t\t\t\t\t", data.jam)
            BmkgInfoRow("Coordinates \t", data.coordinates)
            BmkgInfoRow("Lintang \t\t\t", data.lintang)
            BmkgInfoRow("Bujur \t\t\t\t", data.bujur)
            BmkgInfoRow("Magnitude \t", data.magnitude)
            BmkgInfoRow("Kedalaman \t", data.kedalaman)
            BmkgInfoRow("Wilayah \t\t\t", data.wilayah)
            BmkgInfoRow("Dirasakan \t\t", data.dirasakan)
        }
    }
}

// Baris informasi BMKG, menampilkan label dan nilainya
@Composable
fun BmkgInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium) // Label informasi
        Text(text = ": ", style = MaterialTheme.typography.bodyMedium) // Tanda ":" sebagai pemisah
        Text(text = value, style = MaterialTheme.typography.bodyMedium) // Nilai informasi
    }
}

// Daftar informasi BMKG dalam daftar bergulir
@Composable
fun BmkgListScreen(
    data: List<Bmkg>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = data,
            key = { bmkg ->
                "${bmkg.tanggal}_${bmkg.jam}"
            }
        ) { bmkg ->
            BmkgCard(data = bmkg, modifier = Modifier.fillMaxWidth())
        }
    }
}

// Contoh tampilan layar loading untuk pratinjau dalam Composable
@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    BmkgTheme() { // Tema aplikasi BMKG
        val mockData = List(1) {
            Bmkg(
                tanggal = "22 Feb 2024",
                jam = "10:06:34 WIB",
                dateTime = "2024-02-22T03:06:34+00:00",
                coordinates = "-8.05,107.74",
                lintang = "8.05 LS",
                bujur = "107.74 BT",
                magnitude = "3.2",
                kedalaman = "24 km",
                wilayah = "Pusat gempa berada di laut 87 km BaratDaya Kab. Tasikmalaya",
                dirasakan = "II Kab. Tasikmalaya"
            )
        }
        BmkgListScreen(mockData, Modifier.fillMaxSize())
    }
}

