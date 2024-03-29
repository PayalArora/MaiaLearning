package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.SearchCareerItemBinding
import com.maialearning.model.CareerSearchResponseItem
import com.maialearning.util.checkNonNull
import com.maialearning.util.extractYoutubeId
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import kotlin.math.roundToInt

class SearchCareerAdapter(
    var context: Context,
    val arrayList: ArrayList<CareerSearchResponseItem>?,
    var likeClick: (code: String?,
                    title: String?, type:Boolean?) -> Unit,
    var itemClick:(url: String?,
                   title: String?)-> Unit,
    var openFactsheet:(item:CareerSearchResponseItem, position:Int)->Unit
) : RecyclerView.Adapter<SearchCareerAdapter.ViewHolder>() {
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
           if (item?.isfav== false) {
               viewHolder.binding.like.setImageResource(R.drawable.like)
           } else  {
               viewHolder.binding.like.setImageResource(R.drawable.heart_filled)
           }
           item?.videoUrl?.let {
               val videoId = extractYoutubeId(item?.videoUrl)

               val img_url =
                   "http://img.youtube.com/vi/" + videoId.toString() + "/0.jpg" // this is link which will give u thumnail image of that video
               Picasso.with(context)
                   .load(img_url)
                   .error(R.drawable.static_coll).into(videoView)

           }
           viewHolder.binding.like.setOnClickListener {
               if (item?.isfav?:false == false) {
                   arrayList?.get(position)?.isfav = true
                   likeClick(arrayList?.get(position)?.onetId, arrayList?.get(position)?.title, arrayList?.get(position)?.isfav )

               } else {
                   arrayList?.get(position)?.isfav = false
                   likeClick(arrayList?.get(position)?.nid, arrayList?.get(position)?.title, arrayList?.get(position)?.isfav )

               }
               notifyDataSetChanged()
               }
           image.setOnClickListener{
               itemClick(item?.videoUrl,arrayList?.get(position)?.title )
           }
           name.setOnClickListener {
               item?.let { it1 -> openFactsheet(it1, position) }
           }
           if (item?.search_type == "demand") {
               postingLayout.visibility = View.VISIBLE
               if (checkNonNull(item?.uniquePostings)){
                   if (item?.uniquePostings?.toDoubleOrNull() is Double){
                       val posting: Double? = item?.uniquePostings?.toDouble()
                       val formatter = DecimalFormat("#,###")
                       postings.text = "${formatter.format(posting)} Unique Postings"
                   }
               }
           }

       }
    }

    override fun getItemCount(): Int {
            return arrayList?.size!!
    }



}
