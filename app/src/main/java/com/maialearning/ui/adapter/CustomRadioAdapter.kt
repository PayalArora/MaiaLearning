package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.CheckboxRowBinding
import com.maialearning.databinding.ProgramItemRowBinding


class CustomRadioAdapter() : RecyclerView.Adapter<CustomRadioAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ProgramItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ProgramItemRowBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
       viewHolder.binding.apply {

           rgProgram.orientation = LinearLayout.VERTICAL
            for (i in 1..5) {
                val inflater =
                    root.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val binding = CheckboxRowBinding.inflate(inflater)

                // binding.rbInner.id = position + i
               rgProgram.addView(binding.root)
           }
           headerTitle.setOnClickListener {
               for (i in 1..5) {
               val radioButton = rgProgram.getChildAt(i).findViewById<RadioButton>(R.id.rb_inner)
                   if (headerTitle.isChecked){
                       radioButton.isChecked = true
                   } else {
                       radioButton.isChecked = false
                   }
               }
           }
       }

    }

    override fun getItemCount(): Int {
        return 6
    }

}

