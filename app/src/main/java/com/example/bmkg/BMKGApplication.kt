package com.example.bmkg

import android.app.Application
import com.example.bmkg.data.AppContainer
import com.example.bmkg.data.DefaultAppContainer

class BmkgApplication : Application() {
    // Container aplikasi yang menyimpan dependensi
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Menginisialisasi kontainer dengan implementasi default
        container = DefaultAppContainer()
    }
}
