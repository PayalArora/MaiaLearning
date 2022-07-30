package com.maialearning.ui.activity

import android.annotation.TargetApi
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.maialearning.databinding.ActivityLoadUrlBinding
import com.maialearning.util.showLoadingDialog


class LoadUrlActivity: AppCompatActivity() {
    private lateinit var mBinding: ActivityLoadUrlBinding
    private lateinit var mWebView: WebView
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoadUrlBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()
    }

    fun init() {
        mWebView = mBinding.webView
        mWebView.settings.javaScriptEnabled = true // enable javascript
        mWebView.settings.loadWithOverviewMode = true
//        mWebView.settings.useWideViewPort = true
//        mWebView.settings.domStorageEnabled = true
        dialog = showLoadingDialog(this)

        mWebView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                dialog.dismiss()
                Toast.makeText(this@LoadUrlActivity, description, Toast.LENGTH_SHORT).show()
            }

            @TargetApi(Build.VERSION_CODES.M)
            override fun onReceivedError(
                view: WebView,
                req: WebResourceRequest,
                rerr: WebResourceError
            ) {
                dialog.dismiss()
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(
                    view,
                    rerr.errorCode,
                    rerr.description.toString(),
                    req.url.toString()
                )
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                dialog.show()
                view?.loadUrl(url?:"")
                return true
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                dialog.dismiss()
            }
        }
        mWebView.loadUrl(intent.getStringExtra("url")?:"")
    }



}