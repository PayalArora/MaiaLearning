package com.maialearning.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.text.TextUtils
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Toast
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
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val CAT_API_BASE_URL = "https://app-www-maia.maialearning.com/ajs-services/"
const val CAT_API_MSG_URL = "https://msg-staging.maialearning.com/"
//const val CAT_API_MSG_URL = "https://maia2-msg.maialearning.com/"
var BASE_URL = "https://maia2-staging-backend.maialearning.com/ajs-services/"
var UNIV_LOGO_URL="https://college-images-staging.maialearning.com/"
const val ORIGIN = "https://maia2-staging.maialearning.com"
const val ACCEPT_JSON = "application/json, text/plain, */*"
const val ML_URL = "https://ml-api-staging.maialearning.com/"
const val ANTI_VIRUS = "https://api-gw-staging.maialearning.com/ml-s3-antivirus-status"
const val CARRER_URL = "https://maia2-staging.maialearning.com/atlas-static-data/career-factsheet/"
const val CAREER_FACTSHEET = "/career_search_factsheet.json"
const val TITLE = "title"
const val DESCRIPTION = "description"
const val LOGINRESPONSE = "login_response"
const val SEARCH_AFFINITY = "https://services.onetcenter.org/v1.9/ws/mnm/search?keyword="
const val CAREER_AFFINITY = "https://services.onetcenter.org/v1.9/ws/online/career_clusters?client=serviceinfinity"
const val CAREER_LIST = "https://services.onetcenter.org/v1.9/ws/online/career_clusters/"
const val INDUSTRY_LIST = "https://services.onetcenter.org/v1.9/ws/online/industries/"
const val CAREER_CLIENT = "?client=serviceinfinity"
const val SEARCH_CLIENT = "&client=serviceinfinity"
const val SEARCH_KEYWORD = "https://app-www-maia.maialearning.com/ajs-services/career_search_onet"
const val US_SEARCH= "get_military_careers_data?pager=1&service="
var COLLEGE_JSON = "https://api-gw-staging.maialearning.com/college-json-filter"
object URL{
    var BASEURL = 0
}

fun String.getURLSearch():String{
    return "$SEARCH_AFFINITY$this$SEARCH_CLIENT"
}
fun String.getURLCluster():String{
    return "$CAREER_LIST$this$CAREER_CLIENT"
}
fun String.getURLIndustry():String{
    return "$INDUSTRY_LIST$this$CAREER_CLIENT"
}
fun String.getUSIndustry():String{
    return "$BASE_URL$US_SEARCH$this$CAREER_CLIENT"
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
    viewModel { HomeViewModel(catRepository = get())}
    viewModel { DashboardViewModel(catRepository = get())}

    viewModel { FactSheetModel(catRepository = get()) }
    viewModel { CareerViewModel(catRepository = get()) }
    viewModel { ProfileViewModel(catRepository = get()) }
    viewModel { DashboardFragViewModel(catRepository = get()) }
    viewModel { RelatedCareerViewModel(catRepository = get()) }

    viewModel { PortfolioViewModel(catRepository = get()) }
}
val appModules1 = module{
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

    return if (BuildConfig.DEBUG){
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

fun getDateTime(s: String, format: String): String? {
    try {
        val sdf = SimpleDateFormat(format)
        val netDate = Date((s.toLong()))
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
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
fun getAbbreviatedFromDateTime(dateTime: String, dateFormat: String, field: String): String? {
    val input = SimpleDateFormat(dateFormat)
    val output = SimpleDateFormat(field)
    try {
        val getAbbreviate = input.parse(dateTime)    // parse input
        return output.format(getAbbreviate)    // format output
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return null
}
fun formateDateFromstring(
    inputFormat: String?,
    outputFormat: String?,
    inputDate: String?
): String? {
    var parsed: Date? = null
    var outputDate = ""
    val df_input = SimpleDateFormat(inputFormat)
    val df_output = SimpleDateFormat(outputFormat)
    try {
        parsed = df_input.parse(inputDate)
        outputDate = df_output.format(parsed)
    } catch (e: ParseException) {
    }
    return outputDate
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

fun parseEmpty(string: String?): String {
    if (string == null || string == "null" || string.isNullOrEmpty()) {
        return ""
    } else {
        return string+ "\n"+ "\n"
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


fun String.replaceNextLine(): String {
    return this.replace("\n", " ").replace("\t", " ").replace("\r", " ")
}
fun String.replaceInvertedComas():String {
    return this.replace("\"", "")
}

fun String.replaceCrossBracketsComas():String {
    return this.replace("[", "").replace("]", "").replace("\"", "")
}

fun Context.showToast(it:String){
    this.let {it1->
        Toast.makeText(it1, it, Toast.LENGTH_SHORT).show()
    }
}


@Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
fun String.getMd5Hash(): String? {
    val md: MessageDigest = MessageDigest.getInstance("MD5")
    val thedigest: ByteArray = md.digest(this.toByteArray(charset("UTF-8")))
    val hexString = java.lang.StringBuilder()
    for (i in thedigest.indices) {
        val hex = Integer.toHexString(0xFF and thedigest[i].toInt())
        if (hex.length == 1) hexString.append('0')
        hexString.append(hex)
    }
    return hexString.toString().toUpperCase()
}