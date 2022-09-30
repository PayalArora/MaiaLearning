package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.CompareCareersViewpagerBinding
import com.maialearning.model.BrightOutlookModel
import kotlinx.serialization.json.JsonObject
import org.json.JSONObject
import java.util.stream.Collectors

class CareerCompareAdapter
    (
    var context: Context,
    var arrayListOut: ArrayList<BrightOutlookModel.Data>, val json: JSONObject
) : RecyclerView.Adapter<CareerCompareAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: CompareCareersViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = CompareCareersViewpagerBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.name.text = arrayListOut.get(position).title
        viewHolder.binding.name1.text = arrayListOut.get(position).education.stream()?.collect(
            Collectors.joining(",", "", "")
        )
        viewHolder.binding.text2.text = arrayListOut.get(position).salary
        viewHolder.binding.text1.text = arrayListOut.get(position).brightOutlook.stream()?.collect(
            Collectors.joining(",", "", "")
        )
        viewHolder.binding.detail.text =
            json.optJSONObject(arrayListOut.get(position).ccode).optString("detail")
    }

    override fun getItemCount(): Int {
        return arrayListOut.size
    }

}

