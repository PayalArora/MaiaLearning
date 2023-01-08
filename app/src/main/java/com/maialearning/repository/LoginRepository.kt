package com.maialearning.repository

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.*
import com.maialearning.network.AllAPi
import com.maialearning.network.BaseApplication
import com.maialearning.network.UseCaseResult
import com.maialearning.util.*

import retrofit2.HttpException


import com.maialearning.util.prefhandler.SharedHelper
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.json.Json
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.http.Field


interface LoginRepository {
    // Suspend is used to await the result from Deferred
    suspend fun getUserLogin(username: String, password: String): UseCaseResult<LoginNewModel>
    suspend fun getGoogleLogin(
        email: String,
        id: String,
        id_token: String
    ): UseCaseResult<LoginNewModel>

    suspend fun getMicroLogin(token: String): UseCaseResult<LoginNewModel>
    suspend fun getForgetPassword(email: String): UseCaseResult<ForgetModel>

    suspend fun getUserProfile(id_token: String, id: String): UseCaseResult<ProfileResponse>
    suspend fun getConsiderList(id: String, status: String): UseCaseResult<JsonObject>
    suspend fun getColleges(id: String): UseCaseResult<JsonObject>
    suspend fun getNotes(id: String): UseCaseResult<NotesModel>
    suspend fun getApplyList(id: String): UseCaseResult<JsonObject>
    suspend fun getJWTToken(): UseCaseResult<String>
    suspend fun getInbox(): UseCaseResult<JsonObject>
    suspend fun updateSmsNotification(
        token: String,
        id: String,
        ph: String,
        code: String,
        sms: String
    ): UseCaseResult<String>

    suspend fun updateEmail(token: String, userData: UpdateUserData): UseCaseResult<String>
    suspend fun getCountries(token: String): UseCaseResult<JsonObject>
    suspend fun getStates(token: String, id: String): UseCaseResult<JsonObject>
    suspend fun getEthnicities(
        token: String,
        id: String
    ): UseCaseResult<ArrayList<EthnicityResponse.EthnicityResponseItem?>>

    suspend fun getRaces(token: String, id: String): UseCaseResult<ArrayList<RaceItem?>>
    suspend fun getImageURL(
        token: String,
        id: String,
        ext: String,
        schoolId: String
    ): UseCaseResult<JsonArray>

    suspend fun uploadImage(content: String, url: String, bode: RequestBody): UseCaseResult<Unit>
    suspend fun getRecipients(id: String, type: String): UseCaseResult<JsonArray>
    suspend fun getOverDueCompleted(
        token: String,
        id: String
    ): UseCaseResult<DashboardOverdueResponse>

    suspend fun getColFactSheet(token: String, id: String): UseCaseResult<JsonObject>
    suspend fun getColFactSheetOther(token: String, id: String): UseCaseResult<FactsheetModelOther>
    suspend fun getCollegeNid(token: String, id: String): UseCaseResult<JsonObject>
    suspend fun getUniversityContact(token: String, id: String): UseCaseResult<CollegeContactModel>
    suspend fun getUniversityNotes(
        token: String,
        id: String,
        id2: String
    ): UseCaseResult<JsonObject>

    suspend fun getUniversityList(
        status: String, uid: String
    ): UseCaseResult<JsonArray>
    //  suspend fun getSearchResults(search: UniversitySearch): UseCaseResult<DashboardOverdueResponse>

    suspend fun updateStudentPlan(
        updateStudentPlan: UpdateStudentPlan
    ): UseCaseResult<JsonObject>

    suspend fun searchUniversities(
        payload: UniversitySearchPayload
    ): UseCaseResult<JsonObject>

    suspend fun euroUniversities(
        payload: UniversitySearchPayload
    ): UseCaseResult<JsonObject>

