package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.KnowSubListBinding

class KnowSubListAdapter(var type:String) : RecyclerView.Adapter<KnowSubListAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: KnowSubListBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = KnowSubListBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
      if(type == "2"){
          if (position == 0) {
              viewHolder.binding.name.text =
                  "My Personal Abilities"
          }
          if (position ==1) {
              viewHolder.binding.name.text =
                  "Career Areas Where my Abilities Will be Useful"
          }
      }
        if (position == 3) {
            viewHolder.binding.name.text =
                "1c. Work Preference : Working with People, Ideas, and Things"
        }
    }

    override fun getItemCount(): Int {
        if(type=="2"){
            return 2
        }else{
            return 3
        }

    }
}