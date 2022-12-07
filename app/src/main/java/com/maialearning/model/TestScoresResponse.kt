package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class TestScoresResponse(

    @field:SerializedName("TestScoresResponse")
    val testScoresResponse: List<TestScoresResponseItem?>? = null
)

data class TestScoresResponseItem(

    @field:SerializedName("status_id")
    var statusId: String? = null,

    @field:SerializedName("transcript_nid")
    val transcriptNid: String? = null,

    @field:SerializedName("college_name")
    val collegeName: String? = null,

    @field:SerializedName("field_college_target_id")
    val fieldCollegeTargetId: String? = null,

    var change: Boolean = false
)
