package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.SportsRowBinding
import com.maialearning.ui.activity.ClickFilters


class SportsFilterAdapter(val arr: ArrayList<String>) :
    RecyclerView.Adapter<SportsFilterAdapter.ViewHolder>(),ClickFilters {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    val array: Array<String> = arrayOf("Division 1", "Division 2")
    var visibility: Array<Int> = arrayOf(1, 0, 1, 0, 0, 0)

    class ViewHolder(val binding: SportsRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = SportsRowBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            rbInenr.setText(arr.get(position))
//            if(position>=2)
//                arrow.visibility = visibility[position]

           arrow.setOnClickListener {
              if(position>=2) {
                  if (rvCheckbox.isVisible) {
                      rvCheckbox.visibility = View.GONE
                  } else {
                      rvCheckbox.visibility = View.VISIBLE
                  }
                  (rvCheckbox.adapter as SportsInnerAdapter).check(rbInenr.isChecked)
                 // notifyDataSetChanged()
              }
            }

            rvCheckbox.adapter = SportsInnerAdapter(array)

            rbInenr.setOnCheckedChangeListener { compoundButton, b ->
                if(b){
                    (rvCheckbox.adapter as SportsInnerAdapter).check(true)
                }else{
                    (rvCheckbox.adapter as SportsInnerAdapter).check(false)
                }

            }

        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onClick(positiion: Int, type: Int, img:ImageView?) {

    }

}


