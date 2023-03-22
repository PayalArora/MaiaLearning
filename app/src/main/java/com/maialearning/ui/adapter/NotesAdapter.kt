package com.maialearning.ui.adapter

import android.annotation.SuppressLint
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.calbacks.OnItemClickType
import com.maialearning.databinding.ItemNotesBinding
import com.maialearning.model.NotesModel
import com.maialearning.util.*


class NotesAdapter(val onItemClick: OnItemClickType, val array: List<NotesModel.DataItem?>?) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
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
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (array != null) {
            viewHolder.binding.textTitle.text = array[position]?.title
            var name =""
            if (checkNonNull(array.get(position)?.user?.salutation)){
                name = "${array.get(position)?.user?.salutation}"
            }
            if (checkNonNull(array.get(position)?.user?.lastName)){
                name = "$name ${array.get(position)?.user?.lastName}"
            }
            if (checkNonNull(array.get(position)?.user?.firstName)){
                name = "$name,${array.get(position)?.user?.firstName}"
            }
            viewHolder.binding.textDescription.text = name
                // viewHolder. binding.textDate.text= getDateTime(array[position].sentTimestamp!!,"MMM dd YYYY, hh:mm a")
            viewHolder.binding.textDate.text =
                array.get(position)?.created?.toLong()?.let {
                    getDate(
                        it, "MMM dd yyyy"
                    )
                }
            viewHolder.binding.detail.setOnClickListener {
                onItemClick.onClickOpt(
                    position,
                    "root"
                )
            }
            viewHolder.binding.apply {
                commentList.setOnClickListener { onItemClick.onClickOpt(position, "comment") }
            }
        }

    }

    override fun getItemCount(): Int {
        return array?.size ?: 0
    }

}

