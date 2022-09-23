package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class SelectedCareerResponse(

	@field:SerializedName("work_activities")
	val workActivities: ArrayList<WorkActivitiesItem?>? = null,

	@field:SerializedName("career_description")
	val careerDescription: String? = null,

	@field:SerializedName("responsibilities")
	val responsibilities: ArrayList<String?>? = null,

	@field:SerializedName("interest")
	val interest: ArrayList<InterestItem?>? = null,

	@field:SerializedName("related_careers")
	val relatedCareers: List<RelatedCareersItem?>? = null,

	@field:SerializedName("skill")
	val skill: ArrayList<SkillItem?>? = null,

	@field:SerializedName("academic_knowledge")
	val academicKnowledge: ArrayList<AcademicKnowledgeItem?>? = null,

	@field:SerializedName("education_level")
	val educationLevel: List<Any?>? = null,

	@field:SerializedName("career_title")
	val careerTitle: String? = null,

	@field:SerializedName("salary")
	val salary: String? = null,

	@field:SerializedName("careeronestop_video")
	val careeronestopVideo: String? = null,

	@field:SerializedName("year_wise_code")
	val yearWiseCode: YearWiseCode? = null
)

data class AcademicKnowledgeItem(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class YearWiseCode(

	@field:SerializedName("2019")
	val jsonMember2019: String? = null
)

data class RelatedCareersItem(

	@field:SerializedName("bright_outlook")
	val brightOutlook: String? = null,

	@field:SerializedName("career_id")
	val careerId: String? = null,

	@field:SerializedName("career_title")
	val careerTitle: String? = null
)

data class WorkActivitiesItem(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class InterestItem(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class SkillItem(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
