package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.maialearning.databinding.FactsLayoutNotesBinding
import com.maialearning.network.BaseApplication
import com.maialearning.ui.adapter.NotesFactAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.viewmodel.FactSheetModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FactsNotesFragment : Fragment() {
    private lateinit var mBinding: FactsLayoutNotesBinding
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
        mModel.idObserver.observe(requireActivity()) {
            it
//            SharedHelper(requireContext()).collegeNId = it.get("nid").toString()
//            mModel.getUniversityContact("Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,SharedHelper(requireContext()).collegeNId)

        }
    }

    private fun initData(){
        mModel.getUniversityNotes("Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
            SharedHelper(requireContext()).collegeNId,SharedHelper(requireContext()).ethnicityTarget?:"")


    }
}