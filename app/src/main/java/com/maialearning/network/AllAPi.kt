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
    ):  Deferred<LoginNewModel>

    @POST("azure_ad_oauth_login")
    @FormUrlEncoded
    fun microLoginAsync(
        @Header("origin") origin:String,
        @Field("token") token: String
    ):  Deferred<LoginNewModel>

 @GET("user_my_account_info/{id}")
    fun getProfile(
     @Header("Authorization") Authorization: String,
     @Path("id") id: String
    ):  Deferred<ProfileResponse>

    @POST("forgot_password")
    @FormUrlEncoded
    fun forgetPassAsync(
        @Field("email") email: String,
        @Field("type") type: String
    ):  Deferred<ForgetModel>

//    @GET("counselor_college/{id}?status={status}")
//    fun considerListAsync(
//        @Path("id")  id:String,
//        @Header("status") status: String
//    ):  Deferred<JSONObject>

    @GET("counselor_college/{id}?status=considering")
    fun considerListAsync(
        @Header("Authorization")  AutToken:String,
        @Path("id") id:String
    ):  Deferred<JsonObject>

    @GET("counselor_college/{id}?status=applying")
    fun applyListAsync(
        @Path("id")  id:String,
        @Header("Authorization")  AutToken:String
    ):  Deferred<JsonObject>

    @GET("get_notes_for_student/{id}")
    fun getNotes(
        @Path("id") id: String,
        @Header("Authorization") AutToken: String
    ): Deferred<NotesModel>

    @POST("edit-phone-values")
    @FormUrlEncoded
    fun updateSMSSetting(
        @Header("Authorization") AutToken: String,
        @Field("nid") n_id: String,
        @Field("phone_number") phone_no: String,
        @Field("phone_country_code") country_code: String,
        @Field("sms_enable") sms: String
    ): Deferred<JsonArray>

    @POST("user_my_account_info")
    fun updateEmail(
        @Header("Authorization") AutToken: String,
        @Body updateUserData: UpdateUserData
    ): Deferred<JsonArray>

    @GET("get_country_list")
    fun getCountries(
        @Header("Authorization") Authorization: String
    ): Deferred<JsonObject>

    @GET("get-state-list-country/{id}")
    fun getStates(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Deferred<JsonObject>

    @GET("get_jwt_token")
    fun getJWTToken(
        @Header("Authorization")  AutToken:String
    ):  Deferred<JsonArray>

//    @GET("{id}/inbox")
//    fun getInbox(
//        @Header("x-access-token")  JwtToken:String,
//        @Path("id")  id:String,
//    ):  Deferred<InboxResponse>

    @GET
    fun getInboxN(@Url url: String,
                 @Header("x-access-token")  JwtToken:String,
    ):  Deferred<JsonObject>

    @GET("{id}/inbox")
    fun getInbox(
        @Header("x-access-token")  AutToken:String,
        @Path("id") id:String
    ):  Deferred<JsonObject>


    @GET("school_wide_configuration/field_ethnicity_config/{id}")
    fun getEthnicities(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Deferred< ArrayList<EthnicityResponseItem?>>

    @GET("school_wide_configuration/field_race_config/{id}")
    fun getRaces(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Deferred< ArrayList<RaceItem?>>

    @POST("get_profile_picure_update_presigned_url")
    @FormUrlEncoded
    fun updateProfImage(
        @Header("Authorization") AutToken: String,
        @Field("uid") n_id: String,
        @Field("ext") ext: String,
        @Field("school_id") schoolId: String
    ): Deferred<JsonArray>

    @PUT
    fun uploadImage(
        @Url() url: String,
        @Header("Content-Type") content: String,
        @Body body:RequestBody): Deferred<Unit>

    @GET("student-assignment-dashboard/{id}")
    fun getOverDueCompleted(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Deferred<DashboardOverdueResponse>

    @GET
    fun getColFactSheet(
        @Url() url: String,

    ): Deferred<JsonObject>

    @GET("college_nid_by_unitid/{id}")
    fun getCollegeNid(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Deferred<JsonObject>

    @GET("university-contact-student/{id}")
    fun universityContacts(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Deferred<CollegeContactModel>

    @GET("counselor_college_notes/{id}/{cid}")
    fun universityNotes(
        @Path("id") id: String,
        @Path("cid") cid: String,
        @Header("Authorization") Authorization: String
    ): Deferred<JsonObject>


    @POST("edit_student_plan_college_application")
    fun editStudentPlan(
        @Header("Authorization") Authorization: String,
        @Body updateStudentPlan: UpdateStudentPlan
    ): Deferred<JsonObject>

    @POST("un_aws_cloud_search")
    fun searchUniversties(
        @Header("Authorization") Authorization: String,
        @Body payload: UniversitySearchPayload
    ): Deferred<JsonObject>

    @POST("top-picks")
    @FormUrlEncoded
    fun hitLikeUniversity(
        @Header("Authorization") Authorization: String,
        @Field("student_id") schoolId: String,
        @Field("college_id") collegeId: String,
        @Field("applying_flag") ext: String
    ): Deferred<JsonArray>

    @DELETE("top-picks/{schoolId}/{studentId}")
    fun hitUnlikeUniversity(
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
    fun moveToApplying(
        @Header("Authorization") Authorization: String,
        @Field("student_uid") schoolId: String,
        @Field("college_nid") collegeId: String,
        @Field("apply_status") ext: String
    ): Deferred<JsonArray>

    @POST("ml-add-program-by-trans-nid")
    fun addProgramToConsidering(
        @Header("Authorization") Authorization: String,
        @Body payload: AddProgramConsider
    ): Deferred<JsonObject>

    @DELETE("ml-fav-program/{id}")
    fun deleteMlProgram(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String,
    ): Deferred<JsonArray>

    @GET
    fun getSurveys(
        @Url() url: String,
        @Header("Authorization") Authorization: String
    ): Deferred<SurveysResponse>
    @GET
    fun getWebinars(
        @Url() url: String,
        @Header("Authorization") Authorization: String
    ): Deferred<WebinarResponse>

    @POST("get_documents_download_presigned_url")
    @FormUrlEncoded
    fun downloadWorkSheet( @Header("Authorization") AutToken: String,
                           @Header("origin") origin: String,
                           @Header("referer") referer: String,
                           @Field("student_uid") uuid: String,
                           @Field("file_id") fileid: String,
                           @Field("document_type") docType: String): Deferred<JsonArray>

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
        @Field("user_type") type: String): Deferred<JsonArray>


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
    @GET("recommendation_without_college/{id}")
    fun getRecomType(
        @Header("Authorization") AutToken: String,
        @Path("id") id: String,
        ): Deferred<JsonArray>


}