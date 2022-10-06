package com.maialearning.viewmodel


import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.*

import com.maialearning.network.UseCaseResult
import com.maialearning.repository.LoginRepository
import com.maialearning.util.Coroutines
import com.maialearning.util.replaceCrossBracketsComas
import com.maialearning.util.replaceInvertedComas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlinx.serialization.descriptors.PrimitiveKind
import okhttp3.RequestBody
import org.json.JSONArray
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
    val listArrayObserver = MutableLiveData<JsonArray>()
    val showError = SingleLiveEvent<String>()
    val updateStudentPlanObserver = MutableLiveData<JsonObject>()
    val searchUniversityObserver = MutableLiveData<JsonObject>()
    val likeObserver = MutableLiveData<JsonArray>()
    val unlikeObserver = MutableLiveData<Unit>()
    val delObserver = MutableLiveData<Unit>()
    val applyingObserver = MutableLiveData<JsonArray>()
    val recSendObserver = MutableLiveData<JsonArray>()
    val recUCASObserver = MutableLiveData<JsonArray>()
    val recommdersObserver = MutableLiveData<RecomdersModel>()
    val recommdersCollegeObserver = MutableLiveData<JsonObject>()
    val typeObserver = MutableLiveData<JsonArray>()
    val recDeadline = MutableLiveData<JsonArray>()
    val addProgramObserver = MutableLiveData<JsonObject>()
    val deleteProgramObserver = MutableLiveData<JsonArray>()
    val decisionStatusObserver = MutableLiveData<JsonObject>()
    val cancelRecommendRequestObserver = MutableLiveData<Unit>()
    val getDocumentPresignedObserver = MutableLiveData<JsonObject>()
    val uploadImageObserver = MutableLiveData<String>()
    val saveDocumentBragsheetObserver = MutableLiveData<Unit>()
    val getUniversitiesObserver = MutableLiveData<JsonObject>()
    val getMilestoneIDObserver = MutableLiveData<JsonObject>()
    val getMilestonesObserver = MutableLiveData<MilestoneResponse>()
    val downloadObserver = MutableLiveData<JsonArray>()
    val bragSheetObserver = MutableLiveData<JsonObject>()
    val checkItaskObserver = MutableLiveData<Unit>()
    val uncheckItaskObserver = MutableLiveData<Unit>()
    val univJsonFilter = MutableLiveData<JsonObject>()
    val getcontinentFilter = MutableLiveData<JsonObject>()

    fun getConsiderList(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getConsiderList(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> listObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
                        ?.replaceCrossBracketsComas()

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
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()

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
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()

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
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
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
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
            }
        }
    }

    fun getRecipients(context: Context, id: String, type: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getRecipients(
                    id,
                    type
                )
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> listArrayObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()
                is UseCaseResult.Exception -> showError.value = result.exception.message

            }
        }
    }
    fun euroUniversities(payload: UniversitySearchPayload) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.euroUniversities(payload)
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
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
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
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
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
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
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
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
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
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()
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
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()

            }
        }
    }

    fun getDecisionStatuses() {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getDecisionStatuses()
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> decisionStatusObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceCrossBracketsComas()

            }
        }
    }

    fun getTeachers(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.teacherList(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> applyingObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun getRecDeadline() {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.recDeadline()
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> recDeadline.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    //    fun sendRecomTeachers(id: JSONObject) {
    fun sendRecomTeachers(model: RecModel) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {

                catRepository.sendRecom(model)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> recSendObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun sendRecomUCAS(model: RecModel) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {

                catRepository.sendUcasRec(model)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> recUCASObserver.value = result.data
                is UseCaseResult.Error -> showError.value =
                    result.exception.response()?.errorBody()?.string()?.replaceInvertedComas()
            }
        }
    }

    fun getRecomType(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {

                catRepository.getRecomType(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> typeObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun getRecommenders(id: String, page: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {

                catRepository.getRecomders(id, page)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> recommdersObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun getRecommendersCollege(id: String, page: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {

                catRepository.getRecomdersCollege(id, page)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> recommdersCollegeObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun cancelRecommendRequest(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {

                catRepository.cancelRecommedationRequest(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> cancelRecommendRequestObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun getPresignedURL(name: String, uID: String, docType: String, hash: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {

                catRepository.getDocumentPresignedURl(name, uID, docType, hash)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> getDocumentPresignedObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun uploadDoc(url: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.uploadDoc(url)
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

    val fileVirusObserver = MutableLiveData<JsonObject>()

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

    fun saveDocumentBragsheet(
        id: String,
        name: String,
        path: String,
        exist: Int,
        url: String,
        hash: String
    ) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {

                catRepository.uploadDocSaveBragsheet(id, name, url, exist, path, hash)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> saveDocumentBragsheetObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun getUniversities(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {

                catRepository.getUniversities(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> getUniversitiesObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun getMilestonesID() {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getMilestonesID()
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> getMilestoneIDObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun getMilestones(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getMilestones(id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> getMilestonesObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun downloadBragSheet(file_id: String, uuid: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.downloadBragSheet(file_id, uuid)
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> downloadObserver.value = result.data
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

    fun getBragSheet(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getBragSheet(id)
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> bragSheetObserver.value = result.data
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

    fun checkItask(id: String, studentId: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.checkItask(id, studentId)
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> checkItaskObserver.value = result.data
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

    fun uncheckItask(id: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.unCheckItask(id)
            }
            // showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> uncheckItaskObserver.value = result.data
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

    fun uploadRecoBragsheet(
        id: String,
        name: String,
        path: String,
        hash: String
    ) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {

                catRepository.uploadRecoBragsheet(name, path, hash, id)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> saveDocumentBragsheetObserver.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }

    fun getCollegeJsonFilter(url: String, file: UnivCollegeModel) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCollegeJsonFilter(url, file)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> univJsonFilter.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun getCountriesContinentBased(code: String) {
        showLoading.value = true
        Coroutines.mainWorker {
            val result = withContext(Dispatchers.Main) {
                catRepository.getCountriesContinentBased(code)
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> getcontinentFilter.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }
}