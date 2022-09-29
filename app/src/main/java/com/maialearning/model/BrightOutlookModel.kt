package com.maialearning.model

import com.google.gson.annotations.SerializedName


data class BrightOutlookModel (

    @SerializedName("pager" ) var pager : Pager?          = Pager(),
    @SerializedName("data"  ) var data  : ArrayList<Data> = ArrayList()

){


    data class Pager (

        @SerializedName("current" ) var current : Int? = null,
        @SerializedName("last"    ) var last    : Int? = null,
        @SerializedName("total"   ) var total   : Int? = null

    )
        data class Data (

            @SerializedName("ccode"          ) var ccode         : String?           = null,
            @SerializedName("title"          ) var title         : String?           = null,
            @SerializedName("education"      ) var education     : ArrayList<String> = arrayListOf(),
            @SerializedName("salary"         ) var salary        : String?           = null,
            @SerializedName("bright_outlook" ) var brightOutlook : ArrayList<String> = arrayListOf()

        )

}