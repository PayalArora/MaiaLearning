package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemListFilterBinding
import com.maialearning.model.UniersitiesListModel
import com.maialearning.ui.activity.ClickFilters

class UniversityListAdapter(
    val arr: MutableList<UniersitiesListModel>,
    val onItemClick: ClickFilters
) : RecyclerView.Adapter<UniversityListAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ItemListFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemListFilterBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            check.setText(arr.get(position).name)

            check.setOnCheckedChangeListener { compoundButton, b -> arr.get(position).selected = b }
        }


    }

    override fun getItemCount(): Int {
        return arr.size
    }

}


