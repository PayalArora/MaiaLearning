package com.maialearning.network

import com.maialearning.model.Consider
import com.maialearning.model.ForgetModel

import com.maialearning.model.LoginNewModel
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

    @GET("counselor_college/{id}")
    fun considerListAsync(
        @Path("id")  id:String,
        @Query("status") status: String
    ):  Deferred<Consider>

    @GET("counselor_college/{id}")
    fun applyListAsync(
        @Path("id")  id:String,
        @Query("status") status: String
    ):  Deferred<ForgetModel>
}