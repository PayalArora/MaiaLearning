package com.maialearning.model

import com.google.gson.annotations.SerializedName



data class FmTagsResponseItem(

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("school_id")
	val schoolId: String? = null,

	@field:SerializedName("created")
	val created: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("used")
	val used: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("updated")
	val updated: String? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
