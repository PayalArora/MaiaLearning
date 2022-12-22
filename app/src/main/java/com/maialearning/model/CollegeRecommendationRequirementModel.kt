package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class CollegeRecommendationRequirementModel(
    @SerializedName("counselor_eval_reqd")
    var counselorEvalReqd: Boolean?,
    @SerializedName("min_te_reqd")
    var minTeReqd: String?,
    @SerializedName("maxTeReqd")
    var max_te_reqd: String?,
    @SerializedName("college_nid")
    var collegeNid: String,
    @SerializedName("college_name")
    var collegeName: String,

    var recomendorList: ArrayList<RecommenderModel>? =null


)
