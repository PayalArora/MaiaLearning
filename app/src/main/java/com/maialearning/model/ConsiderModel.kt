package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class ConsiderModel (

        @SerializedName("9375")
        var x9375: X9375
    ) {
        data class X9375(
            @SerializedName("data")
            var `data`: Data,
            @SerializedName("ncaa")
            var ncaa: String
        ) data class Data(
        @SerializedName("contact_info")
        var contactInfo: Int,
        @SerializedName("parchment")
        var parchment: Int,
        @SerializedName("slate")
        var slate: Int,
        @SerializedName("transcript_nid")
        var transcriptNid: Int,
        @SerializedName("university_nid")
        var universityNid: String,
        @SerializedName("naviance_college_name")
        var naviance_college_name: String)

    }

