package com.maialearning.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.AdmissionAdapterBinding


class AdmissionAdapter(var context: Context) : RecyclerView.Adapter<AdmissionAdapter.ViewHolder>() {
    var selectedPostion = 0

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: AdmissionAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = AdmissionAdapterBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (position == 0)
            viewHolder.binding.name.text = "All"
//            DrawableCompat.setTint(viewHolder.binding.lay.background, Color.parseColor("#3743BE"))
//        }else
//        DrawableCompat.setTint(viewHolder.binding.lay.background, Color.parseColor("#EAEBF0"))

        if (position == 1)
            viewHolder.binding.name.text = "ED"
        if (position == 2)
            viewHolder.binding.name.text = "EA"
        if (position == 3)
            viewHolder.binding.name.text = "ROLL"
        if (position == 4)
            viewHolder.binding.name.text = "RD"
        viewHolder.binding.lay.setOnClickListener {
            selectedPostion = position
            viewHolder.binding.lay.background =
                ContextCompat.getDrawable(context, R.drawable.back_stroke_selected)
            notifyDataSetChanged()
        }
        if (position == selectedPostion) {
            viewHolder.binding.lay.background =
                ContextCompat.getDrawable(context, R.drawable.back_stroke_selected)
        } else {
            viewHolder.binding.lay.background =
                ContextCompat.getDrawable(context, R.drawable.bg_white_rect)

        }
    }

    override fun getItemCount(): Int {
        return 5
    }

}