package com.maialearning.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.ProgramListLayoutBinding
import com.maialearning.model.CourseListModel
import com.maialearning.model.CourseListOptionModel
import com.maialearning.model.FactsheetModelOther
import com.maialearning.model.UkResponseModel
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.CoursesAdapter
import com.maialearning.ui.adapter.ProgramListAdapter
import org.json.JSONObject

class ProgramListFragment : Fragment() {
    private lateinit var mBinding: ProgramListLayoutBinding
    var modelOther: FactsheetModelOther? = null
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

        initData()

    }

    private fun initData() {

        modelOther = (context as UniversitiesActivity).getDataOther()
        if (modelOther != null && modelOther?.course_list != null) {
            val courses = JSONObject(modelOther?.course_list.toString())
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
                    courseOption.outcomeQualification = jsonSub.optString("OutcomeQualification")
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
            mBinding.programList.adapter = ProgramListAdapter(
                requireContext(),
                courseList
            )
        }
    }
}