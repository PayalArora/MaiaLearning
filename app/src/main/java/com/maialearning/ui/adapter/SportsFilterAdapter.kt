package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.CheckboxRowBinding
import com.maialearning.databinding.SportsRowBinding


class SportsFilterAdapter(val arr: Array<String>) :
    RecyclerView.Adapter<SportsFilterAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    val array: Array<String> = arrayOf("Division 1", "Division 2")
    var visibility: Array<Boolean> = arrayOf(false, false, false, false, false)

    class ViewHolder(val binding: SportsRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = SportsRowBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            rbInenr.setText(arr.get(position))
            if (position == 2 || position == 3) {
                arrow.visibility = View.VISIBLE

            }
            for (i in array) {
                val inflater =
                    root.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val binding = CheckboxRowBinding.inflate(inflater)
                layoutRecycler.addView(binding.root)
            }
            arrow.setOnClickListener {
                if (layoutRecycler.isVisible)
                    layoutRecycler.visibility = View.GONE
                else
                    layoutRecycler.visibility = View.VISIBLE
            }
            rbInenr.setOnClickListener {
                for (i in array.indices) {
                    val radioButton =
                        layoutRecycler.getChildAt(i).findViewById<RadioButton>(R.id.rb_inenr)
                    if (rbInenr.isChecked) {
                        radioButton.isChecked = true
                    } else {
                        radioButton.isChecked = false
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}


