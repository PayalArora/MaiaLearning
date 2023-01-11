package com.maialearning.ui.adapter

import android.content.Context
import android.text.Html
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
import com.maialearning.databinding.ProgramListItemBinding
import com.maialearning.databinding.ProgramListItemLayoutBinding
import com.maialearning.model.CourseListModel
import com.maialearning.model.GermanUniversitiesResponse
import com.maialearning.model.ProgramListFactSheet
import com.maialearning.model.UkResponseModel
import com.maialearning.ui.fragments.OnClickOption
import com.maialearning.ui.fragments.OnItemClickOption

class ProgramListAdapterEuropean(
    var context: Context,
    val list: List<ProgramListFactSheet?>?, val onClickOption: OnClickOption
    ) :
    RecyclerView.Adapter<ProgramListAdapterEuropean.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ProgramListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ProgramListItemBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.progName.text = list?.get(position)?.programName
        viewHolder.binding.durationTxt.text = list?.get(position)?.duration
        viewHolder.binding.price.text = Html.fromHtml(list?.get(position)?.tuitionFee)
        viewHolder.binding.arrow.setOnClickListener {

        }
    }



    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}