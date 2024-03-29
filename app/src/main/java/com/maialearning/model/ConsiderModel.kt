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
        @SerializedName("due_date")
        var dueDate: String?,
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
        var applicationType: String? = null,
        @SerializedName("application_term")
        var applicationTerm: String?,
        @SerializedName("application_mode")
        var applicationMode: String?,
        @SerializedName("application_status_name")
        var applicationStatusName: String,
        @SerializedName("app_by_program_supported")
        var appByProgramSupported: String,
        @SerializedName("confirm_applied")
        var confirmApplied: Int,
        var collegeAppLicationType: CollType?,
        @SerializedName("required_recommendation")
        var requiredRecommendation: RequiredRecommendation?,
        @SerializedName("manual_update")
        var manualUpdate: Int,
        @SerializedName("application_round")
        var applicationRound: String?,
        @SerializedName("application_round_detail")
        var applicationRoundDetail: ArrayList<ApplicationRoundDetail>?,
        @SerializedName("status")
        var status: String?,
        @SerializedName("isCommonApp")
        var commonApp: Boolean,
        @SerializedName("naviance_mapping")
        var navianceMapping: String? = null,
        @SerializedName("college_compare")
        var collegeCompare: CollegeCompare?,
        var selected: Boolean = false,
        var selectedAppModeValue: String? = null,
        var selectedAppPlanValue: String? = null,
        var header: String = "",
    )

    data class RequiredRecommendation(
        @SerializedName("teacher_evaluation")
        var teacherEvaluation: String?,
        @SerializedName("max_teacher_evaluation")
        var maxTeacherEvaluation: String?,
        @SerializedName("counselor_recommendation")
        var counselorRecommendation: String?
    )

    data class CollegeCompare(

        @SerializedName("college")
        var college: String?,
        @SerializedName("gpa")
        var gpa: String?,
        @SerializedName("sat_1600")
        var sat1600: String?,
        @SerializedName("sat_2400")
        var sat2400: String?,
        @SerializedName("act")
        var act: String?,
        @SerializedName("sat_points")
        var satPoints: String?,
        @SerializedName("act_points")
        var actPoints: String?

    )

    data class CollType(var collType: ArrayList<DynamicKeyValue>?, var selectedPlanType: String?)

    class CollTerm {
        var type: String? = null
        var termList: ArrayList<String>? = null
        var planList: ArrayList<DecisionPlan>? = null
        var collTerm: ArrayList<CollPlan>? = null

    }

    class CollPlan {
        lateinit var plan: String
        var collPlan: ArrayList<Decision>? = null
    }

    data class Decision(
        var decision_plan: String,
        var decision_plan_value: String,
        var deadline_date: String?
    )

    data class DecisionPlan(
        var id: String,
        var label: String
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
        @SerializedName("app_status_val")
        var app_status_val: String? = null
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


    data class ApplicationRoundDetail(

        @SerializedName("id")
        var id: String,

        @SerializedName("tr_nid")
        var trNid: String,

        @SerializedName("college_id")
        var collegeId: String,

        @SerializedName("student_uid")
        var studentUid: String,

        @SerializedName("author_uid")
        var authorUid: String? = null,

        @SerializedName("plan")
        var plan: String,

        @SerializedName("deadline")
        var deadline: String,

        @SerializedName("internal_deadline")
        var internalDeadline: String? = null,

        @SerializedName("app_submitted")
        var appSubmitted: String,

        @SerializedName("app_status")
        var appStatus: String,

        @SerializedName("created")
        var created: String? = null,

        @SerializedName("app_type")
        var appType: String,

        @SerializedName("term")
        var term: String,

        @SerializedName("round_number")
        var roundNumber: String? = null
    )

}

