package com.maialearning.ui.activity

<<<<<<< HEAD
import android.content.res.Resources
||||||| 2b13a3b
=======
import android.content.Intent
import android.content.res.Resources
>>>>>>> 2087b07bcee6870adaedebbbe638b072e03ec0a4
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
<<<<<<< HEAD
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
||||||| 2b13a3b
=======
import com.google.android.material.bottomsheet.BottomSheetDialog
>>>>>>> 2087b07bcee6870adaedebbbe638b072e03ec0a4
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ActivityUniversitiesBinding
import com.maialearning.databinding.LayoutProgramsBinding
import com.maialearning.databinding.LayoutUniversityBinding
import com.maialearning.ui.adapter.AddUniversiityAdapter
import com.maialearning.ui.adapter.ProgramAdapter
import com.maialearning.ui.adapter.ViewStateAdapter
import com.maialearning.ui.adapter.ViewStateFactAdapter
import com.maialearning.ui.fragments.MilestonesFragment


class UniversitiesActivity : FragmentActivity(), OnItemClick {
    private lateinit var binding: ActivityUniversitiesBinding
    private lateinit var toolbarBinding: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUniversitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbarBinding = binding.toolbar
        binding.toolbar.contentInsetStartWithNavigation = 0
        binding.toolbar.setNavigationIcon(getDrawable(R.drawable.ic_baseline_keyboard_arrow_left_24))
        binding.toolbar.title = getString(R.string.universities)
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.GONE
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility = View.VISIBLE
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_arrow).visibility = View.VISIBLE
        toolbarBinding.setNavigationOnClickListener {
            finish()
        }
        initView()

    }

    private fun initView() {
        var tabArray = arrayOf(getString(R.string.search),
            getString(R.string.considering),
            getString(R.string.applying),
            getString(R.string.milestone),
            getString(R.string.recommendations),
            getString(R.string.decisions),
            getString(R.string.essays),
            getString(R.string.scholarships))
        for (item in tabArray) {
            binding.tabs.addTab(binding.tabs.newTab().setText(item))

        }
        val fm: FragmentManager = supportFragmentManager
        val adapter = ViewStateAdapter(fm, lifecycle, tabArray.size)

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.setText(tabArray[position])
        }.attach()
<<<<<<< HEAD
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).apply {
            setOnClickListener {
                bottomSheetWork()
            }
        }
||||||| 2b13a3b
=======
        binding.addFab.setOnClickListener {
            bottomSheet()

        }
>>>>>>> 2087b07bcee6870adaedebbbe638b072e03ec0a4

//        TabLayoutMediator(mBinding.tabs, mBinding.viewPager) { tab, position ->
//        }.attach()
    }
    private fun bottomSheetWork() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.layout_uni_factsheets, null)
        val factTabs = view.findViewById<TabLayout>(R.id.fact_tabs)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        // val bottomSheetBehavior = BottomSheetBehavior.from(layout)

        //  bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED;
        //view.minimumHeight = ViewGroup.LayoutParams.MATCH_PARENT

        val tabArray = arrayOf(getString(R.string.overview),
            getString(R.string.community),
            getString(R.string.admission),
            getString(R.string.cost_),
            getString(R.string.notes),
            getString(R.string.campus_service))
        for (item in tabArray) {
            factTabs.addTab(factTabs.newTab().setText(item))
        }

<<<<<<< HEAD
        val fm: FragmentManager = supportFragmentManager
        val adapter = ViewStateFactAdapter(fm, lifecycle, tabArray.size)
        viewPager.adapter = adapter
        TabLayoutMediator(factTabs, viewPager) { tab, position ->
            tab.text = tabArray[position]
        }.attach()
        view.minimumHeight =( (Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(view)
        dialog.show()
    }
||||||| 2b13a3b
=======
    private fun bottomSheet() {
        val dialog = BottomSheetDialog(this)
        val sheetBinding: LayoutUniversityBinding = LayoutUniversityBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight =( (Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.close.setOnClickListener { dialog.dismiss()}

        sheetBinding.reciepentList.adapter = AddUniversiityAdapter(this)
        sheetBinding.save.setOnClickListener { dialog.dismiss() }
    }

    override fun onClick(positiion: Int) {
>>>>>>> 2087b07bcee6870adaedebbbe638b072e03ec0a4

    }
}