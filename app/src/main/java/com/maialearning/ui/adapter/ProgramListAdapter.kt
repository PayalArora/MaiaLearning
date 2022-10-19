package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.AdmissionAdapterBinding
import com.maialearning.databinding.CoursesListAdapterBinding
import com.maialearning.databinding.ProgramListItemLayoutBinding
import com.maialearning.model.CourseListModel
import com.maialearning.model.GermanUniversitiesResponse
import com.maialearning.model.UkResponseModel

class ProgramListAdapter(
    var context: Context,
    val list: List<CourseListModel?>?,
) :
    RecyclerView.Adapter<ProgramListAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ProgramListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ProgramListItemLayoutBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.name.text = list?.get(position)?.courseName
        viewHolder.binding.option.text = list?.get(position)?.optionCount + " Options"
        if (list?.get(position)?.aLevel != "null" && list?.get(position)?.aLevel != "") {
            viewHolder.binding.alevels.text = "A Lvl: " + list?.get(position)?.aLevel
        } else {
            viewHolder.binding.alevels.text = "A Lvl: -- "
        }
        if (list?.get(position)?.ib != "null" && list?.get(position)?.ib != "") {
            viewHolder.binding.ib.text = "IB " + list?.get(position)?.ib
        } else {
            viewHolder.binding.ib.text = " -- "
        }
        viewHolder.binding.arrow.setOnClickListener {
            if (list?.get(position)?.courseOptionList != null) {
                if (!viewHolder.binding.subList.isVisible) {
                    viewHolder.binding.subList.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    viewHolder.binding.subList.adapter =
                        CourseOptionAdapter(context, list?.get(position)?.courseOptionList)
                    viewHolder.binding.subList.visibility = View.VISIBLE
                }else{
                    viewHolder.binding.subList.visibility = View.GONE
                }

            }
        }


    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}