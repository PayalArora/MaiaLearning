package com.maialearning.model

data class RecModel(val notes: String,val student_id: String ,val due_date: String,val teacher_id:ArrayList<String>, val college_id:ArrayList<String>?=null)
