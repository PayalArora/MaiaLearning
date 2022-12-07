package com.maialearning.network

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.*
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.http.*


interface AllAPi {

    @POST("user/sign-in")
    @FormUrlEncoded
    fun userLoginAsync(
        @Field("username") username: String,
        @Field("password") password: String
    ): Deferred<LoginNewModel>


    @POST("google_login")
    @FormUrlEncoded
    fun googleLoginAsync(
        @Header("origin") origin: String,
        @Field("email") email: String,
        @Field("id") id: String,
        @Field("id_token") id_token: String
    ): Deferred<LoginNewModel>

    @POST("azure_ad_oauth_login")
    @FormUrlEncoded
    fun microLoginAsync(
        @Header("origin") origin: String,
        @Field("token") token: String
    ): Deferred<LoginNewModel>

    @GET("user_my_account_info/{id}")
    fun getProfileAsync(
        @Header("Authorization") Authorization: String,
        @Path("id") id: String
    ): Deferred<ProfileResponse>

    @POST("forgot_password")
    @FormUrlEncoded
    fun forgetPassAsync(
        @Field("email") email: String,
        @Field("type") type: String
    ): Deferred<ForgetModel>

//    @GET("counselor_college/{id}?status={status}")
//    fun considerListAsync(
//        @Path("id")  id:String,
//        @Header("status") status: String
//    ):  Deferred<JSONObject>

    @GET("counselor_college/{id}")
    fun considerListAsync(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String,
        @Query("status") status: String
    ): Deferred<JsonObject>

    @GET("counselor_college/{id}?status=applying")
    fun applyListAsync(
        @Path("id") id: String,
        @Header("Authorization") AutToken: String
    ): Deferred<JsonObject>

    @GET("get_notes_for_student/{id}")
    fun getNotesAsync(
        @Path("id") id: String,
        @Header("Authorization") AutToken: String
    ): Deferred<NotesModel>

    @POST("edit-phone-values")
    @FormUrlEncoded
    fun updateSMSSettingAsync(
        @Header("Authorization") AutToken: String,
        @Field("nid") n_id: String,
        @Field("phone_number") phone_no: String,
        @Field("phone_country_code") country_code: String,
        @Field("sms_enable") sms: String
    ): Deferred<JsonArray>

    @POST("user_my_account_info")
    fun updateEmailAsync(
        @Header("Authorization") AutToken: String,
        @Body updateUserData: UpdateUserData
    ): Deferred<JsonArray>

    @GET("get_country_list")
    fun getCountriesAsync(
        @Header("Authorization") Authorization: String
    ): Deferred<JsonObject>

