package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.EthnicityItemBinding
import com.maialearning.databinding.SportsInnerBinding
import com.maialearning.model.EthnicityResponseItem
import com.maialearning.model.ProfileResponse

class EthnicityAdapter(
    val arr: ArrayList<EthnicityResponseItem?>,
    var ethinicityList: ArrayList<String> = arrayListOf<String>(),
    var click: (position: String, check: Boolean) -> Unit
) : RecyclerView.Adapter<EthnicityAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    class ViewHolder(val binding: EthnicityItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = EthnicityItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            notHisponic.setText(arr.get(position)?.name)
            if (ethinicityList!!.contains(arr.get(position)?.name)) {
                notHisponic.isChecked = true
            }
           notHisponic.setOnClickListener {
               arr.get(position)?.name?.let { it1 -> click(it1, notHisponic.isChecked) }
           }
//            rbInenr.setOnCheckedChangeListener { compoundButton, b ->
//                arr.get(position)?.name?.let { it1 -> click(it1, b) }
//            }
        }
    }

    override fun getItemCount(): Int {
        return arr.size
    }

}


