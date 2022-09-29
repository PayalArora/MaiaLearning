package com.maialearning.model

import com.google.gson.annotations.SerializedName


data class IndustryListModel (

    @SerializedName("industry"   ) var industry   : String?               = null,
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

        @SerializedName("href"                         ) var href                      : String?                              = null,
        @SerializedName("code"                         ) var code                      : String?                              = null,
        @SerializedName("title"                        ) var title                     : String?                              = null,
        @SerializedName("tags"                         ) var tags                      : Tags?                                = Tags(),
        @SerializedName("percent_employed_by_industry" ) var percentEmployedByIndustry : ArrayList<PercentEmployedByIndustry> = arrayListOf(),
        @SerializedName("projected_growth"             ) var projectedGrowth           : String?                              = null,
        @SerializedName("projected_openings"           ) var projectedOpenings         : Int?                                 = null

    ){
        data class PercentEmployedByIndustry (

            @SerializedName("code"    ) var code    : Int? = null,
            @SerializedName("percent" ) var percent : Int? = null

        )
        data class Tags (

            @SerializedName("bright_outlook" ) var brightOutlook : Boolean? = null,
            @SerializedName("green"          ) var green         : Boolean? = null

        )
    }
}