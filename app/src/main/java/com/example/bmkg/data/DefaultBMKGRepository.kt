package com.example.bmkg.data

import com.example.bmkg.model.BmkgResponse
import com.example.bmkg.network.BmkgApiService

interface BmkgRepository {
    // Fungsi untuk mengambil data BMKG secara suspending
    suspend fun getBmkg(): BmkgResponse
}

class DefaultBmkgRepository(
    private val bmkgApiService: BmkgApiService
) : BmkgRepository {
    // Implementasi fungsi getBmkg dari BmkgRepository
    override suspend fun getBmkg(): BmkgResponse {
        return try {
            // Memanggil layanan BmkgApiService untuk mendapatkan data BMKG
            val response = bmkgApiService.getBmkg()
            // Mencetak respons untuk keperluan debugging
            println("Response: $response")
            response
        } catch (e: Exception) {
            // Menangani kesalahan dan melemparkannya kembali
            println("Error: ${e.message}")
            throw e
        }
    }
}

