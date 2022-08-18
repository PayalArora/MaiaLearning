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
import android.widget.*
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
import com.maialearning.ui.adapter.ConsiderAdapter
import com.maialearning.util.*
import com.maialearning.util.prefhandler.SharedHelper
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
    var assignmentList = arrayListOf<AssignmentItem>()
    var endList = arrayListOf<SortedDateModel>()
    var endCompletedList = arrayListOf<SortedDateModel>()
    var upcomingList = arrayListOf<SortedDateModel>()
    var surveyList = arrayListOf<SortedDateModel>()
    private var upcomingFragment: UpcomingFragment? = null
    private var surveyFragment: SurveyFragment? = null
    private var overDueFragment: OverDueFragment? = null
    private lateinit var toolbarBinding: Toolbar
    private lateinit var applicationFilterBinding: ApplicationFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DashbordFragBinding.inflate(inflater, container, false)

        toolbarBinding = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbarBinding.title = getString(R.string.dashboard)
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.GONE
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility = View.VISIBLE
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: DateFilterBinding = DateFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        val appFilter = showApplicationFilter(dialog)

        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).setOnClickListener {
            if (!dialog.isShowing)
                dialog.show()
            handleRadioButtonVisibility(sheetBinding)
            sheetBinding.close.setOnClickListener { dialog.dismiss() }
            sheetBinding.applictaion.setOnClickListener {
                if (!appFilter.isShowing) {
                    handleApplicationRadioButtons(applicationFilterBinding)
                    appFilter.show()
                }
            }
            sheetBinding.rbThisWeek.setOnClickListener {
                val new_list = upcomingList.filter {
                    it.date != "No Due Date" && compareDateWeek(it.date,
                        currentWeekDays().split(" - ")[0], currentWeekDays().split(" - ")[1])
                } as ArrayList<SortedDateModel>
                upcomingFragment?.setAdapter(new_list)
                if (dialog.isShowing)
                    dialog.dismiss()
            }

            sheetBinding.rbNextWeek.setOnClickListener {
                val new_list = upcomingList.filter {
                    it.date != "No Due Date" && compareDateWeek(it.date,
                        getNextWeek().split(" - ")[0], getNextWeek().split(" - ")[1])
                } as ArrayList<SortedDateModel>
                upcomingFragment?.setAdapter(new_list)
                if (dialog.isShowing)
                    dialog.dismiss()
            }
            sheetBinding.rbThisMnth.setOnClickListener {
                val new_list = upcomingList.filter {
                    it.date != "No Due Date" && compareDateWeek(it.date,
                        getCurrentMonth().split(" - ")[0], getCurrentMonth().split(" - ")[1])
                } as ArrayList<SortedDateModel>
                upcomingFragment?.setAdapter(new_list)
                if (dialog.isShowing)
                    dialog.dismiss()
            }
            sheetBinding.rbNextMonth.setOnClickListener {
                println("nxt" + getNextMonth())
                val new_list = upcomingList.filter {
                    it.date != "No Due Date" && compareDateWeek(it.date,
                        getNextMonth().split(" - ")[0], getNextMonth().split(" - ")[1])
                } as ArrayList<SortedDateModel>
                upcomingFragment?.setAdapter(new_list)
                if (dialog.isShowing)
                    dialog.dismiss()
            }

            sheetBinding.rbUpcoming.setOnClickListener {
                upcomingFragment?.setAdapter(upcomingList)
                if (dialog.isShowing)
                    dialog.dismiss()
            }

        }
        // setAdapter()


        return mBinding.root

    }

    private fun handleRadioButtonVisibility(sheetBinding: DateFilterBinding) {
        when (mBinding.tabs.selectedTabPosition) {
            0 -> {
                sheetBinding.radioGroupDate.visibility = View.VISIBLE
            }
            else ->
                sheetBinding.radioGroupDate.visibility = View.GONE
        }
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

    fun showApplicationFilter(dialog1: BottomSheetDialog): BottomSheetDialog {
        val dialog = BottomSheetDialog(requireContext())
        applicationFilterBinding =
            ApplicationFilterBinding.inflate(layoutInflater)
        applicationFilterBinding.root.minimumHeight =
            ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(applicationFilterBinding.root)
        applicationFilterBinding.backBtn.setOnClickListener {
            if (dialog.isShowing)
                dialog.dismiss()
        }

        applicationFilterBinding.radioApplication.setOnCheckedChangeListener { group, checkedId ->
            if (dialog.isShowing)
                dialog.dismiss()
            if (dialog1.isShowing)
                dialog1.dismiss()
        }
        return dialog
    }

    private fun handleApplicationRadioButtons(sheetBinding: ApplicationFilterBinding) {
        when (mBinding.tabs.selectedTabPosition) {
            3 -> {
                sheetBinding.rbFairs.visibility = View.GONE
                sheetBinding.rbMeetings.visibility = View.GONE
                sheetBinding.rbAssignments.visibility = View.GONE
                sheetBinding.rbApp.visibility = View.GONE
                sheetBinding.rbAllAssignments.visibility = View.VISIBLE
                sheetBinding.rbSurveys.visibility = View.VISIBLE

            }
            2 -> {

                sheetBinding.rbFairs.visibility = View.GONE
                sheetBinding.rbMeetings.visibility = View.GONE
                sheetBinding.rbSurveys.visibility = View.GONE
                sheetBinding.rbApp.visibility = View.GONE
                sheetBinding.rbAllAssignments.visibility = View.VISIBLE
                sheetBinding.rbAssignments.visibility = View.VISIBLE
            }
            else -> {
                sheetBinding.rbFairs.visibility = View.VISIBLE
                sheetBinding.rbMeetings.visibility = View.VISIBLE
                sheetBinding.rbSurveys.visibility = View.VISIBLE
                sheetBinding.rbApp.visibility = View.VISIBLE
                sheetBinding.rbAllAssignments.visibility = View.VISIBLE
                sheetBinding.rbAssignments.visibility = View.VISIBLE
            }
        }

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
        var assignment = ArrayList<AssignmentItem>()
        // var noDueList = arrayListOf<SortedDateModel>()
        if (data != null) {
            for (i in data.indices) {
                if ((data.get(i)?.endTime == null || data.get(i)?.endTime.equals("0"))) {
                    if (data.get(i)?.status == "active") {
                        var assignmentItem = AssignmentItem()
                        assignmentItem.date = null
                        assignmentItem.category = "Survey"
                        assignmentItem.body = data.get(i)?.title
                        assignment.add(assignmentItem)
                        Log.e("survey list size", " " + assignmentItem.body)
                    }
                } else if (data.get(i)?.status == "closed" && (data.get(i)?.assignedWithUser?.contains(
                        SharedHelper(requireContext()).uuid
                    )) ?: false
                ) {
                    val jobj: JsonObject? = survey?.get(data.get(i)?.uuid) as JsonObject?
                    if (jobj?.get("response_status").toString()?.replace("\"", "") == "completed") {
                        var assignmentItem = AssignmentItem()
                        assignmentItem.date = data.get(i)?.endTime
                        assignmentItem.category = "Survey"
                        assignmentItem.body = data.get(i)?.title
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
        assignmentList = dashboardOverdueResponse.assignment as ArrayList<AssignmentItem>
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
//        it.status == 0 &&
        var upcoming =
            assignmentList.filter {
                it.completed != 1 && (it.date == null || (it.date != null && compareDate(
                    it.date?.toLong()
                        ?.let { it1 -> getDate(it1, "E dd MMM, yyyy") })))
            } as ArrayList<AssignmentItem>

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
        upcomingList.addAll(noDueList)
        Log.e("data upcoming", "" + upcomingList.size)
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
            return false
    }

    private fun completedListWork() {
        var completedList = assignmentList.filter { it.completed == 1 } as ArrayList<AssignmentItem>

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
                        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility =
                            View.VISIBLE
                        upcomingFragment =
                            UpcomingFragment(dashboardViewModel, upcomingList, ::click)
                        loadFragment(upcomingFragment)
                    }
                    1 -> {
                        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility =
                            View.GONE
                        surveyFragment = SurveyFragment(dashboardViewModel, surveyList, ::click)
                        loadFragment(surveyFragment)
                    }
                    2 -> {
                        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility =
                            View.VISIBLE
                        overDueFragment = OverDueFragment(dashboardViewModel, endList, ::click)
                        loadFragment(overDueFragment)
                    }
                    3 -> {
                        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility =
                            View.VISIBLE
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