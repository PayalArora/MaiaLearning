package com.maialearning.repository

import com.google.gson.JsonObject
import com.maialearning.network.AllMessageAPi
import com.maialearning.network.BaseApplication
import com.maialearning.network.UseCaseResult

import retrofit2.HttpException

import com.maialearning.util.prefhandler.SharedHelper
import org.json.JSONObject

interface MessageRepository {
    // Suspend is used to await the result from Deferred

    suspend fun getInbox(): UseCaseResult<JsonObject>
    suspend fun getSent(): UseCaseResult<JsonObject>
    suspend fun getTrash(): UseCaseResult<JsonObject>
    suspend fun getMessage(id:String): UseCaseResult<JsonObject>
    suspend fun delMessage(id:String): UseCaseResult<JsonObject>
    suspend fun createMessage(id: JSONObject): UseCaseResult<JsonObject>
   }

class MessageRepositoryImpl(private val catApi: AllMessageAPi) : MessageRepository {

    override suspend fun getInbox(): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getInboxAsync(
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
            val result = catApi.getSentAsync(
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
            val result = catApi.getTrashAsync(
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
    override suspend fun getMessage(id:String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getMessageAsync(
                SharedHelper(BaseApplication.applicationContext()).jwtToken,
                id

            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }
    override suspend fun delMessage(id:String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.delMessageAsync(
                SharedHelper(BaseApplication.applicationContext()).jwtToken,
                id

            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun createMessage(id: JSONObject): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.createMessage(
                SharedHelper(BaseApplication.applicationContext()).jwtToken,"application/json; charset=utf-8",
                id

            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }


}