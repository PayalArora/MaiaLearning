package com.maialearning.ui.adapter


import android.content.Context
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.maialearning.R
import com.maialearning.model.CareerClusterModel
import android.view.View as View


class CareerCluster(
    context: Context?,
    algorithmList: ArrayList<CareerClusterModel.CareerCluster>
) :
    ArrayAdapter<CareerClusterModel.CareerCluster>(context!!, 0, algorithmList) {
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
        val currentItem: CareerClusterModel.CareerCluster? = getItem(position)

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName?.setText(currentItem.title)
        }
        return convertView!!
    }
}