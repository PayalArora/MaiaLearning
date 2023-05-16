package com.maialearning.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maialearning.R
import com.maialearning.databinding.CareerAdapterBinding
import com.maialearning.databinding.SpinnerRegionTypeBinding
import com.maialearning.model.CareersItem
import com.maialearning.model.CeebResponseItem

class CeeBAdapter(
    private val mContext: Context,
    private val mLayoutResourceId: Int,
    CeebResponseItem: ArrayList<CeebResponseItem>
) :
ArrayAdapter<CeebResponseItem>(mContext, mLayoutResourceId, CeebResponseItem) {
    private val city: MutableList<CeebResponseItem> = ArrayList(CeebResponseItem)
    private var allCeebResponseItem: List<CeebResponseItem> = ArrayList(CeebResponseItem)

    override fun getCount(): Int {
        return city.size
    }
    override fun getItem(position: Int): CeebResponseItem {
        return city[position]
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = (mContext as Activity).layoutInflater
            convertView = inflater.inflate(mLayoutResourceId, parent, false)
        }
        try {
            val city: CeebResponseItem = getItem(position)
            val cityAutoCompleteView = convertView!!.findViewById<View>(R.id.text1) as TextView
            cityAutoCompleteView.text = city.title
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return convertView!!
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun convertResultToString(resultValue: Any) :String {
                return (resultValue as CeebResponseItem).fieldCeebCodeValue?:""
            }
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    val citySuggestion: MutableList<CeebResponseItem> = ArrayList()
                    for (city in allCeebResponseItem) {
                        if (city.fieldCeebCodeValue?.contains(constraint.toString())?:false
                        ) {
                            citySuggestion.add(city)
                        }
                    }
                    filterResults.values = citySuggestion
                    filterResults.count = citySuggestion.size
                }
                return filterResults
            }
            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults
            ) {
                city.clear()
                if (results.count > 0) {
                    for (result in results.values as List<*>) {
                        if (result is CeebResponseItem) {
                            city.add(result)
                        }
                    }
                    notifyDataSetChanged()
                } else if (constraint == null) {
                    city.addAll(allCeebResponseItem)
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}
