package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maialearning.R
import com.maialearning.databinding.PlanFragmentBinding
import com.maialearning.model.StudentCarrerPlanResponse
import com.maialearning.ui.adapter.*
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.CareerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlanFragment() : Fragment() {
    private val careerViewModel: CareerViewModel by viewModel()
    private lateinit var progress: Dialog
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

        mBinding = PlanFragmentBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.careersList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        mBinding.knowList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        mBinding.actionList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        mBinding.toolsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        mBinding.techList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        mBinding.abilityList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        mBinding.actionList.adapter = KnowledgeActionAdapter(requireContext())
        mBinding.addKnow.setOnClickListener {
            bottomSheetList()
        }
        mBinding.addSkills.setOnClickListener {
            bottomSheetList()
        }
        mBinding.addAbility.setOnClickListener {
            bottomSheetList()
        }
        mBinding.addTools.setOnClickListener {
            bottomSheetList()
        }
        mBinding.addTech.setOnClickListener {
            bottomSheetList()
        }

        progress = showLoadingDialog(requireContext())

        SharedHelper(requireContext()).id?.let { careerViewModel.getStudentCareerPlan(it) }
        initObserver()
    }

    private fun initObserver() {
        var gson: Gson = GsonBuilder().create()
        careerViewModel.showError.observe(requireActivity()) {
            progress.dismiss()
        }
        careerViewModel.studentCareerPlanObserver.observe(requireActivity()) {
            val itModel = gson.fromJson(it, StudentCarrerPlanResponse::class.java)
            Log.e("DATA", "" + itModel.nid)
            dataset(itModel)
        }
    }

    private fun dataset(itModel: StudentCarrerPlanResponse?) {
        mBinding.careersList.adapter = CareerAdapter(itModel?.careers)
        mBinding.knowList.adapter = KnowledgeAttrAdapter(requireContext(), itModel?.knowledge)
        mBinding.actionList.adapter = StudentSkillsPlanAdapter(requireContext(), itModel?.skills)
        mBinding.abilityList.adapter = AbilityAdapter(requireContext(), itModel?.ability)
        mBinding.toolsList.adapter = ToolsAdapter(requireContext(), itModel?.tools)
        mBinding.techList.adapter = TechnologyAdapter(requireContext(), itModel?.technology)
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

        listing.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
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
