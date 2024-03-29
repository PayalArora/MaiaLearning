package com.maialearning.ui.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.databinding.ItemCountryFilterBinding
import com.maialearning.model.FilterUSModelClass
import com.maialearning.ui.activity.ClickFilters
import com.maialearning.util.prefhandler.SharedHelper
import com.squareup.picasso.Picasso


class CountryAdapter(
    val arr:ArrayList<FilterUSModelClass.CountryList>, var click: (url:String) -> Unit, var context :Context,  val mFlagImg: ImageView
) : RecyclerView.Adapter<CountryAdapter.ViewHolder>()

{
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    var checked:Array<Boolean> = arrayOf(false, false, false,false,false,false,false,false)
    class ViewHolder(val binding: ItemCountryFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemCountryFilterBinding.inflate(inflater, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
       // viewHolder. binding.root.setOnClickListener { onItemClick.onClick(position, 1) }
        viewHolder.binding.apply {

            filters.text = arr[position].name

            if (arr[position].select){
                imgCheck.visibility = View.VISIBLE
                filters.setTypeface(filters.typeface, Typeface.BOLD)
            } else {
                imgCheck.visibility = View.GONE
                filters.setTypeface(filters.typeface, Typeface.NORMAL)
            }

            Picasso.with(viewHolder.binding.root.context)
                .load("https://countryflagsapi.com/png/${arr[position].code}")
                .into(flagImg)


            root.setOnClickListener {
                for (i in arr.indices) {
                    arr[i].select = false
                }
                arr[position].select=true
               // SharedHelper(context).country=arr[position].code
               click(arr[position].code)

                Picasso.with(viewHolder.binding.root.context)
                    .load("https://countryflagsapi.com/png/${arr[position].code}")
                    .into(mFlagImg)

                notifyDataSetChanged()
            }

        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

}


