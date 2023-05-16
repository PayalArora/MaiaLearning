package com.maialearning.parser

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.maialearning.model.CareerListResponse

class CareerListParser(val it: JsonObject) {
    fun parse():CareerListResponse?{
        val gson:Gson = GsonBuilder().create()
        var careerListResponse:CareerListResponse?= null
        //val jobj = JsonObject(it.toString())
        val iter: Iterator<String> = it.keySet().iterator()
        val arrCareer: ArrayList<CareerListResponse.CareerItem> = arrayListOf()
        while (iter.hasNext()) {
            val key = iter.next()
        val jobjCareerItem = it.get(key)
            val itModel = gson.fromJson(jobjCareerItem, CareerListResponse.CareerItem::class.java)
            arrCareer?.add(itModel)
        }
        println("career ${arrCareer.size}")
        careerListResponse = CareerListResponse(arrCareer)
        return careerListResponse
    }
}