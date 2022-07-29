package com.maialearning.ui.activity

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.MessageDetailBinding
import com.maialearning.databinding.NewMessageBinding
import com.maialearning.model.AttachMessages
import com.maialearning.ui.adapter.FilesAdapter
import com.maialearning.ui.adapter.RecipentAdapter

class NewMessageActivity : AppCompatActivity(), OnItemClick {
    private lateinit var mBinding: NewMessageBinding
    var attachedArray=ArrayList<AttachMessages>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = NewMessageBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.root.setBackgroundColor(getResources().getColor(R.color.white))
        mBinding.filesList.adapter = FilesAdapter(this,attachedArray)
        mBinding.toolbar.backBtn.setOnClickListener { finish() }
        mBinding.toolbar.textTitle.setText(getString(R.string.new_message))
        mBinding.textReciepent.setOnClickListener { bottomSheetWork() }

    }

    private fun bottomSheetWork() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.reciepent_layout_bottomsheet, null)
        val recyclerView = view.findViewById<RecyclerView>(R.id.reciepent_list)
        recyclerView.adapter = RecipentAdapter(this)

        val selectAll = view.findViewById<CheckBox>(R.id.select_all)
        selectAll.setOnClickListener {
            ( recyclerView.adapter as RecipentAdapter).selectAll(selectAll.isChecked)
        }
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        dialog.setContentView(view)
        dialog.show()
        view.findViewById<RelativeLayout>(R.id.close).setOnClickListener{
            dialog.dismiss()
        }
    }

    override fun onClick(positiion: Int) {

    }


}