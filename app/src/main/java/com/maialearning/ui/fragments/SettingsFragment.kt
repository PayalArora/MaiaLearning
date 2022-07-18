package com.maialearning.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ProfileViewpagerBinding
import com.maialearning.databinding.SettingsViewpagerBinding
import com.maialearning.ui.adapter.ConnectionAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.prefhandler.SharedPreference
import com.maialearning.viewmodel.ProfileViewModel

class SettingsFragment(val viewModel: ProfileViewModel) : Fragment(), OnItemClick {
    private lateinit var mBinding: SettingsViewpagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = SettingsViewpagerBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var phNo = "0"
        var countryCode = "US"
        var nid = ""
        mBinding.smsBtn.setOnCheckedChangeListener { compoundButton, b ->
            var check = "0"
            if (b) {
                check = "1"
            } else {
                check = "0"
            }
            SharedHelper(requireContext()).authkey?.let { it1 ->
                viewModel.updateSMSSetting("Bearer " + it1, nid, phNo, countryCode, check)

            }
        }
        viewModel.observer.observe(requireActivity(), {
            phNo = it.info!!.primaryPhone!!
            countryCode = it.info!!.countryCode!!
            nid = it.info!!.nid!!
            if (it.info?.receiveSms.equals("1")) {
                mBinding.smsBtn.isChecked = true
            } else {
                mBinding.smsBtn.isChecked = false
            }
            if (it.info?.receiveSmsFromOther.equals("1")) {
                mBinding.receiveSmsBtnOther.isChecked = true
            } else {
                mBinding.receiveSmsBtnOther.isChecked = false
            }
            if (it.info?.fieldEmailReceive == 1) {
                mBinding.receiveEmailBtn.isChecked = true
            } else {
                mBinding.receiveEmailBtn.isChecked = false
            }
        })


        viewModel.smsObserver.observe(requireActivity(), {
            SharedHelper(requireContext()).authkey?.let {
                SharedHelper(requireContext()).id?.let { it1 ->
                    viewModel.getProfile("Bearer " + it, it1)
                }
            }
        })

    }

    override fun onClick(positiion: Int) {

    }


}