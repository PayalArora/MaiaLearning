package com.maialearning.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.databinding.EthnicityItemBinding
import com.maialearning.databinding.LayoutTeacherBinding
import com.maialearning.model.TeacherListModelItem
import com.maialearning.model.UniversitiesModel
import com.maialearning.ui.fragments.RecommendationFragment

class SelectUniversityAdapter (
    val arr: ArrayList<UniversitiesModel>,
    val onClick: (data: UniversitiesModel, type:Int) -> Unit, val type:Int
) : RecyclerView.Adapter<SelectUniversityAdapter.ViewHolder>() {
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
                viewHolder.binding.notHisponic.text =arr[position].name
        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}

