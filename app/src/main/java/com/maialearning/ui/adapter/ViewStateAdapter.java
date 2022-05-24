package com.maialearning.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.maialearning.ui.fragments.ConsideringFragment;

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
                return new ConsideringFragment();
            }
            return new ConsideringFragment();
        }

        @Override
        public int getItemCount() {
            // Hardcoded, use lists
            return size;
        }
    }