package com.maialearning.ui.adapter

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.CitizenItemBinding
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.setEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener


class CitizenshipAdapter(val citizenship: ArrayList<String?>?, val onItemClick: OnItemClick) :
    RecyclerView.Adapter<CitizenshipAdapter.ViewHolder>() {
    var position1 = -1
    var txt = ""

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
        position1= position
//        viewHolder.binding.root.setOnClickListener { onItemClick.onClick(position) }
        viewHolder.binding.program.setText(citizenship?.get(position))
        viewHolder.binding.apply {
            remove.setOnClickListener {
                citizenship?.removeAt(position)
                notifyDataSetChanged()
            }
        }
        viewHolder.binding.program.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode === KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                citizenship?.set(position, viewHolder.binding.program.text.toString())
            }
            false
        })

        viewHolder.binding.program.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
             position1 = position
                txt = viewHolder.binding.program.text.toString()
            }
        })
        setEventListener(
            viewHolder.binding.root.context as Activity,
            KeyboardVisibilityEventListener {
                if (!it){
                    if (citizenship?.size!! > position)
                    citizenship?.set(position, viewHolder.binding.program.text.toString())
                } else{

                }
                // some code depending on keyboard visiblity status
            })

    }


    override fun getItemCount(): Int {
        return citizenship?.size ?: 0
    }

    fun addMore() {
        if (position1 >= 0 && citizenship?.size!! > 0) {
            citizenship?.add("")
            citizenship?.set(position1, txt)
        } else{
            citizenship?.add("")
        }
        notifyDataSetChanged()
    }

}
