package com.maialearning.ui.bottomsheets

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.R
import com.maialearning.databinding.PrimaryEmailSheetBinding
import com.maialearning.databinding.ProfileLayoutBinding
import com.maialearning.model.UpdateUserData
import com.maialearning.ui.adapter.ProfileAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.viewmodel.ProfileViewModel
import java.util.regex.Pattern

class PrimaryEmail(
    val con: FragmentActivity,
    val layoutInflater: LayoutInflater,
    val viewModel: ProfileViewModel,
    val email: String
) {

    fun showDialog() {
        val dialog = BottomSheetDialog(con)
        val sheetBinding: PrimaryEmailSheetBinding =
            PrimaryEmailSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        setAdapter(sheetBinding, dialog)

    }

    private fun setAdapter(mBinding: PrimaryEmailSheetBinding, dialog: BottomSheetDialog) {
        mBinding.clearText.setOnClickListener { dialog.dismiss() }
        mBinding.backTxt.setOnClickListener { dialog.dismiss() }
        mBinding.saveBtn.setOnClickListener {
            if (mBinding.emailEdt.getText().isBlank() || !isEmailIdValid(mBinding.emailEdt.getText().toString())) {
                mBinding.emailEdt.setError(con.getString(R.string.err_invalid_email_id))
            } else {
                updateEmail(mBinding.emailEdt.text.toString())
            }
        }
        mBinding.emailEdt.setText(email)
    }

    private fun updateEmail(email: String) {
        var updateUserData = UpdateUserData()
        updateUserData.userdata.mail = email
        updateUserData.userdata.uid = SharedHelper(con).id.toString()
        SharedHelper(con).authkey?.let { viewModel.updateEmail("Bearer " + it, updateUserData) }

    }

    fun isEmailIdValid(emailId: String): Boolean {
        val regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"
        val pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(emailId)
        return matcher.find()
    }


}