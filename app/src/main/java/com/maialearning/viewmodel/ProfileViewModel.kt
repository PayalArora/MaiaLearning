package com.maialearning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.*
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import kotlinx.coroutines.*
import okhttp3.RequestBody
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
    val stateResidenceObserver = MutableLiveData<JsonObject>()
    val ethnicityObserver = MutableLiveData< ArrayList<EthnicityResponse.EthnicityResponseItem?>?>()
    val imageUrlObserver = MutableLiveData<JsonArray>()
    val uploadImageObserver = MutableLiveData<String>()



    val raceObserver = MutableLiveData< ArrayList<RaceItem?>?>()


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

    fun updateEmail(token: String, updateUserData: UpdateUserData) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.updateEmail(token, updateUserData)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> updateObserver.value = result.toString()
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
                is UseCaseResult.Success -> countryObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }

    fun getStates(token: String, id: String, type:Int) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getStates(token, id)
            }
            showLoading.value = false
            when (result) {

                is UseCaseResult.Success -> {
                    if (type == 0)
                    stateObserver.value = result.data
                    else stateResidenceObserver.value = result.data
                }
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }

    fun getEthnicity(token: String, id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getEthnicities(token, id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> ethnicityObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }

    fun getImageURl(token: String, id: String,ext:String,schoolID:String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getImageURL(token, id,ext,schoolID)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> imageUrlObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }
    fun uploadImage(content:String,url: String, body: RequestBody) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.uploadImage(content,url, body)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> uploadImageObserver.value = result.toString()
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }

    fun getRace(token: String, id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getRaces(token, id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> raceObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }
}