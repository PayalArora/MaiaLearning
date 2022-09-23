package com.maialearning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.CareerTopPickResponse
import com.maialearning.model.CareerTopPickResponseItem
import com.maialearning.model.NotesModel
import com.maialearning.model.SelectedCareerResponse
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import com.maialearning.util.replaceCrossBracketsComas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CareerViewModel(private val catRepository: LoginRepository) : ViewModel(), CoroutineScope {
    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    val careerListObserver = MutableLiveData<JsonArray>()
    val showLoading = MutableLiveData<Boolean>()
    val showError = SingleLiveEvent<String>()
    val careerListDetailObserver = MutableLiveData<JsonObject>()
    val careerKeyboardObserver = MutableLiveData<JsonObject>()

    fun getKeyboardSearch(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getKeyboardSearch("https://services.onetcenter.org/v1.9/ws/mnm/search?keyword=x&client=serviceinfinity")
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> careerKeyboardObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
                        ?.replaceCrossBracketsComas()

            }
        }
    }
    fun getCareerList(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCareerListing(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> careerListObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
                        ?.replaceCrossBracketsComas()

            }
        }
    }


    fun getCareerListDetail(id: String, url:String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCareerListingDetail(id, url)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> careerListDetailObserver.value = result.data
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