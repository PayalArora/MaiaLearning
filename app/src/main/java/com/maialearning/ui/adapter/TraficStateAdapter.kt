package com.maialearning.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maialearning.model.SelectedCareerResponse
import com.maialearning.ui.fragments.*

class TraficStateAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle, size: Int, var response: SelectedCareerResponse) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    var size = 0
    override fun createFragment(position: Int): Fragment {
        // Hardcoded in this order, you'll want to use lists and make sure the titles match
        if (position == 0) {
            return SummaryFragment(response)
        } else if (position == 1) {
            return RelatedCareerFragment(response)
        } else if (position == 2) {
            return SalariesFragment()
        }
        return SummaryFragment(response)
    }

    override fun getItemCount(): Int {
        // Hardcoded, use lists
        return size
    }

    init {
        this.size = size
    }
}