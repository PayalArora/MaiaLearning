package com.maialearning.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class SurveysResponse(

	@field:SerializedName("data")
	val data: List<SurveyDataItem?>? = null,
	@field:SerializedName("student_survey_responses")
    val studentSurveyResponses: JsonObject? = null

)
data class StudentSurveyResponses(
	@field:SerializedName("survey_response_uuid")
	val uid: String? = null,
	@field:SerializedName("survey_response_uuid")
	val response_status: String? = null,
)


data class Data(

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("user_profile_nid")
	val userProfileNid: String? = null,

	@field:SerializedName("application_year")
	val applicationYear: Any? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class AuthorData(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null
)

data class SurveyDataItem(

	@field:SerializedName("template_status")
	val templateStatus: Any? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("assigned_with_user")
	val assignedWithUser: List<String>? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: Any? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null,

	@field:SerializedName("allow_others_to_edit")
	val allowOthersToEdit: Boolean? = null,

	@field:SerializedName("assigned_with_group")
	val assignedWithGroup: List<Any?>? = null,

	@field:SerializedName("revision_author_uuid")
	val revisionAuthorUuid: Any? = null,

	@field:SerializedName("scope")
	val scope: Any? = null,

	@field:SerializedName("bundle")
	val bundle: String? = null,

	@field:SerializedName("revision_id")
	val revisionId: Int? = null,

	@field:SerializedName("author_data")
	val authorData: AuthorData? = null,

	@field:SerializedName("created")
	val created: Int? = null,

	@field:SerializedName("end_time")
	val endTime: String? = null,

	@field:SerializedName("assigned_with_role")
	val assignedWithRole: List<String?>? = null,

	@field:SerializedName("shared_with_user")
	val sharedWithUser: List<Any?>? = null,

	@field:SerializedName("shared_with_role")
	val sharedWithRole: List<Any?>? = null,

	@field:SerializedName("assigned_with_grade")
	val assignedWithGrade: List<String?>? = null,

	@field:SerializedName("survey_question")
	val surveyQuestion: List<SurveyQuestionItem?>? = null,

	@field:SerializedName("shared_with_school")
	val sharedWithSchool: List<Any?>? = null,

	@field:SerializedName("start_time")
	val startTime: String? = null,

	@field:SerializedName("updated_by")
	val updatedBy: String? = null,

	@field:SerializedName("assigned_with_class_of")
	val assignedWithClassOf: List<Any?>? = null,

	@field:SerializedName("user")
	val user: String? = null,

	@field:SerializedName("changed")
	val changed: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class SurveyQuestionItem(

	@field:SerializedName("revision_author_uuid")
	val revisionAuthorUuid: Any? = null,

	@field:SerializedName("created")
	val created: Int? = null,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null,

	@field:SerializedName("bundle")
	val bundle: String? = null,

	@field:SerializedName("user")
	val user: String? = null,

	@field:SerializedName("required")
	val required: Boolean? = null,

	@field:SerializedName("revision_id")
	val revisionId: Int? = null,

	@field:SerializedName("changed")
	val changed: Int? = null,

	@field:SerializedName("options")
	val options: List<String?>? = null
)
