package com.maialearning.ui.fragments

import android.app.Dialog
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
import com.maialearning.databinding.*
import com.maialearning.model.ConsiderModel
import com.maialearning.model.UpdateStudentPlan
import com.maialearning.ui.adapter.CommentAdapter
import com.maialearning.ui.adapter.ConsiderAdapter
import com.maialearning.ui.adapter.ProgramAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.HomeViewModel
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

const val type: String = "UCAS"
const val term = "Spring 2022"
const val plan = "Early Action"

class ConsideringFragment : Fragment(), OnItemClickOption, OnItemClick {
    var count: Int = 0
    var dialog: BottomSheetDialog? = null
    var notesDialog: BottomSheetDialog? = null
    private lateinit var dialogP: Dialog
    private lateinit var mBinding: ConsideringLayoutBinding
    private val homeModel: HomeViewModel by viewModel()
    lateinit var userid: String
    val finalArray: ArrayList<ConsiderModel.Data> = ArrayList()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        init()
//        setAdapter()
    }

    private fun init() {
        dialogP = showLoadingDialog(requireContext())
        dialogP.show()
        initObserver()
        SharedHelper(requireContext()).id?.let {
            userid = SharedHelper(requireContext()).id!!
            homeModel.getConsiderList(userid)
        }

    }

    private fun initObserver() {
        homeModel.listObserver.observe(requireActivity()) {
            it?.let {
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
                    var arrayCounselor: ArrayList<ConsiderModel.CounselorNotes> =
                        arrayListOf()
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

                    /*      var counselorNotes = object_.getJSONArray("counselor_notes")
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
                        object_.getString("internal_deadline"),
                        arrayProgram,
                        0,
                        object_.getString("notes"),
                        arrayCounselor
                    )
                    array.add(model)
                }
                finalArray.clear()
                var pos = 0
                // Sorted countries by name
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
                mBinding.universitisCounte.text = array.size.toString() + " Universities"
                mBinding.consideringList.adapter = ConsiderAdapter(this, finalArray, ::notesClick)
            }
        }
    }

    private fun notesClick(data: ConsiderModel.Data) {
        val sheetBinding: ConsideringNotesBottomsheetBinding =
            ConsideringNotesBottomsheetBinding.inflate(layoutInflater)
        notesDialog = BottomSheetDialog(requireContext())
        notesDialog?.setContentView(sheetBinding.root)
        sheetBinding.backBtn.setOnClickListener { notesDialog?.dismiss() }
        notesDialog?.show()
        sheetBinding.apply {
            //notesAddedby.setText(data.c)
        }

    }

    private fun setAdapter() {
        mBinding.consideringList.adapter = ConsiderAdapter(this, arrayListOf(), ::notesClick)
    }


    private fun setListeners() {

    }

    override fun onDestroyView() {
        dialog?.dismiss()
        super.onDestroyView()
    }


    private fun bottomSheetType(layoutId: Int, rbId: Int, type: Int) {
        dialog = BottomSheetDialog(requireContext())

        val view = layoutInflater.inflate(layoutId, null)
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        val radioAppType = view.findViewById<RadioGroup>(rbId)
        dialog?.setContentView(view)
        dialog?.show()
        view.findViewById<RelativeLayout>(R.id.close).setOnClickListener {
            dialog?.dismiss()
        }
        radioAppType.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = radioAppType.findViewById(checkedId) as RadioButton
            (mBinding.consideringList.adapter as ConsiderAdapter).setValue(
                radioButton.text.toString(),
                type
            )
        }
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

    override fun onInfoClick(postion: Int) {
        bottonSheetInfo(postion)
    }

    private fun bottonSheetInfo(postion: Int) {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: ConsideringInfoSheetBinding =
            ConsideringInfoSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.close.setOnClickListener {
            dialog.dismiss()
        }
        sheetBinding.saveBtn.setOnClickListener {
            var updateStudentPlan = UpdateStudentPlan()
            updateStudentPlan.student_uid = SharedHelper(requireContext()).id.toString()
            updateStudentPlan.college_nid = finalArray[postion].university_nid
            updateStudentPlan.school_within_university =
                sheetBinding.schoolWithinUniv.text.toString()
            updateStudentPlan.app_type="4"
            updateStudentPlan.request_transcript="0"
            if (sheetBinding.rateSpinner.selectedItemPosition != 0) {
                updateStudentPlan.college_interest =
                    sheetBinding.rateSpinner.selectedItemPosition.toString()
            }
            if (sheetBinding.interviewSpinner.selectedItemPosition != 0) {
                updateStudentPlan.interview_interest =
                    sheetBinding.interviewSpinner.selectedItemPosition.toString()
            }

            if (sheetBinding.yesRadio.isChecked) {
                updateStudentPlan.campus_tour = "1"
            } else {
                updateStudentPlan.campus_tour = "0"
            }

          //  dialogP.show()
            homeModel.updateStudentPlan(updateStudentPlan)
            homeModel.updateStudentPlanObserver.observe(requireActivity()) {
                dialog.dismiss()
                dialogP.dismiss()
            }
            homeModel.showError.observe(requireActivity()) {
                dialogP.dismiss()
            }
        }
    }


    private fun bottomSheetComment() {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: CommentsSheetBinding = CommentsSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
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
        val sheetBinding: LayoutProgramsBinding = LayoutProgramsBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()

        sheetBinding.addMoreLayout.adapter = ProgramAdapter(this)
        sheetBinding.addMore.setOnClickListener {
            (sheetBinding.addMoreLayout.adapter as ProgramAdapter).setCount(
                count++
            )
        }
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
    fun onInfoClick(postion: Int)
}
