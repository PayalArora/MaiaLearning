package com.maialearning.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maialearning.ui.fragments.*

class UpcomingBannerAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle, val size: Int) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        // Hardcoded in this order, you'll want to use lists and make sure the titles match
        if (position == 0) {
            return BannerOneFragment()
        } else if (position == 1) {
            return BannerTwoFragment()
        } else if (position == 2) {
            return BannerOneFragment()
        }
        return BannerOneFragment()
    }

    override fun getItemCount(): Int {
        // Hardcoded, use lists
        return size
    }
}