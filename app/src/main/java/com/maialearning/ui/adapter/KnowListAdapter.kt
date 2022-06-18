package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.KnowAdapterLayBinding

class KnowListAdapter(var context:Context ,var size:Int)  : RecyclerView.Adapter<KnowListAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: KnowAdapterLayBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = KnowAdapterLayBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if(position==1){
            viewHolder.binding.name.text="Abilities: List personal skills and talents that will be helpful in a career choice"
        }
        viewHolder.binding.subList.layoutManager=
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        viewHolder.binding.subList.adapter = KnowSubListAdapter(position.toString())
    }

    override fun getItemCount(): Int {
        return size
    }
}