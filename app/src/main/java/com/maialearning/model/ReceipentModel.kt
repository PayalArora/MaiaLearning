package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class ReceipentModel (
    @SerializedName("name")
    var name: String,
    @SerializedName("message_center_uid")
    var message_center_uid: String,
    @SerializedName("uid")
    var uid: String,
    )