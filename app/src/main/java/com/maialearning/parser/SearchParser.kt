package com.maialearning.parser

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.maialearning.model.*
import com.maialearning.network.UseCaseResult
import org.json.JSONObject


class SearchParser(val it: JsonObject) {
    fun parseJson(): UniversitySearchResponse{
        val universitySearchResponse= UniversitySearchResponse()
        if (it.has("last"))
        universitySearchResponse.last=it.get("last").asInt
        if (it.has("pager"))
        universitySearchResponse.pager=it.get("pager").asInt
        if (it.has("total_records"))
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
        return itModel
    }
    fun parseEuropeanJson(): EuropeanUniList{
        val gson = GsonBuilder().create()
        val itModel = gson.fromJson(it, EuropeanUniList::class.java)
        return itModel
    }
    fun parseUkJson(): UkResponseModel{
        val gson = GsonBuilder().create()
        val itModel = gson.fromJson(it, UkResponseModel::class.java)
        return itModel
    }
}