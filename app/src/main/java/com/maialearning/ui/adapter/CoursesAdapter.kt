package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.CoursesListAdapterBinding
import com.maialearning.model.CourseListModel
import com.maialearning.model.GermanUniversitiesResponse
import com.maialearning.model.IndustryModel

import com.maialearning.model.UkResponseModel
import com.maialearning.util.toCapitalComma
import com.maialearning.util.toUpperCase

class CoursesAdapter(
    var context: Context,
    val list: List<CourseListModel>?,
    val listGerman: List<GermanUniversitiesResponse.Data.CollegeData.CourseList?>?,
    var type: String,     var click:(String)->Unit
) :
    RecyclerView.Adapter<CoursesAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: CoursesListAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = CoursesListAdapterBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (type == "uk") {
            viewHolder.binding.name.text = list?.get(position)?.courseName
            viewHolder.binding.option.text = list?.get(position)?.optionCount + " Options"
            if (list?.get(position)?.aLevel != "null" && list?.get(position)?.aLevel != "") {
                viewHolder.binding.alevels.text = "A Lvi: " + list?.get(position)?.aLevel?.toCapitalComma()
            } else {
                viewHolder.binding.alevels.text = "A Lvi: -- "
            }
            if (list?.get(position)?.ib != "null" && list?.get(position)?.ib != "") {
                viewHolder.binding.ib.text = list?.get(position)?.ib?.toCapitalComma()
            } else {
                viewHolder.binding.ib.text = " -- "
            }
            viewHolder.binding.name.setOnClickListener {
                list?.get(position)?.let { it1 ->
                    if (it1.courseOptionList.size > 0) {
                        click( it1.courseOptionList.get(0).courseOptionId.toString())
                    }
                }
            }

        } else {
            viewHolder.binding.name.text = listGerman?.get(position)?.courseName
            viewHolder.binding.option.visibility = View.GONE
            if (listGerman?.get(position)?.studyMode != "null" && listGerman?.get(position)?.studyMode != "") {
                viewHolder.binding.alevels.text = listGerman?.get(position)?.studyMode?.toCapitalComma()
            } else {
                viewHolder.binding.alevels.text = " -- "
            }
            if (listGerman?.get(position)?.location != "null" && listGerman?.get(position)?.location != "") {
                viewHolder.binding.ib.text = listGerman?.get(position)?.location?.toCapitalComma()
            } else {
                viewHolder.binding.ib.text = " -- "
            }
          //  viewHolder.binding.name.setOnClickListener {   listGerman?.get(position)?.let { it1 ->  click( it1.courseId.toString())}}
        }

    }

    override fun getItemCount(): Int {
        if (type == "uk") {
            return list?.size ?: 0
        } else {
            return listGerman?.size ?: 0
        }
    }
}