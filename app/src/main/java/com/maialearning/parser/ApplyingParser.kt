package com.maialearning.parser

import com.google.gson.JsonObject
import com.maialearning.model.ConsiderModel
import com.maialearning.model.StatusModel
import org.json.JSONArray
import org.json.JSONObject

class ApplyingParser(val it:JsonObject, val userId:String,  val statuses : ArrayList<StatusModel>) {
    val finalArray: ArrayList<ConsiderModel.Data> = ArrayList()
    fun parseJson(): ArrayList<ConsiderModel.Data> {
        val json = JSONObject(it.toString()).getJSONObject(userId).getJSONObject("data")

        val x = json.keys() as Iterator<String>
        val jsonArray = JSONArray()
        while (x.hasNext()) {
            val key: String = x.next().toString()
            jsonArray.put(json.get(key))
        }
        val countries = ArrayList<String>()
        val array: ArrayList<ConsiderModel.Data> = ArrayList()
        for (i in 0 until jsonArray.length()) {
            val object_ = jsonArray.getJSONObject(i)
            val arrayProgram: ArrayList<ConsiderModel.ProgramData> = ArrayList()
            var programArray = object_.getJSONArray("app_by_program_data")
            for (j in 0 until programArray.length()) {
                val objectProgram = programArray.getJSONObject(j)
                arrayProgram.add(
                    ConsiderModel.ProgramData(
                        objectProgram.getInt("program_id"),
                        objectProgram.getString("program_name"),
                        objectProgram.getString("program_deadline"),
                        objectProgram.getString("program_status"),
                        getStatus(objectProgram.getString("program_status"))
                    )
                )
            }
            var arrayCounselor: ArrayList<ConsiderModel.CounselorNotes> =
                arrayListOf()
            /*       var counselorNotes = object_.getJSONArray("counselor_notes")
                   if (counselorNotes !is JSONArray && counselorNotes.length() != 0) {
                       if (counselorNotes is JSONObject) {
                           val x = counselorNotes.keys() as Iterator<String>
                           while (x.hasNext()) {
                               var json: JSONObject = counselorNotes.get(x.next()) as JSONObject
                               val notesObj: ConsiderModel.CounselorNotes =
                                   ConsiderModel.CounselorNotes(json.optString("id"),
                                       json.optString("uid"),
                                       json.optString("counselor_note"),
                                       json.optString("first_name"),
                                       json.optString("last_name"))
                               arrayCounselor.add(notesObj)
                           }


                       }
                   }*/
            var requiredRecs: ConsiderModel.RequiredRecommendation? = null
            val jobj:JSONObject?=object_.optJSONObject("required_recommendation")
            jobj?.let {
                requiredRecs = ConsiderModel.RequiredRecommendation(it.optString("teacher_evaluation"),
                    it.optString("max_teacher_evaluation"), it.optString("counselor_recommendation") )
            }
            if (!countries.contains(object_.getString("country_name")))
                countries.add(object_.getString("country_name"))
            val model: ConsiderModel.Data = ConsiderModel.Data(
                object_.getInt("contact_info"),
                object_.getInt("parchment"),
                object_.getInt("slate"),
                object_.getInt("transcript_nid"),
                object_.getString("university_nid"),
                object_.getString("name"),
                object_.getString("country"),
                object_.getString("country_name"),
                object_.getString("created_by_name"),
                object_.getString("created_date"),
                object_.getString("college_priority_choice"),
                object_.getString("university_nid"),
                object_.getString("unitid"),
                object_.getString("internal_deadline"),
                object_.getString("due_date"),
                arrayProgram,
                0,
                object_.getString("notes"),
                arrayCounselor, object_.getString("request_transcript"),
                object_.getString("application_type"),
                object_.optString("application_term"),
                object_.getString("application_mode"),
                object_.getString("application_status_name"),
                object_.getString("app_by_program_supported"),
                object_.getInt("confirm_applied"),
                null, requiredRecs,
                object_.getInt("manual_update")
            )
            array.add(model)
        }

        var pos = 0
        // countries.sortBy { it }
        for (j in 0 until countries.size) {
            var firstTime = true
            var count = 0
            for (k in 0 until array.size) {
                if (countries[j] == array[k].country_name) {
                    count += 1
                    if (firstTime) {
                        firstTime = false
                        array[k].country_name = countries[j]
                    } else {
                        array[k].country_name = ""
                    }
                    finalArray.add(array[k])
                    finalArray[pos].count = count
                }
            }
            pos = finalArray.size
        }
        finalArray.sortBy { it.naviance_college_name }
        return finalArray
    }

    fun getStatus(program_status: String? ): String? {
        for (i in statuses) {
            if (i.key == program_status) {
                return i.status
            }
        }
        return program_status
    }

}