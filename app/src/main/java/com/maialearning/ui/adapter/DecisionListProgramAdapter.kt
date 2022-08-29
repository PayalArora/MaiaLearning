package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.RadiobuttonItemBinding
import com.maialearning.model.StatusModel


class DecisionListProgramAdapter(val array: ArrayList<StatusModel>, val selected:String,  val decisionStatusClick: (position:Int, statusPos:Int) -> Unit, val statusPos:Int) :
    RecyclerView.Adapter<DecisionListProgramAdapter.ViewHolder>() {
    var selectCheck :ArrayList<Int> = arrayListOf()
    init {
        for (i in array){
            if (i.key.equals(selected))
                selectCheck.add(1)
            else
            selectCheck.add(0)
        }
    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    var selectedPosition: Int = -1

    class ViewHolder(val binding: RadiobuttonItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = RadiobuttonItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.apply {
            rbButton.setText(array.get(position).status)
            //rbButton.setChecked(position == selectedPosition)

            rbButton.setOnCheckedChangeListener({ compoundButton, b ->
                    // check condition
                    if (b) {
                        selectedPosition = viewHolder.getAdapterPosition()
//                        notifyDataSetChanged()
                    }
                })

                if (selectCheck.get(position) == 1) {
                    rbButton.setChecked(true)
                } else {
                    rbButton.setChecked(false)
                }

                rbButton.setOnClickListener {
                    for (i in selectCheck.indices){
                        if (i == position){
                            selectCheck.set(i,1)
                        } else{
                            selectCheck.set(i,0)
                        }
                    }
                    decisionStatusClick(selectedPosition, statusPos)
                    notifyDataSetChanged();
                    }

        }

    }

    override fun getItemCount(): Int {
        return array.size
    }

}

