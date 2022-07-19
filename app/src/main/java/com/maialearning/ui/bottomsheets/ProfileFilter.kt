package com.maialearning.ui.bottomsheets

import ViewPagerAdapter
import android.content.Intent
import android.content.res.Resources
import android.util.Log
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
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.ext.isInt

class ProfileFilter(val con: FragmentActivity, val layoutInflater: LayoutInflater) {
    private val profileModel: ProfileViewModel by con.viewModel()

    fun showDialog() {
        val dialog = BottomSheetDialog(con)
        val sheetBinding: ProfileLayoutBinding = ProfileLayoutBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.close.setOnClickListener { dialog.dismiss() }
        dialog.show()
        profileWork(sheetBinding)
        setAdapter(sheetBinding)

    }

    private fun setAdapter(mBinding: ProfileLayoutBinding) {
        var tabArray = arrayOf(
            con.getString(R.string.profile),
            con.getString(R.string.setting),
            con.getString(R.string.connections)
        )
        for (item in tabArray) {
            mBinding.tabs.addTab(mBinding.tabs.newTab().setText(item))

        }
        val fm: FragmentManager = con.supportFragmentManager
        val adapter = ProfileAdapter(fm, con.lifecycle, tabArray.size, profileModel)
        mBinding.viewPager.adapter = adapter
        mBinding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(mBinding.tabs, mBinding.viewPager) { tab, position ->
            tab.setText(tabArray[position])
        }.attach()

        mBinding.tabs.tabGravity = TabLayout.GRAVITY_FILL

    }

    private fun profileWork(mBinding: ProfileLayoutBinding) {
        SharedHelper(con).authkey?.let {
            SharedHelper(con).id?.let { it1 ->
                Log.e(" it1 ", it1)
                profileModel.getProfile("Bearer " + it, it1)
            }
        }
        profileModel.observer.observe(con, {
            mBinding.nameTxt.setText(it.info?.firstName + " " + it?.info?.lastName)
            if (SharedHelper(con).convention?:false){
                mBinding.gradeTxt.setText("ID: ${it.info?.nid} (Grade ${it.info?.grade})")
            } else {
                if (it.info?.grade?.isInt() == true)
                mBinding.gradeTxt.setText("ID: ${it.info?.nid} (Year ${it.info?.grade.toInt() + 1})")
                else
                    mBinding.gradeTxt.setText("ID: ${it.info?.nid} (Year ${it.info?.grade})")
            }
            Picasso.with(con).load(it.info?.profilePic).into(mBinding.toolbarProf)
        })
    }

}