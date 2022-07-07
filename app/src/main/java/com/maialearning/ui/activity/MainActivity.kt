package com.maialearning.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.maialearning.R
import com.maialearning.util.prefhandler.SharedHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler(Looper.getMainLooper()).postDelayed({
            if (SharedHelper(this).login != "1") {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                startActivity(Intent(this, DashboardActivity::class.java))
            }
        }, SPLASH_TIMEOUT)

    }


    companion object {
        const val SPLASH_TIMEOUT = 2000L
    }
}