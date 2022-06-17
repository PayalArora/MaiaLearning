package com.maialearning.ui.fragments

import DashboardPagerAdapter
import ViewPagerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.*
import com.maialearning.ui.activity.NewMessageActivity
import com.maialearning.ui.adapter.AddUniversiityAdapter
import com.maialearning.ui.adapter.MessageAdapter
import com.maialearning.ui.adapter.NotesAdapter
import com.maialearning.ui.bottomsheets.SheetUniversityFilter

class DashboardFragment : Fragment(), OnItemClick {
    private lateinit var mBinding: DashbordFragBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DashbordFragBinding.inflate(inflater, container, false)

        val toolbarBinding:Toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbarBinding.title = getString(R.string.dashboard)
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.GONE
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility = View.VISIBLE
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val sheetBinding: DateFilterBinding = DateFilterBinding.inflate(layoutInflater)
            sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
            dialog.setContentView(sheetBinding.root)
            dialog.show()
            sheetBinding.close.setOnClickListener { dialog.dismiss() }
            sheetBinding.applictaion.setOnClickListener {
                showApplicationFilter()
            }
        }
       setAdapter()
        return mBinding.root
    }
    fun showApplicationFilter(){
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: ApplicationFilterBinding = ApplicationFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.backBtn.setOnClickListener { dialog.dismiss() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setAdapter() {
        var tabArray = arrayOf(getString(R.string.upcomings),
            getString(R.string.overdue),
            getString(R.string.complete))
        for (item in tabArray) {
            mBinding.tabs.addTab(mBinding.tabs.newTab().setText(item))

        }
        val adapter = DashboardPagerAdapter(requireContext(), this,
            tabArray.size)
        mBinding.viewPager.adapter = adapter
        mBinding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(mBinding.tabs, mBinding.viewPager) { tab, position ->
            tab.setText(tabArray[position])
        }.attach()

        mBinding.tabs.tabGravity = TabLayout.GRAVITY_FILL


    }

    override fun onClick(positiion: Int) {
       // loadFragment(MessageDetailFragment())
    }

}