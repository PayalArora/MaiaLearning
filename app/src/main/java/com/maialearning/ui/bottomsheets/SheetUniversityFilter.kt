package com.maialearning.ui.bottomsheets

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnItemClickType
import com.maialearning.databinding.UniversityFilterBinding
import com.maialearning.ui.activity.ClickFilters
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.CountryAdapter
import com.maialearning.ui.adapter.CustomRadioAdapter
import com.maialearning.ui.adapter.ItemListAdapter
import com.maialearning.ui.adapter.ReigonAdapter

class SheetUniversityFilter(val con: UniversitiesActivity, val layoutInflater: LayoutInflater) {

    fun showDialog(){
        val dialog = BottomSheetDialog(con)
        val sheetBinding: UniversityFilterBinding = UniversityFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight =( (Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.search.visibility = View.GONE
        sheetBinding.filters.setText(con.resources.getString(R.string.country))
        sheetBinding.clearText.setOnClickListener { dialog.dismiss()}
        sheetBinding.backBtn.setOnClickListener { dialog.dismiss()}
        sheetBinding.reciepentList.adapter = CountryAdapter(con.resources.getStringArray(R.array.Country), con as ClickFilters)

    }

     fun regionFilter(visibility: Int, title: String, positiion: Int) {
        val dialog = BottomSheetDialog(con)
        val sheetBinding: UniversityFilterBinding = UniversityFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.search.visibility = visibility
        sheetBinding.filters.setText(title)
        dialog.show()
         sheetBinding.backBtn.setOnClickListener { dialog.dismiss()}

        sheetBinding.clearText.setOnClickListener { dialog.dismiss() }
        if (positiion == 1)
            sheetBinding.reciepentList.adapter =
                ReigonAdapter(con.resources.getStringArray(R.array.Region), con)
        else if (positiion == 2) {
            sheetBinding.reciepentList.adapter =
                ItemListAdapter(con.resources.getStringArray(R.array.list), con)
            sheetBinding.close.setOnClickListener {
                sheetBinding.searchText.setText("")
            }

        }
        else if (positiion == 5) {
            sheetBinding.reciepentList.adapter =
                CustomRadioAdapter()
            sheetBinding.close.setOnClickListener {
                sheetBinding.searchText.setText("")
            }
        }

        else if (positiion == 4) {
            sheetBinding.reciepentList.adapter =
                ItemListAdapter(con.resources.getStringArray(R.array.selectivity), con)
            sheetBinding.close.setOnClickListener {
                sheetBinding.searchText.setText("")
            }
        }


    }
}