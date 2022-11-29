package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemListFilterBinding
import com.maialearning.model.KeyVal
import com.maialearning.ui.activity.ClickFilters
import com.maialearning.ui.activity.UniversitiesActivity

class MultiSelectionAdapter(val arr: ArrayList<KeyVal>, val onItemClick: ClickFilters) :
    RecyclerView.Adapter<MultiSelectionAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ItemListFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemListFilterBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            check.setText(arr.get(position).value)


            check.setOnClickListener({
                if (arr.get(position).checked) {
                    arr.get(position).checked = false
                    check.isChecked=false
                   // UniversitiesActivity.selectedDiversity = ""
                } else {
                    arr.get(position).checked = true
                    check.isChecked=false
                   // UniversitiesActivity.selectedDiversity =  arr.get(position).key
                }
                notifyDataSetChanged();
            })
            if (arr.get(position).checked) {
                check.isChecked = true
            }else{
                check.isChecked=false
            }
        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}


