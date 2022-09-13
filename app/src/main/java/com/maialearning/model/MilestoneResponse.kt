package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class MilestoneResponse(

	@field:SerializedName("itask")
	val itask: List<ItaskItem?>? = null
)

//data class WorksheetFileIdItem(
//
//	@field:SerializedName("file_name")
//	val fileName: String? = null,
//
//	@field:SerializedName("id")
//	val id: String? = null
//)

data class ItaskItem(

	@field:SerializedName("fid")
	val fid: Any? = null,

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("educator_feedback")
	val educatorFeedback: Any? = null,

	@field:SerializedName("nid")
	val nid: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("submission_required")
	val submissionRequired: String? = null,

	@field:SerializedName("category_id")
	val categoryId: String? = null,

	@field:SerializedName("written_response")
	val writtenResponse: Any? = null,

	@field:SerializedName("worksheet_file_id")
	val worksheetFileId: List<WorksheetFileIdItem?>? = null,

	@field:SerializedName("allow_edit_delete")
	val allowEditDelete: Int? = null,

	@field:SerializedName("course_id")
	val courseId: Any? = null,

	@field:SerializedName("course_name")
	val courseName: Any? = null,

	@field:SerializedName("sequence_order")
	val sequenceOrder: Any? = null,

	@field:SerializedName("assignment_req_type")
	val assignmentReqType: Any? = null,

	@field:SerializedName("new_lesson_plan_id")
	val newLessonPlanId: Any? = null,

	@field:SerializedName("unit")
	val unit: Any? = null,

	@field:SerializedName("task")
	val task: String? = null,

	@field:SerializedName("grade")
	val grade: Any? = null,

	@field:SerializedName("content_url")
	val contentUrl: Any? = null,

	@field:SerializedName("assignee")
	val assignee: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("assigned_student")
	val assignedStudent: String? = null,

	@field:SerializedName("status")
	var status: Int? = null,

	@field:SerializedName("verified_status")
	val verifiedStatus: Any? = null,

	@field:SerializedName("completed_date")
	val completedDate: String? = null,

	@field:SerializedName("verified_time")
	val verifiedTime: Any? = null,

	@field:SerializedName("verified_teacher")
	val verifiedTeacher: Any? = null
)
