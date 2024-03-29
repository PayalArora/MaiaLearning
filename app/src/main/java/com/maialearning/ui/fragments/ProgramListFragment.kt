package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.internal.LinkedTreeMap
import com.maialearning.databinding.ProgramDetailEuropeanSheetBinding
import com.maialearning.databinding.ProgramDetailSheetBinding
import com.maialearning.databinding.ProgramListLayoutBinding
import com.maialearning.model.*
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.EntryRequirementAdapter
import com.maialearning.ui.adapter.FeesAdapter
import com.maialearning.ui.adapter.ProgramListAdapter
import com.maialearning.ui.adapter.ProgramListAdapterEuropean
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.FactSheetModel
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.URLEncoder


class ProgramListFragment : Fragment(), OnClickOption {
    private lateinit var mBinding: ProgramListLayoutBinding
    var modelOther: FactsheetModelOther? = null
    private val mModel: FactSheetModel by viewModel()
    private  var dialogP: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = ProgramListLayoutBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.programList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        dialogP = context?.let { showLoadingDialog(it) }
        initData()
    }

    private fun initData() {

        modelOther = (context as UniversitiesActivity).getDataOther()
        if (modelOther != null && modelOther?.course_list != null) {
            if (modelOther?.course_list.toString().startsWith("{")) {
//                val response_array: List<MyClass> = Gson().fromJson(
//                    questions as JsonArray?,
//                    object : TypeToken<List<MyClass?>?>() {}.type
//                )

                // if response type is object

                // if response type is object
                val gson = GsonBuilder().create()
                val jsonObject =
                    gson.toJsonTree(modelOther?.course_list as LinkedTreeMap<String, String>).asJsonObject

                //val jsonObject = JSONTokener(modelOther?.course_list).nextValue() as JSONObject
                val courses = JSONObject(jsonObject.toString())
                val courseObj = courses.optJSONObject("course_list")
                val iteratorObj: Iterator<*> = courseObj.keys()
                var courseList: ArrayList<CourseListModel> =
                    ArrayList<CourseListModel>()
                while (iteratorObj.hasNext()) {
                    val getJsonObj = iteratorObj.next() as String
                    val json = courseObj.optJSONObject(getJsonObj)
                    var course = CourseListModel()
                    course.aLevel = json.optString("a_level")
                    course.courseId = json.optString("course_id")
                    course.optionCount = json.optString("option_count")
                    course.ib = json.optString("ib")
                    course.courseName = json.optString("course_name")

                    var courseSubList = ArrayList<CourseListOptionModel>()
                    val subJson = json.optJSONObject("course_option_list")
                    val iteratorSubObj: Iterator<*> = subJson.keys()
                    while (iteratorSubObj.hasNext()) {
                        val getSubJsonObj = iteratorSubObj.next() as String
                        val jsonSub = subJson.optJSONObject(getSubJsonObj)
                        var courseOption = CourseListOptionModel()
                        courseOption.courseOptionId = jsonSub.optString("course_option_id")
                        courseOption.outcomeQualification =
                            jsonSub.optString("OutcomeQualification")
                        courseOption.studyMode = jsonSub.optString("StudyMode")
                        courseOption.startDate = jsonSub.optString("StartDate")
                        courseOption.ibScore = jsonSub.optString("ib_score")
                        courseOption.alevels = jsonSub.optString("alevels")
                        courseOption.durationValue = jsonSub.optString("DurationValue")
                        courseOption.durationType = jsonSub.optString("DurationType")
                        courseOption.locationName = jsonSub.optString("LocationName")
                        courseSubList.add(courseOption)
                    }
                    course.courseOptionList = courseSubList
                    courseList.add(course)
                }
                courseList.sortBy { it.courseName }
                mBinding.head1.visibility = View.GONE
                mBinding.programList.adapter = ProgramListAdapter(
                    requireContext(),
                    courseList, this
                )
            } else if (modelOther?.course_list.toString().startsWith("[")){
                val gson = GsonBuilder().create()
                val jsonArray = gson.toJsonTree(modelOther?.course_list as ArrayList<*>) as JsonArray
                val courses = JSONArray(jsonArray.toString())
                var programList: ArrayList<ProgramListFactSheet> =
                    ArrayList<ProgramListFactSheet>()
                for (i in 0 until  courses.length()){
                    val course: JSONObject? = courses.optJSONObject(i)
                    course?.let {
                        val prog = ProgramListFactSheet(it.optString("program_name"), it.optString("duration"), it.optString("tuition_fee"),  it.optString("program_id") )
                        programList.add(prog)
                    }

                }
                programList.sortBy { it.programName }
                mBinding.head1.visibility = View.VISIBLE
                mBinding.programList.adapter = ProgramListAdapterEuropean(
                    requireContext(),
                    programList, ::click
                )
            }
        }

        mModel.programDetailObserber.observe(viewLifecycleOwner) {
            Log.e("Detail", it.basicInfo.toString())
            val dialog = BottomSheetDialog(requireContext())
//            val sheetBinding= ProgramDetailSheetBinding.inflate(inflater, container, false)
            val sheetBinding: ProgramDetailSheetBinding =
                ProgramDetailSheetBinding.inflate(layoutInflater)
            sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
            dialog.setContentView(sheetBinding.root)
            dialog?.show()
            sheetBinding.close.setOnClickListener {
                dialog.dismiss()
            }
            sheetBinding.backBtn.setOnClickListener {
                dialog.dismiss()
            }
            sheetBinding.descriptionText.setText(it.basicInfo?.description)
            sheetBinding.locationTxt.setText(it.basicInfo?.location)
            sheetBinding.dateTxt.setText(it.basicInfo?.startDate)
            sheetBinding.qualTxt.setText(it.basicInfo?.qualification)
            sheetBinding.durationTxt.setText(it.basicInfo?.duration + "/" + it.basicInfo?.studyMode)
            sheetBinding.courseCode.setText(it.howToApply?.courseCode)
            sheetBinding.instituteCode.setText(it.howToApply?.providerCode)
            sheetBinding.campusName.setText(it.howToApply?.locationName)
            sheetBinding.entryList.layoutManager = GridLayoutManager(requireContext(), 2)
            sheetBinding.entryList.adapter =
                EntryRequirementAdapter(it.entryRequirement?.qualificationTable as ArrayList<QualificationTableItem>)
            sheetBinding.feesList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            sheetBinding.feesList.adapter =
                FeesAdapter(it.feesFunding?.tuitionFees as ArrayList<TuitionFeesItem>)

        }
        mModel.progDetailObserverEurope.observe(viewLifecycleOwner){
            dialogP?.dismiss()
            if (it.size() ==1 ){
                val dialog = BottomSheetDialog(requireContext())
                val sheetBinding: ProgramDetailEuropeanSheetBinding =
                    ProgramDetailEuropeanSheetBinding.inflate(layoutInflater)
                sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
                dialog.setContentView(sheetBinding.root)
                dialog?.show()
                sheetBinding.close.setOnClickListener {
                    dialog.dismiss()
                }
                sheetBinding.backBtn.setOnClickListener {
                    dialog.dismiss()
                }
                sheetBinding.webView.loadDataWithBaseURL(
                    "",
                    it.get(0).toString(),
                    "text/html",
                    "utf-8",
                    ""
                )
            }
        }
    }

    override fun onViewClick(mainPostion: Int, courseListOptionModel: CourseListOptionModel) {
        courseListOptionModel.courseOptionId?.let { mModel.getProgramsDetail(it) }
    }
    fun click(i: String) {
        dialogP?.show()
        mModel.programDetails(i)
    }

}


interface OnClickOption {
    fun onViewClick(mainPostion: Int, courseListOptionModel: CourseListOptionModel)
}