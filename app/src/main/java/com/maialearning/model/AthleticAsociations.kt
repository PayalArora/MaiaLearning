package com.maialearning.ui.model

import com.google.gson.annotations.SerializedName

data class AthleticAsociations(

	@field:SerializedName("Response")
	var response: ArrayList<ResponseItem>? = null
)

data class ResponseItem(

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("key")
	val key: String? = null,

	@field:SerializedName("children")
	val children: ArrayList<ChildrenItem>? = null,
    var checked: Boolean = false
)

data class ChildrenItem(

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("key")
	val key: String? = null,
	var checked: Boolean = false
)
