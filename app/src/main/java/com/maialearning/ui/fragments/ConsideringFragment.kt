package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.CommentsSheetBinding
import com.maialearning.databinding.ConsideringLayoutBinding
import com.maialearning.databinding.LayoutProgramsBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.ui.adapter.CommentAdapter
import com.maialearning.ui.adapter.ConsiderAdapter
import com.maialearning.ui.adapter.ProgramAdapter
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.HomeViewModel
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

const val type: String = "UCAS"
const val term = "Spring 2022"
const val plan = "Early Action"

class ConsideringFragment : Fragment(), OnItemClickOption, OnItemClick {
    var count: Int = 0
    var dialog: BottomSheetDialog? = null
    private lateinit var dialogP: Dialog
    private lateinit var mBinding: ConsideringLayoutBinding
    private val homeModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = ConsideringLayoutBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        init()
//        setAdapter()
    }

    private fun init() {
        dialogP = showLoadingDialog(requireContext())
        dialogP.show()
        initObserver()
        homeModel.getConsiderList("9375")
    }

    private fun initObserver() {
        homeModel.listObserver.observe(requireActivity()) {
            it?.let {
                dialogP?.dismiss()
                val json = JSONObject(it.toString()).getJSONObject("9375").getJSONObject("data")
                val x = json.keys() as Iterator<String>
                val jsonArray = JSONArray()
                while (x.hasNext()) {
                    val key: String = x.next().toString()
                    jsonArray.put(json.get(key))
                }
                val countries = ArrayList<String>()
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
                    if (!countries.contains(object_.getString("country_name")))
                        countries.add(object_.getString("country_name"))
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
                        arrayProgram,
                        0
                    )
                    array.add(model)
                }
                val finalArray: ArrayList<ConsiderModel.Data> = ArrayList()
                var pos = 0
                countries.reverse()
                for (j in 0 until countries.size) {
                    var firstTime = true
                    var count = 0
                    for (k in 0 until array.size) {
                        if (countries[j] == array[k].country_name) {
                            count += 1
                            if (firstTime) {
                                firstTime = false
                                array[k].country_name = countries[j]
                            } else {
                                array[k].country_name = ""
                            }
                            finalArray.add(array[k])
                            finalArray[pos].count = count
                        }
                    }
                    pos = finalArray.size
                }


                mBinding.universitisCounte.text = array.size.toString() + " Universities"
                mBinding.consideringList.adapter = ConsiderAdapter(this, finalArray)


            }
        }
    }

    private fun setAdapter() {
        mBinding.consideringList.adapter = ConsiderAdapter(this, arrayListOf())
    }


    private fun setListeners() {

    }

    override fun onDestroyView() {
        dialog?.dismiss()
        super.onDestroyView()
    }


    private fun bottomSheetType(layoutId: Int, rbId: Int, type: Int) {
        dialog = BottomSheetDialog(requireContext())

        val view = layoutInflater.inflate(layoutId, null)
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        val radioAppType = view.findViewById<RadioGroup>(rbId)
        dialog?.setContentView(view)
        dialog?.show()
        view.findViewById<RelativeLayout>(R.id.close).setOnClickListener {
            dialog?.dismiss()
        }
        radioAppType.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = radioAppType.findViewById(checkedId) as RadioButton
            (mBinding.consideringList.adapter as ConsiderAdapter).setValue(
                radioButton.text.toString(),
                type
            )
        }
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

    override fun onAddClick() {
        bottomSheetProgram()
    }

    private fun bottomSheetComment() {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: CommentsSheetBinding = CommentsSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.close.setOnClickListener {
            dialog.dismiss()
        }
        sheetBinding.commentList.adapter = CommentAdapter(this)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun bottomSheetProgram() {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: LayoutProgramsBinding = LayoutProgramsBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()

        sheetBinding.addMoreLayout.adapter = ProgramAdapter(this)
        sheetBinding.addMore.setOnClickListener {
            (sheetBinding.addMoreLayout.adapter as ProgramAdapter).setCount(
                count++
            )
        }
        sheetBinding.save.setOnClickListener { dialog.dismiss() }

    }

    override fun onClick(positiion: Int) {

    }

}


interface OnItemClickOption {
    fun onTypeClick()
    fun onTermClick()
    fun onPlanClick()
    fun onCommentClick()
    fun onAddClick()
}
