package com.maialearning.ui.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.FragmentOverDueBinding
import com.maialearning.model.AssignmentItem
import com.maialearning.model.DashboardOverdueResponse
import com.maialearning.model.SortedDateModel
import com.maialearning.ui.adapter.OverDueHeadAdapter
import com.maialearning.ui.adapter.UpcomingBannerAdapter
import com.maialearning.util.getDate
import com.maialearning.viewmodel.DashboardFragViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class OverDueFragment(
    val dashboardViewModel: DashboardFragViewModel,
    var endList: ArrayList<SortedDateModel>
) : Fragment(), OnItemClick {

    private lateinit var mBinding: FragmentOverDueBinding
    var assignmentList = arrayListOf<AssignmentItem>()
    var overdueList = arrayListOf<AssignmentItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentOverDueBinding.inflate(inflater, container, false)
//        setAdapter()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
//        dashboardViewModel.overdueObserver.observe(viewLifecycleOwner) {
//            Log.e("Response ", " " + it.assignment?.size)
//            lifecycleScope.launch {
//                dataSet(it)
//            }
//        }

    }

//    private fun dataSet(dashboardOverdueResponse: DashboardOverdueResponse) {
//        assignmentList = dashboardOverdueResponse.assignment as ArrayList<AssignmentItem>
//        if (overdue)
//            overdueList = assignmentList.filter { it.overdue == 1 } as ArrayList<AssignmentItem>
//        else
//            overdueList =
//                assignmentList.filter { it.completed == 1 } as ArrayList<AssignmentItem>
//
//        val dateTimeFormatter: DateTimeFormatter =
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                DateTimeFormatter.ofPattern("E dd MMM, yyyy")
//            } else {
//                TODO("VERSION.SDK_INT < O")
//            }
//        overdueList.sortBy {
//            it.date?.toLong()?.let { it1 ->
//                LocalDate.parse(
//                    getDate(
//                        it1,
//                        "E dd MMM, yyyy"
//                    ), dateTimeFormatter
//                )
//            }
//
//        }
//        var mappedList = overdueList.groupBy {
//            it.date?.toLong()?.let { it1 ->
//                getDate(
//                    it1,
//                    "E dd MMM, yyyy"
//                )
//            }
//        }
//
//
//
//        Log.e("data ", "" + mappedList.size)
//        mappedList.map {
//            if (it.key != null) {
//                endList.add(SortedDateModel(it.key.toString(), it.value))
//            }
//        }
//        setAdapter()
//    }

    private fun setAdapter() {
        mBinding.upcomingList.adapter = OverDueHeadAdapter(endList, this)
    }

    private fun initView() {
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val adapter = UpcomingBannerAdapter(fm, requireActivity().lifecycle, 5)
        mBinding.bannerViewpager.adapter = adapter
        mBinding.bannerViewpager.isUserInputEnabled = true
        TabLayoutMediator(mBinding.tabLayout, mBinding.bannerViewpager) { tab, position ->
            //Some implementation
        }.attach()

    }

    override fun onClick(positiion: Int) {


    }


}