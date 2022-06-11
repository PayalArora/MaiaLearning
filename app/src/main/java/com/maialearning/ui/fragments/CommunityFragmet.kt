package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.R
import com.maialearning.databinding.CommunityLayoutBinding
import com.maialearning.ui.adapter.CommunityAdapter
import com.maialearning.ui.adapter.ProgramAdapter
import com.maialearning.ui.adapter.ProgressAdapter
import com.maialearning.ui.model.CommunityModel

class CommunityFragmet : Fragment() {
    private lateinit var mBinding: CommunityLayoutBinding
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
        mBinding= CommunityLayoutBinding.inflate(inflater,container,false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       initData()
        mBinding.listComunity.layoutManager=
            GridLayoutManager(requireContext(),2, LinearLayoutManager.VERTICAL,false)
        mBinding.progressView.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)


    }
    private fun initData(){
        val communityModel= CommunityModel("Undergraduate","73%","30,853 \n Students","#F89A1F","#F89A1F")
        listData.add(communityModel)
        val communityModel1= CommunityModel("Graduate","27%","30,853 \n Students","#55AC68","#55AC68")
        listData.add(communityModel1)
        val communityModel2= CommunityModel("Full-Time","93%","30,853 \n Students","#0A50BA","#0A50BA")
        listData.add(communityModel2)
        val communityModel3= CommunityModel("Part-Time","7%","30,853 \n Students","#5E4496","#5E4496")
        listData.add(communityModel3)
        val communityModel4= CommunityModel("Female","51%","","#C1345F","#C1345F")
        listData.add(communityModel4)
        val communityModel5= CommunityModel("Male","49%","","#348FC1","#0A50BA")
        listData.add(communityModel5)
        val communityModel6= CommunityModel("Total Students","42,501","","#54575F","#54575F")
        listData.add(communityModel6)
        val communityModel7= CommunityModel("In-state","72%","","#24418E","#24418E")
        listData.add(communityModel7)
        val communityModel8= CommunityModel("Out-of-State","51%","","#804053","#804053")
        listData.add(communityModel8)
        val communityModel9= CommunityModel("International","14%","","#C17034","#C17034")
        listData.add(communityModel9)
        mBinding.listComunity.adapter = CommunityAdapter(listData)
        val communityModela= CommunityModel("International Students","73%","#80C17034","","")
        listDataProgress.add(communityModela)
        val communityModela1= CommunityModel("India Alaska Native","27%","#80348FC1","","")
        listDataProgress.add(communityModela1)
        val communityModela2= CommunityModel("Hispanic Latino","43%","#80804053","","")
        listDataProgress.add(communityModela2)
        val communityModela3= CommunityModel("Race Ethnicity Unkown","35%","#80BF3E8C","","")
        listDataProgress.add(communityModela3)
        val communityModela4= CommunityModel("Native Hawaiian","7%","#80349460","","")
        listDataProgress.add(communityModela4)
        val communityModela5= CommunityModel("Black African American","20%","#80270018","","")
        listDataProgress.add(communityModela5)
        val communityModela6= CommunityModel("Asian","89%","#8024418E","","")
        listDataProgress.add(communityModela6)
        val communityModela7= CommunityModel("White","72%","#54575F","","")
        listDataProgress.add(communityModela7)
        val communityModela8= CommunityModel("Two or more Races","30%","#54575F","","")
        listDataProgress.add(communityModela8)
        mBinding.progressView.adapter = ProgressAdapter(requireContext(),listDataProgress)
    }
}