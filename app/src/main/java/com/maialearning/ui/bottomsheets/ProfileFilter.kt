package com.maialearning.ui.bottomsheets

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.Manifest.permission_group.CAMERA
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Typeface
import android.opengl.Visibility
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.R
import com.maialearning.databinding.PrimaryEmailSheetBinding
import com.maialearning.databinding.ProfileLayoutBinding
import com.maialearning.model.ProfileResponse
import com.maialearning.model.UpdateUserData
import com.maialearning.ui.adapter.*
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.ext.isInt

class ProfileFilter(val con: FragmentActivity, val layoutInflater: LayoutInflater) {
    private val profileModel: ProfileViewModel by con.viewModel()

    fun showDialog() {
        val dialog = BottomSheetDialog(con)
        val sheetBinding: ProfileLayoutBinding = ProfileLayoutBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.close.setOnClickListener { dialog.dismiss() }
        dialog.show()
        profileWork(sheetBinding)
        setAdapter(sheetBinding)
    }

    private fun setAdapter(mBinding: ProfileLayoutBinding) {
        var tabArray = arrayOf(
            con.getString(R.string.profile),
            con.getString(R.string.setting),
            con.getString(R.string.connections)
        )
        for (item in tabArray) {
            mBinding.tabs.addTab(mBinding.tabs.newTab().setText(item))
        }
        val fm: FragmentManager = con.supportFragmentManager
        val adapter = ProfileAdapter(fm, con.lifecycle, tabArray.size, profileModel)
        mBinding.viewPager.adapter = adapter
        mBinding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(mBinding.tabs, mBinding.viewPager) { tab, position ->
            tab.setText(tabArray[position])
        }.attach()

        mBinding.tabs.tabGravity = TabLayout.GRAVITY_FILL
        mBinding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabLayout = (mBinding.tabs.getChildAt(0) as ViewGroup).getChildAt(
                    tab!!.position
                ) as LinearLayout
                val tabTextView = tabLayout.getChildAt(1) as TextView
                tabTextView.setTypeface(tabTextView.typeface, Typeface.BOLD)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabLayout = (mBinding.tabs.getChildAt(0) as ViewGroup).getChildAt(
                    tab!!.position
                ) as LinearLayout
                val tabTextView = tabLayout.getChildAt(1) as TextView
                tabTextView.setTypeface(null, Typeface.NORMAL)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        mBinding.toolbarProf.setOnClickListener {
            if (checkAndRequestPermissions(con)) {
                chooseImage()
            } else {
                checkAndRequestPermissions(con)
            }
        }

    }

    private var profileResponse: ProfileResponse = ProfileResponse()

    private fun profileWork(mBinding: ProfileLayoutBinding) {
        SharedHelper(con).authkey?.let {
            SharedHelper(con).id?.let { it1 ->
                Log.e(" it1 ", it1)
                profileModel.getProfile("Bearer " + it, it1)
            }
        }
        profileModel.observer.observe(con, {
            mBinding.nameTxt.setText("${it.info?.salutation} ${it.info?.lastName}, ${it.info?.firstName} ${it.info?.middleName}")
            if (SharedHelper(con).convention ?: false) {
                mBinding.gradeTxt.setText("ID: ${it.info?.nid} (Grade ${it.info?.grade})")
            } else {
                if (it.info?.grade?.isInt() == true)
                    mBinding.gradeTxt.setText("ID: ${it.info?.nid} (Year ${it.info?.grade.toInt() + 1})")
                else
                    mBinding.gradeTxt.setText("ID: ${it.info?.nid} (Year ${it.info?.grade})")
            }
            Picasso.with(con).load(it.info?.profilePic).into(mBinding.toolbarProf)
            profileResponse = it
        })

        mBinding.nameTxt.setOnClickListener {
            showSheet("name")
        }
    }

    var dialog: BottomSheetDialog? = null
    private lateinit var progress: Dialog


