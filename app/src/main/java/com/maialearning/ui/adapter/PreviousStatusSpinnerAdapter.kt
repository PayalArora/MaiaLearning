package com.maialearning.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.maialearning.model.KeyVal
import com.maialearning.model.StatusModel

class PreviousStatusSpinnerAdapter (context: Context, val list:ArrayList<StatusModel>, resource: Int) :
    ArrayAdapter<StatusModel>(context, resource) {

    // Normally is the same view, but you can customize it if you want
    override fun getDropDownView(
        position: Int, convertView: View?,
        parent: ViewGroup?
    ): View? {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        label.setTextColor(Color.BLACK)
        label.setText(list.get(position).status)

        return label
    }
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): StatusModel {
        return list .get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        val label = super.getView(position, convertView, parent!!) as TextView
        label.setTextColor(Color.BLACK)
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(list.get(position).status)

        // And finally return your dynamic (or custom) view for each spinner item
        return label
    }
}