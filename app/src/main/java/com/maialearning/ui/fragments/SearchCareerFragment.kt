package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.SearchCareerLayBinding
import com.maialearning.ui.adapter.SearchProgramAdapter

class SearchCareerFragment(var type:String) : Fragment() {
    private lateinit var mBinding: SearchCareerLayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding= SearchCareerLayBinding.inflate(inflater,container,false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(type =="list"){
            mBinding.searchLay.visibility=View.GONE
            mBinding.list.visibility=View.VISIBLE

        }else{
            mBinding.searchLay.visibility=View.VISIBLE
            mBinding.list.visibility=View.GONE
        }
        mBinding.searchLay.setOnClickListener {
            mBinding.searchLay.visibility=View.GONE
            mBinding.list.visibility=View.VISIBLE
        }
        mBinding.listProgram.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mBinding.listProgram.adapter = SearchProgramAdapter(requireContext())


    }
}