    @GET("get-state-list-country/{id}")
    fun getStatesAsync(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Deferred<JsonObject>

    @GET("get_jwt_token")
    fun getJWTTokenAsync(
        @Header("Authorization") AutToken: String
    ): Deferred<JsonArray>

//    @GET("{id}/inbox")
//    fun getInbox(
//        @Header("x-access-token")  JwtToken:String,
//        @Path("id")  id:String,
//    ):  Deferred<InboxResponse>

    @GET
    fun getInboxNAsync(
        @Url url: String,
        @Header("x-access-token") JwtToken: String,
    ): Deferred<JsonObject>

    @GET("{id}/inbox")
    fun getInbox(
        @Header("x-access-token") AutToken: String,
        @Path("id") id: String
    ): Deferred<JsonObject>


    @GET("school_wide_configuration/field_ethnicity_config/{id}")
    fun getEthnicitiesAsync(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Deferred<ArrayList<EthnicityResponse.EthnicityResponseItem?>>

    @GET("school_wide_configuration/field_race_config/{id}")
    fun getRacesAsync(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Deferred<ArrayList<RaceItem?>>

    @POST("get_profile_picure_update_presigned_url")
    @FormUrlEncoded
    fun updateProfImageAsync(
        @Header("Authorization") AutToken: String,
        @Field("uid") n_id: String,
        @Field("ext") ext: String,
        @Field("school_id") schoolId: String
    ): Deferred<JsonArray>

    @PUT
    fun uploadImageAsync(
        @Url() url: String,
        @Header("Content-Type") content: String,
        @Body body: RequestBody
    ): Deferred<Unit>

    @GET("student-assignment-dashboard/{id}")
    fun getOverDueCompletedAsync(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Deferred<DashboardOverdueResponse>

    @GET
    fun getColFactSheetAsync(
        @Url() url: String,
    ): Deferred<JsonObject>

    @GET
    fun getColFactSheetOtherAsync(
        @Url() url: String,
        @Header("Authorization") Authorization: String
    ): Deferred<FactsheetModelOther>

    @GET("college_nid_by_unitid/{id}")
    fun getCollegeNidAsync(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Deferred<JsonObject>

    @GET("university-contact-student/{id}")
    fun universityContactsAsync(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Deferred<CollegeContactModel>

    @GET("counselor_college_notes/{id}/{cid}")
    fun universityNotesAsync(
        @Path("id") id: String,
        @Path("cid") cid: String,
        @Header("Authorization") Authorization: String
    ): Deferred<JsonObject>


    @POST("edit_student_plan_college_application")
    fun editStudentPlanAsync(
        @Header("Authorization") Authorization: String,
        @Body updateStudentPlan: UpdateStudentPlan
    ): Deferred<JsonObject>

    @POST("un_aws_cloud_search")
    fun searchUniverstiesAsync(
        @Header("Authorization") Authorization: String,
        @Body payload: UniversitySearchPayload
    ): Deferred<JsonObject>

    @POST("unicas_aws_cloud_search")
    fun euroUniverstiesAsync(
        @Header("Authorization") Authorization: String,
        @Body payload: UniversitySearchPayload
    ): Deferred<JsonObject>

    @POST("german_data_search")
    fun germanUniverstiesAsync(
        @Header("Authorization") Authorization: String,
        @Body payload: UniversitySearchPayload
    ): Deferred<JsonObject>

    @POST("ucas_aws_cloud_search")
    fun ukUniversitiesAsync(
        @Header("Authorization") Authorization: String,
        @Body payload: UniversitySearchPayload
    ): Deferred<JsonObject>

    @POST("top-picks")
    @FormUrlEncoded
    fun hitLikeUniversityAsync(
        @Header("Authorization") Authorization: String,
        @Field("student_id") schoolId: String,
        @Field("college_id") collegeId: String,
        @Field("applying_flag") ext: String
    ): Deferred<JsonArray>

    @DELETE("top-picks/{schoolId}/{studentId}")
    fun hitUnlikeUniversityAsync(
        @Path("schoolId") schoolId: String,
        @Path("studentId") studentId: String,
        @Header("Authorization") Authorization: String
    ): Deferred<Unit>


    @DELETE("top-picks/{schoolId}/{studentId}?new_ui=1")
    fun hitDeleteUniversityConsideringAsync(
        @Path("schoolId") schoolId: String,
        @Path("studentId") studentId: String,
        @Header("Authorization") Authorization: String
    ): Deferred<Unit>

    //  https://maia2-staging-backend.maialearning.com/ajs-services/top-picks/402359/9375?new_ui=1
    @DELETE("top-picks/{schoolId}/{studentId}?new_ui=1")
    fun hitDeleteUniversityConsidering(
        @Path("schoolId") schoolId: String,
        @Path("studentId") studentId: String,
        @Header("Authorization") Authorization: String
    ): Deferred<Unit>

    @POST("college_list_applying")
    @FormUrlEncoded
    fun moveToApplyingAsync(
        @Header("Authorization") Authorization: String,
        @Field("student_uid") schoolId: String,
        @Field("college_nid") collegeId: String,
        @Field("apply_status") ext: String
    ): Deferred<JsonArray>

    @POST("ml-add-program-by-trans-nid")
    fun addProgramToConsideringAsync(
        @Header("Authorization") Authorization: String,
        @Body payload: AddProgramConsider
    ): Deferred<JsonObject>

    @DELETE("ml-fav-program/{id}")
    fun deleteMlProgramAsync(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String,
    ): Deferred<JsonArray>

    @GET("university-list")
    fun getUniversityListAsync(
        @Header("Authorization") Authorization: String,
        @Query("status") status: String,
        @Query("uid") uid: String
    ): Deferred<JsonArray>


    @GET
    fun getSurveysAsync(
        @Url() url: String,
        @Header("Authorization") Authorization: String
    ): Deferred<SurveysResponse>

    @GET
    fun getWebinarsAsync(
        @Url() url: String,
        @Header("Authorization") Authorization: String
    ): Deferred<WebinarResponse>

    @POST("get_documents_download_presigned_url")
    @FormUrlEncoded
    fun downloadWorkSheet(
        @Header("Authorization") AutToken: String,
        @Header("origin") origin: String,
        @Header("referer") referer: String,
        @Field("student_uid") uuid: String,
        @Field("file_id") fileid: String,
        @Field("document_type") docType: String
    ): Deferred<JsonArray>

    @POST("add-student-response-activity")
    @FormUrlEncoded
    fun writeToCounselor(
        @Header("Authorization") AutToken: String,
        @Field("nid") n_id: String,
        @Field("response") ext: String,
    ): Deferred<JsonArray>

    @POST("fm_user_file/presigned_url")
    @FormUrlEncoded
    fun uploadAttachmentFile(
        @Header("Authorization") AutToken: String,
        @Field("filename") n_id: String,
        @Field("fm_uid") ext: String
    ): Deferred<JsonObject>

    @POST("fm_user_file")
    fun updateFileAttachData(
        @Header("Authorization") AutToken: String,
        @Body updateUserData: FileUploadData
    ): Deferred<JsonObject>

    @GET("fm_tag")
    fun getTags(
        @Header("Authorization") AutToken: String
    ): Deferred<ArrayList<FmTagsResponseItem?>>

    @POST
    fun checkFileVirus(
        @Url() url: String,
        @Header("Authorization") AutToken: String,
        @Body file: JsonObject
    ): Deferred<JsonObject>

    @PUT("itask/{itask_nid}/{student_uid}")
    fun completeTask(
        @Header("Authorization") AutToken: String,
        @Path("itask_nid") nid: String,
        @Path("student_uid") uid: String,
    ): Deferred<JsonArray>

    @GET("uncheck-itask-student/{student_uid}")
    fun resetTaskCompleteion(
        @Header("Authorization") AutToken: String,
        @Path("student_uid") uid: String,
    ): Deferred<JsonArray>

    @GET("get_allowed_values_list/field_status")
    fun getDecsionStatuses(
        @Header("Authorization") AutToken: String
    ): Deferred<JsonObject>

    @POST("get_message_center_recipients_for_students")
    @FormUrlEncoded
    fun getRecipients(
        @Header("Authorization") AutToken: String,
        @Field("school_id") n_id: String,
        @Field("user_type") type: String
    ): Deferred<JsonArray>


    @GET("get-teacher-counselor-list/{id}?for_reco=1")
    fun getTeacherList(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String,
    ): Deferred<JsonArray>

    @POST("recommendation-request")
    fun sendRecomTeacher(
        @Header("Authorization") AutToken: String,
        @Body recModel: RecModel

    ): Deferred<JsonArray>

    @POST("req-ucas-ref-letter")
    fun sendUcasRec(
        @Header("Authorization") AutToken: String,
        @Body recModel: RecModel
    ): Deferred<JsonArray>

    @GET("get_recommendation_deadline")
    fun getRecDeadline(
        @Header("Authorization") AutToken: String,
    ): Deferred<JsonArray>

    @GET("wc-recommendation-for-student/{id}/{page}")
    fun getRecomders(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String,
        @Path("page") page: String,
    ): Deferred<RecomdersModel>

    @GET("recommendation-for-student/{id}/{page}")
    fun getRecomdersCollege(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String,
        @Path("page") page: String,
    ): Deferred<JsonObject>

    @DELETE("recommendation-request/{id}")
    fun cancelRecommendationRequest(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String
    ): Deferred<Unit>

    @GET("recommendation_without_college/{id}")
    fun getRecomType(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String,
    ): Deferred<JsonArray>

    @POST("get_documents_presigned_url")
    @FormUrlEncoded
    fun getDocumetPresignedURl(
        @Header("Authorization") AutToken: String,
        @Field("filename") name: String,
        @Field("student_uid") uID: String,
        @Field("document_type") doctype: String,
        @Field("filehash") fileHash: String
    ): Deferred<JsonObject>

    @PUT
    fun uploadDoc(
        @Url() url: String
    ): Deferred<Unit>

    @POST("student-brag-sheet")
    @FormUrlEncoded
    fun saveDocumentBragSheet(
        @Header("Authorization") AutToken: String,
        @Field("student_uid") id: String,
        @Field("filename") name: String,
        @Field("path") path: String,
        @Field("exist") exist: Int,
        @Field("s3_url") url: String,
        @Field("filehash") fileHash: String
    ): Deferred<Unit>

    @GET("get-student-colleges/{id}?for_reco=1")
    fun getUniversities(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String
    ): Deferred<JsonObject>

    @GET("get-itask-category/1/")
    fun getMilestonesID(
        @Header("Authorization") AutToken: String
    ): Deferred<JsonObject>

    @GET("get_category_wise_itask/1/{studentId}/{milestoneID}")
    fun getMilestones(
        @Header("Authorization") AutToken: String,
        @Path("studentId") studentId: String,
        @Path("milestoneID") milestoneID: String
    ): Deferred<MilestoneResponse>

    @POST("get_documents_download_presigned_url")
    @FormUrlEncoded
    fun downloadBragSheet(
        @Header("Authorization") AutToken: String,
        @Field("student_uid") uuid: String,
        @Field("file_id") fileid: String
    ): Deferred<JsonArray>

    @GET("student-brag-sheet/{id}")
    fun getBragSheet(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String
    ): Deferred<JsonObject>

    @PUT("itask/{id}/{studentId}")
    fun checkItask(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String,
        @Path("studentId") studentId: String
    ): Deferred<Unit>

    @GET("uncheck-itask-student/{id}")
    fun uncheckItask(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String
    ): Deferred<Unit>

    @POST("upload-reco-brag-sheet")
    @FormUrlEncoded
    fun uploadRecoBragSheet(
        @Header("Authorization") AutToken: String,
        @Field("rec_id") id: String,
        @Field("filename") name: String,
        @Field("path") path: String,
        @Field("filehash") fileHash: String
    ): Deferred<Unit>

    @POST
    fun getCollegeJsonFilter(
        @Url() url: String,
        @Header("Authorization") AutToken: String,
        @Body univId: UnivCollegeModel
        //@Query("") univId: UnivCollegeModel
    ): Deferred<JsonObject>

    @GET("get_student_career_top_picks/{id}")
    fun getCareerTopPicks(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String
    ): Deferred<JsonArray>

    @GET
    fun getCareerTopPicksDetails(
        @Url() url: String,
    ): Deferred<JsonObject>

    @GET
    fun getKeyboardSearch(
        @Url() url: String,
        @Header("origin") origin: String,
        @Header("accept") accept: String,
    ): Deferred<CareerSearchCodesModel>

    @GET
    fun getCareerCluster(
        @Url() url: String,
        @Header("origin") origin: String,
        @Header("accept") accept: String,
    ): Deferred<CareerClusterModel>

    @GET
    fun getCareerClusterList(
        @Url() url: String,
        @Header("origin") origin: String,
        @Header("accept") accept: String,
    ): Deferred<CareerClusterListModel>

    @GET
    fun getIndustryList(
        @Url() url: String,
        @Header("origin") origin: String,
        @Header("accept") accept: String,
    ): Deferred<IndustryListModel>

    @POST("career_search_bright_outlook")
    fun getCareerBright(
        @Header("Authorization") AutToken: String,
        @Body model: BrightLook,
    ): Deferred<BrightOutlookModel>

    @POST("career_search_onet")
    fun getCareerSearchList(
        @Header("Authorization") AutToken: String,
        @Body model: CareerListModel,
    ): Deferred<ArrayList<BrightOutlookModel.Data>>

    @POST
    fun getKeywoardSearchDetails(
        @Url url: String,
        @Header("origin") origin: String,
        @Header("accept") accept: String,
        @Body model: SearchkeywordRequestModel
    ): Deferred<JsonArray>

    @GET
    fun getWorkList(
        @Header("Authorization") AutToken: String,
        @Url() url: String
    ): Deferred<JsonObject>

    @GET
    fun getUsList(
        @Header("Authorization") AutToken: String,
        @Url() url: String
    ): Deferred<CareerUSModel>

    @GET("newyork_career_plan/{id}")
    fun getNYSCareerPlan(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String
    ): Deferred<JsonObject>

    @GET("get_student_career_plan/{id}")
    fun getStudentCareer_Plan(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String
    ): Deferred<JsonObject>

    @GET("un_aws_search_filters")
    fun getcountryFilterAsync(
    ): Deferred<JsonObject>

    @GET("get_default_country/{id}")
    fun getSaveCountryAsync(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String
    ): Deferred<JsonArray>

    @POST("save_default_country")
    fun setSaveCountryAsync(
        @Header("Authorization") AutToken: String,
        @Body model: SaveCountryModel
    ): Deferred<JsonArray>


    @POST("career_search_compare/")
    fun compareCareers(
        @Header("Authorization") AutToken: String,
        @Body univId: CompareCareerBody
    ): Deferred<JsonObject>

    @POST("get_country_based_on_continent")
    @FormUrlEncoded
    fun getCountriesContinentBased(
        @Header("Authorization") AutToken: String,
        @Field("continent_code") code: String
    ): Deferred<JsonObject>

    @GET
    fun getVideoCode(
        @Url() url: String,
    ): Deferred<JsonObject>

    @GET("mre_activity/{id}")
    fun getExperiences(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String
    ): Deferred<JsonArray>

    @GET("get-student-career-toppick-new")
    fun getStudentTopPick(
        @Header("Authorization") AutToken: String,
        @Query("student_id") id: String
    ): Deferred<JsonArray>

    @POST("create-content")
    fun createContent(
        @Header("Authorization") AutToken: String,
        @Body model: CreateContent,
    ): Deferred<JsonObject>

    @POST("delete-node")
    fun deleteContent(
        @Header("Authorization") AutToken: String,
        @Body model: DeleteContent,
    ): Deferred<JsonObject>

    @GET("ucas_course_details/{id}")
    fun getProgramListDetail(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String
    ): Deferred<CourseModelOptionDetailResponse>

    //    page=1&sort_by=essay_colleges&sort_order=asc&uid=9375
    // ?page={page}&sort_by={sort_by}&sort_order={sort_order}&uid={id}
    @GET("college_essay")
    fun getCollegeEssay(
        @Header("Authorization") AutToken: String,
        @Query("uid") id: String,
        @Query("page") page: String,
        @Query("sort_by") sort_by: String,
        @Query("sort_order") sort_order: String
    ): Deferred<CollegeEssayResponse>

    @DELETE("college_essay/{id}")
    fun deleteCollegeEssay(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String
    ): Deferred<Unit>

    @POST("get_unicas_sub_discipline")
    @FormUrlEncoded
    fun getSubDiscipline(
        @Header("Authorization") AutToken: String,
        @Field("discipline_id") id: String
    ): Deferred<JsonObject>

    @POST("get_german_child_subject_levels")
    @FormUrlEncoded
    fun getSubChildSubject(
        @Header("Authorization") AutToken: String,
        @Field("subject_code") id: String
    ): Deferred<JsonObject>

    @POST("get_ucas_child_Subject_levels")
    @FormUrlEncoded
    fun getGBSubChildSubject(
        @Header("Authorization") AutToken: String,
        @Field("subject_code") id: String
    ): Deferred<JsonObject>

    @GET
    fun getApplyingWith(
        @Url() url: String
    ): Deferred<JsonObject>

    @POST("bulk-college-moving")
    fun bulkCollegeMoving(
        @Header("Authorization") AutToken: String,
        @Body payload: BulkCollegeMovePayload
    ): Deferred<Unit>

    @GET("test-score-submission-status/{id}")
    fun getTestScores(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String
    ): Deferred<JsonArray>

    @POST("test-score-submission-status")
    fun testScoreSubmit(
        @Header("Authorization") AutToken: String,
        @Body payload: TestScoreSubmitPayload
    ): Deferred<Unit>
}