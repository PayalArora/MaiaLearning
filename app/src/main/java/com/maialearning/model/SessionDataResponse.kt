package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class SessionDataResponse(

	@field:SerializedName("data")
	val data: Data? = null
) {

	data class CareerRegionPreference(

		@field:SerializedName("region_name")
		val regionName: String? = null,

		@field:SerializedName("region_level")
		val regionLevel: String? = null,

		@field:SerializedName("region_code")
		val regionCode: String? = null
	)

	data class Notes(

		@field:SerializedName("unread")
		val unread: Int? = null
	)

	data class Data(

		@field:SerializedName("notes")
		val notes: Notes? = null,

		@field:SerializedName("users")
		val users: Users? = null
	)

	data class Users(

		@field:SerializedName("career_region_preference")
		val careerRegionPreference: CareerRegionPreference? = null
	)
}
