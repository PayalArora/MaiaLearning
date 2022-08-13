package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.TransferFragmentBinding
import com.maialearning.model.CollegeFactSheetModel
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.CostAdapter
import com.maialearning.ui.model.CommunityModel

class TransferFragment : Fragment() {
    private lateinit var mBinding: TransferFragmentBinding
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
        mBinding = TransferFragmentBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        mBinding.recyclerList.layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        mBinding.credictList.layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)

    }

    private fun initData() {
        model = (context as UniversitiesActivity).getData()
        if (model != null) {
            val communityModel = CommunityModel(
                "Transfer applicants\naccepted",
                model?.transfers?.transferAccepted.toString() + "%",
                "",
                "#F89A1F",
                ""
            )
            listData.add(communityModel)
            val communityModel1 = CommunityModel(
                "Transfer accepted\nwho enroll",
                model?.transfers?.enrolledAccepted.toString() + "%",
                "",
                "#55AC68",
                ""
            )
            listData.add(communityModel1)
            mBinding.recyclerList.adapter = CostAdapter(listData)
            val credits: CollegeFactSheetModel.Transfers.TranferCredits =
                model?.transfers?.tranferCredits!!
            val communityModel2 = CommunityModel(
                "Maximum\nTransferred",
                credits.maxTransfered.toString(),
                "",
                "#5E4496",
                ""
            )
            listDataProgress.add(communityModel2)
            val communityModela2 = CommunityModel(
                "Minimum Transferors\n Must Complete",
                credits.maxTransferedMust.toString(),
                "",
                "#BF3E8C",
                ""
            )
            listDataProgress.add(communityModela2)
            mBinding.credictList.adapter = CostAdapter(listDataProgress)
            mBinding.textdead.append(model?.transfers?.applicationDeadline)
            val require: CollegeFactSheetModel.Transfers.TransferApplicationDeadline =
                model?.transfers?.transferApplicationDeadline!!
            if (require.fall == null) {
                mBinding.fallTxt.text = "N/A"
            } else {
                mBinding.fallTxt.text = require.fall
            }
            if (require.winter == null) {
                mBinding.winTxt.text = "N/A"
            } else {
                mBinding.winTxt.text = require.winter
            }
            if (require.spring == null) {
                mBinding.sringTxt.text = "N/A"
            } else {
                mBinding.sringTxt.text = require.spring
            }
            if (require.summer == null) {
                mBinding.sumTxt.text = "N/A"
            } else {
                mBinding.sumTxt.text = require.summer
            }
        }
    }
}