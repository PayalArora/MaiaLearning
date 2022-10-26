package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ProgramListItemLayoutBinding
import com.maialearning.databinding.ProgramListOptionItemBinding
import com.maialearning.model.CourseListModel
import com.maialearning.model.CourseListOptionModel
import com.maialearning.ui.fragments.OnClickOption

class CourseOptionAdapter(
    var context: Context,
    val list: List<CourseListOptionModel?>?,val pos:Int,
     val onClickOption: OnClickOption
) :
    RecyclerView.Adapter<CourseOptionAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ProgramListOptionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ProgramListOptionItemBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.name.text = list?.get(position)?.outcomeQualification
        viewHolder.binding.yearDuration.text =
            list?.get(position)?.durationValue + " " + list?.get(position)?.durationType + "-" + list?.get(
                position
            )?.studyMode + "-" + list?.get(
                position
            )?.startDate
        viewHolder.binding.option.text = "Main Site"
        if (list?.get(position)?.alevels != "null" && list?.get(position)?.alevels != "") {
            viewHolder.binding.alevels.text = "A Lvl: " + list?.get(position)?.alevels
        } else {
            viewHolder.binding.alevels.text = "A Lvl: -- "
        }
        if (list?.get(position)?.ibScore != "null" && list?.get(position)?.ibScore != "") {
            viewHolder.binding.ib.text = "IB " + list?.get(position)?.ibScore
        } else {
            viewHolder.binding.ib.text = " -- "
        }

        viewHolder.binding.viewBtn.setOnClickListener {
            list?.get(position)?.let { it1 -> onClickOption.onViewClick(pos, it1) }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}