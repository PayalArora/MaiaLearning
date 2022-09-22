package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.databinding.SummaryTraficBinding
import com.maialearning.model.*
import com.maialearning.ui.adapter.*

class SummaryFragment(var response: SelectedCareerResponse) : Fragment() {
    var dialog: BottomSheetDialog? = null
    private lateinit var mBinding: SummaryTraficBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = SummaryTraficBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val array = ArrayList<String?>()
        array.addAll(response.responsibilities!!)
        mBinding.progressView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.progressView.adapter = TraficSubAdapter(array)
        val arrayAct = ArrayList<WorkActivitiesItem?>()
        mBinding.actView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.actView.adapter = WorkActivityAdapter(arrayAct, null, null, null,"work")
        val acdeAct = ArrayList<AcademicKnowledgeItem?>()
        acdeAct.addAll(response.academicKnowledge!!)
        mBinding.acdeView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.acdeView.adapter = WorkActivityAdapter(null, acdeAct, null, null,"aca")
        val skillAct = ArrayList<SkillItem?>()
        skillAct.addAll(response.skill!!)
        mBinding.skillsView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.skillsView.adapter = WorkActivityAdapter(null, null, skillAct, null,"skill")
        val interAct = ArrayList<InterestItem?>()
        interAct.addAll(response.interest!!)
        mBinding.interView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.interView.adapter = WorkActivityAdapter(null, null, null,interAct, "inter")

    }

}
