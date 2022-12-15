package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.TestScoresItemBinding
import com.maialearning.model.CollegeRecommendationRequirementModel
import com.maialearning.model.TestScoresResponseItem

class RecommendedPrefrenceAdapter(val arr: ArrayList<CollegeRecommendationRequirementModel>) :
    RecyclerView.Adapter<RecommendedPrefrenceAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: TestScoresItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = TestScoresItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            collegeName.setText(arr.get(position).collegeName)
        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}

