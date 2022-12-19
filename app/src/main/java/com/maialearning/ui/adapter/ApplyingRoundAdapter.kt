package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.AppRoundItemBinding
import com.maialearning.databinding.AttachItemRowBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.model.DashboardOverdueResponse
import com.maialearning.util.checkNonNull
import com.maialearning.util.convertDateToLong
import com.maialearning.util.formateDateFromstring
import com.maialearning.util.replaceInvertedComas

class ApplyingRoundAdapter(
    val list: List<ConsiderModel.ApplicationRoundDetail>,
    val collegeAppLicationType: ConsiderModel.CollType?
) :
    RecyclerView.Adapter<ApplyingRoundAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: AppRoundItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = AppRoundItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            roundValue.text = list.get(position).roundNumber
            typeValue.text = getAppType(collegeAppLicationType, list.get(position).appType)
            if (checkNonNull(list.get(position).appStatus))
            statusValue.text = list.get(position).appStatus
            else
                statusValue.text = "---"
            deadlineValue.setText(formateDateFromstring(
                "yyyy-MM-dd hh:mm:ss",
                "MMM dd, yyyy",
                list.get(position).deadline))
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }
    fun getAppType( collegeAppLicationType: ConsiderModel.CollType?, key:String):String?{
        collegeAppLicationType?.collType?.let {
            for (item in it){
                if (item.key.replaceInvertedComas().equals(key)){
                    return item.value
                }
            }
        }
        return null
    }
}