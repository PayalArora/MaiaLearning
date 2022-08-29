package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
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
import retrofit2.http.Field

class RecommendationFragment : Fragment() {
    private lateinit var mBinding: RecommendationLayoutBinding
    private val homeModel: HomeViewModel by viewModel()
    private lateinit var progress: Dialog
    var list: ArrayList<TeacherListModelItem> = ArrayList()
    var selectedList: ArrayList<TeacherListModelItem> = ArrayList()
    var teacherId: ArrayList<String> = ArrayList()
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
        mBinding.send.setOnClickListener {
            if(mBinding.textDescription.text.toString().trim() !=""){
                val data =JSONObject ()
                val data_ =HashMap<String,Any> ()
                data.put("notes",mBinding.textDescription.text.toString())
                data.put("student_id", SharedHelper(requireContext()).id)
                data.put("due_date","1601490599")
                data.put("teacher_id", teacherId)
                data_.put("notes",mBinding.textDescription.text.toString())
                data_.put("student_id", SharedHelper(requireContext()).id?:"")
                data_.put("due_date","1601490599")
                data_.put("teacher_id", teacherId)
                homeModel.sendRecomTeachers(mBinding.textDescription.text.toString(),SharedHelper(requireContext()).id?:"",
                    "1601490599",teacherId)
                }
        }
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
                    data.getString("school_name"),
                    data.getString("first_name"),
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
        sheetBinding.filters.text = resources.getString(R.string.select_teachers)
        dialog.show()
        sheetBinding.clearText.setOnClickListener { dialog.dismiss() }
        sheetBinding.backBtn.setOnClickListener { dialog.dismiss() }
        sheetBinding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        sheetBinding.recyclerView.adapter =
            SelectTeacherAdapter(list, ::click)
        sheetBinding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (sheetBinding.searchText.text.toString().trim() != "") {
                  val filterList=  findMemberByName(sheetBinding.searchText.text.toString().trim())
                    sheetBinding.recyclerView.adapter =
                        SelectTeacherAdapter(filterList, ::click)
                } else {
                    sheetBinding.recyclerView.adapter =
                        SelectTeacherAdapter(list, ::click)
                }

            }

        })
        sheetBinding.searchText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                findMemberByName(sheetBinding.searchText.text.toString().trim())
                return@OnEditorActionListener true
            }
            false
        })

    }

    fun findMemberByName(name: String?): ArrayList<TeacherListModelItem> {
        // go through list of members and compare name with given name
        var filterList: ArrayList<TeacherListModelItem> = ArrayList()
        for (member in list) {
            if ((member.firstName + member.lastName).contains(name ?: "")) {
                filterList.add(member)
                // return member when name found
            }
        }

        return filterList
    }

    private fun click(data: TeacherListModelItem) {
        if (data.isSelected) {
            selectedList.add(data)
            teacherId.add(data.uid?:"")
        } else {
            selectedList.remove(data)
            teacherId.remove(data.nid)
        }
        for (i in 0 until selectedList.size) {
            if (selectedList[i].isSelected)
                mBinding.selectedTeachers.append(selectedList[i].lastName + ",")
        }
        mBinding.textCount.text = "from " + selectedList.size + " teachers"
    }
}