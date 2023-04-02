package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class CareerSearchResponse(

	@field:SerializedName("CareerSearchResponse")
	val careerSearchResponse: List<CareerSearchResponseItem?>? = null
)

data class CareerSearchResponseItem(

	@field:SerializedName("videoUrl")
	val videoUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("averageSalary")
	val averageSalary: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("onetId")
	val onetId: String? = null,
    var isfav: Boolean = false,
	var nid: String?
)
