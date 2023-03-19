package com.maialearning.ui.model

import com.maialearning.model.SurveyQuestionAnswer
import org.json.JSONObject

class SurveyDetailParser(val it: JSONObject?) {
    fun parse(id: String): ArrayList<SurveyQuestionAnswer> {
        val jsonObject = it?.optJSONObject("data")
        val survey_details = it?.optJSONObject("survey_details")

        val arr = arrayListOf<SurveyQuestionAnswer>()
        jsonObject.let {
            val subJsonObject = it?.optJSONObject(id)
            subJsonObject.let { subJsonObject ->
                val survey_question_answers =
                    subJsonObject?.optJSONObject("survey_question_answers")
//                survey_question_answers?.let {
//                    val x = survey_question_answers?.keys() as Iterator<String>
//                    while (x.hasNext()) {
//                        val key: String = x.next()
//                        val surveyQuestionAnswer = SurveyQuestionAnswer()
//
//                        surveyQuestionAnswer.surveyResponseUuid =
//                            subJsonObject?.optString("survey_response_uuid")
//                        surveyQuestionAnswer.responseStatus =
//                            subJsonObject?.optString("response_status")
//                        surveyQuestionAnswer.uuid = survey_details?.optString("uuid")
//                        surveyQuestionAnswer.description = survey_details?.optString("description")
//                        surveyQuestionAnswer.main_title = survey_details?.optString("title")
//                        val jsonObject: JSONObject = survey_question_answers.get(key) as JSONObject
//                        surveyQuestionAnswer.surveyQuestionUuid =
//                            jsonObject.optString("survey_question_uuid")
//                        surveyQuestionAnswer.surveyUuid = jsonObject.optString("survey_uuid")
//                        surveyQuestionAnswer.survey_answer_value =
//                            jsonObject.optString("survey_answer_value")
//                        arr.add(surveyQuestionAnswer)
//                    }
//                }
                survey_details?.let { survey_details ->
                    val survey_question = survey_details?.optJSONArray("survey_question")
                    for (i in 0 until survey_question?.length()!!) {
                        val ques = SurveyQuestionAnswer()
                        val jobj = survey_question.getJSONObject(i)
                        ques.title = jobj?.optString("title")
                        ques.required = jobj?.optBoolean("required")
                        ques.weight = jobj?.optInt("weight")
                        ques.options = jobj?.optJSONArray("options")
                        ques.bundle = jobj?.optString("bundle")
                        ques.surveyQuestionUuid = jobj?.optString("uuid")
                        arr.add(ques)
                    }

                        for (surveyQuestionAnswer in arr) {
                            val survey_question_answers =
                                subJsonObject?.optJSONObject("survey_question_answers")
                            survey_question_answers?.let {
                                val x = survey_question_answers?.keys() as Iterator<String>
                                while (x.hasNext()) {
                                    val key: String = x.next()
                                    if (surveyQuestionAnswer.surveyQuestionUuid == key) {
                                        val jsonObject: JSONObject =
                                            survey_question_answers.get(key) as JSONObject
                                        surveyQuestionAnswer.surveyQuestionUuid =
                                            jsonObject.optString("survey_question_uuid")
                                        surveyQuestionAnswer.surveyUuid =
                                            jsonObject.optString("survey_uuid")
                                        surveyQuestionAnswer.survey_answer_value =
                                            jsonObject.optString("survey_answer_value")
                                        surveyQuestionAnswer.responseStatus =
                                            subJsonObject?.optString("response_status")
                                        surveyQuestionAnswer.uuid =
                                            survey_details?.optString("uuid")
                                        surveyQuestionAnswer.description =
                                            survey_details?.optString("description")
                                        surveyQuestionAnswer.main_title =
                                            survey_details?.optString("title")
                                      //  arr.add(surveyQuestionAnswer)
                                    }
                                }
                            }
                        }
                }

            }

        }
        return arr
    }


}
