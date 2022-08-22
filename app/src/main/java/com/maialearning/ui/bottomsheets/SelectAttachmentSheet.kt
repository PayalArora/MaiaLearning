package com.maialearning.ui.bottomsheets

import android.R.attr.tag
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import com.maialearning.databinding.FileUploadSheetBinding
import com.maialearning.model.FileUploadData
import com.maialearning.model.FmTagsResponseItem
import com.maialearning.ui.adapter.TagAdapter
import com.maialearning.util.ANTI_VIRUS
import com.maialearning.util.BASE_URL
import com.maialearning.util.PathUtil
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.DashboardFragViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File


class SelectAttachmentSheet(
    val con: Context,
    val layoutInflater: LayoutInflater,
    val data: Intent?, val requestCode: Int,
    val dashboardViewModel: DashboardFragViewModel,
    val fragment: Fragment,
    val nId: String?, var dialogDetail: BottomSheetDialog
) {
    var tagList: ArrayList<FmTagsResponseItem?> = arrayListOf()
    private var selectedList = arrayListOf<String?>()
    private var tagVisisble = false
    lateinit var sheetBinding: FileUploadSheetBinding
    private lateinit var progress: Dialog
    var url: String? = null
    var uriAfterUploading: String? = null
    fun showDialog() {
        progress = showLoadingDialog(con)
        val dialog = BottomSheetDialog(con)
        sheetBinding =
            FileUploadSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()

        dashboardViewModel.getTags()
        dashboardViewModel.fmTagsObserver.observe(fragment) {

            tagList = it
            tagList = tagList.filter { it?.type == "default" } as ArrayList<FmTagsResponseItem?>

            sheetBinding.tagList.layoutManager =
                LinearLayoutManager(con, LinearLayoutManager.VERTICAL, false)
            sheetBinding.tagList.adapter =
                tagList?.let { it1 -> TagAdapter(it1, selectedList, ::tagClick) }
        }

        sheetBinding.tagText.setOnClickListener {
            if (tagVisisble) {
                sheetBinding.tagList.visibility = View.GONE
                tagVisisble = false

            } else {
                sheetBinding.tagList.visibility = View.VISIBLE
                tagVisisble = true
            }
        }

        setData()
        sheetBinding.saveBtn.setOnClickListener {
            if (!imagePath.isNullOrEmpty()) {
                SharedHelper(con).id?.let { it1 ->
                    //  progress.show()
                    dashboardViewModel.getPresignedURL(
                        "Bearer " + SharedHelper(con).authkey,
                        it1, sheetBinding.nameEdt.text.toString()
                    )
                }
            } else {
                Toast.makeText(con, "Please select Document First", Toast.LENGTH_SHORT).show()
            }
        }
        dashboardViewModel.presignedUrlObserver.observe(fragment) {
//            progress.dismiss()

            Log.e("Presigned url", " " + it)
            val file = File(imagePath)
            val requestBody: RequestBody =
                RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
            url = it?.get("presigned_url")?.toString()?.replace("\"", "")
            uriAfterUploading = it?.get("uri")?.toString()
            Toast.makeText(con, "Fetching Url", Toast.LENGTH_SHORT)
                .show()
            url.let { it1 ->
                dashboardViewModel.uploadImage(
                    "image/${imageExt}",
                    it1.toString(),
                    requestBody
                )
            }
            dashboardViewModel.uploadImageObserver.observe(fragment) {
                Log.e("Response: ", " $it")
                Toast.makeText(con, "Checking File", Toast.LENGTH_SHORT)
                url?.let { it1 -> dashboardViewModel.checkFileVirus(ANTI_VIRUS, it1) }
                //profileWork()
            }
        }
        dashboardViewModel.fileVirusObserver.observe(fragment) {
            var obj = JSONObject(it.toString())
            if (obj?.get("file_status").toString() == "clean") {
                Toast.makeText(con, "File Status: " + obj?.get("file_status").toString(), Toast.LENGTH_SHORT)
                    .show()
                uploadWork()
            }else{
                Toast.makeText(con, "Please check your File", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        dashboardViewModel.showError.observe(fragment) {
            progress.dismiss()
        }
        dashboardViewModel.uploadEndObserver.observe(fragment) {
            progress.dismiss()
            dialog.dismiss()
            dialogDetail.dismiss()
            SharedHelper(con).id?.let {
                dashboardViewModel.getOverDueCompleted(
                    "Bearer " + SharedHelper(con).authkey,
                    it
                )
            }
        }
    }

    private fun uploadWork() {
        val fileUploadData = FileUploadData()
        fileUploadData.description = sheetBinding.descriptionEdt.text.toString()
        fileUploadData.fm_uid = SharedHelper(con).id.toString()
        fileUploadData.name = sheetBinding.nameEdt.text.toString()
        fileUploadData.uri = uriAfterUploading.toString().replace("\"", "")
        fileUploadData.mime = imageExt.toString()
        fileUploadData.tags = selectedList

        val file: File = File(imagePath)
        val file_size = (file.length() / 1024).toString().toInt()
        fileUploadData.size = file_size.toString()

        fileUploadData.nid = "" + nId
        SharedHelper(con).authkey?.let {
            dashboardViewModel.uploadFile(
                "Bearer " + it,
                fileUploadData
            )
        }


    }

    fun tagClick(ethinicity: String, checked: Boolean) {
        if (checked) {
            if (!selectedList.contains(ethinicity))
                selectedList.add(ethinicity)
        } else
            selectedList.remove(ethinicity)

        sheetBinding.tagText.setText("Selected tag: ${selectedList.size} Selected")
    }

    private val REQUEST_IMAGE_UPCOMING_DETAIL = 11
    private val REQUEST_CHOOSE_PHOTO_UPCOMING_DETAIL = 22
    private val REQUEST_CHOOSE_PDF_UPCOMING_DETAIL = 23


    @SuppressLint("Range")
    fun setData() {
        if (requestCode == REQUEST_IMAGE_UPCOMING_DETAIL) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            fileUri = data.data
            bitmap = imageBitmap
            val byteArrayOutputStream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            val tempUri = getImageUri(con, imageBitmap)
            // CALL THIS METHOD TO GET THE ACTUAL PATH
            imagePath = getRealPathFromURI(tempUri)
            imageExt = tempUri?.let { getMimeType(con, it) }
            sheetBinding.imgLay.setImageBitmap(imageBitmap)
            sheetBinding.fileName.setText(tempUri?.getName(con) ?: null)
            sheetBinding.nameEdt.setText(tempUri?.getName(con) ?: null)
//            uploadImageWork()
        } else if (requestCode == REQUEST_CHOOSE_PHOTO_UPCOMING_DETAIL) {
            handleImageFromGallery(data)
        } else if (requestCode == REQUEST_CHOOSE_PDF_UPCOMING_DETAIL) {

            fileUri = data?.data!!
            val uri: Uri = data?.data!!
            val uriString: String = uri.toString()
            var pdfName: String? = null
            if (uriString.startsWith("content://")) {
                var myCursor: Cursor? = null
                try {
                    // Setting the PDF to the TextView
                    myCursor = con!!.contentResolver.query(uri, null, null, null, null)
                    if (myCursor != null && myCursor.moveToFirst()) {
                        pdfName =
                            myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                        sheetBinding.nameEdt.setText(pdfName)
                        sheetBinding.fileName.setText(pdfName)
                    }
                } finally {
                    myCursor?.close()
                }
            }

            imagePath = PathUtil.getDriveFilePath(con, uri);
//            imagePath=getRealPathFromURI(fileUri)
        }
    }

    private var fileUri: Uri? = null
    private var bitmap: Bitmap? = null
    private var imageExt: String? = null


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
        fileUri = data!!.data
        if (DocumentsContract.isDocumentUri(con, fileUri)) {
            val docId = DocumentsContract.getDocumentId(fileUri)
            if ("com.android.providers.media.documents" == fileUri?.authority) {
                //Getting Images from Documents
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = imagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selsetion)
            } else if ("com.android.providers.downloads.documents" == fileUri?.authority) {
                //Getting Images from Downloads
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(docId)
                )
                imagePath = imagePath(contentUri, null)
            }
        } else if ("content".equals(fileUri?.scheme, ignoreCase = true)) {
            imagePath = imagePath(fileUri, null)
        } else if ("file".equals(fileUri?.scheme, ignoreCase = true)) {
            imagePath = fileUri?.path
        }
        imageExt = fileUri?.let { getMimeType(con, it) }
        displayImage(imagePath)
        sheetBinding.fileName.setText(fileUri?.getName(con) ?: null)
        sheetBinding.nameEdt.setText(fileUri?.getName(con) ?: null)
    }


    private fun displayImage(imagePath: String?) {
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            sheetBinding.imgLay.setImageBitmap(bitmap)
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

    fun Uri.getName(context: Context): String {
        val returnCursor = context.contentResolver.query(this, null, null, null, null)
        val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor?.moveToFirst()
        val fileName = nameIndex?.let { returnCursor?.getString(it) }
        returnCursor?.close()
        return fileName!!
    }

}