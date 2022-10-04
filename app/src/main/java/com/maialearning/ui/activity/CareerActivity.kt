package com.maialearning.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.R
import com.maialearning.databinding.ActivityCareerBinding
import com.maialearning.ui.adapter.CareerCompareAdapter
import com.maialearning.ui.adapter.CareerStateAdapter

class CareerActivity : Fragment() {
    var dialog: BottomSheetDialog? = null
    private lateinit var binding: ActivityCareerBinding
    private lateinit var toolbarBinding: Toolbar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityCareerBinding.inflate(inflater, container, false)
        val toolbar: Toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.visibility = View.GONE
        toolbarBinding = binding.toolbar
        binding.toolbar.contentInsetStartWithNavigation = 0
        binding.toolbar.setNavigationIcon(
            getDrawable(
                requireContext(),
                R.drawable.ic_baseline_keyboard_arrow_left_24
            )
        )
        binding.toolbar.title = getString(R.string.careers)
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.GONE
//        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility = View.VISIBLE
//        toolbarBinding.findViewById<ImageView>(R.id.toolbar_arrow).visibility = View.VISIBLE
        toolbarBinding.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        initView()

//        binding.addFab.setOnClickListener {
//            bottomSheetList()
//        }

        return binding.root
    }

   /* private fun bottomSheetList() {
        dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.compare_careers, null)
//        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        val listing = view.findViewById<RecyclerView>(R.id.listing)
        val layout = view.findViewById<ConstraintLayout>(R.id.layout)
        val close = view.findViewById<RelativeLayout>(R.id.close)
        DrawableCompat.setTint(layout.background, Color.parseColor("#E5E5E5"))

        listing.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        listing.adapter = CareerCompareAdapter(requireContext(), arrayListOut)
        close.setOnClickListener {
            dialog?.dismiss()
        }

        dialog?.setContentView(view)
        dialog?.show()
    }
*/
    private fun initView() {
        val tabArray = arrayOf(
            getString(R.string.search),
            getString(R.string.list),
            getString(R.string.plan),
            getString(R.string.nys_career)
        )
        for (item in tabArray) {
            binding.tabs.addTab(binding.tabs.newTab().setText(item))

        }
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val adapter = CareerStateAdapter(fm, lifecycle, tabArray.size)

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.setText(tabArray[position])
        }.attach()


        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (binding.tabs.selectedTabPosition != 0 && binding.tabs.selectedTabPosition != 1) {
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

}