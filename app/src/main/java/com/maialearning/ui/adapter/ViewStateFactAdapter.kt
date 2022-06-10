package com.maialearning.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maialearning.ui.fragments.*

class ViewStateFactAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, size: Int) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    var size = 0
    override fun createFragment(position: Int): Fragment {
        // Hardcoded in this order, you'll want to use lists and make sure the titles match
        if (position == 0) {
            return OverViewFragment()
        } else if (position == 1) {
            return CommunityFragmet()
        } else if (position == 2) {
            return AdmissionFragment()
        } else if (position == 3) {
            return CostFragment()
        }else if (position == 4) {
            return FactsNotesFragment()
        }else if (position == 5) {
            return CampusFragment()
        }else if (position == 6) {
            return DegreesFragment()
        }
        return OverViewFragment()
    }

    override fun getItemCount(): Int {
        // Hardcoded, use lists
        return size
    }

    init {
        this.size = size
    }
}