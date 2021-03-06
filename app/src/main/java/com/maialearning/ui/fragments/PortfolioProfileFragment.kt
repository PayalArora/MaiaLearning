package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maialearning.R

import com.maialearning.databinding.PortfolioProfileFragmentBinding


class PortfolioProfileFragment : Fragment() {
    private lateinit var mBinding: PortfolioProfileFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = PortfolioProfileFragmentBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.addMore.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager?.beginTransaction()
            transaction?.add(
                R.id.nav_host_fragment_content_dashboard,
                PortfolioFilledProfileFragment()
            )
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }
    }
}
