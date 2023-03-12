package com.maialearning.model

data class UpdateSurveyAnswerReq( val survey: String,// survey_uuid
                                  val survey_answer_value: String, // encoded value
                                  val survey_question: String)
