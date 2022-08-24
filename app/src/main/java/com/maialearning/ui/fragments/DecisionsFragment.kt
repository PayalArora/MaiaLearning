package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.databinding.RadiobuttonFilterBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.model.UpdateStudentPlan
import com.maialearning.ui.adapter.*
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.HomeViewModel
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DecisionsFragment : Fragment(), DecisionClick {
    private lateinit var mBinding: LayoutRecyclerviewBinding
    lateinit var userid: String
    private lateinit var dialogP: Dialog
    private val homeModel: HomeViewModel by viewModel()
    val finalArray: ArrayList<ConsiderModel.Data> = ArrayList()
    var statusArray: Array<String> = arrayOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = LayoutRecyclerviewBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    private fun setAdapter() {
        mBinding.recyclerList.adapter = DecisionAdapter(finalArray, this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setAdapter()
        dialogP = showLoadingDialog(requireContext())
        dialogP.show()
        getApplyingList()
        initObserver()
    }

    private fun setListeners() {

    }

    override fun onDecisionClick(position: Int) {
        showDialog(requireContext(), layoutInflater, position)
    }

    fun showDialog(con: Context, layoutInflater: LayoutInflater, pos: Int) {
        val dialog = BottomSheetDialog(con)
        val sheetBinding: RadiobuttonFilterBinding =
            RadiobuttonFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        dialogP.show()
        homeModel.getDecisionStatuses()
        var statuses = ArrayList<String>()
        var statuskey = ArrayList<String>()


        homeModel.decisionStatusObserver.observe(requireActivity()) {
            dialogP.dismiss()
            val json = JSONObject(it.toString())
            val iterator: Iterator<*> = json.keys()
            while (iterator.hasNext()) {
                val key = iterator.next() as String

                statuses.add(json.getString(key))
                statuskey.add(key)
                //do what you want with the key.
            }
             statusArray = statuses.toTypedArray()
            sheetBinding.filters.setText(con.resources.getString(R.string.select_decision1))

            sheetBinding.rvRadioGroup.adapter =
                RadiobuttonFilterAdapter(statusArray,  finalArray[pos].applicationStatusName)
        }
        sheetBinding.close.setOnClickListener { dialog.dismiss() }
        sheetBinding.saveBtn.setOnClickListener {
            var updateStudentPlan = UpdateStudentPlan()
            updateStudentPlan.student_uid = SharedHelper(requireContext()).id.toString()
            updateStudentPlan.college_nid = finalArray[pos].university_nid
            val selectedpos = (sheetBinding.rvRadioGroup.adapter as RadiobuttonFilterAdapter).onSave()
            updateStudentPlan.app_status = statuses[selectedpos]

            dialogP.show()
            homeModel.updateStudentPlan(updateStudentPlan)
            homeModel.updateStudentPlanObserver.observe(requireActivity()) {
                dialog.dismiss()
                dialogP.dismiss()
            }
        }

    }
//    app_plan: null
//    app_status: "waitlisted"
//    app_type: "4"
//    application_term: null
//    campus_tour: "0"
//    college_interest: "3"
//    college_nid: "175684"
//    college_priority_choice: null
//    deadline_date: null
//    interview_interest: "2"
//    major_name: null
//    mid_november: null
//    multiple_decision_round: null
//    old_app_status: null
//    program_name: null
//    request_transcript: "1"
//    school_within_university: ""
//    student_uid: "9375"

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
                        object_.getString("application_mode"),
                        object_.getString("application_status_name"),
                        object_.getString("app_by_program_supported"),
                        object_.getInt("confirm_applied")

                    )
                    array.add(model)
                }

                var pos = 0
               // countries.sortBy { it }
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
                finalArray.sortBy { it.naviance_college_name }
                setAdapter()
//                (mBinding.applyingList.adapter as ApplyingAdapter).updateAdapter(finalArray)
            }
        }
        homeModel.showError.observe(requireActivity()) {
            dialogP.dismiss()
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}