package com.maialearning.network

import com.maialearning.model.ForgetModel

import com.maialearning.model.LoginNewModel
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
}