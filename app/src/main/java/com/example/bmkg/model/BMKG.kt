package com.example.bmkg.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Model data untuk informasi gempa bumi
@Serializable
data class Bmkg(
    @SerialName("Tanggal") val tanggal: String,
    @SerialName("Jam") val jam: String,
    @SerialName("DateTime") val dateTime: String,
    @SerialName("Coordinates") val coordinates: String,
    @SerialName("Lintang") val lintang: String,
    @SerialName("Bujur") val bujur: String,
    @SerialName("Magnitude") val magnitude: String,
    @SerialName("Kedalaman") val kedalaman: String,
    @SerialName("Wilayah") val wilayah: String,
    @SerialName("Dirasakan") val dirasakan: String
)

// Model data untuk mengelompokkan beberapa informasi gempa
@Serializable
data class Infogempa(
    @SerialName("gempa") val listGempa: List<Bmkg>
)