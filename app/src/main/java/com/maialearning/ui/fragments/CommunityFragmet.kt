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
        if (model != null) {
            mBinding.house.text=model?.campusCommunity?.housing
            mBinding.religTxt.text=model?.campusCommunity?.religious
            mBinding.govern.text=model?.campusCommunity?.studentGovernment
            mBinding.yes1.text=model?.campusCommunity?.studentNewspaper
            mBinding.yes2.text=model?.campusCommunity?.literaryMagazine
            mBinding.yes3.text=model?.campusCommunity?.yearbook
            mBinding.minorityTxt.text=model?.campusCommunity?.minorityOrganizations
            mBinding.interTxt.text=model?.campusCommunity?.internationalOrganizations
            mBinding.religousTxt.text=model?.campusCommunity?.campusOrganizations
            mBinding.orgTxt.text=model?.campusCommunity?.otherOrganizations
            mBinding.learningTxt.text=model?.campusCommunity?.specialOpportunities.toString()
            initData()
        }
    }

    private fun initData() {
        val enroll :CollegeFactSheetModel.CampusCommunity.UndergraduateEnroll=model?.campusCommunity?.undergraduateEnroll!!
        val genroll :CollegeFactSheetModel.CampusCommunity.GraduateEnroll=model?.campusCommunity?.graduateEnroll!!
        val fenroll :CollegeFactSheetModel.CampusCommunity.FullTime=model?.campusCommunity?.fullTime!!
        val penroll :CollegeFactSheetModel.CampusCommunity.PartTime=model?.campusCommunity?.partTime!!
        val communityModel =
            CommunityModel("Undergraduate", enroll.percent.toString()+"%", enroll.value+" \n Students", "#F89A1F", "#F89A1F")
        listData.add(communityModel)
        val communityModel1 =
            CommunityModel("Graduate", genroll.percent.toString()+"%", genroll.value+" \n Students", "#55AC68", "#55AC68")
        listData.add(communityModel1)
        val communityModel2 =
            CommunityModel("Full-Time", fenroll.percent.toString()+"%", fenroll.value+" \n Students", "#0A50BA", "#0A50BA")
        listData.add(communityModel2)
        val communityModel3 =
            CommunityModel("Part-Time", penroll.percent.toString()+"%", penroll.value+" \n Students", "#5E4496", "#5E4496")
        listData.add(communityModel3)
        val communityModel4 = CommunityModel("Female", model?.campusCommunity?.femaleEnrollment.toString()+"%", "", "#C1345F", "#C1345F")
        listData.add(communityModel4)
        val communityModel5 = CommunityModel("Male", model?.campusCommunity?.maleEnrollment.toString()+"%", "", "#348FC1", "#0A50BA")
        listData.add(communityModel5)
        val communityModel6 = CommunityModel("Total Students", model?.campusCommunity?.totalEnrollment.toString(), "", "#54575F", "#54575F")
        listData.add(communityModel6)
        val communityModel7 = CommunityModel("In-state", model?.campusCommunity?.inState.toString()+"%", "", "#24418E", "#24418E")
        listData.add(communityModel7)
        val communityModel8 = CommunityModel("Out-of-State", model?.campusCommunity?.outState.toString()+"%", "", "#804053", "#804053")
        listData.add(communityModel8)
        val communityModel9 = CommunityModel("International", model?.campusCommunity?.foreign.toString()+"%", "", "#C17034", "#C17034")
        listData.add(communityModel9)
        mBinding.listComunity.adapter = CommunityAdapter(listData)
        val ugRace :CollegeFactSheetModel.CampusCommunity.UgRace=model?.campusCommunity?.ugRace!!

        val communityModela =
            CommunityModel("International Students", ugRace.alien+"%", "#C17034", "#80C17034", "")
        listDataProgress.add(communityModela)
        val communityModela1 =
            CommunityModel("India Alaska Native", ugRace.native+"%", "#348FC1", "#80348FC1", "")
        listDataProgress.add(communityModela1)
        val communityModela2 = CommunityModel("Hispanic Latino", ugRace.hispanic+"%", "#804053", "#80804053", "")
        listDataProgress.add(communityModela2)
        val communityModela3 =
            CommunityModel("Race Ethnicity Unkown", ugRace.unknown+"%", "#BF3E8C", "#80BF3E8C", "")
        listDataProgress.add(communityModela3)
        val communityModela4 = CommunityModel("Native Hawaiian", ugRace.hispanic+"%", "#349460", "#80349460", "")
        listDataProgress.add(communityModela4)
        val communityModela5 =
            CommunityModel("Black African American", ugRace.american+"%", "#270018", "#80270018", "")
        listDataProgress.add(communityModela5)
        val communityModela6 = CommunityModel("Asian", ugRace.asian+"%", "#24418E", "#8024418E", "")
        listDataProgress.add(communityModela6)
        val communityModela7 = CommunityModel("White", ugRace.white+"%", "#54575F", "#80918C92", "")
        listDataProgress.add(communityModela7)
        val communityModela8 =
            CommunityModel("Two or more Races", ugRace.twoRaces+"%", "#54575F", "#8054575F", "")
        listDataProgress.add(communityModela8)
        mBinding.progressView.adapter = ProgressAdapter(requireContext(), listDataProgress)
    }
}