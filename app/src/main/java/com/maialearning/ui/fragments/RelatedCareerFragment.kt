package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.databinding.RelatedCareersBinding
import com.maialearning.databinding.SalariesLayBinding
import com.maialearning.model.RelatedCareersItem
import com.maialearning.model.SelectedCareerResponse
import com.maialearning.ui.adapter.RelatedCareerAdapter


class RelatedCareerFragment(val response: SelectedCareerResponse) : Fragment() {
    var dialog: BottomSheetDialog? = null
    private lateinit var mBinding: RelatedCareersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding= RelatedCareersBinding.inflate(inflater,container,false)
        mBinding.listProgram.adapter =
            context?.let { RelatedCareerAdapter(it,response.relatedCareers, ::click) }
        return mBinding.root

    }

    private fun click(i: Int) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}