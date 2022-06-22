package com.maialearning.ui.bottomsheets

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.databinding.UniversityFilterBinding
import com.maialearning.ui.activity.ClickFilters
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.*

class SheetUniversityFilter(val con: UniversitiesActivity, val layoutInflater: LayoutInflater) {

    fun showDialog() {
        val dialog = BottomSheetDialog(con)
        val sheetBinding: UniversityFilterBinding = UniversityFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.search.visibility = View.GONE
        sheetBinding.filters.setText(con.resources.getString(R.string.country))
        sheetBinding.clearText.setOnClickListener { dialog.dismiss() }
        sheetBinding.backBtn.setOnClickListener { dialog.dismiss() }
        sheetBinding.reciepentList.adapter =
            CountryAdapter(con.resources.getStringArray(R.array.Country), con as ClickFilters)

    }

    fun regionFilter(visibility: Int, title: String, positiion: Int, visibility_spinner: Int = 0) {
        val dialog = BottomSheetDialog(con)
        val sheetBinding: UniversityFilterBinding = UniversityFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.search.visibility = visibility
        sheetBinding.filters.setText(title)
        dialog.show()

        sheetBinding.spinnerLay.visibility = visibility_spinner
        sheetBinding.backBtn.setOnClickListener { dialog.dismiss() }

        sheetBinding.clearText.setOnClickListener { dialog.dismiss() }
        if (positiion == 1){
            sheetBinding.reciepentList.adapter =
                ReigonAdapter(con.resources.getStringArray(R.array.Region), con)
            sheetBinding.spinnerLay.visibility = View.GONE

        }
        else if (positiion == 2) {
            sheetBinding.reciepentList.adapter =
                ItemListAdapter(con.resources.getStringArray(R.array.list), con)
            sheetBinding.close.setOnClickListener {
                sheetBinding.searchText.setText("")
            }

        }

        else if (positiion == 6) {
            sheetBinding.reciepentList.adapter =
                SportsFilterAdapter(con.resources.getStringArray(R.array.sports))
            sheetBinding.close.setOnClickListener {
                sheetBinding.searchText.setText("")
            }
            val others = sheetBinding.root.context.resources.getStringArray(R.array.spinner_sports)
            val adapter = ArrayAdapter(
                sheetBinding.root.context,
                R.layout.spinner_text, others
            )
            sheetBinding.spinner.adapter = adapter
        }
        else if (positiion == 5) {
            sheetBinding.reciepentList.adapter =
                CustomRadioAdapter()
            sheetBinding.close.setOnClickListener {
                sheetBinding.searchText.setText("")
            }
        } else if (positiion == 4) {
            sheetBinding.reciepentList.adapter =
                ItemListAdapter(con.resources.getStringArray(R.array.selectivity), con)
            sheetBinding.close.setOnClickListener {
                sheetBinding.searchText.setText("")
            }
        }


    }
}