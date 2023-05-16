package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class CareerListResponse(var careerList: ArrayList<CareerItem>?){

data class CareerItem(

	@field:SerializedName("videoUrl")
	val videoUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("averageSalary")
	val averageSalary: Int? = null,

	@field:SerializedName("pathways")
	val pathways: List<PathwaysItem?>? = null,

	@field:SerializedName("categories")
	val categories: List<CategoriesItem?>? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("onetId")
	val onetId: String? = null,
	@field:SerializedName("nid")
	val nid: String? = null
)
	data class CategoriesItem(

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("id")
		val id: String? = null
	)

	data class PathwaysItem(

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("id")
		val id: String? = null
	)

}