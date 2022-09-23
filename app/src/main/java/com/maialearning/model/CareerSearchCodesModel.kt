package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class CareerSearchCodesModel(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("career")
	val career: List<CareerItem?>? = null,

	@field:SerializedName("start")
	val start: Int? = null,

	@field:SerializedName("link")
	val link: List<LinkItem?>? = null,

	@field:SerializedName("end")
	val end: Int? = null,

	@field:SerializedName("keyword")
	val keyword: String? = null
) {

	data class CareerItem(

		@field:SerializedName("code")
		val code: String? = null,

		@field:SerializedName("href")
		val href: String? = null,

		@field:SerializedName("title")
		val title: String? = null,

		@field:SerializedName("tags")
		val tags: Tags? = null
	)

	data class Tags(

		@field:SerializedName("bright_outlook")
		val brightOutlook: Boolean? = null,

		@field:SerializedName("green")
		val green: Boolean? = null,

		@field:SerializedName("apprenticeship")
		val apprenticeship: Boolean? = null
	)

	data class LinkItem(

		@field:SerializedName("rel")
		val rel: String? = null,

		@field:SerializedName("href")
		val href: String? = null
	)
}
