package com.maialearning.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class FactsheetModelOther(

    @SerializedName("basic_info") var basicInfo: BasicInfo? = BasicInfo(),
    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("title")
    val title: String? = null,
    @field:SerializedName("provider_info")
    val providerInfo: Any? = null,
//    @field:SerializedName("provider_info")
//    val providerInfo: ProviderInfo? = null,
    @field:SerializedName("course_list")
    val course_list: Any? = null
) {
    data class BasicInfo(

        @SerializedName("basic_info") var basicInfo: BasicInfo? = BasicInfo()

    ) {
        data class BasicInfo(

            @SerializedName("name") var name: String? = null,
            @SerializedName("description") var description: String? = null,
            @SerializedName("addr") var addr: String? = null,
            @SerializedName("city") var city: String? = null,
            @SerializedName("state") var state: String? = null,
            @SerializedName("zip") var zip: String? = null,
            @SerializedName("phone") var phone: String? = null,
            @SerializedName("web_addr") var webAddr: String? = null,
            @SerializedName("institution_type") var institutionType: String? = null,
            @SerializedName("award") var award: String? = null,
            @SerializedName("environment_type") var environmentType: String? = null,
            @SerializedName("enr_total") var enrTotal: String? = null,
            @SerializedName("term_type") var termType: String? = null,
            @SerializedName("distance_from_home") var distanceFromHome: String? = null,
            @SerializedName("distance_from_school") var distanceFromSchool: String? = null,
            @SerializedName("longitude") var longitude: String? = null,
            @SerializedName("latitude") var latitude: String? = null

        )
    }

    data class  ProviderInfo(

        @field:SerializedName("country")
        val country: String? = null,

        @field:SerializedName("website")
        val website: String? = null,

        @field:SerializedName("college_name")
        val collegeName: String? = null,

        @field:SerializedName("address3")
        val address3: String? = null,

        @field:SerializedName("address2")
        val address2: String? = null,

        @field:SerializedName("address1")
        val address1: String? = null,

        @field:SerializedName("address4")
        val address4: String? = null,

        @field:SerializedName("postal_code")
        val postalCode: String? = null
    )

}