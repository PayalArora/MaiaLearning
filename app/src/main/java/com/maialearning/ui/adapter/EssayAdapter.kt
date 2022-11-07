package com.maialearning.ui.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.EssayRowBinding
import com.maialearning.databinding.ItemMilestonesBinding
import com.maialearning.model.DataItem
import com.maialearning.model.RecomdersModel
import com.maialearning.ui.fragments.OnItemClickOption
import com.maialearning.ui.fragments.onMenuClick
import com.maialearning.util.OnLoadMoreListener

class EssayAdapter(
    val data: ArrayList<DataItem?>, recycler: RecyclerView, val onClickOption: onMenuClick,

    ) : RecyclerView.Adapter<EssayAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    lateinit var mOnLoadMoreListener: OnLoadMoreListener
    private val linearLayoutManager: LinearLayoutManager =
        recycler.layoutManager as LinearLayoutManager
    var lastVisibleItem: Int = 0
    private val viewTypeItem = 0
    private val viewTypeLoading = 1
    var totalItemCount: Int = 0
    private var isLoading: Boolean = false
    private var totalPages: Int = 0
    var currentPages = 0

    init {
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = linearLayoutManager.childCount
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleItemCount) {
                    if (totalPages != currentPages) {
                        mOnLoadMoreListener.onLoadMore()
                        isLoading = true
                    }
                }
            }
        })
    }

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

        if (data.get(position)?.promptDescription != null)
            viewHolder.binding.textDescription.text =
                Html.fromHtml(data.get(position)?.promptDescription)
        if (data.get(position)?.promptMaxLengthValue != null)
            viewHolder.binding.textWords.text =
                data.get(position)?.promptMaxLengthValue + " " + data.get(position)?.promptMaxLengthUnit
        if (!data.get(position)?.essayStatus.equals("draft"))
            viewHolder.binding.textReview.text =
                viewHolder.binding.root.context.getString(R.string.review_by) + " " + data.get(
                    position
                )?.essayReviewer?.name
        viewHolder.binding.menuBtn.setOnClickListener {
            data.get(position)?.let { it1 -> onClickOption.onMenuClick(it1,position, it) }
        }

    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    fun setLoaded() {
        isLoading = false
    }

    fun addAllLis(list: ArrayList<DataItem?>, total: Int, current: Int) {
        this.data.addAll(list)
        this.totalPages = total
        this.currentPages = current
        notifyItemChanged(list.size)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}

