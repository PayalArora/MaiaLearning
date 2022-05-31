package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemNotesBinding
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ItemCommentBinding
import com.maialearning.databinding.ProgramItemBinding
import com.maialearning.databinding.ReciepentItemBinding


class ProgramAdapter(val onItemClick: OnItemClick) :
    RecyclerView.Adapter<ProgramAdapter.ViewHolder>() {
    var mCount = 0

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ProgramItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ProgramItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.root.setOnClickListener { onItemClick.onClick(position) }
        viewHolder.binding.apply {
            remove.setOnClickListener {
                mCount = mCount - 1
                notifyDataSetChanged()
            }
        }

    }

    override fun getItemCount(): Int {
        return mCount
    }

    fun setCount(count:Int){
        mCount = count
        notifyDataSetChanged()
    }
}

