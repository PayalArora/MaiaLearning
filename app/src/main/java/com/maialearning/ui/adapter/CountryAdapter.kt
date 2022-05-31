package com.maialearning.ui.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemNotesBinding
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ItemCountryFilterBinding
import com.maialearning.databinding.ItemUnivFilterBinding
import com.maialearning.ui.activity.ClickFilters
import java.time.format.TextStyle


class CountryAdapter(val arr:Array<String>, val onItemClick: ClickFilters) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    var checked:Array<Boolean> = arrayOf(false, false, false,false,false,false,false,false)
    class ViewHolder(val binding: ItemCountryFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemCountryFilterBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
       // viewHolder. binding.root.setOnClickListener { onItemClick.onClick(position, 1) }
        viewHolder.binding.apply {
            filters.setText(arr.get(position))
            if (checked.get(position)){
                imgCheck.visibility = View.VISIBLE
                filters.setTypeface(filters.typeface, Typeface.BOLD)
            } else {
                imgCheck.visibility = View.GONE
                filters.setTypeface(filters.typeface, Typeface.NORMAL)

            }


            root.setOnClickListener {
                for (i in checked.indices) {
                   checked[i] = false
                }
                checked[position] =true
                notifyDataSetChanged()
            }

        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}


