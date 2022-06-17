package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.PlanFragmentBinding
import com.maialearning.ui.adapter.CareerAdapter
import com.maialearning.ui.adapter.KnowledgeActionAdapter
import com.maialearning.ui.adapter.KnowledgeAttrAdapter

class NysCareerPlan : Fragment() {
    private lateinit var mBinding: PlanFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding= PlanFragmentBinding.inflate(inflater,container,false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.careersList.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        mBinding.careersList.adapter = CareerAdapter()
        mBinding.knowList.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        mBinding.careersList.adapter = KnowledgeAttrAdapter(requireContext())
        mBinding.actionList.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mBinding.careersList.adapter = KnowledgeActionAdapter(requireContext())

    }
}
