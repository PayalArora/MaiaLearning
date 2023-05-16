package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class CeebResponse(

	@field:SerializedName("CeebResponse")
	val ceebResponse: List<CeebResponseItem?>? = null
)

data class CeebResponseItem(

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("field_ceeb_code_value")
	val fieldCeebCodeValue: String? = null
)
