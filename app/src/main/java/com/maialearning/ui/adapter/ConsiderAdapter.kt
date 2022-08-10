package com.maialearning.ui.adapter

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

class ConsiderAdapter(val onItemClickOption: OnItemClickOption,var array :ArrayList<ConsiderModel.Data>, val notesClick:(data: ConsiderModel.Data)->Unit) :
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
            if(array[position].country_name==""){
                top.visibility=View.GONE
            }else{
                top.visibility=View.VISIBLE
                countryTxt.text = array[position].country_name
                count.text = array[position].count.toString()
            }
            uniName.text=array[position].naviance_college_name
            if (array[position].created_date!=null)
            date.setText(CommonClass.getDate(array[position].created_date.toLong()))
            if (array[position].internal_deadline!=null && array[position].internal_deadline!="null")
            textInternalDate.setText(array[position]?.internal_deadline?.toLong()
                ?.let { CommonClass.getDate(it) })
            name.setText(array[position].created_name)
            typeValue.setText(typeVal)
            termValue.setText(termVal)
            planValue.setText(planVal)

            //uniName.text=array[position].naviance_college_name
            appTerm.setOnClickListener {
                onItemClickOption.onTermClick()

            }
            appType.setOnClickListener {
                onItemClickOption.onTypeClick()

            }
            appPlan.setOnClickListener {
                onItemClickOption.onPlanClick()
            }
            commentImg.setOnClickListener {
                onItemClickOption.onCommentClick()
            }
            addButton.setOnClickListener {
                onItemClickOption.onAddClick()
            }
            val others= ArrayList<String>()
            for (i in 0 until array[position].program_data?.size!!){
                others.add(array[position].program_data?.get(i)?.program_name?:"")
            }
//            val others: Array<out String> = root.context.resources.getStringArray(R.array.spinner_programs)
            val adapter = ArrayAdapter(
                root.context,
                R.layout.spinner_text, others
            )
            allSystem.adapter = adapter
            commentImg.setOnClickListener { notesClick(array[position]) }
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

}

