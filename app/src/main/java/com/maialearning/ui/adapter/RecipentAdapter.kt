package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemNotesBinding
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ReciepentItemBinding
import com.maialearning.model.ReceipentModel


class RecipentAdapter(val onItemClick: OnItemClick,var list:ArrayList<ReceipentModel>) :
    RecyclerView.Adapter<RecipentAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ReciepentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ReciepentItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.checkName.text = list[position].name
        viewHolder.binding.root.setOnClickListener { onItemClick.onClick(position) }
        if (isSelected) {
            viewHolder.binding.checkName.isChecked = true
        } else {
            viewHolder.binding.checkName.isChecked = false
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun selectAll(checked: Boolean) {
        isSelected = checked
        notifyDataSetChanged()

    }

}

