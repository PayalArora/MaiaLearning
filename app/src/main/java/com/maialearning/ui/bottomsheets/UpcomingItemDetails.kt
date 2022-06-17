package com.maialearning.ui.bottomsheets

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.databinding.UniversityFilterBinding
import com.maialearning.databinding.UpcomingItemDetailBinding
import com.maialearning.ui.activity.ClickFilters
import com.maialearning.ui.activity.UniversitiesActivity
import com.maialearning.ui.adapter.*

class UpcomingItemDetails(
    val con: FragmentActivity,
    val layoutInflater: LayoutInflater,
    val pos: Int
) {

    fun showDialog() {
        val dialog = BottomSheetDialog(con)
        val sheetBinding: UpcomingItemDetailBinding =
            UpcomingItemDetailBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        setAdapter(sheetBinding, dialog)

        if (pos < 2) {
            sheetBinding.worksheet.visibility = View.GONE
            sheetBinding.tic.visibility = View.GONE
            sheetBinding.completeBtn.visibility = View.VISIBLE
            sheetBinding.completedText.visibility = View.GONE
            sheetBinding.writeLay.visibility = View.GONE
        } else if(pos ==2){
            sheetBinding.worksheet.visibility = View.VISIBLE
            sheetBinding.completeBtn.visibility = View.GONE
            sheetBinding.tic.visibility = View.GONE
            sheetBinding.completedText.visibility = View.VISIBLE
            sheetBinding.writeLay.visibility = View.GONE

        }else {
            sheetBinding.worksheet.visibility = View.GONE
            sheetBinding.tic.visibility = View.GONE
            sheetBinding.completeBtn.visibility = View.GONE
            sheetBinding.completedText.visibility = View.GONE
            sheetBinding.writeLay.visibility = View.VISIBLE

        }
    }

    private fun setAdapter(sheetBinding: UpcomingItemDetailBinding, dialog: BottomSheetDialog) {
       val linearLayoutManager= LinearLayoutManager(con, LinearLayoutManager.HORIZONTAL ,false)
       sheetBinding.attachList.layoutManager=linearLayoutManager
        sheetBinding.attachList.adapter = AttachmentAdapter(con)
    }

}