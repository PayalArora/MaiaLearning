package com.maialearning.ui.activity

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.R
import com.maialearning.databinding.*
import com.maialearning.ui.adapter.*
import com.maialearning.ui.bottomsheets.ProfileFilter
import com.maialearning.ui.bottomsheets.SheetUniversityFilter


class UniversitiesActivity : FragmentActivity(), ClickFilters {
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
        binding.toolbarProf.setOnClickListener {
          //  ProfileFilter(this, layoutInflater).showDialog()
        }

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

        toolbarBinding.findViewById<ImageView>(R.id.toolbar_arrow).apply {
            setOnClickListener {
                bottomSheetList()
//                bottomSheetWork()
            }
        }

        binding.addFab.setOnClickListener {
            bottomSheetAddUniv()

        }
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).setOnClickListener {
            univFilter()
        }
        binding.tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
              if (binding.tabs.selectedTabPosition == 5){
                  binding.addFab.visibility = View.GONE
              } else
                  binding.addFab.visibility = View.VISIBLE
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

//        TabLayoutMediator(mBinding.tabs, mBinding.viewPager) { tab, position ->
//        }.attach()
    }
    private fun bottomSheetList() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.uni_list_bottom, null)
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        val listing = view.findViewById<RecyclerView>(R.id.listing)
        val layout = view.findViewById<ConstraintLayout>(R.id.layout)
        DrawableCompat.setTint(layout.background, Color.parseColor("#E5E5E5"))

        listing.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        listing.adapter=UniFactAdapter(this,::bottomSheetWork)
//        close.setOnClickListener {
//            dialog.dismiss()
//        }

        dialog.setContentView(view)
        dialog.show()
    }

    public fun bottomSheetWork() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.layout_uni_factsheets, null)
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        val factTabs = view.findViewById<TabLayout>(R.id.fact_tabs)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val close = view.findViewById<ImageView>(R.id.close)
        val tabArray = arrayOf(getString(R.string.overview),
            getString(R.string.community),
            getString(R.string.admission),
            getString(R.string.cost_),
            getString(R.string.degree),
            getString(R.string.transfer),
            getString(R.string.notes),
            getString(R.string.campus_service))
        for (item in tabArray) {
            factTabs.addTab(factTabs.newTab().setText(item))
        }
        close.setOnClickListener {
            dialog.dismiss()
        }
        val fm: FragmentManager = supportFragmentManager
        val adapter = ViewStateFactAdapter(fm, lifecycle, tabArray.size)
        viewPager.adapter = adapter
        TabLayoutMediator(factTabs, viewPager) { tab, position ->
            tab.text = tabArray[position]
        }.attach()
         dialog.setContentView(view)
        dialog.show()
    }

    private fun bottomSheetAddUniv() {
        val dialog = BottomSheetDialog(this)
        val sheetBinding: LayoutUniversityBinding = LayoutUniversityBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.close.setOnClickListener { dialog.dismiss() }

        sheetBinding.reciepentList.adapter = AddUniversiityAdapter(this)
        sheetBinding.save.setOnClickListener { dialog.dismiss() }
        sheetBinding.country.setOnClickListener {
            SheetUniversityFilter(this, layoutInflater).showDialog()
        }
    }

    private fun univFilter() {
        val dialog = BottomSheetDialog(this)

        val sheetBinding: UniversityFilterBinding = UniversityFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.search.visibility = View.GONE
        sheetBinding.filters.setText(resources.getString(R.string.filters))
        dialog.show()
        sheetBinding.clearText.setOnClickListener { dialog.dismiss() }
        sheetBinding.backBtn.setOnClickListener { dialog.dismiss() }
        sheetBinding.reciepentList.adapter =
            UnivFilterAdapter(resources.getStringArray(R.array.UnivFilters), this)

    }

    private fun countryFilter() {
        SheetUniversityFilter(this, layoutInflater).showDialog()
    }

    override fun onClick(positiion: Int, type: Int) {
        if(type==2) {
            if (positiion == 0) {
                countryFilter()
            } else if (positiion == 1) {
                SheetUniversityFilter(this, layoutInflater).regionFilter(View.VISIBLE,
                    resources.getString(R.string.reigon),
                    positiion)
            } else if (positiion == 2) {
                SheetUniversityFilter(this, layoutInflater).regionFilter(View.VISIBLE,
                    resources.getString(R.string.list),
                    positiion, View.GONE)
            } else if (positiion == 3 ) {
                typeFilter()
            }
            else if (positiion == 4 ) {
                SheetUniversityFilter(this, layoutInflater).regionFilter(View.GONE,
                    resources.getString(R.string.selectivity),
                    positiion)
            } else if (positiion == 5 ) {
                SheetUniversityFilter(this, layoutInflater).regionFilter(View.GONE,
                    resources.getString(R.string.programs),
                    positiion)
            }else if (positiion == 6 ) {
                SheetUniversityFilter(this, layoutInflater).regionFilter(View.GONE,
                    resources.getString(R.string.sports),
                    positiion, View.VISIBLE)
            }
            else if (positiion == 7 ) {
                moreFilter()
            }
        }

    }


    private fun moreFilter() {
        val dialog = BottomSheetDialog(this)
        val sheetBinding: MoreSheetBinding = MoreSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.filters.setText(title)
        sheetBinding.backTxt.setOnClickListener { dialog.dismiss() }
        dialog.show()
        sheetBinding.clearText.setOnClickListener { dialog.dismiss() }
        val adapter = ArrayAdapter.createFromResource(this,
            R.array.capmpus_activity,
            android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //sheetBinding.spinner.setPrompt("Campus Activities")

        sheetBinding.spinner.setAdapter(
            NothingSelectedSpinnerAdapter(
                adapter,
                R.layout.nothing_adapter,  // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                this))
        sheetBinding.campusActivity.setOnClickListener { sheetBinding.spinner.performClick() }


    }

    private fun typeFilter() {
        val dialog = BottomSheetDialog(this)
        val sheetBinding: TypeSheetBinding = TypeSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.filters.setText(title)
        dialog.show()
        sheetBinding.clearText.setOnClickListener { dialog.dismiss() }
        sheetBinding.backTxt.setOnClickListener { dialog.dismiss() }
        val adapter = ArrayAdapter.createFromResource(this,
            R.array.capmpus_activity,
            android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sheetBinding.spinner.setAdapter(
            NothingSelectedSpinnerAdapter(
                adapter,
                R.layout.nothing_adapter,  // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                this))
        sheetBinding.religous.setOnClickListener { sheetBinding.spinner.performClick() }


    }

}

interface ClickFilters {
    fun onClick(positiion: Int, type: Int)
}

