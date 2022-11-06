package com.maialearning.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.ConsideringItemLayBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.ui.fragments.OnItemClickOption
import com.maialearning.util.*
import com.squareup.picasso.Picasso

class ConsiderAdapter(
    val onItemClickOption: OnItemClickOption,
    var array: ArrayList<ConsiderModel.Data>,
    val notesClick: (data: ConsiderModel.Data) -> Unit
) :
    RecyclerView.Adapter<ConsiderAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    var typeVal: String = ""
    var termVal = ""
    var planVal = "Early Action"
    var positio:Int = 0

    class ViewHolder(val binding: ConsideringItemLayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ConsideringItemLayBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.apply {
            array[position].apply {
                apply {
                    val isAppMode = checkNonNull(applicationMode)
                    val isAppTerm = checkNonNull(applicationTerm)
                    val isAppPlan = checkNonNull(applicationType)
                    val isAppByProgram = (appByProgramSupported == "1" && applicationMode != "3")
                    val isAppByPlan = !isAppByProgram
                    val isAppDeadlineDisabled =(
                            !isAppMode ||
                                    (collegeAppLicationType?.selectedPlanType == "term" && !isAppTerm) ||
                                    !isAppPlan
                            )
                    if (country_name == "") {
                        top.visibility = View.GONE
                    } else {
                        top.visibility = View.VISIBLE
                        countryTxt.text = country_name
                        countUniv.text = count.toString()
                    }
                    uniName.text = naviance_college_name
                    if (created_date != null)
                        date.setText(CommonClass.getDate(created_date.toLong()))
                    if (internal_deadline != null && internal_deadline != "null")
                        textInternalDate.setText(internal_deadline?.toLong()
                            ?.let { CommonClass.getDateOnly(it) })

                    if (checkNonNull(dueDate))
                        deadlineValue.setText(dueDate
                        ?.let {   formateDateFromstring("yyyy-MM-dd hh:mm:ss","MMM dd yyyy", it) })
                    else
                        deadlineValue.setText("Select")

                   val canShowAppDeadline= isAppByPlan
                    if (canShowAppDeadline) {
                        deadlineValue.visibility = View.VISIBLE
                        appDeadline.visibility = View.VISIBLE
                        view7.visibility = View.VISIBLE
                    }
                    else {
                        deadlineValue.visibility = View.GONE
                        appDeadline.visibility = View.GONE
                        view7.visibility = View.GONE
                    }
                    if (isAppDeadlineDisabled){
                        appDeadline.isEnabled = false

                    } else {
                        appDeadline.isEnabled = true
                    }
                    appDeadline.setOnClickListener {
                       positio = position
                        deadlineValue.showDatePicker(root.context, ::deadlineClick)
                    }
                            //|| (if selected plan has a deadline)
                    name.setText(created_name)

                    if (college_priority_choice.equals("null")) {
                        countTxt.setText("?")
                    } else if (college_priority_choice.equals("1")) {
                        countTxt.setText("1st")
                    } else if (college_priority_choice.equals("2")) {
                        countTxt.setText("2nd")
                    } else {
                        countTxt.setText(college_priority_choice)
                    }
//            university.app_by_program_supported === 1 &&
//                    university.application_mode !== UniversityAppTypeEnum.CommonApp


                    if (isAppMode) {
                        //  typeValue.setText(applicationType)
                        val typeVal = getAppType(applicationMode!!, position)
                        typeVal.let { typeValue.setText(it) }
                        appTerm.isEnabled = true
                    } else {
                        typeValue.setText("Select")
                        appTerm.isEnabled = false
                    }
                    val supportsMaiaDocs = slate == 1 || parchment == 1
                    val canShowTeacherEval =
                        appByProgramSupported != "1" || applicationMode == CommonApp
                    if (canShowTeacherEval) {
                        textTe.setText(
                            "${root.context.resources.getString(R.string.te)} ${
                                parseEvaluation(
                                    requiredRecommendation?.teacherEvaluation,
                                    requiredRecommendation?.maxTeacherEvaluation
                                )
                            }"
                        )
                        textTe.visibility = View.VISIBLE
                    } else {
                        textTe.visibility = View.GONE
                    }
                    val canShowCounselorRec =
                        appByProgramSupported != "1" ||
                                applicationMode == CommonApp

                    if (canShowCounselorRec) {
                        textCrRequired.visibility = View.VISIBLE
                        val cr = parseNA(requiredRecommendation?.counselorRecommendation)
                        if (cr == "true") {
                            textCrRequired.setText(
                                "${root.context.resources.getString(R.string.cr_required)}: ${
                                    root.context.resources.getString(
                                        R.string.cr_required_req
                                    )
                                }"
                            )
                        } else if (cr == "false") {
                            textCrRequired.setText(
                                "${root.context.resources.getString(R.string.cr_required)}: ${
                                    root.context.resources.getString(
                                        R.string.cr_required_opt
                                    )
                                }"
                            )
                        } else {
                            textCrRequired.setText(
                                "${root.context.resources.getString(R.string.cr_required)}: ${
                                    root.context.resources.getString(
                                        R.string.na
                                    )
                                }"
                            )
                        }

                    } else {
                        textCrRequired.visibility = View.GONE
                    }

                    if (applicationType != null && !applicationType.equals("null")) {
                        //  typeValue.setText(applicationType)
                        val typeVal = getAppPlan(applicationType!!, position,appDeadline,deadlineValue)
                        if (typeVal != null)
                            planValue.setText(typeVal)
                        else
                            planValue.setText("Select")
                    } else {
                        planValue.setText("Select")
                    }
                    val isAppPlanDdDisabled = (!(
                            (applicationMode != CommonApp ||
                                    manualUpdate == 0) &&
                                    isAppTerm) ||
                            !isAppMode ||
                            (collegeAppLicationType?.selectedPlanType == "term" && !isAppTerm))
                    Log.e("TERMTYP", collegeAppLicationType?.selectedPlanType.toString())
                    if (isAppPlanDdDisabled){
                        appPlan.isEnabled = false
                    } else {
                        appPlan.isEnabled = true
                    }


                    if (isAppByPlan) {
                        appPlan.visibility = View.VISIBLE
                    } else {
                        appPlan.visibility = View.GONE
                    }
                    if (isAppTerm && !applicationTerm.equals("Reset")) {
                        termValue.setText(applicationTerm)
                    } else {
                        termValue.setText("Select")
                    }

                    Picasso.with(viewHolder.binding.root.context)
                        .load("${UNIV_LOGO_URL}${country?.toLowerCase()}/${unitid}/logo_sm.jpg")
                        .error(R.drawable.static_coll).into(viewHolder.binding.univIcon)
                    Picasso.with(viewHolder.binding.root.context)
                        .load("https://countryflagsapi.com/png/${country}")
                        .into(viewHolder.binding.idIVCourse)
                    //uniName.text=naviance_college_name
                    appTerm.setOnClickListener {
                        onItemClickOption.onTermClick(position)

                    }
                    appType.setOnClickListener {
                        onItemClickOption.onTypeClick(position)

                    }
                    menuDots.setOnClickListener {
                        onItemClickOption.onMenuClick(position, it)
                    }
                    appPlan.setOnClickListener {
                        onItemClickOption.onPlanClick(position)
                    }
                    commentImg.setOnClickListener {
                        onItemClickOption.onCommentClick()
                    }
                    addButton.setOnClickListener {
                        onItemClickOption.onAddClick(position)
                    }
                    infoIcon.setOnClickListener {
                        onItemClickOption.onInfoClick(position)
                    }
                    val others = ArrayList<String>()
                    for (i in 0 until program_data?.size!!) {
                        others.add(program_data?.get(i)?.program_name ?: "")
                    }
