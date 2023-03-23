package com.maialearning.ui.activity

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.maialearning.R
import com.maialearning.databinding.LayoutNotesBinding
import com.maialearning.model.NotesModel
import com.maialearning.util.DESCRIPTION
import com.maialearning.util.TITLE
import com.maialearning.util.getDate
import com.maialearning.util.showToast
import java.io.File

class NotesDetailActivity : AppCompatActivity() {
    private lateinit var notesBinding: LayoutNotesBinding
    private var notesModel: NotesModel.DataItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notesBinding = LayoutNotesBinding.inflate(layoutInflater)
        setContentView(notesBinding.root)
        notesBinding.toolbar.backBtn.setOnClickListener { finish() }
        notesBinding.toolbar.optionMenu.visibility = View.GONE
        notesBinding.toolbar.textTitle.setText(getString(R.string.note))
        intent.extras.let {
            it?.getString(TITLE)?.let {
                notesBinding.textTitl.setText(it)
            }
            it?.getString(DESCRIPTION)?.let {
                notesBinding.textDescription.loadDataWithBaseURL(
                    "",
                    it,
                    "text/html",
                    "utf-8",
                    ""
                )
            }

            notesModel = it?.getSerializable("DATA") as NotesModel.DataItem?
            notesBinding.textDate.text =
                notesModel?.created?.toLong()?.let {
                    getDate(
                        it, "MMM dd, yyyy hh:mm a"
                    )
                }

            notesModel?.file?.let {
                notesBinding.fileLay.visibility = View.VISIBLE
                it?.url?.let {
                    // var str = it.replace("\\/", "//")
                    var ar = it.split("notes")
                    if (ar.size > 1) {
                        var ar1 = ar.get(1).split("?")
                        if (ar1.size > 0) {
                            var ar2 = ar1.get(0).split("/")
                            if (ar2.size > 0) {
                                notesBinding.docName.text = ar2.get(ar2.size - 1)
                            }
                        }
                    }
                }
                // notesBinding.docName.text=notesModel?.file?.
            }
        }
        notesBinding.downloadFile.setOnClickListener {
            var url = ""
            notesModel?.file?.url.let {
                if (it.toString() != "") {
                    url = it.toString()
                    val manager =
                        getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

                    val uri = Uri.parse(url.replace("\"", ""))
                    val file =
                        File(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path.toString() + "/" + File(
                                uri.path
                            ).name
                        )
                    registerReceiver(
                        attachmentDownloadCompleteReceive, IntentFilter(
                            DownloadManager.ACTION_DOWNLOAD_COMPLETE
                        )
                    );
                    val request = DownloadManager.Request(uri)
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    request.setAllowedOverRoaming(true)
                    Log.e("FILE", file.absolutePath)
                    request.setDestinationUri(
                        Uri.fromFile(
                            //File(
//                            context?.getExternalFilesDir(
//                                Environment.DIRECTORY_DOWNLOADS
//                            ).toString(), File(uri.path).name
                            file


                        )
                    )
                    showToast("Downloading..")
                    manager.enqueue(request)
                }
            }
        }


    }

    var attachmentDownloadCompleteReceive: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
                val downloadId = intent.getLongExtra(
                    DownloadManager.EXTRA_DOWNLOAD_ID, 0
                )
                // openDownloadedAttachment(context, downloadId)
                showToast("Downloaded File Successfully")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(attachmentDownloadCompleteReceive)
    }
}