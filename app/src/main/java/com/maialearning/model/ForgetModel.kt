package com.maialearning.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForgetModel(
    @SerialName("success")
    var success: String
)