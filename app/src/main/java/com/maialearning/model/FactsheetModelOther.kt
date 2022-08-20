package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class FactsheetModelOther (

    @SerializedName("basic_info" ) var basicInfo : BasicInfo? = BasicInfo()

){
    data class BasicInfo (

        @SerializedName("basic_info" ) var basicInfo : BasicInfo? = BasicInfo()

    ){
        data class BasicInfo (

            @SerializedName("name"                 ) var name               : String? = null,
            @SerializedName("description"          ) var description        : String? = null,
            @SerializedName("addr"                 ) var addr               : String? = null,
            @SerializedName("city"                 ) var city               : String? = null,
            @SerializedName("state"                ) var state              : String? = null,
            @SerializedName("zip"                  ) var zip                : String? = null,
            @SerializedName("phone"                ) var phone              : String? = null,
            @SerializedName("web_addr"             ) var webAddr            : String? = null,
            @SerializedName("institution_type"     ) var institutionType    : String? = null,
            @SerializedName("award"                ) var award              : String? = null,
            @SerializedName("environment_type"     ) var environmentType    : String? = null,
            @SerializedName("enr_total"            ) var enrTotal           : String? = null,
            @SerializedName("term_type"            ) var termType           : String? = null,
            @SerializedName("distance_from_home"   ) var distanceFromHome   : String? = null,
            @SerializedName("distance_from_school" ) var distanceFromSchool : String? = null,
            @SerializedName("longitude"            ) var longitude          : String? = null,
            @SerializedName("latitude"             ) var latitude           : String? = null

        )
    }
}