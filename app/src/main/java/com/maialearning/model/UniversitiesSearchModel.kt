package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class UniversitiesSearchModel(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("parchment")
	val parchment: Int? = null,

	@field:SerializedName("college_name")
	val collegeName: String? = null,

	@field:SerializedName("city_state")
	val cityState: String? = null,

	@field:SerializedName("nid")
	val nid: String? = null,

	@field:SerializedName("act_scores")
	val actScores: String? = null,

	@field:SerializedName("campus_type")
	val campusType: String? = null,

	@field:SerializedName("top_pick_flag")
	var topPickFlag: Int? = null,

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("sat_scores")
	val satScores: String? = null,

	@field:SerializedName("acceptance")
	val acceptance: String? = null,

	@field:SerializedName("graduation_rate")
	val graduationRate: Any? = null,

	@field:SerializedName("college_type")
	val collegeType: String? = null,

	@field:SerializedName("unitid")
	val unitid: String? = null,

	@field:SerializedName("college_ug_population")
	val collegeUgPopulation: String? = null,

	@field:SerializedName("slate")
	val slate: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)
