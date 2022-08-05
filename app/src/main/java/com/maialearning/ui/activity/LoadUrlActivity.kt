package com.maialearning.ui.activity

import android.annotation.TargetApi
import android.app.Dialog
import android.os.*
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.maialearning.databinding.ActivityLoadUrlBinding
import com.maialearning.util.showLoadingDialog
import java.net.URLEncoder
import java.util.concurrent.Executors


class LoadUrlActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoadUrlBinding
    private lateinit var mWebView: WebView
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoadUrlBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init1()
    }

    fun init1() {
        dialog = showLoadingDialog(this)
        mBinding.toolbar.textTitle.text = "Attachments"

        mWebView = mBinding.webView
        mWebView.getSettings().setJavaScriptEnabled(true)
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON)
        mWebView.getSettings().setDomStorageEnabled(false)
        mWebView.getSettings().setDisplayZoomControls(true)
        mWebView.setWebViewClient(WebViewClient()) // set the WebViewClient
        var url = intent.getStringExtra("url") ?: ""
//        mWebView.loadUrl(url)
        dialog.show()
        mWebView.loadUrl(
            "http://docs.google.com/gview?embedded=true&url=" + URLEncoder.encode(
                url,
                "ISO-8859-1"
            )
        )
        mWebView.setWebViewClient(object : WebViewClient() {
            //add this line to Hide pop-out tool bar of pdfview in pagLoadFinish
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                //                webView.setVisibility(View.VISIBLE);
//                progress.setVisibility(View.GONE);
//                mWebView.loadUrl(
//                    "javascript:(function() { " +
//                            "document.getElementsByClassName('ndfHFb-c4YZDc-GSQQnc-LgbsSe ndfHFb-c4YZDc-to915-LgbsSe VIpgJd-TzA9Ye-eEGnhe ndfHFb-c4YZDc-LgbsSe')[0].style.display='none'; })()"
//                )
            }

            override fun onPageCommitVisible(view: WebView, url: String) {
                super.onPageCommitVisible(view, url)
                mWebView.setVisibility(View.VISIBLE)
                dialog.dismiss()
            }
        })
        mWebView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(
                view: WebView,
                url: String,
                message: String,
                result: JsResult
            ): Boolean {
                return super.onJsAlert(view, url, message, result)
            }
        }
        mBinding.toolbar.backBtn.setOnClickListener {
            onBackPressed()
        }
//        loadPdfFromUrl().execute(url)
    }

    fun init() {
        mWebView = mBinding.webView
        mWebView.settings.javaScriptEnabled = true // enable javascript
        mWebView.settings.loadWithOverviewMode = true
        mWebView.settings.useWideViewPort = true
        mWebView.settings.domStorageEnabled = true
        dialog = showLoadingDialog(this)
        mWebView.setWebChromeClient(WebChromeClient())
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
                view?.loadUrl(url ?: "")
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                dialog.dismiss()
            }
        }
        mWebView.loadUrl(intent.getStringExtra("url") ?: "")
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            /*
            * Your task will be executed here
            * you can execute anything here that
            * you cannot execute in UI thread
            * for example a network operation
            * This is a background thread and you cannot
            * access view elements here
            *
            * its like doInBackground()
            * */
            handler.post {
                /*
                * You can perform any operation that
                * requires UI Thread here.
                *
                * its like onPostExecute()
                * */
            }
        }
    }


}