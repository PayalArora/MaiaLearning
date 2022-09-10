package com.maialearning.model

class RecCollegeModel {
    lateinit var id: String
    lateinit var collegeDetails: CollegeDetails

    data class CollegeDetails(
        val collegeUnitId: String? = null,

        val notes: String? = null,

        val collegeName: String? = null,

        val academicYear: String? = null,
//
//        @field:SerializedName("college_address")
//        val collegeAddress: Any? = null,

        val dueDate: String? = null,

        val applicationMode: String? = null,

        val completed: Int? = null,
     //   var recoName: ArrayList<RecomenderName>? = null
    )
}