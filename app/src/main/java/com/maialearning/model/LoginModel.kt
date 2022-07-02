package com.maialearning.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginModel(
    @SerialName("access_token")
    var accessToken: String,
    @SerialName("refresh_token")
    var refreshToken: String,
    @SerialName("user")
    var user: User,
    @SerialName("essay")
    var essay: Essay,
    @SerialName("userName")
    var userName: UserName,
    @SerialName("enabled_2fa")
    var enabled2fa: Int,
    @SerialName("ccgi_apps_permission")
    var ccgiAppsPermission: CcgiAppsPermission,
    @SerialName("school_type")
    var schoolType: String,
    @SerialName("iec_school")
    var iecSchool: Int,
    @SerialName("school_level")
    var schoolLevel: String,
    @SerialName("student_upload_pic")
    var studentUploadPic: String,
    @SerialName("district_level_activities")
    var districtLevelActivities: String,
    @SerialName("school_starting_month")
    var schoolStartingMonth: String,
    @SerialName("student_default_page")
    var studentDefaultPage: String,
    @SerialName("school_timezone_offset")
    var schoolTimezoneOffset: String,
    @SerialName("school_uuid")
    var schoolUuid: String,
    @SerialName("ap_completed_courses")
    var apCompletedCourses: String,
    @SerialName("ib_completed_courses")
    var ibCompletedCourses: String,
    @SerialName("compare_me_default_year")
    var compareMeDefaultYear: List<String>,
    @SerialName("resume_flag")
    var resumeFlag: String,
    @SerialName("teacher_evaluation_rating_config")
    var teacherEvaluationRatingConfig: String,
    @SerialName("compare_me_class_of_range")
    var compareMeClassOfRange: CompareMeClassOfRange,
    @SerialName("gauges_permission_config")
    var gaugesPermissionConfig: GaugesPermissionConfig,
    @SerialName("default_class_of")

    var defaultClassOf: String,
    @SerialName("default_caseload_id")

    var defaultCaseloadId: String,
    @SerialName("school_timezone_abbr")
    var schoolTimezoneAbbr: String,
    @SerialName("skills_assessment_config")
    var skillsAssessmentConfig: String,
    @SerialName("scattergram_gpa_value")
    var scattergramGpaValue: String,
    @SerialName("compare_me_config")
    var compareMeConfig: String,
    @SerialName("hide_stu_assign_from_edu")
    var hideStuAssignFromEdu: String,
    @SerialName("scattergram_x_y_values")
    var scattergramXYValues: ScattergramXYValues,
    @SerialName("recommendation_mail")
    var recommendationMail: String,
    @SerialName("ca_sync_cc")
    var caSyncCc: String,
    @SerialName("move_to_applying")
    var moveToApplying: String,
    @SerialName("field_hide_data_sent_report")
    var fieldHideDataSentReport: String,
    @SerialName("disabling_doc_download")
    var disablingDocDownload: String,
    @SerialName("parchment_send_ca")
    var parchmentSendCa: String,
    @SerialName("common_app_send")
    var commonAppSend: String,
    @SerialName("school_shortcut")
    var schoolShortcut: SchoolShortcut,
    @SerialName("assessment_permission")
    var assessmentPermission: AssessmentPermission,
    @SerialName("grade_based_college_plan")
    var gradeBasedCollegePlan: GradeBasedCollegePlan,
    @SerialName("grade_based_college_visit")
    var gradeBasedCollegeVisit: GradeBasedCollegeVisit,
    @SerialName("student_dob_show")
    var studentDobShow: String,
    @SerialName("show_reco_student_college_data")
    var showRecoStudentCollegeData: String,
//    @SerialName("switch_role")
//
//    var switchRole: String,
    @SerialName("maia_design_version")
    var maiaDesignVersion: String,
    @SerialName("field_classroom_enabled")
    var fieldClassroomEnabled: String,
    @SerialName("lesson_plan_publisher")
    var lessonPlanPublisher: Int,
    @SerialName("field_brag_sheet_config")
    var fieldBragSheetConfig: Int,
    @SerialName("wwa")
    var wwa: Wwa,
    @SerialName("ml_school_config_data")
    var mlSchoolConfigData: MlSchoolConfigData,
    @SerialName("ml_user_default_config_data")
    var mlUserDefaultConfigData: MlUserDefaultConfigData,
    @SerialName("is_sel_content_school")
    var isSelContentSchool: Int,
    @SerialName("current_common_app_application_year")
    var currentCommonAppApplicationYear: String,
    @SerialName("district_slug")
    var districtSlug: String,
    @SerialName("curriculum_permission")
    var curriculumPermission: CurriculumPermission,
    @SerialName("menu_permissions")
    var menuPermissions: MenuPermissions,
    @SerialName("preferred_langcode")

    var preferredLangcode: String
) {
    @Serializable
    data class User(
        @SerialName("uid")
        var uid: String,
        @SerialName("name")
        var name: String,
        @SerialName("theme")
        var theme: String,
        @SerialName("signature")
        var signature: String,
        @SerialName("signature_format")
        var signatureFormat: String,
        @SerialName("created")
        var created: String,
        @SerialName("access")
        var access: String,
        @SerialName("login")
        var login: Int,
        @SerialName("status")
        var status: String,
        @SerialName("timezone")
        var timezone: String,
        @SerialName("language")
        var language: String,
        @SerialName("picture")
        var picture: String,
        @SerialName("data")
        var `data`: Data,
        @SerialName("uuid")
        var uuid: String,
        @SerialName("roles")
        var roles: Roles,
        @SerialName("og_user_node")
        var ogUserNode: OgUserNode,
        @SerialName("field_sign_up")
        var fieldSignUp: FieldSignUp,
//        @SerialName("field_login_count")
//
//        var fieldLoginCount: List<String>,
        @SerialName("field_google_login_id")

        var fieldGoogleLoginId: List<String>,
        @SerialName("field_email_not_provide")
        var fieldEmailNotProvide: FieldEmailNotProvide,
        @SerialName("grade")
        var grade: String,
        @SerialName("message_id")
        var messageId: String
    ) {
        @Serializable
        data class Data(
            @SerialName("mimemail_textonly")
            var mimemailTextonly: Int,
            @SerialName("ckeditor_default")
            var ckeditorDefault: String,
            @SerialName("ckeditor_show_toggle")
            var ckeditorShowToggle: String,
            @SerialName("ckeditor_width")
            var ckeditorWidth: String,
            @SerialName("ckeditor_lang")
            var ckeditorLang: String,
            @SerialName("ckeditor_auto_lang")
            var ckeditorAutoLang: String
        )

        @Serializable
        data class Roles(
            @SerialName("2")
            var x2: String,
            @SerialName("5")
            var x5: String
        )

        @Serializable
        data class OgUserNode(
            @SerialName("und")
            var und: List<Und>
        ) {
            @Serializable
            data class Und(
                @SerialName("target_id")
                var targetId: String
            )
        }

        @Serializable
        data class FieldSignUp(
            @SerialName("und")
            var und: List<Und>
        ) {
            @Serializable
            data class Und(
                @SerialName("value")
                var value: String
            )
        }

        @Serializable
        data class FieldEmailNotProvide(
            @SerialName("und")
            var und: List<Und>
        ) {
            @Serializable
            data class Und(
                @SerialName("value")
                var value: String
            )
        }
    }

    @Serializable
    data class Essay(
        @SerialName("list_type")
        var listType: String
    )

    @Serializable
    data class UserName(
        @SerialName("first_name")
        var firstName: String,
        @SerialName("last_name")
        var lastName: String
    )

    @Serializable
    data class CcgiAppsPermission(
        @SerialName("csu")
        var csu: Int,
        @SerialName("uc")
        var uc: Int,
        @SerialName("mi")
        var mi: Int,
        @SerialName("lsi")
        var lsi: Int,
        @SerialName("dwya")
        var dwya: Int,
        @SerialName("sms")
        var sms: Int,
        @SerialName("survey_school")
        var surveySchool: Int,
        @SerialName("scheduler")
        var scheduler: Int,
        @SerialName("message_center")
        var messageCenter: Int,
        @SerialName("academic_planner")
        var academicPlanner: Int,
        @SerialName("career_plan")
        var careerPlan: Int,
        @SerialName("ny_state_career")
        var nyStateCareer: Int,
        @SerialName("recommendation")
        var recommendation: Int,
        @SerialName("college_ratings")
        var collegeRatings: Int,
        @SerialName("college_planner_lock")
        var collegePlannerLock: Int,
        @SerialName("test_score_edit_by_student")
        var testScoreEditByStudent: Int,
        @SerialName("college_visits_visibility")
        var collegeVisitsVisibility: Int,
        @SerialName("hide_university_contact_email")
        var hideUniversityContactEmail: Int,
        @SerialName("hide_university_contact_phone")
        var hideUniversityContactPhone: Int,
        @SerialName("parent_add_college_for_student")
        var parentAddCollegeForStudent: Int,
        @SerialName("college_application_status_lock")
        var collegeApplicationStatusLock: Int,
        @SerialName("course_catalog")
        var courseCatalog: Int,
        @SerialName("request_recs")
        var requestRecs: Int,
        @SerialName("send_summary")
        var sendSummary: Int,
        @SerialName("prepare_documents")
        var prepareDocuments: Int,
        @SerialName("send_documents")
        var sendDocuments: Int,
        @SerialName("university_contacts")
        var universityContacts: Int,
        @SerialName("recommendation_dashboard")
        var recommendationDashboard: Int,
        @SerialName("thriving_index")
        var thrivingIndex: Int
    )

    @Serializable
    data class CompareMeClassOfRange(
        @SerialName("max_class_of")
        var maxClassOf: String,
        @SerialName("min_class_of")
        var minClassOf: String
    )

    @Serializable
    data class GaugesPermissionConfig(
        @SerialName("gpa")
        var gpa: Gpa,
        @SerialName("psat")
        var psat: Psat,
        @SerialName("sat")
        var sat: Sat,
        @SerialName("pre-act")
        var preAct: PreAct,
        @SerialName("act")
        var act: Act,
        @SerialName("wgpa")
        var wgpa: Wgpa,
        @SerialName("french_bac")
        var frenchBac: FrenchBac,
        @SerialName("ib_score")
        var ibScore: IbScore,
        @SerialName("ib_predicted")
        var ibPredicted: IbPredicted,
        @SerialName("fafsa")
        var fafsa: Fafsa
    ) {
        @Serializable
        data class Gpa(
            @SerialName("visibility")
            var visibility: Int,
            @SerialName("max_value")
            var maxValue: Int
        )

        @Serializable
        data class Psat(
            @SerialName("visibility")
            var visibility: Int,
            @SerialName("max_value")
            var maxValue: Int
        )

        @Serializable
        data class Sat(
            @SerialName("visibility")
            var visibility: Int,
            @SerialName("max_value")
            var maxValue: Int
        )

        @Serializable
        data class PreAct(
            @SerialName("visibility")
            var visibility: Int,
            @SerialName("max_value")
            var maxValue: Int
        )

        @Serializable
        data class Act(
            @SerialName("visibility")
            var visibility: Int,
            @SerialName("max_value")
            var maxValue: Int
        )

        @Serializable
        data class Wgpa(
            @SerialName("visibility")
            var visibility: Int,
            @SerialName("max_value")
            var maxValue: Int
        )

        @Serializable
        data class FrenchBac(
            @SerialName("visibility")
            var visibility: Int,
            @SerialName("max_value")
            var maxValue: Int
        )

        @Serializable
        data class IbScore(
            @SerialName("visibility")
            var visibility: Int,
            @SerialName("max_value")
            var maxValue: Int
        )

        @Serializable
        data class IbPredicted(
            @SerialName("visibility")
            var visibility: Int,
            @SerialName("max_value")
            var maxValue: Int
        )

        @Serializable
        data class Fafsa(
            @SerialName("visibility")
            var visibility: Int,
//            @SerialName("max_value")
//
//            var maxValue: String
        )
    }

    @Serializable
    data class ScattergramXYValues(
        @SerialName("SAT")
        var sAT: SAT,
        @SerialName("ACT")
        var aCT: ACT,
        @SerialName("GPA")
        var gPA: GPA,
        @SerialName("WGPA")
        var wGPA: WGPA
    ) {
        @Serializable
        data class SAT(
            @SerialName("min")
            var min: String,
            @SerialName("max")
            var max: Int
        )

        @Serializable
        data class ACT(
            @SerialName("min")
            var min: String,
            @SerialName("max")
            var max: Int
        )

        @Serializable
        data class GPA(
            @SerialName("min")
            var min: String,
            @SerialName("max")
            var max: Int
        )

        @Serializable
        data class WGPA(
            @SerialName("min")
            var min: String,
            @SerialName("max")
            var max: Int
        )
    }

    @Serializable
    data class SchoolShortcut(
        @SerialName("school")
        var school: List<School>,
        @SerialName("student")
        var student: String
    ) {
        @Serializable
        data class School(
            @SerialName("name")
            var name: String,
            @SerialName("external_link")
            var externalLink: String
        )
    }

    @Serializable
    data class AssessmentPermission(
        @SerialName("lsi")
        var lsi: List<Int>,
        @SerialName("dwyr")
        var dwyr: List<Int>,
        @SerialName("mi")
        var mi: List<Int>,
        @SerialName("interest_profiler")
        var interestProfiler: List<Int>,
        @SerialName("work_values")
        var workValues: List<Int>,
        @SerialName("skills")
        var skills: List<Int>,
        @SerialName("tte_ti")
        var tteTi: List<Int>,
        @SerialName("tte_ac")
        var tteAc: List<Int>,
        @SerialName("tte_ex")
        var tteEx: List<Int>
    )

    @Serializable
    data class GradeBasedCollegePlan(
        @SerialName("college_plan")
        var collegePlan: List<Int>
    )

    @Serializable
    data class GradeBasedCollegeVisit(
        @SerialName("college_visit")
        var collegeVisit: List<Int>
    )

    @Serializable
    data class Wwa(
        @SerialName("license")
        var license: Int,
        @SerialName("token")
        var token: String
    )

    @Serializable
    data class MlSchoolConfigData(
        @SerialName("ib_transcript_listing")
        var ibTranscriptListing: IbTranscriptListing,
        @SerialName("verify_activity_complete")
        var verifyActivityComplete: List<Int>,
        @SerialName("show_new_home_page")
        var showNewHomePage: List<Int>,
        @SerialName("days_before_visits_to_show")

        var daysBeforeVisitsToShow: String,
        @SerialName("mark_activity_complete_counselor")
        var markActivityCompleteCounselor: List<Int>,
        @SerialName("enable_dob_visit_download")
        var enableDobVisitDownload: List<Int>,
        @SerialName("bulk_upload_gpa_settings")
        var bulkUploadGpaSettings: BulkUploadGpaSettings,
        @SerialName("curriculum_permission")
        var curriculumPermission: CurriculumPermission,
        @SerialName("curriculum_permission_school")
        var curriculumPermissionSchool: CurriculumPermissionSchool,
        @SerialName("grade_convention")
        var gradeConvention: GradeConvention,
        @SerialName("student_can_set_preferred_recommender")
        var studentCanSetPreferredRecommender: List<Int>,
        @SerialName("internal_application_deadline")
        var internalApplicationDeadline: InternalApplicationDeadline,
        @SerialName("recommendation")
        var recommendation: Recommendation,
        @SerialName("old_student_dashboard")
        var oldStudentDashboard: List<Int>,
        @SerialName("time_tracking")
        var timeTracking: TimeTracking,
        @SerialName("invoicing")
        var invoicing: Invoicing,
        @SerialName("essay_outline_developer_view")
        var essayOutlineDeveloperView: List<Int>,
        @SerialName("district_wide_recommendation")
        var districtWideRecommendation: List<Int>
    ) {
        @Serializable
        data class IbTranscriptListing(
            @SerialName("9")
            var x9: X9,
            @SerialName("10")
            var x10: X10,
            @SerialName("11")
            var x11: X11,
            @SerialName("12")
            var x12: X12
        ) {
            @Serializable
            data class X9(
                @SerialName("subject")
                var subject: Subject,
                @SerialName("level")
                var level: Level,
                @SerialName("midyear")
                var midyear: Midyear,
                @SerialName("yearend")
                var yearend: Yearend,
                @SerialName("predicted")
                var predicted: Predicted,
                @SerialName("results")
                var results: Results
            ) {
                @Serializable
                data class Subject(
                    @SerialName("label")
                    var label: String
                )

                @Serializable
                data class Level(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Midyear(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Yearend(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Predicted(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Results(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )
            }

            @Serializable
            data class X10(
                @SerialName("subject")
                var subject: Subject,
                @SerialName("level")
                var level: Level,
                @SerialName("midyear")
                var midyear: Midyear,
                @SerialName("yearend")
                var yearend: Yearend,
                @SerialName("predicted")
                var predicted: Predicted,
                @SerialName("results")
                var results: Results
            ) {
                @Serializable
                data class Subject(
                    @SerialName("label")
                    var label: String
                )

                @Serializable
                data class Level(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Midyear(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Yearend(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Predicted(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Results(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )
            }

            @Serializable
            data class X11(
                @SerialName("subject")
                var subject: Subject,
                @SerialName("level")
                var level: Level,
                @SerialName("midyear")
                var midyear: Midyear,
                @SerialName("yearend")
                var yearend: Yearend,
                @SerialName("predicted")
                var predicted: Predicted,
                @SerialName("results")
                var results: Results
            ) {
                @Serializable
                data class Subject(
                    @SerialName("label")
                    var label: String
                )

                @Serializable
                data class Level(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Midyear(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Yearend(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Predicted(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Results(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )
            }

            @Serializable
            data class X12(
                @SerialName("subject")
                var subject: Subject,
                @SerialName("level")
                var level: Level,
                @SerialName("midyear")
                var midyear: Midyear,
                @SerialName("yearend")
                var yearend: Yearend,
                @SerialName("predicted")
                var predicted: Predicted,
                @SerialName("results")
                var results: Results
            ) {
                @Serializable
                data class Subject(
                    @SerialName("label")
                    var label: String
                )

                @Serializable
                data class Level(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Midyear(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Yearend(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Predicted(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )

                @Serializable
                data class Results(
                    @SerialName("label")
                    var label: String,
                    @SerialName("visibility")
                    var visibility: Int
                )
            }
        }

        @Serializable
        data class BulkUploadGpaSettings(
            @SerialName("update_gpa_field")
            var updateGpaField: Int,
            @SerialName("update_wgpa_field")
            var updateWgpaField: Int,
            @SerialName("gpa_search_string")
            var gpaSearchString: String,
            @SerialName("wgpa_search_string")
            var wgpaSearchString: String
        )

        @Serializable
        data class CurriculumPermission(
            @SerialName("enable_curriculum_school")
            var enableCurriculumSchool: Int,
            @SerialName("add_curriculum_course")
            var addCurriculumCourse: Int
        )

        @Serializable
        data class CurriculumPermissionSchool(
            @SerialName("allow_parents_to_view_sel_lessons")
            var allowParentsToViewSelLessons: Int,
            @SerialName("allow_parents_to_see_student_activities_in_the_lessons")
            var allowParentsToSeeStudentActivitiesInTheLessons: Int,
            @SerialName("allow_parents_to_see_submitted_activities_by_students")
            var allowParentsToSeeSubmittedActivitiesByStudents: Int
        )

        @Serializable
        data class GradeConvention(
            @SerialName("uk_convention")
            var ukConvention: Int,
            @SerialName("us_convention")
            var usConvention: Int
        )

        @Serializable
        data class InternalApplicationDeadline(
            @SerialName("config_choices")
            var configChoices: ConfigChoices,
            @SerialName("config_values")
            var configValues: ConfigValues
        ) {
            @Serializable
            data class ConfigChoices(
                @SerialName("internal_deadline_date")
                var internalDeadlineDate: Int,
                @SerialName("relative_deadline")
                var relativeDeadline: Int
            )

            @Serializable
            data class ConfigValues(
                @SerialName("relative_deadline_days")
                var relativeDeadlineDays: Int
            )
        }

        @Serializable
        data class Recommendation(
            @SerialName("default_listing")
            var defaultListing: DefaultListing
        ) {
            @Serializable
            data class DefaultListing(
                @SerialName("recommendation_letter_requests")
                var recommendationLetterRequests: Int,
                @SerialName("ucas_reference_letter")
                var ucasReferenceLetter: Int,
                @SerialName("both")
                var both: Int
            )
        }

        @Serializable
        data class TimeTracking(
            @SerialName("allow_time_tracking")
            var allowTimeTracking: Int
        )

        @Serializable
        data class Invoicing(
            @SerialName("allow_invoicing")
            var allowInvoicing: Int,
            @SerialName("company_logo")
            var companyLogo: String,
            @SerialName("default_currency")
            var defaultCurrency: String,
            @SerialName("default_message_email")
            var defaultMessageEmail: String,
            @SerialName("default_message_invoice")
            var defaultMessageInvoice: String,
            @SerialName("invoice_due_days")
            var invoiceDueDays: Int,
            @SerialName("rate")
            var rate: Int
        )
    }

    @Serializable
    data class MlUserDefaultConfigData(
        @SerialName("default_college_list_view")
        var defaultCollegeListView: List<String>
    )

    @Serializable
    data class CurriculumPermission(
        @SerialName("enable_curriculum_school")
        var enableCurriculumSchool: Int,
        @SerialName("add_curriculum_course")
        var addCurriculumCourse: Int
    )

    @Serializable
    data class MenuPermissions(
        @SerialName("Explore")
        var explore: String,
        @SerialName("Interest Profiler")
        var interestProfiler: String,
        @SerialName("Work Values")
        var workValues: String,
        @SerialName("Personality")
        var personality: String,
        @SerialName("Skills")
        var skills: String,
        @SerialName("Intelligences")
        var intelligences: String,
        @SerialName("Learning & Productivity")
        var learningProductivity: String,
        @SerialName("Career Plan")
        var careerPlan: String,
        @SerialName("Assessments")
        var assessments: String,
        @SerialName("Career Search")
        var careerSearch: String,
        @SerialName("Career List")
        var careerList: String,
        @SerialName("NYS Career Plan")
        var nYSCareerPlan: String,
        @SerialName("Academic Plan")
        var academicPlan: String,
        @SerialName("Academic Planner")
        var academicPlanner: String,
        @SerialName("Completed Courses")
        var completedCourses: String,
        @SerialName("Course Catalog")
        var courseCatalog: String,
        @SerialName("IB Transcript")
        var iBTranscript: String,
        @SerialName("College Plan")
        var collegePlan: String,
        @SerialName("College Search")
        var collegeSearch: String,
        @SerialName("Colleges Considering")
        var collegesConsidering: String,
        @SerialName("Colleges Applying")
        var collegesApplying: String,
        @SerialName("Compare Me")
        var compareMe: String,
        @SerialName("Request Recs")
        var requestRecs: String,
        @SerialName("College Visits")
        var collegeVisits: String,
        @SerialName("Test Scores")
        var testScores: String,
        @SerialName("Portfolio")
        var portfolio: String,
        @SerialName("Goals")
        var goals: String,
        @SerialName("Journals")
        var journals: String,
        @SerialName("Experiences")
        var experiences: String,
        @SerialName("Galleries")
        var galleries: String,
        @SerialName("Resume")
        var resume: String,
        @SerialName("MaiaDrive")
        var maiaDrive: String,
        @SerialName("Maia Docs")
        var maiaDocs: String,
        @SerialName("Send Summary")
        var sendSummary: String,
        @SerialName("Send Docs")
        var sendDocs: String,
        @SerialName("Reports")
        var reports: String,
        @SerialName("Career Exploration")
        var careerExploration: String,
        @SerialName("College Apps")
        var collegeApps: String,
        @SerialName("Scholarship")
        var scholarship: String,
        @SerialName("Engagement")
        var engagement: String,
        @SerialName("PortfolioSummary")
        var portfolioSummary: String,
        @SerialName("Scholarships")
        var scholarships: String,
        @SerialName("Notes")
        var notes: String,
        @SerialName("Thriving Index")
        var thrivingIndex: String,
        @SerialName("AGILE Cognitive")
        var aGILECognitive: String,
        @SerialName("Element X")
        var elementX: String,
        @SerialName("Admission Office")
        var admissionOffice: String
    )
}