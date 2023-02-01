package com.maialearning.ui.activity

import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnItemClickId
import com.maialearning.databinding.MessageDetailBinding
import com.maialearning.model.AttachMessages
import com.maialearning.ui.adapter.FilesAdapter
import com.maialearning.util.getDate
import com.maialearning.util.getDateTime
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.MessageViewModel
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class MessageDetailActivity : AppCompatActivity(), OnItemClickId {
    private lateinit var mBinding: MessageDetailBinding
    private val messageViewModel: MessageViewModel by viewModel()
    var attachedArray = ArrayList<AttachMessages>()
    private lateinit var dialog: Dialog
    private lateinit var dialogB: BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = MessageDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()

    }

    fun init() {
        mBinding.toolbar.optionMenu.setOnClickListener {
            bottomSheetType()
        }
        mBinding.root.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
        mBinding.filesList.adapter = FilesAdapter(this, attachedArray)
        mBinding.textFiles.visibility = View.GONE
        dialog = showLoadingDialog(this)
        dialog.show()
        messageViewModel.getMessage(intent.getStringExtra("id") ?: "")
        mBinding.toolbar.backBtn.setOnClickListener { finish() }
        if(intent.getStringExtra("type").equals("false")){
            mBinding.toolbar.optionMenu.visibility=View.GONE
        }
        observer()
        mBinding.toolbar.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(positiion: Int, id: String) {
        dialog.show()
       // startActivity(Intent(this, LoadUrlActivity::class.java).putExtra("url", id))
        messageViewModel.getFile(
            SharedHelper(this).jwtToken, attachedArray.get(positiion).name ?: "", attachedArray.get(positiion).type,
            attachedArray.get(positiion).key
        )
    }

    private fun observer() {
        messageViewModel.fileUrlObserver.observe(this) {
            dialog.dismiss()
            var url = ""
            if (it.get("url").toString() != "") {
                url = it.get("url").toString()
                val manager =
                   getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

                val uri = Uri.parse(url.replace("\"", ""))
                val file =
                    File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path.toString() + "/" +  File(uri.path).name)

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

                manager.enqueue(request)
            }
        }
        messageViewModel.inboxObserver.observe(this) { it ->
            it?.let {
                dialog.dismiss()
                val json = JSONObject(it.toString()).getJSONObject("Item")
                mBinding.subject.text = "Subject: " + json.getString("subject")
                mBinding.textDescription.loadDataWithBaseURL(
                    "",
                    json.getString("messageBody"),
                    "text/html",
                    "utf-8",
                    ""
                )
                //  mBinding.textDescription.text = Html.fromHtml(json.getString("messageBody"))
                mBinding.textDate.text= "Sent on : "+ getDateTime(json.getString("sentTimestamp"), "MMM dd YYYY, hh:mm a")
                val array = json.optJSONArray("attachments")
                if (array!=null) {
                    for (j in 0 until array.length()) {
                        mBinding.textFiles.visibility = View.VISIBLE
                        val objectProgram = array.optJSONObject(j)
                        attachedArray.add(
                            AttachMessages(
                                objectProgram.getString("filename"),
                                objectProgram.getString("type"),
                                objectProgram.getString("fileType"),
                                objectProgram.getString("key"),
                                objectProgram.getString("schoolNid")
                            )
                        )
                    }
                    mBinding.filesList.adapter = FilesAdapter(this, attachedArray)
                }
            }
            messageViewModel.showError.observe(this) {
                dialog.dismiss()
            }
            messageViewModel.delObserver.observe(this) {
                dialog.dismiss()
                dialogB.dismiss()
                onBackPressed()
            }
        }
    }

    private fun bottomSheetType() {
        dialogB = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.delete_dialog, null)
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        val delete = view.findViewById<TextView>(R.id.delete)
        val cancel = view.findViewById<TextView>(R.id.cancel)
        dialogB.setContentView(view)
        dialogB.show()
        cancel.setOnClickListener {
            dialogB.dismiss()
        }
        delete.setOnClickListener {
            dialog.show()
            messageViewModel.delMessage(intent.getStringExtra("id") ?: "")
        }

    }
}