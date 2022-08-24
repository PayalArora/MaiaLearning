package com.maialearning.ui.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.databinding.ItemMilestonesBinding
import com.maialearning.databinding.ItemShorcutsBinding
import com.maialearning.databinding.LayoutDecisionsBinding
import com.maialearning.databinding.UniversityFilterBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.ui.activity.ClickFilters
import com.maialearning.util.UNIV_LOGO_URL
import com.squareup.picasso.Picasso

class DecisionAdapter(var data: ArrayList<ConsiderModel.Data>, val decisionClick: DecisionClick) :
    RecyclerView.Adapter<DecisionAdapter.ViewHolder>() {
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

            univ.text = data[position].naviance_college_name
            Picasso.with(viewHolder.binding.root.context)
                .load("$UNIV_LOGO_URL${data[position].country?.toLowerCase()}/${data[position].unitid}/logo_sm.jpg")
                .error(R.drawable.static_coll).into(viewHolder.binding.univIcon)
            if (!data.get(position).applicationType.equals("null"))
                decisionPlan.setText(
                    viewHolder.binding.root.context.resources.getStringArray(R.array.DECISION_PLAN)[data.get(
                        position
                    ).applicationType.toInt()]
                )


            programList.layoutManager = LinearLayoutManager(
                viewHolder.binding.root.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            programList.adapter = data[position].program_data?.let { DecisionProgramAdapter(it) }
            if (data[position].program_data != null && data[position].program_data!!.size > 0) {
                view1.visibility = VISIBLE
                view2.visibility = VISIBLE
            } else {
                view1.visibility = GONE
                view2.visibility = GONE
            }

//            if (position == 1 || position == 2) {
//                rbDecision.visibility = VISIBLE
//                rbAccepted.visibility = GONE
//            } else {
//                rbAccepted.visibility = VISIBLE
//                rbDecision.visibility = GONE
//            }
         /*   if(data[position].appByProgramSupported.equals("0")||data[position].applicationMode.equals("3"))
            {
                rbAccepted.visibility = GONE
                rbDecision.visibility = VISIBLE
            } else if(data[position].appByProgramSupported.equals("1")&&data[position].applicationMode.equals("3")){
                if (!data[position].applicationStatusName.equals("null")) {
                    rbAccepted.visibility = VISIBLE
                    rbDecision.visibility = GONE
                    rbAccepted.setText(data[position].applicationStatusName)
                }
            }else{
                rbAccepted.visibility = GONE
                rbDecision.visibility = GONE
            }*/

            if (!data[position].applicationStatusName.equals("null")) {
                rbAccepted.visibility = VISIBLE
                rbDecision.visibility = GONE
                rbAccepted.setText(data[position].applicationStatusName)
            } else {
                rbAccepted.visibility = GONE
                rbDecision.visibility = VISIBLE
            }
            rbDecision.setOnClickListener {
                decisionClick.onDecisionClick(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

}

interface DecisionClick {
    fun onDecisionClick(position: Int)
}