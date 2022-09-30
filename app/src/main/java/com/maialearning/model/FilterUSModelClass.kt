package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class FilterUSModelClass (
    @SerializedName("continents")
    var continents: ArrayList<Continents>,
    @SerializedName("country_list")
    var country_list:ArrayList<CountryList>,
){
    data class Continents(
        @SerializedName("code")
        var code:String,
        @SerializedName("name")
        var name: String,
    )
    data class CountryList(
        @SerializedName("code")
        var code:String,
        @SerializedName("name")
        var name: String,
        var select :Boolean
    )
    data class Region(
        @SerializedName("code")
        var code:String,
    )
}