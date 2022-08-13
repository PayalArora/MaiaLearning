package com.maialearning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.maialearning.model.CollegeContactModel
import com.maialearning.model.CollegeFactSheetModel
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import com.maialearning.util.prefhandler.SharedHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FactSheetModel (private val catRepository: LoginRepository) : ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val idObserver = MutableLiveData<JsonObject>()
    val contactInfoObserver = MutableLiveData<CollegeContactModel>()
//    val factSheetObserver = MutableLiveData<CollegeFactSheetModel>()
    val factSheetObserver = MutableLiveData<JsonObject>()
    val showError = SingleLiveEvent<String>()

    fun getColFactSheet(token:String,id:String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getColFactSheet(token,id)
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
                    showError.value = result.exception.message}

            }
        }
    }
    fun getCollegeNid(token:String,id:String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCollegeNid(token,id)
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
                    showError.value = result.exception.message}

            }
        }
    }

    fun getUniversityContact(token:String,id:String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getUniversityContact(token,id)
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
                    showError.value = result.exception.message}

            }
        }
    }
    fun getUniversityNotes(token:String,id:String,id2:String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getUniversityNotes(token,id,id2)
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
                    showError.value = result.exception.message}

            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }
}