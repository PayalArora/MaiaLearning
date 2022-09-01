package com.maialearning.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maialearning.R
import com.maialearning.databinding.FragmentDashboardBinding
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.model.ShortcutModel
import com.maialearning.ui.adapter.ShortcutAdapter
import com.maialearning.util.prefhandler.SharedHelper

class ShortcutFragment : Fragment() {
    private lateinit var mBinding: LayoutRecyclerviewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = LayoutRecyclerviewBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    private fun setAdapter() {
        val array:ArrayList<ShortcutModel> = arrayListOf()
        val list = SharedHelper(requireContext()).appResponse?.schoolShortcut?.school
        if (list != null) {
            for (i in list) {
                val name = i?.name?:""
                val link = i?.externalLink?:""

              val shortcutModel = ShortcutModel(name, link)
              array.add(shortcutModel)
            }
        }
        array.add(ShortcutModel("Google Drive", SharedHelper(requireContext()).appResponse?.schoolShortcut?.student.toString()))



        mBinding.recyclerList.adapter = ShortcutAdapter(requireContext(), array)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setAdapter()
    }

    private fun setListeners() {

    }

}