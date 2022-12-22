package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.PrefrenceRecommenderLayoutBinding
import com.maialearning.databinding.TestScoresItemBinding
import com.maialearning.model.CollegeRecommendationRequirementModel
import com.maialearning.model.RecommenderModel
import com.maialearning.model.TestScoresResponseItem
import com.maialearning.util.checkNonNull

class RecommendedPrefrenceAdapter(val context:Context,
    val arr: ArrayList<CollegeRecommendationRequirementModel>
    //val recommenders: ArrayList<RecommenderModel>
) :
    RecyclerView.Adapter<RecommendedPrefrenceAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: PrefrenceRecommenderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = PrefrenceRecommenderLayoutBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            collegeName.setText(arr.get(position).collegeName)
            if (checkNonNull(arr.get(position).minTeReqd)) {
            collegeMin.setText("Recomendations: Min - ${arr.get(position).minTeReqd}, Max - ${arr.get(position).max_te_reqd}")
                collegeMin.visibility = View.VISIBLE
            } else{
                collegeMin.visibility = View.GONE
            }
            recommenderList.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            recommenderList.adapter= arr.get(position).recomendorList?.let {
                HorizontalRecommenderAdapter(
                    it
                )
            }

        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}

