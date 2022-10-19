package com.maialearning.parser

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.maialearning.model.*
import com.maialearning.network.UseCaseResult
import org.json.JSONArray
import org.json.JSONObject


class SearchParser(val it: JsonObject) {
    fun parseJson(): UniversitySearchResponse {
        val universitySearchResponse = UniversitySearchResponse()
        if (it.has("last"))
            universitySearchResponse.last = it.get("last").asInt
        if (it.has("pager"))
            universitySearchResponse.pager = it.get("pager").asInt
        if (it.has("total_records"))
            universitySearchResponse.totalRecords = it.get("total_records").asInt
        val arrayList = arrayListOf<UniversitiesSearchModel>()
        val json = JSONObject(it.toString())
        val x = json.keys() as Iterator<String>
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

    fun parseGermanJson(): GermanUniversitiesResponse {
        val gson = GsonBuilder().create()
        val itModel = gson.fromJson(it, GermanUniversitiesResponse::class.java)
        return itModel
    }

    fun parseEuropeanJson(): EuropeanUniList {
        val gson = GsonBuilder().create()
        val itModel = gson.fromJson(it, EuropeanUniList::class.java)
        return itModel
    }

    fun parseUkJson(): UkResponseModel {
        val gson = GsonBuilder().create()
        val itModel = gson.fromJson(it, UkResponseModel::class.java)
        val itModelNew = ArrayList<UkResponseModel.Data.CollegeData>()
        val json = JSONObject(it.toString()).getJSONObject("data").getJSONArray("college_data")
        for (i in 0 until json.length()) {
            val varArray: ArrayList<CourseListModel> =
                ArrayList()
            val data = json.getJSONObject(i)
            val jsonVar = data.getJSONObject("course_list")
            val varList = ArrayList<String>()
            val x1 = jsonVar.keys() as Iterator<String>
            val jsonVarArray = JSONArray()
            while (x1.hasNext()) {
                val key: String = x1.next().toString()
                jsonVarArray.put(jsonVar.get(key))
                varList.add(key)
            }
            for (j in 0 until jsonVarArray.length()) {
                val objectProgram = jsonVarArray.getJSONObject(j)
                varArray.add(
                    CourseListModel(
                        objectProgram.getString("course_id") ?: "",
                        objectProgram.getString("course_name") ?: "",
                        objectProgram.getString("option_count") ?: "",
                        objectProgram.getString("a_level") ?: "",
                        objectProgram.getString("ib") ?: ""
                    )
                )

            }
            itModelNew.add(
                UkResponseModel.Data.CollegeData(
                    data.getString("college_nid"),
                    data.getString("college_name"),
                    data.getInt("parchment"),
                    data.getInt("slate"),
                    data.getString("course_count"),
                    data.getInt("top_pick_flag"),
                    data.getString("file_name"),
                    varArray
                )
            )
        }
        itModel.data?.collegeData = itModelNew
        return itModel
    }
}