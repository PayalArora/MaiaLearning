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
import com.maialearning.network.BaseApplication
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.VideoFactAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.viewmodel.FactSheetModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class OverViewFragment : Fragment() {
    private lateinit var mBinding:OverviewLayoutBinding
    private val mModel: FactSheetModel by viewModel()
    var model: CollegeFactSheetModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding= OverviewLayoutBinding.inflate(inflater,container,false)
    return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment: SupportMapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.map, mapFragment)
            .commit()
        mBinding.recyclerView.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        mBinding.recyclerView.adapter = VideoFactAdapter()
//        observer()
        init()
    }

    private fun init() {
        model = (context as UniversitiesActivity).getData()
        if (model != null) {
            mBinding.aboutdes.text=model?.basicInfo?.description
            mBinding.phoneNo.text=model?.basicInfo?.phone
            mBinding.webUrl.text=model?.basicInfo?.webAddr
            mBinding.entType.text=model?.basicInfo?.environmentType
            mBinding.degree.text=model?.basicInfo?.environmentType
            mModel.getCollegeNid("Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,"222178")

        }}
    fun observer(){
        mModel.idObserver.observe(requireActivity()) {
            SharedHelper(requireContext()).collegeNId = it.get("nid").toString()
            mModel.getUniversityContact("Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,SharedHelper(requireContext()).collegeNId)

        }
        mModel.contactInfoObserver.observe(requireActivity()) {
            it
//            SharedHelper(requireContext()).collegeNId = it.get("nid").toString()
//            mModel.getUniversityContact("Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,SharedHelper(requireContext()).collegeNId)

        }
    }

}