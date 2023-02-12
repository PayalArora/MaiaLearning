package com.maialearning.ui.adapter

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemListFilterBinding
import com.maialearning.databinding.RadiobuttonItemFilterBinding
import com.maialearning.model.KeyVal
import com.maialearning.ui.activity.ClickFilters
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.util.toUpperCase

class YearAdapter(val arr: ArrayList<KeyVal>, val onItemClick: ClickFilters) :
    RecyclerView.Adapter<YearAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: RadiobuttonItemFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = RadiobuttonItemFilterBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            check.setText(arr.get(position).value.toUpperCase())

           // check.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)

            check.setOnClickListener({
                for (i in arr.indices) {
                    if (arr.get(i).checked) {
                        arr.get(i).checked = false
                    } else{
                        arr.get(i).checked = true
                    }
                }

              notifyDataSetChanged();
            })

            if (arr.get(position).checked== true) {
                check.setChecked(true)
                for (k in arr){
                    println("true "+position +k.checked)
                }
            }else{
                check.setChecked(false)
                for (k in arr){
                    println("false "+position +k.checked)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}


