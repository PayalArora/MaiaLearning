package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.EthnicityItemBinding
import com.maialearning.databinding.ItemUnivFilterBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.model.TeacherListModelItem
import com.maialearning.ui.activity.ClickFilters
import com.maialearning.ui.fragments.RecommendationFragment.Companion.REC_TYPE_RECOMENDATION

class SelectTeacherAdapter(
    val arr: ArrayList<TeacherListModelItem>,
    val onClick: (data: TeacherListModelItem,type:Int) -> Unit, val type:Int
) : RecyclerView.Adapter<SelectTeacherAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: EthnicityItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = EthnicityItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.notHisponic.setOnClickListener {
//            if (arr[position].isSelected) {
//                arr[position].isSelected = false
//            } else {
                arr[position].isSelected = true
                onClick(arr[position],type)
            //}
        }
        viewHolder.binding.apply {
            if (type == REC_TYPE_RECOMENDATION)
            viewHolder.binding.notHisponic.text =
                arr[position].firstName?.capitalize() + " " + arr[position].lastName?.capitalize() + " (" + arr[position].schoolName + ")"
            else
                viewHolder.binding.notHisponic.text =
                    arr[position].firstName?.capitalize() + " " + arr[position].lastName?.capitalize()


        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}

