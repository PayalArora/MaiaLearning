package com.maialearning.util.prefhandler

import android.content.Context
import com.google.gson.Gson
import com.maialearning.model.LoginNewModel
import com.maialearning.util.LOGINRESPONSE

class SharedHelper(var context: Context) {
    var sharedPreference: SharedPreference? = null
    val gson = Gson()
    init {
        sharedPreference=SharedPreference(context)
    }

    var login: String?
        get() = sharedPreference!!.getKey("login")
        set(user_name) {
            sharedPreference!!.putKey("login", user_name)
        }
    var expire:String?
        get() = sharedPreference!!.getKey("login")
        set(user_name) {
            sharedPreference!!.putKey("login", user_name)
        }
    var authkey:String?
        get() = sharedPreference!!.getKey("authkey")
        set(authkey) {
            sharedPreference!!.putKey("authkey", authkey)
        }

    var jwtToken:String
        get() = sharedPreference!!.getKey("jwtToken")
        set(authkey) {
            sharedPreference!!.putKey("jwtToken", authkey)
        }
    var messageId:String
        get() = sharedPreference!!.getKey("messageId")
        set(authkey) {
            sharedPreference!!.putKey("messageId", authkey)
        }
    var auditId:String
        get() = sharedPreference!!.getKey("auditId")
        set(authkey) {
            sharedPreference!!.putKey("auditId", authkey)
        }
    var id:String?
        get() = sharedPreference!!.getKey("id")
        set(authkey) {
            sharedPreference!!.putKey("id", authkey)
        }
    var schoolId:String?
        get() = sharedPreference!!.getKey("schoolId")
        set(authkey) {
            sharedPreference!!.putKey("schoolId", authkey)
        }
    var uuid:String?
        get() = sharedPreference!!.getKey("uuid")
        set(authkey) {
            sharedPreference!!.putKey("uuid", authkey)
        }
    var ethnicityTarget:String?
        get() = sharedPreference!!.getKey("target_id")
        set(authkey) {
            sharedPreference!!.putKey("target_id", authkey)
        }
    var convention:Boolean?
        get() = sharedPreference!!.getBooleanKey("convention")
        set(convention) {
            sharedPreference!!.putBooleanKey("convention", convention)
        }
    var picture:String?
        get() = sharedPreference!!.getKey("picture")
        set(picture) {
            sharedPreference!!.putKey("picture", picture)
        }
    var collegeNId:String
        get() = sharedPreference!!.getKey("collegenId")
        set(collegeId) {
            sharedPreference!!.putKey("collegenId", collegeId)
        }
    var appResponse: LoginNewModel?
        get() = gson.fromJson(sharedPreference!!.getKey(LOGINRESPONSE).toString(),
            LoginNewModel::class.java)
        set(value) = sharedPreference!!.putKey (
           LOGINRESPONSE, gson.toJson(value))

    var country:String?
        get() = sharedPreference!!.getKey("country")
        set(country) {
            sharedPreference!!.putKey("country", country)
        }
    var countryName:String?
        get() = sharedPreference!!.getKey("countryName")
        set(country) {
            sharedPreference!!.putKey("countryName", country)
        }
}