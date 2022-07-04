package com.maialearning.repository

import com.maialearning.model.ForgetModel
import com.maialearning.model.LoginNewModel
import com.maialearning.network.AllAPi
import com.maialearning.network.UseCaseResult

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
}

class LoginRepositoryImpl(private val catApi: AllAPi) : LoginRepository {


    override suspend fun getUserLogin(
        username: String,
        password: String
    ): UseCaseResult<LoginNewModel> {
        return try {
            val result = catApi.userLoginAsync(username, password).await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

    override suspend fun getGoogleLogin(
        email: String,
        id: String,
        id_token: String
    ): UseCaseResult<LoginNewModel> {
        return try {
            val result = catApi.googleLoginAsync(email, id, id_token).await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

    override suspend fun getMicroLogin(token: String): UseCaseResult<LoginNewModel> {
        return try {
            val result = catApi.microLoginAsync(token).await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

    override suspend fun getForgetPassword(email: String): UseCaseResult<ForgetModel> {
        return try {
            val result = catApi.forgetPassAsync(email,"email").await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }


}