package com.maialearning.model

import com.google.gson.annotations.SerializedName

data class LoginNewModel(
    @field:SerializedName("wwa")
    val wwa: Wwa? = null,

    @field:SerializedName("school_uuid")
    val schoolUuid: String? = null,

    @field:SerializedName("school_shortcut")
    val schoolShortcut: SchoolShortcut? = null,

    @field:SerializedName("school_type")
    val schoolType: String? = null,

    @field:SerializedName("school_timezone_abbr")
    val schoolTimezoneAbbr: String? = null,

    @field:SerializedName("field_brag_sheet_config")
    val fieldBragSheetConfig: Int? = null,

    @field:SerializedName("common_app_send")
    val commonAppSend: String? = null,

    @field:SerializedName("default_class_of")
    val defaultClassOf: Any? = null,

    @field:SerializedName("teacher_evaluation_rating_config")
    val teacherEvaluationRatingConfig: String? = null,

    @field:SerializedName("parchment_send_ca")
    val parchmentSendCa: String? = null,

    @field:SerializedName("field_hide_data_sent_report")
    val fieldHideDataSentReport: String? = null,

    @field:SerializedName("disabling_doc_download")
    val disablingDocDownload: String? = null,

    @field:SerializedName("compare_me_config")
    val compareMeConfig: String? = null,

    @field:SerializedName("scattergram_gpa_value")
    val scattergramGpaValue: String? = null,

    @field:SerializedName("school_timezone_offset")
    val schoolTimezoneOffset: String? = null,

    @field:SerializedName("show_reco_student_college_data")
    val showRecoStudentCollegeData: String? = null,

    @field:SerializedName("ca_sync_cc")
    val caSyncCc: String? = null,

    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("refresh_token")
    val refreshToken: String? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("essay")
    val essay: Essay? = null,

    @field:SerializedName("school_level")
    val schoolLevel: String? = null,

    @field:SerializedName("curriculum_permission")
    val curriculumPermission: CurriculumPermission? = null,

    @field:SerializedName("hide_stu_assign_from_edu")
    val hideStuAssignFromEdu: String? = null,

    @field:SerializedName("current_common_app_application_year")
    val currentCommonAppApplicationYear: String? = null,

    @field:SerializedName("assessment_permission")
    val assessmentPermission: AssessmentPermission? = null,

    @field:SerializedName("recommendation_mail")
    val recommendationMail: String? = null,

    @field:SerializedName("move_to_applying")
    val moveToApplying: String? = null,

    @field:SerializedName("student_upload_pic")
    val studentUploadPic: String? = null,

    @field:SerializedName("district_level_activities")
    val districtLevelActivities: String? = null,

    @field:SerializedName("is_sel_content_school")
    val isSelContentSchool: Int? = null,

    @field:SerializedName("switch_role")
    val switchRole: Any? = null,

    @field:SerializedName("student_default_page")
    val studentDefaultPage: String? = null,

    @field:SerializedName("lesson_plan_publisher")
    val lessonPlanPublisher: Int? = null,

    @field:SerializedName("preferred_langcode")
    val preferredLangcode: Any? = null,

    @field:SerializedName("maia_design_version")
    val maiaDesignVersion: String? = null,

    @field:SerializedName("ib_completed_courses")
    val ibCompletedCourses: String? = null,

    @field:SerializedName("scattergram_x_y_values")
    val scattergramXYValues: ScattergramXYValues? = null,

    @field:SerializedName("iec_school")
    val iecSchool: Int? = null,

    @field:SerializedName("userName")
    val userName: UserName? = null,

    @field:SerializedName("gauges_permission_config")
    val gaugesPermissionConfig: GaugesPermissionConfig? = null,

//    @field:SerializedName("ml_user_default_config_data")
//    val mlUserDefaultConfigData: MlUserDefaultConfigData? = null,

    @field:SerializedName("student_dob_show")
    val studentDobShow: String? = null,

    @field:SerializedName("district_slug")
    val districtSlug: String? = null,

    @field:SerializedName("ap_completed_courses")
    val apCompletedCourses: String? = null,

    @field:SerializedName("ccgi_apps_permission")
    val ccgiAppsPermission: CcgiAppsPermission? = null,

    @field:SerializedName("field_classroom_enabled")
    val fieldClassroomEnabled: String? = null,

    @field:SerializedName("compare_me_default_year")
    val compareMeDefaultYear: List<String?>? = null,

    @field:SerializedName("default_caseload_id")
    val defaultCaseloadId: Any? = null,

    @field:SerializedName("skills_assessment_config")
    val skillsAssessmentConfig: String? = null,

    @field:SerializedName("grade_based_college_plan")
    val gradeBasedCollegePlan: GradeBasedCollegePlan? = null,

    @field:SerializedName("school_starting_month")
    val schoolStartingMonth: String? = null,

    @field:SerializedName("resume_flag")
    val resumeFlag: String? = null,

    @field:SerializedName("compare_me_class_of_range")
    val compareMeClassOfRange: CompareMeClassOfRange? = null,

    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("grade_based_college_visit")
    val gradeBasedCollegeVisit: GradeBasedCollegeVisit? = null,

    @field:SerializedName("ml_school_config_data")
    val mlSchoolConfigData: MlSchoolConfigData? = null,

    @field:SerializedName("enabled_2fa")
    val enabled2fa: Int? = null,

    @field:SerializedName("menu_permissions")
    val menuPermissions: MenuPermissions? = null
) {

    data class SchoolItem(

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("external_link")
        val externalLink: String? = null
    )

    data class FieldSignUp(

        @field:SerializedName("und")
        val und: List<UndItem?>? = null
    )

    data class GradeBasedCollegeVisit(

        @field:SerializedName("college_visit")
        val collegeVisit: List<Int?>? = null
    )

    data class ConfigValues(

        @field:SerializedName("relative_deadline_days")
        val relativeDeadlineDays: Int? = null
    )

    data class GaugesPermissionConfig(

        @field:SerializedName("ib_score")
        val ibScore: IbScore? = null,

        @field:SerializedName("psat")
        val psat: Psat? = null,

        @field:SerializedName("fafsa")
        val fafsa: Fafsa? = null,

        @field:SerializedName("act")
        val act: Act? = null,

        @field:SerializedName("ib_predicted")
        val ibPredicted: IbPredicted? = null,

        @field:SerializedName("wgpa")
        val wgpa: Wgpa1? = null,

        @field:SerializedName("sat")
        val sat: Sat1? = null,

        @field:SerializedName("gpa")
        val gpa: Gpa1? = null,

        @field:SerializedName("french_bac")
        val frenchBac: FrenchBac? = null,

        @field:SerializedName("pre-act")
        val preAct: PreAct? = null
    )

    data class OgUserNode(

        @field:SerializedName("und")
        val und: List<UndItem?>? = null
    )

    data class GPA(

        @field:SerializedName("min")
        val min: String? = null,

        @field:SerializedName("max")
        val max: Int? = null
    )

    data class Recommendation(

        @field:SerializedName("default_listing")
        val defaultListing: DefaultListing? = null
    )

    data class Wgpa1(

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("max_value")
        val maxValue: Int? = null
    )

    data class FieldEmailNotProvide(

        @field:SerializedName("und")
        val und: List<UndItem?>? = null
    )

    data class JsonMember9(

        @field:SerializedName("level")
        val level: Level? = null,

        @field:SerializedName("subject")
        val subject: Subject? = null,

        @field:SerializedName("midyear")
        val midyear: Midyear? = null,

        @field:SerializedName("yearend")
        val yearend: Yearend? = null,

        @field:SerializedName("results")
        val results: Results? = null,

        @field:SerializedName("predicted")
        val predicted: Predicted? = null
    )

    data class Wwa(

        @field:SerializedName("license")
        val license: Int? = null,

        @field:SerializedName("token")
        val token: String? = null
    )

    data class UndItem(

        @field:SerializedName("target_id")
        val targetId: String? = null,

        @field:SerializedName("value")
        val value: String? = null
    )

    data class Subject(

        @field:SerializedName("label")
        val label: String? = null
    )

    data class Roles(

        @field:SerializedName("2")
        val jsonMember2: String? = null,

        @field:SerializedName("5")
        val jsonMember5: String? = null
    )

    data class Midyear(

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("label")
        val label: String? = null
    )

    data class Predicted(

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("label")
        val label: String? = null
    )

    data class MlUserDefaultConfigData(

        @field:SerializedName("default_college_list_view")
        val defaultCollegeListView: ArrayList<String>? = null
    )

    data class Invoicing(

        @field:SerializedName("allow_invoicing")
        val allowInvoicing: Int? = null,

        @field:SerializedName("invoice_due_days")
        val invoiceDueDays: Int? = null,

        @field:SerializedName("company_logo")
        val companyLogo: String? = null,

        @field:SerializedName("rate")
        val rate: Double? = null,

        @field:SerializedName("default_message_email")
        val defaultMessageEmail: String? = null,

        @field:SerializedName("default_currency")
        val defaultCurrency: String? = null,

        @field:SerializedName("default_message_invoice")
        val defaultMessageInvoice: String? = null
    )

    data class PreAct(

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("max_value")
        val maxValue: Int? = null
    )

    data class GradeConvention(

        @field:SerializedName("uk_convention")
        val ukConvention: Int? = null,

        @field:SerializedName("us_convention")
        val usConvention: Int? = null
    )

    data class TimeTracking(

        @field:SerializedName("allow_time_tracking")
        val allowTimeTracking: Int? = null
    )

    data class SchoolShortcut(

        @field:SerializedName("school")
        val school: List<SchoolItem?>? = null,

        @field:SerializedName("student")
        val student: Any? = null
    )

    data class CurriculumPermission(

        @field:SerializedName("enable_curriculum_school")
        val enableCurriculumSchool: Int? = null,

        @field:SerializedName("add_curriculum_course")
        val addCurriculumCourse: Int? = null
    )

    data class ScattergramXYValues(

        @field:SerializedName("ACT")
        val aCT: ACT1? = null,

        @field:SerializedName("SAT")
        val sAT: SAT? = null,

        @field:SerializedName("GPA")
        val gPA: GPA? = null,

        @field:SerializedName("WGPA")
        val wGPA: WGPA? = null
    )

    data class IbPredicted(

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("max_value")
        val maxValue: Int? = null
    )

    data class DefaultListing(

        @field:SerializedName("recommendation_letter_requests")
        val recommendationLetterRequests: Int? = null,

        @field:SerializedName("ucas_reference_letter")
        val ucasReferenceLetter: Int? = null,

        @field:SerializedName("both")
        val both: Int? = null
    )

    data class Psat(

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("max_value")
        val maxValue: Int? = null
    )

    data class MenuPermissions(

        @field:SerializedName("Thriving Index")
        val thrivingIndex: String? = null,

        @field:SerializedName("Explore")
        val explore: String? = null,

        @field:SerializedName("Career List")
        val careerList: String? = null,

        @field:SerializedName("Learning & Productivity")
        val learningProductivity: String? = null,

        @field:SerializedName("Career Exploration")
        val careerExploration: String? = null,

        @field:SerializedName("Course Catalog")
        val courseCatalog: String? = null,

        @field:SerializedName("Colleges Considering")
        val collegesConsidering: String? = null,

        @field:SerializedName("Compare Me")
        val compareMe: String? = null,

        @field:SerializedName("IB Transcript")
        val iBTranscript: String? = null,

        @field:SerializedName("Career Plan")
        val careerPlan: String? = null,

        @field:SerializedName("Send Summary")
        val sendSummary: String? = null,

        @field:SerializedName("Maia Docs")
        val maiaDocs: String? = null,

        @field:SerializedName("MaiaDrive")
        val maiaDrive: String? = null,

        @field:SerializedName("Engagement")
        val engagement: String? = null,

        @field:SerializedName("Career Search")
        val careerSearch: String? = null,

        @field:SerializedName("Academic Plan")
        val academicPlan: String? = null,

        @field:SerializedName("Skills")
        val skills: String? = null,

        @field:SerializedName("College Visits")
        val collegeVisits: String? = null,

        @field:SerializedName("Admission Office")
        val admissionOffice: String? = null,

        @field:SerializedName("College Search")
        val collegeSearch: String? = null,

        @field:SerializedName("Scholarship")
        val scholarship: String? = null,

        @field:SerializedName("Element X")
        val elementX: String? = null,

        @field:SerializedName("Notes")
        val notes: String? = null,

        @field:SerializedName("Scholarships")
        val scholarships: String? = null,

        @field:SerializedName("PortfolioSummary")
        val portfolioSummary: String? = null,

        @field:SerializedName("Request Recs")
        val requestRecs: String? = null,

        @field:SerializedName("Portfolio")
        val portfolio: String? = null,

        @field:SerializedName("Experiences")
        val experiences: String? = null,

        @field:SerializedName("Work Values")
        val workValues: String? = null,

        @field:SerializedName("Reports")
        val reports: String? = null,

        @field:SerializedName("Personality")
        val personality: String? = null,

        @field:SerializedName("Intelligences")
        val intelligences: String? = null,

        @field:SerializedName("Assessments")
        val assessments: String? = null,

        @field:SerializedName("Galleries")
        val galleries: String? = null,

        @field:SerializedName("College Apps")
        val collegeApps: String? = null,

        @field:SerializedName("College Plan")
        val collegePlan: String? = null,

        @field:SerializedName("Academic Planner")
        val academicPlanner: String? = null,

        @field:SerializedName("Colleges Applying")
        val collegesApplying: String? = null,

        @field:SerializedName("Send Docs")
        val sendDocs: String? = null,

        @field:SerializedName("Completed Courses")
        val completedCourses: String? = null,

        @field:SerializedName("AGILE Cognitive")
        val aGILECognitive: String? = null,

        @field:SerializedName("Test Scores")
        val testScores: String? = null,

        @field:SerializedName("Journals")
        val journals: String? = null,

        @field:SerializedName("Interest Profiler")
        val interestProfiler: String? = null,

        @field:SerializedName("NYS Career Plan")
        val nYSCareerPlan: String? = null,

        @field:SerializedName("Goals")
        val goals: String? = null,

        @field:SerializedName("Resume")
        val resume: String? = null
    )

    data class MlSchoolConfigData(

        @field:SerializedName("student_can_set_preferred_recommender")
        val studentCanSetPreferredRecommender: List<Int?>? = null,

        @field:SerializedName("district_wide_recommendation")
        val districtWideRecommendation: List<Int?>? = null,

        @field:SerializedName("verify_activity_complete")
        val verifyActivityComplete: List<Int?>? = null,

        @field:SerializedName("mark_activity_complete_counselor")
        val markActivityCompleteCounselor: List<Int?>? = null,

        @field:SerializedName("show_new_home_page")
        val showNewHomePage: List<Int?>? = null,

        @field:SerializedName("recommendation")
        val recommendation: Recommendation? = null,

        @field:SerializedName("internal_application_deadline")
        val internalApplicationDeadline: InternalApplicationDeadline? = null,

        @field:SerializedName("academic_planner_custom_subject")
        val academicPlannerCustomSubject: List<AcademicPlannerCustomSubjectItem?>? = null,

        @field:SerializedName("curriculum_permission_school")
        val curriculumPermissionSchool: CurriculumPermissionSchool? = null,

        @field:SerializedName("ib_transcript_dob_format")
        val ibTranscriptDobFormat: Any? = null,

        @field:SerializedName("enable_dob_visit_download")
        val enableDobVisitDownload: List<Int?>? = null,

        @field:SerializedName("bulk_upload_gpa_settings")
        val bulkUploadGpaSettings: BulkUploadGpaSettings? = null,

        @field:SerializedName("days_before_visits_to_show")
        val daysBeforeVisitsToShow: Any? = null,

        @field:SerializedName("old_student_dashboard")
        val oldStudentDashboard: List<Int?>? = null,

        @field:SerializedName("ib_transcript_listing")
        val ibTranscriptListing: IbTranscriptListing? = null,

        @field:SerializedName("curriculum_permission")
        val curriculumPermission: CurriculumPermission? = null,

        @field:SerializedName("invoicing")
        val invoicing: Invoicing? = null,

        @field:SerializedName("grade_convention")
        val gradeConvention: GradeConvention? = null,

        @field:SerializedName("time_tracking")
        val timeTracking: TimeTracking? = null,

        @field:SerializedName("essay_outline_developer_view")
        val essayOutlineDeveloperView: List<Int?>? = null
    )

    data class UserName(

        @field:SerializedName("last_name")
        val lastName: String? = null,

        @field:SerializedName("first_name")
        val firstName: String? = null
    )

    data class Gpa1(

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("max_value")
        val maxValue: Int? = null
    )

    data class Sat1(

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("max_value")
        val maxValue: Int? = null
    )

    data class GradeBasedCollegePlan(

        @field:SerializedName("college_plan")
        val collegePlan: List<Int?>? = null
    )

    data class IbScore(

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("max_value")
        val maxValue: Int? = null
    )

    data class BulkUploadGpaSettings(

        @field:SerializedName("update_gpa_field")
        val updateGpaField: Int? = null,

        @field:SerializedName("wgpa_search_string")
        val wgpaSearchString: String? = null,

        @field:SerializedName("gpa_search_string")
        val gpaSearchString: String? = null,

        @field:SerializedName("update_wgpa_field")
        val updateWgpaField: Int? = null
    )

    data class Yearend(

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("label")
        val label: String? = null
    )

    data class ACT1(

        @field:SerializedName("min")
        val min: String? = null,

        @field:SerializedName("max")
        val max: Int? = null
    )

    data class CcgiAppsPermission(

        @field:SerializedName("hide_university_contact_phone")
        val hideUniversityContactPhone: Int? = null,

        @field:SerializedName("lsi")
        val lsi: Int? = null,

        @field:SerializedName("csu")
        val csu: Int? = null,

        @field:SerializedName("survey_school")
        val surveySchool: Int? = null,

        @field:SerializedName("send_summary")
        val sendSummary: Int? = null,

        @field:SerializedName("recommendation")
        val recommendation: Int? = null,

        @field:SerializedName("university_contacts")
        val universityContacts: Int? = null,

        @field:SerializedName("hide_university_contact_email")
        val hideUniversityContactEmail: Int? = null,

        @field:SerializedName("message_center")
        val messageCenter: Int? = null,

        @field:SerializedName("uc")
        val uc: Int? = null,

        @field:SerializedName("scheduler")
        val scheduler: Int? = null,

        @field:SerializedName("college_application_status_lock")
        val collegeApplicationStatusLock: Int? = null,

        @field:SerializedName("sms")
        val sms: Int? = null,

        @field:SerializedName("prepare_documents")
        val prepareDocuments: Int? = null,

        @field:SerializedName("academic_planner")
        val academicPlanner: Int? = null,

        @field:SerializedName("mi")
        val mi: Int? = null,

        @field:SerializedName("ny_state_career")
        val nyStateCareer: Int? = null,

        @field:SerializedName("thriving_index")
        val thrivingIndex: Int? = null,

        @field:SerializedName("career_plan")
        val careerPlan: Int? = null,

        @field:SerializedName("college_visits_visibility")
        val collegeVisitsVisibility: Int? = null,

        @field:SerializedName("recommendation_dashboard")
        val recommendationDashboard: Int? = null,

        @field:SerializedName("dwya")
        val dwya: Int? = null,

        @field:SerializedName("parent_add_college_for_student")
        val parentAddCollegeForStudent: Int? = null,

        @field:SerializedName("college_ratings")
        val collegeRatings: Int? = null,

        @field:SerializedName("college_planner_lock")
        val collegePlannerLock: Int? = null,

        @field:SerializedName("course_catalog")
        val courseCatalog: Int? = null,

        @field:SerializedName("send_documents")
        val sendDocuments: Int? = null,

        @field:SerializedName("test_score_edit_by_student")
        val testScoreEditByStudent: Int? = null,

        @field:SerializedName("request_recs")
        val requestRecs: Int? = null
    )

    data class JsonMember12(

        @field:SerializedName("level")
        val level: Level? = null,

        @field:SerializedName("subject")
        val subject: Subject? = null,

        @field:SerializedName("midyear")
        val midyear: Midyear? = null,

        @field:SerializedName("yearend")
        val yearend: Yearend? = null,

        @field:SerializedName("results")
        val results: Results? = null,

        @field:SerializedName("predicted")
        val predicted: Predicted? = null
    )

    data class SAT(

        @field:SerializedName("min")
        val min: String? = null,

        @field:SerializedName("max")
        val max: Int? = null
    )

    data class AcademicPlannerCustomSubjectItem(

        @field:SerializedName("subject_id")
        val subjectId: Int? = null,

        @field:SerializedName("deleted")
        val deleted: Int? = null,

        @field:SerializedName("subject_name")
        val subjectName: String? = null
    )

    data class CurriculumPermissionSchool(

        @field:SerializedName("allow_parents_to_view_sel_lessons")
        val allowParentsToViewSelLessons: Int? = null,

        @field:SerializedName("allow_parents_to_see_student_activities_in_the_lessons")
        val allowParentsToSeeStudentActivitiesInTheLessons: Int? = null,

        @field:SerializedName("allow_parents_to_see_submitted_activities_by_students")
        val allowParentsToSeeSubmittedActivitiesByStudents: Int? = null
    )

    data class FrenchBac(

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("max_value")
        val maxValue: Int? = null
    )

    data class InternalApplicationDeadline(

        @field:SerializedName("config_values")
        val configValues: ConfigValues? = null,

        @field:SerializedName("config_choices")
        val configChoices: ConfigChoices? = null
    )

    data class Level(

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("label")
        val label: String? = null
    )

    data class AssessmentPermission(

        @field:SerializedName("skills")
        val skills: List<Int?>? = null,

        @field:SerializedName("lsi")
        val lsi: List<Int?>? = null,

        @field:SerializedName("dwyr")
        val dwyr: List<Int?>? = null,

        @field:SerializedName("work_values")
        val workValues: List<Int?>? = null,

        @field:SerializedName("tte_ac")
        val tteAc: List<Int?>? = null,

        @field:SerializedName("tte_ex")
        val tteEx: List<Int?>? = null,

        @field:SerializedName("tte_ti")
        val tteTi: List<Int?>? = null,

        @field:SerializedName("mi")
        val mi: List<Int?>? = null,

        @field:SerializedName("interest_profiler")
        val interestProfiler: List<Int?>? = null
    )

    data class Results(

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("label")
        val label: String? = null
    )

    data class JsonMember10(

        @field:SerializedName("level")
        val level: Level? = null,

        @field:SerializedName("subject")
        val subject: Subject? = null,

        @field:SerializedName("midyear")
        val midyear: Midyear? = null,

        @field:SerializedName("yearend")
        val yearend: Yearend? = null,

        @field:SerializedName("results")
        val results: Results? = null,

        @field:SerializedName("predicted")
        val predicted: Predicted? = null
    )

    data class User(

        @field:SerializedName("access")
        val access: String? = null,

        @field:SerializedName("mail")
        val mail: String? = null,

        @field:SerializedName("data")
        val data: Data? = null,

        @field:SerializedName("signature")
        val signature: String? = null,

        @field:SerializedName("created")
        val created: String? = null,

        @field:SerializedName("timezone")
        val timezone: Any? = null,

        @field:SerializedName("roles")
        val roles: Roles? = null,

        @field:SerializedName("language")
        val language: String? = null,

        @field:SerializedName("og_user_node")
        val ogUserNode: OgUserNode? = null,

        @field:SerializedName("message_id")
        val messageId: String? = null,

        @field:SerializedName("field_google_login_id")
        val fieldGoogleLoginId: List<Any?>? = null,

        @field:SerializedName("login")
        val login: Int? = null,

        @field:SerializedName("uuid")
        val uuid: String? = null,

        @field:SerializedName("picture")
        val picture: String? = null,

        @field:SerializedName("uid")
        val uid: String? = null,

        @field:SerializedName("signature_format")
        val signatureFormat: Any? = null,

        @field:SerializedName("field_email_not_provide")
        val fieldEmailNotProvide: FieldEmailNotProvide? = null,

        @field:SerializedName("field_sign_up")
        val fieldSignUp: FieldSignUp? = null,

        @field:SerializedName("grade")
        val grade: String? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("theme")
        val theme: String? = null,

        @field:SerializedName("field_login_count")
        val fieldLoginCount: List<Any?>? = null,

        @field:SerializedName("status")
        val status: String? = null
    )

    data class Data(

        @field:SerializedName("mimemail_textonly")
        val mimemailTextonly: Int? = null
    )

    data class Fafsa(

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("max_value")
        val maxValue: Any? = null
    )

    data class JsonMember11(

        @field:SerializedName("level")
        val level: Level? = null,

        @field:SerializedName("subject")
        val subject: Subject? = null,

        @field:SerializedName("midyear")
        val midyear: Midyear? = null,

        @field:SerializedName("yearend")
        val yearend: Yearend? = null,

        @field:SerializedName("results")
        val results: Results? = null,

        @field:SerializedName("predicted")
        val predicted: Predicted? = null
    )

    data class ConfigChoices(

        @field:SerializedName("internal_deadline_date")
        val internalDeadlineDate: Int? = null,

        @field:SerializedName("relative_deadline")
        val relativeDeadline: Int? = null
    )

    data class IbTranscriptListing(

        @field:SerializedName("11")
        val jsonMember11: JsonMember11? = null,

        @field:SerializedName("12")
        val jsonMember12: JsonMember12? = null,

        @field:SerializedName("9")
        val jsonMember9: JsonMember9? = null,

        @field:SerializedName("10")
        val jsonMember10: JsonMember10? = null
    )

    data class Essay(

        @field:SerializedName("list_type")
        val listType: String? = null
    )

    data class WGPA(

        @field:SerializedName("min")
        val min: String? = null,

        @field:SerializedName("max")
        val max: Int? = null
    )

    data class Act(

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("max_value")
        val maxValue: Int? = null
    )

    data class CompareMeClassOfRange(

        @field:SerializedName("min_class_of")
        val minClassOf: String? = null,

        @field:SerializedName("max_class_of")
        val maxClassOf: String? = null
    )
}
