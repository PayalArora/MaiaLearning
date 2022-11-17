package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.SportsRowBinding
import com.maialearning.ui.activity.ClickFilters
import com.maialearning.ui.model.ResponseItem


class SportsFilterAdapter(val arr: ArrayList<ResponseItem>) :
    RecyclerView.Adapter<SportsFilterAdapter.ViewHolder>(),ClickFilters {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
//    val array: Array<String> = arrayOf("Division 1", "Division 2")
//    var visibility: Array<Int> = arrayOf(1, 0, 1, 0, 0, 0,1,1,1)

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
            rbInenr.isChecked = arr.get(position).checked
            rbInenr.setText(arr.get(position).value)
            if (arr.get(position).children!=null){
            arrow.visibility = View.VISIBLE}
            else
            {
                arrow.visibility = View.GONE
                }


            arr.get(position).children?.let {
                rvCheckbox.adapter = SportsInnerAdapter(it, position, ::rbInnerClick, rbInenr)
                arrow.setOnClickListener {
                    if (rvCheckbox.isVisible) {
                        rvCheckbox.visibility = View.GONE
                    } else {
                        rvCheckbox.visibility = View.VISIBLE
                    }
                }
                for (i in it){
                    if (i.checked){
                        rvCheckbox.visibility = View.VISIBLE
                    }
                }

            }

            rbInenr.setOnCheckedChangeListener { compoundButton, b ->
                arr.get(position).children?.let {
                    for (i in it)
                        i.checked = b
                if(b){
                    (rvCheckbox.adapter as SportsInnerAdapter).check(true)
                } else{
                    (rvCheckbox.adapter as SportsInnerAdapter).check(false)
                }

                }
                arr.get(position).checked = b

            }

        }

    }

    private fun rbInnerClick(i: Int, check:CheckBox) {
        var count = 0
        for (item in arr.get(i).children!!){
            if (item.checked ){
                count ++
            }
        }
        if (count == arr.get(i).children!!.size){
            arr.get(i).checked = true
        } else {
            arr.get(i).checked = false
        }
        check.isChecked = arr.get(i).checked

    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onClick(positiion: Int, type: Int, img:ImageView?) {

    }

    override fun onDiversityClick(position: Int) {
    }

    override fun onTypeClick(position: Int, type: String) {

    }

}


