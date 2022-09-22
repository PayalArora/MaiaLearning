package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.SummarySubLayoutBinding
import com.maialearning.databinding.TraficSubAdapBinding
import com.maialearning.model.AcademicKnowledgeItem
import com.maialearning.model.InterestItem
import com.maialearning.model.SkillItem
import com.maialearning.model.WorkActivitiesItem

class WorkActivityAdapter  (val arr:ArrayList<WorkActivitiesItem?>?, var arrA:ArrayList<AcademicKnowledgeItem?>?, var arrS:ArrayList<SkillItem?>?, var arrI:ArrayList<InterestItem?>?, var type:String) : RecyclerView.Adapter<WorkActivityAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: SummarySubLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = SummarySubLayoutBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if(type == "work") {
            viewHolder.binding.name.text = arr?.get(position)?.title!!
                    viewHolder.binding.detail.text = arr[position]?.description
        }else  if(type == "skill") {
            viewHolder.binding.name.text = arrS?.get(position)?.title!!
            viewHolder.binding.detail.text = arrS!![position]?.description
          }else  if(type == "inter") {
            viewHolder.binding.name.text = arrI?.get(position)?.title!!
            viewHolder.binding.detail.text = arrI!![position]?.description
        }else{
            viewHolder.binding.name.text = arrA?.get(position)?.title!!
            viewHolder.binding.detail.text = arrA!![position]?.description
        }
    }

    override fun getItemCount(): Int {
        return if(type == "work"){
            arr!!.size
        }else if (type == "skill"){
            arrS!!.size
        }else if (type == "inter"){
            arrI!!.size
        }else{
            arrA!!.size
        }

    }

}
