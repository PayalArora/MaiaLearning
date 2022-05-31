package com.maialearning.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.maialearning.R
import com.maialearning.databinding.ProgressAdapterBinding
import com.maialearning.databinding.ReciepentItemBinding
import com.maialearning.ui.model.CommunityModel

class ProgressAdapter(private var context: Context,var listData:ArrayList<CommunityModel>) :
    RecyclerView.Adapter<ProgressAdapter.ViewHolder>() {

    class ViewHolder(val binding: ProgressAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ProgressAdapterBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.binding.progress1bar.progress=listData[position].percent.replace("%","").toInt()
            holder.binding.progress1bar.setIndicatorColor(Color.parseColor(listData[position].text))
            holder.binding.name.setTextColor(Color.parseColor(listData[position].text))
        holder.binding.name.text = listData[position].name

    }

    override fun getItemCount(): Int {
 return listData.size
    }
}