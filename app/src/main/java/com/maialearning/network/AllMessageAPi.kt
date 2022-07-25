package com.maialearning.network

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.*
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface AllMessageAPi {

    @GET
    fun getInboxN(@Url url: String,
                 @Header("x-access-token")  JwtToken:String,
    ):  Deferred<JsonObject>

    @GET("v1/messaging/users/{id}/inbox")
    fun getInbox(
        @Header("x-access-token")  AutToken:String,
        @Path("id") id:String
    ):  Deferred<JsonObject>

    @GET("v1/messaging/users/{id}/sent")
    fun getSent(
        @Header("x-access-token")  AutToken:String,
        @Path("id") id:String
    ):  Deferred<JsonObject>
    @GET("v1/messaging/users/{id}/trash")
    fun getTrash(
        @Header("x-access-token")  AutToken:String,
        @Path("id") id:String
    ):  Deferred<JsonObject>

    @GET("get_jwt_token")
    fun getJWTToken(
        @Header("Authorization")  AutToken:String
    ):  Deferred<JsonArray>

}