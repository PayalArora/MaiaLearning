package com.maialearning.ui.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.maialearning.R
import com.maialearning.databinding.LayoutSurveydetailBinding
import com.maialearning.databinding.RangeBarSurveyBinding
import com.maialearning.model.CompleteSurveyReq
import com.maialearning.model.KeyVal
import com.maialearning.model.SurveyQuestionAnswer
import com.maialearning.model.UpdateSurveyAnswerReq
import com.maialearning.ui.adapter.*
import com.maialearning.ui.model.SurveyDetailParser
import com.maialearning.util.checkNonNull
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.SurveyDetailViewModel
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class SurveyDetail : AppCompatActivity() {
    private lateinit var mBinding: LayoutSurveydetailBinding
    private val surveyDetailViewModel: SurveyDetailViewModel by viewModel()
    private lateinit var dialog: Dialog
    var count: Int = 0
    var arr = arrayListOf<SurveyQuestionAnswer>()
    var click = 0
    var responseStatus = "in_progress"
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
        surveyDetailViewModel.surveyDetailObserver.observe(this) {
            dialog.dismiss()
            mBinding.next.visibility = View.VISIBLE
            mBinding.closeBtn.visibility = View.VISIBLE
            mBinding.started.visibility = View.GONE
            mBinding.surveyAdapter.main.visibility = View.VISIBLE
            mBinding.startScreen.startScreen.visibility = View.GONE
            val parser = SurveyDetailParser(JSONObject(it.toString()))
            arr = parser.parse(intent.getStringExtra(ID)!!)
            arr.sortBy { it.weight }
            Log.e("arr", "" + arr.size)
            if (arr.size > 0) {
                if (arr.size == 1) {
                    if (responseStatus == "completed" || responseStatus == "incomplete") {
                        mBinding.next.visibility = View.GONE
                    } else {
                        mBinding.next.visibility = View.VISIBLE
                    }
                    mBinding.backBtn.visibility = View.GONE
                }
                val survey = arr.get(count)
                for (i in 1..10) {
                    val inflater =
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val binding = RangeBarSurveyBinding.inflate(inflater)

                    val params = LinearLayout.LayoutParams(0, 120, 1.0f)
                    params.setMargins(10, 0, 10, 0);
                    binding.txt1.layoutParams = params
                    if (responseStatus == "completed" || responseStatus == "incomplete")
                        binding.txt1.isEnabled = false
                    else
                        binding.txt1.isEnabled = true
                    mBinding.surveyAdapter.layoutRange.addView(binding.root)
                }
                displayDataQuestion(survey)
            }
        }
        surveyDetailViewModel.updateAnswerObserver.observe(this) {
            dialog.dismiss()
            if (click == 0) {
                doNext()
            } else if (click == 1) {
                doPrev()
            } else if (click == 3) {
                displayCongratulations()
            } else if (click == 4) {
                finishActiv()
            }
        }
        surveyDetailViewModel.completeSurveyObserver.observe(this) {
            if (responseStatus == "pending") {
                responseStatus = "in_progress"
                arr.get(count).responseStatus = responseStatus
                saveAnswer()
            } else {
                dialog.dismiss()
                arr.get(count).responseStatus = "completed"
                responseStatus = "completed"
                finishActiv()
            }
        }
    }

    private fun displayDataQuestion(survey: SurveyQuestionAnswer) {

        mBinding.surveyAdapter.apply {
            if (responseStatus == "completed" || responseStatus == "incomplete") {
                mBinding.closeBtn.isVisible = true
                mBinding.finishLater.isVisible = false
            } else {
                mBinding.closeBtn.isVisible = false
                mBinding.finishLater.isVisible = true
            }
            when (survey.bundle) {
                "comment_box" -> {
                    questionLay.visibility = View.VISIBLE
                    textDescription.visibility = View.VISIBLE
                    layoutCongratulations.visibility = View.GONE
                    layoutSingleChoice.visibility = View.GONE
                    layoutRange.visibility = View.GONE
                    mBinding.finalizeBtn.visibility = View.GONE
                    ques.text = survey.title
                    countTxt.text = survey.weight.toString()
                    if (checkNonNull(survey.survey_answer_value)) {
                        textDescription.setText(survey.survey_answer_value.toString())
                    } else {
                        textDescription.setText("")
                    }
                    if (responseStatus == "completed" || responseStatus == "incomplete")
                        textDescription.isEnabled = false
                    else
                        textDescription.isEnabled = true
                }
                "single_choice" -> {
                    questionLay.visibility = View.VISIBLE
                    textDescription.visibility = View.GONE
                    ques.text = survey.title
                    mBinding.finalizeBtn.visibility = View.GONE
                    layoutCongratulations.visibility = View.GONE
                    layoutSingleChoice.visibility = View.VISIBLE
                    layoutRange.visibility = View.GONE
                    countTxt.text = survey.weight.toString()
                    survey.options?.let {
                        var arrchoice = arrayListOf<KeyVal>()
                        if (checkNonNull(survey.survey_answer_value)) {
                            for (i in 0 until it.length()) {
                                arrchoice.add(
                                    KeyVal(
                                        it.getString(i),
                                        it.getString(i).split("=")[1],
                                        it.getString(i)
                                            .split("=")[1] == survey.survey_answer_value || it.getString(
                                            i
                                        ) == survey.survey_answer_value
                                    )
                                )
                            }
                        } else {
                            for (i in 0 until it.length()) {
                                arrchoice.add(
                                    KeyVal(
                                        it.getString(i),
                                        it.getString(i).split("=")[1],
                                        false
                                    )
                                )
                            }
                        }
                        arr.get(count).arr_choice = arrchoice
                        if (responseStatus == "completed" || responseStatus == "incomplete")
                            recyclerList.adapter = SurveySingleChoiceAdapter(arrchoice)
                        else
                            recyclerList.adapter = SurveySingleChoiceAdapter(arrchoice, true)

                    }

                }
                "multiple_choice" -> {
                    questionLay.visibility = View.VISIBLE
                    textDescription.visibility = View.GONE
                    ques.text = survey.title
                    layoutCongratulations.visibility = View.GONE
                    layoutSingleChoice.visibility = View.VISIBLE
                    mBinding.finalizeBtn.visibility = View.GONE
                    layoutRange.visibility = View.GONE
                    countTxt.text = survey.weight.toString()
                    survey.options?.let {
                        var arrchoice = arrayListOf<KeyVal>()
                        if (checkNonNull(survey.survey_answer_value)) {
                            for (i in 0 until it.length()) {
                                val multichoice = survey.survey_answer_value?.split("|||")
//                                var m2 = ""
//                                if (multichoice?.size ?: 0 > 1) {
//                                    m2 = survey.survey_answer_value?.split("|||")?.get(1)!!
//                                }
                                var choice = false
                                if (multichoice != null) {
                                    for (j in multichoice.indices){
                                        if (it.getString(i).split("=")[1] == survey.survey_answer_value || it.getString(i) == multichoice.get(j)) {
                                            choice = true
                                            break
                                        }
                                    }
                                }
                                arrchoice.add(
                                    KeyVal( it.getString(i),  it.getString(i).split("=")[1],
                                        choice))

//                                arrchoice.add(
//                                    KeyVal(
//                                        it.getString(i),
//                                        it.getString(i).split("=")[1],
//                                        it.getString(i).split("=")[1] == survey.survey_answer_value
//                                                || it.getString(i) == survey.survey_answer_value || it.getString(
//                                            i
//                                        ) == survey.survey_answer_value?.split("|||")?.get(0)
//                                                || it.getString(i) == m2
//                                    )
//                                )
                            }
                        } else {
                            for (i in 0 until it.length()) {
                                arrchoice.add(
                                    KeyVal(
                                        it.getString(i),
                                        it.getString(i).split("=")[1],
                                        false
                                    )
                                )
                            }

                        }
                        arr.get(count).arr_choice = arrchoice
                        if (responseStatus == "completed" || responseStatus == "incomplete")
                            recyclerList.adapter =
                                SurveyMultiChoiceAdapter(arr.get(count).arr_choice!!)
                        else
                            recyclerList.adapter =
                                SurveyMultiChoiceAdapter(arr.get(count).arr_choice!!, true)

                    }
                }
                "range" -> {
                    questionLay.visibility = View.VISIBLE
                    textDescription.visibility = View.GONE
                    ques.text = survey.title
                    layoutSingleChoice.visibility = View.GONE
                    mBinding.finalizeBtn.visibility = View.GONE
                    layoutCongratulations.visibility = View.GONE
                    layoutRange.visibility = View.VISIBLE
                    countTxt.text = survey.weight.toString()
                    if (checkNonNull(survey.survey_answer_value)) {
                        val answer = survey.survey_answer_value?.toInt()
                        answer?.let {
                            for (i in 0..it - 1) {
                                val checkbox = mBinding.surveyAdapter.layoutRange.getChildAt(i)
                                    .findViewById<CheckBox>(R.id.txt1)
                                checkbox.text = (i + 1).toString()
                                checkbox.isChecked = true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun init() {
        dialog = showLoadingDialog(this)
        mBinding.apply {
            responseStatus = intent.getStringExtra(STATUS).toString()
            if (responseStatus == "completed" || responseStatus == "incomplete") {
                surveyAdapter.main.visibility = View.VISIBLE
                startScreen.startScreen.visibility = View.GONE
                started.visibility = View.GONE
                mBinding.next.visibility = View.VISIBLE
                mBinding.closeBtn.visibility = View.VISIBLE
                dialog.show()
                surveyDetailViewModel.getSurveyResponses(intent.getStringExtra(ID)!!)
            } else {
                surveyAdapter.main.visibility = View.GONE
                startScreen.startScreen.visibility = View.VISIBLE
                started.visibility = View.VISIBLE
                if (checkNonNull(intent.getStringExtra(TITLE)))
                    startScreen.surveyTitle.text = intent.getStringExtra(TITLE)
                if (checkNonNull(intent.getStringExtra(DESCRIPTION)))
                    startScreen.surveyDescription.text = intent.getStringExtra(DESCRIPTION)
                if (checkNonNull(intent.getStringExtra(DESC)))
                    startScreen.surveyDesc.text = intent.getStringExtra(DESC)
            }
        }
    }

    private fun setListeners() {
        mBinding.started.setOnClickListener {
            dialog.show()
            surveyDetailViewModel.getSurveyResponses(intent.getStringExtra(ID)!!)
        }
        mBinding.closeBtn.setOnClickListener {
            finishActiv()
        }
        mBinding.finishLater.setOnClickListener {
            click = 4
            val survey = arr.get(count)
            var answer: String? = survey.survey_answer_value
            if (checkNonNull(answer))
                saveAnswer()
            else
                finishActiv()
        }

        mBinding.next.setOnClickListener {
            click = 0
            Log.e("count", "" + count)
            Log.e("arr", "" + (arr.size - 1))
            if (responseStatus == "completed" || responseStatus == "incomplete") {
                doNext()
            } else {
                if (responseStatus == "pending") {
                    if (arr.size > 0 && checkNonNull(isAnswerFill())) {
                        dialog.show()
                        val req = CompleteSurveyReq("in_progress")
                        arr.get(0).surveyResponseUuid?.let { it1 ->
                            surveyDetailViewModel.completeSurvey(
                                req,
                                it1
                            )
                        }
                    }
                } else {
                    saveAnswer()
                }
            }
        }


        mBinding.backBtn.setOnClickListener {
            Log.e("count", "" + count)

            click = 1
            if (responseStatus == "completed" || responseStatus == "incomplete") {
                doPrev()
            } else {
                val survey = arr.get(count)
                var answer: String? = survey.survey_answer_value
                if (checkNonNull(answer))
                    saveAnswer()
                else
                    doPrev()

            }
        }
        mBinding.back.setOnClickListener {
            val returnIntent = Intent()
            setResult(101, returnIntent)
            // finishActiv()
            finishActiv()
        }
        mBinding.finalizeBtn.setOnClickListener {
            dialog.show()
            val req = CompleteSurveyReq("completed")
            arr.get(0).surveyResponseUuid?.let { it1 ->
                surveyDetailViewModel.completeSurvey(
                    req,
                    it1
                )
            }
        }
    }

    fun doPrev() {
        if (count > 0) {
            mBinding.backBtn.visibility = View.VISIBLE
            count--
            val survey = arr.get(count)
            displayDataQuestion(survey)
            mBinding.next.visibility = View.VISIBLE
            if ((count <= 0)) {
                mBinding.backBtn.visibility = View.GONE
            }
        }
    }

    fun doNext() {
        if (count < arr.size - 1) {
            count++
            var survey = arr.get(count)
            mBinding.next.visibility = View.VISIBLE
            Log.e("arr", "" + (arr.size - 1))
            displayDataQuestion(survey)
            mBinding.backBtn.visibility = View.VISIBLE
            if ((count + 1) >= arr.size) {
                if (responseStatus == "completed" || responseStatus == "incomplete") {
                    mBinding.next.visibility = View.GONE
                } else {
                    mBinding.next.visibility = View.VISIBLE
                }
            }
        } else {
            var survey = arr.get(count)
            if (responseStatus == "in_progress") {
                saveAnswer()
                click = 3
            }
        }
    }

    private fun displayCongratulations() {
        mBinding.surveyAdapter.apply {
            questionLay.visibility = View.GONE
            textDescription.visibility = View.GONE
            layoutSingleChoice.visibility = View.GONE
            layoutRange.visibility = View.GONE
            layoutCongratulations.visibility = View.VISIBLE
            mBinding.next.visibility = View.GONE
            mBinding.finalizeBtn.visibility = View.VISIBLE
        }
    }

    fun saveAnswer() {
        val survey = arr.get(count)
        var answer: String? = isAnswerFill()
       if (checkNonNull(answer)) {
            val encoder: Base64.Encoder =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Base64.getEncoder()
                } else {
                    TODO("VERSION.SDK_INT<0")
                }
            val encoded: String = encoder.encodeToString(
                answer?.toByteArray()
            )
            if (checkNonNull(answer)) {
                dialog.show()
                arr.get(count).survey_answer_value = answer
                if (mBinding.surveyAdapter.layoutCongratulations.isVisible) {
                    count++
                }
                val updateSurveyAnswerReq =
                    UpdateSurveyAnswerReq(survey.uuid!!, encoded, survey.surveyQuestionUuid!!)
                surveyDetailViewModel.updateSurveyAnswer(updateSurveyAnswerReq)
            }
        }

    }
    fun isAnswerFill():String?{
        val survey = arr.get(count)
        var answer: String? = survey.survey_answer_value
        when (survey.bundle) {
            "comment_box" -> {
                answer = mBinding.surveyAdapter.textDescription.text.toString()
            }
            "single_choice" -> {
                survey.arr_choice?.let {
                    for (item in it) {
                        if (item.checked) {
                            answer = item.key
                        }
                    }
                }
            }
            "multiple_choice" -> {
                var answer_choice = arrayListOf<String>()
                survey.arr_choice?.let {
                    for (item in it) {
                        if (item.checked) {
                            answer_choice.add(item.key)
                        }
                    }
                    answer = answer_choice.joinToString("|||", "", "")
                }
            }
        }
        return answer
    }

    fun finishActiv() {
        val returnIntent = Intent()
        setResult(101, returnIntent)
        finish()

    }
}