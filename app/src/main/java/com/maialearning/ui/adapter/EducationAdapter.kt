package com.maialearning.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.AgeItemBinding
import com.maialearning.databinding.CampusAdapterBinding
import com.maialearning.databinding.ClusterAdapterBinding
import com.maialearning.model.CareerFactsheetResponse
import com.maialearning.util.format
import kotlin.math.roundToInt

class EducationAdapter(var list:  ArrayList<CareerFactsheetResponse.EducationBreakdownItem>?, var arr:Array<Int>)  : RecyclerView.Adapter<EducationAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: AgeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = AgeItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
         val binding = viewHolder.binding
        val item = list?.get(position)
        binding.apply {
            ageTxt.text = item?.group
            item?.percent?.let {
                val roundoff = (it * 100.0).roundToInt() / 100.0
                jobPerTxt.text = "${roundoff}%"
            }
            jobTxt.text = item?.jobs?.let { format(it) }
        }
        DrawableCompat.setTint(
            DrawableCompat.wrap(viewHolder.binding.bullet.getDrawable()),
            ContextCompat.getColor(binding.root.context,arr[position])
        );

    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }
}