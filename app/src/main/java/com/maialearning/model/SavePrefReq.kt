package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class SavePrefReq(var career_region_preference: CareerRegionPreference){
data class CareerRegionPreference(

    @field:SerializedName("region_name")
    val regionName: String? = null,

    @field:SerializedName("region_level")
    val regionLevel: String? = null,

    @field:SerializedName("region_code")
    val regionCode: String? = null
)}