    suspend fun getSurveys(url: String, token: String): UseCaseResult<SurveysResponse>
    suspend fun getWebinar(url: String, token: String): UseCaseResult<WebinarResponse>
    suspend fun downloadWorkSheet(
        file_id: String,
        uuid: String,
        doc_type: String
    ): UseCaseResult<JsonArray>

    suspend fun downloadBragSheet(
        file_id: String,
        uuid: String
    ): UseCaseResult<JsonArray>

    suspend fun getBragSheet(
        id: String
    ): UseCaseResult<JsonObject>

    suspend fun writeToCounselor(
        token: String,
        nid: String,
        response: String
    ): UseCaseResult<JsonArray>

    suspend fun getAttachmentPResignedUrl(
        token: String,
        name: String,
        fm_uid: String
    ): UseCaseResult<JsonObject>

    suspend fun updateFileAttachData(
        token: String,
        updateUserData: FileUploadData
    ): UseCaseResult<JsonObject>

    suspend fun getFMTags(
    ): UseCaseResult<ArrayList<FmTagsResponseItem?>>

    suspend fun checkFileVirus(
        url: String,
        putUrl: String
    ): UseCaseResult<JsonObject>

    suspend fun completeAssignment(
        nid: String,
        uid: String
    ): UseCaseResult<JsonArray>

    suspend fun resetTaskCompletion(
        uid: String
    ): UseCaseResult<JsonArray>

    suspend fun hitLikeUniv(studentId: String, collegeId: String): UseCaseResult<JsonArray>
    suspend fun hitUnlikeUniv(studentId: String, collegeId: String): UseCaseResult<Unit>
    suspend fun hitDelUnivCons(studentId: String, collegeId: String): UseCaseResult<Unit>
    suspend fun moveToApplying(
        studentId: String,
        collegeId: String,
        status: String
    ): UseCaseResult<JsonArray>

    suspend fun addProgramToConsidering(
        payload: AddProgramConsider
    ): UseCaseResult<JsonObject>

    suspend fun deleteProgramCOnsider(
        id: String
    ): UseCaseResult<JsonArray>

    suspend fun teacherList(
        id: String
    ): UseCaseResult<JsonArray>

    suspend fun sendRecom(
        recModel: RecModel
    ): UseCaseResult<JsonArray>

    suspend fun getDecisionStatuses(
    ): UseCaseResult<JsonObject>

    suspend fun recDeadline(): UseCaseResult<JsonArray>
    suspend fun sendUcasRec(recModel: RecModel): UseCaseResult<JsonArray>
    suspend fun getRecomders(id: String, page: String): UseCaseResult<RecomdersModel>
    suspend fun getRecomdersCollege(id: String, page: String): UseCaseResult<JsonObject>
    suspend fun cancelRecommedationRequest(id: String): UseCaseResult<Unit>
    suspend fun getRecomType(id: String): UseCaseResult<JsonArray>
    suspend fun getDocumentPresignedURl(
        name: String,
        uid: String,
        docType: String,
        hash: String
    ): UseCaseResult<JsonObject>

    suspend fun uploadDoc(url: String): UseCaseResult<Unit>

    suspend fun uploadDocSaveBragsheet(
        id: String,
        name: String,
        url: String,
        exist: Int,
        path: String,
        hash: String
    ): UseCaseResult<Unit>

    suspend fun getUniversities(
        id: String
    ): UseCaseResult<JsonObject>

    suspend fun getMilestonesID(
    ): UseCaseResult<JsonObject>

    suspend fun getMilestones(
        milestoneId: String
    ): UseCaseResult<MilestoneResponse>

    suspend fun checkItask(
        iTaskId: String, studentID: String
    ): UseCaseResult<Unit>

    suspend fun unCheckItask(
        iTaskId: String
    ): UseCaseResult<Unit>

    suspend fun uploadRecoBragsheet(
        name: String,
        path: String,
        hash: String,
        id: String
    ): UseCaseResult<Unit>

    suspend fun getCollegeJsonFilter(
        url: String,
        file: UnivCollegeModel
    ): UseCaseResult<JsonObject>

