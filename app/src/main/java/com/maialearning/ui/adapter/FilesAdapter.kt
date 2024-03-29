package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.ItemNotesBinding
import com.maialearning.calbacks.OnItemClick
import com.maialearning.calbacks.OnItemClickId
import com.maialearning.databinding.ItemFilesBinding
import com.maialearning.model.AttachMessages


class FilesAdapter(val onItemClick: OnItemClickId,var attachedArray:ArrayList<AttachMessages>) : RecyclerView.Adapter<FilesAdapter.ViewHolder>() {
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
       viewHolder. binding.root.setOnClickListener { onItemClick.onClick(position,attachedArray[position].schoolNid ) }
            viewHolder. binding.textTitle.setText(attachedArray[position].name)
//            viewHolder.binding.note.setImageDrawable( viewHolder. binding.root.context.getDrawable(R.drawable.sheet))

    }

    override fun getItemCount(): Int {
        return attachedArray.size
    }

}

