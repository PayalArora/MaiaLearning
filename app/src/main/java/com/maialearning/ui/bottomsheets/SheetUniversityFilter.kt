package com.maialearning.ui.bottomsheets

import android.content.Context
import android.content.res.Resources
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.databinding.UniversityFilterBinding
import com.maialearning.model.*
import com.maialearning.ui.activity.ClickFilters
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.*

class SheetUniversityFilter(val con: UniversitiesActivity, val layoutInflater: LayoutInflater) {

    fun showDialog(
        list: ArrayList<FilterUSModelClass.CountryList>,
        context: Context,
        flagImg: ImageView
    ) {
        val dialog = BottomSheetDialog(con)
        val sheetBinding: UniversityFilterBinding = UniversityFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.search.visibility = View.GONE
        sheetBinding.filters.setText(con.resources.getString(R.string.country))
        sheetBinding.clearText.setText(con.resources.getString(R.string.done))
        sheetBinding.clearText.setOnClickListener {
            con.refreshTab()
            dialog.dismiss()
        }
        sheetBinding.backBtn.setOnClickListener {
            con.refreshTab()
            dialog.dismiss()
        }
        sheetBinding.reciepentList.adapter =
            CountryAdapter(list, ::selectCountry, context, flagImg)

    }

    fun regionFilter(
        list: ArrayList<String>,
        visibility: Int,
        title: String,
        positiion: Int,
        visibility_spinner: Int = 0
    ) {
        val dialog = BottomSheetDialog(con)
        var reigonList = arrayListOf<Region>()
        val sheetBinding: UniversityFilterBinding = UniversityFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.clearText.setText(con.resources.getString(R.string.done))
        sheetBinding.search.visibility = visibility
        sheetBinding.filters.setText(title)
        dialog.show()

        sheetBinding.spinnerLay.visibility = visibility_spinner
        sheetBinding.invisibleLay.visibility = visibility_spinner
        sheetBinding.backBtn.setOnClickListener { dialog.dismiss() }

        sheetBinding.clearText.setOnClickListener {
            con.reigonFilterDone(reigonList)
            dialog.dismiss()
        }
        if (positiion == 1) {

            for (i in list){
                if (UniversitiesActivity.Filters.selectedRegion.contains(i))
                reigonList.add(Region(i, true))
                else
                    reigonList.add(Region(i, false))
            }
            sheetBinding.reciepentList.adapter =
                ReigonAdapter(reigonList, ::selectedReigon)
            sheetBinding.spinnerLay.visibility = View.GONE

        } else if (positiion == 2) {
//            sheetBinding.spinnerLay.visibility = View.GONE
//            sheetBinding.reciepentList.adapter =
//                ItemListAdapter(con.resources.getStringArray(R.array.list), con)
//            sheetBinding.close.setOnClickListener {
//                sheetBinding.searchText.setText("")
//            }

        } else if (positiion == 7) {
            sheetBinding.spinnerLay.visibility = View.VISIBLE
//            sheetBinding.reciepentList.adapter =
//                SportsFilterAdapter(sheetBinding.root.context.resources.getStringArray(R.array.sports).toList() as ArrayList<String>)
//
           sheetBinding.close.setOnClickListener {
                sheetBinding.searchText.setText("")
            }
            val others = sheetBinding.root.context.resources.getStringArray(R.array.spinner_sports)
            val adapter = ArrayAdapter(
                sheetBinding.root.context,
                R.layout.spinner_text, others
            )
            sheetBinding.spinner.adapter = adapter
        } else if (positiion == 5) {

            // selectivity
            sheetBinding.reciepentList.adapter =
                CustomRadioAdapter()
            sheetBinding.spinnerLay.visibility = View.GONE
            sheetBinding.close.setOnClickListener {
                sheetBinding.searchText.setText("")
            }
        } else if (positiion == 4) {
            sheetBinding.reciepentList.adapter =
                ItemListAdapter(list, con)
            sheetBinding.spinnerLay.visibility = View.GONE
            sheetBinding.close.setOnClickListener {
                sheetBinding.searchText.setText("")
            }
        }


    }
    fun selectedReigon(list: ArrayList<Region>) {

    }

    fun selectRegionFilter(
        visibility: Int,
        title: String,
        listUni: MutableList<UniersitiesListModel>,
        visibility_spinner: Int = 0
    ) {

        val dialog = BottomSheetDialog(con)
        val sheetBinding: UniversityFilterBinding = UniversityFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.clearText.setText(con.resources.getString(R.string.done))
        sheetBinding.search.visibility = visibility
        sheetBinding.filters.setText(title)
        dialog.show()

        sheetBinding.spinnerLay.visibility = visibility_spinner
        sheetBinding.invisibleLay.visibility = visibility_spinner
        sheetBinding.backBtn.setOnClickListener { dialog.dismiss() }

        sheetBinding.clearText.setOnClickListener {
            dialog.dismiss()
            con.listFilterDone(listUni)
        }

        sheetBinding.spinnerLay.visibility = View.GONE
        sheetBinding.reciepentList.adapter =
            UniversityListAdapter(listUni, con)
        sheetBinding.close.setOnClickListener {
            sheetBinding.searchText.setText("")
        }

    }
    fun stateFilter(
        visibility: Int,
        title: String,
        listUni:ArrayList<KeyVal>,
        visibility_spinner: Int = 0,
                positiion: Int
    ) {

        val dialog = BottomSheetDialog(con)
        val sheetBinding: UniversityFilterBinding = UniversityFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.clearText.setText(con.resources.getString(R.string.done))
        sheetBinding.search.visibility = visibility
        sheetBinding.filters.setText(title)
        dialog.show()

        sheetBinding.spinnerLay.visibility = visibility_spinner
        sheetBinding.invisibleLay.visibility = visibility_spinner
        sheetBinding.backBtn.setOnClickListener { dialog.dismiss() }

        sheetBinding.clearText.setOnClickListener {
            dialog.dismiss()
            when(positiion){
                2->{con.stateFilterDone(listUni)}
                5->{con.selectivityFilterDone(listUni)}
            }

        }
        when(positiion){
            2->{for (i in UniversitiesActivity.selectedStateFilter){
                for (j in listUni){
                    if (i == j.key) {
                        j.checked = true
                    }
                }
            }}
            5->{for (i in UniversitiesActivity.selectedSelectivityFilter){
                for (j in listUni){
                    if (i == j.key) {
                        j.checked = true
                    }
                }
            }}
        }

        sheetBinding.reciepentList.adapter =
            UniversityStateAdapter(listUni, con)
        sheetBinding.close.setOnClickListener {
            sheetBinding.searchText.setText("")
        }

    }
}

fun selectCountry(s: String) {
}

