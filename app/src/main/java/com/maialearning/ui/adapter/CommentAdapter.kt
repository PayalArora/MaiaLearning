package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemNotesBinding
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ItemCommentBinding
import com.maialearning.databinding.ReciepentItemBinding


class CommentAdapter(val onItemClick: OnItemClick) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemCommentBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.root.setOnClickListener { onItemClick.onClick(position) }

    }

    override fun getItemCount(): Int {
        return 4
    }
}

