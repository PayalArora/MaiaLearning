package com.maialearning.model

import com.google.gson.annotations.SerializedName


data class CareerClusterModel (

    @SerializedName("career_cluster" ) var careerCluster : ArrayList<CareerCluster> = arrayListOf()

){
    data class CareerCluster (

        @SerializedName("href"  ) var href  : String? = null,
        @SerializedName("code"  ) var code  : String? = null,
        @SerializedName("title" ) var title : String? = null

    )
}