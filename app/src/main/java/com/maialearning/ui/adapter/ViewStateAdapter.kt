package com.maialearning.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;
import com.maialearning.ui.fragments.ApplyingFragment;
import com.maialearning.ui.fragments.ConsideringFragment;
import com.maialearning.ui.fragments.DecisionsFragment;
import com.maialearning.ui.fragments.EssaysFragment;
import com.maialearning.ui.fragments.MilestonesFragment;
import com.maialearning.ui.fragments.RecommendationFragment;
import com.maialearning.ui.fragments.SearchFragment;

class ViewStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,val tabs: TabLayout, var size:Int ) :
        FragmentStateAdapter(fragmentManager, lifecycle)
        {
            override fun getItemCount(): Int {
               return size
            }

            override fun createFragment(position: Int): Fragment {
            // Hardcoded in this order, you'll want to use lists and make sure the titles match
            if (position == 0) {
             return  SearchFragment();
            } else if (position == 1) {
                return  ConsideringFragment();
            }else if (position ==2) {
                return  ApplyingFragment(tabs);
            }else if (position ==3) {
                return  MilestonesFragment();
            }else if (position ==4) {
                return  RecommendationFragment();
            } else if (position ==5) {
              return  DecisionsFragment();
            } else if (position ==6) {
                 return  EssaysFragment();
            }

            return  EssaysFragment();
        }

    }