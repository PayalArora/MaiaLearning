package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.SearchCareerAdapterBinding
import com.maialearning.model.CareerTopPickResponseItem
import java.util.stream.Collectors

class SearchProgramAdapter(
    var context: Context,
    val arrayList: ArrayList<CareerTopPickResponseItem>,
    var click: (position: Int) -> Unit
) : RecyclerView.Adapter<SearchProgramAdapter.ViewHolder>() {
    var isSelected = false
    var count = 0
    var selectedPostion = -1

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: SearchCareerAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = SearchCareerAdapterBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.lay.setOnClickListener {
            click(position)
        }
        viewHolder.binding.name.text = arrayList.get(position).title
        viewHolder.binding.name1.text =
            arrayList.get(position).education?.stream()?.collect(Collectors.joining(",", "", ""))
        viewHolder.binding.text2.setText(arrayList.get(position).salary)
        if( arrayList.get(position).brightOutlook?.size!! >0){
            viewHolder.binding.lay1.visibility=View.VISIBLE
        viewHolder.binding.text1.setText(
            arrayList.get(position).brightOutlook?.stream()
                ?.collect(Collectors.joining(",", "", ""))
        )}else{
            viewHolder.binding.lay1.visibility=View.INVISIBLE

        }
        if (arrayList.get(position).addToCareerPlanStatus.equals("1")){
            viewHolder.binding.tick.visibility=View.VISIBLE
            viewHolder.binding.text3.visibility=View.VISIBLE
            viewHolder.binding.text3.text="In Plan"
        }else{
            viewHolder.binding.tick.visibility=View.GONE
        }

            viewHolder.binding.button.setOnClickListener {
                if (viewHolder.binding.button.isChecked) {
                    viewHolder.binding.lay.background =
                        ContextCompat.getDrawable(context, R.drawable.back_stroke_selected)
                } else {
                    viewHolder.binding.lay.background =
                        ContextCompat.getDrawable(context, R.drawable.bg_white_rect)
                }

            }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}