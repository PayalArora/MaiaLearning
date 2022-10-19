package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class CourseListModel(
    @SerializedName("course_id") var courseId: String? = "",
    @SerializedName("course_name") var courseName: String? = "",
    @SerializedName("option_count") var optionCount: String? = "",
    @SerializedName("a_level") var aLevel: String? = "",
    @SerializedName("ib") var ib: String? = "",
    var courseOptionList: ArrayList<CourseListOptionModel> = ArrayList<CourseListOptionModel>()
)
