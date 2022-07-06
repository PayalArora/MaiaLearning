package com.maialearning.network

import com.google.gson.JsonObject
import com.maialearning.model.Consider
import com.maialearning.model.ForgetModel

import com.maialearning.model.LoginNewModel
import com.maialearning.model.NotesModel
import com.maialearning.util.prefhandler.SharedHelper
import kotlinx.coroutines.Deferred
import org.json.JSONObject
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
        @Field("email") email: String,
        @Field("id") id: String,
        @Field("id_token") id_token: String
    ):  Deferred<LoginNewModel>

    @POST("azure_ad_oauth_login")
    @FormUrlEncoded
    fun microLoginAsync(
        @Field("token") id_token: String
    ):  Deferred<LoginNewModel>

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
        @Path("id")  id:String,
        @Header("Authorization")  AutToken:String
    ):  Deferred<NotesModel>
}