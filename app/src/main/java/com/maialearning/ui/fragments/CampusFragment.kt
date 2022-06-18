package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.CampusLayoutBinding
import com.maialearning.ui.adapter.CampusAdapter

class CampusFragment : Fragment() {
    private lateinit var mBinding: CampusLayoutBinding

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
            GridLayoutManager(requireContext(),3, LinearLayoutManager.VERTICAL,false)
        mBinding.recyclerView.adapter=CampusAdapter()


    }
    private fun initData(){


    }
}