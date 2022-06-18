package com.maialearning.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.databinding.SearchCareerLayBinding
import com.maialearning.ui.adapter.CareerCompareAdapter
import com.maialearning.ui.adapter.SearchProgramAdapter

class SearchCareerFragment(var type:String) : Fragment() {
    var dialog: BottomSheetDialog? = null
    private lateinit var mBinding: SearchCareerLayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding= SearchCareerLayBinding.inflate(inflater,container,false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(type =="list"){
            mBinding.searchLay.visibility=View.GONE
            mBinding.list.visibility=View.VISIBLE

        }else{
            mBinding.searchLay.visibility=View.VISIBLE
            mBinding.list.visibility=View.GONE
        }
        mBinding.searchLay.setOnClickListener {
            mBinding.searchLay.visibility=View.GONE
            mBinding.list.visibility=View.VISIBLE
        }
        mBinding.listProgram.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mBinding.listProgram.adapter = SearchProgramAdapter(requireContext(),::bottomSheetList)


    }

    private fun bottomSheetList(position:Int) {
        dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.compare_careers, null)
//        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        val listing = view.findViewById<RecyclerView>(R.id.listing)
        val layout = view.findViewById<ConstraintLayout>(R.id.layout)
        val close = view.findViewById<RelativeLayout>(R.id.close)
        DrawableCompat.setTint(layout.background, Color.parseColor("#E5E5E5"))

        listing.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        listing.adapter = CareerCompareAdapter(requireContext())
        close.setOnClickListener {
            dialog?.dismiss()
        }

        dialog?.setContentView(view)
        dialog?.show()
    }
}