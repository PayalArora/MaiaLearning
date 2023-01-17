package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.maialearning.R
import com.maialearning.databinding.ProgressLayoutBinding
import com.maialearning.model.GermanUniversitiesResponse
import com.maialearning.util.OnLoadMoreListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.UniListGermanBinding
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.util.UNIV_LOGO_URL
import com.maialearning.util.prefhandler.SharedHelper
import com.squareup.picasso.Picasso

class GermanFactAdapter(
    var context: Context,
    var university_list: ArrayList<GermanUniversitiesResponse.Data.CollegeData?>,
    var click: (position: String?, like:Boolean) -> Unit,
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

    class ViewHolder(val binding: UniListGermanBinding) : RecyclerView.ViewHolder(binding.root) {
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
                val bindingView = UniListGermanBinding.inflate(inflater, viewGroup, false)
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
            viewHolder.binding.university.text = university_list[position]?.collegeName

            if ((university_list[position]?.courseList?.size ?: 0) > 2) {
                viewHolder.binding.profit.visibility = View.VISIBLE
                viewHolder.binding.profit.text =
                    "See all " + university_list[position]?.courseList?.size ?: "0"
            } else {
                viewHolder.binding.profit.visibility = View.GONE
            }
            Picasso.with(viewHolder.binding.root.context).load(
                "$UNIV_LOGO_URL${SharedHelper(context).country?.toLowerCase()}/${
                    university_list.get(position)?.collegeNid
                }/logo_sm.jpg").placeholder(R.drawable.static_coll).error(R.drawable.static_coll).into(viewHolder.binding.image)

            if ((university_list[position]?.courseList?.size ?: 0) >= 2) {
                viewHolder.binding.type.text = university_list[position]?.courseList?.get(0)?.courseName
                viewHolder.binding.type2.text = university_list[position]?.courseList?.get(1)?.courseName
                viewHolder.binding.term.text = university_list[position]?.courseList?.get(0)?.studyMode
                viewHolder.binding.term2.text = university_list[position]?.courseList?.get(1)?.studyMode
                viewHolder.binding.termValue.text = university_list[position]?.courseList?.get(0)?.location
                viewHolder.binding.termValue2.text = university_list[position]?.courseList?.get(1)?.location

            } else if ((university_list[position]?.courseList?.size ?: 0) >= 1) {
                viewHolder.binding.type.text = university_list[position]?.courseList?.get(0)?.courseName
                viewHolder.binding.type2.text = "--"
                viewHolder.binding.term.text = university_list[position]?.courseList?.get(0)?.studyMode
                viewHolder.binding.term2.text = "--"
                viewHolder.binding.termValue.text = university_list[position]?.courseList?.get(0)?.location
                viewHolder.binding.termValue2.text = "--"
            } else {
                viewHolder.binding.type.text = "--"
                viewHolder.binding.type2.text = "--"
                viewHolder.binding.term.text = "--"
                viewHolder.binding.term2.text = "--"
                viewHolder.binding.termValue2.text = "--"
                viewHolder.binding.termValue.text = "--"
            }
            if (university_list[position]?.topPickFlag == 0) {
                viewHolder.binding.like.setImageResource(R.drawable.like)
            } else if (university_list[position]?.topPickFlag == 1) {
                viewHolder.binding.like.setImageResource(R.drawable.heart_filled)
            }

            viewHolder.binding.university.setOnClickListener {
                // click

                    university_list.get(position)?.collegeNid?.let { it1 ->
                        (context as UniversitiesActivity).bottomSheetGerman(
                    it1,university_list.get(position)?.topPickFlag, university_list.get(position)?.collegeName,position, university_list.get(position), ::likeClick)
                    }

            }
            viewHolder.binding.image.setOnClickListener {
                // click
                university_list.get(position)?.collegeNid?.let { it1 ->
                    (context as UniversitiesActivity).bottomSheetGerman(
                        it1,university_list.get(position)?.topPickFlag, university_list.get(position)?.collegeName,position, university_list.get(position), ::likeClick)
                }
            }

            viewHolder.binding.like.setOnClickListener {

                if (university_list.get(position)?.topPickFlag?:0 == 0) {
                    university_list.get(position)?.topPickFlag = 1
                    click(university_list.get(position)?.collegeNid,true)
                } else {
                    click(university_list.get(position)?.collegeNid,false)
                    university_list.get(position)?.topPickFlag = 0
                }

                notifyDataSetChanged()
            }

            viewHolder.binding.profit.setOnClickListener {
                (context as UniversitiesActivity).bottomSheetCourseList(null,university_list[position]!!,"gr")
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

    fun addAllLis(
        list: ArrayList<GermanUniversitiesResponse.Data.CollegeData?>,
        total: Int,
        current: Int, searchtext:Boolean
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
    fun likeClick(i: Int, flag:Int?) {
        this.university_list.get(i)?.topPickFlag = flag
        notifyDataSetChanged()
    }
}

