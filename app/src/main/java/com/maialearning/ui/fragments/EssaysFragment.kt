package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.ui.adapter.EssayAdapter

class EssaysFragment : Fragment() {

    private lateinit var mBinding: LayoutRecyclerviewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = LayoutRecyclerviewBinding.inflate(inflater, container, false)
        return mBinding.root
    }
    private fun setAdapter() {
        mBinding.recyclerList.adapter = EssayAdapter()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setAdapter()
    }

    private fun setListeners() {

    }
}