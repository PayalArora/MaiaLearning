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
            val result = catApi.getInbox(
                SharedHelper(BaseApplication.applicationContext()).jwtToken,
                SharedHelper(BaseApplication.applicationContext()).messageId

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
                SharedHelper(BaseApplication.applicationContext()).jwtToken,
                SharedHelper(BaseApplication.applicationContext()).messageId

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
                SharedHelper(BaseApplication.applicationContext()).jwtToken,
                SharedHelper(BaseApplication.applicationContext()).messageId

            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }


}