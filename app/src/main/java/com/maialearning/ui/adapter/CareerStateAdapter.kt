package com.maialearning.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maialearning.ui.fragments.*

class CareerStateAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle, size: Int) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    var size = 0

    override fun createFragment(position: Int): Fragment {
        // Hardcoded in this order, you'll want to use lists and make sure the titles match
        if (position == 0) {
            return SearchCareerFragment("")
        } else if (position == 1) {
            return SearchCareerFragment("list")
        } else if (position == 2) {
            return PlanFragment()
        } else if (position == 3) {
            return NysCareerPlan()
        }
        return SearchCareerFragment("")
    }

    override fun getItemCount(): Int {
        // Hardcoded, use lists
        return size
    }

    init {
        this.size = size
    }
}