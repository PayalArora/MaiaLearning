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
import com.maialearning.ui.adapter.CountryAdapter

class SheetUniversityFilter(val con: Context, val layoutInflater: LayoutInflater) {

    fun showDialog(){
        val dialog = BottomSheetDialog(con)
        val sheetBinding: UniversityFilterBinding = UniversityFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight =( (Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.search.visibility = View.GONE
        sheetBinding.filters.setText(con.resources.getString(R.string.country))
        sheetBinding.clearText.setOnClickListener { dialog.dismiss()}
        sheetBinding.reciepentList.adapter = CountryAdapter(con.resources.getStringArray(R.array.Country), con as ClickFilters)

    }
}