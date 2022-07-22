package com.maialearning.network

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.*
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface AllAPi {

    @POST("user/sign-in")
    @FormUrlEncoded
    fun userLoginAsync(
        @Field("username") username: String,
        @Field("password") password: String
    ):  Deferred<LoginNewModel>


    @POST("google_login")
    @FormUrlEncoded
    fun googleLoginAsync(
        @Header("origin") origin:String,
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
    fun getInbox(@Url url: String,
                 @Header("x-access-token")  JwtToken:String,
    ):  Deferred<InboxResponse>

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
}