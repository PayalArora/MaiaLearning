package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.CareerUsLayoutBinding
import com.maialearning.model.CareerUSModel

class UsCareerAdapter(
    var context: Context,
    val arrayList: ArrayList<CareerUSModel.Data?>?
) : RecyclerView.Adapter<UsCareerAdapter.ViewHolder>() {
    var isSelected = false
    var count = 0
    var selectedPostion = -1

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: CareerUsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = CareerUsLayoutBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.binding.name.text = arrayList?.get(position)?.title
            viewHolder.binding.name1.text =
                arrayList?.get(position)?.careerCluster?:""
        if(arrayList?.get(position)?.officer.equals("1")){
            viewHolder.binding.officerI.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.tick))
        }else{
            viewHolder.binding.officerI.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.close))

        }
        if(arrayList?.get(position)?.enlisted.equals("1")){
            viewHolder.binding.enlist.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.tick))
        }else{
            viewHolder.binding.enlist.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.close))
        }
        if(arrayList?.get(position)?.activeDuty.equals("1")){
            viewHolder.binding.active.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.tick))
        }else{
            viewHolder.binding.active.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.close))
        }
        if(arrayList?.get(position)?.reserve.equals("1")){
            viewHolder.binding.reser.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.tick))
        }else{
            viewHolder.binding.reser.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.close))
        }

    }

    override fun getItemCount(): Int {
            return arrayList?.size!!


    }
}