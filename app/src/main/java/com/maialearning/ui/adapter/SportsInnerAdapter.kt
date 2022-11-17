package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.*
import com.maialearning.ui.activity.ClickFilters
import com.maialearning.ui.model.ChildrenItem


class SportsInnerAdapter(val arr: ArrayList<ChildrenItem>,val pos:Int, val click: (position: Int, inner:CheckBox) -> Unit,val inner:CheckBox ) :
    RecyclerView.Adapter<SportsInnerAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    var checked: Boolean = false

    class ViewHolder(val binding: SportsInnerBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = SportsInnerBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            rbInenr.setText(arr.get(position).value)
            if (checked){
                rbInenr.isChecked = true
            }
           else if (arr.get(position).checked) {
                rbInenr.isChecked = true

            } else {
                rbInenr.isChecked = false
            }

            rbInenr.setOnClickListener {
                arr.get(position).checked = rbInenr.isChecked
                click(pos, inner)
            }
        }
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    fun check(b: Boolean) {
       checked = b
        notifyDataSetChanged()
    }

}


