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
    var assignmentList = arrayListOf<DashboardOverdueResponse.AssignmentItem>()
    var endList = arrayListOf<SortedDateModel>()
    var filteredUpcomingList = arrayListOf<SortedDateModel>()
    var endCompletedList = arrayListOf<SortedDateModel>()
    var upcomingList = arrayListOf<SortedDateModel>()
    var surveyList = arrayListOf<SortedDateModel>()
    var webinarList = arrayListOf<WebinarDataItem?>()
    var surveyListUpcoming = arrayListOf<DashboardOverdueResponse.AssignmentItem>()
    var surveyListCompleted = arrayListOf<DashboardOverdueResponse.AssignmentItem>()
    private var upcomingFragment: UpcomingFragment? = null
    private var surveyFragment: SurveyFragment? = null
    private var overDueFragment: OverDueFragment? = null
    private lateinit var toolbarBinding: Toolbar
    private lateinit var applicationFilterBinding: ApplicationFilterBinding
    var selectedApplication = "All Assignments"
    var selectedAppType= "All"
    var selectedTimeType= 0

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
                var new_list = getAppTypeData(selectedAppType, filteredUpcomingList)
                selectedTimeType = 1
                new_list = handleTimeFiter(selectedTimeType,new_list)


//                val new_list = filteredUpcomingList.filter {
//                    it.date != "No Due Date" && compareDateWeek(it.date,
//                        currentWeekDays().split(" - ")[0], currentWeekDays().split(" - ")[1])
//                } as ArrayList<SortedDateModel>
                upcomingFragment?.setAdapter(new_list)
              //  filteredUpcomingList = new_list
                Log.e("rbThisWeek ", " " + new_list.size)
                if (dialog.isShowing)
                    dialog.dismiss()
            }

            sheetBinding.rbNextWeek.setOnClickListener {
                var new_list = getAppTypeData(selectedAppType, filteredUpcomingList)
                selectedTimeType = 2
                new_list = handleTimeFiter(selectedTimeType,new_list)
//                val new_list = filteredUpcomingList.filter {
//                    it.date != "No Due Date" && compareDateWeek(it.date,
//                        getNextWeek().split(" - ")[0], getNextWeek().split(" - ")[1])
//                } as ArrayList<SortedDateModel>
              //  filteredUpcomingList = new_list
                upcomingFragment?.setAdapter(new_list)
                if (dialog.isShowing)
                    dialog.dismiss()
            }
            sheetBinding.rbThisMnth.setOnClickListener {
                var new_list = getAppTypeData(selectedAppType, filteredUpcomingList)
//                 new_list = new_list.filter {
//                    it.date != "No Due Date" && compareDateWeek(it.date,
//                        getCurrentMonth().split(" - ")[0], getCurrentMonth().split(" - ")[1])
//                } as ArrayList<SortedDateModel>
                selectedTimeType = 3
                new_list = handleTimeFiter(selectedTimeType,new_list)
                  //  filteredUpcomingList = new_list
                upcomingFragment?.setAdapter(new_list)

                if (dialog.isShowing)
                    dialog.dismiss()
            }
            sheetBinding.rbNextMonth.setOnClickListener {
                println("nxt" + getNextMonth())
                var new_list = getAppTypeData(selectedAppType, filteredUpcomingList)
                selectedTimeType = 4
                new_list = handleTimeFiter(selectedTimeType,new_list)

//                val new_list = filteredUpcomingList.filter {
//                    it.date != "No Due Date" && compareDateWeek(it.date,
//                        getNextMonth().split(" - ")[0], getNextMonth().split(" - ")[1])
//                } as ArrayList<SortedDateModel>
              //  filteredUpcomingList = new_list
                upcomingFragment?.setAdapter(new_list)
                if (dialog.isShowing)
                    dialog.dismiss()
            }

            sheetBinding.rbUpcoming.setOnClickListener {
              //  filteredUpcomingList = upcomingList
                //upcomingFragment?.setAdapter(upcomingList)
                var new_list = getAppTypeData(selectedAppType, filteredUpcomingList)
                selectedTimeType = 0
                new_list = handleTimeFiter(selectedTimeType,new_list)
                upcomingFragment?.setAdapter(new_list)
                if (dialog.isShowing)
                    dialog.dismiss()
            }
            sheetBinding.applictaion.text = selectedApplication

        }
        // setAdapter()


        return mBinding.root

    }

    fun handleTimeFiter( selectedAppType: Int,filteredUpcomingList:ArrayList<SortedDateModel>):ArrayList<SortedDateModel>{
        var new_list = arrayListOf<SortedDateModel>()
        if (selectedAppType ==3 ){
            new_list = filteredUpcomingList.filter {
                it.date != "No Due Date" && compareDateWeek(it.date,
                    getCurrentMonth().split(" - ")[0], getCurrentMonth().split(" - ")[1])
            } as ArrayList<SortedDateModel>

        } else if (selectedAppType == 1){
            new_list = filteredUpcomingList.filter {
                it.date != "No Due Date" && compareDateWeek(it.date,
                    currentWeekDays().split(" - ")[0], currentWeekDays().split(" - ")[1])
            } as ArrayList<SortedDateModel>
        }else if (selectedAppType == 2){
            new_list = filteredUpcomingList.filter {
                it.date != "No Due Date" && compareDateWeek(it.date,
                    getNextWeek().split(" - ")[0], getNextWeek().split(" - ")[1])
            } as ArrayList<SortedDateModel>
        }else if (selectedAppType == 4){
            new_list =  filteredUpcomingList.filter {
                it.date != "No Due Date" && compareDateWeek(it.date,
                    getNextMonth().split(" - ")[0], getNextMonth().split(" - ")[1])
            } as ArrayList<SortedDateModel>
        }else if (selectedAppType == 0){
            new_list =  filteredUpcomingList
        }
        return new_list
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
            REFRESH->{
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
        applicationFilterBinding.rbSurveys.setOnClickListener {
             selectedApplication = context?.getString(R.string.survey).toString()
            selectedAppType=  "Survey"
            when (mBinding.tabs.selectedTabPosition) {
                3 -> {
                    val new_list = arrayListOf<SortedDateModel>()
                    for (it in endCompletedList){
                        val assignment = it.assignment?.filter { it?.category == "Survey" }
                        if (assignment?.size?:0>0) {
                            val model = SortedDateModel(it.date, assignment)
                            new_list.add(model)
                        }
                    }
                    overDueFragment?.setAdapter(new_list)

                    if (dialog.isShowing)
                        dialog.dismiss()
                    if (dialog1.isShowing)
                        dialog1.dismiss()
                }
//                0 -> {
//                 val new_list = arrayListOf<SortedDateModel>()
//                    for (it in filteredUpcomingList){
//                        val assignment = it.assignment?.filter { it?.category == "Survey" }
//                        if (assignment?.size?:0>0) {
//                            val model = SortedDateModel(it.date, assignment)
//                            new_list.add(model)
//                        }
//                    }
//                    upcomingFragment?.setAdapter(new_list)
//                    if (dialog.isShowing)
//                        dialog.dismiss()
//                    if (dialog1.isShowing)
//                        dialog1.dismiss()
//                }

            }
            handleUpcomingListApplicationFilters(dialog1,dialog,"Survey",filteredUpcomingList)

        }
        applicationFilterBinding.rbAssignments.setOnClickListener {
            selectedApplication = context?.getString(R.string.assignment).toString()
            selectedAppType=  "Assignments"
            when (mBinding.tabs.selectedTabPosition) {
                3 -> {
                    val new_list = arrayListOf<SortedDateModel>()
                    for (it in endCompletedList) {
                        val assignment = it.assignment?.filter { it?.category != "Survey" && it?.category != "Webinar" && it?.category != "Applications" }
                        if (assignment?.size ?: 0 > 0) {
                            val model = SortedDateModel(it.date, assignment)
                            new_list.add(model)
                        }
                    }
                    overDueFragment?.setAdapter(new_list)
                    if (dialog.isShowing)
                        dialog.dismiss()
                    if (dialog1.isShowing)
                        dialog1.dismiss()
                }

//                0 -> {
//                    val new_list = arrayListOf<SortedDateModel>()
//                    for (it in filteredUpcomingList){
//                        val assignment = it.assignment?.filter { it?.category != "Survey"&& it?.category != "Webinar" && it?.category != "Applications"  }
//                        if (assignment?.size?:0>0) {
//                            val model = SortedDateModel(it.date, assignment)
//                            new_list.add(model)
//                        }
//                    }
//                   // filteredUpcomingList = new_list
//                    upcomingFragment?.setAdapter(new_list)
//                    if (dialog.isShowing)
//                        dialog.dismiss()
//                    if (dialog1.isShowing)
//                        dialog1.dismiss()
//                }
            }
            handleUpcomingListApplicationFilters(dialog1,dialog,selectedApplication,filteredUpcomingList)
        }
        applicationFilterBinding.rbAllAssignments.setOnClickListener {
            selectedApplication = context?.getString(R.string.all_assignments).toString()
            selectedAppType ="All"
            when (mBinding.tabs.selectedTabPosition) {
                3 -> {
                    overDueFragment?.setAdapter(endCompletedList)
                    if (dialog.isShowing)
                        dialog.dismiss()
                }
            0 -> {
                handleUpcomingListApplicationFilters(dialog1,dialog,"All",filteredUpcomingList)
        }}
    }
        applicationFilterBinding.rbFairs.setOnClickListener {
            selectedApplication = context?.getString(R.string.fairs).toString()
            selectedAppType=  "Webinar"
//            when (mBinding.tabs.selectedTabPosition) {
//                0 -> {
//                    val new_list = arrayListOf<SortedDateModel>()
//                    for (it in filteredUpcomingList){
//                        val assignment = it.assignment?.filter { it?.category == "Webinar"}
//                        if (assignment?.size?:0>0) {
//                            val model = SortedDateModel(it.date, assignment)
//                            new_list.add(model)
//                        }
//                    }
//                    //filteredUpcomingList = new_list
//                    upcomingFragment?.setAdapter(new_list)
//
//                    if (dialog.isShowing)
//                        dialog.dismiss()
//                    if (dialog1.isShowing)
//                        dialog1.dismiss()
//                }
//            }
            handleUpcomingListApplicationFilters(dialog1,dialog,"Webinar",filteredUpcomingList)
        }
        applicationFilterBinding.rbApp.setOnClickListener {
            selectedApplication = context?.getString(R.string.Applications).toString()
            selectedAppType=  selectedApplication
//            when (mBinding.tabs.selectedTabPosition) {
//                0 -> {
//                    val new_list = arrayListOf<SortedDateModel>()
//                    for (it in filteredUpcomingList){
//                        val assignment = it.assignment?.filter { it?.category == "Applications"}
//                        if (assignment?.size?:0>0) {
//                            val model = SortedDateModel(it.date, assignment)
//                            new_list.add(model)
//                        }
//                    }
//                  //  filteredUpcomingList = new_list
//                    upcomingFragment?.setAdapter(new_list)
//                    if (dialog.isShowing)
//                        dialog.dismiss()
//                    if (dialog1.isShowing)
//                        dialog1.dismiss()
//                }
//            }
            handleUpcomingListApplicationFilters(dialog1,dialog,selectedApplication,filteredUpcomingList)
        }
        applicationFilterBinding.rbMeetings.setOnClickListener {
            selectedApplication = context?.getString(R.string.meetings).toString()
            selectedAppType=  selectedApplication
            handleUpcomingListApplicationFilters(dialog1,dialog,selectedApplication,filteredUpcomingList)
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
                sheetBinding.rbAssignments.visibility = View.VISIBLE
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

    fun  handleUpcomingListApplicationFilters(dialog1: BottomSheetDialog, dialog: BottomSheetDialog, selectedAppType:String,
                                              filteredUpcomingList:ArrayList<SortedDateModel>){
        when (mBinding.tabs.selectedTabPosition) {
            0 -> {
                var new_list = getAppTypeData(selectedAppType, filteredUpcomingList)
                new_list = handleTimeFiter(selectedTimeType,new_list)
                //  filteredUpcomingList = new_list
                upcomingFragment?.setAdapter(new_list)
                if (dialog.isShowing)
                    dialog.dismiss()
                if (dialog1.isShowing)
                    dialog1.dismiss()
            }
        }
    }

    fun getAppTypeData(selectedAppType: String ,filteredUpcomingList:ArrayList<SortedDateModel>):ArrayList<SortedDateModel>{
        val new_list = arrayListOf<SortedDateModel>()
        if(selectedAppType == "All"){
         return filteredUpcomingList
        }
        for (it in filteredUpcomingList) {
            var assignment:List<DashboardOverdueResponse.AssignmentItem?>? = null
            if (selectedAppType == "Assignments" ){
                assignment = it.assignment?.filter {  it?.category != "Survey" && it?.category != "Webinar" && it?.category != "Applications"  }
            }
            else{
                assignment = it.assignment?.filter { it?.category == selectedAppType }
            }

            if (assignment?.size ?: 0 > 0) {
                val model = SortedDateModel(it.date, assignment)
                new_list.add(model)
            }
        }
        return new_list
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
                    filteredUpcomingList = upcomingList
                    upcomingFragment?.setAdapter(upcomingList)
                } else if (mBinding.tabs.selectedTabPosition == 1) {
                    surveyFragment?.setAdapter(surveyList)
                }
                else if (mBinding.tabs.selectedTabPosition == 2) {
                    overDueFragment?.setAdapter(endList)}
                else if (mBinding.tabs.selectedTabPosition == 3){
                    overDueFragment?.setAdapter(endCompletedList)
                }

            }
        }
        dashboardViewModel.surveysObserver.observe(viewLifecycleOwner) {
            Log.e("Response Surveys", " " + it.data?.size)
            lifecycleScope.launch(Dispatchers.Main) {
                SharedHelper(requireContext()).id?.let {
                    dashboardViewModel.getOverDueCompleted(
                        "Bearer " + SharedHelper(requireContext()).authkey,
                        it
                    )
                }
                surveySet(it.data, it.studentSurveyResponses)
              //  dashboardViewModel.showLoading.value = false
            }
        }
        dashboardViewModel.webinarObserver.observe(viewLifecycleOwner) {
            Log.e("Response Webinar", " " + it.data?.size)
            webinarList.clear()
            lifecycleScope.launch(Dispatchers.Main) {
              //  dashboardViewModel.showLoading.value = false
                 webinarList =   it.data?.filter { it1->(it1?.webinar != null &&  (it1.webinar.startTime != null && compareDate(
                    it1.webinar.startTime?.toLong()
                        ?.let { it1 -> getDate(it1, "E dd MMM, yyyy") })))
                } as ArrayList<WebinarDataItem?>
                Log.e("Response Webinar", " " + webinarList?.size)
                dashboardViewModel.getSurveys(
                    "Bearer " + SharedHelper(requireContext()).authkey,
                    ML_URL + "v2/surveys"
                )
            }

        }

    }

    private fun listing() {
        dashboardViewModel.getWebinar(
            "Bearer " + SharedHelper(requireContext()).authkey,
            "${ML_URL}v1/university-fairs/registered?fields=webinar,webinar.uuid,webinar.topic,webinar.university_name,webinar.university_introduction,webinar.session_type,webinar.program,chosen_university,webinar.external_registration,uuid,join_url,webinar.end_time,webinar.start_time&limit=50&offset=0&order_by=ASC&show=upcoming&sort_by=webinar:start_time&webinar:session_delivery=live&webinar:status=published"
        )

    }

    private fun surveySet(data: List<SurveyDataItem?>?, survey: JsonObject?) {
        var assignment = ArrayList<DashboardOverdueResponse.AssignmentItem>()
        surveyListUpcoming.clear()
        surveyListCompleted.clear()
        // var noDueList = arrayListOf<SortedDateModel>()
        if (data != null) {
            for (i in data.indices) {

                if (data.get(i)?.status == "active") {
                    if ((data.get(i)?.endTime == null || data.get(i)?.endTime.equals("0"))) {
                        val jobj: JsonObject? = survey?.get(data.get(i)?.uuid) as JsonObject?

                            var assignmentItem = DashboardOverdueResponse.AssignmentItem()
                        assignmentItem.date = null
                        assignmentItem.status = 1
                        if (jobj?.get("response_status").toString()?.replace("\"", "") != "completed") {
                            assignmentItem.completed = 0
                        } else
                        {
                                assignmentItem.completed = 1
                        }
                        assignmentItem.response_status = jobj?.get("response_status").toString()?.replaceInvertedComas()
                        assignmentItem.start_time =  data.get(i)?.startTime
                        assignmentItem.category = "Survey"
                        assignmentItem.body = data.get(i)?.title
                        assignmentItem.authorF = data.get(i)?.authorData?.data?.firstName
                        assignmentItem.authorL = data.get(i)?.authorData?.data?.lastName
                        assignmentItem.questionSize = data.get(i)?.surveyQuestion?.size.toString()
                        assignmentItem.description = data.get(i)?.description
                        assignmentItem.surveyQuestion = data.get(i)?.surveyQuestion
                        assignmentItem.categoryId = data.get(i)?.uuid
//                        assignmentItem.status=data.get(i)?.status
                        assignment.add(assignmentItem)
                        Log.e("survey list size", " " + assignmentItem.body)
                    //}
                } else {
                        val jobj: JsonObject? = survey?.get(data.get(i)?.uuid) as JsonObject?

                        var assignmentItem = DashboardOverdueResponse.AssignmentItem()
                        assignmentItem.date = data.get(i)?.endTime
                        assignmentItem.status = 1
                        if (jobj?.get("response_status").toString()?.replace("\"", "") != "completed") {
                            assignmentItem.completed = 0
                        } else
                        {
                            assignmentItem.completed = 1
                        }
                        assignmentItem.response_status = jobj?.get("response_status").toString()?.replaceInvertedComas()
                        assignmentItem.start_time =  data.get(i)?.startTime
                        assignmentItem.category = "Survey"
                        assignmentItem.authorF = data.get(i)?.authorData?.data?.firstName
                        assignmentItem.authorL = data.get(i)?.authorData?.data?.lastName
                        assignmentItem.questionSize = data.get(i)?.surveyQuestion?.size.toString()
                        assignmentItem.body = data.get(i)?.title
                        assignmentItem.description = data.get(i)?.description
                        assignmentItem.surveyQuestion = data.get(i)?.surveyQuestion
                        assignmentItem.categoryId = data.get(i)?.uuid
//                        assignmentItem.status=data.get(i)?.status
                        assignment.add(assignmentItem)
                }
                } else if (data.get(i)?.status == "closed") {
                    if ((data.get(i)?.endTime != null && !data.get(i)?.endTime.equals("0"))) {
                        val jobj: JsonObject? =
                            survey?.get(data.get(i)?.uuid?.replaceInvertedComas()) as JsonObject?
                        if (jobj?.get("response_status").toString()
                                ?.replace("\"", "") == "completed" || jobj?.get("response_status")
                                .toString()?.replace("\"", "") == "incomplete"
                        ) {
                            var assignmentItem = DashboardOverdueResponse.AssignmentItem()
                            assignmentItem.date = data.get(i)?.endTime
                            assignmentItem.start_time = data.get(i)?.startTime
                            assignmentItem.category = "Survey"
                            assignmentItem.categoryId = data.get(i)?.uuid
                            assignmentItem.status = 0
                            if (jobj?.get("response_status").toString()
                                    ?.replace("\"", "") != "completed"
                            ) {
                                assignmentItem.completed = 0
                            } else {
                                assignmentItem.completed = 1
                            }
                            assignmentItem.body = data.get(i)?.title
                            assignmentItem.description = data.get(i)?.description
                            assignmentItem.surveyQuestion = data.get(i)?.surveyQuestion
                            assignmentItem.authorF = data.get(i)?.authorData?.data?.firstName
                            assignmentItem.authorL = data.get(i)?.authorData?.data?.lastName
                            assignmentItem.questionSize = data.get(i)?.surveyQuestion?.size.toString()
                            assignmentItem.surveyQuestion = data.get(i)?.surveyQuestion
//                        assignmentItem.status=data.get(i)?.status
                            assignmentItem.response_status =
                                jobj?.get("response_status").toString()?.replaceInvertedComas()
                            assignment.add(assignmentItem)
                            Log.e("survey list size", " " + assignmentItem.body)
                            Log.e("survey list size",
                                " " + jobj?.get("response_status").toString()
                                    ?.replaceInvertedComas()
                            )
                        }
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
        Log.e("survey uuid", " " + SharedHelper(requireContext()).uuid)
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

      surveyListUpcoming =  assignment.filter { it.status==1 && it.completed == 0
        } as ArrayList<DashboardOverdueResponse.AssignmentItem>
        Log.e("survey list size", " " + surveyListUpcoming.size)

        surveyListCompleted =  assignment.filter {it.response_status == "completed"||it.response_status == "incomplete"
        } as ArrayList<DashboardOverdueResponse.AssignmentItem>
        Log.e("survey list size", " " + surveyListCompleted.size)
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

        for (i in webinarList){
            val assignment = DashboardOverdueResponse.AssignmentItem()
            assignment.date = i?.webinar?.startTime.toString()
            assignment.body = i?.webinar?.topic
            assignment.task = i?.webinar?.topic
            assignment.category = "Webinar"
            assignment.categoryId = i?.webinar?.uuid
            upcoming.add(assignment)
        }
        upcoming.addAll(surveyListUpcoming)


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
        completedList.addAll(surveyListCompleted)
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
                        val toolbarBinding: Toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
                        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility =
                            View.GONE
                        surveyFragment = SurveyFragment(dashboardViewModel, surveyList, ::click)
                        loadFragment(surveyFragment)
                    }
                    2 -> {
                        val toolbarBinding: Toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
                        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility =
                            View.VISIBLE
                        overDueFragment = OverDueFragment(dashboardViewModel, endList, ::click)
                        loadFragment(overDueFragment)
                    }
                    3 -> {
                        val toolbarBinding: Toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
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
        const val REFRESH = "refresh"
    }
}