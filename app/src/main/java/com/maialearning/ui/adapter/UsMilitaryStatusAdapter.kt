package com.maialearning.ui.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemReigonBinding
import com.maialearning.model.IndustryModel
import com.maialearning.model.Region
import com.maialearning.ui.activity.ClickFilters


class UsMilitaryStatusAdapter(val arr:ArrayList<Region>, var click:(ArrayList<Region>)->Unit ) : RecyclerView.Adapter<UsMilitaryStatusAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */


    class ViewHolder(val binding: ItemReigonBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemReigonBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
       // viewHolder. binding.root.setOnClickListener { onItemClick.onClick(position, 1) }
        viewHolder.binding.apply {
            filters.setText(arr.get(position).reigon)

            if (arr.get(position).checked){
                imgCheck.visibility = View.VISIBLE
                filters.setTypeface(filters.typeface, Typeface.BOLD)
            } else {
                imgCheck.visibility = View.GONE
                filters.setTypeface(filters.typeface, Typeface.NORMAL)

            }
            root.setOnClickListener {
                if (arr.get(position).checked){
                    arr.get(position).checked = false
                }  else {
                    arr.get(position).checked = true
                }
                notifyDataSetChanged()
               click(arr)
            }

        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}