    suspend fun getCareerListing(
        id: String
    ): UseCaseResult<JsonArray>

    suspend fun getCareerListingDetail(
        id: String, url: String
    ): UseCaseResult<JsonObject>

    suspend fun getKeyboardSearch(
        url: String
    ): UseCaseResult<CareerSearchCodesModel>

    suspend fun getCareerCluster(
        url: String
    ): UseCaseResult<CareerClusterModel>

    suspend fun getCareerClusterList(
        url: String
    ): UseCaseResult<CareerClusterListModel>

    suspend fun getIndustryList(
        url: String
    ): UseCaseResult<IndustryListModel>

    suspend fun getCareerBright(
        type: String
    ): UseCaseResult<BrightOutlookModel>

    suspend fun getCareerClusterList(
        list: ArrayList<String>
    ): UseCaseResult<ArrayList<BrightOutlookModel.Data>>

    suspend fun getKeywoardSearchDetails(
        url: String,
        list: ArrayList<String>
    ): UseCaseResult<JsonArray>

    suspend fun getWorkSearch(
        url: String
    ): UseCaseResult<JsonObject>

    suspend fun getUSSearch(
        url: String
    ): UseCaseResult<CareerUSModel>

    suspend fun getNYSCareer(
        id: String
    ): UseCaseResult<JsonObject>

    suspend fun getStudentCareerPlan(
        id: String
    ): UseCaseResult<JsonObject>

    suspend fun getcountryFilter(
    ): UseCaseResult<JsonObject>

    suspend fun getSaveCountry(
    ): UseCaseResult<JsonArray>

    suspend fun setSaveCountry(
    ): UseCaseResult<JsonArray>

    suspend fun getCareerComparisons(
        body: CompareCareerBody
    ): UseCaseResult<JsonObject>

    suspend fun getCountriesContinentBased(
        body: String
    ): UseCaseResult<JsonObject>

    suspend fun getVideoCode(
        body: String
    ): UseCaseResult<JsonObject>

    suspend fun getExperiences(
        id: String
    ): UseCaseResult<JsonArray>

    suspend fun getStudentTopPick(
        id: String
    ): UseCaseResult<JsonArray>

    suspend fun likeRelatedCareer(
        content: CreateContent
    ): UseCaseResult<JsonObject>

    suspend fun unlikeRelatedCareer(
        content: DeleteContent
    ): UseCaseResult<JsonObject>

    suspend fun getProgramListDetail(
        id: String
    ): UseCaseResult<CourseModelOptionDetailResponse>

    suspend fun getCollegeEssay(
        id: String, page: String, sortBy: String, sortOrder: String
    ): UseCaseResult<CollegeEssayResponse>

    suspend fun deleteCollegeEssay(
        id: String
    ): UseCaseResult<Unit>

    suspend fun getSubDiscipline(
        id: String
    ): UseCaseResult<JsonObject>

    suspend fun getGermanChildSubject(
        id: String
    ): UseCaseResult<JsonObject>

    suspend fun getGbSubChild(
        id: String
    ): UseCaseResult<JsonObject>

    suspend fun getFosChild(
        id: String
    ): UseCaseResult<JsonObject>

    suspend fun getFosOther(
    ): UseCaseResult<JsonObject>

    suspend fun getApplyingWIth(
    ): UseCaseResult<JsonObject>

    suspend fun bulkCollegeMoving(
        payload: BulkCollegeMovePayload
    ): UseCaseResult<Unit>

    suspend fun getTestScores(
        id: String
    ): UseCaseResult<JsonArray>

    suspend fun testScoreStatusSubmit(
        payload: TestScoreSubmitPayload
    ): UseCaseResult<Unit>

    suspend fun checkAllTranscripts(
        id: String, value: Int, ncaa: Int
    ): UseCaseResult<Unit>

    suspend fun checkReqTranscripts(
        id: String, value: Int
    ): UseCaseResult<JsonObject>

