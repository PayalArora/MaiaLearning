package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.EthnicityItemBinding
import com.maialearning.model.FmTagsResponseItem

class TagAdapter(
    var tagList: ArrayList<FmTagsResponseItem?> = arrayListOf(),
    var selectedList: ArrayList<String?> = arrayListOf<String?>(),
    var click: (position: String, check: Boolean) -> Unit
) : RecyclerView.Adapter<TagAdapter.ViewHolder>() {
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
            notHisponic.setText(tagList.get(position)?.name)
            if (selectedList!!.contains(tagList.get(position)?.id)) {
                notHisponic.isChecked = true
            }
            notHisponic.setOnClickListener {
                tagList.get(position)?.let { it1 -> it1.id?.let { it2 -> click(it2, notHisponic.isChecked) } }
            }
//            rbInenr.setOnCheckedChangeListener { compoundButton, b ->
//                arr.get(position)?.name?.let { it1 -> click(it1, b) }
//            }
        }
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

}


