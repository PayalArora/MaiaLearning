package com.maialearning.ui.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemMilestonesBinding
import com.maialearning.databinding.ItemShorcutsBinding
import com.maialearning.model.ItaskItem
import com.maialearning.util.getDate

class MilestonesAdapter(var itask: List<ItaskItem?>?) :
    RecyclerView.Adapter<MilestonesAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ItemMilestonesBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemMilestonesBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.textTitle.text = itask?.get(position)?.title
        viewHolder.binding.textDescription.text = Html.fromHtml(itask?.get(position)?.body)
        viewHolder.binding.textDate.text = itask?.get(position)?.date?.toLong()
            ?.let { getDate(it, "MMM dd, yyyy") }
        if (itask?.get(position)?.status == 1) {
            viewHolder.binding.rbOtherApp.visibility = View.VISIBLE
        } else {
            viewHolder.binding.rbOtherApp.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return itask?.size ?: 0
    }

}

