package com.example.bmkg.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bmkg.BmkgApplication
import com.example.bmkg.data.BmkgRepository
import com.example.bmkg.model.BmkgResponse

import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BmkgUiState {
    // Interface untuk merepresentasikan state UI dalam aplikasi BMKG
    data class Success(val bmkg: BmkgResponse) : BmkgUiState // State ketika data berhasil dimuat
    object Error : BmkgUiState // State ketika terjadi kesalahan saat memuat data
    object Loading : BmkgUiState // State ketika sedang dalam proses memuat data
}

class BmkgViewModel(private val bmkgRepository: BmkgRepository) : ViewModel() {

    // State UI yang digunakan dalam ViewModel
    var bmkgUiState: BmkgUiState by mutableStateOf(BmkgUiState.Loading)
        private set

    // Fungsi inisialisasi ViewModel
    init {
        getBmkg()
    }

    // Fungsi untuk mendapatkan data BMKG
    fun getBmkg() {
        viewModelScope.launch {
            bmkgUiState = BmkgUiState.Loading // Mengatur state UI menjadi loading saat memuat data
            bmkgUiState = try {
                BmkgUiState.Success(bmkgRepository.getBmkg()) // Mengambil data dari repository jika berhasil
            } catch (e: IOException) {
                BmkgUiState.Error // Mengatur state UI menjadi error jika terjadi IOException
            } catch (e: HttpException) {
                BmkgUiState.Error // Mengatur state UI menjadi error jika terjadi HttpException
            }
        }
    }

    companion object {
        // Factory untuk membuat instance ViewModel dengan dependencies
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Mendapatkan instance Application dari AndroidViewModelFactory
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BmkgApplication)
                // Mendapatkan repository dari container Application
                val bmkgRepository = application.container.bmkgRepository
                // Menginisialisasi dan mengembalikan instance BmkgViewModel
                BmkgViewModel(bmkgRepository = bmkgRepository)
            }
        }
    }
}