//            val others: Array<out String> = root.context.resources.getStringArray(R.array.spinner_programs)
                    val adapter = ArrayAdapter(
                        root.context,
                        R.layout.spinner_text, others
                    )
                    allSystem.adapter = adapter
                    commentImg.setOnClickListener { notesClick(array[position]) }
                    applyingLay.setOnClickListener({
                        onItemClickOption.onApplyingClick(position)
                    })
                }
            }
        }


    }


    override fun getItemCount(): Int {
        return array.size
    }

    fun setValue(value: String, type: Int) {
        if (type == 0)
            termVal = value
        else if (type == 1)
            typeVal = value
        else if (type == 2)
            planVal = value
        notifyDataSetChanged()
    }

    fun getAppType(key:String, position: Int):String?{
        array.get(position).collegeAppLicationType?.collType?.let {
            for (item in it){
                if (item.key.replaceInvertedComas().equals(key)){
                    return item.value
                }
            }
        }
        return null
    }

    fun getAppPlan(key:String, position: Int, appDeadline: LinearLayout, deadlineValue: TextView):String?{
        array.get(position).collegeAppLicationType?.collType?.let {
            for (item in it){
                if (item?.term?.type == "term" && item?.term?.collTerm!= null) {
                    for (plan in item?.term?.collTerm!!) {
                        if ( plan?.collPlan!= null) {
                            for (planitem in plan?.collPlan!!) {
                                if (planitem.decision_plan.replaceInvertedComas() == key) {
                                    if (checkNonNull(planitem.deadline_date)){
                                        appDeadline.isEnabled = false
                                        deadlineValue.setText(planitem.deadline_date
                                            ?.let {   formateDateFromstring("MM/dd/yyyy","MMM dd yyyy", it) })

                                    }
                                    return planitem.decision_plan_value
                                }
                            }
                        }
                    }
                }
                else {
                    if(item?.term?.planList!= null) {
                        for (i in item?.term?.planList!!) {
                            if (i.id.replaceInvertedComas() == key) {
                                return i.label
                            }
                        }
                    }
                }
            }
        }
        return null
    }
//    fun showAppPlan (position: Int):Boolean{
//        array.get(position).collegeAppLicationType?.collType?.let {
//            for (item in it){
//                if (item?.term?.type == "term" && item?.term?.collTerm!= null) {
//                    for (plan in item?.term?.collTerm!!) {
//                        if ( plan?.collPlan!= null) {
//                            for (planitem in plan?.collPlan!!) {
//                                return true
//                            }
//                        }
//                    }
//                }
//                else {
//                    if(item?.term?.planList!= null) {
//                        for (i in item?.term?.planList!!) {
//                            return true
//                        }
//                    }
//                }
//            }
//        }
//        return false
//    }

    fun deadlineClick(string: String){
        onItemClickOption.onDeadlineClick(positio,string)
    }
}


