package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ConsideringTypeTermRowBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.ui.fragments.ClickOptionFilters

class ConsiderDecisionAdapter (var listData: ArrayList<ConsiderModel.DecisionPlan>,
                               val type: Int,
                               val onItemClick: ClickOptionFilters
) :
RecyclerView.Adapter<ConsiderDecisionAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ConsideringTypeTermRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ConsideringTypeTermRowBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.valueTxt.text = listData[position].label
        viewHolder.binding.valueTxt.setOnClickListener {

            onItemClick.onPlanOptionClick(position,type,listData.get(position).id, listData.get(position).label)
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}

