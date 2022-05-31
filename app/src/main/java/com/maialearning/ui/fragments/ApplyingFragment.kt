package com.maialearning.ui.fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.CommentsSheetBinding
import com.maialearning.databinding.FragmentApplyingBinding
import com.maialearning.ui.adapter.ApplyingAdapter
import com.maialearning.ui.adapter.CommentAdapter

class ApplyingFragment : Fragment(), OnItemClickOption, OnItemClick {
    var selectedValue = ""
    private lateinit var mBinding: FragmentApplyingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentApplyingBinding.inflate(inflater, container, false)
        return mBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        mBinding.applyingList.adapter = ApplyingAdapter(this)
    }

    private fun bottomSheetType(layoutId:Int, rbId:Int, type:Int) {
        val dialog = BottomSheetDialog(requireContext())

        val view = layoutInflater.inflate(layoutId, null)
        view?.minimumHeight =( (Resources.getSystem().displayMetrics.heightPixels))
        val radioAppType = view.findViewById<RadioGroup>(rbId)
        dialog.setContentView(view)
        dialog.show()
        view.findViewById<RelativeLayout>(R.id.close).setOnClickListener {
            dialog.dismiss()
        }
        radioAppType.setOnCheckedChangeListener(
            { group, checkedId ->
                val radioButton = radioAppType.findViewById(checkedId) as RadioButton
                ( mBinding.applyingList.adapter as ApplyingAdapter).setValue(radioButton.text.toString(), type)
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
    private fun bottomSheetComment() {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: com.maialearning.databinding.CommentsSheetBinding = CommentsSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight =( (Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.close.setOnClickListener {
            dialog.dismiss()
        }
        sheetBinding.commentList.adapter = CommentAdapter(this)
    }

    override fun onClick(positiion: Int) {

    }

    override fun onAddClick() {

    }

}