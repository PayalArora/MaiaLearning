package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.TraficSubAdapBinding
import com.maialearning.ui.activity.ClickFilters

class TraficSubAdapter (val arr:ArrayList<String?>) : RecyclerView.Adapter<TraficSubAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: TraficSubAdapBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = TraficSubAdapBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder. binding.detail.text=arr[position]

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}
