package com.maialearning.model

class PrefferedRecoSaveModel {
    lateinit var preferred_recommender_data:ArrayList<PrefferedRecoSave>
    lateinit var school_nid: String
    lateinit var student_uid:String
    data class PrefferedRecoSave(
        val college_nid: String,
        val recommender_uid: String, val set: String
    )
}