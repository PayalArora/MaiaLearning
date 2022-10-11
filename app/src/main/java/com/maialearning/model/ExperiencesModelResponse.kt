package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class ExperiencesModelResponse(

	@field:SerializedName("ExperiencesModelResponse")
	val experiencesModelResponse: List<ExperiencesModelResponseItem?>? = null
)

data class ExperiencesModelResponseItem(

	@field:SerializedName("hours")
	val hours: String? = null,

	@field:SerializedName("weeks")
	val weeks: String? = null,

	@field:SerializedName("award")
	val award: String? = null,

	@field:SerializedName("year")
	val year: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("add_resume")
	val addResume: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
