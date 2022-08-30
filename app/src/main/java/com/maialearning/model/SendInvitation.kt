package com.maialearning.model

class SendInvitation {
    lateinit var notes: String
    lateinit var student_id: String
    lateinit var due_date: String
    var teacher_id: ArrayList<String> = ArrayList()
}