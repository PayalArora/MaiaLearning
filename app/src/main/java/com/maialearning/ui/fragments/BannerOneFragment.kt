package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.BannerOneBinding
import com.maialearning.databinding.ConnectionsViewpagerBinding
import com.maialearning.ui.adapter.ConnectionAdapter

class BannerOneFragment : Fragment(), OnItemClick {
    private lateinit var mBinding: BannerOneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = BannerOneBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(positiion: Int) {

    }


}