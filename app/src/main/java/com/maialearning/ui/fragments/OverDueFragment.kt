package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.FragmentOverDueBinding
import com.maialearning.model.SortedDateModel
import com.maialearning.ui.adapter.OnClick
import com.maialearning.ui.adapter.OverDueHeadAdapter
import com.maialearning.ui.adapter.UpcomingBannerAdapter
import com.maialearning.ui.bottomsheets.ChooseClick
import com.maialearning.ui.bottomsheets.SelectAttachmentSheet
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.DashboardFragViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OverDueFragment(
    val dashboardViewModel: DashboardFragViewModel,
    var endList: ArrayList<SortedDateModel>,
    var clickType: (type: String) -> Unit

) : Fragment(), OnItemClick, ChooseClick, OnClick {
    private lateinit var dialog: Dialog
    private lateinit var mBinding: FragmentOverDueBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentOverDueBinding.inflate(inflater, container, false)
        dialog = showLoadingDialog(requireContext())


        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        mBinding.upcomingList.adapter = OverDueHeadAdapter(null, requireActivity(), this, this)
        dialog.show()
        lifecycleScope.launch(Dispatchers.Main) {
            delay(100)
            setAdapter()
            delay(1000)
            dialog.dismiss()
        }

//        dashboardViewModel.overdueObserver.observe(viewLifecycleOwner) {
//            Log.e("Response ", " " + it.assignment?.size)
//            lifecycleScope.launch {
//                dataSet(it)
//            }
//        }

    }

     fun setAdapter( list: ArrayList<SortedDateModel> = endList) {
        // mBinding.upcomingList.adapter = OverDueHeadAdapter(overdueList = , this)
        ((mBinding.upcomingList.adapter) as OverDueHeadAdapter).overdueList = list
        ((mBinding.upcomingList.adapter) as OverDueHeadAdapter).notifyDataSetChanged()
    }

    private fun initView() {
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val adapter = UpcomingBannerAdapter(fm, requireActivity().lifecycle, 5)
        mBinding.bannerViewpager.adapter = adapter
        mBinding.bannerViewpager.isUserInputEnabled = true
        TabLayoutMediator(mBinding.tabLayout, mBinding.bannerViewpager) { tab, position ->
            //Some implementation
        }.attach()

    }

    override fun onClick(positiion: Int) {


    }

    var nId: String? = null
    private val REQUEST_IMAGE_UPCOMING_DETAIL = 11
    private val REQUEST_CHOOSE_PHOTO_UPCOMING_DETAIL = 22
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == REQUEST_IMAGE_UPCOMING_DETAIL || requestCode == REQUEST_CHOOSE_PHOTO_UPCOMING_DETAIL) && resultCode == AppCompatActivity.RESULT_OK) {
            SelectAttachmentSheet(
                requireContext(),
                layoutInflater,
                data,
                requestCode,
                dashboardViewModel,
                this,
                nId, dialogDetail
            ).showDialog()

        }
    }

    lateinit var dialogDetail: BottomSheetDialog
    override fun onChooseClick(nid: String?, dialog: BottomSheetDialog) {
        nId = nid
        Log.e("Choose, click", "Choose xclick")
        dialogDetail = dialog
    }

    override fun click(type: String) {
        clickType(type)
        //setAdapter(endList)
    }


}