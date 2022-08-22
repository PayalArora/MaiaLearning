package com.maialearning.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.FactsLayoutNotesBinding
import com.maialearning.network.BaseApplication
import com.maialearning.ui.adapter.NotesFactAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.FactSheetModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FactsNotesFragment : Fragment() {
    private lateinit var mBinding: FactsLayoutNotesBinding
    private lateinit var dialogP: Dialog
    private val mModel: FactSheetModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding= FactsLayoutNotesBinding.inflate(inflater,container,false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        mBinding.recyclerView.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mBinding.recyclerView.adapter= NotesFactAdapter()
        initObserver()

    }

    private fun initObserver() {
        mModel.noteObserver.observe(viewLifecycleOwner) {
            dialogP?.dismiss()
            val note=it.getAsJsonObject("counselor_note").getAsJsonPrimitive("255").toString().replace("\"","")
            mBinding.notesText.text="Note: \n "+Html.fromHtml(note)
        }
        mModel.showLoading.observe(viewLifecycleOwner) {
            if (it == true) {
                dialogP = showLoadingDialog(requireContext())
            } else {
                dialogP?.dismiss()
            }
        }
    }

    private fun initData(){
        dialogP = showLoadingDialog(requireContext())
        mModel.getUniversityNotes("Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
            SharedHelper(requireContext()).collegeNId,SharedHelper(requireContext()).ethnicityTarget?:"")


    }
}