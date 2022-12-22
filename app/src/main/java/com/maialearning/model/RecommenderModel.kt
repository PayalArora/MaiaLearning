package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class RecommenderModel(
    @SerializedName("recommender_uid")
    var recommenderUid: String,
    @SerializedName("recommender_name")
    var recommenderName: String,
    @SerializedName("recommendation_nid")
    var recommendationNid: String,
    @SerializedName("college_nid")
    var collegeNid: String,
    @SerializedName("college_name")
    var collegeName: String,
    @SerializedName("preferred_recommender")
    var preferredRecommender: Int,
    @SerializedName("set_by_counselor")
    var setByCounscelor: Int = 0

)