    private fun showSheet(s: String) {
        dialog = BottomSheetDialog(con)
        val sheetBinding: PrimaryEmailSheetBinding =
            PrimaryEmailSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog?.setContentView(sheetBinding.root)
        dialog?.show()
        sheetBinding.emailEdt.visibility = View.GONE
        sheetBinding.addressLay.visibility = View.VISIBLE
        sheetBinding.addOne.setText(con.getString(R.string.firs_name))
        sheetBinding.addTwo.setText(con.getString(R.string.middle_name))
        sheetBinding.postalTxt.setText(con.getString(R.string.last_name))
        sheetBinding.cityTxt.setText(con.getString(R.string.nick_name))
        sheetBinding.filters.setText(con.getString(R.string.name))
        sheetBinding.addressEdt.setText(profileResponse.info?.firstName)
        sheetBinding.address2Edt.setText(profileResponse.info?.middleName)
        sheetBinding.postalcodeEdt.setText(profileResponse.info?.lastName)
        sheetBinding.cityEdt.setText(profileResponse.info?.nickName)
        sheetBinding.saveBtn.setOnClickListener {
            if (sheetBinding.addressEdt.text.isBlank()) {
                sheetBinding.addressEdt.setError(con.getString(R.string.err_firstname))
                return@setOnClickListener
            } else if (sheetBinding.postalcodeEdt.text.isBlank()) {
                sheetBinding.addressEdt.setError(con.getString(R.string.err_lastname))
                return@setOnClickListener
            } else {
            }
            val updateUserData = UpdateUserData()
            updateUserData.userdata.uid = SharedHelper(con).id.toString()
            updateUserData.userdata.first_name = sheetBinding.addressEdt.text.toString()
            updateUserData.userdata.last_name = sheetBinding.address2Edt.text.toString()
            updateUserData.userdata.middle_name = sheetBinding.postalcodeEdt.text.toString()
            updateUserData.userdata.nick_name = sheetBinding.cityEdt.text.toString()
            progress = showLoadingDialog(con)
            progress.show()
            updateName(updateUserData)
        }

    }

    private fun updateName(updateUserData: UpdateUserData) {
        SharedHelper(con).authkey?.let {
            profileModel.updateEmail("Bearer $it", updateUserData)
        }

        profileModel.updateObserver.observe(con) {
            Log.e("Response", "" + it.toString())
            progress.dismiss()
            SharedHelper(con).authkey?.let {
                SharedHelper(con).id?.let { it1 ->
                    profileModel.getProfile("Bearer $it", it1)
                    dialog?.dismiss()
                    progress.dismiss()
                }
            }
        }
    }

    val REQUEST_ID_MULTIPLE_PERMISSIONS = 101

    fun checkAndRequestPermissions(context: Activity?): Boolean {
        val WExtstorePermission = ContextCompat.checkSelfPermission(
            context!!,
            WRITE_EXTERNAL_STORAGE
        )
        val cameraPermission = ContextCompat.checkSelfPermission(
            context!!,
            CAMERA
        )
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(CAMERA)
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                .add(WRITE_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                context, listPermissionsNeeded
                    .toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }


    private val PERMISSION_REQUEST_CODE = 200

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>?,
        grantResults: IntArray?
    ) {
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> if (ContextCompat.checkSelfPermission(
                    con,
                    CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                    getApplicationContext(),
                    "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT
                )
                    .show()
            } else if (ContextCompat.checkSelfPermission(
                    con,
                    WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                    getApplicationContext(),
                    "FlagUp Requires Access to Your Storage.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                chooseImage()
            }
        }
    }

    private fun chooseImage() {
        val optionsMenu = arrayOf<CharSequence>(
            "Take Photo",
            "Choose from Gallery",
            "Exit"
        ) // create a menuOption Array
        // create a dialog for showing the optionsMenu
        val builder = AlertDialog.Builder(con)
        // set the items in builder
        builder.setItems(
            optionsMenu
        ) { dialogInterface, i ->
            if (optionsMenu[i] == "Take Photo") {
                // Open the camera and get the photo
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                con.startActivityForResult(takePicture, 0)
            } else if (optionsMenu[i] == "Choose from Gallery") {
                // choose from  external storage
                val pickPhoto = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                con.startActivityForResult(pickPhoto, 1)
            } else if (optionsMenu[i] == "Exit") {
                dialogInterface.dismiss()
            }
        }
        builder.show()
    }
}