package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.calbacks.OnItemClick
import com.maialearning.calbacks.OnItemClickDelete
import com.maialearning.calbacks.OnItemClickId
import com.maialearning.databinding.ItemNotesBinding
import com.maialearning.databinding.ItemShorcutsBinding
import com.maialearning.databinding.LayoutMessageBinding
import com.maialearning.model.InboxResponse
import com.maialearning.util.CommonClass
import com.maialearning.util.getDateTime

class MessageAdapter(val onItemClick: OnItemClickId, val array:ArrayList<InboxResponse.MessagesItem>, val type:Int = 0) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

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
        if (type == 1){
            viewHolder. binding.textTitle.visibility = View.GONE
        }
        viewHolder. binding.textTitle.text=array[position].senderName
        viewHolder. binding.textDate.text= getDateTime(array[position].sentTimestamp!!,"MMM dd YYYY, hh:mm a")
        viewHolder. binding.textDescription.text=array[position].subject
        viewHolder. binding.root.setOnClickListener { onItemClick.onClick(position,array[position].messageId?:"") }

    }

    override fun getItemCount(): Int {
        return array.size
    }


}

