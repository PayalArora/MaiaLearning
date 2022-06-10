package com.maialearning.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.maialearning.ui.fragments.ApplyingFragment;
import com.maialearning.ui.fragments.ConsideringFragment;
import com.maialearning.ui.fragments.DecisionsFragment;
import com.maialearning.ui.fragments.EssaysFragment;
import com.maialearning.ui.fragments.MilestonesFragment;
import com.maialearning.ui.fragments.RecommendationFragment;
import com.maialearning.ui.fragments.SearchFragment;
import com.maialearning.ui.fragments.UpcomingFragment;

public class ViewStateAdapter extends FragmentStateAdapter {
int size=0;
        public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,int size) {
            super(fragmentManager, lifecycle);
            this.size=size;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // Hardcoded in this order, you'll want to use lists and make sure the titles match
            if (position == 0) {
                return new SearchFragment();
            } if (position == 1) {
                return new ConsideringFragment();
            }else if (position ==2) {
                return new ApplyingFragment();
            }else if (position ==3) {
                return new MilestonesFragment();
            }else if (position ==4) {
//                return new RecommendationFragment();
            } else if (position ==5) {
              return new DecisionsFragment();
            } else if (position ==6) {
                 return new EssaysFragment();
            }

            return new UpcomingFragment();
        }

        @Override
        public int getItemCount() {
            // Hardcoded, use lists
            return size;
        }
    }