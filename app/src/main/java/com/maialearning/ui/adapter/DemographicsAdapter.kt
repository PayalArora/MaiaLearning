package com.maialearning.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.CampusAdapterBinding
import com.maialearning.databinding.ClusterAdapterBinding
import com.maialearning.databinding.ItemActivitiesBinding
import com.maialearning.databinding.RelatedCareersBinding
import com.maialearning.model.CareerFactsheetResponse

class DemographicsAdapter(var list: ArrayList<CareerFactsheetResponse.RelatedCareersItem>?)  : RecyclerView.Adapter<DemographicsAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ItemActivitiesBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemActivitiesBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.binding.name.text=list?.get(position)?.name
        viewHolder.binding.name.setTextColor(viewHolder.binding.root.context.getColor(R.color.blue))
        //DrawableCompat.setTint(viewHolder.binding.layout.background, Color.parseColor("#F5F5F5"))


    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }
}