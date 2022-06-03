package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.CommunityLayoutBinding
import com.maialearning.databinding.CostFragmentBinding
import com.maialearning.ui.adapter.CommunityAdapter
import com.maialearning.ui.adapter.CostAdapter
import com.maialearning.ui.model.CommunityModel

class CostFragment: Fragment() {
    private lateinit var mBinding: CostFragmentBinding
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
        mBinding= CostFragmentBinding.inflate(inflater,container,false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        mBinding.recyclerList.layoutManager=
            GridLayoutManager(requireContext(),2, LinearLayoutManager.VERTICAL,false)
        mBinding.credictList.layoutManager=
            GridLayoutManager(requireContext(),2, LinearLayoutManager.VERTICAL,false)

    }
    private fun initData(){
        val communityModel= CommunityModel("Transfer applicants \n accepted","49%","","#F89A1F","")
        listData.add(communityModel)
        val communityModel1= CommunityModel("Transfer applicants \n accepted","$18,735","","#55AC68","")
        listData.add(communityModel1)
        mBinding.recyclerList.adapter = CostAdapter(listData)
        val communityModel2= CommunityModel("Maximum\n Transferred","150","semester\n hours","#5E4496","")
        listDataProgress.add(communityModel2)
        val communityModela2= CommunityModel("Minimum Transferors \n Must Complete","60","semester\n hours","#BF3E8C","")
        listDataProgress.add(communityModela2)
        mBinding.credictList.adapter = CostAdapter(listDataProgress)
    }
}