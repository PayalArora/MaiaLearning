package com.maialearning.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maialearning.ui.fragments.*
import com.maialearning.viewmodel.ProfileViewModel

class ProfileAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val size: Int, val profileModel: ProfileViewModel) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        // Hardcoded in this order, you'll want to use lists and make sure the titles match
        if (position == 0) {
            return ProfileFragment(profileModel)
        } else if (position == 1) {
            return SettingsFragment(profileModel)
        } else if (position == 2) {
            return ConnectionsFragment(profileModel)
        }
        return ProfileFragment(profileModel)
    }

    override fun getItemCount(): Int {
        // Hardcoded, use lists
        return size
    }


}