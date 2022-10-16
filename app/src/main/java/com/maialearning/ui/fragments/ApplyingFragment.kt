package com.maialearning.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.CommentsSheetBinding
import com.maialearning.databinding.FragmentApplyingBinding
import com.maialearning.databinding.LayoutProgramsBinding
import com.maialearning.model.AddProgramConsider
import com.maialearning.model.ConsiderModel
import com.maialearning.model.UpdateStudentPlan
import com.maialearning.ui.adapter.ApplyingAdapter
import com.maialearning.ui.adapter.CommentAdapter
import com.maialearning.ui.adapter.ConsiderAdapter
import com.maialearning.ui.adapter.ProgramAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.util.showToast
import com.maialearning.viewmodel.HomeViewModel
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApplyingFragment(val tabs: TabLayout) : Fragment(), OnItemClickOption, OnItemClick {
    var selectedValue = ""
    private lateinit var mBinding: FragmentApplyingBinding
    private lateinit var dialogP: Dialog
    private val homeModel: HomeViewModel by viewModel()
    val finalArray: ArrayList<ConsiderModel.Data> = ArrayList()
    lateinit var userid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentApplyingBinding.inflate(inflater, container, false)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tabs.selectedTabPosition == 2) {
                    init()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setAdapter()

    }

    private fun init() {
        dialogP = showLoadingDialog(requireContext())
        dialogP.show()
        initObserver()
        getApplyingList()
    }

    private fun getApplyingList() {
        SharedHelper(requireContext()).id?.let {
            userid = SharedHelper(requireContext()).id!!
            homeModel.getApplyList(userid)
        }
    }

    private fun initObserver() {
        homeModel.applyObserver.observe(requireActivity()) {
            it?.let {
                finalArray.clear()
                dialogP?.dismiss()
                val json = JSONObject(it.toString()).getJSONObject(userid).getJSONObject("data")

                val x = json.keys() as Iterator<String>
                val jsonArray = JSONArray()
                while (x.hasNext()) {
                    val key: String = x.next().toString()
                    jsonArray.put(json.get(key))
                }
                val countries = ArrayList<String>()
                val array: ArrayList<ConsiderModel.Data> = ArrayList()
                for (i in 0 until jsonArray.length()) {
                    val object_ = jsonArray.getJSONObject(i)
                    val arrayProgram: ArrayList<ConsiderModel.ProgramData> = ArrayList()
                    var programArray = object_.getJSONArray("app_by_program_data")
                    for (j in 0 until programArray.length()) {
                        val objectProgram = programArray.getJSONObject(j)
                        arrayProgram.add(
                            ConsiderModel.ProgramData(
                                objectProgram.getInt("program_id"),
                                objectProgram.getString("program_name"),
                                "",
                                ""
                            )
                        )
                    }
                    var arrayCounselor: ArrayList<ConsiderModel.CounselorNotes> =
                        arrayListOf()
                    /*       var counselorNotes = object_.getJSONArray("counselor_notes")
                           if (counselorNotes !is JSONArray && counselorNotes.length() != 0) {
                               if (counselorNotes is JSONObject) {
                                   val x = counselorNotes.keys() as Iterator<String>
                                   while (x.hasNext()) {
                                       var json: JSONObject = counselorNotes.get(x.next()) as JSONObject
                                       val notesObj: ConsiderModel.CounselorNotes =
                                           ConsiderModel.CounselorNotes(json.optString("id"),
                                               json.optString("uid"),
                                               json.optString("counselor_note"),
                                               json.optString("first_name"),
                                               json.optString("last_name"))
                                       arrayCounselor.add(notesObj)
                                   }


                               }
                           }*/
                    if (!countries.contains(object_.getString("country_name")))
                        countries.add(object_.getString("country_name"))
                    val model: ConsiderModel.Data = ConsiderModel.Data(
                        object_.getInt("contact_info"),
                        object_.getInt("parchment"),
                        object_.getInt("slate"),
                        object_.getInt("transcript_nid"),
                        object_.getString("university_nid"),
                        object_.getString("name"),
                        object_.getString("country"),
                        object_.getString("country_name"),
                        object_.getString("created_by_name"),
                        object_.getString("created_date"),
                        object_.getString("college_priority_choice"),
                        object_.getString("university_nid"),
                        object_.getString("unitid"),
                        object_.getString("internal_deadline"),
                        arrayProgram,
                        0,
                        object_.getString("notes"),
                        arrayCounselor, object_.getString("request_transcript"),
                        object_.getString("application_type"),
                        object_.optString("application_term"),
                        object_.getString("application_mode"),
                        object_.getString("application_status_name"),
                        object_.getString("app_by_program_supported"),
                        object_.getInt("confirm_applied"), null
                    )
                    array.add(model)
                    array.sortBy { it.naviance_college_name }
                }

                var pos = 0
                countries.sortBy { it }
                for (j in 0 until countries.size) {
                    var firstTime = true
                    var count = 0
                    for (k in 0 until array.size) {
                        if (countries[j] == array[k].country_name) {
                            count += 1
                            if (firstTime) {
                                firstTime = false
                                array[k].country_name = countries[j]
                            } else {
                                array[k].country_name = ""
                            }
                            finalArray.add(array[k])
                            finalArray[pos].count = count
                        }
                    }
                    pos = finalArray.size
                }

                (mBinding.applyingList.adapter as ApplyingAdapter).updateAdapter(finalArray)
            }
        }
        homeModel.showError.observe(requireActivity()) {
            dialogP.dismiss()
            context?.showToast(it)
        }
    }

    private fun setAdapter() {
        mBinding.applyingList.adapter = ApplyingAdapter(this, arrayListOf())
    }

    private fun bottomSheetType(layoutId: Int, rbId: Int, type: Int) {
        val dialog = BottomSheetDialog(requireContext())

        val view = layoutInflater.inflate(layoutId, null)
        view?.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        val radioAppType = view.findViewById<RadioGroup>(rbId)
        dialog.setContentView(view)
        dialog.show()
        view.findViewById<RelativeLayout>(R.id.close).setOnClickListener {
            dialog.dismiss()
        }
        radioAppType.setOnCheckedChangeListener(
            { group, checkedId ->
                val radioButton = radioAppType.findViewById(checkedId) as RadioButton
                (mBinding.applyingList.adapter as ApplyingAdapter).setValue(
                    radioButton.text.toString(),
                    type
                )
            })
    }

    override fun onTypeClick(postion: Int) {
        bottomSheetType(R.layout.application_type, R.id.radio_app_type, 1)
    }

    override fun onTermClick(postion: Int) {
        bottomSheetType(R.layout.application_term, R.id.radio_app_term, 0)
    }

    override fun onPlanClick(postion: Int) {
        bottomSheetType(R.layout.application_plan_filter, R.id.radio_action, 2)
    }

    override fun onCommentClick() {
        //     bottomSheetComment()
    }

    private fun bottomSheetComment() {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: com.maialearning.databinding.CommentsSheetBinding =
            CommentsSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.close.setOnClickListener {
            dialog.dismiss()
        }
        sheetBinding.commentList.adapter = CommentAdapter(this)
    }

    override fun onClick(positiion: Int) {

    }

    override fun onInfoClick(postion: Int) {
    }

    override fun onApplyingClick(postion: Int) {
        TODO("Not yet implemented")
    }


    override fun onTranscriptRequest(postion: Int, checked: String) {
        var updateStudentPlan = UpdateStudentPlan()
        updateStudentPlan.student_uid = SharedHelper(requireContext()).id.toString()
        updateStudentPlan.college_nid = finalArray[postion].university_nid
        updateStudentPlan.app_type = "4"
        updateStudentPlan.request_transcript = checked
        dialogP.show()
        homeModel.updateStudentPlan(updateStudentPlan)
        homeModel.updateStudentPlanObserver.observe(requireActivity()) {
            dialogP.dismiss()
        }
        homeModel.showError.observe(requireActivity()) {
            dialogP.dismiss()
        }
    }

    private fun bottomSheetProgram(postion: Int) {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: LayoutProgramsBinding = LayoutProgramsBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        var addedPrograms: ArrayList<AddProgramConsider.Programs?>? = ArrayList()
        for (i in finalArray[postion].program_data?.indices!!) {
            var programData: AddProgramConsider.Programs = AddProgramConsider.Programs()
            programData.program_name =
                finalArray[postion].program_data?.get(i)?.program_name.toString()
            programData.program_id = finalArray[postion].program_data?.get(i)?.program_id
            addedPrograms?.add(programData)
        }
        var deletedPrograms: ArrayList<String> = ArrayList()
        sheetBinding.addMoreLayout.adapter = ProgramAdapter(addedPrograms, deletedPrograms, this)
        sheetBinding.addMore.setOnClickListener {
            ((sheetBinding.addMoreLayout.adapter) as ProgramAdapter).addMore()
        }
        sheetBinding.save.setOnClickListener {

            addedPrograms = ((sheetBinding.addMoreLayout.adapter) as ProgramAdapter).save()
            dialogP.show()
//            var newPrograms: ArrayList<AddProgramConsider.Programs?>? = ArrayList()
//
//            for (i in addedPrograms!!.indices) {
//                var programData: AddProgramConsider.Programs = AddProgramConsider.Programs()
//                programData.program_name = addedPrograms.get(i).toString()
//                newPrograms?.add(programData)
//            }
            if (deletedPrograms.size > 0) {
                for (i in deletedPrograms.indices) {
                    homeModel.deleteMlProgram(deletedPrograms.get(i))
                }
            }

            var newPrograms: ArrayList<AddProgramConsider.Programs?> = ArrayList()

            if (addedPrograms != null) {
                for (i in addedPrograms!!.indices) {
                    if ((addedPrograms!![i]?.program_name ?: "").isEmpty()) {
                        newPrograms.add(addedPrograms!![i])
                    }
                }
                addedPrograms!!.removeAll(newPrograms)
            }

            var addProgramConsider = AddProgramConsider()
            addProgramConsider.trans_req_nid = finalArray[postion].transcriptNid.toString()
            addProgramConsider.program_data = addedPrograms
            homeModel.addProgramToConsidering(addProgramConsider)

            homeModel.addProgramObserver.observe(requireActivity()) {
                dialogP.dismiss()
                dialog.dismiss()
                SharedHelper(requireContext()).id?.let {
                    val userid = SharedHelper(requireContext()).id!!
                    homeModel.getApplyList(userid)
                }

            }
        }

    }

    override fun onAddClick(position: Int) {
        bottomSheetProgram(position)
    }

    override fun onMenuClick(postion: Int, it: View?) {
        menuPopUp(postion, it)
    }

    private fun menuPopUp(position: Int, it: View?) {

        val popupMenu = PopupMenu(requireContext(), it)
        popupMenu.inflate(R.menu.consider_popup)
        popupMenu.show()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true)
        };

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.del_coll -> {
                    confirmPopup(position)
                }
            }

            true

        })
    }

    private fun confirmPopup(position: Int) {
        AlertDialog.Builder(requireContext())
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage(getString(R.string.move_to_applying))
            .setPositiveButton(
                "Yes"
            ) { dialog, _ -> deleteWork(position) }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteWork(position: Int) {
        SharedHelper(requireContext()).id?.let {
            dialogP.show()
            homeModel.moveToApplying(
                it,
                finalArray.get(position).university_nid,
                "0"
            )
        }
        homeModel.applyingObserver.observe(requireActivity()) {
            getApplyingList()
        }
    }

}