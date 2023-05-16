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
import kotlin.coroutines.CoroutineContext

class SummaryModel(private val catRepository: LoginRepository) : ViewModel(), CoroutineScope {
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    val showLoading = MutableLiveData<Boolean>()
    val showError = SingleLiveEvent<String>()
    val careerPrefObserver = MutableLiveData<JsonObject>()
    val getCareerFilterObserver = MutableLiveData<CareerFilterResponse>()

    val careerListFactsheetObserver = MutableLiveData<JsonObject>()

    fun getCareerFilters() {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCareerFilters()
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> getCareerFilterObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.toString()

            }
        }
    }
    fun careerPref(regionLevel: String, regionCode: String, regionName:String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.careerPref(regionLevel, regionCode, regionName)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> careerPrefObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.toString()

            }
        }
    }
    fun getCareerFactsheetDetail(id: String, regionLevel: String?, regionCode: String?) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCareerFactsheetDetail(id, regionLevel, regionCode)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> careerListFactsheetObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
                        ?.replaceCrossBracketsComas()

            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }
}

