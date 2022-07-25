package com.maialearning.ui.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ConnectionsItemRowBinding
import com.maialearning.model.AssignedCounselorsItem
import com.maialearning.model.ParentItem
import java.util.*

class CounselorAdapter(
    val connections: List<AssignedCounselorsItem?>?,
    val onItemClick: OnItemClick
) :
    RecyclerView.Adapter<CounselorAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ConnectionsItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ConnectionsItemRowBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.nameTxt.setText(
            "${connections?.get(position)?.lastName}, ${
                connections?.get(
                    position
                )?.firstName
            }"
        )
        viewHolder.binding.emailTxt.setText(connections?.get(position)?.teacherEmail)
        viewHolder.binding.dateTxt.visibility= View.GONE
    }


    fun getDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        val date = DateFormat.format("MMM dd, yyyy", calendar).toString()
        return date
    }

    override fun getItemCount(): Int {
        return connections?.size ?: 0
    }


}

