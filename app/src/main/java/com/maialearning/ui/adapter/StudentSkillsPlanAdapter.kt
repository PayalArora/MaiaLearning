package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.KnowAttrLayouBinding
import com.maialearning.model.KnowledgeItem
import com.maialearning.model.SkillItems

class StudentSkillsPlanAdapter (var context: Context, val knowledge: List<SkillItems?>?) :
    RecyclerView.Adapter<StudentSkillsPlanAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: KnowAttrLayouBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = KnowAttrLayouBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.name.text = knowledge?.get(position)?.name

    }

    override fun getItemCount(): Int {
        return knowledge?.size ?: 0
    }
}