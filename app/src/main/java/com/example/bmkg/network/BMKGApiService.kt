package com.example.bmkg.network

import com.example.bmkg.model.BmkgResponse
import retrofit2.http.GET

interface BmkgApiService {
    // Fungsi untuk mengambil data BMKG secara suspending menggunakan Retrofit GET request
    @GET("gempadirasakan.json")
    suspend fun getBmkg(): BmkgResponse
}
