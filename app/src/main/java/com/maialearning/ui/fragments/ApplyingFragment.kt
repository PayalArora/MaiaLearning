package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maialearning.databinding.ConsideringLayoutBinding
import com.maialearning.databinding.FragmentApplyingBinding
import com.maialearning.ui.adapter.ApplyingAdapter
import com.maialearning.ui.adapter.ConsiderAdapter

class ApplyingFragment : Fragment(), OnItemClickOption {
    var selectedValue = ""
    private lateinit var mBinding: FragmentApplyingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentApplyingBinding.inflate(inflater, container, false)
        return mBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        mBinding.applyingList.adapter = ApplyingAdapter(this)
    }

    override fun onTypeClick() {

    }

    override fun onTermClick() {
    }

    override fun onPlanClick() {
    }

}