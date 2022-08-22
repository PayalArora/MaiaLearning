package com.maialearning.ui.bottomsheets

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.DownloadManager
import android.content.*
import android.content.Context.DOWNLOAD_SERVICE
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.databinding.PrimaryEmailSheetBinding
import com.maialearning.databinding.RicheditorBinding
import com.maialearning.databinding.UpcomingItemDetailBinding
import com.maialearning.model.AssignmentItem
import com.maialearning.ui.adapter.*
import com.maialearning.util.getDate
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.DashboardFragViewModel
import jp.wasabeef.richeditor.RichEditor
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


/*{student_uid: "9375", file_id: "zYd3VPnBDomb", document_type: "itask"}*/

class UpcomingItemDetails(
    val con: Fragment,
    val layoutInflater: LayoutInflater,
    val data: AssignmentItem,    var clickType: (type: String, dialog: BottomSheetDialog, progress: Dialog, nid:String?) -> Unit
) : RichEditor.OnTextChangeListener {
    private val chooseClick: ChooseClick = con as ChooseClick
    private val dashboardViewModel: DashboardFragViewModel by con.viewModel()
    private lateinit var progress: Dialog

    fun showDialog() {
        progress = showLoadingDialog(con.requireContext())
        val dialog = BottomSheetDialog(con.requireContext())
        val sheetBinding: UpcomingItemDetailBinding =
            UpcomingItemDetailBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        observer(dialog)

        if (data.category?.contains("Academic") == true) {
            sheetBinding.textCarr.text = data.category
            sheetBinding.textCarr.setTextColor(con.resources.getColor(R.color.purple))
            val img: Drawable = con.getResources().getDrawable(R.drawable.ic_cap)
            sheetBinding.textCarr.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null)
        }
        sheetBinding.subDescription.setBackgroundColor(con.resources.getColor(android.R.color.transparent))
        sheetBinding.textCarr.text = data.category
        data.body?.let {
            sheetBinding.subDescription.loadDataWithBaseURL(
                "",
                it,
                "text/html",
                "utf-8",
                ""
            )
        }
        if (data.date == null) {
            sheetBinding.timeJoinText.text = "No Due Date"
        } else
            sheetBinding.timeJoinText.text = data.date?.let { getDate(it.toLong(), "MMM dd, yyyy") }
        if (data.completed == 1) {
            sheetBinding.tic.visibility = View.VISIBLE
            sheetBinding.completedText.visibility = View.VISIBLE
        }
        if (data.worksheetFileId != null && data?.worksheetFileId?.size ?: 0 > 0) {
            sheetBinding.attachFiles.visibility = View.VISIBLE
            sheetBinding.attachList.visibility = View.VISIBLE
            sheetBinding.textSubmitted.visibility = View.VISIBLE
            setAdapter(sheetBinding, dialog)
        } else {
            sheetBinding.chooseFile.visibility = View.VISIBLE
        }
        sheetBinding.close.setOnClickListener { dialog.dismiss() }

        if (!data.filename.isNullOrEmpty()) {
            sheetBinding.fileLay.visibility = View.VISIBLE
            sheetBinding.docName.text = data.filename
        }
        sheetBinding.worksheet.setOnClickListener {
            if (data.worksheetFileId != null && data?.worksheetFileId?.size ?: 0 > 0) {
                downloadWorkSheet(data?.worksheetFileId?.get(0)?.id)
            }
        }

        if (data.completed != 1) {
            sheetBinding.completeBtn.visibility = View.VISIBLE
            if (data?.writtenResponse!=null)
                sheetBinding.write.visibility = View.GONE
            sheetBinding.writeLay.visibility = View.VISIBLE
            sheetBinding.resetComp.visibility = View.GONE
        } else {
            if (data?.writtenResponse!=null)
                sheetBinding.write.visibility = View.GONE
            sheetBinding.writeLay.visibility = View.VISIBLE
            sheetBinding.resetComp.visibility = View.VISIBLE
        }


        sheetBinding.chooseFile.setOnClickListener {
            chooseClick.onChooseClick(data.nid,dialog);
            checkStoragePermissionAndOpenImageSelection()
        }

        sheetBinding.write.setOnClickListener {
            showSheet("write")
        }

        sheetBinding.completeBtn.setOnClickListener {
           clickType("completeBtn", dialog, progress, data.nid)
        }
        sheetBinding.resetComp.setOnClickListener {
            clickType("resetCompleteWork", dialog, progress, data.nid)

        }
    }

    private fun observer(dialog: BottomSheetDialog) {
        dashboardViewModel.preAssignedDownloadObserver.observe(con) {
            val url = it.get(0).toString().replace("\"", "")
            val manager =
                con.requireContext().getSystemService(DOWNLOAD_SERVICE) as DownloadManager?
            val uri: Uri =
                Uri.parse(url)
            val request: DownloadManager.Request = DownloadManager.Request(uri)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            val reference: Long = manager?.enqueue(request)!!

        }
        dashboardViewModel.showError.observe(con) {
            progress.dismiss()
            Toast.makeText(con.requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadWorkSheet(fileId: String?) {
        fileId?.let {
            SharedHelper(con.requireContext()).id?.let { it1 ->
                dashboardViewModel.downloadWorkSheet(
                    it,
                    it1, "itask"
                )
            }
        }
    }

    private fun setAdapter(sheetBinding: UpcomingItemDetailBinding, dialog: BottomSheetDialog) {
        val linearLayoutManager =
            LinearLayoutManager(con.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        sheetBinding.attachList.layoutManager = linearLayoutManager
        sheetBinding.attachList.adapter =
            AttachmentAdapter(con.requireContext(), data.worksheetFileId)
        sheetBinding.worksheet.visibility = View.VISIBLE
    }

    // Write work
    lateinit var onTextChangeListener: RichEditor.OnTextChangeListener
    var gapText = ""

    private fun showSheet(s: String) {
        onTextChangeListener = this

        var dialog = BottomSheetDialog(con.requireContext())
        val dilBinding: PrimaryEmailSheetBinding =
            PrimaryEmailSheetBinding.inflate(layoutInflater)
//        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog?.setContentView(dilBinding.root)
        dialog?.show()
        dilBinding.backTxt.setOnClickListener {
            dialog?.dismiss()
        }
        dilBinding.filters.setText(con.requireContext().resources.getString(R.string.write_to_conselor))
        dilBinding.gapYearTxt.setOnClickListener {
            if (dilBinding.gapYearTxt.isChecked) {
                dilBinding.gapEdt.visibility = View.VISIBLE
                dilBinding.richeditor.richEditorLay.visibility = View.VISIBLE
            } else {
                dilBinding.gapEdt.visibility = View.GONE
                dilBinding.richeditor.richEditorLay.visibility = View.GONE
            }
        }

        val mEditor = dilBinding.gapEdt
        dilBinding.gapEdt.apply {
            setEditorHeight(200);
            setEditorFontSize(14)
            setPadding(10, 10, 10, 10);
            setPlaceholder(con.requireContext().resources.getString(R.string.write_to_conselor));
            setBackground(resources.getDrawable(R.drawable.white_rect_border));
        }

        setEditor(mEditor, dilBinding.richeditor)

        dilBinding.emailEdt.visibility = View.GONE
        dilBinding.layoutRich.visibility = View.VISIBLE
        dilBinding.gapYearTxt.visibility = View.GONE
        dilBinding.gapEdt.setOnTextChangeListener(onTextChangeListener)
        dilBinding.backTxt.setOnClickListener {
            dialog.dismiss()
        }
        dilBinding.saveBtn.setOnClickListener {
            if (gapText.isBlank()
            ) {
                Toast.makeText(
                    con.requireContext(),
                    con.requireContext().getString(R.string.err_gapyear),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            } else {
                val encoder: Base64.Encoder =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Base64.getEncoder()
                    } else {
                        TODO("VERSION.SDK_INT<0")
                    }
                val encoded: String = encoder.encodeToString(
                    gapText.toByteArray()
                )
                data?.nid?.let { it1 ->
                    progress.show()
                    dashboardViewModel.writeToCounselor(
                        "Bearer " + SharedHelper(con.requireContext()).authkey,
                        it1, encoded
                    )
                }
            }
        }
        dashboardViewModel.writeToCounselerObserver.observe(con) {
            progress.dismiss()
            Toast.makeText(con.requireContext(), "Response Updated", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

//        clickListener(s, sheetBinding, dialog!!)
    }

    override fun onTextChange(text: String?) {
        if (text != null) {
            gapText = text
        }
    }

    private fun setEditor(mEditor: RichEditor, richeditorBinding: RicheditorBinding) {
        richeditorBinding.actionUndo.setOnClickListener(View.OnClickListener { mEditor.undo() })

        richeditorBinding.actionRedo.setOnClickListener(View.OnClickListener { mEditor.redo() })

        richeditorBinding.actionBold.setOnClickListener(View.OnClickListener { mEditor.setBold() })

        richeditorBinding.actionItalic.setOnClickListener(View.OnClickListener { mEditor.setItalic() })

        richeditorBinding.actionSubscript.setOnClickListener(View.OnClickListener { mEditor.setSubscript() })

        richeditorBinding.actionSuperscript.setOnClickListener(View.OnClickListener { mEditor.setSuperscript() })

        richeditorBinding.actionStrikethrough.setOnClickListener(View.OnClickListener { mEditor.setStrikeThrough() })

        richeditorBinding.actionUnderline.setOnClickListener(View.OnClickListener { mEditor.setUnderline() })

        richeditorBinding.actionHeading1.setOnClickListener(View.OnClickListener {
            mEditor.setHeading(1)
        })

        richeditorBinding.actionHeading2.setOnClickListener(View.OnClickListener {
            mEditor.setHeading(2)
        })

        richeditorBinding.actionHeading3.setOnClickListener(View.OnClickListener {
            mEditor.setHeading(3)
        })

        richeditorBinding.actionHeading4.setOnClickListener(View.OnClickListener {
            mEditor.setHeading(4)
        })

        richeditorBinding.actionHeading5.setOnClickListener(View.OnClickListener {
            mEditor.setHeading(5)
        })

        richeditorBinding.actionHeading6.setOnClickListener(View.OnClickListener {
            mEditor.setHeading(6)
        })

        richeditorBinding.actionTxtColor.setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                mEditor.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                isChanged = !isChanged
            }
        })

        richeditorBinding.actionBgColor.setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                mEditor.setTextBackgroundColor(if (isChanged) Color.TRANSPARENT else Color.YELLOW)
                isChanged = !isChanged
            }
        })

        richeditorBinding.actionIndent.setOnClickListener(View.OnClickListener { mEditor.setIndent() })

        richeditorBinding.actionOutdent.setOnClickListener(View.OnClickListener { mEditor.setOutdent() })

        richeditorBinding.actionAlignLeft.setOnClickListener(View.OnClickListener { mEditor.setAlignLeft() })

        richeditorBinding.actionAlignCenter.setOnClickListener(View.OnClickListener { mEditor.setAlignCenter() })

        richeditorBinding.actionAlignRight.setOnClickListener(View.OnClickListener { mEditor.setAlignRight() })

        richeditorBinding.actionBlockquote.setOnClickListener(View.OnClickListener { mEditor.setBlockquote() })

        richeditorBinding.actionInsertBullets.setOnClickListener(View.OnClickListener { mEditor.setBullets() })

        richeditorBinding.actionInsertNumbers.setOnClickListener(View.OnClickListener { mEditor.setNumbers() })

        richeditorBinding.actionInsertCheckbox.setOnClickListener(View.OnClickListener { mEditor.insertTodo() })
    }


    // Choose File Work

    private val REQUEST_IMAGE_UPCOMING_DETAIL = 11
    private val REQUEST_CHOOSE_PHOTO_UPCOMING_DETAIL = 22
    private val REQUEST_FILE_ACCESS = 3
    private val REQUEST_CHOOSE_PDF_UPCOMING_DETAIL = 23


    private fun selectImage() {
        val options =
            arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Choose PDF", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(con.requireContext())
        builder.setTitle("Select Attachment")
        builder.setItems(options, DialogInterface.OnClickListener { dialogInterface, item ->
            if (options[item] == "Take Photo") {
                checkCameraPermissionAndOpen()
            } else if (options[item] == "Choose from Gallery") {
                openAlbum("image/*")
            } else if (options[item] == "Choose Document") {
                selectDoc()
            } else if (options[item] == "Cancel") {
                dialogInterface.dismiss()
            }
        })
        builder.show()
    }

    private fun checkStoragePermissionAndOpenImageSelection() {
        if (ContextCompat.checkSelfPermission(
                con.requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            selectImage()
        } else {
            //changed here
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    con.requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_FILE_ACCESS
                )
            }
        }
    }

    private fun selectDoc() {
        val mimeTypes = arrayOf(
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",  // .doc & .docx
            "application/vnd.ms-powerpoint",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation",  // .ppt & .pptx
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",  // .xls & .xlsx
            "text/plain",
            "text/comma-separated-values",
            "application/pdf"
        )
        val intent = Intent()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
            if (mimeTypes.size > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            }
        } else {
            var mimeTypesStr = ""
            for (mimeType in mimeTypes) {
                mimeTypesStr += "$mimeType|"
            }
            intent.type = mimeTypesStr.substring(0, mimeTypesStr.length - 1)
        }
        intent.action = Intent.ACTION_GET_CONTENT
        con.startActivityForResult(Intent.createChooser(intent, "Select Doc"), REQUEST_CHOOSE_PDF_UPCOMING_DETAIL)
    }

    //+10 changed its signature as Fragment; without it  onRequestPermissionsResult won't bbe called
    private fun checkCameraPermissionAndOpen() {
        if (ContextCompat.checkSelfPermission(
                con.requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            //changed here
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    con.requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_IMAGE_UPCOMING_DETAIL
                )
            }
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            con.startActivityForResult(takePictureIntent, REQUEST_IMAGE_UPCOMING_DETAIL)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
            Log.e("Exception", e.localizedMessage)
        }
    }

    private fun openAlbum(type: String) {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = type
        con.startActivityForResult(intent, REQUEST_CHOOSE_PHOTO_UPCOMING_DETAIL)
    }

}

interface ChooseClick {
    fun onChooseClick(nid: String?,dialog: BottomSheetDialog)
}