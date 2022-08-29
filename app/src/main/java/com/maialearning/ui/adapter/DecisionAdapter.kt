package com.maialearning.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.LayoutDecisionsBinding
import com.maialearning.model.ConsiderModel
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
                decisionPlan.setText(viewHolder.binding.root.context.resources.getStringArray(R.array.DECISION_PLAN)[data.get(
                    position).applicationType.toInt()])
            else
                decisionPlan.setText("")

            if (!data.get(position).applicationMode.equals("null"))
                applicationMode.setText(viewHolder.binding.root.context.resources.getStringArray(R.array.APPLICATION_TYPE)[data.get(
                    position).applicationMode.toInt()])
            else
                applicationMode.setText("")


            programList.layoutManager = LinearLayoutManager(
                viewHolder.binding.root.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            val isVisible =
                data[position].appByProgramSupported.equals("1") && !data[position].applicationMode.equals(
                    "3")
            programList.adapter = data[position].program_data?.let {
                DecisionProgramAdapter(it,
                    isVisible, position, ::clickProgramDecision)
            }
            if (data[position].program_data != null && data[position].program_data!!.size > 0) {
                view1.visibility = VISIBLE
                view2.visibility = VISIBLE
            } else {
                view1.visibility = GONE
                view2.visibility = GONE
            }

//            Log.e("Decision",
//                "appByProgramSupported ${data[position].appByProgramSupported}" + " , applicationMode ${data[position].applicationMode}, $isVisible  name ${data[position].naviance_college_name}")

            if (data[position].appByProgramSupported.equals("1") && !data[position].applicationMode.equals(
                    "3")
            ) {
                rbAccepted.visibility = GONE
                rbDecision.visibility = GONE
            } else {
//                 if (data[position].appByProgramSupported.equals("0") && data[position].applicationMode.equals(
//                         "3")
                rbDecision.visibility = VISIBLE
                rbAccepted.visibility = GONE
            }

            if (!data[position].applicationStatusName.equals("null")) {
                rbDecision.setText(data[position].applicationStatusName)
            }

            rbDecision.setOnClickListener {
                decisionClick.onDecisionClick(position)
            }
            if (data.get(position).confirmApplied == 1) {
                rbOtherApp.isChecked = true
            } else
                rbOtherApp.isChecked = false
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun clickProgramDecision(i: Int, decisionPosition: Int) {
        decisionClick.onProgramDecisionClick(i, decisionPosition)
    }
}

interface DecisionClick {
    fun onDecisionClick(position: Int)
    fun onProgramDecisionClick(position: Int, decisionPosition: Int)
}
