package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.databinding.LayoutProgramsBinding
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.databinding.RadiobuttonFilterBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.model.UpdateStudentPlan
import com.maialearning.parser.ApplyingParser
import com.maialearning.ui.adapter.*
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.HomeViewModel
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DecisionsFragment : Fragment(), DecisionClick {
    private lateinit var mBinding: LayoutRecyclerviewBinding
    lateinit var userid: String
    private lateinit var dialogP: Dialog
    private val homeModel: HomeViewModel by viewModel()
    var finalArray: ArrayList<ConsiderModel.Data> = ArrayList()
    var statusArray: Array<String> = arrayOf()
    var statuses = ArrayList<String>()
    var statuskey = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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
        homeModel.getDecisionStatuses()
        initObserver()
    }

    private fun setListeners() {

    }

    override fun onDecisionClick(position: Int) {
        showDialog(requireContext(), layoutInflater, position)
    }

    override fun onProgramDecisionClick(position: Int) {
        // showDialog(requireContext(), layoutInflater, position)
        bottomSheetDecisionProgram(position)
    }

    fun showDialog(con: Context, layoutInflater: LayoutInflater, pos: Int) {
        val dialog = BottomSheetDialog(con)
        val sheetBinding: RadiobuttonFilterBinding =
            RadiobuttonFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        if (statuses.size == 0) {
            dialogP.show()
            homeModel.getDecisionStatuses()
        }
        homeModel.decisionStatusObserver.observe(requireActivity()) {
            if (statuses.size == 0) {
                dialogP.dismiss()
            }
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
                RadiobuttonFilterAdapter(statusArray, finalArray[pos].applicationStatusName)
        }
        sheetBinding.close.setOnClickListener { dialog.dismiss() }
        sheetBinding.saveBtn.setOnClickListener {

            var updateStudentPlan = UpdateStudentPlan()
            updateStudentPlan.student_uid = SharedHelper(requireContext()).id.toString()
            updateStudentPlan.college_nid = finalArray[pos].university_nid
            val selectedpos =
                (sheetBinding.rvRadioGroup.adapter as RadiobuttonFilterAdapter).onSave()
            updateStudentPlan.app_status = statuskey[selectedpos]

            dialogP.show()
            homeModel.updateStudentPlan(updateStudentPlan)
            homeModel.updateStudentPlanObserver.observe(requireActivity()) {
                finalArray = ApplyingParser(it, userid).parseJson()
                setAdapter()
                dialog.dismiss()
                dialogP.dismiss()
            }
        }

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
                finalArray = ApplyingParser(it, userid).parseJson()
                setAdapter()
            }
        }
        homeModel.showError.observe(requireActivity()) {
            dialogP.dismiss()
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun bottomSheetDecisionProgram(postion: Int) {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: LayoutProgramsBinding = LayoutProgramsBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.addProgram.setText("Application Status")
        sheetBinding.addMore.visibility = View.GONE
        Log.e("Size"," "+finalArray[postion].program_data?.size)
        sheetBinding.addMoreLayout.adapter =
            DecisonProgramStatusAdapter(finalArray[postion].program_data,this)


        sheetBinding.save.setOnClickListener {

        }

    }
}