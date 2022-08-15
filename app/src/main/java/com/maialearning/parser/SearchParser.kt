package com.maialearning.parser

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.maialearning.model.UniversitiesSearchModel
import com.maialearning.model.UniversitySearchResponse
import com.maialearning.network.UseCaseResult
import org.json.JSONObject


class SearchParser(val it: JsonObject) {
    fun parseJson(): UniversitySearchResponse{
        var universitySearchResponse= UniversitySearchResponse()
        universitySearchResponse.last=it.get("last").asInt
        universitySearchResponse.pager=it.get("pager").asInt
        universitySearchResponse.totalRecords=it.get("total_records").asInt
        var arrayList= arrayListOf<UniversitiesSearchModel>()
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
}