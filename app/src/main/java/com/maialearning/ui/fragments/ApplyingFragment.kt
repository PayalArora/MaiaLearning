package com.maialearning.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.CommentsSheetBinding
import com.maialearning.databinding.FragmentApplyingBinding
import com.maialearning.databinding.LayoutProgramsBinding
import com.maialearning.model.*
import com.maialearning.ui.adapter.*
import com.maialearning.util.*
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.viewmodel.HomeViewModel
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class ApplyingFragment(val tabs: TabLayout) : Fragment(), OnItemClickOption, OnItemClick,
    ClickOptionFilters {
    var selectedValue = ""
    var dialog: BottomSheetDialog? = null
    var typeTermPosition = -1
    private lateinit var mBinding: FragmentApplyingBinding
    private lateinit var dialogP: Dialog
    private val homeModel: HomeViewModel by viewModel()
    val finalArray: ArrayList<ConsiderModel.Data> = ArrayList()
    lateinit var userid: String
    var selectedUnivId: String = ""
    var positio: Int = 0
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
                    var requiredRecs: ConsiderModel.RequiredRecommendation? = null
                    val jobj: JSONObject? = object_.optJSONObject("required_recommendation")
                    jobj?.let {
                        requiredRecs = ConsiderModel.RequiredRecommendation(
                            it.optString("teacher_evaluation"),
                            it.optString("max_teacher_evaluation"),
                            it.optString("counselor_recommendation")
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
                        object_.getString("unitid"),
                        object_.getString("internal_deadline"),
                        object_.getString("due_date"),
                        arrayProgram,
                        0,
                        object_.getString("notes"),
                        arrayCounselor, object_.getString("request_transcript"),
                        object_.getString("application_type"),
                        object_.optString("application_term"),
                        object_.getString("application_mode"),
                        object_.getString("application_status_name"),
                        object_.getString("app_by_program_supported"),
                        object_.getInt("confirm_applied"), null, requiredRecs,

                        object_.getInt("manual_update"),
                        object_.optString("application_round"),
                        object_.optString("status")

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

                // (mBinding.applyingList.adapter as ApplyingAdapter).updateAdapter(finalArray)
                //mBinding.universitisCounte.text = array.size.toString() + " Universities"
                var univModel = UnivCollegeModel()
                var ids = java.util.ArrayList<String>()
                for (i in finalArray.indices) {
                    ids.add(finalArray[i].universityNid)
                    selectedUnivId = finalArray[i].universityNid
                }
                univModel.university_nids = ids
                dialogP.show()
                homeModel.getCollegeJsonFilter(COLLEGE_JSON, univModel)
            }
        }
        homeModel.showError.observe(requireActivity()) {
            dialogP.dismiss()
            context?.showToast(it)
        }
        lateinit var allowedType: JSONObject
        lateinit var allowedTypeArray: JSONArray
        homeModel.univJsonFilter.observe(requireActivity()) {
            dialogP.dismiss()
            for (i in finalArray.indices) {
                selectedUnivId = finalArray[i].university_nid
                val json = JSONObject(it.toString())
                if (json.has(selectedUnivId)) {
                    var jsonUniv = json.optJSONObject(selectedUnivId)
                    var selectedAppModeType: String? = null
                    if (jsonUniv.has("allowed_application_type")) {
                        var typeList = java.util.ArrayList<DynamicKeyValue>()

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
                                val collTerm: ConsiderModel.CollTerm =
                                    ConsiderModel.CollTerm()

                                collTerm.type = term.optString("type")
                                if (finalArray.get(i).applicationMode == currentDynamicKey) {
                                    selectedAppModeType = collTerm.type
                                }
                                if (collTerm.type == "term") {
                                    var termList = java.util.ArrayList<String>()

                                    val termListArray: JSONArray? = term.optJSONArray("term_list")
                                    //collTerm.collTerm = termList

//                                println("term " + term.optString("type"))
                                    //val collTermList: ArrayList<ConsiderModel.CollTerm> = arrayListOf()
                                    val collPlanList: java.util.ArrayList<ConsiderModel.CollPlan> =
                                        arrayListOf()
                                    termListArray?.let { termListArray ->
                                        for (i in 0 until termListArray.length()) {
                                            termList.add(termListArray.getString(i))
//                                        println("termListArray" + termListArray.getString(i))
                                            val plan: JSONArray? =
                                                term.optJSONArray(termListArray.getString(i))
                                            val collPlan = ConsiderModel.CollPlan()
                                            collPlan.plan = termListArray.getString(i)
                                            var decision: java.util.ArrayList<ConsiderModel.Decision> =
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
                                } else {
                                    val arrayPlans = arrayListOf<ConsiderModel.DecisionPlan>()
                                    if (term.optJSONArray("data") != null) {
                                        val decisionPlans: JSONArray = term.optJSONArray("data")
                                        decisionPlans?.let {
                                            for (i in 0 until it.length()) {


                                                val plan: ConsiderModel.DecisionPlan =
                                                    ConsiderModel.DecisionPlan(
                                                        it.getJSONObject(i).optString("id"),
                                                        it.getJSONObject(i).optString("label")
                                                    )
                                                arrayPlans.add(plan)
                                            }
                                        }
                                        collTerm.planList = arrayPlans
                                    } else if (term.optJSONObject("data") != null) {
                                        val jobj: JSONObject? = term.optJSONObject("data")
                                        jobj?.let {

                                            val iterator = it.keys()
                                            while (iterator.hasNext()) {
                                                val id = iterator.next() as String
                                                val plan: ConsiderModel.DecisionPlan =
                                                    ConsiderModel.DecisionPlan(
                                                        id,
                                                        it.optString(id)
                                                    )
                                                arrayPlans.add(plan)
                                            }
                                        }
                                        collTerm.planList = arrayPlans
                                    }


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
                                ConsiderModel.CollType(typeList, selectedAppModeType)

                        }
//                        else if (jsonUniv.optJSONArray("allowed_application_type") != null) {
//                            allowedTypeArray = jsonUniv.optJSONArray("allowed_application_type")
//                            for (i in 0 until allowedTypeArray.length()) {
//                                val type = allowedTypeArray.getJSONObject(i)
//                                var termList = java.util.ArrayList<String>()
//
//                                //collTerm.collTerm = termList
//
//                                val collTerm: ConsiderModel.CollTerm =
//                                    ConsiderModel.CollTerm()
//                                val term: JSONObject? =
//                                    jsonUniv.optJSONObject(type.optString("label"))
//                                term?.let {
//                                    collTerm.type = term?.optString("type")
//                                    if (finalArray.get(i).applicationMode == type.optString("id")) {
//                                        selectedAppModeType = collTerm.type
//                                    }
//                                    if (collTerm.type == "term") {
//                                        val termListArray: JSONArray? =
//                                            type?.optJSONArray("term_list")
//                                        val collTermList: java.util.ArrayList<ConsiderModel.CollTerm> =
//                                            arrayListOf()
//                                        val collPlanList: java.util.ArrayList<ConsiderModel.CollPlan> =
//                                            arrayListOf()
//                                        termListArray?.let { termListArray ->
//                                            for (i in 0 until termListArray.length()) {
//                                                termList.add(termListArray.getString(i))
//                                                val plan: JSONArray? =
//                                                    term.optJSONArray(termListArray.getString(i))
//                                                collTerm.termList = termList
//                                                val collPlan = ConsiderModel.CollPlan()
//                                                collPlan.plan = termListArray.getString(i)
//                                                var decision: java.util.ArrayList<ConsiderModel.Decision> =
//                                                    arrayListOf()
//                                                plan?.let { plan ->
//                                                    for (i in 0 until plan.length()) {
//                                                        val json: JSONObject = plan[i] as JSONObject
//                                                        val descisionItem = ConsiderModel.Decision(
//                                                            json.optString("decision_plan"),
//                                                            json.optString("decision_plan_value"),
//                                                            json.optString("deadline_date")
//                                                        )
//                                                        decision?.add(descisionItem)
//                                                    }
//                                                }
//                                                collPlan.collPlan = decision
//                                                collPlanList.add(collPlan)
//                                                collTerm.termList = termList
//                                                collTerm.collTerm = collPlanList
//                                                collTermList.add(collTerm)
//                                            }
//
//                                        }
//                                    } else {
//                                        val decisionPlans: JSONArray = term?.optJSONArray("data")
//                                        val arrayPlans = arrayListOf<ConsiderModel.DecisionPlan>()
//                                        decisionPlans?.let {
//                                            for (i in 0 until it.length()) {
//
//
//                                                val plan: ConsiderModel.DecisionPlan =
//                                                    ConsiderModel.DecisionPlan(
//                                                        it.getJSONObject(i).optString("id"),
//                                                        it.getJSONObject(i).optString("label")
//                                                    )
//                                                arrayPlans.add(plan)
//                                            }
//                                        }
//                                        collTerm.planList = arrayPlans
//                                    }
//                                }
//                                typeList.add(
//                                    DynamicKeyValue(
//                                        type.optString("id"),
//                                        type.optString("label"),
//                                        collTerm
//                                    )
//                                )
//
//                            }
//
//                            finalArray.get(i).collegeAppLicationType =
//                                ConsiderModel.CollType(typeList, selectedAppModeType)
//
//                        }
                        (mBinding.applyingList.adapter as ApplyingAdapter).updateAdapter(finalArray)
                    }
                }
            }
        }
    }

    private fun setAdapter() {
        mBinding.applyingList.adapter = ApplyingAdapter(this, arrayListOf())
    }

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
                    val array = arrayListOf<DynamicKeyValue>()
                    array.clear()
                    array.addAll(it)
                    val term = ConsiderModel.CollTerm()
                    array!!.add(DynamicKeyValue("Reset", "Reset", term))
                    ConsideringTypeTermAdapter(array, type, this)

                }
        } else if (type == 0) {
            for (i in finalArray[arratlistPosition].collegeAppLicationType?.collType?.indices!!) {
                if (finalArray[arratlistPosition].applicationMode == finalArray[arratlistPosition].collegeAppLicationType?.collType?.get(
                        i
                    )?.key
                ) {
                    if (finalArray[arratlistPosition].collegeAppLicationType?.collType?.get(i)?.term?.type == "term") {
                        finalArray[arratlistPosition].collegeAppLicationType?.collType?.get(i)?.term?.termList?.let {
                            recyclerView.adapter = ConsideringTermAdapter(
                                it, type, this
                            )
                            return
                        }
                    } else {
                        recyclerView.adapter = ConsideringTermAdapter(
                            java.util.ArrayList(Arrays.asList(* resources.getStringArray(R.array.APPLICATION_TERM))),
                            type,
                            this
                        )
                    }
                }
            }
        } else if (type == 2) {
            for (i in finalArray[arratlistPosition].collegeAppLicationType?.collType?.indices!!) {
                for (k in finalArray[arratlistPosition].collegeAppLicationType?.collType?.indices!!) {
                    if (finalArray[arratlistPosition].collegeAppLicationType?.collType?.get(i)?.term?.type == "term") {
//
                        if (finalArray[arratlistPosition].applicationTerm.equals(
                                finalArray[arratlistPosition].collegeAppLicationType?.collType?.get(
                                    k
                                )?.term?.collTerm?.get(k)?.plan
                            )
                        ) {
                            finalArray[arratlistPosition].collegeAppLicationType?.collType?.get(k)?.term?.collTerm?.get(
                                k
                            )?.collPlan
                            finalArray[arratlistPosition].collegeAppLicationType?.collType?.get(i)?.term?.collTerm?.get(
                                k
                            )?.collPlan?.let {
                                val array = arrayListOf<ConsiderModel.Decision>()
                                array.clear()
                                array.addAll(it)
                                array!!.add(ConsiderModel.Decision("Reset", "Reset", null))

                                recyclerView.adapter = ConsiderPlanAdapter(
                                    array, type, this
                                )
                                return
                            }
                        }
                    } else {
                        finalArray[arratlistPosition].collegeAppLicationType?.collType?.get(i)?.term?.planList?.let {
                            val array = arrayListOf<ConsiderModel.DecisionPlan>()
                            array.clear()
                            array.addAll(it)
                            array!!.add(ConsiderModel.DecisionPlan("Reset", "Reset"))

                            recyclerView.adapter = ConsiderDecisionAdapter(
                                it, type, this
                            )
                            return
                        }

                    }
                }
            }
        }
        radioAppType.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = radioAppType.findViewById(checkedId) as RadioButton
            (mBinding.applyingList.adapter as ApplyingAdapter).setValue(
                radioButton.text.toString(),
                type
            )
        }
    }


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
        //     bottomSheetComment()
    }

    private fun bottomSheetComment() {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: com.maialearning.databinding.CommentsSheetBinding =
            CommentsSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight =
            ((Resources.getSystem().displayMetrics.heightPixels))
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
        if (finalArray.get(postion).confirmApplied == 1) {
            showConfirmDialog(postion)
        } else {
            showDialogDate(postion)
        }
    }

    private fun showConfirmDialog(postion: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm")
            .setMessage("Confirm Not Applied")
            .setCancelable(false)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(
                "Confirm"
            ) { dialog, whichButton ->
                var updateStudentPlan = UpdateStudentPlan()
                updateStudentPlan.student_uid = SharedHelper(requireContext()).id.toString()
                updateStudentPlan.college_nid = finalArray[postion].university_nid

                updateStudentPlan.confirm_applied = 0

                dialogP.show()
                homeModel.updateStudentPlan(updateStudentPlan)
                homeModel.updateStudentPlanObserver.observe(requireActivity()) {
                    finalArray.get(postion).confirmApplied = 0
                    mBinding.applyingList.adapter?.notifyDataSetChanged()
                    dialogP.dismiss()
                    context?.resources?.getString(R.string.updated)
                        ?.let { it1 -> context?.showToast(it1) }

                    dialog?.dismiss()
                }
                homeModel.showError.observe(requireActivity()) {
                    updateStudentPlan.confirm_applied = finalArray[postion].confirmApplied
                    mBinding.applyingList.adapter?.notifyDataSetChanged()
                    dialogP.dismiss()
                }
            }
            .setNegativeButton("Cancel") { dialog, whichButton ->
                mBinding.applyingList.adapter?.notifyDataSetChanged()
            }.show()
    }


    fun showDialogDate(postion: Int) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val width: Int =
            (requireContext().getResources()
                .getDisplayMetrics().widthPixels) - 30 //<-- int width=400;
        dialog.setContentView(R.layout.submitted_date_lay)
        dialog.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        val back = ColorDrawable(Color.WHITE)
        val inset = InsetDrawable(back, 16, -8, 16, -8)
        dialog.window!!.setBackgroundDrawable(inset)
        dialog.setCancelable(false)

        // dialog.setContentView(R.layout.submitted_date_lay)
        val text = dialog.findViewById<View>(R.id.calender_edt) as TextView
        text.setOnClickListener {
            text.showDatePicker(requireContext(), ::setDate)
        }
        val dialogButton = dialog.findViewById<View>(R.id.btn_submit) as Button
        dialogButton.setOnClickListener {
            if (!text.text.toString().isNullOrEmpty()) {
                var updateStudentPlan = UpdateStudentPlan()
                updateStudentPlan.student_uid = SharedHelper(requireContext()).id.toString()
                updateStudentPlan.college_nid = finalArray[postion].university_nid

                updateStudentPlan.confirm_applied = 1
                updateStudentPlan.confirm_applied_time =
                    convertDateToLong(text.text.toString()).toString()
                dialogP.show()
                homeModel.updateStudentPlan(updateStudentPlan)
                homeModel.updateStudentPlanObserver.observe(requireActivity()) {
                    finalArray.get(postion).confirmApplied = 1
                    mBinding.applyingList.adapter?.notifyDataSetChanged()
                    dialogP.dismiss()
                    context?.resources?.getString(R.string.updated)
                        ?.let { it1 -> context?.showToast(it1) }

                    dialog?.dismiss()
                }
                homeModel.showError.observe(requireActivity()) {
                    updateStudentPlan.confirm_applied = finalArray[postion].confirmApplied
                    mBinding.applyingList.adapter?.notifyDataSetChanged()
                    dialogP.dismiss()
                }
            } else {
                mBinding.applyingList.adapter?.notifyDataSetChanged()
                dialog?.dismiss()
            }
        }
        val dialogButtonCancel = dialog.findViewById<View>(R.id.btn_cancel) as Button
        dialogButtonCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

    private fun setDate(s: String) {

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

    override fun onDeadlineClick(postion: Int, deadline: String) {
        typeTermPosition = postion
        var updateStudentPlan = UpdateStudentPlan()
        updateStudentPlan.student_uid = SharedHelper(requireContext()).id.toString()
        updateStudentPlan.college_nid = finalArray.get(typeTermPosition).university_nid
        updateStudentPlan.application_term = finalArray.get(typeTermPosition).applicationTerm
        updateStudentPlan.request_transcript = finalArray.get(typeTermPosition).requestTranscript
        updateStudentPlan.app_plan = finalArray.get(typeTermPosition).applicationType
        updateStudentPlan.app_type = finalArray.get(typeTermPosition).applicationMode
        updateStudentPlan.deadline_date =
            formateDateFromstring("MMM dd yyyy", "MM/dd/yyyy", deadline)

        dialogP = showLoadingDialog(requireContext())
        dialogP.show()
        homeModel.updateStudentPlan(updateStudentPlan)
        homeModel.updateStudentPlanObserver.observe(requireActivity()) {
            dialogP.dismiss()

            finalArray.get(typeTermPosition).dueDate =
                formateDateFromstring("MMM dd yyyy", "yyyy-MM-dd hh:mm:ss", deadline)
            mBinding.applyingList.adapter?.notifyDataSetChanged()
        }
    }

    private fun bottomSheetProgram(postion: Int) {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: LayoutProgramsBinding =
            LayoutProgramsBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight =
            ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        val isAppByProgram =
            (finalArray[postion].appByProgramSupported == "1" && finalArray[postion].applicationMode != "3")
        val canShowProgramWithDeadline = isAppByProgram
        var addedPrograms: ArrayList<AddProgramConsider.Programs?>? = ArrayList()
        for (i in finalArray[postion].program_data?.indices!!) {
            var programData: AddProgramConsider.Programs = AddProgramConsider.Programs()
            programData.program_name =
                finalArray[postion].program_data?.get(i)?.program_name.toString()
            programData.program_id = finalArray[postion].program_data?.get(i)?.program_id
            addedPrograms?.add(programData)
        }
        var deletedPrograms: ArrayList<String> = ArrayList()
        sheetBinding.addMoreLayout.adapter =
            ProgramAdapter(addedPrograms, deletedPrograms, this, canShowProgramWithDeadline)
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

    var selectedKeyType: String? = null
    override fun onItemClick(positiion: Int, type: Int, dynamicKeyValue: DynamicKeyValue) {
        var updateStudentPlan = UpdateStudentPlan()
        updateStudentPlan.student_uid = SharedHelper(requireContext()).id.toString()
        updateStudentPlan.college_nid = finalArray.get(typeTermPosition).university_nid
        if (type == 1) {
            if (dynamicKeyValue.key == "Reset") {
                selectedKeyType = null
                updateStudentPlan.app_type = null
                updateStudentPlan.application_term = null
                updateStudentPlan.app_plan = null
                updateStudentPlan.deadline_date = null
            } else {
                selectedKeyType = dynamicKeyValue.key
                updateStudentPlan.app_type = dynamicKeyValue.key
                updateStudentPlan.app_status = "accepted"
            }
        } else if (type == 0) {
            if (dynamicKeyValue.key == "Reset") {
                selectedKeyType = null
                updateStudentPlan.application_term = null
                updateStudentPlan.app_plan = null
                updateStudentPlan.deadline_date = null
            } else {
                selectedKeyType = dynamicKeyValue.key
                updateStudentPlan.application_term = dynamicKeyValue.key
                updateStudentPlan.app_status = "accepted"
            }
        }
        updateStudentPlan.app_status = "3"
        dialogP = showLoadingDialog(requireContext())
        dialogP.show()
        homeModel.updateStudentPlan(updateStudentPlan)
        homeModel.updateStudentPlanObserver.observe(requireActivity()) {
            dialog?.dismiss()
            dialogP.dismiss()
            if (type == 1) {
                if (dynamicKeyValue.key == "Reset") {
                    finalArray.get(typeTermPosition).applicationMode = null
                    finalArray.get(typeTermPosition).applicationTerm = null
                    finalArray.get(typeTermPosition).applicationType = null
                    finalArray.get(typeTermPosition).dueDate = null
                } else
                    finalArray.get(typeTermPosition).applicationMode = dynamicKeyValue.key
                mBinding.applyingList.adapter?.notifyDataSetChanged()

            } else if (type == 0) {
                if (dynamicKeyValue.key == "Reset") {
                    finalArray.get(typeTermPosition).applicationTerm = null
                    finalArray.get(typeTermPosition).applicationType = null
                    finalArray.get(typeTermPosition).dueDate = null
                } else
                    finalArray.get(typeTermPosition).applicationTerm = dynamicKeyValue.value
                mBinding.applyingList.adapter?.notifyDataSetChanged()

            }
        }
    }

    override fun onTermItemClick(positiion: Int, type: Int, dynamicKeyValue: String) {
        var updateStudentPlan = UpdateStudentPlan()
        updateStudentPlan.student_uid = SharedHelper(requireContext()).id.toString()
        updateStudentPlan.college_nid = finalArray.get(typeTermPosition).university_nid
        updateStudentPlan.app_type = finalArray.get(typeTermPosition).applicationMode
        if (dynamicKeyValue == "Reset") {
            selectedKeyType = null
            updateStudentPlan.application_term = null
            updateStudentPlan.app_plan = null
            updateStudentPlan.deadline_date = null
        } else {
            selectedKeyType = dynamicKeyValue
            updateStudentPlan.application_term = dynamicKeyValue
            //  updateStudentPlan.app_status = "accepted"
        }

        updateStudentPlan.app_status = "3"
        dialogP = showLoadingDialog(requireContext())
        dialogP.show()
        homeModel.updateStudentPlan(updateStudentPlan)
        homeModel.updateStudentPlanObserver.observe(requireActivity()) {
            dialog?.dismiss()
            dialogP.dismiss()
            if (dynamicKeyValue == "Reset") {
                finalArray.get(typeTermPosition).applicationTerm = null
                finalArray.get(typeTermPosition).applicationType = null
                finalArray.get(typeTermPosition).dueDate = null
            } else
                finalArray.get(typeTermPosition).applicationTerm = dynamicKeyValue
            mBinding.applyingList.adapter?.notifyDataSetChanged()
        }
    }

    override fun onPlanOptionClick(
        positiion: Int,
        type: Int,
        dynamicKey: String, dynamicKeyValue: String
    ) {
        var updateStudentPlan = UpdateStudentPlan()
        updateStudentPlan.student_uid = SharedHelper(requireContext()).id.toString()
        updateStudentPlan.college_nid = finalArray.get(typeTermPosition).university_nid
        updateStudentPlan.app_type = finalArray.get(typeTermPosition).applicationMode
        //  updateStudentPlan.app_status = finalArray.get(typeTermPosition).applicationStatusName
        updateStudentPlan.app_plan = dynamicKey
        updateStudentPlan.application_term = finalArray.get(typeTermPosition).applicationTerm
        updateStudentPlan.request_transcript = finalArray.get(typeTermPosition).requestTranscript
        if (dynamicKeyValue == "Reset") {
            finalArray.get(typeTermPosition).dueDate = null
        }
        //updateStudentPlan.app_status = "3"
        dialogP = showLoadingDialog(requireContext())
        dialogP.show()
        homeModel.updateStudentPlan(updateStudentPlan)
        homeModel.updateStudentPlanObserver.observe(requireActivity()) {
            dialog?.dismiss()
            dialogP.dismiss()
            if (!dynamicKeyValue.equals("Reset"))
                finalArray.get(typeTermPosition).applicationType = dynamicKey
            else {
                finalArray.get(typeTermPosition).applicationType = null
                finalArray.get(typeTermPosition).dueDate = null
            }
            mBinding.applyingList.adapter?.notifyDataSetChanged()
        }
    }

}

