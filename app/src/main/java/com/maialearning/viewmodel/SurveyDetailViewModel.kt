package com.maialearning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.maialearning.model.DashboardOverdueResponse
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SurveyDetailViewModel(private val catRepository: LoginRepository
) : ViewModel(), CoroutineScope {

    // Coroutine's background job
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    val showLoading = MutableLiveData<Boolean>()
    val surveyDetailObserver = MutableLiveData<JsonObject>()
    val showError = SingleLiveEvent<String>()

    fun getSurveyResponses(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getSurveyResponses(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> surveyDetailObserver.value = result.data
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