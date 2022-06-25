package com.maialearning.ui.bottomsheets

import ViewPagerAdapter
import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.R
import com.maialearning.databinding.ProfileLayoutBinding
import com.maialearning.databinding.UniversityFilterBinding
import com.maialearning.ui.activity.ClickFilters
import com.maialearning.ui.activity.NewMessageActivity
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.*

class ProfileFilter(val con: FragmentActivity, val layoutInflater: LayoutInflater) {

    fun showDialog() {
        val dialog = BottomSheetDialog(con)
        val sheetBinding: ProfileLayoutBinding = ProfileLayoutBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.close.setOnClickListener { dialog.dismiss() }
        dialog.show()
        setAdapter(sheetBinding)

    }
    private fun setAdapter( mBinding: ProfileLayoutBinding ) {
        var tabArray = arrayOf(con.getString(R.string.profile),
           con. getString(R.string.setting),
           con. getString(R.string.connections))
        for (item in tabArray) {
            mBinding.tabs.addTab(mBinding.tabs.newTab().setText(item))

        }
        val fm: FragmentManager = con.supportFragmentManager
        val adapter = ProfileAdapter(fm, con.lifecycle, tabArray.size)
        mBinding.viewPager.adapter = adapter
        mBinding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(mBinding.tabs, mBinding.viewPager) { tab, position ->
            tab.setText(tabArray[position])
        }.attach()

        mBinding.tabs.tabGravity = TabLayout.GRAVITY_FILL

    }
}