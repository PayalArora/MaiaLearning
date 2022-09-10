package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ProgressLayoutBinding
import com.maialearning.databinding.RecomendationSubCollegeItemBinding
import com.maialearning.model.RecCollegeModel


class RecommenderSubAdapter (var context: Context, var list: ArrayList<RecCollegeModel.RecomenderName>?
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
             return   viewHolder


    }

    class ViewHolder2(val binding: ProgressLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ViewHolder) {
            viewHolder.binding.recName.text = list?.get(position)?.done
            if(list?.get(position)?.cancel.equals("1")){
                viewHolder.binding.cancel.visibility=View.VISIBLE
            }else{
                viewHolder.binding.cancel.visibility=View.GONE
            }
        }
    }



    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


}