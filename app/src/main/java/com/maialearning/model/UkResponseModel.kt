package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class UkResponseModel(
	@SerializedName("pager" ) var pager : Pager? = Pager(),
	@SerializedName("data"  ) var data  : Data?  = Data()

){
	data class Data (

		@SerializedName("college_data" ) var collegeData : ArrayList<CollegeData> = arrayListOf()

	){
		data class CollegeData (

			@SerializedName("college_nid"   ) var collegeNid  : String?     = null,
			@SerializedName("college_name"  ) var collegeName : String?     = null,
			@SerializedName("parchment"     ) var parchment   : Int?        = null,
			@SerializedName("slate"         ) var slate       : Int?        = null,
			@SerializedName("course_count"  ) var courseCount : String?     = null,
			@SerializedName("top_pick_flag" ) var topPickFlag : Int?        = null,
			@SerializedName("file_name"     ) var fileName    : String?     = null,
			var courseList  : ArrayList<CourseList> = ArrayList<CourseList>()

		){
				data class CourseList(
					@SerializedName("course_id") var courseId: String? = "",
					@SerializedName("course_name") var courseName: String? = "",
					@SerializedName("option_count") var location: String? = "",
					@SerializedName("a_level") var studyMode: String? = ""
				)
		}
	}
	data class Pager (
		@SerializedName("current" ) var current : Int?    = null,
		@SerializedName("last"    ) var last    : Int?    = null,
		@SerializedName("total"   ) var total   : String? = null
	)
}



