package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.calbacks.OnItemClick
import com.maialearning.calbacks.OnItemClickDelete
import com.maialearning.databinding.ItemNotesBinding
import com.maialearning.databinding.ItemShorcutsBinding
import com.maialearning.databinding.LayoutMessageBinding
import com.maialearning.model.InboxResponse

class MessageAdapter(val onItemClick: OnItemClickDelete, val array:ArrayList<InboxResponse.MessagesItem>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = LayoutMessageBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder. binding.root.setOnClickListener { onItemClick.onClick(position) }

    }

    override fun getItemCount(): Int {
        return array.size
    }


}

