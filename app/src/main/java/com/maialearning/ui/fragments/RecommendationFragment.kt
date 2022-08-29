package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.databinding.LayoutTeacherBinding
import com.maialearning.databinding.RecommendationLayoutBinding
import com.maialearning.model.TeacherListModelItem
import com.maialearning.ui.adapter.SelectTeacherAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.HomeViewModel
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecommendationFragment : Fragment() {
    private lateinit var mBinding: RecommendationLayoutBinding
    private val homeModel: HomeViewModel by viewModel()
    private lateinit var progress: Dialog
    var list: ArrayList<TeacherListModelItem> = ArrayList()
    var selectedList: ArrayList<TeacherListModelItem> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = RecommendationLayoutBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        progress = showLoadingDialog(requireContext())
        progress.show()
        SharedHelper(requireContext()).id?.let { homeModel.getTeachers(it) }
        mBinding.recipent.setOnClickListener {
            listTeacher()
        }
    }

    private fun setListeners() {
        homeModel.applyingObserver.observe(requireActivity()) {
            progress.dismiss()
            for (i in 0 until it.size()) {
                val data = JSONObject(it.get(i).toString())
                val model = TeacherListModelItem(
                    data.getString("uid"),
                    data.getString("users_name"),
                    data.getString("school_nid"),
                    data.getString("nid"),
                    data.getString("last_name"),
                    data.getString("first_name")
                )
                list.add(model)
            }

        }
    }

    private fun listTeacher() {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: LayoutTeacherBinding = LayoutTeacherBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.filters.setText(resources.getString(R.string.select_teachers))
        dialog.show()
        sheetBinding.clearText.setOnClickListener { dialog.dismiss() }
        sheetBinding.backBtn.setOnClickListener { dialog.dismiss() }
        sheetBinding.recyclerView.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        sheetBinding.recyclerView.adapter =
            SelectTeacherAdapter(list, ::click)

    }

    private fun click(data: TeacherListModelItem) {
        if (data.isSelected) {
            selectedList.add(data)
        } else {
            selectedList.remove(data)
        }
        for (i in 0 until selectedList.size) {
            if (selectedList[i].isSelected)
                mBinding.selectedTeachers.append(selectedList[i].lastName + ",")
        }
        mBinding.textCount.text = "from " + selectedList.size + " teachers"
    }
}