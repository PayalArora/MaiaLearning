package com.maialearning.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.SupportMapFragment
import com.maialearning.R
import com.maialearning.databinding.OverviewLayoutBinding
import com.maialearning.model.CollegeFactSheetModel
import com.maialearning.model.FactsheetModelOther
import com.maialearning.network.BaseApplication
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.VideoFactAdapter
import com.maialearning.util.parseEmpty
import com.maialearning.util.parseNA
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.viewmodel.FactSheetModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class OverViewFragment : Fragment() {
    private lateinit var mBinding: OverviewLayoutBinding
    private val mModel: FactSheetModel by viewModel()
    var model: CollegeFactSheetModel? = null
    var modelOther: FactsheetModelOther? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = OverviewLayoutBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment: SupportMapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.map, mapFragment)
            .commit()
        mBinding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        mBinding.recyclerView.adapter = VideoFactAdapter()
        observer()
        init()
    }

    private fun init() {
        if ((SharedHelper(context as UniversitiesActivity).country ?: "US") == "US") {
            model = (context as UniversitiesActivity).getData()
            if (model != null && model?.basicInfo != null) {
                mBinding.aboutdes.text = " ${model?.basicInfo?.description}"
                mBinding.phoneNo.text = " ${model?.basicInfo?.phone}"
                mBinding.webUrl.text = " ${model?.basicInfo?.webAddr}"
                mBinding.entType.text = " ${model?.basicInfo?.environmentType}"
                mBinding.termTyp.text = " ${model?.basicInfo?.termType}"
                mBinding.intsType.text = " ${model?.basicInfo?.institutionType}"
                mBinding.degree.text = " ${model?.basicInfo?.award?.joinToString(",")}"
                mBinding.locTxt.text =
                    " ${model?.basicInfo?.city + "," + model?.basicInfo?.state + "," + model?.basicInfo?.zip}"
                mModel.getCollegeNid(
                    "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                    "222178"
                )

            }
        } else {
            modelOther = (context as UniversitiesActivity).getDataOther()
            if (modelOther != null && modelOther?.basicInfo?.basicInfo?.name != null) {
                mBinding.aboutdes.text = " ${modelOther?.basicInfo?.basicInfo?.description}"
                mBinding.phoneNo.text = " ${modelOther?.basicInfo?.basicInfo?.phone}"
                mBinding.webUrl.text = " ${modelOther?.basicInfo?.basicInfo?.webAddr}"
                mBinding.entType.text = " ${modelOther?.basicInfo?.basicInfo?.environmentType}"
                mBinding.termTyp.text = " ${modelOther?.basicInfo?.basicInfo?.termType}"
                mBinding.intsType.text = " ${modelOther?.basicInfo?.basicInfo?.institutionType}"
                mBinding.degree.text = " ${modelOther?.basicInfo?.basicInfo?.award}"
                mBinding.locTxt.text =
                    " ${ parseEmpty(modelOther?.basicInfo?.basicInfo?.addr) + parseEmpty(modelOther?.basicInfo?.basicInfo?.city) +  parseEmpty(modelOther?.basicInfo?.basicInfo?.state) +   parseEmpty(modelOther?.basicInfo?.basicInfo?.zip)}"
                mModel.getCollegeNid(
                    "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                    "222178"
                )
                mBinding.aboutdes.visibility = View.VISIBLE
            } else if (modelOther?.providerInfo != null) {
                if (modelOther?.providerInfo is FactsheetModelOther.ProviderInfo) {
                    val providerInfo = modelOther?.providerInfo as FactsheetModelOther.ProviderInfo
                    mBinding.aboutdes.visibility = View.GONE
                    mBinding.webUrl.text = " ${providerInfo.website}"
                    mBinding.locTxt.text =
                        "${
                            parseEmpty(providerInfo.address1) + parseEmpty(providerInfo.address2) + parseEmpty(
                                providerInfo.address3
                            ) + parseEmpty(providerInfo.address4) + parseEmpty(
                                providerInfo.postalCode
                            )
                        }"


                }
            }
        }
    }

    fun observer() {
        mModel.idObserver.observe(requireActivity()) {
            SharedHelper(requireContext()).collegeNId = it.get("nid").toString()
            mModel.getUniversityContact(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                SharedHelper(requireContext()).collegeNId
            )

        }
    }

}