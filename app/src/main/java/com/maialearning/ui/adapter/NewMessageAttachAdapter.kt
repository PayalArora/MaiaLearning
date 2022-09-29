package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.calbacks.OnItemClickId
import com.maialearning.databinding.ItemFilesBinding
import com.maialearning.model.AttachMessages
import com.maialearning.model.AttachmentModel

class NewMessageAttachAdapter (val onItemClick: OnItemClickId, var attachedArray:ArrayList<AttachmentModel>) : RecyclerView.Adapter<NewMessageAttachAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ItemFilesBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemFilesBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        viewHolder. binding.root.setOnClickListener { onItemClick.onClick(position,attachedArray[position].url) }
        viewHolder. binding.textTitle.setText(attachedArray[position].filename)
        viewHolder.binding.menu.visibility= View.GONE
        viewHolder.binding.deleteBtn.visibility=View.VISIBLE
        viewHolder.binding.deleteBtn.setOnClickListener {
            onItemClick.onClick(position,"")
        }

//            viewHolder.binding.note.setImageDrawable( viewHolder. binding.root.context.getDrawable(R.drawable.sheet))

    }

    override fun getItemCount(): Int {
        return attachedArray.size
    }

}

