package com.maialearning.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.*
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import com.maialearning.util.replaceInvertedComas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext


class DashboardFragViewModel(private val catRepository: LoginRepository) : ViewModel(),CoroutineScope{
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val overdueObserver = MutableLiveData<DashboardOverdueResponse>()
    val showError = SingleLiveEvent<String>()
    val surveysObserver = MutableLiveData<SurveysResponse>()
    val webinarObserver = MutableLiveData<WebinarResponse>()
    val preAssignedDownloadObserver = MutableLiveData<JsonArray>()
    val writeToCounselerObserver = MutableLiveData<JsonArray>()
    val presignedUrlObserver = MutableLiveData<JsonObject>()
    var liveDataMerger: MediatorLiveData<*> = MediatorLiveData<Any>()
    val uploadImageObserver = MutableLiveData<String>()
    val fmTagsObserver = MutableLiveData<ArrayList<FmTagsResponseItem?>>()
    val uploadEndObserver = MutableLiveData<String>()
    val fileVirusObserver = MutableLiveData<JsonObject>()
    val completeFileObserver = MutableLiveData<String>()
    val resetCompleteFileObserver = MutableLiveData<String>()


    fun getOverDueCompleted(token:String,id:String) {
       // showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getOverDueCompleted(token,id)
            }
           showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> overdueObserver.value = result.data
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

    fun getSurveys(token:String,url:String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getSurveys(url,token)
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> surveysObserver.value = result.data
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


    fun getWebinar(token: String, url: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getWebinar(url,token)
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> webinarObserver.value = result.data
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

    fun downloadWorkSheet(file_id:String, uuid:String, doc_type:String){
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.downloadWorkSheet(file_id, uuid,doc_type )
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> preAssignedDownloadObserver.value = result.data
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

    fun writeToCounselor(token:String, nid:String, response:String){
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.writeToCounselor(token, nid,response )
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> writeToCounselerObserver.value = result.data
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
    fun getPresignedURL(token:String, nid:String, name:String){
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getAttachmentPResignedUrl(token, name,nid )
            }
             showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> presignedUrlObserver.value = result.data
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

    fun uploadFile(token:String,url: FileUploadData) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.updateFileAttachData(token,url)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> uploadEndObserver.value = result.toString()
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }

    fun getTags() {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getFMTags()
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> fmTagsObserver.value = result.data
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
    fun completetTask(nid: String, uid: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.completeAssignment(nid, uid)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> completeFileObserver.value = result.data[0].toString().replaceInvertedComas()
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }
    fun resetCompleteTask( uid: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.resetTaskCompletion( uid)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> resetCompleteFileObserver.value = result.data[0].toString().replaceInvertedComas()
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