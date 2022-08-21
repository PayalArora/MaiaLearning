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

class ApplyingAdapter (val onItemClickOption: OnItemClickOption,var array :ArrayList<ConsiderModel.Data>) :
RecyclerView.Adapter<ApplyingAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    var typeVal: String = "UCAS"
    var termVal = "Spring 2022"
    var planVal = "Early Action"

    class ViewHolder(val binding: ApplyingItemLayBinding    ) :
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
            if(array[position].country_name==""){
                top.visibility= View.GONE
            }else{
                top.visibility= View.VISIBLE
                countryTxt.text = array[position].country_name
                count.text = array[position].count.toString()
            }
            uniName.text=array[position].naviance_college_name
            date.setText(CommonClass.getDate(array[position].created_date.toLong()))
            if (array[position].internal_deadline!=null && array[position].internal_deadline!="null")
                textInternalDate.setText(array[position].internal_deadline?.toLong()
                    ?.let { CommonClass.getDate(it) })
            name.setText(" by: "+array[position].created_name)
            typeValue.setText(typeVal)
            termValue.setText(termVal)
            planValue.setText(planVal)
            Picasso.with(viewHolder.binding.root.context).
            load("$UNIV_LOGO_URL${array[position].country?.toLowerCase()}/${array[position].unitid}/logo_sm.jpg").error(R.drawable.static_coll).into(viewHolder.binding.univIcon)

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
            commentImg.setOnClickListener {
                onItemClickOption.onCommentClick()
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

