package com.maialearning.model

import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class SurveyQuestionAnswer (
    @field:SerializedName("survey_uuid")
    var surveyUuid: String? = null,

    @field:SerializedName("survey_response_uuid")
    var surveyResponseUuid: String? = null,

    @field:SerializedName("survey_question_uuid")
    var surveyQuestionUuid: String? = null,

    @field:SerializedName("response_status")
    var responseStatus: String? = null,

    @field:SerializedName("survey_answer_value")
    var survey_answer_value: String? = null,

    @field:SerializedName("options")
    var options:JSONArray? = null,

    @field:SerializedName("weight")
    var weight: Int? = null,

    @field:SerializedName("title")
    var title: String? = null,
    @field:SerializedName("description")
    var description: String? = null,
    var main_title: String? = null,
    @field:SerializedName("uuid")
    var uuid: String? = null,

    @field:SerializedName("bundle")
    var bundle: String? = null,

    @field:SerializedName("user")
    var user: Any? = null,

    @field:SerializedName("required")
    var required: Boolean? = null,
    var arr_choice:ArrayList<KeyVal>? = null,

    )
