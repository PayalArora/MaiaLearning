package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.CostFragmentBinding
import com.maialearning.databinding.TransferFragmentBinding
import com.maialearning.ui.adapter.CommunityAdapter
import com.maialearning.ui.adapter.CostAdapter
import com.maialearning.ui.model.CommunityModel

class CostFragment : Fragment() {
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
        val communityModel= CommunityModel("Tuition & Fees \n(In-State)","$14,184","","#24418E","#1A24418E")
        listData.add(communityModel)
        val communityModel1= CommunityModel("Tuition & Fees \n(In-State)","$43,176","","#804053","#1A804053")
        listData.add(communityModel1)
        val communityModel2= CommunityModel("Room\n& Board","$18,222","","#54575F","#1A54575F")
        listData.add(communityModel2)
        val communityModela3= CommunityModel("Additional \nFees","$4,811","","#C1345F","#1AC1345F")
        listData.add(communityModela3)
        val communityModela4= CommunityModel("Books & \nSupplies","$849","","#C17034","#1AC17034")
        listData.add(communityModela4)
        val communityModela5= CommunityModel("Total \n(In-State)","$38,066","","#5E4496","#1A5E4496")
        listData.add(communityModela5)
        val communityModela6= CommunityModel("Total \n(Out-State)","$67,058","","#348FC1","#1A348FC1")
        listData.add(communityModela6)
        mBinding.recyclerList.adapter = CommunityAdapter(listData)

        val communityModela_1= CommunityModel("Percentage of students' needs were fully met ","49%","","#F89A1F","#1AF89A1F")
        listDataProgress.add(communityModela_1)
        val communityModela_2= CommunityModel("Average financial aid award  ","$18,735","","#55AC68","#1A55AC68")
        listDataProgress.add(communityModela_2)
        val communityModela_3= CommunityModel("Students receiving Pell Grant ","19%","","#0A50BA","#1A0A50BA")
        listDataProgress.add(communityModela_3)
        val communityModela_4= CommunityModel("Students with institutional grant or scholarship","48%","","#5E4496","#1A5E4496")
        listDataProgress.add(communityModela_4)
        val communityModela_5= CommunityModel("Students receiving Federal student loans","20%","","#F89A1F","#1AF89A1F")
        listDataProgress.add(communityModela_5)
        mBinding.credictList.adapter = CommunityAdapter(listDataProgress)
    }
}