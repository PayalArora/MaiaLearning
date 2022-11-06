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
import com.maialearning.databinding.ItemNotesBinding
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ItemCommentBinding
import com.maialearning.databinding.ProgramItemBinding
import com.maialearning.databinding.ReciepentItemBinding
import com.maialearning.model.AddProgramConsider
import com.maialearning.util.checkNonNull
import com.maialearning.util.convertDateToLong
import com.maialearning.util.getDate
import com.maialearning.util.showDatePicker
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener


class ProgramAdapter(
    private var programs: ArrayList<AddProgramConsider.Programs?>?,
    var deletedPrograms: ArrayList<String>,
    val onItemClick: OnItemClick, val canShowProgramWithDeadline:Boolean
) :
    RecyclerView.Adapter<ProgramAdapter.ViewHolder>() {
    var mCount = 0
    var position1 = -1
    var clickedPos = -1
    var txt = ""

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

        viewHolder.binding.program.setText(programs?.get(position)?.program_name)
        viewHolder.binding.apply {
            remove.setOnClickListener {
                deletedPrograms.add("" + programs?.get(position)?.program_id)
                programs?.removeAt(position)
                notifyDataSetChanged()
            }
        }
        if (canShowProgramWithDeadline) {
            viewHolder.binding.deadline.visibility = View.VISIBLE
            if (checkNonNull(programs?.get(position)?.program_deadline)){
                viewHolder.binding.deadline.setText( getDate(programs?.get(position)?.program_deadline?.toLong()!!, "MMM dd, yyyy")  )
            }

        } else {
            viewHolder.binding.deadline.visibility = View.GONE
        }
        viewHolder.binding.deadline.setOnClickListener {
            clickedPos = position
            viewHolder.binding.deadline.showDatePicker(  viewHolder.binding.root.context, ::deadlineClick)
        }

        viewHolder.binding.program.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode === KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                var programData: AddProgramConsider.Programs = AddProgramConsider.Programs()
                programData.program_name = viewHolder.binding.program.text.toString()
                programData.program_id = programs?.get(position)?.program_id
                programData.program_deadline = programs?.get(position)?.program_deadline
                programs?.set(position, programData)
            }
            false
        })

        viewHolder.binding.program.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (!TextUtils.isEmpty(viewHolder.binding.program.text.toString())) {
                    position1 = position
                    txt = viewHolder.binding.program.text.toString()
                }
            }
        })
        KeyboardVisibilityEvent.setEventListener(
            viewHolder.binding.root.context as Activity,
            {
                if (!it) {
                    if (programs?.size!! > position) {
                        var programData: AddProgramConsider.Programs = AddProgramConsider.Programs()
                        programData.program_name = viewHolder.binding.program.text.toString()
                        programData.program_id = programs?.get(position)?.program_id
                        programData.program_deadline = programs?.get(position)?.program_deadline
                        programs?.set(position, programData)
                    }
                } else {
                }
                // some code depending on keyboard visiblity status
            })
    }

    override fun getItemCount(): Int {
        return programs?.size ?: 0
    }

    fun addMore() {
        if (position1 >= 0 && programs?.size!! > position1) {
            var programData: AddProgramConsider.Programs = AddProgramConsider.Programs()
            programData.program_name = txt
            programData.program_id = null
            programs?.set(position1, programData)
        }
        var programData: AddProgramConsider.Programs = AddProgramConsider.Programs()
        programData.program_name = ""
        programData.program_id = null
        programs?.add(programData)
        notifyDataSetChanged()
    }

    fun save(): ArrayList<AddProgramConsider.Programs?>? {
        return programs
    }
    fun deadlineClick(s: String) {
        programs?.get(clickedPos)?.program_name?.let {
            var programData: AddProgramConsider.Programs = AddProgramConsider.Programs()
            programData.program_deadline = convertDateToLong(s).toString()
            programData.program_name = it
            programData.program_id = programs?.get(clickedPos)?.program_id
            programs?.set(clickedPos, programData)
        }
    }
}



