package com.maialearning.model

data class EthnicityResponse(
	val ethnicityResponse: List<EthnicityResponseItem?>? = null
)

data class EthnicityResponseItem(
	val jsonMemberDefault: String? = null,
	val code: String? = null,
	val name: String? = null,
	val id: String? = null,
	val type: String? = null,
	val enabled: Int? = null
)

