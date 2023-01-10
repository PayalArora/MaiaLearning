package com.maialearning.ui.fragments

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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.gson.JsonObject
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ApplicationFilterBinding
import com.maialearning.databinding.DashbordFragBinding
import com.maialearning.databinding.DateFilterBinding
import com.maialearning.model.*
import com.maialearning.util.ML_URL
import com.maialearning.util.getDate
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.DashboardFragViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class DashboardFragment : Fragment() {
    private lateinit var mBinding: DashbordFragBinding
    private val dashboardViewModel: DashboardFragViewModel by viewModel()
    private lateinit var dialog: Dialog
    var assignmentList = arrayListOf<DashboardOverdueResponse.AssignmentItem>()
    var endList = arrayListOf<SortedDateModel>()
    var endCompletedList = arrayListOf<SortedDateModel>()
    var upcomingList = arrayListOf<SortedDateModel>()
    var surveyList = arrayListOf<SortedDateModel>()
    private var upcomingFragment: UpcomingFragment? = null
    private var surveyFragment: SurveyFragment? = null
    private var overDueFragment: OverDueFragment? = null

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
            sheetBinding.rbThisWeek.setOnClickListener {
//                upcomingList.filter {  }
            }
        }
        // setAdapter()


        return mBinding.root

    }

    private fun click(type: String) {
        when (type) {
            COMPLETE -> {
                listing()
            }
            RESET -> {
                listing()
            }
        }
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
        dialog = showLoadingDialog(requireContext())
        upcomingFragment = UpcomingFragment(dashboardViewModel, upcomingList, ::click)
        loadFragment(upcomingFragment)
        listing()
        setAdapter()

        dashboardViewModel.showLoading.observe(viewLifecycleOwner) {
            if (it == true) {
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
            lifecycleScope.launch(Dispatchers.Main) {
                dataSet(it)
                dashboardViewModel.showLoading.value = false
                if (mBinding.tabs.selectedTabPosition == 0) {
                    upcomingFragment?.setAdapter(upcomingList)
                } else if (mBinding.tabs.selectedTabPosition == 1)
                    surveyFragment?.setAdapter(surveyList)
                else if (mBinding.tabs.selectedTabPosition == 2)
                    overDueFragment?.setAdapter(endList)
                else if (mBinding.tabs.selectedTabPosition == 3)
                    overDueFragment?.setAdapter(endCompletedList)

            }
        }
        dashboardViewModel.surveysObserver.observe(viewLifecycleOwner) {
            Log.e("Response Surveys", " " + it.data?.size)
            lifecycleScope.launch(Dispatchers.Main) {
                surveySet(it.data, it.studentSurveyResponses)
                dashboardViewModel.showLoading.value = false
            }
        }
        dashboardViewModel.webinarObserver.observe(viewLifecycleOwner) {
            Log.e("Response Webinar", " " + it.data?.size)
            lifecycleScope.launch(Dispatchers.Main) {
                dashboardViewModel.showLoading.value = false
            }
        }

    }

    private fun listing() {
        SharedHelper(requireContext()).id?.let {
            dashboardViewModel.getOverDueCompleted(
                "Bearer " + SharedHelper(requireContext()).authkey,
                it
            )
        }
        dashboardViewModel.getSurveys(
            "Bearer " + SharedHelper(requireContext()).authkey,
            ML_URL + "v2/surveys"
        )

        dashboardViewModel.getWebinar(
            "Bearer " + SharedHelper(requireContext()).authkey,
            "${ML_URL}v1/university-fairs/registered?fields=webinar,webinar.uuid,webinar.topic,webinar.university_name,webinar.university_introduction,webinar.session_type,webinar.program,chosen_university,webinar.external_registration,uuid,join_url,webinar.end_time,webinar.start_time&limit=50&offset=0&order_by=ASC&show=upcoming&sort_by=webinar:start_time&webinar:session_delivery=live&webinar:status=published"
        )
    }

    private fun surveySet(data: List<SurveyDataItem?>?, survey: JsonObject?) {
        var assignment = ArrayList<DashboardOverdueResponse.AssignmentItem>()
        // var noDueList = arrayListOf<SortedDateModel>()
        if (data != null) {
            for (i in data.indices) {
                if ((data.get(i)?.endTime == null || data.get(i)?.endTime.equals("0"))) {
                    if (data.get(i)?.status == "active") {
                        var assignmentItem = DashboardOverdueResponse.AssignmentItem()
                        assignmentItem.date = null
                        assignmentItem.category = "Survey"
                        assignmentItem.body = data.get(i)?.title
                        assignmentItem.surveyQuestion = data.get(i)?.surveyQuestion
//                        assignmentItem.status=data.get(i)?.status
                        assignment.add(assignmentItem)
                        Log.e("survey list size", " " + assignmentItem.body)
                    }
                } else if (data.get(i)?.status == "closed" && (data.get(i)?.assignedWithUser?.contains(
                        SharedHelper(requireContext()).uuid
                    )) ?: false
                ) {
                    val jobj: JsonObject? = survey?.get(data.get(i)?.uuid) as JsonObject?
                    if (jobj?.get("response_status").toString()?.replace("\"", "") == "completed") {
                        var assignmentItem = DashboardOverdueResponse.AssignmentItem()
                        assignmentItem.date = data.get(i)?.endTime
                        assignmentItem.category = "Survey"
                        assignmentItem.body = data.get(i)?.title
                        assignmentItem.surveyQuestion = data.get(i)?.surveyQuestion
//                        assignmentItem.status=data.get(i)?.status
                        assignment.add(assignmentItem)
                        Log.e("survey list size", " " + assignmentItem.body)
                    }
                }
            }
            // noDueList.add(SortedDateModel("No Due Date", assignment))

        }

        val dateTimeFormatter: DateTimeFormatter =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter.ofPattern("E dd MMM, yyyy")
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        //  var nodueList = assignmentList.filter { it.date.isNullOrEmpty() }

        Log.e("survey list size", " " + assignment.size)
        assignment.sortBy {
            it.date?.toLong()?.let { it1 ->
                LocalDate.parse(
                    getDate(
                        it1,
                        "E dd MMM, yyyy"
                    ), dateTimeFormatter
                )
            }

        }

        var mappedList = assignment.groupBy {
            it.date?.toLong()?.let { it1 ->
                getDate(
                    it1,
                    "E dd MMM, yyyy"
                )
            }
        }

        surveyList.clear()
        var noDueList = arrayListOf<SortedDateModel>()
        Log.e("data ", "" + mappedList.size)
        mappedList.map {
            if (it.key != null) {
                surveyList.add(SortedDateModel(it.key.toString(), it.value))
            } else {
                noDueList.add(SortedDateModel("No Due Date", it.value))
            }
        }
        surveyList.addAll(noDueList)
    }


    private fun dataSet(dashboardOverdueResponse: DashboardOverdueResponse) {
        assignmentList =
            dashboardOverdueResponse.assignment as ArrayList<DashboardOverdueResponse.AssignmentItem>
        upcomingListWork()
        overDueListWork()
        completedListWork()
//        setAdapter()
    }

    private fun upcomingListWork() {
        val dateTimeFormatter: DateTimeFormatter =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter.ofPattern("E dd MMM, yyyy")
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        //  var nodueList = assignmentList.filter { it.date.isNullOrEmpty() }
        var upcoming =
            assignmentList.filter {
                it.status == 0 && it.completed != 1 && (it.date == null || (it.date != null && compareDate(
                    it.date?.toLong()
                        ?.let { it1 -> getDate(it1, "E dd MMM, yyyy") })))
            } as ArrayList<DashboardOverdueResponse.AssignmentItem>

        Log.e("upcoming list size", " " + upcoming.size)
        upcoming.sortBy {
            it.date?.toLong()?.let { it1 ->
                LocalDate.parse(
                    getDate(
                        it1,
                        "E dd MMM, yyyy"
                    ), dateTimeFormatter
                )
            }

        }

        var mappedList = upcoming.groupBy {
            it.date?.toLong()?.let { it1 ->
                getDate(
                    it1,
                    "E dd MMM, yyyy"
                )
            }
        }


        var noDueList = arrayListOf<SortedDateModel>()
        upcomingList.clear()
        mappedList.map {
            if (it.key != null) {
                upcomingList.add(SortedDateModel(it.key.toString(), it.value))
            } else {
                noDueList.add(SortedDateModel("No Due Date", it.value))
            }
        }
        // upcomingList.clear()
        upcomingList.addAll(noDueList)
        Log.e("data ", "" + upcomingList.size)
    }

    private fun compareDate(date: String?): Boolean {
        if (date != null) {
            val sdf = SimpleDateFormat("E dd MMM, yyyy")
            val strDate: Date = sdf.parse(date)
            if (strDate.after(Date())) {
                return true
            } else
                return false
        } else
            return true
    }

    private fun completedListWork() {
        var completedList =
            assignmentList.filter { it.completed == 1 } as ArrayList<DashboardOverdueResponse.AssignmentItem>

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

        endCompletedList.clear()
        var noDueList = arrayListOf<SortedDateModel>()

        mappedList.map {
            if (it.key != null) {
                endCompletedList.add(SortedDateModel(it.key.toString(), it.value))
            } else {
                noDueList.add(SortedDateModel("No Due Date", it.value))
            }
        }

        endCompletedList.addAll(noDueList)
        Log.e("endCompletedList ", "" + endCompletedList.size)
    }

    private fun overDueListWork() {
        var overdueList =
            assignmentList.filter { it.overdue == 1 } as ArrayList<DashboardOverdueResponse.AssignmentItem>

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


        endList.clear()
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
            getString(R.string.surveys),
            getString(R.string.overdue),
            getString(R.string.complete)
        )
        for (item in tabArray) {
            mBinding.tabs.addTab(mBinding.tabs.newTab().setText(item))
        }

//        val adapter = DashboardPagerAdapter(
//            requireContext(), this,
//            tabArray.size, dashboardViewModel, endList, endCompletedList
//        )
        // mBinding.viewPager.adapter = adapter

        mBinding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        upcomingFragment =
                            UpcomingFragment(dashboardViewModel, upcomingList, ::click)
                        loadFragment(upcomingFragment)
                    }
                    1 -> {
                        surveyFragment = SurveyFragment(dashboardViewModel, surveyList, ::click)
                        loadFragment(surveyFragment)
                    }
                    2 -> {
                        overDueFragment = OverDueFragment(dashboardViewModel, endList, ::click)
                        loadFragment(overDueFragment)
                    }
                    3 -> {
                        overDueFragment =
                            OverDueFragment(dashboardViewModel, endCompletedList, ::click)
                        loadFragment(overDueFragment)
                    }
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

    private fun loadFragment(fragment: Fragment?) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        fragment?.let { transaction.add(R.id.host_nav, it) }
        transaction.commit()
    }


    companion object {
        const val COMPLETE = "complete"
        const val RESET = "reset"
    }
}