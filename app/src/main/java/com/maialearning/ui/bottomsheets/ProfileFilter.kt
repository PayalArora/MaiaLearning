package com.maialearning.ui.bottomsheets

import android.Manifest
import android.R.attr.path
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.*
import android.content.pm.PackageManager
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.JsonArray
import com.maialearning.R
import com.maialearning.databinding.PrimaryEmailSheetBinding
import com.maialearning.databinding.ProfileLayoutBinding
import com.maialearning.model.ProfileResponse
import com.maialearning.model.UpdateUserData
import com.maialearning.network.AllAPi
import com.maialearning.ui.adapter.*
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.ext.isInt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URI.create


class ProfileFilter(val con: FragmentActivity, val layoutInflater: LayoutInflater) {
    private val profileModel: ProfileViewModel by con.viewModel()
    val sheetBinding: ProfileLayoutBinding = ProfileLayoutBinding.inflate(layoutInflater)
    var dialog: BottomSheetDialog? = null
    private lateinit var progress: Dialog

    fun showDialog() {
        val dialog = BottomSheetDialog(con)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.close.setOnClickListener { dialog.dismiss() }
        dialog.show()
        progress = showLoadingDialog(con)
        profileWork()
        setAdapter(sheetBinding)
    }

    private fun setAdapter(sheetBinding: ProfileLayoutBinding) {
        var tabArray = arrayOf(
            con.getString(R.string.profile),
            con.getString(R.string.setting),
            con.getString(R.string.connections)
        )
        for (item in tabArray) {
            sheetBinding.tabs.addTab(sheetBinding.tabs.newTab().setText(item))

        }
        val fm: FragmentManager = con.supportFragmentManager
        val adapter = ProfileAdapter(fm, con.lifecycle, tabArray.size, profileModel)
        sheetBinding.viewPager.adapter = adapter
        sheetBinding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(sheetBinding.tabs, sheetBinding.viewPager) { tab, position ->
            tab.setText(tabArray[position])
        }.attach()

        sheetBinding.tabs.tabGravity = TabLayout.GRAVITY_FILL
        sheetBinding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabLayout = (sheetBinding.tabs.getChildAt(0) as ViewGroup).getChildAt(
                    tab!!.position
                ) as LinearLayout
                val tabTextView = tabLayout.getChildAt(1) as TextView
                tabTextView.setTypeface(tabTextView.typeface, Typeface.BOLD)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabLayout = (sheetBinding.tabs.getChildAt(0) as ViewGroup).getChildAt(
                    tab!!.position
                ) as LinearLayout
                val tabTextView = tabLayout.getChildAt(1) as TextView
                tabTextView.setTypeface(null, Typeface.NORMAL)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        sheetBinding.toolbarProf.setOnClickListener {
            checkStoragePermissionAndOpenImageSelection()
        }

    }

    private var profileResponse: ProfileResponse = ProfileResponse()

    private fun profileWork() {
        SharedHelper(con).authkey?.let {
            SharedHelper(con).id?.let { it1 ->
                Log.e(" it1 ", it1)
                profileModel.getProfile("Bearer " + it, it1)
            }
        }
        profileModel.observer.observe(con, {
            sheetBinding.nameTxt.setText(it.info?.firstName + " " + it?.info?.lastName)
            if (SharedHelper(con).convention ?: false) {
                sheetBinding.gradeTxt.setText("ID: ${it.info?.nid} (Grade ${it.info?.grade})")
            } else {
                if (it.info?.grade?.isInt() == true)
                    sheetBinding.gradeTxt.setText("ID: ${it.info?.nid} (Year ${it.info?.grade.toInt() + 1})")
                else
                    sheetBinding.gradeTxt.setText("ID: ${it.info?.nid} (Year ${it.info?.grade})")
            }
            Picasso.with(con).load(it.info?.profilePic).into(sheetBinding.toolbarProf)
            profileResponse = it
        })

        sheetBinding.nameTxt.setOnClickListener {
            showSheet("name")
        }

        profileModel.showError.observe(con) {
            progress.dismiss()
        }
    }


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

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_CHOOSE_PHOTO = 2
    private val REQUEST_FILE_ACCESS = 3

