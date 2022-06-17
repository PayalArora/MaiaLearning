package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ConnectionsItemRowBinding
import com.maialearning.databinding.UpcomingItemRowBinding

class UpcomingListAdapter(val onItemClick: OnItemClick) :
    RecyclerView.Adapter<UpcomingListAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: UpcomingItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = UpcomingItemRowBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.setOnClickListener { onItemClick.onClick(position) }
            if (position == 0){
                setVisibility(viewHolder.binding, carrier_lay = 1)
            } else if (position == 1){
                setVisibility(viewHolder.binding, academic_lay = 1)
            }else if (position == 2){
                setVisibility(viewHolder.binding, submitted_lay = 1)
            }else if (position == 3){
                setVisibility(viewHolder.binding, fair_lay = 1)
            }else if (position == 4){
                setVisibility(viewHolder.binding, fair_join_lay = 1)
            }else if (position == 5){
                setVisibility(viewHolder.binding, approved_lay = 1)
            }else if (position == 6){
                setVisibility(viewHolder.binding, deadline_lay = 1)
            }
            else if (position == 7){
                setVisibility(viewHolder.binding, survey_lay = 1)
            }


    }

    override fun getItemCount(): Int {
        return 8
    }

fun setVisibility(binding: UpcomingItemRowBinding, carrier_lay:Int= View.GONE, academic_lay:Int=  View.GONE, submitted_lay:Int=  View.GONE, fair_lay:Int=  View.GONE, fair_join_lay:Int=  View.GONE, approved_lay:Int=  View.GONE,
                  deadline_lay:Int=  View.GONE, survey_lay:Int=  View.GONE){
    binding.apply {
        carrierLay.visibility = carrier_lay
        academicLay.visibility = academic_lay
        submittedLay.visibility = submitted_lay
        fairLay.visibility= fair_lay
        fairJoinLay.visibility = fair_join_lay
        approvedLay.visibility = approved_lay
        deadlineLay.visibility = deadline_lay
        surveyLay.visibility = survey_lay
    }
    }
}

