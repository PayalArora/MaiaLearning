package com.maialearning.model

class RecCollegeModel {
    lateinit var id: String
    lateinit var collegeDetails: CollegeDetails
    var currentPage:String ?=null
    var last:String ?=null
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
        var recoName: ArrayList<RecomenderName>? = null
    )

    data class RecomenderName(
        val done: String? = null,

        val preserved_data: String? = null,

            val cancel: String? = null,

        val reco_created: String? = null,
//
//        @field:SerializedName("college_address")
//        val collegeAddress: Any? = null,

        val req_fileid: String? = null,

        val req_filename: String? = null,


    )
}