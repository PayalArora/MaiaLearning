package com.maialearning.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.OverdueItemRowBinding
import com.maialearning.model.DashboardOverdueResponse
import com.maialearning.model.SortedDateModel
import com.maialearning.viewmodel.DashboardFragViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OverDueHeadAdapter(
    var overdueList: ArrayList<SortedDateModel>?,
    val con: FragmentActivity,
    val onItemClick: OnItemClick,
    val fragment: Fragment
) :
    RecyclerView.Adapter<OverDueHeadAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: OverdueItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = OverdueItemRowBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }
    private val dashboardViewModel: DashboardFragViewModel by fragment.viewModel()

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.setOnClickListener { onItemClick.onClick(position) }
        viewHolder.binding.dateTxt.setText(overdueList?.get(position)?.date)
        overdueList?.get(position)?.assignment?.let { viewHolder.binding.countTxt.setText("" + it.size) }
        viewHolder.binding.assignmentRow.adapter = OverDueAdapter(
            overdueList?.get(position)?.assignment as ArrayList<DashboardOverdueResponse.AssignmentItem>,
            con,
            fragment
        )

    }

    override fun getItemCount(): Int {
        return overdueList?.size ?: 0
    }

}

