package com.maialearning.ui.fragments

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R

import com.maialearning.databinding.SearchLayoutBinding
import com.maialearning.ui.adapter.UniFactAdapter

class SearchFragment : Fragment() {
    private lateinit var mBinding: SearchLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding= SearchLayoutBinding.inflate(inflater,container,false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment: SupportMapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.map, mapFragment)
            .commit()
        bottomSheetList()

    }
    private fun bottomSheetList() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.search_bottom_sheet, null)
        view.minimumHeight = Resources.getSystem().displayMetrics.heightPixels/3
//

        val listing = view.findViewById<RecyclerView>(R.id.listing)
//        val bottomSheet = view.findViewById<View>(R.id.listing)
//        val mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
//        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        listing.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        listing.adapter= UniFactAdapter(requireContext(),::click)
//        close.setOnClickListener {
//            dialog.dismiss()
//        }

        dialog.setContentView(view)
        dialog.show()
    }
    fun click(){}
}