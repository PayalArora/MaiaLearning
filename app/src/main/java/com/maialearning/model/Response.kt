package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("basic_info")
	val basicInfo: BasicInfo? = null,

	@field:SerializedName("course_list")
	val courseList: List<Any?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("provider_info")
	val providerInfo: String? = null
)

data class BasicInfo(

	@field:SerializedName("basic_info")
	val basicInfo: BasicInfo? = null,

	@field:SerializedName("zip")
	val zip: String? = null,

	@field:SerializedName("institution_type")
	val institutionType: String? = null,

	@field:SerializedName("distance_from_home")
	val distanceFromHome: Any? = null,

	@field:SerializedName("distance_from_school")
	val distanceFromSchool: Any? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("latitude")
	val latitude: Any? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("environment_type")
	val environmentType: Any? = null,

	@field:SerializedName("award")
	val award: Any? = null,

	@field:SerializedName("enr_total")
	val enrTotal: Any? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("web_addr")
	val webAddr: String? = null,

	@field:SerializedName("state")
	val state: Any? = null,

	@field:SerializedName("addr")
	val addr: String? = null,

	@field:SerializedName("term_type")
	val termType: Any? = null,

	@field:SerializedName("longitude")
	val longitude: Any? = null
)
