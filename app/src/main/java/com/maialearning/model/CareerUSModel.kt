package com.maialearning.model

import com.google.gson.annotations.SerializedName


data class CareerUSModel (

    @SerializedName("pager" ) var pager : Pager?          = Pager(),
    @SerializedName("data"  ) var data  : ArrayList<Data> = arrayListOf()

){
    data class Pager (

        @SerializedName("current" ) var current : String? = null,
        @SerializedName("last"    ) var last    : Int?    = null,
        @SerializedName("total"   ) var total   : Int?    = null

    )
    data class Data (
        @SerializedName("nid"            ) var nid           : String? = null,
        @SerializedName("title"          ) var title         : String? = null,
        @SerializedName("cluster"        ) var cluster       : String? = null,
        @SerializedName("reserve"        ) var reserve       : String? = null,
        @SerializedName("officer"        ) var officer       : String? = null,
        @SerializedName("enlisted"       ) var enlisted      : String? = null,
        @SerializedName("active_duty"    ) var activeDuty    : String? = null,
        @SerializedName("career_cluster" ) var careerCluster : String? = null
    )
}