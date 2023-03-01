package com.maialearning.ui.activity

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.databinding.LayoutSurveydetailBinding
import com.maialearning.model.KeyVal
import com.maialearning.model.SurveyQuestionAnswer
import com.maialearning.ui.adapter.ID
import com.maialearning.ui.adapter.SurveyMultiChoiceAdapter
import com.maialearning.ui.adapter.SurveySingleChoiceAdapter
import com.maialearning.ui.model.SurveyDetailParser
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.MessageViewModel
import com.maialearning.viewmodel.SurveyDetailViewModel
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SurveyDetail: AppCompatActivity() {
    private lateinit var mBinding: LayoutSurveydetailBinding
    private val surveyDetailViewModel: SurveyDetailViewModel by viewModel()
    private lateinit var dialog: Dialog
    var count:Int = 0
    var arr = arrayListOf<SurveyQuestionAnswer>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout for this fragment
        mBinding = LayoutSurveydetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setListeners()
        init()
        setObserver()
    }

    private fun setObserver() {
        surveyDetailViewModel.surveyDetailObserver.observe(this){
            dialog.dismiss()
            val parser = SurveyDetailParser(JSONObject(it.toString()))
             arr =parser.parse(intent.getStringExtra(ID)!!)
            arr.sortBy { it.weight }
            Log.e("arr", ""+arr.size)

           if (arr.size>0){
               val survey = arr.get(count)
               displayDataQuestion(survey)
           }
        }
    }

    private fun displayDataQuestion(survey:SurveyQuestionAnswer){
        mBinding.surveyAdapter.apply {
            when (survey.bundle) {
                "comment_box"-> {
                    questionLay.visibility = View.VISIBLE
                    textDescription.visibility = View.VISIBLE
                    layoutSingleChoice.visibility = View.GONE
                    ques.text = survey.title
                    countTxt.text = survey.weight.toString()
                    textDescription.setText(survey.survey_answer_value.toString())
                    textDescription.isEnabled = false
                }
                "single_choice" ->{
                    questionLay.visibility = View.VISIBLE
                    textDescription.visibility = View.GONE
                    ques.text = survey.title
                    layoutSingleChoice.visibility = View.VISIBLE
                    countTxt.text = survey.weight.toString()
                    survey.options?.let {
                        val arr = arrayListOf<KeyVal>()
                        for (i in 0 until it.length()){
                            arr.add(KeyVal(it.getString(i).split("=")[1], it.getString(i).split("=")[1], it.getString(i).split("=")[1]== survey.survey_answer_value) )
                        }
                        recyclerList.adapter = SurveySingleChoiceAdapter(arr)
                    }

                }
               "multiple_choice" ->{
                   questionLay.visibility = View.VISIBLE
                   textDescription.visibility = View.GONE
                   ques.text = survey.title
                   layoutSingleChoice.visibility = View.VISIBLE
                   countTxt.text = survey.weight.toString()
                   survey.options?.let {
                       val arr = arrayListOf<KeyVal>()
                       for (i in 0 until it.length()){
                           arr.add(KeyVal(it.getString(i).split("=")[1], it.getString(i).split("=")[1], it.getString(i) == survey.survey_answer_value) )
                       }
                       recyclerList.adapter = SurveyMultiChoiceAdapter(arr)
                   }
                }
            }
        }
    }

    private fun init() {
        dialog = showLoadingDialog(this)
        dialog.show()
        surveyDetailViewModel.getSurveyResponses(intent.getStringExtra(ID)!!)
    }

    private fun setListeners() {
        mBinding.next.setOnClickListener {
            Log.e("count",""+count)
            Log.e("arr", ""+(arr.size-1))
            if (count < arr.size-1) {
                Log.e("arr", ""+(arr.size-1))
                count++
                val survey = arr.get(count)
                displayDataQuestion(survey)
            }
//            mBinding.apply {
//                surveyAdapter.question.visibility = View.VISIBLE
//            if (mBinding.surveyAdapter.textDescription.isVisible){
//                mBinding.surveyAdapter.textDescription.visibility = View.GONE
//                mBinding.surveyAdapter.layoutCheckbox.visibility = View.VISIBLE
//
//            }
//             else   if (mBinding.surveyAdapter.layoutCheckbox.isVisible){
//                    mBinding.surveyAdapter.layoutCheckbox.visibility = View.GONE
//                    mBinding.surveyAdapter.layoutMultiChoice.visibility = View.VISIBLE
//                }
//              else  if (mBinding.surveyAdapter.layoutMultiChoice.isVisible){
//                    mBinding.surveyAdapter.layoutMultiChoice.visibility = View.GONE
//                    mBinding.surveyAdapter.layoutSingleChoice.visibility = View.VISIBLE
//                }
//               else if (mBinding.surveyAdapter.layoutSingleChoice.isVisible){
//                    mBinding.surveyAdapter.layoutSingleChoice.visibility = View.GONE
//                    mBinding.surveyAdapter.congTxt.visibility = View.VISIBLE
//                surveyAdapter.question.visibility = View.GONE
//                }
//            }
        }
        mBinding.backBtn.setOnClickListener {
            Log.e("count",""+count)
            if (count >0) {
                count--
                val survey = arr.get(count)
                displayDataQuestion(survey)
            }
        }
        mBinding.back.setOnClickListener {
         finish()
        }
    }

}