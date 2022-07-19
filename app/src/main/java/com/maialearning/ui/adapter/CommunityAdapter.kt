package com.maialearning.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ComunityAdapterBinding
import com.maialearning.ui.model.CommunityModel

class CommunityAdapter(var listData: ArrayList<CommunityModel>) :
    RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ComunityAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ComunityAdapterBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.name.text = listData[position].name
        viewHolder.binding.percent.text = listData[position].percent
        viewHolder.binding.percent.setTextColor(Color.parseColor(listData[position].color))
        DrawableCompat.setTint(
            viewHolder.binding.percent.background,
            Color.parseColor((listData[position].color))
        )
//            viewHolder.binding.percent.background.colorFilter= Color.parseColor((listData[position].color))
        viewHolder.binding.count.text = listData[position].text
    }

    override fun getItemCount(): Int {
        return listData.size
    }

}