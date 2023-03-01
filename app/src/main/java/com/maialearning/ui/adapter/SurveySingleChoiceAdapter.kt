package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.RadiobuttonItemFilterBinding
import com.maialearning.databinding.SurveysinglechoiceBinding
import com.maialearning.model.KeyVal
import org.json.JSONArray

class SurveySingleChoiceAdapter(val arr: ArrayList<KeyVal>) :
    RecyclerView.Adapter<SurveySingleChoiceAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: SurveysinglechoiceBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = SurveysinglechoiceBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            check.setText(arr.get(position).value.uppercase())

           // check.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
            if (arr.get(position).checked)  {
                check.isChecked = true
            }else{
                check.isChecked=false
            }
            check.setOnClickListener({
//                for (i in arr.indices) {
//                    if (i != position) {
//                        arr.get(i).checked = false
//                    }
//                }

//                arr[position].checked=true
//                notifyDataSetChanged();
            })


        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}


