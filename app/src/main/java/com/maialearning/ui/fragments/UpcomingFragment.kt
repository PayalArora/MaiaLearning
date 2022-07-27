package com.maialearning.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.FragmentDashboardBinding
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.databinding.UpcomingLayoutBinding
import com.maialearning.ui.adapter.*
import com.maialearning.ui.bottomsheets.PrimaryEmail
import com.maialearning.ui.bottomsheets.UpcomingItemDetails

class UpcomingFragment : Fragment(), OnItemClick {
    private lateinit var mBinding: UpcomingLayoutBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = UpcomingLayoutBinding.inflate(inflater, container, false)
        return mBinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initview()
        setListeners()
    }

    private fun initview() {

        val fm: FragmentManager = requireActivity().supportFragmentManager
        val adapter = UpcomingBannerAdapter(fm, requireActivity().lifecycle,5)
        mBinding.bannerViewpager.adapter = adapter
        mBinding.bannerViewpager.isUserInputEnabled = true
        TabLayoutMediator(mBinding.tabLayout, mBinding.bannerViewpager) { tab, position ->
            //Some implementation
        }.attach()
        mBinding.upcomingList.adapter = UpcomingListAdapter(this)

    }

    private fun setListeners() {
   
    }

    override fun onClick(positiion: Int) {
        UpcomingItemDetails(requireActivity(), layoutInflater,positiion).showDialog()
    }

}