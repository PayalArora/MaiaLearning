package com.maialearning.model

import com.google.gson.annotations.SerializedName


data class CareerClusterModel (

    @field:SerializedName("career_cluster" ) var careerCluster : ArrayList<CareerCluster> = arrayListOf()

){
    data class CareerCluster (

        @field:SerializedName("href"  ) var href  : String? = null,
        @field:SerializedName("code"  ) var code  : String? = null,
        @field:SerializedName("title" ) var title : String? = null

    )
}