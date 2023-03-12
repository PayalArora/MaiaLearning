package com.maialearning.ui.adapter

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.provider.MediaStore
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.view.MenuItem.OnMenuItemClickListener
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.databinding.PrimaryEmailSheetBinding
import com.maialearning.databinding.RicheditorBinding
import com.maialearning.databinding.UpcomingItemRowBinding
import com.maialearning.model.DashboardOverdueResponse
import com.maialearning.ui.activity.MessageDetailActivity
import com.maialearning.ui.activity.SurveyDetail
import com.maialearning.ui.bottomsheets.UpcomingItemDetails
import com.maialearning.util.checkNonNull

import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.replaceNextLine
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.DashboardFragViewModel
import jp.wasabeef.richeditor.RichEditor
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

const val ID:String = "ID"
const val TITLE:String = "TITLE"
const val DESCRIPTION:String = "DESCRIPTION"
const val DESC:String = "DESC"
const val STATUS:String = "STATUS"

class OverDueAdapter(
    var overdueList: ArrayList<DashboardOverdueResponse.AssignmentItem>,
    val con: FragmentActivity,
    val fragment: Fragment
) :
    RecyclerView.Adapter<OverDueAdapter.ViewHolder>(), RichEditor.OnTextChangeListener {
    var isSelected = false
    lateinit var inflater: LayoutInflater
    private lateinit var progress: Dialog
    private val click: OnClick = fragment as OnClick
    var clickedPos = 0


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: UpcomingItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        inflater = LayoutInflater.from(viewGroup.context)
        val binding = UpcomingItemRowBinding.inflate(inflater, viewGroup, false)
        progress = showLoadingDialog(con)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        if (position == 0) {
//            setVisibility(viewHolder.binding, carrier_lay = 1)
//        } else if (position == 1) {
//            setVisibility(viewHolder.binding, academic_lay = 1)
//        } else if (position == 2) {
//            setVisibility(viewHolder.binding, submitted_lay = 1)
//        } else if (position == 3) {
//            setVisibility(viewHolder.binding, fair_lay = 1)
//        } else if (position == 4) {
//            setVisibility(viewHolder.binding, fair_join_lay = 1)
//        } else if (position == 5) {
//            setVisibility(viewHolder.binding, approved_lay = 1)
//        } else if (position == 6) {
//            setVisibility(viewHolder.binding, deadline_lay = 1)
//        } else if (position == 7) {
//            setVisibility(viewHolder.binding, survey_lay = 1)
//        }


        if (overdueList?.get(position)?.category == "Career") {
            setVisibility(viewHolder.binding, carrier_lay = 1)
            viewHolder.binding.textType.setTextColor(Color.parseColor("#36779C"))
            overdueList.get(position).body?.let {
//                viewHolder.binding.textDescription.loadDataWithBaseURL(
//                    "",
//                    it,
//                    "text/html",
//                    "utf-8",
//                    ""
//                )
                //  viewHolder.binding.textDescription.setText(Html.fromHtml(it))
                val spanned = Html.fromHtml(it)
                val chars = CharArray(spanned.length)
                TextUtils.getChars(spanned, 0, spanned.length, chars, 0)
                val plainText = String(chars)
                viewHolder.binding.textDescription.setText(plainText.trim().replaceNextLine())
            }
        } else if (overdueList?.get(position)?.category == "Academic") {
            setVisibility(viewHolder.binding, academic_lay = 1)
//            viewHolder.binding.actDescription.text =
//                Html.fromHtml(overdueList.get(position).task.toString())
            val spanned = Html.fromHtml(overdueList.get(position).task.toString())
            var chars = CharArray(spanned.length)
            TextUtils.getChars(spanned, 0, spanned.length, chars, 0)
            val plainText = String(chars)
            viewHolder.binding.actDescription.setText(plainText.trim().replaceNextLine())
        } else if (overdueList?.get(position)?.category == "College") {
            setVisibility(viewHolder.binding, carrier_lay = 1)
            //viewHolder.binding.textDescription.settings.javaScriptEnabled = true
            // viewHolder.binding.textDescription.settings.javaScriptCanOpenWindowsAutomatically = true
            overdueList.get(position).body?.let {
//                viewHolder.binding.textDescription.loadDataWithBaseURL(
//                    "",
//                    it,
//                    "text/html",
//                    "utf-8",
//                    ""
//                )
                //viewHolder.binding.textDescription.setText(Html.escapeHtml(it))
                val spanned = Html.fromHtml(it)
                val chars = CharArray(spanned.length)
                if (spanned.length < 200)
                    TextUtils.getChars(spanned, 0, spanned.length, chars, 0)
                else
                    TextUtils.getChars(spanned, 0, 198, chars, 0)
                val plainText = String(chars)
                viewHolder.binding.textDescription.setText(plainText.trim().replaceNextLine())

            }
            viewHolder.binding.textType.setText(overdueList.get(position).category.toString())
            viewHolder.binding.textType.setTextColor(Color.parseColor("#000000"))
        } else if (overdueList?.get(position)?.category == "Financial Aid") {
            setVisibility(viewHolder.binding, carrier_lay = 1)
            overdueList.get(position).body?.let {
//                viewHolder.binding.textDescription.loadDataWithBaseURL(
//                    "",
//                    it,
//                    "text/html",
//                    "utf-8",
//                    ""
//                )
                //  viewHolder.binding.textDescription.setText(Html.escapeHtml(it))
                val spanned = Html.fromHtml(it)
                val chars = CharArray(spanned.length)
                if (spanned.length < 200)
                    TextUtils.getChars(spanned, 0, spanned.length, chars, 0)
                else
                    TextUtils.getChars(spanned, 0, 198, chars, 0)
                val plainText = String(chars)
                viewHolder.binding.textDescription.setText(plainText.trim().replaceNextLine())

            }
            viewHolder.binding.textType.setText(overdueList.get(position).category.toString())
            viewHolder.binding.textType.setTextColor(Color.parseColor("#000000"))
        } else if (overdueList.get(position).category == "Survey") {
            setVisibility(viewHolder.binding, survey_lay = 1)
            viewHolder.binding.textType.text = overdueList?.get(position)?.category
            viewHolder.binding.descrptionSurvey.text =overdueList?.get(position)?.body
            if (overdueList?.get(position)?.response_status == "pending"){
                viewHolder.binding.completeText.text = "Start"
            } else if (overdueList?.get(position)?.response_status == "in_progress") {
                viewHolder.binding.completeText.text = "Continue"
            }else if (overdueList?.get(position)?.response_status == "completed"|| overdueList?.get(position)?.response_status == "incomplete") {
                viewHolder.binding.completeText.text = "View"
            } else{
                overdueList?.get(position)?.response_status= "pending"
                viewHolder.binding.completeText.text = "Start"
            }

        } else {
            setVisibility(viewHolder.binding, carrier_lay = 1)
            viewHolder.binding.textType.text = overdueList?.get(position)?.category
            viewHolder.binding.textType.setTextColor(Color.parseColor("#36779C"))
            overdueList.get(position).body?.let {
//                viewHolder.binding.textDescription.loadDataWithBaseURL(
//                    "",
//                    it,
//                    "text/html",
//                    "utf-8",
//                    ""
//                )
                // viewHolder.binding.textDescription.setText(Html.escapeHtml(it))
                val spanned = Html.fromHtml(it)
                val chars = CharArray(spanned.length)
                if (spanned.length < 200)
                    TextUtils.getChars(spanned, 0, spanned.length, chars, 0)
                else
                    TextUtils.getChars(spanned, 0, 198, chars, 0)
                TextUtils.getChars(spanned, 0, spanned.length, chars, 0)
                val plainText = String(chars)
                viewHolder.binding.textDescription.setText(plainText.trim().replaceNextLine())
            }
        }

        if (overdueList.get(position).worksheetFileId != null && overdueList.get(position).worksheetFileId!!.size > 0) {
            viewHolder.binding.docsLay.visibility = View.VISIBLE
            viewHolder.binding.textSubmittedDoc.visibility = View.VISIBLE
            viewHolder.binding.docsAttached.text =
                "${overdueList.get(position)?.worksheetFileId?.size}  docs attached"
        } else {
            viewHolder.binding.docsLay.visibility = View.GONE
            viewHolder.binding.textSubmittedDoc.visibility = View.GONE
        }
        viewHolder.itemView.setOnClickListener {
            if (!overdueList.get(position).category.equals("Survey") && !overdueList.get(position).category.equals("Applications")  &&
                 !overdueList.get(position).category.equals("Webinar") ) {
                clickedPos = position
                UpcomingItemDetails(
                    fragment,
                    inflater,
                    overdueList.get(position),
                    ::clickDetail, ::clickCounscellor
                ).showDialog(progress)
            } else if(overdueList.get(position).category.equals("Survey")){
                if (overdueList?.get(position)?.response_status == "pending"){
                    overdueList?.get(position)?.categoryId?.let {
                         var desc=""
                        if (checkNonNull(overdueList?.get(position)?.authorF))
                            desc = "Author:${overdueList?.get(position)?.authorF}"
                        if (checkNonNull(overdueList?.get(position)?.authorL))
                            desc = "$desc${overdueList?.get(position)?.authorF}"
                        if (checkNonNull(overdueList?.get(position)?.questionSize))
                            desc = "$desc \n\nThere are ${overdueList?.get(position)?.questionSize} questions in this survey"
                        var intent = Intent(fragment.activity, SurveyDetail::class.java).putExtra(ID, it).putExtra(TITLE,  overdueList?.get(position)?.body.toString()).putExtra(
                            DESCRIPTION, desc).putExtra(STATUS, overdueList?.get(position)?.response_status).putExtra(
                            DESC, overdueList?.get(position)?.description.toString())
                        //fragment.activity?.startActivity(intent)
                       fragment.activity?.startActivityFromFragment(fragment,intent, 101)

                    }
                } else if (overdueList?.get(position)?.response_status == "in_progress") {
                    overdueList?.get(position)?.categoryId?.let {
                        var desc=""
                        if (checkNonNull(overdueList?.get(position)?.authorF))
                            desc = "Author:${overdueList?.get(position)?.authorF}"
                        if (checkNonNull(overdueList?.get(position)?.authorL))
                            desc = "$desc${overdueList?.get(position)?.authorF}"
                        if (checkNonNull(overdueList?.get(position)?.questionSize))
                            desc = "$desc \n\nThere are ${overdueList?.get(position)?.questionSize} questions in this survey"
                        var intent = Intent(fragment.activity, SurveyDetail::class.java).putExtra(ID, it).putExtra(TITLE,  overdueList?.get(position)?.body.toString()).putExtra(
                            DESCRIPTION, desc).putExtra(STATUS, overdueList?.get(position)?.response_status).putExtra(
                            DESC, overdueList?.get(position)?.description.toString())
                        fragment.activity?.startActivityFromFragment(fragment,intent, 101)

                    }
                }else if (overdueList?.get(position)?.response_status == "completed"|| overdueList?.get(position)?.response_status == "incomplete") {
                    //loadFragment(SurveyDetail(),fragment.activity)
                    overdueList?.get(position)?.categoryId?.let {
                        var desc=""
                        if (checkNonNull(overdueList?.get(position)?.authorF))
                            desc = "Author:${overdueList?.get(position)?.authorF}"
                        if (checkNonNull(overdueList?.get(position)?.authorL))
                            desc = "$desc${overdueList?.get(position)?.authorF}"
                        if (checkNonNull(overdueList?.get(position)?.questionSize))
                            desc = "$desc \n\nThere are ${overdueList?.get(position)?.questionSize} questions in this survey"
                         val intent = Intent(fragment.activity, SurveyDetail::class.java).putExtra(ID, it).putExtra(TITLE,  overdueList?.get(position)?.body.toString()).putExtra(
                            DESCRIPTION,  desc).putExtra(STATUS, overdueList?.get(position)?.response_status).putExtra(
                             DESC, overdueList?.get(position)?.description.toString())
                        fragment.activity?.startActivityFromFragment(fragment,intent, 101)

                    }

                }

            }
        }

        if (!overdueList.get(position).category.equals("Survey") && !overdueList.get(position).category.equals("Applications")  &&
            !overdueList.get(position).category.equals("Webinar") ){

            viewHolder.binding.menuClick.visibility = View.VISIBLE
        }
        else {
            viewHolder.binding.menuClick.visibility = View.GONE
        }
        viewHolder.binding.menuClick.setOnClickListener {
            if (!overdueList.get(position).category.equals("Survey") && !overdueList.get(position).category.equals("Applications")  &&
                !overdueList.get(position).category.equals("Webinar") ) {
                menuPopUp(position, it)
            }
        }
        viewHolder.binding.academicMenuClick.setOnClickListener {
            if (!overdueList.get(position).category.equals("Survey"))
                menuPopUp(position, it)
        }
    }

    private val dashboardViewModel: DashboardFragViewModel by fragment.viewModel()

    private fun menuPopUp(position: Int, it: View?) {

        val popupMenu = PopupMenu(con, it)
        popupMenu.inflate(R.menu.dashboard_popup)
        popupMenu.show()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true)
        };

        if (overdueList.get(position).completed == 1) {
            popupMenu.getMenu().findItem(R.id.navigation_complete).setVisible(false)
            popupMenu.getMenu().findItem(R.id.navigation_reset_complete).setVisible(true)
        }

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.navigation_file_upload -> {
                    checkStoragePermissionAndOpenImageSelection()
                }
                R.id.navigation_view -> {
                    if (!overdueList.get(position).category.equals("Survey"))
                        UpcomingItemDetails(
                            fragment,
                            inflater,
                            overdueList.get(position),
                            ::clickDetail,::clickCounscellor
                        ).showDialog(progress)
                }
                R.id.navigation_write -> {
                    gapText = ""
                    showSheet(position)
                }
                R.id.navigation_complete -> {
                    completeWork(overdueList.get(position).nid)
                }
                R.id.navigation_reset_complete -> {
                    resetCompleteWork(overdueList.get(position).nid)
                }
            }

            true

        })

    }

    private fun resetCompleteWork(nid: String?) {
        nid?.let {
            progress.show()
            dashboardViewModel.resetCompleteTask(
                it,
            )
        }
        dashboardViewModel.resetCompleteFileObserver.observe(fragment.viewLifecycleOwner) {
            progress.dismiss()
//            overdueList.removeAt(position)
//            notifyDataSetChanged()
//            SharedHelper(con).id?.let {
//                dashboardViewModel.getOverDueCompleted(
//                    "Bearer " + SharedHelper(con).authkey,
//                    it
//                )
//            }
            click.click("reset")
        }
    }

    private fun completeWork(nid: String?) {
        nid?.let {
            SharedHelper(con).id?.let { it1 ->
                progress.show()
                dashboardViewModel.completetTask(
                    it,
                    it1
                )
            }
        }
        dashboardViewModel.completeFileObserver.observe(fragment.viewLifecycleOwner) {
            progress.dismiss()
            click.click("complete")
            //   overdueList.removeAt(position)
            // notifyDataSetChanged()
//            SharedHelper(con).id?.let {
//                dashboardViewModel.getOverDueCompleted(
//                    "Bearer " + SharedHelper(con).authkey,
//                    it
//                )
//            }
        }
    }


    override fun getItemCount(): Int {
        return overdueList.size
    }

    fun setVisibility(
        binding: UpcomingItemRowBinding,
        carrier_lay: Int = View.GONE,
        academic_lay: Int = View.GONE,
        submitted_lay: Int = View.GONE,
        fair_lay: Int = View.GONE,
        fair_join_lay: Int = View.GONE,
        approved_lay: Int = View.GONE,
        deadline_lay: Int = View.GONE,
        survey_lay: Int = View.GONE
    ) {
        binding.apply {
            carrierLay.visibility = carrier_lay
            academicLay.visibility = academic_lay
            submittedLay.visibility = submitted_lay
            fairLay.visibility = fair_lay
            fairJoinLay.visibility = fair_join_lay
            approvedLay.visibility = approved_lay
            deadlineLay.visibility = deadline_lay
            surveyLay.visibility = survey_lay
        }
    }

    // Write work
    lateinit var onTextChangeListener: RichEditor.OnTextChangeListener
    var gapText = ""

    private fun showSheet(position: Int) {
        onTextChangeListener = this

        var dialog = BottomSheetDialog(con)
        val dilBinding: PrimaryEmailSheetBinding =
            PrimaryEmailSheetBinding.inflate(con.layoutInflater)
//        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog?.setContentView(dilBinding.root)
        dialog?.show()
        dilBinding.backTxt.setOnClickListener {
            dialog?.dismiss()
        }
        dilBinding.filters.setText(con.resources.getString(R.string.write_to_conselor))
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
            setPlaceholder(con.resources.getString(R.string.write_to_conselor));
            setBackground(resources.getDrawable(R.drawable.white_rect_border));
            html = gapText
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
                    con,
                    con.getString(R.string.err_gapyear),
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
                overdueList?.get(position)?.nid?.let { it1 ->
                    progress.show()
                    dashboardViewModel.writeToCounselor(
                        "Bearer " + SharedHelper(con).authkey,
                        it1, encoded
                    )
                }
            }
        }
        if (!dashboardViewModel.writeToCounselerObserver.hasObservers()) {
            dashboardViewModel.writeToCounselerObserver.observe(fragment) {
                progress.dismiss()
                Toast.makeText(con, "Response Updated", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
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
        val builder: AlertDialog.Builder = AlertDialog.Builder(con)
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
                con,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            selectImage()
        } else {
            //changed here
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(
                    con,
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
        con.startActivityForResult(
            Intent.createChooser(intent, "Select Doc"),
            REQUEST_CHOOSE_PDF_UPCOMING_DETAIL
        )
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
                ActivityCompat.requestPermissions(
                    con,
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
    fun clickCounscellor(s: String) {
        gapText = s
        showSheet(clickedPos)
    }
    private fun clickDetail(
        type: String,
        dialog: BottomSheetDialog,
        progress: Dialog,
        nid: String?
    ) {
        progress.dismiss()
        dialog.dismiss()
        if (type == "completeBtn") {
            completeWork(nid)
        } else {
            resetCompleteWork(nid)
        }
    }
}
private fun loadFragment(fragment: Fragment, con: FragmentActivity?) {
    val transaction = con?.supportFragmentManager?.beginTransaction()
    transaction?.replace(R.id.host_nav, fragment)
    transaction?.commit()
}

interface OnClick {
    fun click(type: String)
}
