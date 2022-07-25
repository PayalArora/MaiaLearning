package com.maialearning.repository

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.*
import com.maialearning.network.AllAPi
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
    suspend fun getInbox(): UseCaseResult<JsonObject>
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
    suspend fun getEthnicities(token: String,id: String)  :UseCaseResult<ArrayList<EthnicityResponseItem?>>
    suspend fun getRaces(token: String,id: String)  :UseCaseResult<ArrayList<RaceItem?>>
    suspend fun getImageURL(
        token: String,
        id: String,
        ext: String,
        schoolId: String
    ): UseCaseResult<JsonArray>

    suspend fun uploadImage(content:String,url: String, bode:RequestBody): UseCaseResult<Unit>
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
            print(ex.response()?.errorBody().toString())
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            print( ex.message)
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
        }
    }

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

    override suspend fun getStates(token: String, id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getStates(id, token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getEthnicities(
        token: String,
        id: String
    ): UseCaseResult<ArrayList<EthnicityResponseItem?>> {
        return try {
            val result = catApi.getEthnicities(id, token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getRaces(token: String, id: String): UseCaseResult<ArrayList<RaceItem?>> {
        return try {
            val result = catApi.getRaces(id,token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getImageURL(
        token: String,
        id: String,
        ext: String,
        schoolId: String
    ): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.updateProfImage(token, id, ext, schoolId).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun uploadImage(content:String,url: String, bode: RequestBody): UseCaseResult<Unit> {
        return try {
            val result = catApi.uploadImage(url,content, bode).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getJWTToken(): UseCaseResult<String> {
        return try {
            val result =
                catApi.getJWTToken("Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey)
                    .await()
            UseCaseResult.Success(result.get(0).toString().replace("\"", ""))
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getInbox(): UseCaseResult<JsonObject> {
        return try {
            BASE_URL=CAT_API_MSG_URL
//            val result = catApi.getInboxN(
//                CAT_API_MSG_URL + "${SharedHelper(BaseApplication.applicationContext()).messageId}/inbox",
//                "Bearer " +SharedHelper(BaseApplication.applicationContext()).jwtToken
//            ).await()
            val result = catApi.getInboxN(
                CAT_API_MSG_URL + "96c607b0-9a6c-4928-bd8c-8f332525fbe7/inbox",
                "Bearer " +SharedHelper(BaseApplication.applicationContext()).jwtToken
            ).await()
//            val result = catApi.getInbox(
//                SharedHelper(BaseApplication.applicationContext()).jwtToken,
//                 SharedHelper(BaseApplication.applicationContext()).messageId
//            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

}