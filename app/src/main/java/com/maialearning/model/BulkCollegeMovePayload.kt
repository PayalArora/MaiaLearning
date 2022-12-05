package com.maialearning.model

class BulkCollegeMovePayload {
    lateinit var student_uid: String
    lateinit var status: String
    var transcript_nids: ArrayList<String>? = null
}