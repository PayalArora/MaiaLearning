package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemUnivFilterBinding
import com.maialearning.ui.activity.ClickFilters
import com.maialearning.ui.fragments.ClickOptionFilters
import com.maialearning.util.prefhandler.SharedHelper
import com.squareup.picasso.Picasso

class ConsiderFilterAdapter(val arr:Array<String>,val clickMainCosideringFilter: (position: Int, flag: ImageView)->Unit) : RecyclerView.Adapter<ConsiderFilterAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ItemUnivFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemUnivFilterBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder. binding.root.setOnClickListener { clickMainCosideringFilter(position, viewHolder.binding.flagImg) }
        viewHolder.binding.apply {
            filters.setText(arr.get(position))
           if (position ==1){
                flagImg.visibility = View.GONE
                flagText.visibility = View.GONE
            } else {
                flagImg.visibility = View.GONE
                flagText.visibility = View.GONE
            }
//            Picasso.with(root.context)
//                .load("https://countryflagsapi.com/png/${SharedHelper(root.context).country}")
//                .into(flagImg)


        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}