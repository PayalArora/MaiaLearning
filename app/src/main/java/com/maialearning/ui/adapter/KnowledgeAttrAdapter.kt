package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.CareerAdapterBinding
import com.maialearning.databinding.KnowAttrLayouBinding

class KnowledgeAttrAdapter(var context: Context) : RecyclerView.Adapter<KnowledgeAttrAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: KnowAttrLayouBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = KnowAttrLayouBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (position ==1 ){
            viewHolder.binding.name.text="Biology"
            viewHolder.binding.name1.text="Anesthesiologists, Comm..."
            viewHolder.binding.name.setTextColor(ContextCompat.getColor(context, R.color.green_1))
            viewHolder.binding.name1.setTextColor(ContextCompat.getColor(context, R.color.green_1))
        }


    }

    override fun getItemCount(): Int {
        return 2
    }
}