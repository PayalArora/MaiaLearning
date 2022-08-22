package com.maialearning.model


import com.google.gson.annotations.SerializedName

data class CollegeContactModel(
    @SerializedName("data")
    var `data`: List<Data>,
    @SerializedName("schoolName")
    var schoolName: String
) {
    data class Data(
        @SerializedName("id")
        var id: String,
        @SerializedName("firstname")
        var firstname: String,
        @SerializedName("lastname")
        var lastname: String,
        @SerializedName("phone")
        var phone: String,
        @SerializedName("email")
        var email: String,
        @SerializedName("schoolnid")
        var schoolnid: String,
        @SerializedName("uid")
        var uid: String,
        @SerializedName("timestamp")
        var timestamp: String,
        @SerializedName("college_id")
        var collegeId: String,
        @SerializedName("primary_contact")
        var primaryContact: Any,
        @SerializedName("note")
        var note: String
    )
}