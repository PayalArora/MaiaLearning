package com.maialearning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.maialearning.model.ProfileResponse
import com.maialearning.model.UpdateUserData
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class ProfileViewModel(private val catRepository: LoginRepository) : ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val observer = MutableLiveData<ProfileResponse>()
    val smsObserver = MutableLiveData<String>()
    val showError = SingleLiveEvent<String>()
    val updateObserver = MutableLiveData<String>()
    val countryObserver = MutableLiveData<JsonObject>()
    val stateObserver = MutableLiveData<JsonObject>()


    fun getProfile(token: String, id: String) {
        showLoading.value = true
//        Coroutines.ioWorkerViewModel(viewModelScope) {
//            val result = catRepository.getUserProfile(
//                token,
//                id
//            )
//            showLoading.value = false
//            when (result) {
//                is UseCaseResult.Success -> observer.value = result.data
//                is UseCaseResult.Error -> showError.value = result.exception.message
//            }
//        }

        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getUserProfile(token, id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> observer.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }

    }

    fun updateSMSSetting(token: String, id: String, ph: String, code: String, sms: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.updateSmsNotification(token, id, ph, code, sms)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> smsObserver.value = result.toString()
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

    fun updateEmail(token:String,updateUserData:UpdateUserData) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.updateEmail(token, updateUserData)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> updateObserver.value =result.toString()
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }

    fun getCountries(token: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCountries(token)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> countryObserver.value =result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }    }

    fun getStates(token: String,id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getStates(token,id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> stateObserver.value =result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }    }
}