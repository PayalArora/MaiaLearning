package com.maialearning.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import androidx.viewbinding.BuildConfig
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.maialearning.R
import com.maialearning.network.AllAPi
import com.maialearning.network.AllMessageAPi
import com.maialearning.network.BaseApplication
import com.maialearning.repository.LoginRepository
import com.maialearning.repository.LoginRepositoryImpl
import com.maialearning.repository.MessageRepository
import com.maialearning.repository.MessageRepositoryImpl
import com.maialearning.ui.activity.LoginActivity
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.viewmodel.HomeViewModel
import com.maialearning.viewmodel.LoginNewModel
import com.maialearning.viewmodel.LoginViewModel
import com.maialearning.viewmodel.ProfileViewModel
import com.maialearning.viewmodel.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

const val CAT_API_BASE_URL = "https://app-www-maia.maialearning.com/ajs-services/"

//const val CAT_API_MSG_URL = "https://msg-staging.maialearning.com/v1/messaging/users/"
const val CAT_API_MSG_URL = "https://maia2-msg.maialearning.com/"
var BASE_URL = "https://maia2-staging-backend.maialearning.com/ajs-services/"
var UNIV_LOGO_URL="https://college-images-staging.maialearning.com/"
const val ORIGIN = "https://maia2-staging.maialearning.com"
const val TITLE = "title"
const val DESCRIPTION = "description"

object URL {
    var BASEURL = 0
}

fun Context.isNetworkConnected(): Boolean {
    val connectivityManager: ConnectivityManager? =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    var isConnected = false
    if (connectivityManager != null) {
        val activeNetwork = connectivityManager.activeNetworkInfo
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
    return isConnected
}

fun showLog(tag: String?, content: String) {
    Log.d(tag, content)
}

val appModules = module {
    // The Retrofit service using our custom HTTP client instance as a singleton
    single {
        createWebService<AllAPi>(
            okHttpClient = createHttpClient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = BASE_URL
        )
//        createWebService<AllMessageAPi>(
//            okHttpClient = createHttpClient(),
//            factory = RxJava2CallAdapterFactory.create(),
//            baseUrl = CAT_API_MSG_URL
//        )
    }
    // Tells Koin how to create an instance of CatRepository
    factory<LoginRepository> { LoginRepositoryImpl(catApi = get()) }

    // Specific viewModel pattern to tell Koin how to build MainViewModel
    viewModel {
        LoginNewModel(catRepository = get())
    }
    viewModel { HomeViewModel(catRepository = get()) }
    viewModel { DashboardViewModel(catRepository = get()) }
    viewModel { FactSheetModel(catRepository = get()) }

    viewModel { ProfileViewModel(catRepository = get()) }
    viewModel { DashboardFragViewModel(catRepository = get()) }

}
val appModules1 = module {
    single {
        createWebService<AllMessageAPi>(
            okHttpClient = createHttpClient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = CAT_API_MSG_URL
        )
    }
    factory<MessageRepository> { MessageRepositoryImpl(catApi = get()) }
    viewModel { MessageViewModel(catRepository = get()) }
}

fun createHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    client.readTimeout(5 * 60, TimeUnit.SECONDS)
//    val loggingInterceptor = HttpLoggingInterceptor()
//    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//    return client.addInterceptor {
//        val original = it.request()
//        val requestBuilder = original.newBuilder()
//        requestBuilder.header("Content-Type", "application/json, text/plain, */")
//        val request = requestBuilder.method(original.method(), original.body()).build()
//        return@addInterceptor it.proceed(request)
//    }.build()

    return if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client
            .addInterceptor(loggingInterceptor)
            .build()
    }
}


fun getDate(timestamp: Long, format: String): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = timestamp * 1000L
    val date = DateFormat.format(format, calendar).toString()
    return date
}

inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    factory: CallAdapter.Factory, baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(factory)
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}

private fun checkToken(ex: HttpException, con: Context) {
    if (ex.code() == 401) {
        val intent = Intent(con, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // To clean up all activities
        con.startActivity(intent)
        (con as Activity).finish()
    }
}

fun parseNA(string: String?): String {
    if (string == null || string == "null") {
        return BaseApplication.applicationContext().getString(R.string.na)
    } else {
        return string
    }
}

fun parsePercentNA(string: String?): String {

    if (string == null || string == "null") {
        return BaseApplication.applicationContext().getString(R.string.na)
    } else {
        return "${string}%"
    }

}

fun parsePercentZero(string: String?): String {

    if (string == null || string == "null") {
        return "${BaseApplication.applicationContext().getString(R.string.zero)}%"
    } else {
        return "${string}%"
    }

}
fun parsePercentDollar(string: Any?): String {

    if (string == null || string.toString() == "null") {
        return "$${BaseApplication.applicationContext().getString(R.string.zero)}"
    } else {
        return "$${string}"
    }

}
fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}
