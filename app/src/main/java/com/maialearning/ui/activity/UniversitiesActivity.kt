package com.maialearning.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.maialearning.R
import com.maialearning.databinding.ActivityUniversitiesBinding
import com.maialearning.ui.adapter.ViewStateAdapter

class UniversitiesActivity : FragmentActivity() {
    private lateinit var binding: ActivityUniversitiesBinding
    private lateinit var toolbarBinding: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUniversitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbarBinding = binding.toolbar
        binding.toolbar.title = ""
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.VISIBLE
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility = View.GONE
        initView()

    }

    private fun initView() {
        var tabArray = arrayOf(getString(R.string.search),getString(R.string.considering),getString(R.string.applying)
            ,getString(R.string.milestone),getString(R.string.recommendations),getString(R.string.decisions),getString(R.string.essays),getString(R.string.scholarships))

        val fm: FragmentManager = supportFragmentManager
        val adapter = ViewStateAdapter(fm, lifecycle,tabArray.size)

        binding.viewPager.adapter = adapter

        for (item in tabArray) {
            binding.tabs.addTab(binding.tabs.newTab().setText(item))
        }


//        TabLayoutMediator(mBinding.tabs, mBinding.viewPager) { tab, position ->
//        }.attach()
    }
}