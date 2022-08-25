package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class WebinarResponse(

	@field:SerializedName("total")
	val total: String? = null,

	@field:SerializedName("data")
	val data: List<WebinarDataItem?>? = null
)

data class WebinarDataItem(

	@field:SerializedName("join_url")
	val joinUrl: String? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null,

	@field:SerializedName("chosen_university")
	val chosenUniversity: Boolean? = null,

	@field:SerializedName("webinar")
	val webinar: Webinar? = null
)

data class Webinar(

	@field:SerializedName("start_time")
	val startTime: Int? = null,

	@field:SerializedName("university_introduction")
	val universityIntroduction: String? = null,

	@field:SerializedName("end_time")
	val endTime: Int? = null,

	@field:SerializedName("topic")
	val topic: String? = null,

	@field:SerializedName("university_name")
	val universityName: String? = null,

	@field:SerializedName("program")
	val program: List<Any?>? = null,

	@field:SerializedName("session_type")
	val sessionType: String? = null,

	@field:SerializedName("external_registration")
	val externalRegistration: Boolean? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null
)
