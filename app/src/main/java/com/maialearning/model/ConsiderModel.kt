package com.maialearning.model

import com.google.gson.annotations.SerializedName

class ConsiderModel {
    data class Data(
        @SerializedName("contact_info")
        var contactInfo: Int,
        @SerializedName("parchment")
        var parchment: Int,
        @SerializedName("slate")
        var slate: Int,
        @SerializedName("transcript_nid")
        var transcriptNid: Int,
        @SerializedName("university_nid")
        var universityNid: String,
        @SerializedName("name")
        var naviance_college_name: String,
        @SerializedName("country")
        var country: String,
        @SerializedName("country_name")
        var country_name: String,
        @SerializedName("created_by_name")
        var created_name: String,
        @SerializedName("created_date")
        var created_date: String,
        @SerializedName("college_priority_choice")
        var college_priority_choice: String,
        @SerializedName("university_nid")
        var university_nid: String,
        @SerializedName("unitid")
        var unitid: String,
        @SerializedName("internal_deadline")
        var internal_deadline: String?,
        @SerializedName("app_by_program_data")
        var program_data: ArrayList<ProgramData>?,
        @SerializedName("count")
        var count: Int,
        @SerializedName("notes")
        var notes: String,
        @SerializedName("counselor_notes")
        var counselorNotes: ArrayList<CounselorNotes>?,
        @SerializedName("request_transcript")
        var requestTranscript: String,
        @SerializedName("application_type")
        var applicationType: String,
        @SerializedName("application_mode")
        var applicationMode: String
    )

    data class ProgramData(
        @SerializedName("program_id")
        var program_id: Int,
        @SerializedName("program_name")
        var program_name: String,
        @SerializedName("program_deadline")
        var program_deadline: String,
        @SerializedName("program_status")
        var program_status: String,
    )

    data class CounselorNotes(
        @SerializedName("id")
        var id: String,
        @SerializedName("uid")
        var uid: String,
        @SerializedName("counselor_note")
        var counselorNote: String,
        @SerializedName("first_name")
        var firstName: String,
        @SerializedName("last_name")
        var lastName: String
    )
}

