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
                "Bearer " +  SharedHelper(BaseApplication.applicationContext()).authkey,
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