package com.example.bmkg.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Model data response untuk informasi gempa bumi
@Serializable
data class BmkgResponse(
    @SerialName("Infogempa") val infogempa: Infogempa
)