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

}