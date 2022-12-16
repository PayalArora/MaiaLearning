package com.maialearning.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.ApplyingItemLayBinding
import com.maialearning.databinding.ConsideringItemLayBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.ui.fragments.OnItemClickOption
import com.maialearning.util.*
import com.squareup.picasso.Picasso

class ApplyingAdapter(
    val onItemClickOption: OnItemClickOption,
    var considerarray: ArrayList<ConsiderModel.Data>
) :
    RecyclerView.Adapter<ApplyingAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    var typeVal: String = "UCAS"
    var termVal = "Spring 2022"
    var planVal = "Early Action"
    var positio:Int = 0
    var selectionVisiblility: Boolean = false
    var prev = ""

    class ViewHolder(val binding: ApplyingItemLayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ApplyingItemLayBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.apply {
            considerarray[position].apply {
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

                if (prev == country_name) {
                    top.visibility = View.GONE
                } else {
                    prev = country_name
                    top.visibility = View.VISIBLE
                    countryTxt.text = country_name
                }
                    countUniv.text = count.toString()

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
                    val typeVal = getAppPlan(applicationType!!, position,appDeadline, deadlineValue)
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
                    .load("$UNIV_LOGO_URL${country?.toLowerCase()}/${unitid}/logo_sm.jpg")
                    .error(R.drawable.static_coll).into(viewHolder.binding.univIcon)

                Picasso.with(viewHolder.binding.root.context)
                    .load("https://countryflagsapi.com/png/${country}")
                    .into(viewHolder.binding.idIVCourse)

                appTerm.setOnClickListener {
                    onItemClickOption.onTermClick(position)

                }
                appType.setOnClickListener {
                    onItemClickOption.onTypeClick(position)

                }
                addButton.setOnClickListener {
                    onItemClickOption.onAddClick(position)
                }
                appPlan.setOnClickListener {
                    onItemClickOption.onPlanClick(position)
                }
                menuDots.setOnClickListener {
                    onItemClickOption.onMenuClick(position, it)
                }
                commentImg.setOnClickListener {
                    onItemClickOption.onCommentClick()
                }
                appRound.setOnClickListener {
                    onItemClickOption.onInfoClick(position)
                }
                /*          transcriptBtn.setOnCheckedChangeListener { compoundButton, b ->
                          if (compoundButton.isChecked) {
                              onItemClickOption.onTranscriptRequest(position)
                          }
                      }*/
                transcriptBtn.setOnClickListener {
                    if (transcriptBtn.isChecked) {
                        onItemClickOption.onTranscriptRequest(position, "1")
                    } else {
                        onItemClickOption.onTranscriptRequest(position, "0")
                    }
                }
                appliedBtn.setOnClickListener {
                    if (appliedBtn.isChecked) {
                        notifyDataSetChanged()
                        onItemClickOption.onApplyingClick(position)
                    } else {
                        notifyDataSetChanged()
                        onItemClickOption.onApplyingClick(position)
                    }
                }
                if (confirmApplied == 1) {
                    appliedBtn.isChecked = true
                } else {
                    appliedBtn.isChecked = false
                }
                if (requestTranscript == "1") {
                    transcriptBtn.isChecked = true
                } else {
                    transcriptBtn.isChecked = false
                }

            var others = ArrayList<String>()
            for (i in 0 until program_data?.size!!) {
                others.add(program_data?.get(i)?.program_name ?: "")
            }
//            val others: considerarray<out String> = root.context.resources.getStringconsiderarray(R.considerarray.spinner_programs)
            val adapter = ArrayAdapter(
                root.context,
                R.layout.spinner_text, others
            )

                allSystem.adapter = adapter

                val canShowRound= ((appByProgramSupported =="0"  ||
                        applicationMode == CommonApp))
                if(canShowRound) {
                    appRound.visibility=View.VISIBLE
                    roundValue.text = applicationRound
                }else {
                    appRound.visibility = View.GONE
                    roundValue.text = root.context.resources.getString(R.string.na)
                }
                if (selectionVisiblility) {
                    selection.visibility = View.VISIBLE
                } else {
                    selection.visibility = View.GONE

                }
                if (selected == true){
                    selection.isChecked = true
                } else
                {
                    selection.isChecked= false
                }


                selection.setOnClickListener{
                    selected = selection.isChecked
                }
        }

    }
    }

    override fun getItemCount(): Int {
        return considerarray.size
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

    fun updateAdapter(consider: ArrayList<ConsiderModel.Data>) {
        prev = ""
        considerarray = consider
        notifyDataSetChanged()
    }
    fun getAppPlan(key:String, position: Int, appDeadline:LinearLayout, deadlineValue:TextView):String?{
        considerarray.get(position).collegeAppLicationType?.collType?.let {
            for (item in it){
                if (item?.term?.type == "term" && item?.term?.collTerm!= null) {
                    for (plan in item?.term?.collTerm!!) {
                        if ( plan?.collPlan!= null) {
                            for (planitem in plan?.collPlan!!) {
                                if (planitem.decision_plan.replaceInvertedComas() == key) {
                                    if (checkNonNull(planitem.deadline_date)){
                                        appDeadline.isEnabled = false
                                        deadlineValue.setText(planitem.deadline_date
                                            ?.let {   formateDateFromstring("yyyy-MM-dd hh:mm:ss","MMM dd yyyy", it) })

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
    fun deadlineClick(string: String){
        onItemClickOption.onDeadlineClick(positio,string)
    }
    fun getAppType(key:String, position: Int):String?{
        considerarray.get(position).collegeAppLicationType?.collType?.let {
            for (item in it){
                if (item.key.replaceInvertedComas().equals(key)){
                    return item.value
                }
            }
        }
        return null
    }
    fun selectionVisibility(b: Boolean) : Boolean{
        selectionVisiblility = b
        notifyDataSetChanged()
        return  b
    }
}

