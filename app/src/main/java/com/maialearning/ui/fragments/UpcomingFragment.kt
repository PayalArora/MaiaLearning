package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.UpcomingLayoutBinding
import com.maialearning.model.SortedDateModel
import com.maialearning.ui.adapter.*
import com.maialearning.ui.bottomsheets.ChooseClick
import com.maialearning.ui.bottomsheets.SelectAttachmentSheet
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.DashboardFragViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UpcomingFragment(
    val dashboardViewModel: DashboardFragViewModel,
    var endList: ArrayList<SortedDateModel>,
    var clickType: (type: String) -> Unit
) : Fragment(), OnItemClick, ChooseClick , OnClick{
    private lateinit var mBinding: UpcomingLayoutBinding
    private lateinit var progress: Dialog
    private lateinit var inflate: LayoutInflater
    private lateinit var dialogDetail: BottomSheetDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflate = inflater
        mBinding = UpcomingLayoutBinding.inflate(inflater, container, false)
        progress = showLoadingDialog(requireContext())

        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initview()
        setListeners()
    }

    private fun initview() {

        val fm: FragmentManager = requireActivity().supportFragmentManager
        val adapter = UpcomingBannerAdapter(fm, requireActivity().lifecycle, 5)
        mBinding.bannerViewpager.adapter = adapter
        mBinding.bannerViewpager.isUserInputEnabled = true
        TabLayoutMediator(mBinding.tabLayout, mBinding.bannerViewpager) { tab, position ->
            //Some implementation
        }.attach()

        mBinding.upcomingList.adapter = OverDueHeadAdapter(null, requireActivity(), this, this)
        // dialog.show()
        lifecycleScope.launch(Dispatchers.Main) {
            // delay(100)
            setAdapter()
            // delay(3300)
            // dialog.dismiss()
        }

    }

    private fun setListeners() {

    }

    override fun onClick(positiion: Int) {
//        UpcomingItemDetails(requireActivity(), layoutInflater, positiion).showDialog()
    }

     fun setAdapter(list:ArrayList<SortedDateModel>? = endList) {
        // mBinding.upcomingList.adapter = OverDueHeadAdapter(overdueList = , this)
        ((mBinding.upcomingList.adapter) as OverDueHeadAdapter).overdueList = endList
        ((mBinding.upcomingList.adapter) as OverDueHeadAdapter).notifyDataSetChanged()
    }

    private val REQUEST_IMAGE_UPCOMING_DETAIL = 11
    private val REQUEST_CHOOSE_PHOTO_UPCOMING_DETAIL = 22
    private val REQUEST_CHOOSE_PDF_UPCOMING_DETAIL = 23

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == REQUEST_IMAGE_UPCOMING_DETAIL || requestCode == REQUEST_CHOOSE_PHOTO_UPCOMING_DETAIL || requestCode == REQUEST_CHOOSE_PDF_UPCOMING_DETAIL) && resultCode == AppCompatActivity.RESULT_OK) {
            SelectAttachmentSheet(
                requireContext(),
                inflate,
                data,
                requestCode,
                dashboardViewModel,
                this, nId, dialogDetail
            ).showDialog()

        }
    }

    var nId: String? = null
    override fun onChooseClick(nid: String?, dialog: BottomSheetDialog) {
        Log.e("Choose, click", "Choose click")
        nId = nid
        dialogDetail = dialog
    }

    override fun click(type: String) {
        clickType(type)
        //setAdapter()
    }
}