package com.maialearning.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemShorcutsBinding
import com.maialearning.model.ShortcutModel


class ShortcutAdapter(var context: Context, var array: ArrayList<ShortcutModel>) : RecyclerView.Adapter<ShortcutAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ItemShorcutsBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemShorcutsBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


            viewHolder.binding.textUpcomingVisits.text = array.get(position).name

        viewHolder.binding.textDescription.text = "N/A"
        viewHolder.binding.layout.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse( array.get(position).link))
                context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return array.size
    }

}

