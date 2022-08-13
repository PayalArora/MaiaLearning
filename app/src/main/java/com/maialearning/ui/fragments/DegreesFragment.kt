package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.DegreeFragmentBinding
import com.maialearning.model.CollegeFactSheetModel
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.DegreeAdapter
import com.maialearning.viewmodel.FactSheetModel
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class DegreesFragment : Fragment() {
    private val mModel: FactSheetModel by viewModel()
    private lateinit var mBinding: DegreeFragmentBinding
    var model: CollegeFactSheetModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = DegreeFragmentBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

//        mModel.getColFactSheet(
//            "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
//            "222178"
//        )
//        observer()
        init()
    }

    private fun init() {
        model = (context as UniversitiesActivity).getData()
        if (model != null) {
            val sortedList: MutableList<CollegeFactSheetModel.DegreeMajors1.Majors1> =
                model?.degreeMajors1?.majors!!.groupBy { it.name }
                    .values
                    .map {
                        it.reduce {
                                acc, item -> CollegeFactSheetModel.DegreeMajors1.Majors1(item.name,item.agriculturalBusinessAndManagement)
                        }
                    }.sortedWith(compareBy { it.name }).toMutableList()
            mBinding.recyclerView.adapter = DegreeAdapter(sortedList)

        }
    }

    fun observer() {
        mModel.factSheetObserver.observe(requireActivity()) {
            val array: ArrayList<CollegeFactSheetModel.DegreeMajors1.Majors1> = ArrayList()
            val json =
                JSONObject(it.toString()).getJSONObject("degree_majors").getJSONObject("majors")
            var keyList = ArrayList<String>()
            val x = json.keys() as Iterator<String>
            val jsonArray = JSONArray()
            while (x.hasNext()) {
                val key: String = x.next().toString()
                jsonArray.put(json.get(key))
                keyList.add(key)
            }
            for (i in 0 until jsonArray.length()) {
                val objectProgram = jsonArray.getJSONObject(i)
                array.add(
                    CollegeFactSheetModel.DegreeMajors1.Majors1(
                        keyList.get(i), CollegeFactSheetModel.DegreeMajors1.Majors1.AnimalSciences(
                            objectProgram.getInt("Associate Degree"),
                            objectProgram.getInt("Master Degree"),
                            objectProgram.getInt("Bachelor Degree"),
                            objectProgram.getInt("Doctorate Degree"),
                            objectProgram.getInt("count"),
                            ""
                        )
                    )
                )

            }
            mBinding.recyclerView.adapter = DegreeAdapter(array)
        }

    }
}
