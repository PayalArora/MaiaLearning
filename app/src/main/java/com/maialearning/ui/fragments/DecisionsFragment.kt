package com.maialearning.ui.fragments

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnItemClickType
import com.maialearning.databinding.FragmentDashboardBinding
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.databinding.RadiobuttonFilterBinding
import com.maialearning.databinding.UniversityFilterBinding
import com.maialearning.ui.activity.ClickFilters
import com.maialearning.ui.adapter.*

class DecisionsFragment : Fragment(), DecisionClick{
    private lateinit var mBinding: LayoutRecyclerviewBinding


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


    private fun setAdapter() {
        mBinding.recyclerList.adapter = DecisionAdapter(this)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setAdapter()
    }

    private fun setListeners() {
   
    }

    override fun onDecisionClick() {
        showDialog(requireContext(), layoutInflater)
    }
    fun showDialog(con: Context, layoutInflater:LayoutInflater ){
        val dialog = BottomSheetDialog(con)
        val sheetBinding: RadiobuttonFilterBinding = RadiobuttonFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight =( (Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.filters.setText(con.resources.getString(R.string.select_decision1))
        sheetBinding.close.setOnClickListener { dialog.dismiss()}
       sheetBinding.rvRadioGroup.adapter = RadiobuttonFilterAdapter(con.resources.getStringArray(R.array.decision_filter))
    }

}