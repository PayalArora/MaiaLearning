package com.maialearning.parser

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.maialearning.model.CollegeFactSheetModel
import com.maialearning.model.GermanUniversitiesResponse
import com.maialearning.model.UniversitiesSearchModel
import com.maialearning.model.UniversitySearchResponse
import com.maialearning.network.UseCaseResult
import org.json.JSONObject


class SearchParser(val it: JsonObject) {
    fun parseJson(): UniversitySearchResponse{
        val universitySearchResponse= UniversitySearchResponse()
        universitySearchResponse.last=it.get("last").asInt
        universitySearchResponse.pager=it.get("pager").asInt
        universitySearchResponse.totalRecords=it.get("total_records").asInt
        val arrayList= arrayListOf<UniversitiesSearchModel>()
        val json = JSONObject(it.toString())
        val x =json.keys() as Iterator<String>
        x.next()
        x.next()
        x.next()
        while (x.hasNext()) {
            val key: String = x.next()
            val obj = it.getAsJsonObject(key)
            val gson = Gson()
            val responseModel: UniversitiesSearchModel = gson.fromJson(
                obj,
                UniversitiesSearchModel::class.java
            )
            arrayList.add(responseModel)

        }
        universitySearchResponse.university_list = arrayList

        return universitySearchResponse


    }

    fun parseGermanJson(): GermanUniversitiesResponse{
        val gson = GsonBuilder().create()
        val itModel = gson.fromJson(it, GermanUniversitiesResponse::class.java)
//        val universitySearchResponse= GermanUniversitiesResponse()
//        universitySearchResponse.pager?.last=(it.get("pager") as JsonObject).get("last").asInt
//        universitySearchResponse.pager?.current=(it.get("pager") as JsonObject).get("current").asInt
//        universitySearchResponse.pager?.total=(it.get("pager") as JsonObject).get("total").toString()
//        val arrayList= arrayListOf<GermanUniversitiesResponse.Data.CollegeData>()
//        val json = JSONObject(it.toString())
//        val x =json.keys() as Iterator<String>
//        x.next()
//        x.next()
//        x.next()
//        while (x.hasNext()) {
//            val key: String = x.next()
//            val obj = it.getAsJsonObject(key)
//            val gson = Gson()
//            val responseModel: GermanUniversitiesResponse.Data.CollegeData = gson.fromJson(
//                obj,
//                GermanUniversitiesResponse.Data.CollegeData::class.java
//            )
//            arrayList.add(responseModel)
//
//        }
//        universitySearchResponse.data?.collegeData = arrayList

        return itModel


    }
}