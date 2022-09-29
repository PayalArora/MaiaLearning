package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.KnowAdapterLayBinding
import com.maialearning.databinding.NysCareerItemBinding
import com.maialearning.model.AbilitiesItem
import com.maialearning.model.PersonalAcademicAreasItem
import com.maialearning.model.StudentCareerReviewItem

class NYSPersonAdapter(var context: Context, var data: List<PersonalAcademicAreasItem?>?) :
    RecyclerView.Adapter<NYSPersonAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: KnowAdapterLayBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = KnowAdapterLayBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.gradeNo.text = data?.get(position)?.grade
        viewHolder.binding.textA.setText(data?.get(position)?.needStrengthen)
        viewHolder.binding.textB.setText(data?.get(position)?.stepsToStrengthenAreas)
        viewHolder.binding.textC.visibility = View.GONE
        viewHolder.binding.nameC.visibility = View.GONE
        viewHolder.binding.nameA.text = context.getString(R.string.area_i_need)
        viewHolder.binding.nameB.text = context.getString(R.string.steps_i_will)

//        }
//        viewHolder.binding.subList.layoutManager=
//            LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
//        viewHolder.binding.subList.adapter = KnowSubListAdapter(position.toString())
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }
}