package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class StudentCarrerPlanResponse(

	@field:SerializedName("useful_work_experience")
	val usefulWorkExperience: String? = null,

	@field:SerializedName("work_values")
	val workValues: List<WorkValuesItem?>? = null,

	@field:SerializedName("nid")
	val nid: String? = null,

	@field:SerializedName("action_plan_knowledge")
	val actionPlanKnowledge: String? = null,

	@field:SerializedName("technology")
	val technology: List<TechnologyItem?>? = null,

	@field:SerializedName("tools")
	val tools: List<ToolsItem?>? = null,

	@field:SerializedName("skills")
	val skills: List<SkillItems?>? = null,

	@field:SerializedName("action_plan_tools")
	val actionPlanTools: Any? = null,

	@field:SerializedName("people_list")
	val peopleList: Any? = null,

	@field:SerializedName("careers")
	val careers: List<CareersItem?>? = null,

	@field:SerializedName("action_plan_work_value")
	val actionPlanWorkValue: String? = null,

	@field:SerializedName("ability")
	val ability: List<AbilityItem?>? = null,

	@field:SerializedName("knowledge")
	val knowledge: List<KnowledgeItem?>? = null
)

data class WorkValuesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class TechnologyItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class ToolsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class CareersItem(

	@field:SerializedName("technologies")
	val technologies: List<Any?>? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("educations")
	val educations: List<String?>? = null
)

data class TechnologiesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class SkillItems(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class AbilityItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class KnowledgeItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
