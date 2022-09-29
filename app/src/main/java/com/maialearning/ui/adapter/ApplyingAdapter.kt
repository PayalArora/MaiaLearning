package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.ApplyingItemLayBinding
import com.maialearning.databinding.ConsideringItemLayBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.ui.fragments.OnItemClickOption
import com.maialearning.util.CommonClass
import com.maialearning.util.UNIV_LOGO_URL
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
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.apply {
            if (considerarray[position].country_name == "") {
                top.visibility = View.GONE
            } else {
                top.visibility = View.VISIBLE
                countryTxt.text = considerarray[position].country_name
                count.text = considerarray[position].count.toString()
            }
            uniName.text = considerarray[position].naviance_college_name
            date.setText(CommonClass.getDate(considerarray[position].created_date.toLong()))
            if (considerarray[position].internal_deadline != null && considerarray[position].internal_deadline != "null")
                textInternalDate.setText(considerarray[position].internal_deadline?.toLong()
                    ?.let { CommonClass.getDate(it) })
            name.setText(" by: " + considerarray[position].created_name)
            typeValue.setText(typeVal)
            termValue.setText(termVal)
            planValue.setText(planVal)
            Picasso.with(viewHolder.binding.root.context)
                .load("$UNIV_LOGO_URL${considerarray[position].country?.toLowerCase()}/${considerarray[position].unitid}/logo_sm.jpg")
                .error(R.drawable.static_coll).into(viewHolder.binding.univIcon)

            Picasso.with(viewHolder.binding.root.context)
                .load("https://countryflagsapi.com/png/${considerarray[position].country}")
                .into(viewHolder.binding.idIVCourse)

            appTerm.setOnClickListener {
                onItemClickOption.onTermClick()

            }
            appType.setOnClickListener {
                onItemClickOption.onTypeClick()

            }
            addButton.setOnClickListener {
                onItemClickOption.onAddClick(position)
            }
            appPlan.setOnClickListener {
                onItemClickOption.onPlanClick()
            }
            menuDots.setOnClickListener {
                onItemClickOption.onMenuClick(position,it)
            }
            commentImg.setOnClickListener {
                onItemClickOption.onCommentClick()
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
            if (considerarray[position].requestTranscript == "1") {
                transcriptBtn.isChecked = true
            } else {
                transcriptBtn.isChecked = false
            }

            var others = ArrayList<String>()
            for (i in 0 until considerarray[position].program_data?.size!!) {
                others.add(considerarray[position].program_data?.get(i)?.program_name ?: "")
            }
//            val others: considerarray<out String> = root.context.resources.getStringconsiderarray(R.considerarray.spinner_programs)
            val adapter = ArrayAdapter(
                root.context,
                R.layout.spinner_text, others
            )
            allSystem.adapter = adapter
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
        considerarray = consider
        notifyDataSetChanged()
    }
}

