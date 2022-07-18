package com.maialearning.ui.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemNotesBinding
import com.maialearning.calbacks.OnItemClick
import com.maialearning.calbacks.OnItemClickType
import com.maialearning.model.NotesModel


class NotesAdapter(val onItemClick: OnItemClickType,val array: NotesModel?) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemNotesBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (array!= null) {
            viewHolder.binding.textTitle.text = array[position].noteTitle
            viewHolder.binding.textDescription.text = Html.fromHtml(array[position].noteDescription)
            viewHolder.binding.textDate.text =
                array[position].noteCreationDate + " " + array[position].authorName
            viewHolder.binding.textDescription.setOnClickListener {
                onItemClick.onClickOpt(position,
                    "root")
            }
            viewHolder.binding.apply {
                commentList.setOnClickListener { onItemClick.onClickOpt(position, "comment") }
            }
        }

    }

    override fun getItemCount(): Int {
        return array?.size?:0
    }

}

