package com.maialearning.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maialearning.ui.fragments.*
import com.maialearning.util.prefhandler.SharedHelper

class ViewStateFactAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    size: Int,
    var context: Context
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    var size = 0
    override fun createFragment(position: Int): Fragment {
        // Hardcoded in this order, you'll want to use lists and make sure the titles match
        if (position == 0) {
            return OverViewFragment()
        } else if (position == 1) {
            if ((SharedHelper(context).country ?: "US") == "US") {
                return CommunityFragmet()
            } else {
                return AdmissionFragment()
            }
        } else if (position == 2) {
            return AdmissionFragment()
        } else if (position == 3) {
            if ((SharedHelper(context).country ?: "US") == "US") {
                return CostFragment()
            } else {
                return CampusFragment()
            }
        } else if (position == 4) {
            return DegreesFragment()
        } else if (position == 5) {
            return VarsityFragment()
        } else if (position == 6) {
            return TransferFragment()
        } else if (position == 7) {
            return FactsNotesFragment()
        } else if (position == 8) {
            return CampusFragment()
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