package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.CareerFilterItemRowBinding
import com.maialearning.model.CareerClusterModel
import com.maialearning.model.IndustryModel

class IndustryClusterAdapter(
    val context: Context,
    var arrayListOut: ArrayList<IndustryModel>,    var click:(item:IndustryModel, type:String)->Unit, val type:String
) : RecyclerView.Adapter<IndustryClusterAdapter.ViewHolder>() {

    class ViewHolder(val binding: CareerFilterItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): IndustryClusterAdapter.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = CareerFilterItemRowBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: IndustryClusterAdapter.ViewHolder, position: Int) {
        viewHolder.binding.name.text = arrayListOut.get(position).name
        viewHolder.binding.name.setOnClickListener {click(arrayListOut.get(position), type)  }
    }

    override fun getItemCount(): Int {
        return arrayListOut.size
    }
}