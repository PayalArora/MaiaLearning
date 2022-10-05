package com.maialearning.ui.adapter

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
import com.maialearning.databinding.UniListItemBinding
import com.maialearning.model.UniversitiesSearchModel
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.util.OnLoadMoreListener
import com.maialearning.util.UNIV_LOGO_URL
import com.maialearning.util.parseNA
import com.squareup.picasso.Picasso

class UniFactAdapter(
    var context: Context,
    var university_list: ArrayList<UniversitiesSearchModel?>,
    var click: (position: Int) -> Unit,
    recycler: RecyclerView
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

    class ViewHolder(val binding: UniListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
//        val binding = UniListItemBinding.inflate(inflater, viewGroup, false)
//
//        return ViewHolder(binding)
        val viewHolder: RecyclerView.ViewHolder?
        return when (viewType) {
            viewTypeItem -> {
                val bindingView = UniListItemBinding.inflate(inflater, viewGroup, false)
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
            viewHolder.binding.university.setText(parseNA(university_list.get(position)?.collegeName))
            viewHolder.binding.name.setText(university_list.get(position)?.cityState)
            viewHolder.binding.country.setText(university_list.get(position)?.country)
            viewHolder.binding.profit.setText(university_list.get(position)?.collegeType)
            viewHolder.binding.typeValue.setText("SAT Scores")
            viewHolder.binding.type.setText(university_list.get(position)?.satScores)
            viewHolder.binding.term.setText(university_list.get(position)?.actScores)
            viewHolder.binding.termValue.setText("ACT Scores")
            viewHolder.binding.plan.setText(university_list.get(position)?.acceptance ?: "N/A")
            viewHolder.binding.planValue.setText("Acceptance Rate")
            if (university_list.get(position)?.countryCode!= "US"){
                viewHolder.binding.list.visibility = View.GONE
                viewHolder.binding.location.visibility = View.GONE
            } else
            {
                viewHolder.binding.list.visibility = View.VISIBLE
                viewHolder.binding.location.visibility = View.VISIBLE
            }
            if (university_list.get(position)?.topPickFlag == 0) {
                viewHolder.binding.like.setImageResource(R.drawable.like)
            } else if (university_list.get(position)?.topPickFlag == 1) {
                viewHolder.binding.like.setImageResource(R.drawable.heart_filled)
            }
//        https://college-images-staging.maialearning.com/us/488031/logo_sm.jpg
            Picasso.with(viewHolder.binding.root.context).load(
                "${UNIV_LOGO_URL}${university_list.get(position)?.countryCode?.toLowerCase()}/${
                    university_list.get(position)?.unitid
                }/logo_sm.jpg"
            ).error(R.drawable.static_coll).into(viewHolder.binding.image)


            viewHolder.binding.university.setOnClickListener {
                // click
                (context as UniversitiesActivity).bottomSheetWork(university_list.get(position)!!)
            }
            viewHolder.binding.image.setOnClickListener {
                // click
                (context as UniversitiesActivity).bottomSheetWork(university_list.get(position)!!)
            }
            viewHolder.binding.like.setOnClickListener {
                click(position)
                if (university_list.get(position)?.topPickFlag == 1) {
                    university_list.get(position)?.topPickFlag = 0
                } else {
                    university_list.get(position)?.topPickFlag = 1
                }
                notifyDataSetChanged()
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

    override fun getItemCount(): Int {
        return university_list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (university_list[position] == null) viewTypeLoading else viewTypeItem
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    fun setLoaded() {
        isLoading = false
    }

    fun addAllLis(list: ArrayList<UniversitiesSearchModel?>, total: Int, current: Int) {
        this.university_list.addAll(list)
        this.totalPages = total
        this.currentPages = current
        notifyItemChanged(university_list.size)
    }
}

