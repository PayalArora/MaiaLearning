package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class CareerFactsheetResponse(

	@field:SerializedName("percentRetiringSoon")
	val percentRetiringSoon: Double? = null,

	@field:SerializedName("percentFemale")
	val percentFemale: Double? = null,

	@field:SerializedName("commonSkills")
	val commonSkills: List<CommonSkillsItem?>? = null,

	@field:SerializedName("relatedCareers")
	val relatedCareers: List<RelatedCareersItem?>? = null,

	@field:SerializedName("ageBreakdown")
	val ageBreakdown: ArrayList<AgeBreakdownItem>? = null,

	@field:SerializedName("startMonth")
	val startMonth: String? = null,

	@field:SerializedName("raceEthnicityBreakdown")
	val raceEthnicityBreakdown: ArrayList<RaceEthnicityBreakdownItem>? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("socId")
	val socId: String? = null,

	@field:SerializedName("totalEmployment")
	val totalEmployment: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("topUniversities")
	val topUniversities: List<TopUniversitiesItem?>? = null,

	@field:SerializedName("currentYear")
	val currentYear: String? = null,

	@field:SerializedName("topJobTitles")
	val topJobTitles: List<TopJobTitlesItem?>? = null,

	@field:SerializedName("regionCode")
	val regionCode: String? = null,

	@field:SerializedName("employersCompeting")
	val employersCompeting: Int? = null,

	@field:SerializedName("videoUrl")
	val videoUrl: String? = null,

	@field:SerializedName("topJobCompanies")
	val topJobCompanies: List<TopJobCompaniesItem?>? = null,

	@field:SerializedName("workActivities")
	val workActivities: List<String?>? = null,

	@field:SerializedName("educationBreakdown")
	val educationBreakdown: ArrayList<EducationBreakdownItem>? = null,

	@field:SerializedName("categories")
	val categories: ArrayList<CategoriesItem>? = null,

	@field:SerializedName("specializedSkills")
	val specializedSkills: List<SpecializedSkillsItem?>? = null,

	@field:SerializedName("genderBreakdown")
	val genderBreakdown: ArrayList<GenderBreakdownItem>? = null,

	@field:SerializedName("uniqueJobPostings")
	val uniqueJobPostings: Int? = null,

	@field:SerializedName("pathways")
	val pathways: ArrayList<PathwaysItem>? = null,

	@field:SerializedName("regionLevel")
	val regionLevel: String? = null,

	@field:SerializedName("onetId")
	val onetId: String? = null,

	@field:SerializedName("averagePostingDuration")
	val averagePostingDuration: Int? = null,

	@field:SerializedName("softwareSkills")
	val softwareSkills: List<SoftwareSkillsItem?>? = null,

	@field:SerializedName("averageSalary")
	val averageSalary: Double? = null,

	@field:SerializedName("demandTrend")
	val demandTrend: List<DemandTrendItem?>? = null,

	@field:SerializedName("cipCodes")
	val cipCodes: List<String?>? = null,

	@field:SerializedName("programs")
	val programs: List<ProgramsItem?>? = null,

	@field:SerializedName("endMonth")
	val endMonth: String? = null
) {

	data class TopJobTitlesItem(

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("postings")
		val postings: Int? = null
	)

	data class EducationBreakdownItem(

		@field:SerializedName("jobs")
		val jobs: Int? = null,

		@field:SerializedName("percent")
		val percent: Double? = null,

		@field:SerializedName("group")
		val group: String? = null
	)

	data class SpecializedSkillsItem(

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("postings")
		val postings: Int? = null
	)

	data class TopUniversitiesItem(

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("completions")
		val completions: Int? = null
	)

	data class SoftwareSkillsItem(

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("postings")
		val postings: Int? = null
	)

	data class RaceEthnicityBreakdownItem(

		@field:SerializedName("jobs")
		val jobs: Int? = null,

		@field:SerializedName("percent")
		val percent: Double? = null,

		@field:SerializedName("group")
		val group: String? = null
	)

	data class DemandTrendItem(

		@field:SerializedName("month")
		val month: String? = null,

		@field:SerializedName("hires")
		val hires: Int? = null,

		@field:SerializedName("postings")
		val postings: Int? = null
	)

	data class CommonSkillsItem(

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("postings")
		val postings: Int? = null
	)

	data class GenderBreakdownItem(

		@field:SerializedName("jobs")
		val jobs: Int? = null,

		@field:SerializedName("percent")
		val percent: Double? = null,

		@field:SerializedName("group")
		val group: String? = null
	)

	data class ProgramsItem(

		@field:SerializedName("code")
		val code: String? = null,

		@field:SerializedName("description")
		val description: String? = null,

		@field:SerializedName("title")
		val title: String? = null
	)

	data class TopJobCompaniesItem(

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("postings")
		val postings: Int? = null
	)

	data class AgeBreakdownItem(

		@field:SerializedName("jobs")
		val jobs: Int? = null,

		@field:SerializedName("percent")
		val percent: Double? = null,

		@field:SerializedName("group")
		val group: String? = null
	)

	data class CategoriesItem(

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("id")
		val id: Int? = null
	)

	data class PathwaysItem(

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("id")
		val id: String? = null
	)

	data class RelatedCareersItem(

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("onetId")
		val onetId: String? = null
	)
}