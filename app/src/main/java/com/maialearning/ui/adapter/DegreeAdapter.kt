package com.maialearning.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import com.maialearning.databinding.DegreeAdapterBinding
import com.maialearning.model.CollegeFactSheetModel

class DegreeAdapter (var list:ArrayList<CollegeFactSheetModel.DegreeMajors1.Majors1>) : RecyclerView.Adapter<DegreeAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: DegreeAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = DegreeAdapterBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            viewHolder.binding.name.text=list.get(position).name
        if (list[position].agriculturalBusinessAndManagement.associateDegree==1){
            viewHolder.binding.aa.visibility= View.VISIBLE
        }else{
            viewHolder.binding.aa.visibility= View.INVISIBLE
        }
        if (list[position].agriculturalBusinessAndManagement.masterDegree==1){
            viewHolder.binding.ma.visibility= View.VISIBLE
        }else{
            viewHolder.binding.ma.visibility= View.INVISIBLE
        }
        if (list[position].agriculturalBusinessAndManagement.bachelorDegree==1){
            viewHolder.binding.ba.visibility= View.VISIBLE
        }else{
            viewHolder.binding.ba.visibility= View.INVISIBLE
        }
        if (list[position].agriculturalBusinessAndManagement.doctorateDegree==1){
            viewHolder.binding.da.visibility= View.VISIBLE
        }else{
            viewHolder.binding.da.visibility= View.INVISIBLE
        }
        }

    override fun getItemCount(): Int {
        return list.size
    }
}
