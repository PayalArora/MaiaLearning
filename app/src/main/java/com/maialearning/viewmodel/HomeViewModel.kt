package com.maialearning.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.*

import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import com.maialearning.util.replaceCrossBracketsComas
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
    val delObserver = MutableLiveData<Unit>()
    val applyingObserver = MutableLiveData<JsonArray>()
    val addProgramObserver = MutableLiveData<JsonObject>()
    val deleteProgramObserver = MutableLiveData<JsonArray>()
    val decisionStatusObserver = MutableLiveData<JsonObject>()


    fun getConsiderList(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getConsiderList(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> listObserver.value = result.data
                     is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()?.replaceCrossBracketsComas()
         
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
                     is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
         
            }
        }
    }

    fun getNotes(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getNotes(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> notesObserver.value = result.data
                     is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
         
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
                is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
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
                is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
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
                is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
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
                is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
            }
        }
    }
    fun hidel(studentid: String, collegeId: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.hitDelUnivCons(studentid, collegeId)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> delObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
            }
        }
    }
    fun moveToApplying(studentid: String, collegeId: String, status: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.moveToApplying(studentid, collegeId, status)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> applyingObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
            }
        }
    }

    fun addProgramToConsidering(addProgramConsider: AddProgramConsider) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.addProgramToConsidering(addProgramConsider)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> addProgramObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
            }
        }
    }

    fun deleteMlProgram(get: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.deleteProgramCOnsider(get)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> deleteProgramObserver.value = result.data
                     is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
         
            }
        }
    }

    fun getDecisionStatuses() {
       // showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getDecisionStatuses()
            }
           // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> decisionStatusObserver.value = result.data
                     is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
         
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }




}