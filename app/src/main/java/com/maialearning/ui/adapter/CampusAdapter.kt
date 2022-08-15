package com.maialearning.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.CampusAdapterBinding
import com.maialearning.databinding.CampusAdapterItemBinding
import com.maialearning.model.CollegeFactSheetModel
import com.maialearning.model.SetServices

class CampusAdapter(var list:ArrayList<SetServices>,var context:Context) : RecyclerView.Adapter<CampusAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: CampusAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = CampusAdapterItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.recyclerView.layoutManager=
            GridLayoutManager(context,2, LinearLayoutManager.VERTICAL,false)
        viewHolder.binding.recyclerView.adapter=CampusInnerList(list[position].name)
//        if (position ==0 || position ==3){
        viewHolder.binding.about.text= list[position].key
//        }else{
//            viewHolder.binding.service.append("2")
//        }
//        DrawableCompat.setTint(viewHolder.binding.layout.background, Color.parseColor("#F5F5F5"))


    }

    override fun getItemCount(): Int {
        return list.size
    }
}