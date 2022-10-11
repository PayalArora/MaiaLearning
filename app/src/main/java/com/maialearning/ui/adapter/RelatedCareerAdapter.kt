package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.RelatedCareerItemBinding
import com.maialearning.model.RelatedCareersItem
import com.maialearning.util.parseNA

class RelatedCareerAdapter(
    var context: Context,
    val arrayList: ArrayList<RelatedCareersItem>?,
    var click: (position: Int) -> Unit
) : RecyclerView.Adapter<RelatedCareerAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: RelatedCareerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = RelatedCareerItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.binding.plan.setText(
               parseNA(arrayList?.get(position)?.brightOutlook?.capitalize()))
        viewHolder.binding.title.text = arrayList?.get(position)?.careerTitle
        if ( arrayList?.get(position)?.topPick == true){
            viewHolder.binding.like.setImageResource(R.drawable.heart_filled)
        } else {
            viewHolder.binding.like.setImageResource(R.drawable.like)
        }
        viewHolder.binding.like.setOnClickListener {

            if (arrayList?.get(position)?.topPick == true) {
                arrayList?.get(position)?.topPick = false
            } else {
                arrayList?.get(position)?.topPick = true
            }
            click(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return arrayList?.size?:0
    }

}