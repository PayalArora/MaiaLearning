package com.maialearning.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.CeebResponse
import com.maialearning.model.CeebResponseItem
import com.maialearning.model.ForgetModel
import com.maialearning.model.LoginNewModel
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class LoginNewModel(private val catRepository: LoginRepository) : ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val loginObserver = MutableLiveData<LoginNewModel>()
    val forgetObserver = MutableLiveData<ForgetModel>()
    val ceebObserver = MutableLiveData<ArrayList<CeebResponseItem>>()
    val samlUrlObserver = MutableLiveData<Any>()
    val showError = SingleLiveEvent<String>()


    fun userLogin(context: Context, email: String, password: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getUserLogin(
                    email,
                    password
                )
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> loginObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }

    fun googleLogin(email: String, id: String, id_token: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getGoogleLogin(
                    email,
                    id, id_token
                )
            }

            showLoading.postValue(false)
            when (result) {
                is UseCaseResult.Success -> loginObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message
            }

        }
    }

    fun microLogin(token: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getMicroLogin(
                    token
                )
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> loginObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }

    fun forgetPassword(email: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getForgetPassword(
                    email
                )
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> forgetObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }
    fun ceebCode() {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.ceebCode(
                )
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> ceebObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }
    fun samlUrl(email: String?, code:String?) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.samlUrl(email, code
                )
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> samlUrlObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }
}