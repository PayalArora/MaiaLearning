package com.maialearning.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maialearning.ui.fragments.NysCareerPlan
import com.maialearning.ui.fragments.PlanFragment
import com.maialearning.ui.fragments.PortfolioProfileFragment
import com.maialearning.ui.fragments.SearchCareerFragment

class PortfolioAdapter (fragmentManager: Fragment, size: Int) :
    FragmentStateAdapter(fragmentManager) {
    var size = 0

    override fun createFragment(position: Int): Fragment {
        // Hardcoded in this order, you'll want to use lists and make sure the titles match
        if (position == 0) {
            return PortfolioProfileFragment()

        } else if (position == 2) {

        } else if (position == 3) {

        }
        return PortfolioProfileFragment()
    }

    override fun getItemCount(): Int {
        // Hardcoded, use lists
        return size
    }

    init {
        this.size = size
    }
}