package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ConnectionsViewpagerBinding
import com.maialearning.ui.adapter.ConnectionAdapter
import com.maialearning.viewmodel.ProfileViewModel

class ConnectionsFragment (val viewModel: ProfileViewModel): Fragment(), OnItemClick {
    private lateinit var mBinding: ConnectionsViewpagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = ConnectionsViewpagerBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.connection_list)
//        recyclerView.adapter = ConnectionAdapter(this)
        viewModel.observer.observe(requireActivity(), {
            recyclerView.adapter = ConnectionAdapter(it.parent,this)
        })
    }


    override fun onClick(positiion: Int) {

    }


}