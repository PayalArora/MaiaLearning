package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class CareerFilterResponse(

	@field:SerializedName("county")
	var county: ArrayList<CountyItem>? = null,

	@field:SerializedName("msa")
	var msa: ArrayList<MsaItem>? = null,

	@field:SerializedName("state")
	var state: ArrayList<StateItem>? = null
)

data class StateItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class CountyItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class MsaItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
