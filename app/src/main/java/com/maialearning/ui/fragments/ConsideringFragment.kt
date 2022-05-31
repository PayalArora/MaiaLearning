package com.maialearning.ui.fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.CommentsSheetBinding
import com.maialearning.databinding.ConsideringLayoutBinding
import com.maialearning.databinding.LayoutProgramsBinding
import com.maialearning.ui.adapter.CommentAdapter
import com.maialearning.ui.adapter.ConsiderAdapter
import com.maialearning.ui.adapter.ProgramAdapter

const val type: String = "UCAS"
const val term = "Spring 2022"
const val plan = "Early Action"
class ConsideringFragment : Fragment(), OnItemClickOption, OnItemClick {
    var count:Int = 0
    var dialog:BottomSheetDialog? = null

    private lateinit var mBinding: ConsideringLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = ConsideringLayoutBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    private fun setAdapter() {
        mBinding.consideringList.adapter = ConsiderAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setAdapter()
    }

    private fun setListeners() {

    }

    override fun onDestroyView() {
        dialog?.dismiss()
        super.onDestroyView()
    }


    private fun bottomSheetType(layoutId:Int, rbId:Int, type:Int) {
        dialog = BottomSheetDialog(requireContext())

        val view = layoutInflater.inflate(layoutId, null)
        view.minimumHeight =( (Resources.getSystem().displayMetrics.heightPixels))
        val radioAppType = view.findViewById<RadioGroup>(rbId)
        dialog?.setContentView(view)
        dialog?.show()
        view.findViewById<RelativeLayout>(R.id.close).setOnClickListener {
            dialog?.dismiss()
        }
        radioAppType.setOnCheckedChangeListener(
            { group, checkedId ->
                val radioButton = radioAppType.findViewById(checkedId) as RadioButton
                ( mBinding.consideringList.adapter as ConsiderAdapter).setValue(radioButton.text.toString(), type)
            })
    }

    override fun onTypeClick() {
       bottomSheetType(R.layout.application_type, R.id.radio_app_type, 1)
    }

    override fun onTermClick() {
     bottomSheetType(R.layout.application_term, R.id.radio_app_term, 0)
    }

    override fun onPlanClick() {
        bottomSheetType(R.layout.application_plan_filter, R.id.radio_action, 2)
    }

    override fun onCommentClick() {
        bottomSheetComment()
    }

    override fun onAddClick() {
        bottomSheetProgram()
    }

    private fun bottomSheetComment() {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: CommentsSheetBinding = CommentsSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight =( (Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.close.setOnClickListener {
            dialog.dismiss()
        }
        sheetBinding.commentList.adapter = CommentAdapter(this)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
    private fun bottomSheetProgram() {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding:LayoutProgramsBinding = LayoutProgramsBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight =( (Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()

        sheetBinding.addMoreLayout.adapter = ProgramAdapter(this)
        sheetBinding.addMore.setOnClickListener { (sheetBinding.addMoreLayout.adapter as ProgramAdapter).setCount(count++)  }
        sheetBinding.save.setOnClickListener { dialog.dismiss() }

    }
    override fun onClick(positiion: Int) {

    }

}




interface OnItemClickOption {
    fun onTypeClick()
    fun onTermClick()
    fun onPlanClick()
    fun onCommentClick()
    fun onAddClick()
}