    private fun checkStoragePermissionAndOpenImageSelection() {
        if (ContextCompat.checkSelfPermission(
                con,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            selectImage(con)
        } else {
            //changed here
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    con,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_FILE_ACCESS
                )
            }
        }
    }

    private fun selectImage(context: Context) {
        val options =
            arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Choose your profile picture")
        builder.setItems(options, DialogInterface.OnClickListener { dialogInterface, item ->
            if (options[item] == "Take Photo") {
                checkCameraPermissionAndOpen()
            } else if (options[item] == "Choose from Gallery") {
                openAlbum()
            } else if (options[item] == "Cancel") {
                dialogInterface.dismiss()
            }
        })
        builder.show()
    }

    //+10 changed its signature as Fragment; without it  onRequestPermissionsResult won't bbe called
    private fun checkCameraPermissionAndOpen() {
        if (ContextCompat.checkSelfPermission(
                con,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            //changed here
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(con, arrayOf(Manifest.permission.CAMERA), REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            con.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
            Log.e("Exception", e.localizedMessage)
        }
    }

    private fun openAlbum() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        con.startActivityForResult(intent, REQUEST_CHOOSE_PHOTO)
    }

    private var fileUri: Uri? = null
    private var bitmap: Bitmap? = null
    private var imageExt: String? = null

    fun setData(requestCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            sheetBinding.toolbarProf.setImageBitmap(imageBitmap)
            fileUri = data.data
            bitmap = imageBitmap
            val byteArrayOutputStream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            val tempUri = getImageUri(con.applicationContext, imageBitmap)
            // CALL THIS METHOD TO GET THE ACTUAL PATH
            imagePath = getRealPathFromURI(tempUri)
            imageExt = tempUri?.let { getMimeType(con, it) }
            uploadImageWork()
        } else if (requestCode == REQUEST_CHOOSE_PHOTO) {
            handleImageFromGallery(data)
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getRealPathFromURI(uri: Uri?): String? {
        var path = ""
        if (con.getContentResolver() != null) {
            val cursor: Cursor? =
                uri?.let {
                    con.getContentResolver().query(it, null, null, null, null)
                }
            if (cursor != null) {
                cursor.moveToFirst()
                val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path
    }


    var imagePath: String? = null
    private fun handleImageFromGallery(data: Intent?) {
        imagePath = null
        val uri = data!!.data
        if (DocumentsContract.isDocumentUri(con, uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri?.authority) {
                //Getting Images from Documents
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = imagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selsetion)
            } else if ("com.android.providers.downloads.documents" == uri?.authority) {
                //Getting Images from Downloads
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(docId)
                )
                imagePath = imagePath(contentUri, null)
            }
        } else if ("content".equals(uri?.scheme, ignoreCase = true)) {
            imagePath = imagePath(uri, null)
        } else if ("file".equals(uri?.scheme, ignoreCase = true)) {
            imagePath = uri?.path
        }
        imageExt = uri?.let { getMimeType(con, it) }
        displayImage(imagePath)
        uploadImageWork()
    }


    private fun displayImage(imagePath: String?) {
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
//            profileImageView.setImageBitmap(bitmap)
        } else {
            Toast.makeText(con, "Failed to get image", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("Range")
    private fun imagePath(uri: Uri?, selection: String?): String {
        var path: String? = null
        val cursor =
            uri?.let {
                con.contentResolver.query(it, null, selection, null, null)
            }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }

    fun getMimeType(context: Context, uri: Uri): String? {
        val extension: String?

        //Check uri format to avoid null
        extension = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //If scheme is a content
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
        }
        return extension
    }

    private fun uploadImageWork() {
        progress.show()
        SharedHelper(con).id?.let {
            imageExt?.let { it1 ->
                SharedHelper(con).ethnicityTarget?.let { it2 ->
                    profileModel.getImageURl(
                        "Bearer " + SharedHelper(con).authkey,
                        it, it1, it2
                    )
                }
            }
        }

        profileModel.imageUrlObserver.observe(con) {
            Log.e("Response: ", " $it")
            upload(it)
        }

    }

    private fun upload(it: JsonArray?) {


        val file: File = File(imagePath)
        val requestBody: RequestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        it?.get(0)?.let { it1 ->
            profileModel.uploadImage(
                "image/${imageExt}",
                it1.toString(),
                requestBody
            )
        }
        profileModel.uploadImageObserver.observe(con) {
            Log.e("Response: ", " $it")
            progress.dismiss()
            profileWork()
        }


    }
}