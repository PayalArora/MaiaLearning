package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemNotesBinding
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ItemUnivFilterBinding
import com.maialearning.ui.activity.ClickFilters


class UnivFilterAdapter(val arr:Array<String>, val onItemClick: ClickFilters) : RecyclerView.Adapter<UnivFilterAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ItemUnivFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemUnivFilterBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder. binding.root.setOnClickListener { onItemClick.onClick(position, 2) }
        viewHolder.binding.apply {
            filters.setText(arr.get(position))
            if (position == 0){
                flagImg.visibility = View.VISIBLE
                flagText.visibility = View.GONE
            } else if (position ==1){
                flagImg.visibility = View.GONE
                flagText.visibility = View.VISIBLE
            } else {
                flagImg.visibility = View.GONE
                flagText.visibility = View.GONE
            }
        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}

