package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.AttachItemRowBinding
import com.maialearning.model.DashboardOverdueResponse


class AttachmentAdapter(val con: Context,val  worksheetFileId: List<DashboardOverdueResponse.AssignmentItem.WorksheetFileIdItem?>?): RecyclerView.Adapter<AttachmentAdapter.ViewHolder>() {
    var isSelected = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: AttachItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = AttachItemRowBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        if (position ==0 ){
            viewHolder.binding.image.setImageDrawable( getDrawable(con,R.drawable.ic_documents))
            viewHolder.binding.textName.setText(worksheetFileId?.get(position)?.fileName)
//        }else{
//            viewHolder.binding.image.setImageDrawable(getDrawable(con,R.drawable.ic_excel))
//            viewHolder.binding.textName.setText("Spreadsheet File")
//        }



    }

    override fun getItemCount(): Int {
        return worksheetFileId!!.size
    }
}