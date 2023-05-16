package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.SearchCareerItemBinding
import com.maialearning.model.CareerListResponse
import com.maialearning.model.CareerSearchResponseItem
import com.maialearning.util.checkNonNull
import com.maialearning.util.extractYoutubeId
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import kotlin.math.roundToInt

class ListCareerAdapter(
    var context: Context,
    val arrayList: ArrayList<CareerListResponse.CareerItem>?,
    var itemClick:(url: String?,
                   title: String?)-> Unit,
    var openFactsheet:(item: CareerListResponse.CareerItem, position:Int)->Unit
) : RecyclerView.Adapter<ListCareerAdapter.ViewHolder>() {
    var isSelected = false
    var count = 0

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: SearchCareerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = SearchCareerItemBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
       viewHolder.binding.apply {
           val item = arrayList?.get(position)
           name.text = item?.title
           name1.text = item?.description
           val amount: Double? = item?.averageSalary?.toDouble()?.roundToInt()?.toDouble()
           val formatter = DecimalFormat("#,###")
           text2.text = "${formatter.format(amount)}"
           viewHolder.binding.like.visibility = View.GONE
           item?.videoUrl?.let {
               val videoId = extractYoutubeId(item?.videoUrl)

               val img_url =
                   "http://img.youtube.com/vi/" + videoId.toString() + "/0.jpg" // this is link which will give u thumnail image of that video
               Picasso.with(context)
                   .load(img_url)
                   .error(R.drawable.static_coll).into(videoView)

           }
           image.setOnClickListener{
               itemClick(item?.videoUrl,arrayList?.get(position)?.title )
           }
           name.setOnClickListener {
               item?.let { it1 -> openFactsheet(it1, position) }
           }
       }
    }

    override fun getItemCount(): Int {
            return arrayList?.size!!
    }



}
