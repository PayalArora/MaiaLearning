package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.SupportMapFragment
import com.maialearning.R
import com.maialearning.databinding.DegreeFragmentBinding
import com.maialearning.databinding.OverviewLayoutBinding
import com.maialearning.ui.adapter.DegreeAdapter
import com.maialearning.ui.adapter.VideoFactAdapter

class DegreesFragment : Fragment() {
    private lateinit var mBinding: DegreeFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding= DegreeFragmentBinding.inflate(inflater,container,false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.recyclerView.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mBinding.recyclerView.adapter = DegreeAdapter()

    }
}