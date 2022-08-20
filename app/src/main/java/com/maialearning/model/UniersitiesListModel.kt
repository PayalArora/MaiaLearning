package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class UniersitiesListModel(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("created")
    var created: String? = null,
    @SerializedName("changed")
    var changed: String? = null,
    @SerializedName("uid")
    var uid: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("school")
    var school: String? = null,
    @SerializedName("district_scope")
    var districtScope: String? = null,
//    @SerializedName("universities")
//    var universities: ArrayList<Universities> = ArrayList()
) {
    class Universities(

        @SerializedName("nid") var nid: String? = null,
        @SerializedName("unitid") var unitid: String? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("country") var country: String? = null,
        @SerializedName("considered") var considered: String? = null
    )
}