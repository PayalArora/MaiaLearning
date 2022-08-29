package com.maialearning.ui.adapter

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.DecisionProgramItemBinding
import com.maialearning.databinding.LayoutDecisionsBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.util.UNIV_LOGO_URL
import com.squareup.picasso.Picasso

class DecisionProgramAdapter(
    var data: ArrayList<ConsiderModel.ProgramData>,
    var isVisible: Boolean,
    var pos:Int,
    val decision: (position:Int, decisionPosition: Int) -> Unit
):
    RecyclerView.Adapter<DecisionProgramAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: DecisionProgramItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = DecisionProgramItemBinding.inflate(inflater, viewGroup, false)


        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {


            program.text=data.get(position).program_name
            println("prognam ${ data.get(position).program_status}")
            if (!data.get(position).program_status.equals("null") && !TextUtils.isEmpty(data.get(position).program_status))
            {    if (!data.get(position).app_status_val.equals("null") && !TextUtils.isEmpty(data.get(position).app_status_val))
                rbDecision.text = data.get(position).app_status_val
                else
                rbDecision.text = data.get(position).program_status
            }
            if (isVisible == true) {
             rbDecision.visibility = View.VISIBLE

            } else {
                rbDecision.visibility = View.GONE
            }
            rbDecision.setOnClickListener { decision(pos, position) }
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

}

