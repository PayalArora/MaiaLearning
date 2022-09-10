package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.ProgressLayoutBinding
import com.maialearning.databinding.RecomendationCollegeItemBinding
import com.maialearning.model.RecCollegeModel
import com.maialearning.model.RecomdersModel
import com.maialearning.util.OnLoadMoreListener

class RecommenderCollegeAdapter(var context: Context, var list: ArrayList<RecCollegeModel.CollegeDetails?>, recycler: RecyclerView,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var selectedPostion = 0
    private val linearLayoutManager: LinearLayoutManager = recycler.layoutManager as LinearLayoutManager
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
    class ViewHolder(val binding: RecomendationCollegeItemBinding) :
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
                val bindingView = RecomendationCollegeItemBinding.inflate(inflater, viewGroup, false)
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
            viewHolder.binding.uniName.text = list[position]?.collegeName
            viewHolder.binding.recyclerView.adapter=RecommenderSubAdapter(context, list[position]?.recoName)
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

    fun addAllLis(list: ArrayList<RecCollegeModel.CollegeDetails?>, total: Int, current: Int) {
        this.list.addAll(list)
        this.totalPages = total
        this.currentPages = current
        notifyItemChanged(list.size)
    }

}