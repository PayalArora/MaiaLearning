package com.maialearning.ui.adapter

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.CitizenItemBinding


class CitizenshipAdapter(val citizenship: ArrayList<String?>?, val onItemClick: OnItemClick) :
    RecyclerView.Adapter<CitizenshipAdapter.ViewHolder>() {
    var mCount = 0

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: CitizenItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = CitizenItemBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        viewHolder.binding.root.setOnClickListener { onItemClick.onClick(position) }
        viewHolder.binding.program.setText(citizenship?.get(position))
        viewHolder.binding.apply {
            remove.setOnClickListener {
                mCount = mCount - 1
                citizenship?.removeAt(position)
                notifyDataSetChanged()
            }
        }
        viewHolder.binding.program.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode === KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                citizenship?.add(position, viewHolder.binding.program.text.toString())
            }
            false
        })


    }

    override fun getItemCount(): Int {
        return citizenship?.size ?: 0
    }


}
