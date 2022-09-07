package com.maialearning.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.ProgressLayoutBinding
import com.maialearning.databinding.RecommdersAdapterBinding
import com.maialearning.model.RecomdersModel
import com.maialearning.ui.fragments.OnItemClickOption
import com.maialearning.ui.fragments.RecommendationFragment
import com.maialearning.ui.fragments.onClick
import com.maialearning.util.OnLoadMoreListener
import com.maialearning.util.getDate

class RecommenderAdapter(
    var context: Context,
    var list: ArrayList<RecomdersModel.Data?>,
    var onClick: onClick,
    recycler: RecyclerView
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var selectedPostion = 0
    private val linearLayoutManager: LinearLayoutManager =
        recycler.layoutManager as LinearLayoutManager
    var lastVisibleItem: Int = 0
    private val viewTypeItem = 0
    private val viewTypeLoading = 1
    var totalItemCount: Int = 0
    private var isLoading: Boolean = false
    private var totalPages: Int = 0
    var currentPages = 0
    lateinit var mOnLoadMoreListener: OnLoadMoreListener

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

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: RecommdersAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val viewHolder: RecyclerView.ViewHolder?
        return when (viewType) {
            viewTypeItem -> {
                val bindingView = RecommdersAdapterBinding.inflate(inflater, viewGroup, false)
                viewHolder = ViewHolder(bindingView)
                viewHolder
            }
            viewTypeLoading -> {
                val view = ProgressLayoutBinding.inflate(inflater, viewGroup, false)
                viewHolder = ViewHolder2(view)
                viewHolder
            }
            else -> null!!
        }
    }

    class ViewHolder2(val binding: ProgressLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ViewHolder) {
            viewHolder.binding.name.text = list[position]?.name?.trim()
            if (list[position]?.recoCreated != null && list[position]?.recoCreated != "null" && list[position]?.recoCreated != "")
                viewHolder.binding.created.text =
                    "Rec: " + getDate(list[position]?.recoCreated!!.toLong(), "MMM dd, yyyy")
            if (list[position]?.done != 0) {
                viewHolder.binding.status.text = "Complete"
            } else {
                viewHolder.binding.status.text = "Incomplete"
            }
            if (list[position]?.dueDate != null && list[position]?.dueDate != "null" && list[position]?.dueDate != "")
                viewHolder.binding.statusD.text =
                    getDate(list[position]?.dueDate!!.toLong(), "MM/dd/yyyy")
            if (list[position]?.isRefLetterCompleted != 0) {
                if (list[position]?.ucasRefLetterCompleted != null && list[position]?.ucasRefLetterCompleted != "null" && list[position]?.ucasRefLetterCompleted != "")
                    viewHolder.binding.letter.text =
                        "Complete , " + getDate(
                            list[position]?.ucasRefLetterCompleted!!.toLong(),
                            "MM/dd/yyyy"
                        )
            } else {
                if (list[position]?.ucasRefLetterdue != null && list[position]?.ucasRefLetterdue != "null" && list[position]?.ucasRefLetterdue != "")
                    viewHolder.binding.letter.text =
                        "Incomplete , " + getDate(
                            list[position]?.ucasRefLetterdue!!.toLong(),
                            "MM/dd/yyyy"
                        )
            }
            if (list[position]?.reqFilename == null || list[position]?.reqFilename == "null" || list[position]?.reqFilename == "") {
                viewHolder.binding.brag.text = "Update Brag Sheet"
                viewHolder.binding.cancelLay.visibility = View.VISIBLE
            } else {
                viewHolder.binding.cancelLay.visibility = View.GONE
            }
            viewHolder.binding.cancel.setOnClickListener {
                cancelPopup(position)
            }

        } else {
            val loadingViewHolder = viewHolder as ViewHolder2
            loadingViewHolder.binding.layout.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            loadingViewHolder.binding.progressBar
        }
    }

    private fun cancelPopup(position: Int) {
        AlertDialog.Builder(context)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage(context.getString(R.string.cancel_req_msg))
            .setPositiveButton(
                "Yes"
            ) { dialog, _ ->
                (onClick.onCancelClick(list.get(position)))
                list.removeAt(position)
                notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] == null) viewTypeLoading else viewTypeItem
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    fun setLoaded() {
        isLoading = false
    }

    fun addAllLis(list: ArrayList<RecomdersModel.Data?>, total: Int, current: Int) {
        this.list.addAll(list)
        this.totalPages = total
        this.currentPages = current
        notifyItemChanged(list.size)
    }

}