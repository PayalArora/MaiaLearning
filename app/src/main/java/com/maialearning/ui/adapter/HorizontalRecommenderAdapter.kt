package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.HorizontalRecommenderItemBinding
import com.maialearning.databinding.PrefrenceRecommenderLayoutBinding
import com.maialearning.model.RecommenderModel

class HorizontalRecommenderAdapter(
    val recommenderList: ArrayList<RecommenderModel>  = ArrayList()
) :
    RecyclerView.Adapter<HorizontalRecommenderAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: HorizontalRecommenderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = HorizontalRecommenderItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            recName.setText(recommenderList.get(position).recommenderName)
            if (recommenderList.get(position).preferredRecommender == 1){
                recName.isChecked = true
            }else{
                recName.isChecked = false
            }
            if (recommenderList.get(position).setByCounscelor == 1){
                recName.isEnabled =true
            }else{
                recName.isEnabled = false
            }
        }

    }

    override fun getItemCount(): Int {
        return recommenderList.size
    }

}

