package com.maialearning.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CareerTopPickResponse(

	@field:SerializedName("CareerTopPickResponse")
	val careerTopPickResponse: List<CareerTopPickResponseItem?>? = null
)

data class CareerTopPickResponseItem(

	@field:SerializedName("bright_outlook")
	val brightOutlook: List<String?>? = null,

	@field:SerializedName("ccode")
	val ccode: String? = null,

	@field:SerializedName("education")
	val education: List<String?>? = null,

	@field:SerializedName("add_to_career_plan_status")
	val addToCareerPlanStatus: String? = null,

	@field:SerializedName("nid")
	val nid: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("salary")
	val salary: String? = null,
	var selected: Boolean = false
) : Serializable
