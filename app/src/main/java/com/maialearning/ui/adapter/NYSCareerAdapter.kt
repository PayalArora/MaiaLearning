package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.KnowAdapterLayBinding
import com.maialearning.databinding.NysCareerItemBinding
import com.maialearning.model.NYSCareerResponse
import com.maialearning.model.StudentCareerReviewItem

class NYSCareerAdapter(var context: Context, var data: ArrayList<StudentCareerReviewItem>) :
    RecyclerView.Adapter<NYSCareerAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: NysCareerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = NysCareerItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.gradeTxt.text = data.get(position).grade
        if (data.get(position).reviewByStudent != null && data.get(position).reviewByStudent.equals(
                "1"
            )
        ) {
            viewHolder.binding.studentCheck.isChecked = true
        } else if (data.get(position).reviewByParent != null && data.get(position).reviewByParent.equals(
                "1"
            )
        ) {
            viewHolder.binding.parentCheck.isChecked = true
        } else if (data.get(position).reviewByTeacher != null && data.get(position).reviewByTeacher.equals(
                "1"
            )
        ) {
            viewHolder.binding.educatorCheck.isChecked = true
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}