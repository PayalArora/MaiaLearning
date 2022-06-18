package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.SearchCareerAdapterBinding

class SearchProgramAdapter (var context: Context,var click:  (position: Int) -> Unit) : RecyclerView.Adapter<SearchProgramAdapter.ViewHolder>() {
    var isSelected = false
var count=0
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: SearchCareerAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = SearchCareerAdapterBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.button.setOnClickListener {
            count=count+1
            if(count>1){
            click(position)
            }

        }

    }

    override fun getItemCount(): Int {
        return 4
    }
}