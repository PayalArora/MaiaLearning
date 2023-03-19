package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.AnticipatedRowItemBinding
import com.maialearning.databinding.AnticipatedSubItemBinding
import com.maialearning.databinding.KnowAttrLayouBinding
import com.maialearning.model.*
import com.maialearning.util.*

class AntiscipatedSubAdapter(var context: Context, val array: ArrayList<AnticipatedKeyVal?>) :
    RecyclerView.Adapter<AntiscipatedSubAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: AnticipatedSubItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = AnticipatedSubItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = array.get(position)
        viewHolder.binding.apply {
            key.setText(item?.keyDisplay)
            if (checkNonNull(item?.value)){
                when(item?.keyUnit){
                    ""->{
                        value.setText(parseEmptySpace( item?.value?:""))
                    }
                    "$"->{
                        value.setText(parseEmptySpace( "$${item?.value?:""}"))
                    }
                    "%"->{
                        value.setText(parseEmptySpace( "${item?.value?:""}%"))
                    }
                }
            }
            else{
                value.visibility = View.GONE
            }

        }

    }

    override fun getItemCount(): Int {
        return array?.size ?: 0
    }
}