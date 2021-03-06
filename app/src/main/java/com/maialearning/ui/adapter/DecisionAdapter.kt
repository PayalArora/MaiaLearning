package com.maialearning.ui.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.databinding.ItemMilestonesBinding
import com.maialearning.databinding.ItemShorcutsBinding
import com.maialearning.databinding.LayoutDecisionsBinding
import com.maialearning.databinding.UniversityFilterBinding
import com.maialearning.ui.activity.ClickFilters

class DecisionAdapter(val  decisionClick:DecisionClick) : RecyclerView.Adapter<DecisionAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: LayoutDecisionsBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = LayoutDecisionsBinding.inflate(inflater, viewGroup, false)


        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
       if (position ==1 || position ==2){
           rbDecision.visibility = VISIBLE
           rbAccepted.visibility = GONE
       }
        else{
           rbAccepted.visibility = VISIBLE
           rbDecision.visibility = GONE
        }
            rbDecision.setOnClickListener {
             decisionClick.onDecisionClick()
            }
        }

    }

    override fun getItemCount(): Int {
        return 6
    }

}
interface DecisionClick{
    fun onDecisionClick()
}