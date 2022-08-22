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


class DegreesFragment : Fragment() {
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
}
