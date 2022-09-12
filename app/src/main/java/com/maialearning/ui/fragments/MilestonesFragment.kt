package com.maialearning.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.model.ItaskItem
import com.maialearning.ui.adapter.MilestonesAdapter
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.HomeViewModel
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MilestonesFragment : Fragment() {
    private lateinit var mBinding: LayoutRecyclerviewBinding
    private val homeModel: HomeViewModel by viewModel()
    private lateinit var progress: Dialog


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


    private fun setAdapter(itask: List<ItaskItem?>?) {
        mBinding.recyclerList.adapter = MilestonesAdapter(itask)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        progress = showLoadingDialog(requireContext())
        progress.show()
        homeModel.getMilestonesID()
        homeModel.getMilestoneIDObserver.observe(requireActivity()) {
            val json = JSONObject(it.toString())
            val x = json.keys() as Iterator<String>
            var idMile: String = ""
            while (x.hasNext()) {
                val key: String = x.next()
                if (json.optString(key).toString().equals("Milestone")) {
                    idMile = key
                }
            }
            Log.e("ID:>>> ", idMile)

            homeModel.getMilestones(idMile)
        }
        homeModel.getMilestonesObserver.observe(requireActivity()) {
            progress.dismiss()
            setAdapter(it.itask)
        }
    }

    private fun setListeners() {

    }

}