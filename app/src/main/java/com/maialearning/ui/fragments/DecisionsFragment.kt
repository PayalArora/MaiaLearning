package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnItemClickType
import com.maialearning.databinding.FragmentDashboardBinding
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.databinding.RadiobuttonFilterBinding
import com.maialearning.databinding.UniversityFilterBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.ui.activity.ClickFilters
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

    override fun onDecisionClick() {
        showDialog(requireContext(), layoutInflater)
    }

    fun showDialog(con: Context, layoutInflater: LayoutInflater) {
        val dialog = BottomSheetDialog(con)
        val sheetBinding: RadiobuttonFilterBinding =
            RadiobuttonFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.filters.setText(con.resources.getString(R.string.select_decision1))
        sheetBinding.close.setOnClickListener { dialog.dismiss() }
        sheetBinding.rvRadioGroup.adapter =
            RadiobuttonFilterAdapter(con.resources.getStringArray(R.array.decision_filter))
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
                        object_.getString("application_mode")
                    )
                    array.add(model)
                    array.sortBy { it.naviance_college_name }
                }

                var pos = 0
//                countries.sortBy { it }
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