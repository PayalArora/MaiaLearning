package com.maialearning.ui.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ActivityPortfolioBinding
import com.maialearning.databinding.ApplicationFilterBinding
import com.maialearning.databinding.DashbordFragBinding
import com.maialearning.databinding.DateFilterBinding
import com.maialearning.ui.adapter.PortfolioAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.PortfolioViewModel
import com.maialearning.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PortfolioFragment : Fragment(), OnItemClick {




    private lateinit var mBinding: ActivityPortfolioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = ActivityPortfolioBinding.inflate(inflater, container, false)

        val toolbarBinding: Toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbarBinding.title = getString(R.string.portfolio)
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

    fun showApplicationFilter() {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: ApplicationFilterBinding =
            ApplicationFilterBinding.inflate(layoutInflater)
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
        val tabArray = arrayOf(
            getString(R.string.profile),
            getString(R.string.goal),
            getString(R.string.journals),
            getString(R.string.experiences),
            getString(R.string.galler),
        )

        for (item in tabArray) {
            mBinding.tabs.addTab(mBinding.tabs.newTab().setText(item))

        }
//        val fm: FragmentManager = activity.supportFragmentManager
        val adapter = PortfolioAdapter(this, tabArray.size)

        mBinding.viewPager.adapter = adapter

        TabLayoutMediator(mBinding.tabs, mBinding.viewPager) { tab, position ->
            tab.setText(tabArray[position])
        }.attach()


        mBinding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    override fun onClick(positiion: Int) {
        // loadFragment(MessageDetailFragment())
    }

}