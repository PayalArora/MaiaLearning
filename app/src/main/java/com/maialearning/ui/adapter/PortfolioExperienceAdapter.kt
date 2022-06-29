package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.DegreeAdapterBinding
import com.maialearning.databinding.ExperienceItemBinding

class PortfolioExperienceAdapter () : RecyclerView.Adapter<PortfolioExperienceAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ExperienceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ExperienceItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if(position==1){
            viewHolder.binding.waitingLay.visibility=View.VISIBLE
            viewHolder.binding.deleteLay.visibility=View.GONE

        }else{
            viewHolder.binding.waitingLay.visibility=View.GONE
            viewHolder.binding.deleteLay.visibility=View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

}
