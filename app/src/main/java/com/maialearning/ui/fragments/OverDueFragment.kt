package com.maialearning.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.FragmentOverDueBinding
import com.maialearning.model.SortedDateModel
import com.maialearning.ui.adapter.OverDueHeadAdapter
import com.maialearning.ui.adapter.UpcomingBannerAdapter
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.DashboardFragViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OverDueFragment(
    val dashboardViewModel: DashboardFragViewModel,
    var endList: ArrayList<SortedDateModel>,
) : Fragment(), OnItemClick {
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
        mBinding.upcomingList.adapter = OverDueHeadAdapter(null, this)
        dialog.show()
        lifecycleScope.launch(Dispatchers.Main) {
            delay(100)
            setAdapter()
            delay(3300)
            dialog.dismiss()
        }

//        dashboardViewModel.overdueObserver.observe(viewLifecycleOwner) {
//            Log.e("Response ", " " + it.assignment?.size)
//            lifecycleScope.launch {
//                dataSet(it)
//            }
//        }

    }

    private fun setAdapter() {
        // mBinding.upcomingList.adapter = OverDueHeadAdapter(overdueList = , this)
        ((mBinding.upcomingList.adapter) as OverDueHeadAdapter).overdueList = endList
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


}