package com.maialearning.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.*
import android.content.pm.PackageManager
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.calbacks.OnItemClickId
import com.maialearning.databinding.NewMessageBinding
import com.maialearning.model.AttachMessages
import com.maialearning.model.ReceipentModel
import com.maialearning.model.SendMessageModel
import com.maialearning.network.BaseApplication
import com.maialearning.ui.adapter.FilesAdapter
import com.maialearning.ui.adapter.RecipentAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.HomeViewModel
import com.maialearning.viewmodel.MessageViewModel
import com.maialearning.viewmodel.ProfileViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class NewMessageActivity : AppCompatActivity(), OnItemClickId, OnItemClick {
    private lateinit var mBinding: NewMessageBinding
    private lateinit var dialog: Dialog
    private val homeModel: HomeViewModel by viewModel()
    private val msgModel: MessageViewModel by viewModel()
    private val profileModel: ProfileViewModel by viewModel()
    var attachedArray = ArrayList<AttachMessages>()
    var list = ArrayList<ReceipentModel>()
    var selectedList = ArrayList<ReceipentModel>()
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_CHOOSE_PHOTO = 2
    private val REQUEST_FILE_ACCESS = 3
    private var fileUri: Uri? = null
    private var bitmap: Bitmap? = null
    private var imageExt: String? = null
    private var fileName: String? = null
    var arrayList = ArrayList<HashMap<String, String>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = NewMessageBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.root.setBackgroundColor(getResources().getColor(R.color.white))
        mBinding.filesList.adapter = FilesAdapter(this, attachedArray)
        mBinding.toolbar.backBtn.setOnClickListener { finish() }
        mBinding.toolbar.textTitle.setText(getString(R.string.new_message))
        mBinding.textReciepent.setOnClickListener {
            for (i in list){
                i.isSelected = false
            }
            selectedList.clear()
            bottomSheetWork()
        }
        init()
        observer()
    }

    fun init() {
        dialog = showLoadingDialog(this)
        dialog.show()
        homeModel.getRecipients(
            this,
            SharedHelper(BaseApplication.applicationContext()).schoolId ?: "",
            "all"
        )
        mBinding.sendMessageBtn.setOnClickListener {
            if (selectRecipent()) {
                if (mBinding.textDescription.text.toString().trim() != "") {

                    val json = SendMessageModel()
                    val jsonData = json.message
                    jsonData.senderUid=
                        SharedHelper(BaseApplication.applicationContext()).messageId

                    jsonData.messageStatus= "sent"
                    jsonData.messageBody= mBinding.textDescription.text.toString()
                    jsonData.subject=mBinding.textSubject.text.toString()
                    jsonData.auditEntity= "96452bf5-8156-49e4-af30-ec35a958850c"
                    jsonData.recipients= arrayList
                   // jsonData.attachments= arrayList
                    msgModel.createMessage(this, json)
                } else {
                    Toast.makeText(this, "Please add description", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please select Recepent first!", Toast.LENGTH_LONG).show()
            }
        }
        mBinding.textAddFile.setOnClickListener {
            checkStoragePermissionAndOpenImageSelection()
        }
    }

    private fun selectRecipent(): Boolean {
        if (selectedList.size > 0) {
            for (i in 0 until selectedList.size) {
                if (selectedList[i].isSelected) {
                    val mMap = HashMap<String, String>()
                    mMap.put("uid", selectedList[i].message_center_uid)
                    arrayList.add(mMap)
                }

            }
        } else {
            Toast.makeText(this, "Please select list", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    fun observer() {
        homeModel.listArrayObserver.observe(this) {
            it?.let {
                dialog.dismiss()
                val jsonArray =
                    JSONArray(it.toString()) // here  i am getting out of memory

                for (j in 0 until jsonArray.length()) {
                    val objectProgram = jsonArray.get(j)
                    list.add(
                        ReceipentModel(
                            (objectProgram as JSONObject).getString("name"),
                            (objectProgram as JSONObject).getString("message_center_uid"),
                            (objectProgram as JSONObject).getString("uid")
                        )
                    )
                }
            }
        }
        homeModel.showError.observe(this) {
            dialog.dismiss()
        }
        msgModel.imageUrlObserver.observe(this) {
            upload(it)
        }
        msgModel.showError.observe(this) {
            dialog.dismiss()
        }
        msgModel.createObserver.observe(this) {
            Toast.makeText(this, "Message Sent Successfully", Toast.LENGTH_LONG).show()
            dialog.dismiss()
            finish()
        }
    }

    private fun bottomSheetWork() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.reciepent_layout_bottomsheet, null)
        val recyclerView = view.findViewById<RecyclerView>(R.id.reciepent_list)
        recyclerView.adapter = RecipentAdapter(this, list)

        val selectAll = view.findViewById<CheckBox>(R.id.select_all)
        selectAll.setOnClickListener {
            if (selectAll.isChecked) {
                selectedList .addAll(list)
                mBinding.textReciepent.text = "(${selectedList.size} selected)"
            } else {
                selectedList = ArrayList()
            }
            (recyclerView.adapter as RecipentAdapter).selectAll(selectAll.isChecked)
//            dialog.dismiss()
        }
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        dialog.setContentView(view)
        dialog.show()
        view.findViewById<RelativeLayout>(R.id.close).setOnClickListener {

            dialog.dismiss()
        }
        dialog.setOnDismissListener {
            mBinding.textReciepent.text = "(${selectedList.size} selected)"
        }
    }

    override fun onClick(positiion: Int, id: String) {

    }

    override fun onClick(positiion: Int) {
        if (list[positiion].isSelected) {
            selectedList.add(list[positiion])
        } else {
            selectedList.remove(list[positiion])
        }
        mBinding.textReciepent.text = "(${selectedList.size} selected)"

    }

    private fun checkStoragePermissionAndOpenImageSelection() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            selectImage(this)
        } else {
            //changed here
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(
                    this,
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

    private fun openAlbum() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO)
    }

    private fun checkCameraPermissionAndOpen() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            //changed here
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_IMAGE_CAPTURE
                )
            }
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
            Log.e("Exception", e.localizedMessage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setData(requestCode, data)
        } else if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            setData(requestCode, data)
        }
    }

    fun setData(requestCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            fileUri = data.data
            bitmap = imageBitmap
            val byteArrayOutputStream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            val tempUri = getImageUri(this.applicationContext, imageBitmap)
            // CALL THIS METHOD TO GET THE ACTUAL PATH
            imagePath = getRealPathFromURI(tempUri)
            imageExt = tempUri?.let { getMimeType(this, it) }
            fileName = imagePath!!.substring(imagePath?.lastIndexOf("/")!! + 1)
//            sheetBinding.toolbarProf.setImageBitmap(imageBitmap)
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
        if (this.getContentResolver() != null) {
            val cursor: Cursor? =
                uri?.let {
                    this.getContentResolver().query(it, null, null, null, null)
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
        if (DocumentsContract.isDocumentUri(this, uri)) {
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
        imageExt = uri?.let { getMimeType(this, it) }
        displayImage(imagePath)
        fileName = imagePath!!.substring(imagePath?.lastIndexOf("/")!! + 1)
        uploadImageWork()
    }


    private fun displayImage(imagePath: String?) {
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
//            sheetBinding.toolbarProf.setImageBitmap(bitmap)
        } else {
            Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("Range")
    private fun imagePath(uri: Uri?, selection: String?): String {
        var path: String? = null
        val cursor =
            uri?.let {
                this.contentResolver.query(it, null, selection, null, null)
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
        dialog.show()
        mBinding.fileName.text = fileName
        SharedHelper(this).id?.let {
            imageExt?.let { it1 ->
                SharedHelper(this).ethnicityTarget?.let { it2 ->
                    msgModel.getImageURl(
                        SharedHelper(this).jwtToken, fileName ?: "", it1,
                        SimpleDateFormat("yyyyMMddHHmmss").format(Date()) + "_" + fileName, it2
                    )
                }
            }
        }
    }

    private fun upload(it: JsonObject?) {

    }

    private fun upload(it: JsonArray?) {
        val file = File(imagePath)
        val requestBody: RequestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        val url = it?.get(0)?.toString()?.replace("\"", "")
        url.let { it1 ->
            profileModel.uploadImage(
                "image/${imageExt}",
                it1.toString(),
                requestBody
            )
        }
        profileModel.uploadImageObserver.observe(this) {
            Log.e("Response: ", " $it")
            dialog.dismiss()
            //profileWork()
        }


    }
}