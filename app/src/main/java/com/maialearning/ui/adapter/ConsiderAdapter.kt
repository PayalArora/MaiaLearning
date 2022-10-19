package com.maialearning.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.ConsideringItemLayBinding
import com.maialearning.databinding.ItemMilestonesBinding
import com.maialearning.databinding.ItemShorcutsBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.ui.fragments.OnItemClickOption
import com.maialearning.util.CommonClass
import com.maialearning.util.UNIV_LOGO_URL
import com.maialearning.util.replaceInvertedComas
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
    var typeVal: String = "UCAS"
    var termVal = "Spring 2022"
    var planVal = "Early Action"

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
            Log.e(
                "countryname ${array[position].country_name}",
                " Size:--${array[position].count.toString()}"
            )
            if (array[position].country_name == "") {
                top.visibility = View.GONE
            } else {
                top.visibility = View.VISIBLE
                countryTxt.text = array[position].country_name
                count.text = array[position].count.toString()
            }
            uniName.text = array[position].naviance_college_name
            if (array[position].created_date != null)
                date.setText(CommonClass.getDate(array[position].created_date.toLong()))
            if (array[position].internal_deadline != null && array[position].internal_deadline != "null")
                textInternalDate.setText(array[position]?.internal_deadline?.toLong()
                    ?.let { CommonClass.getDate(it) })
            name.setText(array[position].created_name)
            typeValue.setText(typeVal)

            if (array[position].college_priority_choice.equals("null")) {
                countTxt.setText("?")
            } else if (array[position].college_priority_choice.equals("1")) {
                countTxt.setText("1st")
            } else if (array[position].college_priority_choice.equals("2")) {
                countTxt.setText("2nd")
            } else {
                countTxt.setText(array[position].college_priority_choice)
            }

            if (array[position].applicationMode != null && !array[position].applicationMode.equals("null")) {
              //  typeValue.setText(array[position].applicationType)
                val typeVal = getAppType(array[position].applicationMode, position)
                typeVal.let { typeValue.setText(it) }
            } else {
                typeValue.setText("Select")

            }
            if (array[position].applicationTerm != null && !array[position].applicationTerm.equals("null") && !array[position].applicationTerm.equals("Reset")) {
                termValue.setText(array[position].applicationTerm)
                appTerm.visibility = View.VISIBLE
            }
            else if (array[position].applicationMode != null && !array[position].applicationMode.equals("null")) {
                    val appMode = array[position].applicationMode
                    for (i in array[position].collegeAppLicationType?.collType?.indices!!) {
                        if (appMode == array[position].collegeAppLicationType?.collType?.get(
                                i
                            )?.key
                        ) {
                            array[position].collegeAppLicationType?.collType?.get(i)?.term?.termList?.let {

                                return
                            }
                            if (array[position].collegeAppLicationType?.collType?.get(i)?.term?.type == "decision") {
                            termValue.setText("Select")
                            appTerm.visibility = View.VISIBLE}
                            else {
                                termValue.setText("Select")
                               // appTerm.visibility = View.GONE
                            }
                        }
                    }
                } else {
                    appTerm.visibility = View.VISIBLE
                    termValue.setText("Select")
                }


            Picasso.with(viewHolder.binding.root.context)
                .load("${UNIV_LOGO_URL}${array[position].country?.toLowerCase()}/${array[position].unitid}/logo_sm.jpg")
                .error(R.drawable.static_coll).into(viewHolder.binding.univIcon)
            Picasso.with(viewHolder.binding.root.context)
                .load("https://countryflagsapi.com/png/${array[position].country}")
                .into(viewHolder.binding.idIVCourse)
            //uniName.text=array[position].naviance_college_name
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
            for (i in 0 until array[position].program_data?.size!!) {
                others.add(array[position].program_data?.get(i)?.program_name ?: "")
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
}

