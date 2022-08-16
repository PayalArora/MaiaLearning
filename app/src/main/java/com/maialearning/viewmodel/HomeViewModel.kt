package com.maialearning.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.Consider
import com.maialearning.model.NotesModel
import com.maialearning.model.UniversitySearchPayload
import com.maialearning.model.UpdateStudentPlan

import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val catRepository: LoginRepository) : ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val listObserver = MutableLiveData<JsonObject>()
    val applyObserver = MutableLiveData<JsonObject>()
    val notesObserver = MutableLiveData<NotesModel>()
    val showError = SingleLiveEvent<String>()
    val updateStudentPlanObserver = MutableLiveData<JsonObject>()
    val searchUniversityObserver = MutableLiveData<JsonObject>()
    val likeObserver = MutableLiveData<JsonArray>()
    val unlikeObserver = MutableLiveData<Unit>()


    fun getConsiderList(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getConsiderList(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> listObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun getApplyList(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getApplyList(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> applyObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun getNotes(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getNotes("9375")
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> notesObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun updateStudentPlan(updateStudentPlan: UpdateStudentPlan) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.updateStudentPlan(updateStudentPlan)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> updateStudentPlanObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun searchUniversities(payload: UniversitySearchPayload) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.searchUniversities(payload)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> searchUniversityObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun hitlike(studentid: String, collegeId: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.hitLikeUniv(studentid, collegeId)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> likeObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun hitunlike(studentid: String, collegeId: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.hitUnlikeUniv(studentid, collegeId)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> unlikeObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }
}