package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.ItemNotesBinding
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ItemAddUniversityBinding
import com.maialearning.model.UniversitiesSearchModel
import com.maialearning.ui.activity.ClickFilters


class AddUniversiityAdapter(var university_list: ArrayList<UniversitiesSearchModel?>,
                            val onItemClick: ClickFilters) : RecyclerView.Adapter<AddUniversiityAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ItemAddUniversityBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemAddUniversityBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder. binding.root.setOnClickListener { onItemClick.onClick(position, 0, null) }
        viewHolder. binding.addCheck.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked){
                viewHolder. binding.addCheck.setText(viewHolder. binding.root.context.getString(R.string.added))
            } else {
                viewHolder. binding.addCheck.setText(viewHolder. binding.root.context.getString(R.string.add))
            }
        }
    }

    override fun getItemCount(): Int {
        return 15
    }

}

