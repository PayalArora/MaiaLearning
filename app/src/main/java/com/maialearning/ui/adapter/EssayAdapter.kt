package com.maialearning.ui.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.EssayRowBinding
import com.maialearning.databinding.ItemMilestonesBinding
import com.maialearning.model.DataItem
import com.maialearning.util.OnLoadMoreListener

class EssayAdapter(val data: List<DataItem?>) : RecyclerView.Adapter<EssayAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    lateinit var mOnLoadMoreListener: OnLoadMoreListener

    class ViewHolder(val binding: EssayRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = EssayRowBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.textType.text = data.get(position)?.essayStatus
        viewHolder.binding.textHeadline.text = data.get(position)?.promptName

        if(data.get(position)?.promptDescription!=null)
        viewHolder.binding.textDescription.text = Html.fromHtml(data.get(position)?.promptDescription)

        viewHolder.binding.textWords.text =
            data.get(position)?.promptMaxLengthValue + " " + data.get(position)?.promptMaxLengthUnit
        if(!data.get(position)?.promptDescription.equals("draft"))
        viewHolder.binding.textReview.text =
            viewHolder.binding.root.context.getString(R.string.review_by) + " "+data.get(position)?.essayReviewer?.name


    }
    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }
    override fun getItemCount(): Int {
        return data.size
    }

}

