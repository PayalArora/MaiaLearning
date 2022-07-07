package com.maialearning.network

import com.maialearning.model.ForgetModel

import com.maialearning.model.LoginNewModel
import com.maialearning.model.ProfileResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*
//https://maia2-staging-backend.maialearning.com/ajs-services/google_login
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

 @GET("user_my_account_info/{id}")
    @FormUrlEncoded
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
}