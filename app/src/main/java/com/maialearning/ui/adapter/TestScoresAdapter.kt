package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemListFilterBinding
import com.maialearning.databinding.TestScoresItemBinding
import com.maialearning.model.TestScoresResponseItem

class TestScoresAdapter(val arr: ArrayList<TestScoresResponseItem>) :
    RecyclerView.Adapter<TestScoresAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: TestScoresItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = TestScoresItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            collegeName.setText(arr.get(position).collegeName)
            if (arr.get(position).statusId != null && !arr.get(position).statusId!!.isEmpty()) {
                sat.isChecked=false
                act.isChecked=false
                satact.isChecked=false
                notSubmit.isChecked=false
                if (arr.get(position).statusId!!.equals("1")) {
                    sat.isChecked = true
                } else if (arr.get(position).statusId!!.equals("2")) {
                    act.isChecked = true
                } else if (arr.get(position).statusId!!.equals("3")) {
                    satact.isChecked = true
                } else if (arr.get(position).statusId!!.equals("4")) {
                    notSubmit.isChecked = true
                }
            }

            sat.setOnClickListener({
                if (sat.isChecked) {
                    arr.get(position).statusId = "1"
                } else {
                    arr.get(position).statusId = ""
                }
                arr.get(position).change=true
                notifyDataSetChanged()
            })
            satact.setOnClickListener({
                if (satact.isChecked) {
                    arr.get(position).statusId = "3"
                } else {
                    arr.get(position).statusId = ""
                }
                arr.get(position).change=true
                notifyDataSetChanged()
            })
            act.setOnClickListener({
                if (act.isChecked) {
                    arr.get(position).statusId = "2"
                } else {
                    arr.get(position).statusId = ""
                }
                arr.get(position).change=true
                notifyDataSetChanged()
            })
            notSubmit.setOnClickListener({
                if (notSubmit.isChecked) {
                    arr.get(position).statusId = "4"
                } else {
                    arr.get(position).statusId = ""
                }
                arr.get(position).change=true
                notifyDataSetChanged()
            })
        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}


