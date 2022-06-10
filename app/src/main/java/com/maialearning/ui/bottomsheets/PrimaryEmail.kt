package com.maialearning.ui.bottomsheets

import android.content.res.Resources
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.R
import com.maialearning.databinding.PrimaryEmailSheetBinding
import com.maialearning.databinding.ProfileLayoutBinding
import com.maialearning.ui.adapter.ProfileAdapter

class PrimaryEmail(val con: FragmentActivity, val layoutInflater: LayoutInflater) {

    fun showDialog() {
        val dialog = BottomSheetDialog(con)
        val sheetBinding: PrimaryEmailSheetBinding =
            PrimaryEmailSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        setAdapter(sheetBinding,dialog)

    }

    private fun setAdapter(mBinding: PrimaryEmailSheetBinding,dialog: BottomSheetDialog) {
        mBinding.clearText.setOnClickListener { dialog.dismiss() }
        mBinding.backTxt.setOnClickListener { dialog.dismiss()  }
        mBinding.saveBtn.setOnClickListener { dialog.dismiss() }
    }
}