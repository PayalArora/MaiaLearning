package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.AdmisionLayoutBinding
import com.maialearning.ui.adapter.AdmissionAdapter
import com.maialearning.ui.model.CommunityModel

class AdmissionFragment : Fragment() {
    private lateinit var mBinding: AdmisionLayoutBinding
    var listData=ArrayList<CommunityModel>()
    var listDataProgress=ArrayList<CommunityModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding= AdmisionLayoutBinding.inflate(inflater,container,false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        mBinding.listView.layoutManager=
            LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)


    }
    private fun initData(){
        mBinding.listView.adapter = AdmissionAdapter(requireContext())

    }
}