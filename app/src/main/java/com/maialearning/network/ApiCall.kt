package com.maialearning.network

import android.content.Context
import android.os.StrictMode
import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject


interface ApiCall {
    suspend fun getCatList(): UseCaseResult<JsonObject>
    suspend fun getList(): UseCaseResult<JsonArray>
}


//object ApiCall {
//
//    var MY_SOCKET_TIMEOUT_MS = 50000
//    var TAG = ApiCall::class.java.simpleName
//
//    fun postMethod(
//        input: ApiInput,
//        setResponseSuccess: (JSONObject) -> Unit,
//        setErrorResponse: (String) -> Unit
//    ) {
//        showLog("$TAG Request", "${input.url.toString()} ${input.jsonObject.toString()}")
//        if (input.context!!.isNetworkConnected()) {
//
//            var jsonObjectRequest =
//                object : JsonObjectRequest(Method.POST, input.url, input.jsonObject, {
//                    setResponseSuccess(it)
//                    showLog(
//                        "$TAG response",
//                        "${input.url.toString()} ${input.jsonObject.toString()} ${it.toString()}"
//                    )
//                }, {
//                    if (it is TimeoutError || it is NoConnectionError) {
//                        input.context?.getString(R.string.no_internet_connection)
//                            ?.let { it1 -> setErrorResponse(it1) }
//                    } else if (it is AuthFailureError) {
//                        input.context?.getString(R.string.session_expired)
//                            ?.let { it1 -> setErrorResponse(it1) }
//                    } else if (it is ServerError) {
//                        input.context?.getString(R.string.server_error)
//                            ?.let { it1 -> setErrorResponse(it1) }
//                    } else if (it is NetworkError) {
//                        input.context?.getString(R.string.network_error)
//                            ?.let { it1 -> setErrorResponse(it1) }
//                    } else if (it is ParseError) {
//                        input.context?.getString(R.string.parsing_error)
//                            ?.let { it1 -> setErrorResponse(it1) }
//                    } else {
//                        input.context?.getString(R.string.network_error)
//                            ?.let { it1 -> setErrorResponse(it1) }
//                    }
//                }) {
//                    override fun getHeaders(): MutableMap<String, String> {
//                        return if (input.headers != null) {
//                            val params: HashMap<String, String> = HashMap<String, String>()
//
//                            for ((key, value) in input.headers!!) {
//                                params[key] = value
//                            }
//                            params
//                        } else {
//                            super.getHeaders()
//                        }
//                    }
//                }
//            jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
//                MY_SOCKET_TIMEOUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//            )
//
//            showLog("$TAG Request", input.toString())
//
//            AppController.getInstance().addrequestToQueue(jsonObjectRequest)
//        } else {
//            setErrorResponse("No Internet Connection")
//        }
//    }
//
//    fun getMethod(
//        input: ApiInput, setResponseSuccess: (JSONObject) -> Unit,
//        setErrorResponse: (String) -> Unit
//    ) {
//        showLog("$TAG Request", input.url.toString() + " headers: " + input.headers)
//        if (input.context!!.isNetworkConnected()) {
//
//            val jsonObjectRequest =
//                object : JsonObjectRequest(Request.Method.GET, input.url, input.jsonObject, {
//                    setResponseSuccess(it)
//                    showLog("$TAG Response", it.toString())
//                }, {
//
//                    if (it is TimeoutError || it is NoConnectionError) {
//                        input.context?.getString(R.string.no_internet_connection)
//                            ?.let { it1 -> setErrorResponse(it1) }
//                    } else if (it is AuthFailureError) {
//                        input.context?.getString(R.string.session_expired)
//                            ?.let { it1 -> setErrorResponse(it1) }
//                    } else if (it is ServerError) {
//                        input.context?.getString(R.string.server_error)
//                            ?.let { it1 -> setErrorResponse(it1) }
//                    } else if (it is NetworkError) {
//                        input.context?.getString(R.string.network_error)
//                            ?.let { it1 -> setErrorResponse(it1) }
//                    } else if (it is ParseError) {
//                        input.context?.getString(R.string.parsing_error)
//                            ?.let { it1 -> setErrorResponse(it1) }
//                    } else {
//                        input.context?.getString(R.string.network_error)
//                            ?.let { it1 -> setErrorResponse(it1) }
//                    }
//                }) {
//                    override fun getHeaders(): MutableMap<String, String> {
//                        return if (input.headers != null) {
//                            val params: HashMap<String, String> = HashMap<String, String>()
//
//                            for ((key, value) in input.headers!!) {
//                                params[key] = value
//                            }
//                            params
//                        } else {
//                            super.getHeaders()
//                        }
//                    }
//                }
//
//            jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
//                MY_SOCKET_TIMEOUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//            )
//
//            showLog("$TAG Request", input.toString())
//
//
//            AppController.getInstance().addrequestToQueue(jsonObjectRequest)
//        } else {
//            setErrorResponse("No Internet Connection")
//        }
//    }
//
//    fun uploadImage(
//        file: File,
//        url: String,
//        context: Context,
//        setResponseSuccess: (JSONObject) -> Unit,
//        setErrorResponse: (String) -> Unit
//    ) {
//        if (context.isNetworkConnected()) {
//
//            val MEDIA_TYPE_PNG = "image/jpeg".toMediaTypeOrNull()
//
//            val requestBody = MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart(
//                    "file",
//                    "fileName",
//                    RequestBody.create(MEDIA_TYPE_PNG, File(file.path))
//                )
//                .build()
//            val request = okhttp3.Request.Builder()
//                .url(url)
//                .post(requestBody).build()
//            Log.e(TAG, "file: " + file.path)
//
//            Coroutines.ioWorker {
//                try {
//                    var response = uploadRequest(request)
//                    val jsonObject = JSONObject(response.body!!.string())
//                    Log.e(TAG, "Response: ${jsonObject.toString()}")
//                    Coroutines.mainWorker { setResponseSuccess(jsonObject) }
//                } catch (e: IOException) {
//                    Log.e(TAG, "IOException: $e")
//                    Coroutines.mainWorker { setErrorResponse(e.message.toString()) }
//                } catch (e: JSONException) {
//                    Coroutines.mainWorker { setErrorResponse(e.message.toString()) }
//                }
//            }
//
//        } else {
//            Coroutines.mainWorker { setErrorResponse("No Internet Connection") }
//        }
//    }
//
//    fun uploadRequest(request: okhttp3.Request): okhttp3.Response {
//        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)
//        val builder = OkHttpClient.Builder()
//        builder.connectTimeout(30, TimeUnit.SECONDS)
//        builder.readTimeout(30, TimeUnit.SECONDS)
//        builder.writeTimeout(30, TimeUnit.SECONDS)
//        val client = builder.build()
//        val response = client.newCall(request).execute()
//        return response
//    }
//
//
//}