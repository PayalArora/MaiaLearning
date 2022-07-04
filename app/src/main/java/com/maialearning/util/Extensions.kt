package com.maialearning.util

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.maialearning.network.AllAPi
import com.maialearning.repository.LoginRepository
import com.maialearning.repository.LoginRepositoryImpl
import com.maialearning.viewmodel.LoginNewModel
import com.maialearning.viewmodel.LoginViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val CAT_API_BASE_URL = "https://app-www-maia.maialearning.com/ajs-services/"

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
            baseUrl = CAT_API_BASE_URL
        )
    }
    // Tells Koin how to create an instance of CatRepository
    factory<LoginRepository> { LoginRepositoryImpl(catApi = get()) }
    // Specific viewModel pattern to tell Koin how to build MainViewModel
    viewModel { LoginNewModel(catRepository = get()) }
}
fun createHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    client.readTimeout(5 * 60, TimeUnit.SECONDS)
    return client.addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.header("Content-Type", "application/json, text/plain, */")
        val request = requestBuilder.method(original.method(), original.body()).build()
        return@addInterceptor it.proceed(request)
    }.build()
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