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
import com.maialearning.model.CollegeFactSheetModel
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.CommunityAdapter
import com.maialearning.ui.adapter.ProgramAdapter
import com.maialearning.ui.adapter.ProgressAdapter
import com.maialearning.ui.model.CommunityModel
import com.maialearning.util.parseNA
import com.maialearning.util.parsePercentNA

class CommunityFragmet : Fragment() {
    private lateinit var mBinding: CommunityLayoutBinding
    var listData = ArrayList<CommunityModel>()
    var listDataProgress = ArrayList<CommunityModel>()
    var model: CollegeFactSheetModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = CommunityLayoutBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.listComunity.layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        mBinding.progressView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        init()

    }

    private fun init() {
        model = (context as UniversitiesActivity).getData()
            mBinding.house.text= parseNA(model?.campusCommunity?.housing)
            mBinding.religTxt.text=parseNA(model?.campusCommunity?.religious)
            mBinding.govern.text=parseNA(model?.campusCommunity?.studentGovernment)
            mBinding.yes1.text=parseNA(model?.campusCommunity?.studentNewspaper)
            mBinding.yes2.text=parseNA(model?.campusCommunity?.literaryMagazine)
            mBinding.yes3.text=parseNA(model?.campusCommunity?.yearbook)
            mBinding.minorityTxt.text=parseNA(model?.campusCommunity?.minorityOrganizations)
            mBinding.interTxt.text=parseNA(model?.campusCommunity?.internationalOrganizations)
            mBinding.religousTxt.text=parseNA(model?.campusCommunity?.campusOrganizations)
            mBinding.orgTxt.text=parseNA(model?.campusCommunity?.otherOrganizations)
            mBinding.learningTxt.text=parseNA(model?.campusCommunity?.specialOpportunities.toString())
        
        initData()
    }

    private fun initData() {
        val enroll :CollegeFactSheetModel.CampusCommunity.UndergraduateEnroll?=model?.campusCommunity?.undergraduateEnroll
        val genroll :CollegeFactSheetModel.CampusCommunity.GraduateEnroll?=model?.campusCommunity?.graduateEnroll
        val fenroll :CollegeFactSheetModel.CampusCommunity.FullTime?=model?.campusCommunity?.fullTime
        val penroll :CollegeFactSheetModel.CampusCommunity.PartTime?=model?.campusCommunity?.partTime
        val communityModel =
            CommunityModel("Undergraduate", parsePercentNA(enroll?.percent.toString()) , parseNA(enroll?.value)+" \n Students", "#F89A1F", "#F89A1F")
        listData.add(communityModel)
        val communityModel1 =
            CommunityModel("Graduate", parsePercentNA(genroll?.percent.toString()), parseNA(genroll?.value)+" \n Students", "#55AC68", "#55AC68")
        listData.add(communityModel1)
        val communityModel2 =
            CommunityModel("Full-Time", parsePercentNA(fenroll?.percent.toString()), parseNA(fenroll?.value)+" \n Students", "#0A50BA", "#0A50BA")
        listData.add(communityModel2)
        val communityModel3 =
            CommunityModel("Part-Time", parsePercentNA(penroll?.percent.toString()), parseNA(penroll?.value)+" \n Students", "#5E4496", "#5E4496")
        listData.add(communityModel3)
        val communityModel4 = CommunityModel("Female", parsePercentNA(model?.campusCommunity?.femaleEnrollment.toString()), "", "#C1345F", "#C1345F")
        listData.add(communityModel4)
        val communityModel5 = CommunityModel("Male", parsePercentNA(model?.campusCommunity?.maleEnrollment.toString()), "", "#348FC1", "#0A50BA")
        listData.add(communityModel5)
        val communityModel6 = CommunityModel("Total Students", parsePercentNA(model?.campusCommunity?.totalEnrollment.toString()), "", "#54575F", "#54575F")
        listData.add(communityModel6)
        val communityModel7 = CommunityModel("In-state", parsePercentNA(model?.campusCommunity?.inState.toString()), "", "#24418E", "#24418E")
        listData.add(communityModel7)
        val communityModel8 = CommunityModel("Out-of-State", parsePercentNA(model?.campusCommunity?.outState.toString()), "", "#804053", "#804053")
        listData.add(communityModel8)
        val communityModel9 = CommunityModel("International", parsePercentNA(model?.campusCommunity?.foreign.toString()), "", "#C17034", "#C17034")
        listData.add(communityModel9)
        mBinding.listComunity.adapter = CommunityAdapter(listData)
        val ugRace :CollegeFactSheetModel.CampusCommunity.UgRace?=model?.campusCommunity?.ugRace

        val communityModela =
            CommunityModel("International Students", parsePercentNA(ugRace?.alien), "#C17034", "#80C17034", "")
        listDataProgress.add(communityModela)
        val communityModela1 =
            CommunityModel("India Alaska Native", parsePercentNA(ugRace?.native), "#348FC1", "#80348FC1", "")
        listDataProgress.add(communityModela1)
        val communityModela2 = CommunityModel("Hispanic Latino", parsePercentNA(ugRace?.hispanic), "#804053", "#80804053", "")
        listDataProgress.add(communityModela2)
        val communityModela3 =
            CommunityModel("Race Ethnicity Unkown", parsePercentNA(ugRace?.unknown), "#BF3E8C", "#80BF3E8C", "")
        listDataProgress.add(communityModela3)
        val communityModela4 = CommunityModel("Native Hawaiian",parsePercentNA( ugRace?.hispanic), "#349460", "#80349460", "")
        listDataProgress.add(communityModela4)
        val communityModela5 =
            CommunityModel("Black African American", parsePercentNA(ugRace?.american), "#270018", "#80270018", "")
        listDataProgress.add(communityModela5)
        val communityModela6 = CommunityModel("Asian", parsePercentNA(ugRace?.asian), "#24418E", "#8024418E", "")
        listDataProgress.add(communityModela6)
        val communityModela7 = CommunityModel("White", parsePercentNA(ugRace?.white), "#54575F", "#80918C92", "")
        listDataProgress.add(communityModela7)
        val communityModela8 =
            CommunityModel("Two or more Races", parsePercentNA(ugRace?.twoRaces), "#54575F", "#8054575F", "")
        listDataProgress.add(communityModela8)
        mBinding.progressView.adapter = ProgressAdapter(requireContext(), listDataProgress)
    }
}