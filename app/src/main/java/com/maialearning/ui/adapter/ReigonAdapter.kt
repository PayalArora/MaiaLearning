package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemReigonBinding
import com.maialearning.ui.activity.ClickFilters


class ReigonAdapter(val arr:Array<String>, val onItemClick: ClickFilters) : RecyclerView.Adapter<ReigonAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    var checked:Array<Boolean> = arrayOf(false, false, false,false,false,false,false,false)
    class ViewHolder(val binding: ItemReigonBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemReigonBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
       // viewHolder. binding.root.setOnClickListener { onItemClick.onClick(position, 1) }
        viewHolder.binding.apply {
            filters.setText(arr.get(position))
        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}


