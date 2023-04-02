package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class CareerCategoryResponse(

	@field:SerializedName("CareerCategoryResponse")
	val careerCategoryResponse: ArrayList<CareerCategoryResponseItem>? = null
)

data class CareerCategoryResponseItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("onets")
	val onets: List<String?>? = null,

	@field:SerializedName("id")
	val id: String? = null
)
