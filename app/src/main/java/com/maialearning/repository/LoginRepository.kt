package com.maialearning.repository

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.*
import com.maialearning.network.AllAPi
import com.maialearning.network.BaseApplication
import com.maialearning.network.UseCaseResult
import com.maialearning.util.CAT_API_BASE_URL
import com.maialearning.util.CAT_API_MSG_URL
import com.maialearning.util.ORIGIN

import retrofit2.HttpException

import retrofit2.Response
import com.maialearning.util.prefhandler.SharedHelper
import org.json.JSONArray
import org.json.JSONObject

interface LoginRepository {
    // Suspend is used to await the result from Deferred
    suspend fun getUserLogin(username: String, password: String): UseCaseResult<LoginNewModel>
    suspend fun getGoogleLogin(
        email: String,
        id: String,
        id_token: String
    ): UseCaseResult<LoginNewModel>

    suspend fun getMicroLogin(token: String): UseCaseResult<LoginNewModel>
    suspend fun getForgetPassword(email: String): UseCaseResult<ForgetModel>

    suspend fun getUserProfile(id_token: String, id: String): UseCaseResult<ProfileResponse>
    suspend fun getConsiderList(id: String): UseCaseResult<JsonObject>
    suspend fun getNotes(id: String): UseCaseResult<NotesModel>
    suspend fun getApplyList(id: String): UseCaseResult<JsonObject>
    suspend fun getJWTToken(): UseCaseResult<String>
    suspend fun getInbox(): UseCaseResult<InboxResponse>
    suspend fun updateSmsNotification(
        token: String,
        id: String,
        ph: String,
        code: String,
        sms: String
    ): UseCaseResult<String>

  suspend  fun updateEmail(token: String, userData: UpdateUserData) :UseCaseResult<String>
   suspend fun getCountries(token: String)  :UseCaseResult<JsonObject>
    suspend fun getStates(token: String,id: String)  :UseCaseResult<JsonObject>

}

class LoginRepositoryImpl(private val catApi: AllAPi) : LoginRepository {


    override suspend fun getUserLogin(
        username: String,
        password: String
    ): UseCaseResult<LoginNewModel> {
        return try {
            val result = catApi.userLoginAsync(username, password).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            println(ex.stackTrace)
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            println(ex.stackTraceToString())
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getGoogleLogin(
        email: String,
        id: String,
        id_token: String
    ): UseCaseResult<LoginNewModel> {
        return try {
            val result = catApi.googleLoginAsync(ORIGIN, email, id, id_token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            println(ex.stackTraceToString())
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getMicroLogin(token: String): UseCaseResult<LoginNewModel> {
        return try {
            val result = catApi.microLoginAsync(ORIGIN, token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getForgetPassword(email: String): UseCaseResult<ForgetModel> {
        return try {
            val result = catApi.forgetPassAsync(email, "email").await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getUserProfile(
        id_token: String,
        id: String,
    ): UseCaseResult<ProfileResponse> {
        return try {
            val result = catApi.getProfile(id_token, id).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getConsiderList(id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.considerListAsync("Bearer "   + SharedHelper(BaseApplication.applicationContext()).authkey,"9375").await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getNotes(id: String): UseCaseResult<NotesModel> {
        return try {
            val result = catApi.getNotes(
                "9375",
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getApplyList(id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.applyListAsync(
                "9375",
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun updateSmsNotification(
        token: String,
        id: String,
        ph: String,
        code: String,
        sms: String
    ): UseCaseResult<String> {
        return try {
            val result = catApi.updateSMSSetting(token, id, ph, code, sms).await()
            UseCaseResult.Success(result.toString())
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun updateEmail(token: String, updateUserData:UpdateUserData): UseCaseResult<String> {
        return try {
            val result = catApi.updateEmail(token, updateUserData).await()
            UseCaseResult.Success(result.toString())
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }    }

    override suspend fun getCountries(token: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getCountries(token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }
    override suspend fun getStates(token: String,id:String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getStates(id,token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getJWTToken(): UseCaseResult<String> {
        return try {
            val result = catApi.getJWTToken("Bearer "+ SharedHelper(BaseApplication.applicationContext()).authkey).await()
            UseCaseResult.Success(result.get(0).toString().replace("\"", ""))
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        }catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getInbox(): UseCaseResult<InboxResponse> {
        return try {
            val result = catApi.getInbox( CAT_API_MSG_URL+"${SharedHelper(BaseApplication.applicationContext()).messageId}/inbox"
                ,SharedHelper(BaseApplication.applicationContext()).jwtToken).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        }catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

}