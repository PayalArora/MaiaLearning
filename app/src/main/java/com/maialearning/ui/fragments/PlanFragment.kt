package com.maialearning.ui.fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.databinding.PlanFragmentBinding
import com.maialearning.ui.adapter.*

class PlanFragment() : Fragment() {
    var dialog: BottomSheetDialog? = null
    private lateinit var mBinding: PlanFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding= PlanFragmentBinding.inflate(inflater,container,false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.careersList.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        mBinding.careersList.adapter = CareerAdapter()
        mBinding.knowList.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        mBinding.knowList.adapter = KnowledgeAttrAdapter(requireContext())
        mBinding.actionList.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        mBinding.actionList.adapter = KnowledgeActionAdapter(requireContext())
        mBinding.add.setOnClickListener {
            bottomSheetList()
        }

    }
    private fun bottomSheetList() {
        dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.add_attr_lay, null)
       view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        val listing = view.findViewById<RecyclerView>(R.id.list)
        val layout = view.findViewById<ConstraintLayout>(R.id.layout)
        val close = view.findViewById<TextView>(R.id.clear)
        val back = view.findViewById<TextView>(R.id.back)
//        DrawableCompat.setTint(layout.background, Color.parseColor("#E5E5E5"))

        listing.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        listing.adapter = AttrAddSelect(requireContext())
        close.setOnClickListener {
            dialog?.dismiss()
        }
        back.setOnClickListener {
            dialog?.dismiss()
        }

        dialog?.setContentView(view)
        dialog?.show()
    }
}
