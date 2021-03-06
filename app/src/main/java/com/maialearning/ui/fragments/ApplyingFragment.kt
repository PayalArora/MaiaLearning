package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.CommentsSheetBinding
import com.maialearning.databinding.FragmentApplyingBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.ui.adapter.ApplyingAdapter
import com.maialearning.ui.adapter.CommentAdapter
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.HomeViewModel
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApplyingFragment : Fragment(), OnItemClickOption, OnItemClick {
    var selectedValue = ""
    private lateinit var mBinding: FragmentApplyingBinding
    private lateinit var dialogP: Dialog
    private val homeModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentApplyingBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setAdapter()

    }

    private fun init() {
        dialogP = showLoadingDialog(requireContext())
        dialogP.show()
        initObserver()
        homeModel.getApplyList("")


    }

    private fun initObserver() {
        homeModel.applyObserver.observe(requireActivity()) {
            it?.let {
                dialogP?.dismiss()
                val json = JSONObject(it.toString()).getJSONObject("9375").getJSONObject("data")
                val x = json.keys() as Iterator<String>
                val jsonArray = JSONArray()
                while (x.hasNext()) {
                    val key: String = x.next().toString()
                    jsonArray.put(json.get(key))
                }
                val array: ArrayList<ConsiderModel.Data> = ArrayList()
                for (i in 0 until jsonArray.length()) {
                    val object_ = jsonArray.getJSONObject(i)
                    val arrayProgram: ArrayList<ConsiderModel.ProgramData> = ArrayList()
                    var programArray = object_.getJSONArray("app_by_program_data")
                    for (j in 0 until programArray.length()) {
                        val objectProgram = programArray.getJSONObject(j)
                        arrayProgram.add(
                            ConsiderModel.ProgramData(
                                objectProgram.getInt("program_id"),
                                objectProgram.getString("program_name"),
                                "",
                                ""
                            )
                        )
                    }
                    val model: ConsiderModel.Data = ConsiderModel.Data(
                        object_.getInt("contact_info"),
                        object_.getInt("parchment"),
                        object_.getInt("slate"),
                        object_.getInt("transcript_nid"),
                        object_.getString("university_nid"),
                        object_.getString("name"),
                        object_.getString("country"),
                        object_.getString("country_name"),
                        object_.getString("created_by_name"),
                        object_.getString("created_date"),
                        object_.getString("internal_deadline"),
                        arrayProgram,0
                    )
                    array.add(model)
                }
                mBinding.applyingList.adapter = ApplyingAdapter(this, array)


            }
        }
    }

    private fun setAdapter() {
        mBinding.applyingList.adapter = ApplyingAdapter(this, arrayListOf())
    }

    private fun bottomSheetType(layoutId: Int, rbId: Int, type: Int) {
        val dialog = BottomSheetDialog(requireContext())

        val view = layoutInflater.inflate(layoutId, null)
        view?.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        val radioAppType = view.findViewById<RadioGroup>(rbId)
        dialog.setContentView(view)
        dialog.show()
        view.findViewById<RelativeLayout>(R.id.close).setOnClickListener {
            dialog.dismiss()
        }
        radioAppType.setOnCheckedChangeListener(
            { group, checkedId ->
                val radioButton = radioAppType.findViewById(checkedId) as RadioButton
                (mBinding.applyingList.adapter as ApplyingAdapter).setValue(
                    radioButton.text.toString(),
                    type
                )
            })
    }

    override fun onTypeClick() {
        bottomSheetType(R.layout.application_type, R.id.radio_app_type, 1)
    }

    override fun onTermClick() {
        bottomSheetType(R.layout.application_term, R.id.radio_app_term, 0)
    }

    override fun onPlanClick() {
        bottomSheetType(R.layout.application_plan_filter, R.id.radio_action, 2)
    }

    override fun onCommentClick() {
        bottomSheetComment()
    }

    private fun bottomSheetComment() {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: com.maialearning.databinding.CommentsSheetBinding =
            CommentsSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.close.setOnClickListener {
            dialog.dismiss()
        }
        sheetBinding.commentList.adapter = CommentAdapter(this)
    }

    override fun onClick(positiion: Int) {

    }

    override fun onAddClick() {

    }

}