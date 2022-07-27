package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class DashboardOverdueResponse(

	@field:SerializedName("assignment")
	val assignment: List<AssignmentItem?>? = null
)

data class AssignmentItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("task")
	val task: String? = null,

	@field:SerializedName("assignment_type")
	val assignmentType: String? = null,

	@field:SerializedName("assignee")
	val assignee: String? = null,

	@field:SerializedName("assigned_student")
	val assignedStudent: String? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("title")
	val title: Any? = null,

	@field:SerializedName("fid")
	val fid: Any? = null,

	@field:SerializedName("nid")
	val nid: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("assignment_req_type")
	val assignmentReqType: Any? = null,

	@field:SerializedName("unit")
	val unit: Any? = null,

	@field:SerializedName("category_id")
	val categoryId: String? = null,

	@field:SerializedName("overdue")
	val overdue: Int? = null,

	@field:SerializedName("grade")
	val grade: String? = null,

	@field:SerializedName("worksheet_file_id")
	val worksheetFileId: List<WorksheetFileIdItem?>? = null,

	@field:SerializedName("content_url")
	val contentUrl: Any? = null,

	@field:SerializedName("allow_edit_delete")
	val allowEditDelete: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("filename")
	val filename: String? = null,

	@field:SerializedName("verified_status")
	val verifiedStatus: Any? = null,

	@field:SerializedName("completed")
	val completed: Int? = null,

	@field:SerializedName("completed_date")
	val completedDate: String? = null,

	@field:SerializedName("verified_teacher")
	val verifiedTeacher: Any? = null,

	@field:SerializedName("verified_time")
	val verifiedTime: Any? = null
)

data class WorksheetFileIdItem(

	@field:SerializedName("file_name")
	val fileName: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
