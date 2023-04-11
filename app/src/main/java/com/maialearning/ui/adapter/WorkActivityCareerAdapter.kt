package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemActivitiesBinding
import com.maialearning.databinding.SummarySubLayoutBinding
import com.maialearning.databinding.TraficSubAdapBinding
import com.maialearning.model.AcademicKnowledgeItem
import com.maialearning.model.InterestItem
import com.maialearning.model.SkillItem
import com.maialearning.model.WorkActivitiesItem

class WorkActivityCareerAdapter  (val arr:ArrayList<String>?) : RecyclerView.Adapter<WorkActivityCareerAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ItemActivitiesBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemActivitiesBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.name.text = arr?.get(position)
    }

    override fun getItemCount(): Int {
        return arr?.size ?: 0

    }

}
