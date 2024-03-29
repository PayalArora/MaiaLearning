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

class DiversityAdapter(val arr: ArrayList<KeyVal>, val onItemClick: ClickFilters) :
    RecyclerView.Adapter<DiversityAdapter.ViewHolder>() {
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
            if (arr.get(position).checked) {
                check.isChecked = true
            }else{
                check.isChecked=false
            }
            check.setOnClickListener({
                for (i in arr.indices) {
                    if (i != position) {
                        arr.get(i).checked = false
                    }
                }
//                if (arr.get(position).checked) {
//                    arr.get(position).checked = false
//                   // check.isChecked=false
//                   // UniversitiesActivity.selectedDiversity = ""
//                } else {
//                    arr.get(position).checked = true
//                   // check.isChecked=true
//                   // UniversitiesActivity.selectedDiversity =  arr.get(position).key
//                }
                arr[position].checked=true
                notifyDataSetChanged();
            })


        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}


