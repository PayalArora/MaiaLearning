package com.maialearning.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.res.Resources
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.DecionProgramStatusAdapterBinding
import com.maialearning.databinding.ProgramItemBinding
import com.maialearning.databinding.RadiobuttonFilterBinding
import com.maialearning.model.AddProgramConsider
import com.maialearning.model.ConsiderModel
import com.maialearning.model.StatusModel
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

class DecisonProgramStatusAdapter(
    private var programs: ArrayList<ConsiderModel.ProgramData>?,
    val array: ArrayList<StatusModel>,
    val decPosition: Int,

    ) :
    RecyclerView.Adapter<DecisonProgramStatusAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    lateinit var decisionProgDialog: BottomSheetDialog

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
            viewHolder.binding.status.setText(getStatus(programs?.get(position)?.program_status))
        }
        viewHolder.binding.status.setOnClickListener {
            decisionStatusClick(viewHolder.binding.root.context, position)
        }

    }

    override fun getItemCount(): Int {
        return programs?.size ?: 0
    }

    fun getStatus(program_status: String?): String? {
        for (i in array) {
            if (i.key == program_status) {
                return i.status
            }
        }
        return program_status
    }


    private fun decisionStatusClick(con: Context, position: Int) {
        decisionProgDialog = BottomSheetDialog(con)
        val inflater = LayoutInflater.from(con)
        val sheetBinding: RadiobuttonFilterBinding =
            RadiobuttonFilterBinding.inflate(inflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        decisionProgDialog.setContentView(sheetBinding.root)
        decisionProgDialog.show()
        sheetBinding.filters.setText(con.resources.getString(R.string.select_decision1))
        sheetBinding.rvRadioGroup.adapter =
            DecisionListProgramAdapter(array,
                programs?.get(position)?.program_status ?: "", ::onDecisionSelected, position)
        sheetBinding.close.setOnClickListener { decisionProgDialog.dismiss() }
        sheetBinding.saveBtn.visibility = View.GONE
    }

    private fun onDecisionSelected(position: Int,statusPos:Int) {
        decisionProgDialog.dismiss()
        programs?.get(statusPos)?.program_status = array.get(position).status
        notifyDataSetChanged()
    }

    fun save(): ArrayList<ConsiderModel.ProgramData>?{
        return programs
    }

}