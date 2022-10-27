package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.EntryRequirementRowLayoutBinding
import com.maialearning.databinding.FeesFundingRowBinding
import com.maialearning.model.QualificationTableItem
import com.maialearning.model.TuitionFeesItem

class FeesAdapter (var list: ArrayList<TuitionFeesItem>) :
    RecyclerView.Adapter<FeesAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: FeesFundingRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = FeesFundingRowBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.countryTxt.text = list.get(position).locale
        viewHolder.binding.feesText.text = "$"+list.get(position).courseFees
        viewHolder.binding.yearTxt.text = "$"+list.get(position).feeDurationPeriod

    }

    override fun getItemCount(): Int {
        return list.size
    }

}

