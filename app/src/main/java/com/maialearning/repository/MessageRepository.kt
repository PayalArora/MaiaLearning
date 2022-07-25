package com.maialearning.repository

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.*
import com.maialearning.network.AllAPi
import com.maialearning.network.AllMessageAPi
import com.maialearning.network.BaseApplication
import com.maialearning.network.UseCaseResult
import com.maialearning.util.BASE_URL
import com.maialearning.util.CAT_API_MSG_URL
import com.maialearning.util.ORIGIN

import retrofit2.HttpException

import retrofit2.Response
import com.maialearning.util.prefhandler.SharedHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject

interface MessageRepository {
    // Suspend is used to await the result from Deferred

    suspend fun getInbox(): UseCaseResult<JsonObject>
    suspend fun getSent(): UseCaseResult<JsonObject>
    suspend fun getTrash(): UseCaseResult<JsonObject>
   }

class MessageRepositoryImpl(private val catApi: AllMessageAPi) : MessageRepository {

    override suspend fun getInbox(): UseCaseResult<JsonObject> {
        return try {
//            val result = catApi.getInboxN(
//                CAT_API_MSG_URL + "${SharedHelper(BaseApplication.applicationContext()).messageId}/inbox",
//                "Bearer " +SharedHelper(BaseApplication.applicationContext()).jwtToken
//            ).await()
//            val result = catApi.getInboxN(
//                CAT_API_MSG_URL + "96c607b0-9a6c-4928-bd8c-8f332525fbe7/inbox",
//                "Bearer " +SharedHelper(BaseApplication.applicationContext()).jwtToken
//            ).await()
            val result = catApi.getInbox(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiOTZjNjA3YjAtOWE2Yy00OTI4LWJkOGMtOGYzMzI1MjVmYmU3IiwiYWlkIjoiNGRmYmVhZGEtM2ZiYi00MmEwLWIxODQtZWRlZWNlYzcwNzFjIiwiaWF0IjoxNjU4NzQ2MTgyLCJleHAiOjE2NTg4MzI1ODJ9.bu7XXKFnr9dYRSBdVDRhXAPYvrzOKDOwfu_OUy9iaYY",
                "96c607b0-9a6c-4928-bd8c-8f332525fbe7"
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getSent(): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getSent(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiOTZjNjA3YjAtOWE2Yy00OTI4LWJkOGMtOGYzMzI1MjVmYmU3IiwiYWlkIjoiNGRmYmVhZGEtM2ZiYi00MmEwLWIxODQtZWRlZWNlYzcwNzFjIiwiaWF0IjoxNjU4NzQ2MTgyLCJleHAiOjE2NTg4MzI1ODJ9.bu7XXKFnr9dYRSBdVDRhXAPYvrzOKDOwfu_OUy9iaYY",
                "96c607b0-9a6c-4928-bd8c-8f332525fbe7"
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }
    override suspend fun getTrash(): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getTrash(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiOTZjNjA3YjAtOWE2Yy00OTI4LWJkOGMtOGYzMzI1MjVmYmU3IiwiYWlkIjoiNGRmYmVhZGEtM2ZiYi00MmEwLWIxODQtZWRlZWNlYzcwNzFjIiwiaWF0IjoxNjU4NzQ2MTgyLCJleHAiOjE2NTg4MzI1ODJ9.bu7XXKFnr9dYRSBdVDRhXAPYvrzOKDOwfu_OUy9iaYY",
                "96c607b0-9a6c-4928-bd8c-8f332525fbe7"
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }


}