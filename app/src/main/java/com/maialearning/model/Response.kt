package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("college_unit_id")
	val collegeUnitId: String? = null,

	@field:SerializedName("notes")
	val notes: String? = null,

	@field:SerializedName("college_name")
	val collegeName: String? = null,

	@field:SerializedName("academic_year")
	val academicYear: String? = null,

	@field:SerializedName("college_address")
	val collegeAddress: Any? = null,

	@field:SerializedName("due_date")
	val dueDate: String? = null,

	@field:SerializedName("application_mode")
	val applicationMode: String? = null,

	@field:SerializedName("completed")
	val completed: Int? = null
)
