package com.maialearning.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.*
import com.maialearning.model.*
import com.maialearning.ui.adapter.*
import com.maialearning.util.COLLEGE_JSON
import com.maialearning.util.OnLoadMoreListener
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.replaceInvertedComas
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

class ConsideringFragment : Fragment(), OnItemClickOption, OnItemClick, ClickOptionFilters {
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
        initObserver()
        dialogP.show()
        getConsideringList()
    }

    private fun getConsideringList() {
        SharedHelper(requireContext()).id?.let {
            userid = SharedHelper(requireContext()).id!!
            homeModel.getConsiderList(userid)
        }
    }

    private fun initObserver() {
        homeModel.listObserver.observe(requireActivity()) {
            it?.let {
                dialogP?.dismiss()
                finalArray.clear()
                if (JSONObject(it.toString()).getJSONObject(userid).has("data")) {
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
                        val arrayProgram: ArrayList<ConsiderModel.ProgramData> = arrayListOf()
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
                    var univModel = UnivCollegeModel()
                    var ids = ArrayList<String>()
                    for (i in finalArray.indices) {
                        ids.add(finalArray[i].universityNid)
                        selectedUnivId = finalArray[i].universityNid
                    }
                    univModel.university_nids = ids
                    dialogP.show()
                    homeModel.getCollegeJsonFilter(COLLEGE_JSON, univModel)

                }
            }
        }
        homeModel.univJsonFilter.observe(requireActivity()) {
            dialogP.dismiss()
            for (i in finalArray.indices) {
                selectedUnivId = finalArray[i].university_nid
                val json = JSONObject(it.toString())
                if (json.has(selectedUnivId)) {
                    var jsonUniv = json.optJSONObject(selectedUnivId)
                    if (jsonUniv.has("allowed_application_type")) {
                        var typeList = ArrayList<DynamicKeyValue>()

                        if (jsonUniv.optJSONObject("allowed_application_type") != null) {
                            allowedType = jsonUniv.optJSONObject("allowed_application_type")
//                            println("allowedType " + allowedType)
//                            println("allowedType " + selectedUnivId)
                            val keys: Iterator<*> = allowedType.keys()
                            while (keys.hasNext()) {
                                // loop to get the dynamic key
                                val currentDynamicKey = keys.next() as String
                                // get the value of the dynamic key
                                val term =
                                    jsonUniv.optJSONObject(allowedType.getString(currentDynamicKey))
                                var termList = ArrayList<String>()
                                val termListArray: JSONArray? = term.optJSONArray("term_list")
                                //collTerm.collTerm = termList
                                val collTerm: ConsiderModel.CollTerm =
                                    ConsiderModel.CollTerm()

                                collTerm.type = term.optString("type")
//                                println("term " + term.optString("type"))
                                //val collTermList: ArrayList<ConsiderModel.CollTerm> = arrayListOf()
                                val collPlanList: ArrayList<ConsiderModel.CollPlan> = arrayListOf()
                                termListArray?.let { termListArray ->
                                    for (i in 0 until termListArray.length()) {
                                        termList.add(termListArray.getString(i))
//                                        println("termListArray" + termListArray.getString(i))
                                        val plan: JSONArray? =
                                            term.optJSONArray(termListArray.getString(i))
                                        val collPlan = ConsiderModel.CollPlan()
                                        collPlan.plan = termListArray.getString(i)
                                        var decision: ArrayList<ConsiderModel.Decision> =
                                            arrayListOf()
                                        plan?.let { plan ->
                                            for (i in 0 until plan.length()) {
                                                val json: JSONObject = plan[i] as JSONObject
                                                val descisionItem = ConsiderModel.Decision(
                                                    json.optString("decision_plan"),
                                                    json.optString("decision_plan_value"),
                                                    json.optString("deadline_date")
                                                )
                                                decision?.add(descisionItem)
                                            }
                                        }
                                        collPlan.collPlan = decision
                                        collPlanList.add(collPlan)

                                        collTerm.collTerm = collPlanList
                                    }
                                    collTerm.termList = termList

                                }
//                                println("collTerm " + collTerm.termList.toString())
                                typeList.add(
                                    DynamicKeyValue(
                                        currentDynamicKey,
                                        allowedType.getString(currentDynamicKey),
                                        collTerm
                                    )
                                )

                            }

                            finalArray.get(i).collegeAppLicationType =
                                ConsiderModel.CollType(typeList)

                        } else if (jsonUniv.optJSONArray("allowed_application_type") != null) {
                            allowedTypeArray = jsonUniv.optJSONArray("allowed_application_type")
                            for (i in 0 until allowedTypeArray.length()) {
                                val term = allowedTypeArray.getJSONObject(i)
                                var termList = ArrayList<String>()
                                val termListArray: JSONArray? = term.optJSONArray("term_list")
                                //collTerm.collTerm = termList

                                val collTerm: ConsiderModel.CollTerm =
                                    ConsiderModel.CollTerm()

                                collTerm.type = term.optString("type")
                                val collTermList: ArrayList<ConsiderModel.CollTerm> = arrayListOf()
                                val collPlanList: ArrayList<ConsiderModel.CollPlan> = arrayListOf()
                                termListArray?.let { termListArray ->
                                    for (i in 0 until termListArray.length()) {
                                        termList.add(termListArray.getString(i))
                                        val plan: JSONArray? =
                                            term.optJSONArray(termListArray.getString(i))
                                        collTerm.termList = termList
                                        val collPlan = ConsiderModel.CollPlan()
                                        collPlan.plan = termListArray.getString(i)
                                        var decision: ArrayList<ConsiderModel.Decision> =
                                            arrayListOf()
                                        plan?.let { plan ->
                                            for (i in 0 until plan.length()) {
                                                val json: JSONObject = plan[i] as JSONObject
                                                val descisionItem = ConsiderModel.Decision(
                                                    json.optString("decision_plan"),
                                                    json.optString("decision_plan_value"),
                                                    json.optString("deadline_date")
                                                )
                                                decision?.add(descisionItem)
                                            }
                                        }
                                        collPlan.collPlan = decision
                                        collPlanList.add(collPlan)
                                        collTerm.termList = termList
                                        collTerm.collTerm = collPlanList
                                        collTermList.add(collTerm)
                                    }

                                }
                                typeList.add(
                                    DynamicKeyValue(
                                        term.optString("id"),
                                        term.optString("label"),
                                        collTerm
                                    )
                                )
                            }

                            finalArray.get(i).collegeAppLicationType =
                                ConsiderModel.CollType(typeList)

                        }


//                        if (typeList != null && typeList.size > 0 && type == 1) {
//                            recyclerView.adapter = ConsideringTypeTermAdapter(typeList, type, this)
//                        } else if (type == 0) {
//                            if (selectedValue != null) {
//                                val selectedJson = jsonUniv.optJSONObject(selectedValue)
//                                val termListArray = selectedJson.optJSONArray("term_list")
//                                if (termListArray != null) {
//                                    for (i in 0 until termListArray.length()) {
//                                        // get the value of the dynamic key
//                                        termList.add(
//                                            DynamicKeyValue(
//                                                "",
//                                                termListArray.getString(i), false
//                                            )
//                                        )
//
//
//                                        recyclerView.adapter =
//                                            ConsideringTypeTermAdapter(termList, type, this)
//                                    }
//                                }
//                            }
//                        }

//        radioAppType.setOnCheckedChangeListener { group, checkedId ->
//            val radioButton = radioAppType.findViewById(checkedId) as RadioButton
//            (mBinding.consideringList.adapter as ConsiderAdapter).setValue(
//                radioButton.text.toString(),
//                type
//            )
//        }
                    }
                }
            }
            mBinding.consideringList.adapter =
                ConsiderAdapter(this, finalArray, ::notesClick)
        }
        homeModel.showError.observe(requireActivity()) {
            dialogP.dismiss()
        }
    }

    private fun notesClick(data: ConsiderModel.Data) {
//        val sheetBinding: ConsideringNotesBottomsheetBinding =
//            ConsideringNotesBottomsheetBinding.inflate(layoutInflater)
//        notesDialog = BottomSheetDialog(requireContext())
//        notesDialog?.setContentView(sheetBinding.root)
//        sheetBinding.backBtn.setOnClickListener { notesDialog?.dismiss() }
//        notesDialog?.show()
//        sheetBinding.apply {
//            //notesAddedby.setText(data.c)
//        }

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

    lateinit var allowedType: JSONObject
    lateinit var allowedTypeArray: JSONArray

    var selectedUnivId: String = ""
    private fun bottomSheetType(
        layoutId: Int,
        rbId: Int,
        type: Int,
        arratlistPosition: Int,
        selectedValue: String
    ) {
        dialog = BottomSheetDialog(requireContext())

        val view = layoutInflater.inflate(layoutId, null)
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        val radioAppType = view.findViewById<RadioGroup>(rbId)
        dialog?.setContentView(view)
        dialog?.show()
        view.findViewById<RelativeLayout>(R.id.close).setOnClickListener {
            dialog?.dismiss()
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.consider_list_type)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        if (type == 1) {
            recyclerView.adapter =
                finalArray[arratlistPosition].collegeAppLicationType?.collType?.let {

                    ConsideringTypeTermAdapter(it, type, this)

                }
        } else if (type == 0) {
            for (i in finalArray[arratlistPosition].collegeAppLicationType?.collType?.indices!!) {
                if (finalArray[arratlistPosition].applicationMode == finalArray[i].collegeAppLicationType?.collType?.get(
                        i
                    )?.key
                ) {
                    finalArray[arratlistPosition].collegeAppLicationType?.collType?.get(i)?.term?.termList?.let {
                        recyclerView.adapter = ConsideringTermAdapter(
                            it, type, this
                        )
                        return
                    }
                }
            }
        }
        radioAppType.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = radioAppType.findViewById(checkedId) as RadioButton
            (mBinding.consideringList.adapter as ConsiderAdapter).setValue(
                radioButton.text.toString(),
                type
            )
        }
//        var univModel = UnivCollegeModel()
//        var ids = ArrayList<String>()
//        ids.add(finalArray[arratlistPosition].universityNid)
//        selectedUnivId = finalArray[arratlistPosition].universityNid
//        univModel.university_nids = ids
//
//        dialogP.show()
////        val obj = JsonObject()
////        val jsArray = JSONArray(ids)
////        obj.addProperty("university_nids", jsArray.toString())
//        homeModel.getCollegeJsonFilter(COLLEGE_JSON, univModel)
//        homeModel.univJsonFilter.observe(requireActivity()) {
//            dialogP.dismiss()
//            for ( i in finalArray.indices) {
//                selectedUnivId = finalArray[i].university_nid
//            val json = JSONObject(it.toString())
//            if (json.has(selectedUnivId)) {
//                jsonUniv = json.optJSONObject(selectedUnivId)
//                if (jsonUniv.has("allowed_application_type")) {
//                    var typeList = ArrayList<DynamicKeyValue>()
//
//                    if (jsonUniv.optJSONObject("allowed_application_type") != null) {
//                        allowedType = jsonUniv.optJSONObject("allowed_application_type")
//                        val keys: Iterator<*> = allowedType.keys()
//                        while (keys.hasNext()) {
//                            // loop to get the dynamic key
//                            val currentDynamicKey = keys.next() as String
//                            // get the value of the dynamic key
//                            typeList.add(
//                                DynamicKeyValue(
//                                    currentDynamicKey,
//                                    allowedType.getString(currentDynamicKey)
//                                )
//                            )
//                            val term = jsonUniv.optJSONObject( allowedType.getString(currentDynamicKey))
//                            var termList = ArrayList<String>()
//                            val termListArray:JSONArray = term.optJSONArray("term_list")
//                            val collTerm : ConsiderModel.CollTerm = ConsiderModel.CollTerm(termList, decisionPlan)
//
//                            for (i in 0 ..termListArray.length()){
//                                termList.add(termListArray.getString(i))
//                                val plan = term.optJSONArray(termListArray.getString(i))
//                               var decision: ArrayList<ConsiderModel.Decision> = arrayListOf()
//                                for (i in 0 ..plan.length()){
//                                    val json:JSONObject = plan[i] as JSONObject
//                                val descisionItem = ConsiderModel.Decision(json.optString("decision_plan"),
//                                    json.optString("decision_plan_value"), json.optString("deadline_date") )
//                                    decision.add(descisionItem)
//                                }
//                                val decisionPlan: ConsiderModel.DecisionPlan = ConsiderModel.DecisionPlan(decision)
//                                collTerm.plan = decisionPlan
//                            }
//
//
//                        }
//
//                       finalArray.get(i).collegeAppLicationType = ConsiderModel.CollType(typeList)
//                    } else if (jsonUniv.optJSONArray("allowed_application_type") != null) {
//                        allowedTypeArray = jsonUniv.optJSONArray("allowed_application_type")
//                        for (i in 0 until allowedTypeArray.length()) {
//                            val item = allowedTypeArray.getJSONObject(i)
//                            typeList.add(
//                                DynamicKeyValue(
//                                    item.optString("id"),
//                                    item.optString("label"),
//                                    false
//                                )
//                            )
//                        }
//                    }
//
//                    if (typeList != null && typeList.size > 0 && type == 1) {
//                        recyclerView.adapter = ConsideringTypeTermAdapter(typeList, type, this)
//                    } else if (type == 0) {
//                        if (selectedValue != null) {
//                            val selectedJson = jsonUniv.optJSONObject(selectedValue)
//                            val termListArray = selectedJson.optJSONArray("term_list")
//                            if (termListArray != null) {
//                                for (i in 0 until termListArray.length()) {
//                                    // get the value of the dynamic key
//                                    termList.add(
//                                        DynamicKeyValue(
//                                            "",
//                                            termListArray.getString(i), false
//                                        )
//                                    )
//
//
//                                    recyclerView.adapter =
//                                        ConsideringTypeTermAdapter(termList, type, this)
//                                }
//                            }
//                        }
//                    }
//                }
////        radioAppType.setOnCheckedChangeListener { group, checkedId ->
////            val radioButton = radioAppType.findViewById(checkedId) as RadioButton
////            (mBinding.consideringList.adapter as ConsiderAdapter).setValue(
////                radioButton.text.toString(),
////                type
////            )
////        }
//                }
//            }
//        }
    }


    var typeTermPosition = -1
    override fun onTypeClick(postion: Int) {
        typeTermPosition = postion
        bottomSheetType(R.layout.application_type, R.id.radio_app_type, 1, postion, "")
    }

    override fun onTermClick(postion: Int) {
        typeTermPosition = postion
        bottomSheetType(R.layout.application_term, R.id.radio_app_term, 0, postion, "")
    }

    override fun onPlanClick(postion: Int) {
        typeTermPosition = postion
        bottomSheetType(R.layout.application_plan_filter, R.id.radio_action, 2, postion, "")
    }

    override fun onCommentClick() {
        bottomSheetComment()
    }

    override fun onAddClick(position: Int) {
        bottomSheetProgram(position)
    }

    override fun onInfoClick(postion: Int) {
        bottonSheetInfo(postion)
    }

    override fun onApplyingClick(postion: Int) {
        SharedHelper(requireContext()).id?.let {
            dialogP.show()
            homeModel.moveToApplying(
                it,
                finalArray.get(postion).university_nid,
                "1"
            )
        }
        homeModel.applyingObserver.observe(requireActivity()) {
//            finalArray.removeAt(postion)
//            if (finalArray.get(postion).count != 0)
//                finalArray.get(postion).count = finalArray.get(postion).count - 1
//            mBinding.consideringList.adapter?.notifyDataSetChanged()
//            mBinding.universitisCounte.text = finalArray.size.toString() + " Universities"
            dialogP.dismiss()
            getConsideringList()
        }
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
            updateStudentPlan.app_type = "4"
            updateStudentPlan.request_transcript = "0"
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

            dialogP.show()
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
                dialog.dismiss()
                getConsideringList()
            }
        }

    }

    override fun onTranscriptRequest(postion: Int, chcked: String) {
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
            .setMessage("Are you sure you want to delete college?")
            .setPositiveButton(
                "Yes"
            ) { dialog, _ -> deleteWork(position) }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteWork(position: Int) {
        SharedHelper(requireContext()).id?.let {
            dialogP.show()
            homeModel.hidel(
                it,
                finalArray.get(position).university_nid
            )
        }
        homeModel.delObserver.observe(requireActivity()) {
            getConsideringList()
        }
    }

    override fun onClick(positiion: Int) {

    }

    var selectedKeyType: String = ""
    override fun onItemClick(positiion: Int, type: Int, dynamicKeyValue: DynamicKeyValue) {
        var updateStudentPlan = UpdateStudentPlan()
        updateStudentPlan.student_uid = SharedHelper(requireContext()).id.toString()
        updateStudentPlan.college_nid = selectedUnivId
        if (type == 1) {
            selectedKeyType = dynamicKeyValue.key
            updateStudentPlan.app_type = dynamicKeyValue.key
            updateStudentPlan.app_status = "accepted"
        }
        updateStudentPlan.app_status = "3"
        dialogP = showLoadingDialog(requireContext())
        dialogP.show()
        homeModel.updateStudentPlan(updateStudentPlan)
        homeModel.updateStudentPlanObserver.observe(requireActivity()) {
            dialog?.dismiss()
            dialogP.dismiss()
            if (type == 1) {
                finalArray.get(typeTermPosition).applicationTerm = dynamicKeyValue.value
                mBinding.consideringList.adapter?.notifyDataSetChanged()

            }
        }
    }

    override fun onTermItemClick(positiion: Int, type: Int, dynamicKeyValue: String) {
        var updateStudentPlan = UpdateStudentPlan()
        updateStudentPlan.student_uid = SharedHelper(requireContext()).id.toString()
        updateStudentPlan.college_nid = selectedUnivId
        updateStudentPlan.app_type = finalArray.get(typeTermPosition).applicationMode
        //updateStudentPlan.app_status = "accepted"
        updateStudentPlan.application_term = dynamicKeyValue
        updateStudentPlan.app_status = "3"
        dialogP = showLoadingDialog(requireContext())
        dialogP.show()
        homeModel.updateStudentPlan(updateStudentPlan)
        homeModel.updateStudentPlanObserver.observe(requireActivity()) {
            dialog?.dismiss()
            dialogP.dismiss()
            finalArray.get(typeTermPosition).applicationTerm = dynamicKeyValue
            mBinding.consideringList.adapter?.notifyDataSetChanged()
        }
    }

}

interface ClickOptionFilters {
    fun onItemClick(positiion: Int, type: Int, dynamicKeyValue: DynamicKeyValue)
    fun onTermItemClick(positiion: Int, type: Int, dynamicKeyValue: String)
}

interface OnItemClickOption {
    fun onTypeClick(postion: Int)
    fun onTermClick(postion: Int)
    fun onPlanClick(postion: Int)
    fun onCommentClick()
    fun onAddClick(postion: Int)
    fun onInfoClick(postion: Int)
    fun onApplyingClick(postion: Int)
    fun onMenuClick(postion: Int, it: View?)
    fun onTranscriptRequest(postion: Int, checked: String)
}
