package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.CostFragmentBinding
import com.maialearning.model.CollegeFactSheetModel
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.CommunityAdapter
import com.maialearning.ui.model.CommunityModel

class CostFragment : Fragment() {
    private lateinit var mBinding: CostFragmentBinding
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
        mBinding = CostFragmentBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        mBinding.recyclerList.layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        mBinding.credictList.layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)

    }

    private fun init() {
        model = (context as UniversitiesActivity).getData()
        if (model != null) {
            initData()
        }
    }

    private fun initData() {
        val financial: CollegeFactSheetModel.TutionFinancial = model?.tutionFinancial!!

        val communityModel = CommunityModel(
            "Tuition & Fees \n(In-State)",
            "$" + financial.tutionFeesInstate,
            "",
            "#24418E",
            "#1A24418E"
        )
        listData.add(communityModel)
        val communityModel1 = CommunityModel(
            "Tuition & Fees \n(Out-State)",
            "$" + financial.tutionFeesOutstate,
            "",
            "#804053",
            "#1A804053"
        )
        listData.add(communityModel1)
        val communityModel2 =
            CommunityModel("Room\n& Board", "$" + financial.roomBoard, "", "#54575F", "#1A54575F")
        listData.add(communityModel2)
        val communityModela3 = CommunityModel(
            "Additional \nFees",
            "$" + financial.otherCharges,
            "",
            "#C1345F",
            "#1AC1345F"
        )
        listData.add(communityModela3)
        val communityModela4 =
            CommunityModel("Books & \nSupplies", "$" + financial.books, "", "#C17034", "#1AC17034")
        listData.add(communityModela4)
        val communityModela5 = CommunityModel(
            "Total \n(In-State)",
            "$" + financial.totalInstate,
            "",
            "#5E4496",
            "#1A5E4496"
        )
        listData.add(communityModela5)
        val communityModela6 = CommunityModel(
            "Total \n(Out-State)",
            "$" + financial.totalOutstate,
            "",
            "#348FC1",
            "#1A348FC1"
        )
        listData.add(communityModela6)
        mBinding.recyclerList.adapter = CommunityAdapter(listData)
        val financialAid: CollegeFactSheetModel.TutionFinancial.FinancialAid =
            financial.financialAid
        val communityModela_1 = CommunityModel(
            "Percentage of students' needs were fully met ",
            financialAid.fullyMetStudent + "%",
            "",
            "#F89A1F",
            "#1AF89A1F"
        )
        listDataProgress.add(communityModela_1)
        val communityModela_2 = CommunityModel(
            "Average financial aid award  ",
            "$" + financialAid.avgFinancialAward,
            "",
            "#55AC68",
            "#1A55AC68"
        )
        listDataProgress.add(communityModela_2)
        val communityModela_3 = CommunityModel(
            "Students receiving Pell Grant ",
            financialAid.pellGrantStudents + "%",
            "",
            "#0A50BA",
            "#1A0A50BA"
        )
        listDataProgress.add(communityModela_3)
        val communityModela_4 = CommunityModel(
            "Students with institutional grant or scholarship",
            financialAid.institutionalGrantStudents + "%",
            "",
            "#5E4496",
            "#1A5E4496"
        )
        listDataProgress.add(communityModela_4)
        val communityModela_5 = CommunityModel(
            "Students receiving Federal student loans",
            financialAid.federalStudentsLoans + "%",
            "",
            "#F89A1F",
            "#1AF89A1F"
        )
        listDataProgress.add(communityModela_5)
        mBinding.credictList.adapter = CommunityAdapter(listDataProgress)
        if (financial.financialDeadlines.priorityDeadline != null) {
            mBinding.periorTxt.text = financial.financialDeadlines.priorityDeadline
        } else {
            mBinding.periorTxt.text="N/A"
        }
        if ( financial.financialDeadlines.notificationDate != null) {
            mBinding.notTxt.text = financial.financialDeadlines.notificationDate
        } else {
            mBinding.notTxt.text ="N/A"
        }
        if (financial.financialDeadlines.regularDeadline != null) {
            mBinding.regularTxt.text = financial.financialDeadlines.regularDeadline
        } else {
            mBinding.regularTxt.text="N/A"

        }
        mBinding.textdead.text= financial.financialOffice.mail
        mBinding.phoneno.text= financial.financialOffice.phone1
        mBinding.fax.text= financial.financialOffice.phone2
        mBinding.fsfa.text= financial.financialOffice.fafsaCode
    }
}