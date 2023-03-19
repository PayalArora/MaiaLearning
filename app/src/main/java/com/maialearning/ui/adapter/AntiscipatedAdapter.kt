package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.AnticipatedRowItemBinding
import com.maialearning.databinding.KnowAttrLayouBinding
import com.maialearning.model.AbilityItem
import com.maialearning.model.AntiscipatedModel
import com.maialearning.model.KnowledgeItem

class AntiscipatedAdapter(var context: Context, val model: AntiscipatedModel) :
    RecyclerView.Adapter<AntiscipatedAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: AnticipatedRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = AnticipatedRowItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = model.collegeCostCompare?.get(position)
        viewHolder.binding.apply {
            college.setText(model.name?.capitalize())
            title.setText(item?.key?.capitalize()?:"")
            subList.adapter= item?.keyValue?.let { AntiscipatedSubAdapter(context, it) }
        }

    }

    override fun getItemCount(): Int {
        return model.collegeCostCompare?.size ?: 0
    }
}