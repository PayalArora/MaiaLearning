package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.UniListItemBinding
import com.maialearning.ui.activity.UniversitiesActivity

class UniFactAdapter(var context:Context,var click: () -> Unit) : RecyclerView.Adapter<UniFactAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: UniListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = UniListItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.list.setOnClickListener {
            click
            ( context as UniversitiesActivity).bottomSheetWork()
        }
        viewHolder.binding.list.setOnClickListener {
            click
            ( context as UniversitiesActivity).bottomSheetWork()
        }

    }

    override fun getItemCount(): Int {
        return 5
    }

}

