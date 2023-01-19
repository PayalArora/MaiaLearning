package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ConnectionsItemRowBinding
import com.maialearning.databinding.CounselorNotesCommentsLayoutBinding
import com.maialearning.model.ConsiderModel
import java.util.*

class CounselorNotesCommentsAdapter(
    val comments: List<ConsiderModel.CounselorNotes?>?
) :
    RecyclerView.Adapter<CounselorNotesCommentsAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: CounselorNotesCommentsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = CounselorNotesCommentsLayoutBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.notesTxt.setText(comments?.get(position)?.counselorNote)
        viewHolder.binding.notesSubTxt.setText(
            comments?.get(position)?.lastName + ", " + comments?.get(
                position
            )?.firstName
        )
    }


    override fun getItemCount(): Int {
        return comments?.size ?: 0
    }


}

