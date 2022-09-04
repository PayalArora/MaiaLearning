package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class GermanUniversitiesResponse(
    @SerializedName("pager") var pager: Pager? = Pager(),
    @SerializedName("data") var data: Data? = Data()

) {
    data class Pager(
        @SerializedName("current") var current: Int? = null,
        @SerializedName("last") var last: Int? = null,
        @SerializedName("total") var total: String? = null
    )

    data class Data(

        @SerializedName("college_data") var collegeData: ArrayList<CollegeData> = arrayListOf()

    ) {
        data class CollegeData(

            @SerializedName("college_nid") var collegeNid: String? = null,
            @SerializedName("college_name") var collegeName: String? = null,
            @SerializedName("course_count") var courseCount: String? = null,
            @SerializedName("top_pick_flag") var topPickFlag: Int? = null,
            @SerializedName("college_logo") var collegeLogo: String? = null,
            @SerializedName("course_list") var courseList: ArrayList<CourseList> = arrayListOf()

        ) {
            data class CourseList(
                @SerializedName("course_id") var courseId: String? = null,
                @SerializedName("course_name") var courseName: String? = null,
                @SerializedName("location") var location: String? = null,
                @SerializedName("study_mode") var studyMode: String? = null

            )
        }
    }
}