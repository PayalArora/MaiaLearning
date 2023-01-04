package com.maialearning.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.ItemNotesBinding
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.ItemAddUniversityBinding
import com.maialearning.databinding.ProgressLayoutBinding
import com.maialearning.databinding.UniListItemBinding
import com.maialearning.model.UniversitiesSearchModel
import com.maialearning.ui.activity.ClickFilters
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.util.OnLoadMoreListener
import com.maialearning.util.UNIV_LOGO_URL
import com.maialearning.util.parseNA
import com.squareup.picasso.Picasso


class AddUniversiityAdapter(
    var university_list: ArrayList<UniversitiesSearchModel?>, recycler: RecyclerView,
    val onItemClick: ClickFilters
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
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

    class ViewHolder(val binding: ItemAddUniversityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val inflater = LayoutInflater.from(viewGroup.context)
//        val binding = ItemAddUniversityBinding.inflate(inflater, viewGroup, false)
//
//        return ViewHolder(binding)

        val inflater = LayoutInflater.from(viewGroup.context)
        val viewHolder: RecyclerView.ViewHolder?
        return when (viewType) {
            viewTypeItem -> {
                val bindingView = ItemAddUniversityBinding.inflate(inflater, viewGroup, false)
                viewHolder = AddUniversiityAdapter.ViewHolder(bindingView)
                viewHolder
            }
            viewTypeLoading -> {
                val view = ProgressLayoutBinding.inflate(inflater, viewGroup, false)
                viewHolder = AddUniversiityAdapter.ViewHolder2(view)
                viewHolder
            }
            else -> null!!
        }
    }

    class ViewHolder2(val binding: ProgressLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    // Replace the contents of a view (invoked by the layout manager)
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        viewHolder.binding.root.setOnClickListener { onItemClick.onClick(position, 0, null) }
//        viewHolder.binding.addCheck.setOnCheckedChangeListener { compoundButton, b ->
//            if (compoundButton.isChecked) {
//                viewHolder.binding.addCheck.setText(viewHolder.binding.root.context.getString(R.string.added))
//            } else {
//                viewHolder.binding.addCheck.setText(viewHolder.binding.root.context.getString(R.string.add))
//            }
//        }
//    }

    override fun getItemCount(): Int {
        return university_list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.binding.univ.setText(university_list.get(position)?.collegeName)
            holder.binding.root.setOnClickListener { onItemClick.onClick(position, 0, null) }
            holder.binding.addCheck.setOnCheckedChangeListener { compoundButton, b ->
                if (compoundButton.isChecked) {
                    holder.binding.addCheck.setText(holder.binding.root.context.getString(R.string.added))
                } else {
                    holder.binding.addCheck.setText(holder.binding.root.context.getString(R.string.add))
                }
            }
        } else {
            val loadingViewHolder = holder as ViewHolder2
            loadingViewHolder.binding.layout.setBackgroundColor(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.white
                )
            )
            loadingViewHolder.binding.progressBar
        }
    }


    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    fun setLoaded() {
        isLoading = false
    }

    fun addAllLis(
        list: ArrayList<UniversitiesSearchModel?>,
        total: Int,
        current: Int,
        searchtext: Boolean
    ) {
        if (searchtext)
            university_list.clear()
        this.university_list.addAll(list)
        this.totalPages = total
        this.currentPages = current
        if (searchtext)
            notifyDataSetChanged()
        else
            notifyItemChanged(university_list.size)
    }


}

