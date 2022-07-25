package com.maialearning.network

import android.app.Application
import android.content.Context
import com.maialearning.util.appModules
import com.maialearning.util.appModules1
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: BaseApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // Adding Koin modules to our application
        val context: Context = BaseApplication.applicationContext()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@BaseApplication)
            modules(appModules)
            modules(appModules1)
        }

    }

}
