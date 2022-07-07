package com.maialearning.model


import com.google.gson.annotations.SerializedName

data class Consider(
    @SerializedName("9375")
    var x9375: X9375
) {
    data class X9375(
        @SerializedName("data")
        var `data`: Data,
        @SerializedName("ncaa")
        var ncaa: String
    ) {
        data class Data(
            @SerializedName("176773")
            var x176773: X176773,
            @SerializedName("176789")
            var x176789: X176789,
            @SerializedName("175671")
            var x175671: X175671,
            @SerializedName("197817")
            var x197817: X197817,
            @SerializedName("177070")
            var x177070: X177070,
            @SerializedName("178083")
            var x178083: X178083,
            @SerializedName("179934")
            var x179934: X179934,
            @SerializedName("180200")
            var x180200: X180200,
            @SerializedName("181096")
            var x181096: X181096,
            @SerializedName("182472")
            var x182472: X182472,
            @SerializedName("581930")
            var x581930: X581930,
            @SerializedName("581931")
            var x581931: X581931,
            @SerializedName("581932")
            var x581932: X581932,
            @SerializedName("581933")
            var x581933: X581933,
            @SerializedName("181262")
            var x181262: X181262,
            @SerializedName("177156")
            var x177156: X177156,
            @SerializedName("464402")
            var x464402: X464402,
            @SerializedName("1308620")
            var x1308620: X1308620,
            @SerializedName("479948")
            var x479948: X479948
        ) {
            data class X176773(
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
                @SerializedName("application_term")
                var applicationTerm: String,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: String,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: String,
                @SerializedName("member_id")
                var memberId: String,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<AppByProgramData>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: String,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: String,
                @SerializedName("due_date")
                var dueDate: String,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: String,
                @SerializedName("major_name")
                var majorName: String,
                @SerializedName("confirm_applied")
                var confirmApplied: String,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: String,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: String,
                @SerializedName("application_status_name")
                var applicationStatusName: String,
                @SerializedName("school_university")
                var schoolUniversity: String,
                @SerializedName("campus_tour_value")
                var campusTourValue: String,
                @SerializedName("mid_november")
                var midNovember: String,
                @SerializedName("college_interest_value")
                var collegeInterestValue: String,
                @SerializedName("college_interest_name")
                var collegeInterestName: String,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: String,
                @SerializedName("interview_interest_name")
                var interviewInterestName: String,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: String,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: String,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: String
                )

                data class AppByProgramData(
                    @SerializedName("program_id")
                    var programId: String,
                    @SerializedName("program_name")
                    var programName: String,
                    @SerializedName("program_deadline")
                    var programDeadline: Any,
                    @SerializedName("program_status")
                    var programStatus: Any
                )
            }

            data class X176789(
                @SerializedName("contact_info")
                var contactInfo: Int,
                @SerializedName("parchment")
                var parchment: String,
                @SerializedName("slate")
                var slate: Int,
                @SerializedName("transcript_nid")
                var transcriptNid: Int,
                @SerializedName("university_nid")
                var universityNid: String,
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: Any,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: String,
                @SerializedName("member_id")
                var memberId: String,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<Any>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: Any,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: Int,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: Any,
                @SerializedName("major_name")
                var majorName: Any,
                @SerializedName("confirm_applied")
                var confirmApplied: Int,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: String,
                @SerializedName("application_status_name")
                var applicationStatusName: String,
                @SerializedName("school_university")
                var schoolUniversity: Any,
                @SerializedName("campus_tour_value")
                var campusTourValue: Any,
                @SerializedName("mid_november")
                var midNovember: Any,
                @SerializedName("college_interest_value")
                var collegeInterestValue: Any,
                @SerializedName("college_interest_name")
                var collegeInterestName: Any,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: Any,
                @SerializedName("interview_interest_name")
                var interviewInterestName: Any,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: String,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: String,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: String
                )
            }

            data class X175671(
                @SerializedName("contact_info")
                var contactInfo: Int,
                @SerializedName("parchment")
                var parchment: String,
                @SerializedName("slate")
                var slate: Int,
                @SerializedName("transcript_nid")
                var transcriptNid: Int,
                @SerializedName("university_nid")
                var universityNid: String,
                @SerializedName("application_term")
                var applicationTerm: String,
                @SerializedName("internal_deadline")
                var internalDeadline: String,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: Any,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: String,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: String,
                @SerializedName("member_id")
                var memberId: String,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<Any>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: Any,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: String,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: Any,
                @SerializedName("major_name")
                var majorName: Any,
                @SerializedName("confirm_applied")
                var confirmApplied: Int,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: Any,
                @SerializedName("application_status_name")
                var applicationStatusName: Any,
                @SerializedName("school_university")
                var schoolUniversity: Any,
                @SerializedName("campus_tour_value")
                var campusTourValue: String,
                @SerializedName("mid_november")
                var midNovember: String,
                @SerializedName("college_interest_value")
                var collegeInterestValue: String,
                @SerializedName("college_interest_name")
                var collegeInterestName: String,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: String,
                @SerializedName("interview_interest_name")
                var interviewInterestName: String,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: String,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: String,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: String
                )
            }

            data class X197817(
                @SerializedName("contact_info")
                var contactInfo: Int,
                @SerializedName("parchment")
                var parchment: String,
                @SerializedName("slate")
                var slate: Int,
                @SerializedName("transcript_nid")
                var transcriptNid: Int,
                @SerializedName("university_nid")
                var universityNid: String,
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: String,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: String,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: String,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<Any>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: String,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: String,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: Any,
                @SerializedName("major_name")
                var majorName: Any,
                @SerializedName("confirm_applied")
                var confirmApplied: String,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: Any,
                @SerializedName("application_status_name")
                var applicationStatusName: Any,
                @SerializedName("school_university")
                var schoolUniversity: Any,
                @SerializedName("campus_tour_value")
                var campusTourValue: String,
                @SerializedName("mid_november")
                var midNovember: String,
                @SerializedName("college_interest_value")
                var collegeInterestValue: Any,
                @SerializedName("college_interest_name")
                var collegeInterestName: Any,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: Any,
                @SerializedName("interview_interest_name")
                var interviewInterestName: Any,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )
            }

            data class X177070(
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
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: Any,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: String,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<AppByProgramData>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: String,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: String,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: String,
                @SerializedName("major_name")
                var majorName: String,
                @SerializedName("confirm_applied")
                var confirmApplied: Int,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: String,
                @SerializedName("application_status_name")
                var applicationStatusName: String,
                @SerializedName("school_university")
                var schoolUniversity: String,
                @SerializedName("campus_tour_value")
                var campusTourValue: String,
                @SerializedName("mid_november")
                var midNovember: String,
                @SerializedName("college_interest_value")
                var collegeInterestValue: String,
                @SerializedName("college_interest_name")
                var collegeInterestName: String,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: String,
                @SerializedName("interview_interest_name")
                var interviewInterestName: String,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )

                data class AppByProgramData(
                    @SerializedName("program_id")
                    var programId: String,
                    @SerializedName("program_name")
                    var programName: String,
                    @SerializedName("program_deadline")
                    var programDeadline: Any,
                    @SerializedName("program_status")
                    var programStatus: String
                )
            }

            data class X178083(
                @SerializedName("contact_info")
                var contactInfo: Int,
                @SerializedName("parchment")
                var parchment: String,
                @SerializedName("slate")
                var slate: Int,
                @SerializedName("transcript_nid")
                var transcriptNid: Int,
                @SerializedName("university_nid")
                var universityNid: String,
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: Any,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: String,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: String,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<AppByProgramData>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: Any,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: String,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: String,
                @SerializedName("major_name")
                var majorName: String,
                @SerializedName("confirm_applied")
                var confirmApplied: String,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: Any,
                @SerializedName("application_status_name")
                var applicationStatusName: Any,
                @SerializedName("school_university")
                var schoolUniversity: String,
                @SerializedName("campus_tour_value")
                var campusTourValue: String,
                @SerializedName("mid_november")
                var midNovember: String,
                @SerializedName("college_interest_value")
                var collegeInterestValue: String,
                @SerializedName("college_interest_name")
                var collegeInterestName: String,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: String,
                @SerializedName("interview_interest_name")
                var interviewInterestName: String,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )

                data class AppByProgramData(
                    @SerializedName("program_id")
                    var programId: String,
                    @SerializedName("program_name")
                    var programName: String,
                    @SerializedName("program_deadline")
                    var programDeadline: Any,
                    @SerializedName("program_status")
                    var programStatus: Any
                )
            }

            data class X179934(
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
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: Any,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: String,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<Any>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: String,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: Int,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: Any,
                @SerializedName("major_name")
                var majorName: Any,
                @SerializedName("confirm_applied")
                var confirmApplied: String,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: String,
                @SerializedName("application_status_name")
                var applicationStatusName: String,
                @SerializedName("school_university")
                var schoolUniversity: Any,
                @SerializedName("campus_tour_value")
                var campusTourValue: Any,
                @SerializedName("mid_november")
                var midNovember: Any,
                @SerializedName("college_interest_value")
                var collegeInterestValue: Any,
                @SerializedName("college_interest_name")
                var collegeInterestName: Any,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: Any,
                @SerializedName("interview_interest_name")
                var interviewInterestName: Any,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int,
                @SerializedName("flagging_data")
                var flaggingData: List<String>
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )
            }

            data class X180200(
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
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: Any,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: String,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<Any>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: String,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: Int,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: Any,
                @SerializedName("major_name")
                var majorName: Any,
                @SerializedName("confirm_applied")
                var confirmApplied: String,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: String,
                @SerializedName("application_status_name")
                var applicationStatusName: String,
                @SerializedName("school_university")
                var schoolUniversity: Any,
                @SerializedName("campus_tour_value")
                var campusTourValue: Any,
                @SerializedName("mid_november")
                var midNovember: Any,
                @SerializedName("college_interest_value")
                var collegeInterestValue: Any,
                @SerializedName("college_interest_name")
                var collegeInterestName: Any,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: Any,
                @SerializedName("interview_interest_name")
                var interviewInterestName: Any,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int,
                @SerializedName("flagging_data")
                var flaggingData: List<String>
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )
            }

            data class X181096(
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
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: Any,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: String,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<Any>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: String,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: Int,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: Any,
                @SerializedName("major_name")
                var majorName: Any,
                @SerializedName("confirm_applied")
                var confirmApplied: String,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: String,
                @SerializedName("application_status_name")
                var applicationStatusName: String,
                @SerializedName("school_university")
                var schoolUniversity: Any,
                @SerializedName("campus_tour_value")
                var campusTourValue: Any,
                @SerializedName("mid_november")
                var midNovember: Any,
                @SerializedName("college_interest_value")
                var collegeInterestValue: Any,
                @SerializedName("college_interest_name")
                var collegeInterestName: Any,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: Any,
                @SerializedName("interview_interest_name")
                var interviewInterestName: Any,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int,
                @SerializedName("flagging_data")
                var flaggingData: List<String>
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )
            }

            data class X182472(
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
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: Any,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: String,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<Any>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: String,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: Int,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: Any,
                @SerializedName("major_name")
                var majorName: Any,
                @SerializedName("confirm_applied")
                var confirmApplied: String,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: String,
                @SerializedName("application_status_name")
                var applicationStatusName: String,
                @SerializedName("school_university")
                var schoolUniversity: Any,
                @SerializedName("campus_tour_value")
                var campusTourValue: Any,
                @SerializedName("mid_november")
                var midNovember: Any,
                @SerializedName("college_interest_value")
                var collegeInterestValue: Any,
                @SerializedName("college_interest_name")
                var collegeInterestName: Any,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: Any,
                @SerializedName("interview_interest_name")
                var interviewInterestName: Any,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int,
                @SerializedName("flagging_data")
                var flaggingData: List<String>
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )
            }

            data class X581930(
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
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: Any,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: Any,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<Any>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: String,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: Int,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: Any,
                @SerializedName("major_name")
                var majorName: Any,
                @SerializedName("confirm_applied")
                var confirmApplied: String,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: String,
                @SerializedName("application_status_name")
                var applicationStatusName: String,
                @SerializedName("school_university")
                var schoolUniversity: Any,
                @SerializedName("campus_tour_value")
                var campusTourValue: Any,
                @SerializedName("mid_november")
                var midNovember: Any,
                @SerializedName("college_interest_value")
                var collegeInterestValue: Any,
                @SerializedName("college_interest_name")
                var collegeInterestName: Any,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: Any,
                @SerializedName("interview_interest_name")
                var interviewInterestName: Any,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int,
                @SerializedName("flagging_data")
                var flaggingData: List<String>
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )
            }

            data class X581931(
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
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: String,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: Any,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<Any>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: Any,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: String,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: Any,
                @SerializedName("major_name")
                var majorName: Any,
                @SerializedName("confirm_applied")
                var confirmApplied: String,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: String,
                @SerializedName("application_status_name")
                var applicationStatusName: String,
                @SerializedName("school_university")
                var schoolUniversity: Any,
                @SerializedName("campus_tour_value")
                var campusTourValue: Any,
                @SerializedName("mid_november")
                var midNovember: Any,
                @SerializedName("college_interest_value")
                var collegeInterestValue: Any,
                @SerializedName("college_interest_name")
                var collegeInterestName: Any,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: Any,
                @SerializedName("interview_interest_name")
                var interviewInterestName: Any,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int,
                @SerializedName("flagging_data")
                var flaggingData: List<String>
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )
            }

            data class X581932(
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
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: String,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: Any,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<Any>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: Any,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: String,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: Any,
                @SerializedName("major_name")
                var majorName: Any,
                @SerializedName("confirm_applied")
                var confirmApplied: String,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: String,
                @SerializedName("application_status_name")
                var applicationStatusName: String,
                @SerializedName("school_university")
                var schoolUniversity: Any,
                @SerializedName("campus_tour_value")
                var campusTourValue: Any,
                @SerializedName("mid_november")
                var midNovember: Any,
                @SerializedName("college_interest_value")
                var collegeInterestValue: Any,
                @SerializedName("college_interest_name")
                var collegeInterestName: Any,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: Any,
                @SerializedName("interview_interest_name")
                var interviewInterestName: Any,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int,
                @SerializedName("flagging_data")
                var flaggingData: List<String>
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )
            }

            data class X581933(
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
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: Any,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: Any,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<Any>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: String,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: Int,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: Any,
                @SerializedName("major_name")
                var majorName: Any,
                @SerializedName("confirm_applied")
                var confirmApplied: String,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: String,
                @SerializedName("application_status_name")
                var applicationStatusName: String,
                @SerializedName("school_university")
                var schoolUniversity: Any,
                @SerializedName("campus_tour_value")
                var campusTourValue: Any,
                @SerializedName("mid_november")
                var midNovember: Any,
                @SerializedName("college_interest_value")
                var collegeInterestValue: Any,
                @SerializedName("college_interest_name")
                var collegeInterestName: Any,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: Any,
                @SerializedName("interview_interest_name")
                var interviewInterestName: Any,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int,
                @SerializedName("flagging_data")
                var flaggingData: List<String>
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )
            }

            data class X181262(
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
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: Any,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: String,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<AppByProgramData>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: Any,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: Any,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: Int,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: String,
                @SerializedName("major_name")
                var majorName: String,
                @SerializedName("confirm_applied")
                var confirmApplied: String,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: String,
                @SerializedName("application_status_name")
                var applicationStatusName: String,
                @SerializedName("school_university")
                var schoolUniversity: String,
                @SerializedName("campus_tour_value")
                var campusTourValue: String,
                @SerializedName("mid_november")
                var midNovember: String,
                @SerializedName("college_interest_value")
                var collegeInterestValue: String,
                @SerializedName("college_interest_name")
                var collegeInterestName: String,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: String,
                @SerializedName("interview_interest_name")
                var interviewInterestName: String,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )

                data class AppByProgramData(
                    @SerializedName("program_id")
                    var programId: String,
                    @SerializedName("program_name")
                    var programName: String,
                    @SerializedName("program_deadline")
                    var programDeadline: Any,
                    @SerializedName("program_status")
                    var programStatus: String
                )
            }

            data class X177156(
                @SerializedName("contact_info")
                var contactInfo: Int,
                @SerializedName("parchment")
                var parchment: String,
                @SerializedName("slate")
                var slate: Int,
                @SerializedName("transcript_nid")
                var transcriptNid: Int,
                @SerializedName("university_nid")
                var universityNid: String,
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: String,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: String,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: String,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<AppByProgramData>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: Any,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: String,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: String,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: String,
                @SerializedName("due_date")
                var dueDate: String,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: String,
                @SerializedName("major_name")
                var majorName: String,
                @SerializedName("confirm_applied")
                var confirmApplied: String,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: String,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: Any,
                @SerializedName("application_status_name")
                var applicationStatusName: Any,
                @SerializedName("school_university")
                var schoolUniversity: String,
                @SerializedName("campus_tour_value")
                var campusTourValue: String,
                @SerializedName("mid_november")
                var midNovember: String,
                @SerializedName("college_interest_value")
                var collegeInterestValue: String,
                @SerializedName("college_interest_name")
                var collegeInterestName: String,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: String,
                @SerializedName("interview_interest_name")
                var interviewInterestName: String,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )

                data class AppByProgramData(
                    @SerializedName("program_id")
                    var programId: String,
                    @SerializedName("program_name")
                    var programName: String,
                    @SerializedName("program_deadline")
                    var programDeadline: Any,
                    @SerializedName("program_status")
                    var programStatus: Any
                )
            }

            data class X464402(
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
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: Any,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: Any,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<Any>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: String,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: Any,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: Any,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: String,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: Any,
                @SerializedName("major_name")
                var majorName: Any,
                @SerializedName("confirm_applied")
                var confirmApplied: String,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: Any,
                @SerializedName("application_status_name")
                var applicationStatusName: Any,
                @SerializedName("school_university")
                var schoolUniversity: Any,
                @SerializedName("campus_tour_value")
                var campusTourValue: Any,
                @SerializedName("mid_november")
                var midNovember: Any,
                @SerializedName("college_interest_value")
                var collegeInterestValue: Any,
                @SerializedName("college_interest_name")
                var collegeInterestName: Any,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: Any,
                @SerializedName("interview_interest_name")
                var interviewInterestName: Any,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )
            }

            data class X1308620(
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
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: String,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: String,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: Any,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<AppByProgramData>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: String,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: Any,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: Any,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: String,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: Any,
                @SerializedName("major_name")
                var majorName: Any,
                @SerializedName("confirm_applied")
                var confirmApplied: Int,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: String,
                @SerializedName("application_status_name")
                var applicationStatusName: String,
                @SerializedName("school_university")
                var schoolUniversity: Any,
                @SerializedName("campus_tour_value")
                var campusTourValue: Any,
                @SerializedName("mid_november")
                var midNovember: Any,
                @SerializedName("college_interest_value")
                var collegeInterestValue: Any,
                @SerializedName("college_interest_name")
                var collegeInterestName: Any,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: Any,
                @SerializedName("interview_interest_name")
                var interviewInterestName: Any,
                @SerializedName("counselor_notes")
                var counselorNotes: CounselorNotes,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )

                data class AppByProgramData(
                    @SerializedName("program_id")
                    var programId: String,
                    @SerializedName("program_name")
                    var programName: String,
                    @SerializedName("program_deadline")
                    var programDeadline: String,
                    @SerializedName("program_status")
                    var programStatus: String
                )

                data class CounselorNotes(
                    @SerializedName("9477")
                    var x9477: X9477
                ) {
                    data class X9477(
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
            }

            data class X479948(
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
                @SerializedName("application_term")
                var applicationTerm: Any,
                @SerializedName("internal_deadline")
                var internalDeadline: Any,
                @SerializedName("required_recommendation")
                var requiredRecommendation: RequiredRecommendation,
                @SerializedName("transcript_chances")
                var transcriptChances: Any,
                @SerializedName("custom_rating")
                var customRating: Any,
                @SerializedName("created_by_name")
                var createdByName: String,
                @SerializedName("created_date")
                var createdDate: String,
                @SerializedName("college_priority_choice")
                var collegePriorityChoice: Any,
                @SerializedName("name")
                var name: String,
                @SerializedName("country")
                var country: String,
                @SerializedName("country_name")
                var countryName: String,
                @SerializedName("city_state")
                var cityState: Any,
                @SerializedName("member_id")
                var memberId: Any,
                @SerializedName("commom_app")
                var commomApp: Int,
                @SerializedName("collegeRecords")
                var collegeRecords: Any,
                @SerializedName("app_by_program_supported")
                var appByProgramSupported: Int,
                @SerializedName("app_by_program_data")
                var appByProgramData: List<AppByProgramData>,
                @SerializedName("supplement_submitted")
                var supplementSubmitted: String,
                @SerializedName("university_considered")
                var universityConsidered: Any,
                @SerializedName("request_transcript")
                var requestTranscript: String,
                @SerializedName("unitid")
                var unitid: Any,
                @SerializedName("application_mode")
                var applicationMode: String,
                @SerializedName("rating")
                var rating: Any,
                @SerializedName("applying")
                var applying: String,
                @SerializedName("application_type")
                var applicationType: Any,
                @SerializedName("isCommonApp")
                var isCommonApp: Boolean,
                @SerializedName("manual_update")
                var manualUpdate: String,
                @SerializedName("due_date")
                var dueDate: Any,
                @SerializedName("application_round")
                var applicationRound: Int,
                @SerializedName("application_round_detail")
                var applicationRoundDetail: List<Any>,
                @SerializedName("application_mode_requirement")
                var applicationModeRequirement: Boolean,
                @SerializedName("status")
                var status: String,
                @SerializedName("program_name")
                var programName: Any,
                @SerializedName("major_name")
                var majorName: Any,
                @SerializedName("confirm_applied")
                var confirmApplied: Int,
                @SerializedName("confirm_applied_time")
                var confirmAppliedTime: Any,
                @SerializedName("notes")
                var notes: Any,
                @SerializedName("application_status")
                var applicationStatus: String,
                @SerializedName("application_status_name")
                var applicationStatusName: String,
                @SerializedName("school_university")
                var schoolUniversity: Any,
                @SerializedName("campus_tour_value")
                var campusTourValue: Any,
                @SerializedName("mid_november")
                var midNovember: Any,
                @SerializedName("college_interest_value")
                var collegeInterestValue: Any,
                @SerializedName("college_interest_name")
                var collegeInterestName: Any,
                @SerializedName("interview_interest_value")
                var interviewInterestValue: Any,
                @SerializedName("interview_interest_name")
                var interviewInterestName: Any,
                @SerializedName("counselor_notes")
                var counselorNotes: List<Any>,
                @SerializedName("college_compare")
                var collegeCompare: List<Any>,
                @SerializedName("naviance_mapping")
                var navianceMapping: Int,
                @SerializedName("naviance_college_name")
                var navianceCollegeName: String,
                @SerializedName("compare_me_default_year")
                var compareMeDefaultYear: String,
                @SerializedName("max_class_of")
                var maxClassOf: Int,
                @SerializedName("min_class_of")
                var minClassOf: Int
            ) {
                data class RequiredRecommendation(
                    @SerializedName("teacher_evaluation")
                    var teacherEvaluation: Any,
                    @SerializedName("max_teacher_evaluation")
                    var maxTeacherEvaluation: Any,
                    @SerializedName("counselor_recommendation")
                    var counselorRecommendation: Any
                )

                data class AppByProgramData(
                    @SerializedName("program_id")
                    var programId: String,
                    @SerializedName("program_name")
                    var programName: String,
                    @SerializedName("program_deadline")
                    var programDeadline: String,
                    @SerializedName("program_status")
                    var programStatus: String
                )
            }
        }
    }
}