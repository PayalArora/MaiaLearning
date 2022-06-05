package com.maialearning.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.CampusAdapterBinding

class CampusAdapter : RecyclerView.Adapter<CampusAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: CampusAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = CampusAdapterBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (position ==0 || position ==3){
        viewHolder.binding.service.append("1")
        }else{
            viewHolder.binding.service.append("2")
        }
        DrawableCompat.setTint(viewHolder.binding.layout.background, Color.parseColor("#F5F5F5"))


    }

    override fun getItemCount(): Int {
        return 6
    }
}