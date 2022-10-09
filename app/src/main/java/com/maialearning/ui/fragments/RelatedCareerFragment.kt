package com.maialearning.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.GsonBuilder
import com.maialearning.databinding.RelatedCareersBinding
import com.maialearning.model.RelatedCareerModel
import com.maialearning.model.SelectedCareerResponse
import com.maialearning.ui.adapter.RelatedCareerAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.RelatedCareerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RelatedCareerFragment(val response: SelectedCareerResponse) : Fragment() {
    var dialog: BottomSheetDialog? = null
    private val relatedCareerViewModel: RelatedCareerViewModel by viewModel()
    private lateinit var progress: Dialog
    private lateinit var mBinding: RelatedCareersBinding
    var arrayTopPick: ArrayList<RelatedCareerModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = RelatedCareersBinding.inflate(inflater, container, false)
        progress = showLoadingDialog(requireContext())
        //progress.show()
        SharedHelper(requireContext()).id?.let {
            relatedCareerViewModel.getStudentTopPick(it)
        }
        observer()

        return mBinding.root

    }

    private fun observer() {
        val gson = GsonBuilder().create()
        relatedCareerViewModel.showError.observe(requireActivity()) {
            progress.dismiss()
        }
        relatedCareerViewModel.topPickObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            arrayTopPick = arrayListOf()
            if (it != null) {
                for (i in it) {
                    val toppick = gson.fromJson(i, RelatedCareerModel::class.java)
                    arrayTopPick?.add(toppick)

                    response.relatedCareers?.let { it1 ->
                        for (career in it1){
                            if (toppick.code == career.careerId){
                                career.topPick = true
                            }

                    }
                }
            }

        }

        mBinding.listProgram.adapter =
            context?.let { RelatedCareerAdapter(it, response.relatedCareers, ::click) }


    }
}

private fun click(i: Int) {

}

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
}
}