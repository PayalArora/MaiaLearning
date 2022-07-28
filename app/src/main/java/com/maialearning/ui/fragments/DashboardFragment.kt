package com.maialearning.ui.fragments

import DashboardPagerAdapter
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ApplicationFilterBinding
import com.maialearning.databinding.DashbordFragBinding
import com.maialearning.databinding.DateFilterBinding
import com.maialearning.model.AssignmentItem
import com.maialearning.model.DashboardOverdueResponse
import com.maialearning.model.SortedDateModel
import com.maialearning.util.getDate
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.DashboardFragViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DashboardFragment : Fragment(), OnItemClick {
    private lateinit var mBinding: DashbordFragBinding
    private val dashboardViewModel: DashboardFragViewModel by viewModel()
    private lateinit var dialog: Dialog
    var assignmentList = arrayListOf<AssignmentItem>()
    var endList = arrayListOf<SortedDateModel>()
    var endCompletedList = arrayListOf<SortedDateModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DashbordFragBinding.inflate(inflater, container, false)

        val toolbarBinding: Toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
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
       // setAdapter()



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
        SharedHelper(requireContext()).id?.let {
            dashboardViewModel.getOverDueCompleted(
                "Bearer " + SharedHelper(requireContext()).authkey,
                it
            )
        }

        dashboardViewModel.showLoading.observe(viewLifecycleOwner) {
            if (it == true) {
                dialog = showLoadingDialog(requireContext())
                dialog.show()
            } else {
                dialog.dismiss()
            }
        }
        dashboardViewModel.showError.observe(viewLifecycleOwner) {
            dialog.dismiss()
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        dashboardViewModel.overdueObserver.observe(viewLifecycleOwner) {
            Log.e("Response ", " " + it.assignment?.size)
            lifecycleScope.launch {
                dataSet(it)
                dialog.dismiss()
            }
        }

    }

    private fun dataSet(dashboardOverdueResponse: DashboardOverdueResponse) {
        assignmentList = dashboardOverdueResponse.assignment as ArrayList<AssignmentItem>
        overDueListWork()
        completedListWork()
//        setAdapter()
    }

    private fun completedListWork() {
      var  completedList = assignmentList.filter { it.completed == 1 } as ArrayList<AssignmentItem>

        val dateTimeFormatter: DateTimeFormatter =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter.ofPattern("E dd MMM, yyyy")
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        completedList.sortBy {
            it.date?.toLong()?.let { it1 ->
                LocalDate.parse(
                    getDate(
                        it1,
                        "E dd MMM, yyyy"
                    ), dateTimeFormatter
                )
            }

        }
        var mappedList = completedList.groupBy {
            it.date?.toLong()?.let { it1 ->
                getDate(
                    it1,
                    "E dd MMM, yyyy"
                )
            }
        }



        Log.e("data ", "" + mappedList.size)
        mappedList.map {
            if (it.key != null) {
                endCompletedList.add(SortedDateModel(it.key.toString(), it.value))
            }
        }
    }

    private fun overDueListWork() {
       var overdueList = assignmentList.filter { it.overdue == 1 } as ArrayList<AssignmentItem>

        val dateTimeFormatter: DateTimeFormatter =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter.ofPattern("E dd MMM, yyyy")
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        overdueList.sortBy {
            it.date?.toLong()?.let { it1 ->
                LocalDate.parse(
                    getDate(
                        it1,
                        "E dd MMM, yyyy"
                    ), dateTimeFormatter
                )
            }

        }
        var mappedList = overdueList.groupBy {
            it.date?.toLong()?.let { it1 ->
                getDate(
                    it1,
                    "E dd MMM, yyyy"
                )
            }
        }



        Log.e("data ", "" + mappedList.size)
        mappedList.map {
            if (it.key != null) {
                endList.add(SortedDateModel(it.key.toString(), it.value))
            }
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setAdapter() {
        var tabArray = arrayOf(
            getString(R.string.upcomings),
            getString(R.string.overdue),
            getString(R.string.complete)
        )
        for (item in tabArray) {
            mBinding.tabs.addTab(mBinding.tabs.newTab().setText(item))
        }
        loadFragment(UpcomingFragment())

        val adapter = DashboardPagerAdapter(
            requireContext(), this,
            tabArray.size, dashboardViewModel,endList,endCompletedList
        )
       // mBinding.viewPager.adapter = adapter
        mBinding.tabs.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
              when(tab?.position){
                  0->loadFragment(UpcomingFragment())
                  1-> loadFragment(OverDueFragment(dashboardViewModel,endList))
                  2->loadFragment(OverDueFragment(dashboardViewModel,endCompletedList))
              }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        //mBinding.viewPager.isUserInputEnabled = false
//        TabLayoutMediator(mBinding.tabs, mBinding.viewPager) { tab, position ->
//            tab.setText(tabArray[position])
//        }.attach()

//        TabLayoutMediator(mBinding.tabs, mBinding.viewPager) { tab, position ->
//            when (position) {
//                0 -> {
//                    tab.setCustomView(R.layout.item_tab)
//                    tab.customView?.findViewById<TextView>(R.id.tab_title)
//                        ?.setText(tabArray[position])
//                }
//                1 -> {
//                    tab.setCustomView(R.layout.item_tab)
//                    tab.customView?.findViewById<TextView>(R.id.tab_title)
//                        ?.setText(tabArray[position])
//                }
//                2 -> {
//                    tab.setCustomView(R.layout.item_tab)
//                    tab.customView?.findViewById<TextView>(R.id.tab_title)
//                        ?.setText(tabArray[position])
//                }
//            }
//        }.attach()

        mBinding.tabs.tabGravity = TabLayout.GRAVITY_FILL


    }

    override fun onClick(positiion: Int) {
        // loadFragment(MessageDetailFragment())
    }
    private fun loadFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.add(R.id.host_nav, fragment)
        transaction.commit()
    }
}