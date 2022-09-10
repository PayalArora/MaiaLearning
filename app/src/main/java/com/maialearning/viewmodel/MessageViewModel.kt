package com.maialearning.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.ForgetModel
import com.maialearning.model.InboxResponse
import com.maialearning.model.LoginNewModel
import com.maialearning.model.SendMessageModel
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.repository.MessageRepository
import com.maialearning.util.Coroutines
import com.maialearning.util.replaceInvertedComas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class MessageViewModel(private val catRepository: MessageRepository) : ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val inboxObserver = MutableLiveData<JsonObject>()
    val delObserver = MutableLiveData<JsonObject>()
    val createObserver = MutableLiveData<JsonObject>()
    val showError = SingleLiveEvent<String>()
    val imageUrlObserver = MutableLiveData<JsonObject>()
    val uploadImageObserver = MutableLiveData<String>()
    val fileVirusObserver = MutableLiveData<JsonObject>()

    fun getInbox() {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getInbox()
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> inboxObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }
    fun getSent() {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getSent()
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> inboxObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }
    fun getTrash() {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getTrash()
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> inboxObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }
    fun getMessage(id:String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getMessage(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> inboxObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }

    fun delMessage(id:String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.delMessage(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> delObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }
    fun createMessage(context: Context, id: SendMessageModel) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.createMessage(
                    id

                )
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> createObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }
    fun getImageURl(token: String, filename: String,ext:String,key:String,schoolID:String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getImageURL(token, filename,ext,key,schoolID)
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
                is UseCaseResult.Success -> uploadImageObserver.value = result.toString().replaceInvertedComas()
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }
    fun checkFileVirus(url: String, putUrl: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.checkFileVirus(url, putUrl)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> fileVirusObserver.value = result.data
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