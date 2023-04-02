package com.maialearning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.*
import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import com.maialearning.util.SEARCH_AFFINITY
import com.maialearning.util.replaceCrossBracketsComas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class CareerViewModel(private val catRepository: LoginRepository) : ViewModel(), CoroutineScope {
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    val careerListObserver = MutableLiveData<JsonArray>()
    val showLoading = MutableLiveData<Boolean>()
    val showError = SingleLiveEvent<String>()
    val careerListDetailObserver = MutableLiveData<JsonObject>()
    val careerKeyboardObserver = MutableLiveData<CareerSearchCodesModel>()
    val careerClusterObserver = MutableLiveData<CareerClusterModel>()
    val careerClusterListObserver = MutableLiveData<CareerClusterListModel>()
    val industryListObserver = MutableLiveData<IndustryListModel>()
    val careerClusterDetailObserver = MutableLiveData<ArrayList<BrightOutlookModel.Data>>()
    val brightOutObserver = MutableLiveData<BrightOutlookModel>()
    val workObserver = MutableLiveData<JsonObject>()
    val careerUsObserver = MutableLiveData<CareerUSModel>()
    val careerKeyboardDetailObserver = MutableLiveData<JsonArray>()
    val nysCareerObserver = MutableLiveData<JsonObject>()
    val studentCareerPlanObserver = MutableLiveData<JsonObject>()
    val careerComparisonsObserver = MutableLiveData<JsonObject>()
    val getVideoCodeObserver = MutableLiveData<JsonObject>()
    val getCareerCategoryObserver = MutableLiveData<ArrayList<CareerCategoryResponseItem>>()
    val getCareerPathwayObserver = MutableLiveData<ArrayList<CareerCategoryResponseItem>>()
    val getCareerSearchObserver = MutableLiveData<ArrayList<CareerSearchResponseItem>>()
    val topPickObserver = MutableLiveData<JsonArray?>()
    val addFavObserver = MutableLiveData<JsonObject?>()
    val unlikeObserver = MutableLiveData<JsonObject>()

    fun getKeyboardSearch(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getKeyboardSearch(id)
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

    fun getCareerCluster(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCareerCluster(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> careerClusterObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
                        ?.replaceCrossBracketsComas()

            }
        }
    }

    fun getCareerClusterList(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCareerClusterList(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> careerClusterListObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
                        ?.replaceCrossBracketsComas()

            }
        }
    }

    fun getCareerClusterListDetail(list: ArrayList<String>) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCareerClusterList(list)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> careerClusterDetailObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
                        ?.replaceCrossBracketsComas()

            }
        }
    }

    fun getCareerBright(type: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCareerBright(type)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> brightOutObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
                        ?.replaceCrossBracketsComas()

            }
        }
    }

    fun getKeywoardSearchDetail(url: String, list: ArrayList<String>) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getKeywoardSearchDetails(url, list)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> careerKeyboardDetailObserver.value = result.data
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


    fun getCareerListDetail(id: String, url: String) {
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

    fun getIndustryList(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getIndustryList(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> industryListObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
                        ?.replaceCrossBracketsComas()

            }
        }
    }

    fun getWorkSearch(url: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getWorkSearch(url)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> workObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.toString()

            }
        }
    }

    fun getUSSearch(url: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getUSSearch(url)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> careerUsObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.toString()

            }
        }
    }

    fun getNYSCareerPlan(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getNYSCareer(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> nysCareerObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.toString()

            }
        }
    }


    fun getStudentCareerPlan(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getStudentCareerPlan(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> studentCareerPlanObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.toString()

            }
        }
    }

    fun compareCareers(body: CompareCareerBody) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCareerComparisons(body)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> careerComparisonsObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.toString()

            }
        }
    }

    fun getVideoCode(url: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getVideoCode(url)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> getVideoCodeObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.toString()

            }
        }
    }

    fun getCareerCategories() {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCareerCategories()
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> getCareerCategoryObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.toString()

            }
        }
    }

 fun getCareerPathways() {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCareerPathways()
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> getCareerPathwayObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.toString()

            }
        }
    }

    fun getCareerSearch(searchBy:String,searchValue:String,offset:String,limit:String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCareerSearch(searchBy, searchValue, offset,limit)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> getCareerSearchObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.toString()

            }
        }
    }
    fun getStudentTopPick(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getStudentTopPick(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> topPickObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
                        ?.replaceCrossBracketsComas()

            }
        }
    }

    fun addFavCareer( code: String,
                      student_uid: String,
                      title: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.addFavCareer(code, student_uid, title)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> addFavObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
                        ?.replaceCrossBracketsComas()

            }
        }
    }
    fun unLikeWork(content: DeleteContent) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.unlikeRelatedCareer(content)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> unlikeObserver.value = result.data
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

