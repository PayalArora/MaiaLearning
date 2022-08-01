package com.maialearning.ui.activity

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.widget.CheckBox
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.calbacks.OnItemClickId
import com.maialearning.databinding.NewMessageBinding
import com.maialearning.model.AttachMessages
import com.maialearning.model.ReceipentModel
import com.maialearning.network.BaseApplication
import com.maialearning.ui.adapter.FilesAdapter
import com.maialearning.ui.adapter.RecipentAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.HomeViewModel
import com.maialearning.viewmodel.MessageViewModel
import kotlinx.serialization.json.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewMessageActivity : AppCompatActivity(), OnItemClickId, OnItemClick {
    private lateinit var mBinding: NewMessageBinding
    private lateinit var dialog: Dialog
    private val homeModel: HomeViewModel by viewModel()
    private val msgModel: MessageViewModel by viewModel()
    var attachedArray = ArrayList<AttachMessages>()
    var list = ArrayList<ReceipentModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = NewMessageBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.root.setBackgroundColor(getResources().getColor(R.color.white))
        mBinding.filesList.adapter = FilesAdapter(this, attachedArray)
        mBinding.toolbar.backBtn.setOnClickListener { finish() }
        mBinding.toolbar.textTitle.setText(getString(R.string.new_message))
        mBinding.textReciepent.setOnClickListener { bottomSheetWork() }
        init()
        observer()
    }

    fun init() {
        dialog = showLoadingDialog(this)
        dialog.show()
        homeModel.getRecipients(this, SharedHelper(BaseApplication.applicationContext()).schoolId?:"" , "all")
   mBinding.sendMessageBtn.setOnClickListener {
       var json=JSONObject()
       var jsonData=JSONObject()
       jsonData.put("senderUid",SharedHelper(BaseApplication.applicationContext()).messageId)
       jsonData.put("messageStatus","sent")
       jsonData.put("messageBody","test")
       jsonData.put("isReply",0)
       jsonData.put("isReplyAll",0)
       jsonData.put("subject","test")
       jsonData.put("auditEntity",SharedHelper(BaseApplication.applicationContext()).auditId)
       jsonData.put("recipients",list)
       json.put("message",jsonData)
       msgModel.createMessage(this,json)

   }

    }
    fun observer() {
        homeModel.listArrayObserver.observe(this) {
            it?.let {
                dialog.dismiss()
                val jsonArray =
                    JSONArray(it.toString()) // here  i am getting out of memory

                for (j in 0 until jsonArray.length()) {
                    val objectProgram = jsonArray.get(j)
                    list.add(
                        ReceipentModel(
                            (objectProgram as JSONObject).getString("name"),
                            (objectProgram as JSONObject).getString("message_center_uid"),
                            (objectProgram as JSONObject).getString("uid")
                        )
                    )
                }
            }
        }
        homeModel.showError.observe(this) {
            dialog.dismiss()
        }
    }
    private fun bottomSheetWork() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.reciepent_layout_bottomsheet, null)
        val recyclerView = view.findViewById<RecyclerView>(R.id.reciepent_list)
        recyclerView.adapter = RecipentAdapter(this, list)

        val selectAll = view.findViewById<CheckBox>(R.id.select_all)
        selectAll.setOnClickListener {
            (recyclerView.adapter as RecipentAdapter).selectAll(selectAll.isChecked)
        }
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        dialog.setContentView(view)
        dialog.show()
        view.findViewById<RelativeLayout>(R.id.close).setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onClick(positiion: Int, id: String) {

    }

    override fun onClick(positiion: Int) {
        mBinding.textReciepent.text=list[positiion].name
    }
}