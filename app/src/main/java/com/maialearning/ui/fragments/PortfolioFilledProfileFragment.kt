package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.FilledProfilePortfolioBinding
import com.maialearning.databinding.PortfolioProfileFragmentBinding
import com.maialearning.ui.adapter.PortfolioExperienceAdapter

class PortfolioFilledProfileFragment : Fragment() {
    private lateinit var mBinding: FilledProfilePortfolioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FilledProfilePortfolioBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.experienceList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.experienceList.adapter = PortfolioExperienceAdapter()
    }
}

