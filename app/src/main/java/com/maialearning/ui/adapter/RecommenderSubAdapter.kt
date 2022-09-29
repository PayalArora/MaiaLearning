package com.maialearning.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.ProgressLayoutBinding
import com.maialearning.databinding.RecomendationSubCollegeItemBinding
import com.maialearning.model.RecCollegeModel
import com.maialearning.util.getDate
import com.maialearning.viewmodel.HomeViewModel


class RecommenderSubAdapter(
    var context: Context,
    var list: ArrayList<RecCollegeModel.RecomenderName>?,
    val click: (id: RecCollegeModel.RecomenderName, cancel: Boolean) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: RecomendationSubCollegeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val viewHolder: RecyclerView.ViewHolder?

        val bindingView = RecomendationSubCollegeItemBinding.inflate(inflater, viewGroup, false)
        viewHolder = ViewHolder(bindingView)
        return viewHolder


    }

    class ViewHolder2(val binding: ProgressLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ViewHolder) {
            viewHolder.binding.recName.text = list?.get(position)?.done
            viewHolder.binding.date.text =
                "Req on: " + getDate(list?.get(position)?.reco_created!!.toLong(), "MMM dd, yyyy")
//            if (list?.get(position)?.req_filename != "" && list?.get(position)?.req_filename != null && list?.get(
//                    position
//                )?.req_filename != "null"
//            ) {
//                viewHolder.binding.brag.visibility=View.VISIBLE
//            } else {
//                viewHolder.binding.brag.visibility=View.GONE
//            }
            if (list?.get(position)?.cancel.equals("1")) {
                viewHolder.binding.brag.visibility = View.VISIBLE
                viewHolder.binding.cancel.visibility = View.VISIBLE
            } else {
                viewHolder.binding.brag.visibility = View.GONE
                viewHolder.binding.cancel.visibility = View.GONE
            }
            viewHolder.binding.cancel.setOnClickListener {

                AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(context.getString(R.string.cancel_req_msg))
                    .setPositiveButton(
                        "Yes"
                    ) { dialog, _ ->
                        click(list?.get(position)!!, true)
                        list?.removeAt(position)
                        notifyDataSetChanged()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }

            viewHolder.binding.brag.setOnClickListener {
                click(list?.get(position)!!, false)
            }
        }
    }


    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


}