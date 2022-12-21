package com.maialearning.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.ApplyingItemLayBinding
import com.maialearning.databinding.CompareAllItemBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.util.*
import com.squareup.picasso.Picasso

class CompareAllAdapter(
    var considerarray: ArrayList<ConsiderModel.Data>
) :
    RecyclerView.Adapter<CompareAllAdapter.ViewHolder>() {

    class ViewHolder(val binding: CompareAllItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = CompareAllItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.apply {
            considerarray.apply {
                wgpaValue.text = considerarray.get(position).collegeCompare?.gpa
                satValue.text = considerarray.get(position).collegeCompare?.sat1600
                actValue.text = considerarray.get(position).collegeCompare?.act
                statusValue.text = considerarray.get(position)?.status
                name.text = considerarray.get(position).naviance_college_name

            }

        }
    }

    override fun getItemCount(): Int {
        return considerarray.size
    }


}

