package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.NysCareerLayoutBinding
import com.maialearning.ui.adapter.*

class NysCareerPlan : Fragment() {
    private lateinit var mBinding: NysCareerLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding= NysCareerLayoutBinding.inflate(inflater,container,false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.knowList.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mBinding.knowList.adapter = KnowListAdapter(requireContext(),2)
        mBinding.careerList.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mBinding.careerList.adapter = KnowListAdapter(requireContext(),1)
        mBinding.skillList.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mBinding.skillList.adapter = KnowSkillAdapter()
        mBinding.actionList.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mBinding.actionList.adapter = SkillActionAdapter()


    }
}
