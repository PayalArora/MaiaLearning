package com.maialearning.ui.activity

import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.Html
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
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.MessageViewModel
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        mBinding.toolbar.menu.setOnClickListener {
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
            mBinding.toolbar.menu.visibility=View.GONE
        }
        observer()
        mBinding.toolbar.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(positiion: Int, id: String) {
        startActivity(Intent(this, LoadUrlActivity::class.java).putExtra("url", id))
    }

    private fun observer() {
        messageViewModel.inboxObserver.observe(this) { it ->
            it?.let {
                dialog.dismiss()
                val json = JSONObject(it.toString()).getJSONObject("Item")
                mBinding.subject.text = "Subject: " + json.getString("subject")
                mBinding.textDescription.text = Html.fromHtml(json.getString("messageBody"))
                val array = json.getJSONArray("attachment_urls")
                for (j in 0 until array.length()) {
                    mBinding.textFiles.visibility = View.VISIBLE
                    val objectProgram = array.getJSONObject(j)
                    attachedArray.add(
                        AttachMessages(
                            objectProgram.getString("name"),
                            objectProgram.getString("url")
                        )
                    )
                }
                mBinding.filesList.adapter = FilesAdapter(this, attachedArray)
            }
            messageViewModel.showError.observe(this) {
                dialog.dismiss()
            }
            messageViewModel.delObserver.observe(this) {
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