    suspend fun getStudentRecommendPrefrence(
        schoolId: String, studentId: String
    ): UseCaseResult<JsonObject>

    suspend fun compareAllColleges(
        id: String
    ): UseCaseResult<JsonObject>

    suspend fun cancelRound(
        id: String
    ): UseCaseResult<JsonObject>

    suspend fun savePrefReco(
        arr: PrefferedRecoSaveModel
    ): UseCaseResult<JsonObject>

    suspend fun addUniversityCollSearch(
        payload: UniversitySearchPayload
    ): UseCaseResult<JsonObject>

    suspend fun addUniversity(
        id: String, cId: String
    ): UseCaseResult<Unit>
}

class LoginRepositoryImpl(private val catApi: AllAPi) : LoginRepository {


    override suspend fun getUserLogin(
        username: String,
        password: String
    ): UseCaseResult<LoginNewModel> {
        return try {
            val result = catApi.userLoginAsync(username, password).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            println(ex.stackTrace)
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            println(ex.stackTraceToString())
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getGoogleLogin(
        email: String,
        id: String,
        id_token: String
    ): UseCaseResult<LoginNewModel> {
        return try {
            val result = catApi.googleLoginAsync(ORIGIN, email, id, id_token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            println(ex.stackTraceToString())
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getMicroLogin(token: String): UseCaseResult<LoginNewModel> {
        return try {
            val result = catApi.microLoginAsync(ORIGIN, token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            print(ex.response()?.errorBody().toString())
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            print(ex.message)
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getForgetPassword(email: String): UseCaseResult<ForgetModel> {
        return try {
            val result = catApi.forgetPassAsync(email, "email").await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getUserProfile(
        id_token: String,
        id: String,
    ): UseCaseResult<ProfileResponse> {
        return try {
            val result = catApi.getProfileAsync(id_token, id).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getConsiderList(id: String, status: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.considerListAsync(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                id, status
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }
    override suspend fun getColleges(id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.collegeListAsync(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getNotes(id: String): UseCaseResult<NotesModel> {
        return try {
            val result = catApi.getNotesAsync(
                id,
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getApplyList(id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.applyListAsync(
                id,
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun updateSmsNotification(
        token: String,
        id: String,
        ph: String,
        code: String,
        sms: String
    ): UseCaseResult<String> {
        return try {
            val result = catApi.updateSMSSettingAsync(token, id, ph, code, sms).await()
            UseCaseResult.Success(result.toString())
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun updateEmail(
        token: String,
        updateUserData: UpdateUserData
    ): UseCaseResult<String> {
        return try {
            val result = catApi.updateEmailAsync(token, updateUserData).await()
            UseCaseResult.Success(result.toString())
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getCountries(token: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getCountriesAsync(token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getStates(token: String, id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getStatesAsync(id, token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getEthnicities(
        token: String,
        id: String
    ): UseCaseResult<ArrayList<EthnicityResponse.EthnicityResponseItem?>> {
        return try {
            val result = catApi.getEthnicitiesAsync(id, token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getRaces(token: String, id: String): UseCaseResult<ArrayList<RaceItem?>> {
        return try {
            val result = catApi.getRacesAsync(id, token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getImageURL(
        token: String,
        id: String,
        ext: String,
        schoolId: String
    ): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.updateProfImageAsync(token, id, ext, schoolId).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun uploadImage(
        content: String,
        url: String,
        bode: RequestBody
    ): UseCaseResult<Unit> {
        return try {
            val result = catApi.uploadImageAsync(url, content, bode).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getRecipients(
        id: String,
        type: String
    ): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.getRecipients(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                id,
                type
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getJWTToken(): UseCaseResult<String> {
        return try {
            val result =
                catApi.getJWTTokenAsync("Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey)
                    .await()
            UseCaseResult.Success(result.get(0).toString().replace("\"", ""))
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getInbox(): UseCaseResult<JsonObject> {
        return try {
            BASE_URL = CAT_API_MSG_URL
//            val result = catApi.getInboxN(
//                CAT_API_MSG_URL + "${SharedHelper(BaseApplication.applicationContext()).messageId}/inbox",
//                "Bearer " +SharedHelper(BaseApplication.applicationContext()).jwtToken
//            ).await()
            val result = catApi.getInboxNAsync(
                CAT_API_MSG_URL + "96c607b0-9a6c-4928-bd8c-8f332525fbe7/inbox",
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).jwtToken
            ).await()
//            val result = catApi.getInbox(
//                SharedHelper(BaseApplication.applicationContext()).jwtToken,
//                 SharedHelper(BaseApplication.applicationContext()).messageId
//            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }


    override suspend fun getOverDueCompleted(
        token: String,
        id: String
    ): UseCaseResult<DashboardOverdueResponse> {
        return try {
            val result = catApi.getOverDueCompletedAsync(id, token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getCollegeNid(token: String, id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getCollegeNidAsync(id, token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getColFactSheet(token: String, id: String): UseCaseResult<JsonObject> {
        return try {
            val url =
                "https://maia2-staging.maialearning.com/v2/atlas-static-data/college-factsheet/${id}.json"
            val result = catApi.getColFactSheetAsync(url).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getColFactSheetOther(
        token: String,
        id: String
    ): UseCaseResult<FactsheetModelOther> {
        return try {
            val url =
                "https://maia2-staging-backend.maialearning.com/ajs-services/all_international_factsheet_info/${id}"
            val result = catApi.getColFactSheetOtherAsync(url, token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getUniversityContact(
        token: String,
        id: String
    ): UseCaseResult<CollegeContactModel> {
        return try {
            val result = catApi.universityContactsAsync(id, token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getUniversityNotes(
        token: String,
        id: String,
        id2: String
    ): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.universityNotesAsync(id, id2, token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun updateStudentPlan(
        updateStudentPlan: UpdateStudentPlan
    ): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.editStudentPlanAsync(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                updateStudentPlan
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun searchUniversities(payload: UniversitySearchPayload): UseCaseResult<JsonObject> {
        return try {
            val result = if (payload.country == "DE") {
                catApi.germanUniverstiesAsync(
                    "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                    payload
                ).await()
            } else if (payload.country == "GB") {
                payload.sort_parameter = "name"
                catApi.ukUniversitiesAsync(
                    "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                    payload
                ).await()
            } else {
                catApi.searchUniverstiesAsync(
                    "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                    payload
                ).await()
            }
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun euroUniversities(payload: UniversitySearchPayload): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.euroUniverstiesAsync(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                payload
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun hitLikeUniv(
        studentId: String,
        collegeId: String
    ): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.hitLikeUniversityAsync(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                studentId, collegeId, ""
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun hitUnlikeUniv(
        studentId: String,
        collegeId: String
    ): UseCaseResult<Unit> {
        return try {
            val result = catApi.hitUnlikeUniversityAsync(
                collegeId, studentId,
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getUniversityList(
        status: String, uid: String
    ): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.getUniversityListAsync(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, status, uid
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun hitDelUnivCons(
        studentId: String,
        collegeId: String
    ): UseCaseResult<Unit> {
        return try {
            val result = catApi.hitDeleteUniversityConsideringAsync(
                collegeId, studentId,
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }


    override suspend fun moveToApplying(
        studentId: String,
        collegeId: String,
        status: String
    ): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.moveToApplyingAsync(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                studentId,
                collegeId,
                status
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun addProgramToConsidering(
        payload: AddProgramConsider
    ): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.addProgramToConsideringAsync(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, payload
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun deleteProgramCOnsider(id: String): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.deleteMlProgramAsync(
                id,
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun teacherList(id: String): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.getTeacherList(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getSurveys(url: String, token: String): UseCaseResult<SurveysResponse> {
        return try {
            val result = catApi.getSurveysAsync(url, token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getWebinar(url: String, token: String): UseCaseResult<WebinarResponse> {
        return try {
            val result = catApi.getWebinarsAsync(url, token).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun downloadWorkSheet(
        file_id: String,
        uuid: String,
        doc_type: String
    ): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.downloadWorkSheet(
                "Bearer ${SharedHelper(BaseApplication.applicationContext()).authkey}", ORIGIN,
                ORIGIN, uuid, file_id, doc_type
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun writeToCounselor(
        token: String,
        nid: String,
        response: String
    ): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.writeToCounselor(token, nid, response).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getAttachmentPResignedUrl(
        token: String,
        name: String,
        fm_uid: String
    ): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.uploadAttachmentFile(token, name, fm_uid).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun updateFileAttachData(
        token: String,
        updateUserData: FileUploadData
    ): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.updateFileAttachData(token, updateUserData).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getFMTags(): UseCaseResult<ArrayList<FmTagsResponseItem?>> {
        return try {
            val result = catApi.getTags(
                "Bearer ${SharedHelper(BaseApplication.applicationContext()).authkey}"
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun checkFileVirus(
        url: String,
        putUrl: String
    ): UseCaseResult<JsonObject> {
        return try {
            val obj = JsonObject()
            obj.addProperty("presigned", putUrl)
            val result = catApi.checkFileVirus(
                url,
                "Bearer ${SharedHelper(BaseApplication.applicationContext()).authkey}", obj
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun completeAssignment(nid: String, uid: String): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.completeTask(
                "Bearer ${SharedHelper(BaseApplication.applicationContext()).authkey}", nid, uid
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun resetTaskCompletion(uid: String): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.resetTaskCompleteion(
                "Bearer ${SharedHelper(BaseApplication.applicationContext()).authkey}", uid
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun sendRecom(recModel: RecModel): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.sendRecomTeacher(
                "Bearer ${SharedHelper(BaseApplication.applicationContext()).authkey}", recModel
            ).await()

            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getDecisionStatuses(): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getDecsionStatuses(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun recDeadline(): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.getRecDeadline(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun sendUcasRec(recModel: RecModel): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.sendUcasRec(
                "Bearer ${SharedHelper(BaseApplication.applicationContext()).authkey}", recModel
            ).await()

            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getRecomders(id: String, page: String): UseCaseResult<RecomdersModel> {
        return try {
            val result = catApi.getRecomders(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, id, page
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getRecomdersCollege(id: String, page: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getRecomdersCollege(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, id, page
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun cancelRecommedationRequest(id: String): UseCaseResult<Unit> {
        return try {
            val result = catApi.cancelRecommendationRequest(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getRecomType(id: String): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.getRecomType(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getDocumentPresignedURl(
        name: String,
        uid: String,
        docType: String,
        hash: String
    ): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getDocumetPresignedURl(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                name,
                uid,
                docType,
                hash
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun uploadDoc(url: String): UseCaseResult<Unit> {
        return try {
            val result = catApi.uploadDoc(
                url
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun uploadDocSaveBragsheet(
        id: String,
        name: String,
        url: String,
        exist: Int,
        path: String,
        hash: String
    ): UseCaseResult<Unit> {
        return try {
            val result = catApi.saveDocumentBragSheet(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, id,
                name,
                path,
                exist, url,
                hash
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getUniversities(id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getUniversities(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getMilestonesID(): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getMilestonesID(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getMilestones(milestoneId: String): UseCaseResult<MilestoneResponse> {
        return try {
            val result = catApi.getMilestones(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                "" + SharedHelper(BaseApplication.applicationContext()).id, milestoneId

            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun downloadBragSheet(
        file_id: String,
        uuid: String
    ): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.downloadBragSheet(
                "Bearer ${SharedHelper(BaseApplication.applicationContext()).authkey}",
                uuid, file_id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getBragSheet(
        id: String
    ): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getBragSheet(
                "Bearer ${SharedHelper(BaseApplication.applicationContext()).authkey}", id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun checkItask(iTaskId: String, studentID: String): UseCaseResult<Unit> {
        return try {
            val result = catApi.checkItask(
                "Bearer ${SharedHelper(BaseApplication.applicationContext()).authkey}",
                iTaskId,
                studentID
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun unCheckItask(iTaskId: String): UseCaseResult<Unit> {
        return try {
            val result = catApi.uncheckItask(
                "Bearer ${SharedHelper(BaseApplication.applicationContext()).authkey}",
                iTaskId
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun uploadRecoBragsheet(
        name: String,
        path: String,
        hash: String,
        id: String
    ): UseCaseResult<Unit> {
        return try {
            val result = catApi.uploadRecoBragSheet(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, id,
                name,
                path,
                hash
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getCollegeJsonFilter(
        url: String,
        body: UnivCollegeModel
    ): UseCaseResult<JsonObject> {
        val arr = JsonArray()
        for (i in body.university_nids) {
            arr.add(i)
        }

        val jsonObj = JsonObject()
        jsonObj.add("university_nids", arr)
        return try {
            val result = catApi.getCollegeJsonFilter(
                url,
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, body
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getCareerListing(id: String): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.getCareerTopPicks(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getCareerListingDetail(
        id: String,
        url: String
    ): UseCaseResult<JsonObject> {
        return try {

            val result = catApi.getCareerTopPicksDetails(
                "$url$id$CAREER_FACTSHEET"

            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getKeyboardSearch(url: String): UseCaseResult<CareerSearchCodesModel> {
        return try {

            val result = catApi.getKeyboardSearch(
                url,
                ORIGIN, ACCEPT_JSON
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getCareerCluster(url: String): UseCaseResult<CareerClusterModel> {
        return try {

            val result = catApi.getCareerCluster(
                url,
                ORIGIN, ACCEPT_JSON
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getCareerClusterList(url: String): UseCaseResult<CareerClusterListModel> {
        return try {

            val result = catApi.getCareerClusterList(
                url,
                ORIGIN, ACCEPT_JSON
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getIndustryList(url: String): UseCaseResult<IndustryListModel> {
        return try {

            val result = catApi.getIndustryList(
                url,
                ORIGIN, ACCEPT_JSON
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getCareerClusterList(list: ArrayList<String>): UseCaseResult<ArrayList<BrightOutlookModel.Data>> {
        return try {
            val object_ = CareerListModel()
            object_.onet_code = list
            object_.onet_year = 2019
            val result = catApi.getCareerSearchList(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, object_
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getCareerBright(type: String): UseCaseResult<BrightOutlookModel> {
        return try {

            val object_ = BrightLook()
            object_.bo_key = type
            object_.pager = 1
            val result = catApi.getCareerBright(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, object_
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getKeywoardSearchDetails(
        url: String,
        list: ArrayList<String>
    ): UseCaseResult<JsonArray> {
        return try {
            val searchCodesModel = SearchkeywordRequestModel()
            searchCodesModel.onet_code = list

            val result = catApi.getKeywoardSearchDetails(
                url,
                ORIGIN, ACCEPT_JSON, searchCodesModel
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getWorkSearch(url: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getWorkList(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, url
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getNYSCareer(id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getNYSCareerPlan(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getUSSearch(url: String): UseCaseResult<CareerUSModel> {
        return try {
            val result = catApi.getUsList(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, url
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getStudentCareerPlan(id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getStudentCareer_Plan(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getcountryFilter(): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getcountryFilterAsync().await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getSaveCountry(): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.getSaveCountryAsync(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                "" + SharedHelper(BaseApplication.applicationContext()).id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun setSaveCountry(): UseCaseResult<JsonArray> {
        return try {
            val saveModel = SaveCountryModel()
            saveModel.country_code =
                SharedHelper(BaseApplication.applicationContext()).country ?: ""
            saveModel.user_id = SharedHelper(BaseApplication.applicationContext()).id ?: ""
            val result = catApi.setSaveCountryAsync(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                saveModel
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getCareerComparisons(body: CompareCareerBody): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.compareCareers(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, body
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getCountriesContinentBased(code: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getCountriesContinentBased(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, code
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getStudentTopPick(id: String): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.getStudentTopPick(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getVideoCode(url: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getVideoCode(
                url
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getExperiences(id: String): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.getExperiences(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey, id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }


    override suspend fun likeRelatedCareer(content: CreateContent): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.createContent(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                content
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun unlikeRelatedCareer(content: DeleteContent): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.deleteContent(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                content
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getProgramListDetail(id: String): UseCaseResult<CourseModelOptionDetailResponse> {
        return try {
            val result = catApi.getProgramListDetail(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getCollegeEssay(
        id: String,
        page: String,
        sortBy: String,
        sortOrder: String
    ): UseCaseResult<CollegeEssayResponse> {
        return try {
            val result = catApi.getCollegeEssay(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                id, page, sortBy, sortOrder
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun deleteCollegeEssay(id: String): UseCaseResult<Unit> {
        return try {
            val result = catApi.deleteCollegeEssay(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getSubDiscipline(id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getSubDiscipline(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getGermanChildSubject(id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getSubChildSubject(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getGbSubChild(id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getGBSubChildSubject(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getFosChild(id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getFosChild(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getFosOther(): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getFosOther(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getApplyingWIth(): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.getApplyingWith(
                CONSIDERING_APPLYING_WITH
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun bulkCollegeMoving(
        payload: BulkCollegeMovePayload
    ): UseCaseResult<Unit> {
        return try {
            val result = catApi.bulkCollegeMoving(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                payload
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getTestScores(id: String): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.getTestScores(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun testScoreStatusSubmit(payload: TestScoreSubmitPayload): UseCaseResult<Unit> {
        return try {
            val result = catApi.testScoreSubmit(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                payload
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun checkAllTranscripts(
        id: String,
        value: Int,
        ncaa: Int
    ): UseCaseResult<Unit> {
        return try {
            val obj = JsonObject()
            obj.addProperty("student_uid", id)
            obj.addProperty("value", value)
            if (ncaa !== 0) {
                obj.addProperty("ncaa", ncaa)
            }

            val result = catApi.checkAllTranscript(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                obj
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun checkReqTranscripts(
        id: String,
        value: Int
    ): UseCaseResult<JsonObject> {
        return try {
            val obj = JsonObject()
            obj.addProperty("student_uid", id)
            obj.addProperty("value", value)

            val result = catApi.checkReqTranscript(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                obj
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun getStudentRecommendPrefrence(
        schoolId: String,
        studentId: String
    ): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.studentPrefferedRecommenders(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                schoolId, studentId
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun compareAllColleges(id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.compareALl(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun cancelRound(id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.cancelRound(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                id
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun savePrefReco(arr: PrefferedRecoSaveModel): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.savePrefReco(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                arr
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun addUniversityCollSearch(payload: UniversitySearchPayload): UseCaseResult<JsonObject> {
        return try {
            val result = if (payload.country == "DE") {
                catApi.germanUniverstiesAsync(
                    "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                    payload
                ).await()
            } else if (payload.country == "GB") {
                payload.sort_parameter = "name"
                catApi.ukUniversitiesAsync(
                    "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                    payload
                ).await()
            } else {
                catApi.addUniverstiesCollegeSearch(
                    "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                    payload
                ).await()
            }
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

    override suspend fun addUniversity(id: String, cId: String): UseCaseResult<Unit> {
        return try {
            val result = catApi.addUniversity(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                id, cId
            ).await()
            UseCaseResult.Success(result)
        } catch (ex: HttpException) {
            UseCaseResult.Error(ex)
        } catch (ex: Exception) {
            UseCaseResult.Exception(ex)
        }
    }

}