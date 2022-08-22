package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.CampusLayoutBinding
import com.maialearning.model.CollegeFactSheetModel
import com.maialearning.model.SetServices
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.CampusAdapter

class CampusFragment : Fragment() {
    private lateinit var mBinding: CampusLayoutBinding
    var model: CollegeFactSheetModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding= CampusLayoutBinding.inflate(inflater,container,false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        mBinding.recyclerView.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)




    }
    private fun initData(){
        model = (context as UniversitiesActivity).getData()
        if (model != null) {
            val list=ArrayList<SetServices>()
            val conInner=ArrayList<String>()
            conInner.add(model?.services?.counselingServices!!.x0)
            conInner.add(model?.services?.counselingServices!!.x1)
            conInner.add(model?.services?.counselingServices!!.x2)
            conInner.add(model?.services?.counselingServices!!.x3)
            conInner.add(model?.services?.counselingServices!!.x4)
            conInner.add(model?.services?.counselingServices!!.x5)
            conInner.add(model?.services?.counselingServices!!.x6)
            conInner.add(model?.services?.counselingServices!!.x7)
            list.add(SetServices("Counseling Services",conInner))
            val listInner=ArrayList<String>()
            listInner.add(model?.services?.remedialServices!!.get(0))
            listInner.add(model?.services?.remedialServices!!.get(1))
            listInner.add(model?.services?.remedialServices!!.get(2))
            list.add(SetServices("Remedial Services",listInner))
            val carInner=ArrayList<String>()
            carInner.add(model?.services?.careerServices!!.x0)
            carInner.add(model?.services?.careerServices!!.x1)
            carInner.add(model?.services?.careerServices!!.x2)
            carInner.add(model?.services?.careerServices!!.x3)
            carInner.add(model?.services?.careerServices!!.x4)
            carInner.add(model?.services?.careerServices!!.x5)
            carInner.add(model?.services?.careerServices!!.x6)
            list.add(SetServices("Career Placement Services",carInner))
            val sepInner=ArrayList<String>()
            sepInner.add(model?.services?.specialPrograms!!.x0)
            sepInner.add(model?.services?.specialPrograms!!.x1)
            sepInner.add(model?.services?.specialPrograms!!.x2)
            sepInner.add(model?.services?.specialPrograms!!.x3)
            sepInner.add(model?.services?.specialPrograms!!.x4)
            sepInner.add(model?.services?.specialPrograms!!.x5)
            sepInner.add(model?.services?.specialPrograms!!.x6)
            sepInner.add(model?.services?.specialPrograms!!.x7)
            list.add(SetServices("Special Programs for students with learning and/or physical disabilities",sepInner))
            val addInner=ArrayList<String>()
            addInner.add(model?.services?.additionalServices!!.x0)
            addInner.add(model?.services?.additionalServices!!.x1)
            addInner.add(model?.services?.additionalServices!!.x2)
            addInner.add(model?.services?.additionalServices!!.x3)
            list.add(SetServices("Additional Services",addInner))
            mBinding.recyclerView.adapter=CampusAdapter(list,requireContext())
        }

    }
}