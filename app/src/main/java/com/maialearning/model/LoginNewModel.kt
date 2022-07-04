package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class LoginNewModel(
    @SerializedName("access_token") var accessToken: String,
    @SerializedName("refresh_token") var refreshToken: String,
    @SerializedName("user") var user: User,
    @SerializedName("essay") var essay: Essay,
    @SerializedName("userName") var userName: UserName,
    @SerializedName("enabled_2fa") var enabled2fa: Int,
    @SerializedName("ccgi_apps_permission") var ccgiAppsPermission: CcgiAppsPermission,
    @SerializedName("school_type") var schoolType: String,
    @SerializedName("iec_school") var iecSchool: Int,
    @SerializedName("school_level") var schoolLevel: String,
    @SerializedName("student_upload_pic") var studentUploadPic: String,
    @SerializedName("district_level_activities") var districtLevelActivities: String,
    @SerializedName("school_starting_month") var schoolStartingMonth: String,
    @SerializedName("student_default_page") var studentDefaultPage: String,
    @SerializedName("school_timezone_offset") var schoolTimezoneOffset: String,
    @SerializedName("school_uuid") var schoolUuid: String,
    @SerializedName("ap_completed_courses") var apCompletedCourses: String,
    @SerializedName("ib_completed_courses") var ibCompletedCourses: String,
    @SerializedName("compare_me_default_year") var compareMeDefaultYear: List<String>,
    @SerializedName("resume_flag") var resumeFlag: String,
    @SerializedName("teacher_evaluation_rating_config") var teacherEvaluationRatingConfig: String,
    @SerializedName("compare_me_class_of_range") var compareMeClassOfRange: CompareMeClassOfRange,
//    @SerializedName("gauges_permission_config") var gaugesPermissionConfig: GaugesPermissionConfig,
    @SerializedName("default_class_of") var defaultClassOf: String,
    @SerializedName("default_caseload_id") var defaultCaseloadId: String,
    @SerializedName("school_timezone_abbr") var schoolTimezoneAbbr: String,
    @SerializedName("skills_assessment_config") var skillsAssessmentConfig: String,
    @SerializedName("scattergram_gpa_value") var scattergramGpaValue: String,
    @SerializedName("compare_me_config") var compareMeConfig: String,
    @SerializedName("hide_stu_assign_from_edu") var hideStuAssignFromEdu: String,
    @SerializedName("scattergram_x_y_values") var scattergramXYValues: ScattergramXYValues,
    @SerializedName("recommendation_mail") var recommendationMail: String,
    @SerializedName("ca_sync_cc") var caSyncCc: String,
    @SerializedName("move_to_applying") var moveToApplying: String,
    @SerializedName("field_hide_data_sent_report") var fieldHideDataSentReport: String,
    @SerializedName("disabling_doc_download") var disablingDocDownload: String,
    @SerializedName("parchment_send_ca") var parchmentSendCa: String,
    @SerializedName("common_app_send") var commonAppSend: String,
    @SerializedName("school_shortcut") var schoolShortcut: SchoolShortcut,
    @SerializedName("assessment_permission") var assessmentPermission: AssessmentPermission,
    @SerializedName("grade_based_college_plan") var gradeBasedCollegePlan: GradeBasedCollegePlan,
    @SerializedName("grade_based_college_visit") var gradeBasedCollegeVisit: GradeBasedCollegeVisit,
    @SerializedName("student_dob_show") var studentDobShow: String,
    @SerializedName("show_reco_student_college_data") var showRecoStudentCollegeData: String,
    @SerializedName("switch_role") var switchRole: String,
    @SerializedName("maia_design_version") var maiaDesignVersion: String,
    @SerializedName("field_classroom_enabled") var fieldClassroomEnabled: String,
    @SerializedName("lesson_plan_publisher") var lessonPlanPublisher: Int,
    @SerializedName("field_brag_sheet_config") var fieldBragSheetConfig: Int,
    @SerializedName("wwa") var wwa: Wwa,
    @SerializedName("ml_school_config_data") var mlSchoolConfigData: MlSchoolConfigData,
    @SerializedName("ml_user_default_config_data") var mlUserDefaultConfigData: MlUserDefaultConfigData,
    @SerializedName("is_sel_content_school") var isSelContentSchool: Int,
    @SerializedName("current_common_app_application_year") var currentCommonAppApplicationYear: String,
    @SerializedName("district_slug") var districtSlug: String,
    @SerializedName("curriculum_permission") var curriculumPermission: CurriculumPermission,
    @SerializedName("menu_permissions") var menuPermissions: MenuPermissions,
    @SerializedName("preferred_langcode") var preferredLangcode: String

){
    data class User (

        @SerializedName("uid") var uid : String,
        @SerializedName("name") var name : String,
        @SerializedName("theme") var theme : String,
        @SerializedName("signature") var signature : String,
        @SerializedName("signature_format") var signatureFormat : String,
        @SerializedName("created") var created : String,
        @SerializedName("access") var access : String,
        @SerializedName("login") var login : Int,
        @SerializedName("status") var status : String,
        @SerializedName("timezone") var timezone : String,
        @SerializedName("language") var language : String,
        @SerializedName("picture") var picture : String,
        @SerializedName("data") var data : Data,
        @SerializedName("uuid") var uuid : String,
        @SerializedName("roles") var roles : Roles,
        @SerializedName("og_user_node") var ogUserNode : OgUserNode,
        @SerializedName("field_sign_up") var fieldSignUp : FieldSignUp,
        @SerializedName("field_login_count") var fieldLoginCount : List<String>,
        @SerializedName("field_google_login_id") var fieldGoogleLoginId : List<String>,
        @SerializedName("field_email_not_provide") var fieldEmailNotProvide : FieldEmailNotProvide,
        @SerializedName("grade") var grade : String,
        @SerializedName("message_id") var messageId : String

    ){

        data class Data (

            @SerializedName("mimemail_textonly") var mimemailTextonly : Int,
            @SerializedName("ckeditor_default") var ckeditorDefault : String,
            @SerializedName("ckeditor_show_toggle") var ckeditorShowToggle : String,
            @SerializedName("ckeditor_width") var ckeditorWidth : String,
            @SerializedName("ckeditor_lang") var ckeditorLang : String,
            @SerializedName("ckeditor_auto_lang") var ckeditorAutoLang : String

        )
        data class Roles (

            @SerializedName("2") var two : String,
            @SerializedName("5") var five : String

        )
        data class OgUserNode (

            @SerializedName("und") var und : List<Und>

        )
        data class FieldSignUp (

            @SerializedName("und") var und : List<Und>

        )
        data class FieldEmailNotProvide (

            @SerializedName("und") var und : List<Und>

        )
        data class Und (

            @SerializedName("value") var value : String

        )
    }
    data class Essay (

        @SerializedName("list_type") var listType : String

    )
    data class UserName (

        @SerializedName("first_name") var firstName : String,
        @SerializedName("last_name") var lastName : String

    )
    data class CcgiAppsPermission (

        @SerializedName("csu") var csu : Int,
        @SerializedName("uc") var uc : Int,
        @SerializedName("mi") var mi : Int,
        @SerializedName("lsi") var lsi : Int,
        @SerializedName("dwya") var dwya : Int,
        @SerializedName("sms") var sms : Int,
        @SerializedName("survey_school") var surveySchool : Int,
        @SerializedName("scheduler") var scheduler : Int,
        @SerializedName("message_center") var messageCenter : Int,
        @SerializedName("academic_planner") var academicPlanner : Int,
        @SerializedName("career_plan") var careerPlan : Int,
        @SerializedName("ny_state_career") var nyStateCareer : Int,
        @SerializedName("recommendation") var recommendation : Int,
        @SerializedName("college_ratings") var collegeRatings : Int,
        @SerializedName("college_planner_lock") var collegePlannerLock : Int,
        @SerializedName("test_score_edit_by_student") var testScoreEditByStudent : Int,
        @SerializedName("college_visits_visibility") var collegeVisitsVisibility : Int,
        @SerializedName("hide_university_contact_email") var hideUniversityContactEmail : Int,
        @SerializedName("hide_university_contact_phone") var hideUniversityContactPhone : Int,
        @SerializedName("parent_add_college_for_student") var parentAddCollegeForStudent : Int,
        @SerializedName("college_application_status_lock") var collegeApplicationStatusLock : Int,
        @SerializedName("course_catalog") var courseCatalog : Int,
        @SerializedName("request_recs") var requestRecs : Int,
        @SerializedName("send_summary") var sendSummary : Int,
        @SerializedName("prepare_documents") var prepareDocuments : Int,
        @SerializedName("send_documents") var sendDocuments : Int,
        @SerializedName("university_contacts") var universityContacts : Int,
        @SerializedName("recommendation_dashboard") var recommendationDashboard : Int,
        @SerializedName("thriving_index") var thrivingIndex : Int

    )
    data class CompareMeClassOfRange (

        @SerializedName("max_class_of") var maxClassOf : String,
        @SerializedName("min_class_of") var minClassOf : String

    )
    data class ScattergramXYValues (

        @SerializedName("SAT") var SAT_ : SAT,
        @SerializedName("ACT") var ACT_ : ACT,
        @SerializedName("GPA") var GPA_ : GPA,
        @SerializedName("WGPA") var WGPA_ : WGPA

    ){
        data class SAT (

            @SerializedName("min") var min : String,
            @SerializedName("max") var max : Int

        )
        data class ACT (

            @SerializedName("min") var min : String,
            @SerializedName("max") var max : Int

        )
        data class GPA (

            @SerializedName("min") var min : String,
            @SerializedName("max") var max : Int

        )
        data class WGPA (

            @SerializedName("min") var min : String,
            @SerializedName("max") var max : Int

        )
    }
    data class SchoolShortcut (

        @SerializedName("school") var school : List<School>,
        @SerializedName("student") var student : String

    ){
        data class School (

            @SerializedName("name") var name : String,
            @SerializedName("external_link") var externalLink : String

        )
    }

    data class AssessmentPermission (

        @SerializedName("lsi") var lsi : List<Int>,
        @SerializedName("dwyr") var dwyr : List<Int>,
        @SerializedName("mi") var mi : List<Int>,
        @SerializedName("interest_profiler") var interestProfiler : List<Int>,
        @SerializedName("work_values") var workValues : List<Int>,
        @SerializedName("skills") var skills : List<Int>,
        @SerializedName("tte_ti") var tteTi : List<Int>,
        @SerializedName("tte_ac") var tteAc : List<Int>,
        @SerializedName("tte_ex") var tteEx : List<Int>

    )
    data class GradeBasedCollegePlan (

        @SerializedName("college_plan") var collegePlan : List<Int>

    )
    data class GradeBasedCollegeVisit (

        @SerializedName("college_visit") var collegeVisit : List<Int>

    )
    data class Wwa (

        @SerializedName("license") var license : Int,
        @SerializedName("token") var token : String

    )
    data class MlSchoolConfigData (

        @SerializedName("ib_transcript_listing") var ibTranscriptListing : IbTranscriptListing,
        @SerializedName("verify_activity_complete") var verifyActivityComplete : List<Int>,
        @SerializedName("show_new_home_page") var showNewHomePage : List<Int>,
        @SerializedName("days_before_visits_to_show") var daysBeforeVisitsToShow : String,
        @SerializedName("mark_activity_complete_counselor") var markActivityCompleteCounselor : List<Int>,
        @SerializedName("enable_dob_visit_download") var enableDobVisitDownload : List<Int>,
        @SerializedName("bulk_upload_gpa_settings") var bulkUploadGpaSettings : BulkUploadGpaSettings,
        @SerializedName("curriculum_permission") var curriculumPermission : CurriculumPermission,
        @SerializedName("curriculum_permission_school") var curriculumPermissionSchool : CurriculumPermissionSchool,
        @SerializedName("grade_convention") var gradeConvention : GradeConvention,
        @SerializedName("student_can_set_preferred_recommender") var studentCanSetPreferredRecommender : List<Int>,
        @SerializedName("internal_application_deadline") var internalApplicationDeadline : InternalApplicationDeadline,
        @SerializedName("recommendation") var recommendation : Recommendation,
        @SerializedName("old_student_dashboard") var oldStudentDashboard : List<Int>,
        @SerializedName("time_tracking") var timeTracking : TimeTracking,
        @SerializedName("invoicing") var invoicing : Invoicing,
        @SerializedName("essay_outline_developer_view") var essayOutlineDeveloperView : List<Int>,
        @SerializedName("district_wide_recommendation") var districtWideRecommendation : List<Int>

    ){
        data class IbTranscriptListing (

            @SerializedName("9") var nine_ : Nine,
            @SerializedName("10") var ten : Ten,
            @SerializedName("11") var eleven : Eleven,
            @SerializedName("12") var twelve_ : Twelve

        ){
            data class Nine (

                @SerializedName("subject") var subject : Subject,
                @SerializedName("level") var level : Level,
                @SerializedName("midyear") var midyear : Midyear,
                @SerializedName("yearend") var yearend : Yearend,
                @SerializedName("predicted") var predicted : Predicted,
                @SerializedName("results") var results : Results

            )
                data class Subject (

                    @SerializedName("label") var label : String

                )
                data class Level (

                    @SerializedName("label") var label : String,
                    @SerializedName("visibility") var visibility : Int

                )
                data class Midyear (

                    @SerializedName("label") var label : String,
                    @SerializedName("visibility") var visibility : Int

                )
                data class Yearend (

                    @SerializedName("label") var label : String,
                    @SerializedName("visibility") var visibility : Int

                )
                data class Predicted (

                    @SerializedName("label") var label : String,
                    @SerializedName("visibility") var visibility : Int

                )
                data class Results (

                    @SerializedName("label") var label : String,
                    @SerializedName("visibility") var visibility : Int

                )

            data class Ten (

                @SerializedName("subject") var subject : Subject,
                @SerializedName("level") var level : Level,
                @SerializedName("midyear") var midyear : Midyear,
                @SerializedName("yearend") var yearend : Yearend,
                @SerializedName("predicted") var predicted : Predicted,
                @SerializedName("results") var results : Results

            )
            data class Eleven (

                @SerializedName("subject") var subject : Subject,
                @SerializedName("level") var level : Level,
                @SerializedName("midyear") var midyear : Midyear,
                @SerializedName("yearend") var yearend : Yearend,
                @SerializedName("predicted") var predicted : Predicted,
                @SerializedName("results") var results : Results

            )
            data class Twelve (

                @SerializedName("subject") var subject : Subject,
                @SerializedName("level") var level : Level,
                @SerializedName("midyear") var midyear : Midyear,
                @SerializedName("yearend") var yearend : Yearend,
                @SerializedName("predicted") var predicted : Predicted,
                @SerializedName("results") var results : Results

            )
        }
        data class BulkUploadGpaSettings (

            @SerializedName("update_gpa_field") var updateGpaField : Int,
            @SerializedName("update_wgpa_field") var updateWgpaField : Int,
            @SerializedName("gpa_search_string") var gpaSearchString : String,
            @SerializedName("wgpa_search_string") var wgpaSearchString : String

        )
        data class CurriculumPermission (

            @SerializedName("enable_curriculum_school") var enableCurriculumSchool : Int,
            @SerializedName("add_curriculum_course") var addCurriculumCourse : Int

        )

        data class CurriculumPermissionSchool (

            @SerializedName("allow_parents_to_view_sel_lessons") var allowParentsToViewSelLessons : Int,
            @SerializedName("allow_parents_to_see_student_activities_in_the_lessons") var allowParentsToSeeStudentActivitiesInTheLessons : Int,
            @SerializedName("allow_parents_to_see_submitted_activities_by_students") var allowParentsToSeeSubmittedActivitiesByStudents : Int

        )
        data class GradeConvention (

            @SerializedName("uk_convention") var ukConvention : Int,
            @SerializedName("us_convention") var usConvention : Int

        )
        data class ConfigChoices (

            @SerializedName("internal_deadline_date") var internalDeadlineDate : Int,
            @SerializedName("relative_deadline") var relativeDeadline : Int

        )
        data class ConfigValues (

            @SerializedName("relative_deadline_days") var relativeDeadlineDays : Int

        )
        data class InternalApplicationDeadline (

            @SerializedName("config_choices") var configChoices : ConfigChoices,
            @SerializedName("config_values") var configValues : ConfigValues

        )
        data class DefaultListing (

            @SerializedName("recommendation_letter_requests") var recommendationLetterRequests : Int,
            @SerializedName("ucas_reference_letter") var ucasReferenceLetter : Int,
            @SerializedName("both") var both : Int

        )
        data class Recommendation (

            @SerializedName("default_listing") var defaultListing : DefaultListing

        )
        data class TimeTracking (

            @SerializedName("allow_time_tracking") var allowTimeTracking : Int

        )
        data class Invoicing (

            @SerializedName("allow_invoicing") var allowInvoicing : Int,
            @SerializedName("company_logo") var companyLogo : String,
            @SerializedName("default_currency") var defaultCurrency : String,
            @SerializedName("default_message_email") var defaultMessageEmail : String,
            @SerializedName("default_message_invoice") var defaultMessageInvoice : String,
            @SerializedName("invoice_due_days") var invoiceDueDays : Int,
            @SerializedName("rate") var rate : Int

        )
    }

    data class MlUserDefaultConfigData (

        @SerializedName("default_college_list_view") var defaultCollegeListView : List<String>

    )
    data class CurriculumPermission (

        @SerializedName("enable_curriculum_school") var enableCurriculumSchool : Int,
        @SerializedName("add_curriculum_course") var addCurriculumCourse : Int

    )
    data class MenuPermissions (

        @SerializedName("Explore") var Explore : String,
        @SerializedName("Interest Profiler") var Interest_Profiler : String,
        @SerializedName("Work Values") var Work_Values : String,
        @SerializedName("Personality") var Personality : String,
        @SerializedName("Skills") var Skills : String,
        @SerializedName("Intelligences") var Intelligences : String,
        @SerializedName("Learning & Productivity") var Learning_Productivity : String,
        @SerializedName("Career Plan") var Caree_Plan : String,
        @SerializedName("Assessments") var Assessments : String,
        @SerializedName("Career Search") var Caree_Search : String,
        @SerializedName("Career List") var Career_List : String,
        @SerializedName("NYS Career Plan") var NYS_Career_Plan : String,
        @SerializedName("Academic Plan") var Academic_Plan : String,
        @SerializedName("Academic Planner") var Academic_Planner : String,
        @SerializedName("Completed Courses") var Completed_Courses : String,
        @SerializedName("Course Catalog") var Course_Catalog : String,
        @SerializedName("IB Transcript") var IB_Transcript : String,
        @SerializedName("College Plan") var College_Plan : String,
        @SerializedName("College Search") var College_Search : String,
        @SerializedName("Colleges Considering") var Colleges_Considering : String,
        @SerializedName("Colleges Applying") var Colleges_Applying : String,
        @SerializedName("Compare Me") var Compare_Me : String,
        @SerializedName("Request Recs") var Request_Recs : String,
        @SerializedName("College Visits") var Colleg_Visits : String,
        @SerializedName("Test Scores") var Test_Scores : String,
        @SerializedName("Portfolio") var Portfolio : String,
        @SerializedName("Goals") var Goals : String,
        @SerializedName("Journals") var Journals : String,
        @SerializedName("Experiences") var Experiences : String,
        @SerializedName("Galleries") var Galleries : String,
        @SerializedName("Resume") var Resume : String,
        @SerializedName("MaiaDrive") var MaiaDrive : String,
        @SerializedName("Maia Docs") var Maia_Docs : String,
        @SerializedName("Send Summary") var Send_Summary : String,
        @SerializedName("Send Docs") var Send_Docs : String,
        @SerializedName("Reports") var Reports : String,
        @SerializedName("Career Exploration") var Career_Exploration : String,
        @SerializedName("College Apps") var College_Apps : String,
        @SerializedName("Scholarship") var Scholarship : String,
        @SerializedName("Engagement") var Engagement : String,
        @SerializedName("PortfolioSummary") var PortfolioSummary : String,
        @SerializedName("Scholarships") var Scholarships : String,
        @SerializedName("Notes") var Notes : String,
        @SerializedName("Thriving Index") var Thriving_Index : String,
        @SerializedName("AGILE Cognitive") var AGILE_Cognitive : String,
        @SerializedName("Element X") var Element_X : String,
        @SerializedName("Admission Office") var Admission_Office : String

    )
//    data class GaugesPermissionConfig (
//
//        @SerializedName("gpa") var gpa : Gpa,
//        @SerializedName("psat") var psat : Psat,
//        @SerializedName("sat") var sat : Sat,
//        @SerializedName("pre-act") var pre-act : Pre-act,
//    @SerializedName("act") var act : Act,
//    @SerializedName("wgpa") var wgpa : Wgpa,
//    @SerializedName("french_bac") var frenchBac : FrenchBac,
//    @SerializedName("ib_score") var ibScore : IbScore,
//    @SerializedName("ib_predicted") var ibPredicted : IbPredicted
////    @SerializedName("fafsa") var fafsa : Fafsa
//
//    ){
//        data class IbPredicted (
//
//            @SerializedName("visibility") var visibility : Int,
//            @SerializedName("max_value") var maxValue : Int
//
//        )
//    }
}

