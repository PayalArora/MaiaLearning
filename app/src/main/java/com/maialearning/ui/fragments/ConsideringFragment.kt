package com.maialearning.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.*
import com.maialearning.model.*
import com.maialearning.ui.adapter.*
import com.maialearning.util.*
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.viewmodel.HomeViewModel
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

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
    var deletedArray: ArrayList<ConsiderModel.Data> = ArrayList()
    var activeArray: ArrayList<ConsiderModel.Data> = ArrayList()
    var filteredArray: ArrayList<ConsiderModel.Data> = ArrayList()
    val countryArrayActive: ArrayList<KeyVal> = ArrayList()
    val countryArrayDeleted: ArrayList<KeyVal> = ArrayList()
    var mainDialog: BottomSheetDialog? = null
    var selectedCountryActive = ""
    var selectedCountryDeleted = ""
    var selectedApplyingActive = ""
    var selectedApplyingDeleted = ""
    var selectedConsider = ACTIVE_CONSIDER
    val applyingWithList: ArrayList<KeyVal> = ArrayList()
    val bulkMoveList: ArrayList<String> = ArrayList()

    lateinit var considerAdapter: ConsiderAdapter

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
    }

    private fun init() {
        dialogP = showLoadingDialog(requireContext())
        initObserver()
        dialogP.show()
        getConsideringList()
        val filter = activity?.findViewById<ImageView>(R.id.toolbar_messanger)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        if (viewPager?.currentItem == 1) {
            filter?.setOnClickListener {
                filterWork()
            }
        }
    }

    var sheetBindingUniv: ConsideringFilterBinding? = null

    private fun filterWork() {
        mainDialog = activity?.let { BottomSheetDialog(it) }

        sheetBindingUniv = ConsideringFilterBinding.inflate(layoutInflater)
        sheetBindingUniv?.root?.minimumHeight =
            ((Resources.getSystem().displayMetrics.heightPixels))
        mainDialog?.setContentView(sheetBindingUniv!!.root)
        // sheetBindingUniv?.search?.visibility = View.GONE
        sheetBindingUniv?.filters?.setText(resources.getString(R.string.filters))
        mainDialog?.show()
        when (selectedConsider) {

            ACTIVE_CONSIDER -> {
                sheetBindingUniv!!.activeConsider.isChecked = true
                sheetBindingUniv!!.deletedConsider.isChecked = false
            }
            DELETED -> {
                sheetBindingUniv!!.activeConsider.isChecked = false
                sheetBindingUniv!!.deletedConsider.isChecked = true
            }
        }
        sheetBindingUniv!!.clearText.setOnClickListener {

            selectedCountryActive = ""
            selectedCountryDeleted = ""
            selectedApplyingActive = ""
            selectedApplyingDeleted = ""
            mainDialog?.dismiss()
        }
        sheetBindingUniv!!.backBtn.setOnClickListener {

            mainDialog?.dismiss()

        }

        dialogP.show()
        homeModel.getConsideringApplyingWith()

        sheetBindingUniv!!.countryItem.setOnClickListener {
            subFilter("Country")
        }
        sheetBindingUniv!!.activeConsider.setOnClickListener {
            selectedConsider = ACTIVE_CONSIDER
            activeConsideringWork()

        }
        sheetBindingUniv!!.deletedConsider.setOnClickListener {
            selectedConsider = DELETED
            deletedConsideringWork()
        }

        sheetBindingUniv!!.applyingItem.setOnClickListener {
            subFilter("Applying")
        }
//        sheetBindingUniv!!.reciepentList.adapter =
//            ConsiderFilterAdapter(resources.getStringArray(R.array.consideringFilters), ::onConsideringMainFilterClick)

    }

    private fun countryFilterEvaluation(
        finalArray: ArrayList<ConsiderModel.Data>,
        selectedCountry: String, selectedApplying: String
    ): ArrayList<ConsiderModel.Data> {
        var filteredArray: MutableList<ConsiderModel.Data> = arrayListOf()
        if (selectedCountry == "" || selectedCountry == "All") {
            filteredArray.addAll(finalArray)

        } else {
//            for (i in finalArray) {
//                var checkSame = false
//                if (i.country == selectedCountry) {
//                    checkSame = true
//                    filteredArray.add(i)
//                } else if (i.country == "" && checkSame) {
//                    filteredArray.add(i)
//                } else {
//                    checkSame = false
//                }
//            }
            filteredArray = finalArray.filter { it.country == selectedCountry } as ArrayList<ConsiderModel.Data>
        }
        if (selectedApplying == "" || selectedApplying == "All") {
            filteredArray = filteredArray as ArrayList<ConsiderModel.Data>
        } else {
            filteredArray = filteredArray.filter {
                //  Log.e("APPPlan",  it.selectedAppPlanValue.toString())
                it.selectedAppModeValue == selectedApplying ||
                        it.selectedAppPlanValue == selectedApplying
            } as ArrayList<ConsiderModel.Data>
            var mappedList = filteredArray.groupBy {
                it.country_name
            }
            filteredArray.clear()
            for (i in mappedList){
                for (j in i.value){
                    j.count = i.value.size
               filteredArray.add(j)
                }
            }
//            var count = 1
//            var prev = ""
//
//            for (i in filteredArray) {
//                if (prev == i.country_name) {
//                    count++
//                } else {
//                    prev = i.country_name
//                    count = 1
//                }
//                i.count = count
//            }
        }
        return filteredArray
    }

    private fun onConsideringMainFilterClick(position: Int, imageView: ImageView) {
        if (position == 0) {
            subFilter("Country")
        }
    }

    private fun getConsideringList() {
        SharedHelper(requireContext()).id?.let {
            userid = SharedHelper(requireContext()).id!!
            homeModel.getConsiderList(userid, selectedConsider)
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
                    if (jsonArray.length() > 0) {
                        if (selectedConsider == ACTIVE_CONSIDER) {
                            countryArrayActive.add(
                                KeyVal(
                                    "All",
                                    "All",
                                    false
                                )
                            )
                        } else {
                            countryArrayDeleted.add(
                                KeyVal(
                                    "All",
                                    "All",
                                    false
                                )
                            )

                        }
                    }
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
                                    objectProgram.optString("program_deadline"),
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
                        var requiredRecs: ConsiderModel.RequiredRecommendation? = null
                        val jobj: JSONObject? = object_.optJSONObject("required_recommendation")
                        jobj?.let {
                            requiredRecs = ConsiderModel.RequiredRecommendation(
                                it.optString("teacher_evaluation"),
                                it.optString("max_teacher_evaluation"),
                                it.optString("counselor_recommendation")
                            )
                        }


                        if (!countries.contains(object_.getString("country_name"))) {
                            countries.add(object_.getString("country_name"))
                            if (selectedConsider == ACTIVE_CONSIDER) {
                                countryArrayActive.add(
                                    KeyVal(
                                        object_.getString("country"),
                                        object_.getString("country_name"),
                                        false
                                    )
                                )
                            } else {
                                countryArrayDeleted.add(
                                    KeyVal(
                                        object_.getString("country"),
                                        object_.getString("country_name"),
                                        false
                                    )
                                )
                            }
                        }

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
                            object_.optString("applicaton_round"),
                            null,
                            object_.optString("status")

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
                                    array[k].country_name = countries[j]
                                }
                                finalArray.add(array[k])
                                finalArray[pos].count = count
                            }
                        }
                        pos = finalArray.size
                    }

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
            for (final_index in finalArray.indices) {
                selectedUnivId = finalArray[final_index].university_nid
                val json = JSONObject(it.toString())
                if (json.has(selectedUnivId)) {
                    var jsonUniv = json.optJSONObject(selectedUnivId)
                    var selectedAppModeType: String? = null
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
                                val collTerm: ConsiderModel.CollTerm =
                                    ConsiderModel.CollTerm()

                                collTerm.type = term.optString("type")
                                if (finalArray.get(final_index).applicationMode == currentDynamicKey) {
                                    selectedAppModeType = collTerm.type
                                    finalArray.get(final_index).selectedAppModeValue =
                                        allowedType.getString(currentDynamicKey)
                                }
                                if (collTerm.type == "term") {
                                    var termList = ArrayList<String>()

                                    val termListArray: JSONArray? = term.optJSONArray("term_list")
                                    //collTerm.collTerm = termList

//                                println("term " + term.optString("type"))
                                    //val collTermList: ArrayList<ConsiderModel.CollTerm> = arrayListOf()
                                    val collPlanList: ArrayList<ConsiderModel.CollPlan> =
                                        arrayListOf()
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
                                                for (j in 0 until plan.length()) {
                                                    val json: JSONObject = plan[j] as JSONObject
                                                    val descisionItem = ConsiderModel.Decision(
                                                        json.optString("decision_plan"),
                                                        json.optString("decision_plan_value"),
                                                        json.optString("deadline_date")
                                                    )
                                                    if (finalArray.get(final_index).applicationType?.replaceInvertedComas() == json.optString(
                                                            "decision_plan"
                                                        )?.replaceInvertedComas()
                                                    ) {
                                                        finalArray.get(final_index).selectedAppPlanValue =
                                                            json.optString("decision_plan_value")
                                                    }
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
                                            for (j in 0 until it.length()) {


                                                val plan: ConsiderModel.DecisionPlan =
                                                    ConsiderModel.DecisionPlan(
                                                        it.getJSONObject(j).optString("id"),
                                                        it.getJSONObject(j).optString("label")
                                                    )
                                                if (finalArray.get(final_index).applicationType?.replaceInvertedComas() == it.getJSONObject(
                                                        j
                                                    ).optString("id")?.replaceInvertedComas()
                                                ) {
                                                    finalArray.get(final_index).selectedAppPlanValue =
                                                        it.getJSONObject(j).optString("label")
                                                }
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
                                                if (finalArray.get(final_index).applicationType?.replaceInvertedComas() == id?.replaceInvertedComas()) {
                                                    finalArray.get(final_index).selectedAppPlanValue =
                                                        it.optString(id)
                                                }
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

                            finalArray.get(final_index).collegeAppLicationType =
                                ConsiderModel.CollType(typeList, selectedAppModeType)

                        } else if (jsonUniv.optJSONArray("allowed_application_type") != null) {
                            allowedTypeArray = jsonUniv.optJSONArray("allowed_application_type")
                            for (i in 0 until allowedTypeArray.length()) {
                                val type = allowedTypeArray.getJSONObject(i)
                                var termList = ArrayList<String>()

                                //collTerm.collTerm = termList

                                val collTerm: ConsiderModel.CollTerm =
                                    ConsiderModel.CollTerm()
                                val term: JSONObject? =
                                    jsonUniv.optJSONObject(type.optString("label"))
                                term?.let {
                                    collTerm.type = term?.optString("type")
                                    if (finalArray.get(final_index).applicationMode == type.optString(
                                            "id"
                                        )
                                    ) {
                                        selectedAppModeType = collTerm.type
                                        finalArray.get(final_index).selectedAppModeValue =
                                            type.optString("label")
                                    }
                                    if (collTerm.type == "term") {
                                        val termListArray: JSONArray? =
                                            type?.optJSONArray("term_list")
                                        val collTermList: ArrayList<ConsiderModel.CollTerm> =
                                            arrayListOf()
                                        val collPlanList: ArrayList<ConsiderModel.CollPlan> =
                                            arrayListOf()
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
                                                    for (j in 0 until plan.length()) {
                                                        val json: JSONObject = plan[j] as JSONObject
                                                        val descisionItem = ConsiderModel.Decision(
                                                            json.optString("decision_plan"),
                                                            json.optString("decision_plan_value"),
                                                            json.optString("deadline_date")
                                                        )
                                                        if (finalArray.get(final_index).applicationType?.replaceInvertedComas() == json.optString(
                                                                "decision_plan"
                                                            ).replaceInvertedComas()
                                                        ) {
                                                            finalArray.get(final_index).selectedAppPlanValue =
                                                                json.optString("decision_plan_value")
                                                        }
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
                                    } else {
                                        val decisionPlans: JSONArray = term?.optJSONArray("data")
                                        val arrayPlans = arrayListOf<ConsiderModel.DecisionPlan>()
                                        decisionPlans?.let {
                                            for (j in 0 until it.length()) {


                                                val plan: ConsiderModel.DecisionPlan =
                                                    ConsiderModel.DecisionPlan(
                                                        it.getJSONObject(j).optString("id"),
                                                        it.getJSONObject(j).optString("label")
                                                    )
                                                if (finalArray.get(final_index).applicationType?.replaceInvertedComas() == it.getJSONObject(
                                                        j
                                                    ).optString("id")?.replaceInvertedComas()
                                                ) {
                                                    finalArray.get(final_index).selectedAppPlanValue =
                                                        it.getJSONObject(j).optString("label")
                                                }
                                                arrayPlans.add(plan)
                                            }
                                        }
                                        collTerm.planList = arrayPlans
                                    }
                                }
                                typeList.add(
                                    DynamicKeyValue(
                                        type.optString("id"),
                                        type.optString("label"),
                                        collTerm
                                    )
                                )

                            }

                            finalArray.get(final_index).collegeAppLicationType =
                                ConsiderModel.CollType(typeList, selectedAppModeType)

                        }
                    }
                }
            }
            if (selectedConsider == ACTIVE_CONSIDER) {
                activeArray = finalArray
               // considerAdapter = ConsiderAdapter(this, activeArray, ::notesClick)
              //  mBinding.consideringList.adapter = considerAdapter
                mBinding.universitisCounte.text = activeArray.size.toString() + " Universities"
                activeConsideringWork()
            } else {
                deletedArray = finalArray
                considerAdapter = ConsiderAdapter(this, deletedArray, ::notesClick)
                mBinding.consideringList.adapter = considerAdapter
                mBinding.universitisCounte.text = deletedArray.size.toString() + " Universities"
            }

        }
        homeModel.showError.observe(requireActivity()) {
            dialogP.dismiss()
        }

        homeModel.getApplyingWithObserver.observe(requireActivity()) {
            dialogP.dismiss()
            dialogP.dismiss()
            val jsondisciplineAcivities: JSONObject? =
                JSONObject(it.toString())
            applyingWithList.clear()
            jsondisciplineAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    applyingWithList.add(KeyVal(key, it.getString(key), false))
                }
            }
            applyingWithList.add(KeyVal("All", "All", false))
            for (i in resources.getStringArray(R.array.static_applying_with).indices) {
                applyingWithList.add(
                    KeyVal(
                        "AppType",
                        resources.getStringArray(R.array.static_applying_with)[i],
                        false
                    )
                )
            }
            applyingWithList.sortBy { it.value }
        }
        mBinding.selected.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mBinding.selected.setText("Cancel Selection")
                considerAdapter.selectionVisibility(true)
                mBinding.selectAll.visibility = View.VISIBLE
                mBinding.btnLay.visibility = View.VISIBLE
            } else {
                mBinding.selected.setText("Select")
                considerAdapter.selectionVisibility(false)
                mBinding.selectAll.visibility = View.GONE
                mBinding.btnLay.visibility = View.GONE
            }
        }
        mBinding.selectAll.setOnClickListener {
            mBinding.btnLay.visibility = View.VISIBLE
            if (mBinding.selectAll.isChecked){
                for (i in filteredArray){
                    i.selected = true
                }
             } else {
                for (i in filteredArray){
                    i.selected = false
                }
            }

            considerAdapter.notifyDataSetChanged()
            considerAdapter.selectionVisibility(true)
        }
        mBinding.deleteBtn.setOnClickListener {
            bulkMoveList.clear()
            if (selectedConsider== ACTIVE_CONSIDER) {
                for (i in filteredArray.indices) {
                    if (filteredArray.get(i).selected) {
                        bulkMoveList.add("" + filteredArray.get(i).transcriptNid)
                    }
                }
            }
                if (bulkMoveList != null && bulkMoveList.size > 0) {
                    confirmSelectedDeletePopup(
                        true,
                        resources.getString(R.string.delete_considering)
                    )
                }

        }
        mBinding.transcriptBtn.setOnClickListener {
            if (selectedConsider== ACTIVE_CONSIDER) {
                bulkMoveList.clear()
                for (i in filteredArray.indices) {
                    if (filteredArray.get(i).selected) {
                        bulkMoveList.add("" + filteredArray.get(i).transcriptNid)
                    }
                }
            }
            if (bulkMoveList != null && bulkMoveList.size > 0) {
                confirmSelectedDeletePopup(
                    false,
                    resources.getString(R.string.to_applying_consider)
                )
            }
        }
        homeModel.bulkCollegeMoveObserver.observe(requireActivity()) {
            dialogP.dismiss()
            resetSelection()
            getConsideringList()
        }
    }

    private fun confirmSelectedDeletePopup(delete: Boolean, msg: String) {
        AlertDialog.Builder(requireContext())
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage(msg)
            .setPositiveButton(
                "Confirm"
            ) { dialog, _ ->
                var payload = BulkCollegeMovePayload()
                payload.student_uid = SharedHelper(requireContext()).id.toString()
                if (delete) {
                    payload.status = "considered"
                } else {
                    payload.status = "applying"
                }
                payload.transcript_nids = bulkMoveList
                dialogP.show()
                homeModel.bulkCollegeMove(payload)
            }
            .setNegativeButton("Cancel", null)
            .show()
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
                    val array = arrayListOf<DynamicKeyValue>()
                    array.clear()
                    array.addAll(it)
                    val term = ConsiderModel.CollTerm()
                    if (checkNonNull(finalArray[arratlistPosition].applicationMode))
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
                            val array = arrayListOf<String>()
                            array.clear()
                            array.addAll(it)
                            if (checkNonNull(finalArray[arratlistPosition].applicationTerm))
                                array!!.add("Reset")
                            recyclerView.adapter = ConsideringTermAdapter(
                                array, type, this
                            )
                            return
                        }
                    } else {
                        val array = arrayListOf<String>()
                        array.clear()
                        array.addAll(resources.getStringArray(R.array.APPLICATION_TERM))
                        if (checkNonNull(finalArray[arratlistPosition].applicationTerm))
                            array!!.add("Reset")
                        recyclerView.adapter = ConsideringTermAdapter(
                            array,
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
                                if (checkNonNull(finalArray[arratlistPosition].applicationType))
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
                            if (checkNonNull(finalArray[arratlistPosition].applicationType))
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
            (mBinding.consideringList.adapter as ConsiderAdapter).setValue(
                radioButton.text.toString(),
                type
            )
        }
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
        if (finalArray.get(postion).status.equals("Considered")) {
            confirmConsiderPopup(postion)
        } else {
            SharedHelper(requireContext()).id?.let {
                dialogP.show()
                homeModel.moveToApplying(
                    it,
                    finalArray.get(postion).university_nid,
                    "1"
                )
            }
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

    private fun confirmConsiderPopup(postion: Int) {
        AlertDialog.Builder(requireContext())
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("Do you want to move this university to considering list?")
            .setPositiveButton(
                "Confirm"
            ) { dialog, _ ->
                dialogP.show()

                SharedHelper(requireContext()).id?.let {
                    dialogP.show()
                    homeModel.moveToApplying(
                        it,
                        finalArray.get(postion).university_nid,
                        "0"
                    )
                }

            }
            .setNegativeButton("Cancel", null)
            .show()
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
            updateStudentPlan.request_transcript =
                finalArray.get(typeTermPosition).requestTranscript
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
        val isAppByProgram =
            (finalArray[postion].appByProgramSupported == "1" && finalArray[postion].applicationMode != "3")
        val canShowProgramWithDeadline = isAppByProgram

        var addedPrograms: ArrayList<AddProgramConsider.Programs?>? = ArrayList()
        for (i in finalArray[postion].program_data?.indices!!) {
            var programData: AddProgramConsider.Programs = AddProgramConsider.Programs()
            programData.program_name =
                finalArray[postion].program_data?.get(i)?.program_name.toString()
            programData.program_deadline =
                finalArray[postion].program_data?.get(i)?.program_deadline.toString()
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
                dialog.dismiss()
                getConsideringList()
            }
        }

    }

    override fun onTranscriptRequest(postion: Int, chcked: String) {
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
            mBinding.consideringList.adapter?.notifyDataSetChanged()
        }
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
                mBinding.consideringList.adapter?.notifyDataSetChanged()

            } else if (type == 0) {
                if (dynamicKeyValue.key == "Reset") {
                    finalArray.get(typeTermPosition).applicationTerm = null
                    finalArray.get(typeTermPosition).applicationType = null
                    finalArray.get(typeTermPosition).dueDate = null
                } else
                    finalArray.get(typeTermPosition).applicationTerm = dynamicKeyValue.value
                mBinding.consideringList.adapter?.notifyDataSetChanged()

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
            mBinding.consideringList.adapter?.notifyDataSetChanged()
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
            if (!dynamicKeyValue.equals("Reset")) {
                finalArray.get(typeTermPosition).applicationType = dynamicKey
            } else {
                finalArray.get(typeTermPosition).applicationType = null
                finalArray.get(typeTermPosition).dueDate = null
            }
            mBinding.consideringList.adapter?.notifyDataSetChanged()
        }
    }

    private fun subFilter(type: String) {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: UniversityFilterBinding =
            UniversityFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.search.visibility = View.GONE
        sheetBinding.filters.setText(type)
        dialog.show()

        sheetBinding.spinnerLay.visibility = View.GONE
        sheetBinding.invisibleLay.visibility = View.GONE
        sheetBinding.backBtn.setOnClickListener {
            dialog.dismiss()
        }
        sheetBinding.clearText.setText(resources.getString(R.string.done))
        sheetBinding.spinnerLay.visibility = View.GONE
        sheetBinding.clearText.setOnClickListener {
//            selectedCountryActive = ""
//            selectedCountryDeleted = ""
//            selectedApplyingDeleted = ""
//            selectedApplyingActive = ""
            when (selectedConsider) {

                ACTIVE_CONSIDER -> {
                    activeSelection()
                    if (type  == "Country") {
                    for (i in countryArrayActive) {
                        if (i.checked) {
                            selectedCountryActive = i.key
                            break
                        } else {
                            selectedCountryActive = ""
                        }
                    }} else {
                        for (i in applyingWithList) {
                            if (i.checked) {
                                selectedApplyingActive = i.value
                                break
                            } else {
                                selectedApplyingActive = ""
                            }
                        }
                    }
                }
                DELETED -> {
                   resetSelection()
                    if (type  == "Country") {
                    for (i in countryArrayDeleted) {
                        if (i.checked) {
                            selectedCountryDeleted = i.key
                            break
                        } else {
                            selectedCountryDeleted = ""
                        }
                    }} else {
                        for (i in applyingWithList) {
                            if (i.checked) {
                                selectedApplyingDeleted = i.value
                                break
                            } else {
                                selectedApplyingDeleted = ""
                            }
                        }
                    }
                }
            }

            if (selectedConsider == ACTIVE_CONSIDER) {
               activeConsideringWork()

            } else {
                deletedConsideringWork()
            }
            dialog.dismiss()
            mainDialog?.dismiss()
        }
        sheetBinding.close.setOnClickListener {
            sheetBinding.searchText.setText("")
        }
        Log.e("Country"," "+ selectedCountryActive)
        Log.e("Applying"," "+ selectedApplyingActive)

        if (type.equals("Country")) {
            when (selectedConsider) {
                ACTIVE_CONSIDER -> {
                    for (i in countryArrayActive) {
                        i.checked = selectedCountryActive == i.key
                    }
                    sheetBinding.reciepentList.adapter =
                        ConsiderCountryFilterAdapter(countryArrayActive)
                }
                DELETED -> {
                    for (i in countryArrayDeleted) {
                        i.checked = selectedCountryDeleted == i.key
                    }
                    sheetBinding.reciepentList.adapter =
                        ConsiderCountryFilterAdapter(countryArrayDeleted)
                }
            }
        } else if (type.equals("Applying")) {
            when (selectedConsider) {
                ACTIVE_CONSIDER -> {
                    for (i in applyingWithList) {
                        i.checked = selectedApplyingActive == i.value
                    }
                }
                DELETED -> {
                    for (i in applyingWithList) {
                        i.checked = selectedApplyingDeleted == i.value
                    }
                }
            }

            sheetBinding.reciepentList.adapter =
                ConsiderCountryFilterAdapter(applyingWithList)
        }

    }

    private fun activeSelection() {
        mBinding.selectAll.visibility = View.GONE
        mBinding.selected.visibility = View.VISIBLE
        mBinding.selectAll.isChecked = false
        mBinding.selected.isChecked = false
        mBinding.btnLay.visibility = View.GONE
    }

    private fun activeConsideringWork() {
        activeSelection()
        filteredArray = countryFilterEvaluation(
            activeArray,
            selectedCountryActive,
            selectedApplyingActive
        )
        considerAdapter = ConsiderAdapter(this, filteredArray, ::notesClick)
//        mBinding.consideringList.adapter =
//            ConsiderAdapter(
//                this,
//                filteredArray,
//                ::notesClick
//            )
        mBinding.consideringList.adapter = considerAdapter
        mBinding.universitisCounte.text = filteredArray.size.toString() + " Universities"
    }

    private fun deletedConsideringWork() {
        resetSelection()
        if (deletedArray.size > 0) {
            filteredArray = countryFilterEvaluation(
                deletedArray,
                selectedCountryDeleted,
                selectedApplyingDeleted
            )
            mBinding.consideringList.adapter =
                ConsiderAdapter(
                    this,
                    filteredArray,
                    ::notesClick
                )
            mBinding.universitisCounte.text = filteredArray.size.toString() + " Universities"
        } else {
            getConsideringList()
        }
    }

    private fun resetSelection() {
        mBinding.selectAll.visibility = View.GONE
        mBinding.selected.visibility = View.GONE
        mBinding.btnLay.visibility = View.GONE
        mBinding.selectAll.isChecked = false
        mBinding.selected.isChecked = false
    }

    companion object {
        const val ACTIVE_CONSIDER = "considering"
        const val DELETED = "considered"
    }

}

interface ClickOptionFilters {
    fun onItemClick(positiion: Int, type: Int, dynamicKeyValue: DynamicKeyValue)
    fun onTermItemClick(positiion: Int, type: Int, dynamicKeyValue: String)
    fun onPlanOptionClick(positiion: Int, type: Int, dynamicKey: String, dynamicKeyValue: String)
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
    fun onDeadlineClick(postion: Int, deadline: String)
}
