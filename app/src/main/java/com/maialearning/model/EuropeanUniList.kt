package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class EuropeanUniList(
    @SerializedName("total_records") var totalRecords: Int? = null,
    @SerializedName("pager") var pager: Int? = null,
    @SerializedName("last") var last: Int? = null,
    @SerializedName("college_list") var collegeList: ArrayList<CollegeList> = arrayListOf()
) {
    data class CollegeList(
        @SerializedName("college_nid") var collegeNid: String? = null,
        @SerializedName("college_name") var collegeName: String? = null,
        @SerializedName("program_list") var programList: ArrayList<ProgramList> = arrayListOf(),
        @SerializedName("top_pick_flag") var topPickFlag: Boolean? = null,
        @SerializedName("parchment") var parchment: Int? = null,
        @SerializedName("slate") var slate: Int? = null
    ) {
        data class ProgramList(
            @SerializedName("program_id") var programId: String? = null,
            @SerializedName("program_name") var programName: String? = null,
            @SerializedName("program_tuition_fee") var programTuitionFee: String? = null,
            @SerializedName("program_subject_areas") var programSubjectAreas: ArrayList<String> = arrayListOf(),
            @SerializedName("duration") var duration: String? = null
        )
    }
}