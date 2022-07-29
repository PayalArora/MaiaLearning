package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class AttachMessages (
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    var url: String
)