package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class RelatedCareerModel(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("nid")
	val nid: String? = null,

	@field:SerializedName("node_title")
	val nodeTitle: String? = null
)
