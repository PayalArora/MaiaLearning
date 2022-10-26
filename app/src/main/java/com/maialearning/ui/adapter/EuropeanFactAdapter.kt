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
import com.maialearning.model.EuropeanUniList
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.util.OnLoadMoreListener
import com.maialearning.util.UNIV_LOGO_URL
import com.maialearning.util.prefhandler.SharedHelper
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class EuropeanFactAdapter(
    var context: Context,
    var university_list: ArrayList<EuropeanUniList.CollegeList?>,
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
            viewHolder.binding.university.setText(university_list.get(position)?.collegeName)
//            viewHolder.binding.name.setText(university_list.get(position)?.cityState)
            val loc = Locale("", SharedHelper(context).country)
            viewHolder.binding.country.setText(loc.getDisplayCountry())
//            viewHolder.binding.profit.setText(university_list.get(position)?.collegeType)
//            viewHolder.binding.typeValue.setText("SAT Scores")
//            viewHolder.binding.type.setText(university_list.get(position)?.satScores)
//            viewHolder.binding.term.setText(university_list.get(position)?.actScores)
//            viewHolder.binding.termValue.setText("ACT Scores")
//            viewHolder.binding.plan.setText(university_list.get(position)?.acceptance ?: "N/A")
//            viewHolder.binding.planValue.setText("Acceptance Rate")
            if (university_list.get(position)?.topPickFlag?:false== false) {
                viewHolder.binding.like.setImageResource(R.drawable.like)
            } else if (university_list.get(position)?.topPickFlag?:false  == true) {
                viewHolder.binding.like.setImageResource(R.drawable.heart_filled)
            }
//        https://college-images-staging.maialearning.com/us/488031/logo_sm.jpg
            Picasso.with(viewHolder.binding.root.context).load(
                "$UNIV_LOGO_URL${SharedHelper(context).country?.toLowerCase()}/${
                    university_list.get(position)?.collegeNid
                }/logo_sm.jpg").placeholder(R.drawable.static_coll).error(R.drawable.static_coll).into(viewHolder.binding.image)
            viewHolder.binding.list.visibility = View.GONE
            viewHolder.binding.profit.visibility = View.GONE
            viewHolder.binding.location.visibility = View.GONE
            viewHolder.binding.name.setText("${university_list.get(position)?.programList?.size?:0} Programs")

            viewHolder.binding.university.setOnClickListener {

                university_list.get(position)?.collegeNid?.let { it1 ->
                    (context as UniversitiesActivity).bottomSheetEurope(
                        it1,university_list.get(position)?.topPickFlag, university_list.get(position)?.collegeName, position,  university_list.get(position),::likeClick)
                }
            }
            viewHolder.binding.image.setOnClickListener {
                // click
                university_list.get(position)?.collegeNid?.let { it1 ->
                    (context as UniversitiesActivity).bottomSheetEurope(
                        it1,university_list.get(position)?.topPickFlag, university_list.get(position)?.collegeName, position,university_list.get(position), ::likeClick )
                }
            }
            viewHolder.binding.like.setOnClickListener {

                if (university_list.get(position)?.topPickFlag?:false == false) {
                    university_list.get(position)?.topPickFlag = true
                } else {
                    university_list.get(position)?.topPickFlag = false
                }
                click(university_list.get(position)?.collegeNid,university_list.get(position)?.topPickFlag?:false )
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

    fun addAllLis(list: ArrayList<EuropeanUniList.CollegeList?>, total: Int, current: Int) {
        this.university_list.addAll(list)
        this.totalPages = total
        this.currentPages = current
        notifyItemChanged(university_list.size)
    }
    fun likeClick(i: Int, flag:Int?) {
        if (flag == 1)
        this.university_list.get(i)?.topPickFlag = true
        else
            this.university_list.get(i)?.topPickFlag = false
        notifyDataSetChanged()
    }
}
