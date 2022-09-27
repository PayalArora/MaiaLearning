package com.maialearning.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maialearning.databinding.NysCareerLayoutBinding
import com.maialearning.model.CareerTopPickResponseItem
import com.maialearning.model.NYSCareerResponse
import com.maialearning.model.SkillsItem
import com.maialearning.model.StudentCareerReviewItem
import com.maialearning.ui.adapter.*
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.prefhandler.SharedPreference
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.CareerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NysCareerPlan : Fragment() {
    private val careerViewModel: CareerViewModel by viewModel()
    private lateinit var progress: Dialog

    private lateinit var mBinding: NysCareerLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = NysCareerLayoutBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        mBinding.knowList.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        mBinding.knowList.adapter = KnowListAdapter(requireContext(), 2)
//        mBinding.careerList.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        mBinding.careerList.adapter = KnowListAdapter(requireContext(), 1)
//        mBinding.skillList.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        mBinding.skillList.adapter = KnowSkillAdapter()
//        mBinding.actionList.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        mBinding.actionList.adapter = SkillActionAdapter()
//        progress.show()
        progress = showLoadingDialog(requireContext())

        SharedHelper(requireContext()).id?.let { careerViewModel.getNYSCareerPlan(it) }
        initObserver()

    }

    private fun initObserver() {
        var gson: Gson = GsonBuilder().create()
        careerViewModel.showError.observe(requireActivity()) {
            progress.dismiss()
        }
        careerViewModel.nysCareerObserver.observe(requireActivity()) {
//            progress.dismiss()
            val itModel = gson.fromJson(it, NYSCareerResponse::class.java)
            Log.e("DATA", "" + itModel.studentFirstName)
            dataset(itModel)
        }

    }

    private fun dataset(itModel: NYSCareerResponse?) {
        mBinding.name.text = itModel?.studentFirstName
        mBinding.studentIdNo.text = itModel?.studentId
        mBinding.schoolName.text = itModel?.school

        mBinding.careerList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        mBinding.careerList.adapter = NYSCareerAdapter(
            requireContext(),
            itModel?.studentCareerReview as ArrayList<StudentCareerReviewItem>
        )

        mBinding.skillsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        mBinding.skillsList.adapter = NYSSkillsAdapter(
            requireContext(),
            itModel.skills as ArrayList<SkillsItem>
        )
    }
}
