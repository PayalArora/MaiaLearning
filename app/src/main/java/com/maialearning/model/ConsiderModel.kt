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
            var ncaa: String ,
            @SerializedName("country_Name")
            var country_Name: String
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
        @SerializedName("name")
        var naviance_college_name: String,
        @SerializedName("country")
        var country: String,
        @SerializedName("country_name")
        var country_name: String,
        @SerializedName("created_by_name")
        var created_name: String,
        @SerializedName("created_date")
        var created_date: String,
        @SerializedName("internal_deadline")
        var internal_deadline: String,
        @SerializedName("app_by_program_data")
        var program_data: ArrayList<ProgramData>?,
        @SerializedName("count")
        var count: Int
    )
    data class ProgramData(
        @SerializedName("program_id")
        var program_id: Int,
        @SerializedName("program_name")
        var program_name: String,
        @SerializedName("program_deadline")
        var program_deadline: String,
        @SerializedName("program_status")
        var program_status: String,
    )

    }

