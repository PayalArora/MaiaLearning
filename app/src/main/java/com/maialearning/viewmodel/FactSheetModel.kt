package com.maialearning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.CollegeContactModel
import com.maialearning.model.CollegeFactSheetModel
import com.maialearning.model.FactsheetModelOther
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import com.maialearning.util.prefhandler.SharedHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FactSheetModel(private val catRepository: LoginRepository) : ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val idObserver = MutableLiveData<JsonObject>()
    val contactInfoObserver = MutableLiveData<CollegeContactModel>()
    val noteObserver = MutableLiveData<JsonObject>()
    val listObserver = MutableLiveData<JsonArray>()
    val factSheetObserver = MutableLiveData<JsonObject>()
    val factSheetOtherObserver = MutableLiveData<FactsheetModelOther>()
    val countryObserver = MutableLiveData<JsonObject>()
    val countryFilterObserver = MutableLiveData<JsonObject>()
    val saveCountryObserver = MutableLiveData<JsonArray>()
    val getSaveCountryObserver = MutableLiveData<JsonArray>()
    val showError = SingleLiveEvent<String>()

    fun getColFactSheet(token: String, id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getColFactSheet(token, id)
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> factSheetObserver.value = result.data
                is UseCaseResult.Error -> {
                    showLoading.value = false
                    showError.value = result.exception.response()?.errorBody()?.string()
                }
                is UseCaseResult.Exception -> {
                    showLoading.value = false
                    showError.value = result.exception.message
                }

            }
        }
    }
    fun getColFactSheetOther(token: String, id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getColFactSheetOther(token, id)
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> factSheetOtherObserver.value = result.data
                is UseCaseResult.Error -> {
                    showLoading.value = false
                    showError.value = result.exception.response()?.errorBody()?.string()
                }
                is UseCaseResult.Exception -> {
                    showLoading.value = false
                    showError.value = result.exception.message
                }

            }
        }
    }

    fun getCollegeNid(token: String, id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCollegeNid(token, id)
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> idObserver.value = result.data
                is UseCaseResult.Error -> {
                    showLoading.value = false
                    showError.value = result.exception.response()?.errorBody()?.string()
                }
                is UseCaseResult.Exception -> {
                    showLoading.value = false
                    showError.value = result.exception.message
                }

            }
        }
    }

    fun getUniversityContact(token: String, id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getUniversityContact(token, id)
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> contactInfoObserver.value = result.data
                is UseCaseResult.Error -> {
                    showLoading.value = false
                    showError.value = result.exception.response()?.errorBody()?.string()
                }
                is UseCaseResult.Exception -> {
                    showLoading.value = false
                    showError.value = result.exception.message
                }

            }
        }
    }

    fun getUniversityNotes(token: String, id: String, id2: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getUniversityNotes(token, id, id2)
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> noteObserver.value = result.data
                is UseCaseResult.Error -> {
                    showLoading.value = false
                    showError.value = result.exception.response()?.errorBody()?.string()
                }
                is UseCaseResult.Exception -> {
                    showLoading.value = false
                    showError.value = result.exception.message
                }

            }
        }
    }

    fun getUniversityList(token: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getUniversityList(token)
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> listObserver.value = result.data
                is UseCaseResult.Error -> {
                    showLoading.value = false
                    showError.value = result.exception.response()?.errorBody()?.string()
                }
                is UseCaseResult.Exception -> {
                    showLoading.value = false
                    showError.value = result.exception.message
                }

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

    fun getFilterCollege() {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getcountryFilter()
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> countryFilterObserver.value = result.data
                is UseCaseResult.Error -> {
                    showLoading.value = false
                    showError.value = result.exception.response()?.errorBody()?.string()
                }
                is UseCaseResult.Exception -> {
                    showLoading.value = false
                    showError.value = result.exception.message
                }

            }
        }
    }
    fun getSaveCountry() {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getSaveCountry()
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> saveCountryObserver.value = result.data
                is UseCaseResult.Error -> {
                    showLoading.value = false
                    showError.value = result.exception.response()?.errorBody()?.string()
                }
                is UseCaseResult.Exception -> {
                    showLoading.value = false
                    showError.value = result.exception.message
                }

            }
        }
    }
    fun setSaveCountry() {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.setSaveCountry()
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> getSaveCountryObserver.value = result.data
                is UseCaseResult.Error -> {
                    showLoading.value = false
                    showError.value = result.exception.response()?.errorBody()?.string()
                }
                is UseCaseResult.Exception -> {
                    showLoading.value = false
                    showError.value = result.exception.message
                }

            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }
}