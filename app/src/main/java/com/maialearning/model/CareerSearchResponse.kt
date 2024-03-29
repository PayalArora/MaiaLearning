package com.maialearning.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
	@field:SerializedName("uniquePostings")
	val uniquePostings: String? = null,
    var isfav: Boolean = false,
	var nid: String?,
	var search_type:String = ""
)  : Serializable
