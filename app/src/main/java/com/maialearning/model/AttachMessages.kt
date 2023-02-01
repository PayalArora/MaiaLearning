package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class AttachMessages (
    @SerializedName("filename")
    var name: String,
    @SerializedName("type")
    var type: String,    @SerializedName("fileType")
    var fileType: String, @SerializedName("key")
    var key: String,  @SerializedName("schoolNid")
    var schoolNid: String

)