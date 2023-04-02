package com.maialearning.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.NonNull
import com.maialearning.R
import com.maialearning.model.CareerCategoryResponseItem
import com.maialearning.model.CareerClusterModel
import com.maialearning.model.IndustryModel

class CategoriesAdapter(
    context: Context?,
    algorithmList: ArrayList<CareerCategoryResponseItem>,
    var click:(CareerCategoryResponseItem)->Unit
) :
    ArrayAdapter<CareerCategoryResponseItem>(context!!, 0, algorithmList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }
    override fun getDropDownView(
        position: Int,
        convertView: View?,
        @NonNull parent: ViewGroup
    ): View {
        return initView(position, convertView, parent)
    }

    private fun initView(
        position: Int, convertView: View?,
        parent: ViewGroup
    ): View {
        // It is used to set our custom view.
        var convertView: View? = convertView
        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.spinner_text, parent, false)
        }
        val textViewName: TextView? = convertView?.findViewById(R.id.text1)
        val currentItem: CareerCategoryResponseItem? = getItem(position)

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName?.setText(currentItem.name)
        }
//        textViewName?.setOnClickListener {
//            click(getItem(position)!!)
//        }
        return convertView!!
    }
}