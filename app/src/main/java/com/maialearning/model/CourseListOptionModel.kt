package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class CourseListOptionModel(
    @SerializedName("course_option_id") var courseOptionId: String? = "",
    @SerializedName("OutcomeQualification") var outcomeQualification: String? = "",
    @SerializedName("StudyMode") var studyMode: String? = "",
    @SerializedName("StartDate") var startDate: String? = "",
    @SerializedName("ib_score") var ibScore: String? = "",
    @SerializedName("alevels") var alevels: String? = "",
    @SerializedName("DurationValue") var durationValue: String? = "",
    @SerializedName("DurationType") var durationType: String? = "",
    @SerializedName("LocationName") var locationName: String? = "",
)
