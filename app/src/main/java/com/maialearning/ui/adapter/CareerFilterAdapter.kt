package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.CareerFilterItemRowBinding
import com.maialearning.model.CareerClusterModel


class CareerFilterAdapter(
    val context: Context,
    var arrayListOut: Array<String>,    var click:(item:String)->Unit
) : RecyclerView.Adapter<CareerFilterAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: CareerFilterItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = CareerFilterItemRowBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.name.text = arrayListOut.get(position)
        viewHolder.binding.name.setOnClickListener {click(arrayListOut.get(position))  }
    }

    override fun getItemCount(): Int {
        return arrayListOut.size
    }

}