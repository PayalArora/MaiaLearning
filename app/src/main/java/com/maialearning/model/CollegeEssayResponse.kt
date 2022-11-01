package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class CollegeEssayResponse(

	@field:SerializedName("total")
	val total: String? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("limit")
	val limit: String? = null,

	@field:SerializedName("page")
	val page: String? = null
)

data class DataItem(

	@field:SerializedName("prompt_topic")
	val promptTopic: String? = null,

	@field:SerializedName("prompt_max_length_unit")
	val promptMaxLengthUnit: String? = null,

	@field:SerializedName("essay_due_date")
	val essayDueDate: Any? = null,

	@field:SerializedName("created")
	val created: String? = null,

	@field:SerializedName("nid")
	val nid: String? = null,

	@field:SerializedName("essay_text")
	val essayText: String? = null,

	@field:SerializedName("prompt_description")
	val promptDescription: String? = null,

	@field:SerializedName("prompt_slug")
	val promptSlug: Any? = null,

	@field:SerializedName("essay_application")
	val essayApplication: Any? = null,

	@field:SerializedName("prompt_name")
	val promptName: String? = null,

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("student_name")
	val studentName: String? = null,

	@field:SerializedName("essay_comments")
	val essayComments: String? = null,

	@field:SerializedName("prompt_max_length_value")
	val promptMaxLengthValue: String? = null,

	@field:SerializedName("essay_reviewer")
	val essayReviewer: EssayReviewer? = null,

	@field:SerializedName("essay_note_for_reviewer")
	val essayNoteForReviewer: Any? = null,

	@field:SerializedName("essay_sent_to_student_date")
	val essaySentToStudentDate: Any? = null,

	@field:SerializedName("essay_status")
	val essayStatus: String? = null,

	@field:SerializedName("changed")
	val changed: String? = null,

	@field:SerializedName("essay_colleges")
	val essayColleges: Any? = null,

	@field:SerializedName("essay_note_for_student")
	val essayNoteForStudent: Any? = null
)

data class EssayCollegesItem(

	@field:SerializedName("iped")
	val iped: String? = null,

	@field:SerializedName("nid")
	val nid: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class EssayReviewer(

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)
