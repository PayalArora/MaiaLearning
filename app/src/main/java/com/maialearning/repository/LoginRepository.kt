package com.maialearning.repository

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.model.*
import com.maialearning.network.AllAPi
import com.maialearning.network.BaseApplication
import com.maialearning.network.UseCaseResult
import com.maialearning.util.BASE_URL
import com.maialearning.util.CAT_API_MSG_URL
import com.maialearning.util.ORIGIN

import retrofit2.HttpException

import com.maialearning.util.prefhandler.SharedHelper
import okhttp3.RequestBody

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
    suspend fun getConsiderList(id: String): UseCaseResult<JsonObject>
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
        token: String
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

    override suspend fun getConsiderList(id: String): UseCaseResult<JsonObject> {
        return try {
            val result = catApi.considerListAsync(
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
        token: String
    ): UseCaseResult<JsonArray> {
        return try {
            val result = catApi.getUniversityListAsync(
                "https://app-www-maia.maialearning.com/ajs-services/university-list?status=1&uid=9375",
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
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
}