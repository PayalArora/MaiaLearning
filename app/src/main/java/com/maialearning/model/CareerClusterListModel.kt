package com.maialearning.model

import com.google.gson.annotations.SerializedName


data class CareerClusterListModel (

    @SerializedName("cluster"    ) var cluster    : String?               = null,
    @SerializedName("sort"       ) var sort       : String?               = null,
    @SerializedName("start"      ) var start      : Int?                  = null,
    @SerializedName("end"        ) var end        : Int?                  = null,
    @SerializedName("total"      ) var total      : Int?                  = null,
    @SerializedName("link"       ) var link       : ArrayList<Link>       = arrayListOf(),
    @SerializedName("occupation" ) var occupation : ArrayList<Occupation> = arrayListOf()

){

    data class Link (

        @SerializedName("href" ) var href : String? = null,
        @SerializedName("rel"  ) var rel  : String? = null

    )

    data class Occupation (

        @SerializedName("href"           ) var href          : String?        = null,
        @SerializedName("code"           ) var code          : String?        = null,
        @SerializedName("title"          ) var title         : String?        = null,
        @SerializedName("tags"           ) var tags          : Tags?          = Tags(),
        @SerializedName("career_cluster" ) var careerCluster : CareerCluster? = CareerCluster(),
        @SerializedName("career_pathway" ) var careerPathway : CareerPathway? = CareerPathway()

    ){
        data class Tags (

            @SerializedName("bright_outlook" ) var brightOutlook : Boolean? = null,
            @SerializedName("green"          ) var green         : Boolean? = null

        )

        data class CareerCluster (

            @SerializedName("code"  ) var code  : String? = null,
            @SerializedName("title" ) var title : String? = null

        )

        data class CareerPathway (

            @SerializedName("code"  ) var code  : String? = null,
            @SerializedName("title" ) var title : String? = null

        )
    }
}