package com.maialearning.ui.adapter

import android.app.Activity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.DecionProgramStatusAdapterBinding
import com.maialearning.databinding.ProgramItemBinding
import com.maialearning.model.AddProgramConsider
import com.maialearning.model.ConsiderModel
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

class DecisonProgramStatusAdapter(
    private var programs: ArrayList<ConsiderModel.ProgramData>?,
    val decisionClick: DecisionClick
) :
    RecyclerView.Adapter<DecisonProgramStatusAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: DecionProgramStatusAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = DecionProgramStatusAdapterBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // position1=position
//        viewHolder.binding.root.setOnClickListener { onItemClick.onClick(position) }
        viewHolder.binding.program.setText(programs?.get(position)?.program_name)
        if (!programs?.get(position)?.program_status.equals("null")) {
            viewHolder.binding.status.setText(programs?.get(position)?.program_status)
        }
        viewHolder.binding.status.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->
            if (b) {
                decisionClick.onDecisionClick(position)
            }
        })

    }

    override fun getItemCount(): Int {
        return programs?.size ?: 0
    }


}

