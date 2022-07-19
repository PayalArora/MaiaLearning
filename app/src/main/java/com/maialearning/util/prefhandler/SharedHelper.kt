package com.maialearning.util.prefhandler

import android.content.Context
import com.maialearning.util.prefhandler.SharedPreference

class SharedHelper(var context: Context) {
    var sharedPreference: SharedPreference? = null
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
    var id:String?
        get() = sharedPreference!!.getKey("id")
        set(authkey) {
            sharedPreference!!.putKey("id", authkey)
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
}