package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.SearchCareerAdapterBinding

class SearchProgramAdapter (var context: Context,var click:  (position: Int) -> Unit) : RecyclerView.Adapter<SearchProgramAdapter.ViewHolder>() {
    var isSelected = false
var count=0
    var selectedPostion = -1
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
        viewHolder.binding.lay.setOnClickListener {
            click(position)
        }
        viewHolder.binding.button.setOnClickListener {
            if(viewHolder.binding.button.isChecked){
            viewHolder.binding.lay.background =
                ContextCompat.getDrawable(context, R.drawable.back_stroke_selected)
            }else{
                viewHolder.binding.lay.background =
                    ContextCompat.getDrawable(context, R.drawable.bg_white_rect)
            }

        }

    }

    override fun getItemCount(): Int {
        return 4
    }
}