package com.maialearning.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.OverdueItemRowBinding
import com.maialearning.model.AssignmentItem
import com.maialearning.model.SortedDateModel
import com.maialearning.util.getDate

class OverDueHeadAdapter (var overdueList: ArrayList<SortedDateModel>, val onItemClick: OnItemClick
) :
RecyclerView.Adapter<OverDueHeadAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: OverdueItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = OverdueItemRowBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.setOnClickListener { onItemClick.onClick(position) }
        viewHolder.binding.dateTxt.setText(overdueList.get(position).date)
        overdueList.get(position).assignment?.let { viewHolder.binding.countTxt.setText(""+it.size) }
        viewHolder.binding.assignmentRow.adapter=OverDueAdapter(overdueList.get(position).assignment as ArrayList<AssignmentItem>)
    }

    override fun getItemCount(): Int {
        return overdueList.size
    }

}

