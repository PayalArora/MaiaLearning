package com.maialearning.model

data class UniversitySearchResponse(
	var last: Int? = null,
	var pager: Int? = null,
	var totalRecords: Int? = null,
	var university_list: ArrayList<UniversitiesSearchModel>? = null
)



