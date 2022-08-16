package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.AdmisionLayoutBinding
import com.maialearning.model.CollegeFactSheetModel
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.AdmissionAdapter
import com.maialearning.ui.model.CommunityModel

class AdmissionFragment : Fragment() {
    private lateinit var mBinding: AdmisionLayoutBinding
    var model: CollegeFactSheetModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = AdmisionLayoutBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        mBinding.listView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


    }

    private fun initData() {

        mBinding.listView.adapter = AdmissionAdapter(requireContext())
        model = (context as UniversitiesActivity).getData()
        if (model != null) {
            mBinding.add2.text = model?.admissions?.applicationFee
            if (model?.admissions?.appType != null) {
                mBinding.addm2.text = model?.admissions?.appType
            } else {
                mBinding.addm2.text = ""
            }
            mBinding.addim2.text =
                model?.admissions?.applicationDeadlineFinal
            mBinding.addi2.text = model?.admissions?.admissionInterview
            mBinding.sat1.text = model?.admissions?.satAct
            mBinding.accept2.text = model?.admissions?.acceptance + "%"
            mBinding.statics1.text = model?.admissions?.satCr?.per25.toString()
            mBinding.statics2.text = model?.admissions?.satCr?.per.toString()
            mBinding.math1.text = model?.admissions?.satMath?.per25.toString()
            mBinding.math2.text = model?.admissions?.satMath?.per.toString()
            mBinding.act1.text = model?.admissions?.actComp?.per25.toString()
            mBinding.act2.text = model?.admissions?.actComp?.per.toString()
            mBinding.text.text = model?.admissions?.gpa
            mBinding.contact.text =
                model?.admissions?.contact?.admofficer + "\n" + model?.admissions?.contact?.admtitle + "\nP: " + model?.admissions?.contact?.admPhone + "\nP: " + model?.admissions?.contact?.admFax
            mBinding.codes.text =
                "SAT Number: " + model?.admissions?.sat + "\nACT Number: " + model?.admissions?.act + "\nFAFSA Number: " + model?.admissions?.fafsa + "\nFICE: " + model?.admissions?.fice

        }


    }
}