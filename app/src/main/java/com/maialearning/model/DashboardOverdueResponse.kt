package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class DashboardOverdueResponse(

    @field:SerializedName("assignment")
    val assignment: List<AssignmentItem?>? = null
) {
    data class AssignmentItem(

        @field:SerializedName("date")
        var date: String? = null,

        @field:SerializedName("written_response")
        var writtenResponse: Any? = null,

        @field:SerializedName("task")
        var task: String? = null,

        @field:SerializedName("assignment_type")
        var assignmentType: String? = null,

        @field:SerializedName("assignee")
        var assignee: String? = null,

        @field:SerializedName("assigned_student")
        var assignedStudent: String? = null,

        @field:SerializedName("body")
        var body: String? = null,

        @field:SerializedName("category")
        var category: String? = null,

        @field:SerializedName("title")
        var title: Any? = null,

        @field:SerializedName("fid")
        var fid: Any? = null,

        @field:SerializedName("nid")
        var nid: String? = null,

        @field:SerializedName("type")
        var type: String? = null,

        @field:SerializedName("assignment_req_type")
        var assignmentReqType: Any? = null,

        @field:SerializedName("unit")
        var unit: Any? = null,

        @field:SerializedName("category_id")
        var categoryId: String? = null,

        @field:SerializedName("overdue")
        var overdue: Int? = null,

        @field:SerializedName("grade")
        var grade: String? = null,

        @field:SerializedName("worksheet_file_id")
        var worksheetFileId: List<WorksheetFileIdItem?>? = null,

        @field:SerializedName("content_url")
        var contentUrl: Any? = null,

        @field:SerializedName("allow_edit_delete")
        var allowEditDelete: Int? = null,

        @field:SerializedName("status")
        var status: Int? = null,

        @field:SerializedName("filename")
        var filename: String? = null,

        @field:SerializedName("verified_status")
        var verifiedStatus: Any? = null,

        @field:SerializedName("completed")
        var completed: Int? = null,

        @field:SerializedName("completed_date")
        var completedDate: String? = null,

        @field:SerializedName("verified_teacher")
        var verifiedTeacher: Any? = null,

        @field:SerializedName("verified_time")
        var verifiedTime: Any? = null,


        @field:SerializedName("survey_question")
        var surveyQuestion: List<SurveyQuestionItem?>? = null

    ) {
        data class WorksheetFileIdItem(

            @field:SerializedName("file_name")
            val fileName: String? = null,

            @field:SerializedName("id")
            val id: String? = null
        )
    }
}




