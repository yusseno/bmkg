package com.example.bmkg.data

import com.example.bmkg.network.BmkgApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

// Container untuk menyediakan dependensi aplikasi
interface AppContainer {
    val bmkgRepository: BmkgRepository
}

// Implementasi default dari AppContainer
class DefaultAppContainer : AppContainer {
    // Base URL untuk layanan Retrofit
    private val BASE_URL = "https://data.bmkg.go.id/DataMKG/TEWS/"

    // Interceptor untuk logging HTTP
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Inisialisasi objek Retrofit
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
        .build()

    // Mendapatkan instance layanan Retrofit
    private val retrofitService: BmkgApiService by lazy {
        retrofit.create(BmkgApiService::class.java)
    }

    // Implementasi repositori BMKG
    override val bmkgRepository: BmkgRepository by lazy {
        DefaultBmkgRepository(retrofitService)
    }
}