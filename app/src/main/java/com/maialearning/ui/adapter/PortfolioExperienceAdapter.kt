package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.DegreeAdapterBinding
import com.maialearning.databinding.ExperienceItemBinding
import com.maialearning.model.ExperiencesModelResponseItem
import java.util.stream.Collectors

class PortfolioExperienceAdapter(val experiencesModelResponse: List<ExperiencesModelResponseItem?>?) :
    RecyclerView.Adapter<PortfolioExperienceAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ExperienceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ExperienceItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.name.setText(experiencesModelResponse?.get(position)?.name)
        viewHolder.binding.location.setText(" at " + experiencesModelResponse?.get(position)?.location)
        viewHolder.binding.awardCount.setText(experiencesModelResponse?.get(position)?.award)
        viewHolder.binding.descriptionTxt.setText(experiencesModelResponse?.get(position)?.description)
        val years = ArrayList<String>() as ArrayList
        for (i in experiencesModelResponse?.get(position)?.year?.indices!!) {
            years.add("${(experiencesModelResponse?.get(position)?.year?.get(i)?.toInt()?.plus(5) ?: 0)}" +"th")
        }
//        val year = ArrayList<String>() as ArrayList
//        for (i in years?.indices!!) {
//            year.add(years.get(i).toString())
//        }
        viewHolder.binding.grade.setText(
            "Years: " + years.stream()?.collect(Collectors.joining(",", "", "")
            )
        )

    }

    override fun getItemCount(): Int {
        return experiencesModelResponse?.size ?: 0
    }

}
