package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.VarsityLayoutBinding
import com.maialearning.model.CollegeFactSheetModel
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.VarsityAdapter


class VarsityFragment  : Fragment() {
    private lateinit var mBinding: VarsityLayoutBinding
    var model: CollegeFactSheetModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = VarsityLayoutBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        init()
    }

    private fun init() {
        mBinding.aa.visibility=View.INVISIBLE
        mBinding.ma.visibility=View.INVISIBLE
        mBinding.dda.visibility=View.INVISIBLE
        mBinding.ba.text="Men"
        mBinding.da.text="Women"
        model = (context as UniversitiesActivity).getData()
        if (model != null) {
            val sortedList: MutableList<CollegeFactSheetModel.VarsityAthleticSports1.Teams1> =
                model?.varsityAthleticSports1?.teams!!.groupBy { it.name }
                    .values
                    .map {
                        it.reduce {
                                acc, item -> CollegeFactSheetModel.VarsityAthleticSports1.Teams1(item.name,item.values)
                        }
                    }.sortedWith(compareBy { it.name }).toMutableList()
            mBinding.recyclerView.adapter = VarsityAdapter(sortedList)

        }
    }
}
