package com.maialearning.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.*
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonArray
import com.maialearning.R
import com.maialearning.databinding.LayoutTeacherBinding
import com.maialearning.databinding.RecommendationLayoutBinding
import com.maialearning.model.RecModel
import com.maialearning.model.RecomdersModel
import com.maialearning.model.TeacherListModelItem
import com.maialearning.model.UniversitiesModel
import com.maialearning.ui.adapter.RecommenderAdapter
import com.maialearning.ui.adapter.SelectTeacherAdapter
import com.maialearning.ui.adapter.SelectUniversityAdapter
import com.maialearning.util.*
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.viewmodel.HomeViewModel
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class RecommendationFragment : Fragment(), onClick {
    private lateinit var mBinding: RecommendationLayoutBinding
    private val homeModel: HomeViewModel by viewModel()
    private lateinit var progress: Dialog
    var list: ArrayList<TeacherListModelItem> = ArrayList()
    var univlist: ArrayList<UniversitiesModel> = ArrayList()
    var selectedList: ArrayList<TeacherListModelItem> = ArrayList()
    var selectedUnivList: ArrayList<UniversitiesModel> = ArrayList()
    var selectedUcasList: ArrayList<TeacherListModelItem> = ArrayList()
    var jsonDeadline: JsonArray = JsonArray()
    lateinit var adapter: RecommenderAdapter
    var type_recs = REC_TYPE_RECOMENDATION
    var page: Int = 1
    var requestListUpdate: ArrayList<RecomdersModel.Data?>? = ArrayList()
    var requestList: ArrayList<RecomdersModel.Data?>? = ArrayList()
    private var isLoading = false
    var requestlistNew = ArrayList<RecomdersModel.Data?>()
    var isAttached = false
    var url: String? = null
    var json: JSONObject? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = RecommendationLayoutBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isAttached = true
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isAttached)
            setListeners()
        progress = showLoadingDialog(requireContext())
        progress.show()
        SharedHelper(requireContext()).id?.let { homeModel.getTeachers(it) }
        showRecs()
        homeModel.getRecDeadline()
        mBinding.send.setOnClickListener {
            if (type_recs == REC_TYPE_RECOMENDATION) {
                sendRecomendation(type_recs)
            } else {
                sendRecomendation(type_recs)
                sendUCASRecomendation()
            }
        }
        mBinding.recipent.setOnClickListener {
            selectedList.clear()
            listTeacher(REC_TYPE_RECOMENDATION)
        }
        mBinding.ucasLetter.setOnClickListener {
            showRecs()
        }
        mBinding.recipentUcas.setOnClickListener {
            selectedUcasList.clear()
            listTeacher(REC_TYPE_UCAS)
        }
        mBinding.recipentUniversity.setOnClickListener {
            progress.show()
            listUniversities()
        }
        mBinding.textAddFile.setOnClickListener {
            checkStoragePermissionAndOpenImageSelection()
        }
        progress.show()

        adapter = RecommenderAdapter(requireContext(), requestlistNew, this, mBinding.requestList)
        mBinding.requestList.adapter = adapter
        adapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                requestlistNew.add(null)
                isLoading = true
                adapter.notifyItemInserted(requestlistNew.size - 1)
                Handler(Looper.getMainLooper()).postDelayed({
                    hitAPI(page.toString())

                }, 2000)
            }
        })

        checkRecomType()
    }

    private fun listUniversities() {
        homeModel.getUniversities(SharedHelper(requireContext()).id ?: "")
    }

    private fun checkRecomType() {
        homeModel.getRecomType(SharedHelper(requireContext()).schoolId ?: "")
    }

    fun hitAPI(page: String) {
        homeModel.getRecommenders(SharedHelper(requireContext()).id ?: "", page)
    }


    private fun sendUCASRecomendation() {
        val teacherId = arrayListOf<String>()
        for (i in selectedUcasList) {
            i.uid?.let { it1 -> teacherId.add(it1) }
        }
        if (mBinding.textDescription.text.toString().trim() != "" && teacherId.size
            > 0
        ) {
            progress.show()

            val recmodel = RecModel(
                mBinding.textDescription.text.toString(),
                SharedHelper(requireContext()).id ?: "",
                jsonDeadline.get(mBinding.typeValue.selectedItemPosition).asString,
                teacherId
            )
            homeModel.sendRecomUCAS(recmodel)
        }
    }

    private fun sendRecomendation(type: Int) {
        if (type == REC_TYPE_UCAS) {
            if (mBinding.textDescription.text.toString().trim() == "" || selectedUcasList.size
                == 0 || selectedList.size == 0
            ) {
                return
            }
        }
        val teacherId = arrayListOf<String>()
        for (i in selectedList) {
            i.uid?.let { it1 -> teacherId.add(it1) }
        }
        if (mBinding.textDescription.text.toString().trim() != "" && teacherId.size
            > 0
        ) {
            progress.show()

            val recmodel = RecModel(
                mBinding.textDescription.text.toString(),
                SharedHelper(requireContext()).id ?: "",
                jsonDeadline.get(mBinding.typeValue.selectedItemPosition).asString,
                teacherId
            )
            homeModel.sendRecomTeachers(recmodel)
        }

    }

    private fun showRecs() {
        if (mBinding.ucasLetter.isChecked) {
            type_recs = REC_TYPE_UCAS
            mBinding.recipentUcas.visibility = View.VISIBLE
        } else {
            type_recs = REC_TYPE_RECOMENDATION
            mBinding.recipentUcas.visibility = View.GONE
        }
    }

    private fun setListeners() {
        homeModel.typeObserver.observe(requireActivity()) {
            progress.dismiss()
            if ((it.get(0).toString()) == "0") {
                Toast.makeText(context, "0", Toast.LENGTH_LONG).show()
                mBinding.recipentUcas.visibility = View.GONE
                mBinding.recipentUniversity.visibility = View.VISIBLE
                mBinding.recomendationSelection.visibility = View.GONE
            } else {
                mBinding.recipentUcas.visibility = View.VISIBLE
                mBinding.recipentUniversity.visibility = View.GONE
                mBinding.recomendationSelection.visibility = View.VISIBLE

                hitAPI(page.toString())
            }
        }
        homeModel.applyingObserver.observe(requireActivity()) {
            progress.dismiss()
            for (i in 0 until it.size()) {
                val data = JSONObject(it.get(i).toString())
                val model = TeacherListModelItem(
                    data.getString("uid"),
                    data.getString("users_name"),
                    data.getString("school_nid"),
                    data.getString("nid"),
                    data.getString("last_name"),
                    data.getString("school_name"),
                    data.getString("first_name"),
                )
                list.add(model)
                val id = SharedHelper(requireContext()).schoolId
                list = list.filter { it.schoolNid == id } as ArrayList<TeacherListModelItem>
                list.sortBy { it.firstName?.capitalize() + " " + it.lastName?.capitalize() }

            }

        }
        homeModel.recDeadline.observe(requireActivity()) {
            jsonDeadline = it

            val array = arrayOfNulls<String>(it.size())

            for (i in 0 until it.size()) {
                array[i] = getDate(it[i].asLong, "MMM dd,yyyy")
            }
            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.spinner_text, array
            )
            mBinding.typeValue.adapter = adapter

        }
        homeModel.recommdersObserver.observe(requireActivity()) {
            progress.dismiss()
            page = ((it.pager!!.current?.toInt() ?: 0) + 1)
            val totalPage = it.pager!!.last
            val last = it.pager!!.last
            progress.dismiss()
            requestList?.addAll(it.data)
            requestListUpdate?.addAll(it.data)
            if (isLoading) {
                isLoading = false
                requestlistNew.removeAt(requestlistNew.size - 1)
                adapter.notifyItemRemoved(requestlistNew.size)
            }
            //for swipe refresh page
            if (totalPage != null) {
                if (last != null) {
                    adapter.addAllLis(requestList!!, totalPage, last)
                }
            }
            adapter.setLoaded()

        }
        homeModel.recSendObserver.observe(requireActivity()) {
            progress.dismiss()
            Toast.makeText(requireContext(), it.get(0).asString, Toast.LENGTH_LONG).show()
        }
        homeModel.recUCASObserver.observe(requireActivity()) {
            progress.dismiss()
            Toast.makeText(requireContext(), it.get(0).asString, Toast.LENGTH_LONG).show()
        }
        homeModel.cancelRecommendRequestObserver.observe(requireActivity()) {
            progress.dismiss()
        }
        homeModel.getDocumentPresignedObserver.observe(requireActivity()) {
//            progress.dismiss()
            json = JSONObject(it.toString())
            if (json?.optInt("exist") == 0) {
                url = json?.optString("s3_url")
                url?.let { it1 -> homeModel.uploadDoc(it1) }
            } else if (json?.optInt("exist") == 1) {
                saveWork(1, json, "")
            }

        }
        homeModel.uploadImageObserver.observe(requireActivity()) {
            url?.let { it1 -> homeModel.checkFileVirus(ANTI_VIRUS, it1) }
        }
        homeModel.fileVirusObserver.observe(requireActivity()) {
            var obj = JSONObject(it.toString())
            if (obj?.get("file_status").toString() == "clean") {
                url?.let { it1 -> saveWork(0, json, it1) }
            } else {
                Toast.makeText(requireContext(), "Please check your File", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        homeModel.showError.observe(requireActivity()){
            progress.dismiss()
            context?.showToast(it)
        }
        homeModel.saveDocumentBragsheetObserver.observe(requireActivity()) {
            progress.dismiss()
            pdfDialog.dismiss()
        }

        homeModel.getUniversitiesObserver.observe(requireActivity()) {
            val json = JSONObject(it.toString())
            val x = json.keys() as Iterator<String>
            while (x.hasNext()) {
                val key: String = x.next().toString()
                var universityModel=UniversitiesModel()
                universityModel.id=json.get(key).toString()
                universityModel.name=json.optString(key)
                univlist.add(universityModel)
            }
            Log.e("University size ",">> "+ univlist.size)
            univlist.sortBy { it.name?.capitalize() }
            universititesBottomSheet()
            progress.dismiss()
        }
    }

    private fun universititesBottomSheet() {
        selectedUnivList.clear()
        var type=REC_TYPE_RECOMENDATION
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: LayoutTeacherBinding = LayoutTeacherBinding.inflate(layoutInflater)
        //  sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.filters.text = resources.getString(R.string.select_university)
        dialog.show()
        sheetBinding.clearText.setOnClickListener { dialog.dismiss() }
        sheetBinding.backBtn.setOnClickListener { dialog.dismiss() }
        sheetBinding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        sheetBinding.recyclerView.adapter =
            SelectUniversityAdapter(univlist, ::clickUniversity, type)
        sheetBinding.searchText.hint="Search University"
        sheetBinding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (sheetBinding.searchText.text.toString().trim() != "") {
                    val filterList =
                        findUnivByName(sheetBinding.searchText.text.toString().trim())
                    sheetBinding.recyclerView.adapter =
                        SelectUniversityAdapter(filterList, ::clickUniversity, type)
                } else {
                    sheetBinding.recyclerView.adapter =
                        SelectUniversityAdapter(univlist, ::clickUniversity, type)
                }

            }


        })
        sheetBinding.searchText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                findUnivByName(sheetBinding.searchText.text.toString().trim())
                return@OnEditorActionListener true
            }
            false
        })
        dialog.setOnDismissListener {
                setUniversitySelection(selectedUnivList, type)
        }

    }

    private fun listTeacher(type: Int) {

        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: LayoutTeacherBinding = LayoutTeacherBinding.inflate(layoutInflater)
        //  sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.filters.text = resources.getString(R.string.select_teachers)
        dialog.show()
        sheetBinding.clearText.setOnClickListener { dialog.dismiss() }
        sheetBinding.backBtn.setOnClickListener { dialog.dismiss() }
        sheetBinding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        sheetBinding.recyclerView.adapter =
            SelectTeacherAdapter(list, ::click, type)
        sheetBinding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (sheetBinding.searchText.text.toString().trim() != "") {
                    val filterList =
                        findMemberByName(sheetBinding.searchText.text.toString().trim())
                    sheetBinding.recyclerView.adapter =
                        SelectTeacherAdapter(filterList, ::click, type)
                } else {
                    sheetBinding.recyclerView.adapter =
                        SelectTeacherAdapter(list, ::click, type)
                }

            }


        })
        sheetBinding.searchText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                findMemberByName(sheetBinding.searchText.text.toString().trim())
                return@OnEditorActionListener true
            }
            false
        })
        dialog.setOnDismissListener {
            if (type == REC_TYPE_RECOMENDATION)
                setSelection(selectedList, type)
            else
                setSelection(selectedUcasList, type)
        }

    }

    fun findMemberByName(name: String?): ArrayList<TeacherListModelItem> {
        // go through list of members and compare name with given name
        var filterList: ArrayList<TeacherListModelItem> = ArrayList()
        for (member in list) {
            if ((member.firstName?.lowercase()?.contains(name?.lowercase() ?: "")
                    ?: false) || member.lastName?.lowercase()
                    ?.contains(name?.lowercase() ?: "") ?: false
            ) {
                filterList.add(member)
                // return member when name found
            }
        }
        return filterList
    }

    fun findUnivByName(name: String?): ArrayList<UniversitiesModel> {
        // go through list of members and compare name with given name
        var filterList: ArrayList<UniversitiesModel> = ArrayList()
        for (member in univlist) {
            if ((member.name?.lowercase()?.contains(name?.lowercase() ?: "")
                    ?: false) ?: false
            ) {
                filterList.add(member)
                // return member when name found
            }
        }
        return filterList
    }


    private fun click(data: TeacherListModelItem, type: Int) {
        if (type == REC_TYPE_RECOMENDATION)
            if (data.isSelected) {
                selectedList.add(data)
            } else {
                selectedList.remove(data)

            } else {
            if (data.isSelected) {
                selectedUcasList.add(data)
            } else {
                selectedUcasList.remove(data)
            }
        }
    }

    private fun clickUniversity(data: UniversitiesModel, type: Int) {
            if (data.isSelected) {
                selectedUnivList.add(data)
            } else {
                selectedUnivList.remove(data)
            }
        }



    private fun setSelection(selectedList: ArrayList<TeacherListModelItem>, type: Int) {
        val separator = ", "

        val sb = StringBuilder()
        selectedList.forEach { sb.append(it.lastName).append(separator) }
        val string = sb.removeSuffix(separator).toString()

        if (type == REC_TYPE_RECOMENDATION)
            mBinding.selectedTeachers.text = string
        else
            mBinding.selectedTeachersUcas.text = string

        //  mBinding.textCount.text = "from " + selectedList.size + " teachers"
    }

    private fun setUniversitySelection(selectedList: ArrayList<UniversitiesModel>, type: Int) {
        val separator = ", "
        val sb = StringBuilder()
        selectedList.forEach { sb.append(it.name).append(separator) }
        val string = sb.removeSuffix(separator).toString()
         mBinding.selectedUniversity.text = string
        //  mBinding.textCount.text = "from " + selectedList.size + " teachers"
    }
    companion object {
        const val REC_TYPE_RECOMENDATION = 1
        const val REC_TYPE_UCAS = 2
    }

    override fun onCancelClick(data: RecomdersModel.Data?) {
        progress.show()
        homeModel.cancelRecommendRequest(data?.nid.toString())
    }

    private var fileUri: Uri? = null
    var imagePath: String? = null


    private fun checkStoragePermissionAndOpenImageSelection() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showDialog()
        } else {
            //changed here
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_FILE_ACCESS
                )
            }
        }
    }

    lateinit var body: TextView
    lateinit var yesBtn: Button
    lateinit var pdfDialog: Dialog

    private fun showDialog() {
        pdfDialog = Dialog(requireContext())
        pdfDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pdfDialog.setCancelable(false)
        pdfDialog.setContentView(R.layout.file_upload_dialog)
        body = pdfDialog.findViewById(R.id.tvBody) as TextView
        yesBtn = pdfDialog.findViewById(R.id.btn_yes) as Button
        yesBtn.setText("Upload")
        val noBtn = pdfDialog.findViewById(R.id.btn_cancel) as TextView
        yesBtn.setOnClickListener {
            if (yesBtn.text.equals("Upload"))
                selectDoc()
            else
                uploadFileWork()
        }
        noBtn.setOnClickListener { pdfDialog.dismiss() }
        pdfDialog.show()

    }

    private fun uploadFileWork() {
        val imgFile = File(imagePath)
        if (imgFile.exists() && imgFile.length() > 0) {
            progress.show()

            var hash = getBase64FromPath(imagePath)?.let { it.getMd5Hash() }
            val tsLong = System.currentTimeMillis() / 1000
            var filename = "bragsheet_" + SharedHelper(requireContext()).id + "_" + tsLong + ".pdf"
            SharedHelper(requireContext()).id?.let {
                hash?.let { it1 ->
                    homeModel.getPresignedURL(
                        filename,
                        it, "recommendation_request", it1
                    )
                }
            }
        }
        var url: String? = null
        var json: JSONObject? = null

        homeModel.getDocumentPresignedObserver.observe(requireActivity()) {
//            progress.dismiss()
            json = JSONObject(it.toString())
            if (json?.optInt("exist") == 0) {
                url = json?.optString("s3_url")
                url?.let { it1 -> homeModel.uploadDoc(it1) }
            } else if (json?.optInt("exist") == 1) {
                saveWork(1, json, "")
            }

        }
        homeModel.uploadImageObserver.observe(requireActivity()) {
            url?.let { it1 -> homeModel.checkFileVirus(ANTI_VIRUS, it1) }
        }
        homeModel.fileVirusObserver.observe(requireActivity()) {
            var obj = JSONObject(it.toString())
            if (obj?.get("file_status").toString() == "clean") {
                url?.let { it1 -> saveWork(0, json, it1) }
            } else {
                Toast.makeText(requireContext(), "Please check your File", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        homeModel.showError.observe(requireActivity()){
            progress.dismiss()
        }
    }

    private fun saveWork(exist: Int, json: JSONObject?, url: String) {
        if (json != null) {
            SharedHelper(requireContext()).id?.let {
                homeModel.saveDocumentBragsheet(
                    it,
                    json.optString("filename"),
                    json.optString("path"),
                    exist,
                    url,
                    json.optString("hash")
                )
            }
        }
        homeModel.saveDocumentBragsheetObserver.observe(requireActivity()) {
            progress.dismiss()
            pdfDialog.dismiss()
        }
    }

    private fun selectDoc() {
        val mimeTypes = arrayOf(
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
        startActivityForResult(
            Intent.createChooser(intent, "Select Doc"),
            REQUEST_CHOOSE_PDF_UPCOMING_DETAIL
        )
    }

    private val REQUEST_FILE_ACCESS = 3
    private val REQUEST_CHOOSE_PDF_UPCOMING_DETAIL = 23
    var pdfName: String = ""


    fun getBase64FromPath(path: String?): String? {
        var byteArray: ByteArray? = null
        try {
            val file = File(path)
            val inputStream: InputStream = FileInputStream(file)
            val bos = ByteArrayOutputStream()
            val b = ByteArray(1024 * 11)
            var bytesRead = 0
            while (inputStream.read(b).also { bytesRead = it } != -1) {
                bos.write(b, 0, bytesRead)
            }
            byteArray = bos.toByteArray()
            Log.e("Byte array", ">$byteArray")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHOOSE_PDF_UPCOMING_DETAIL) {

            fileUri = data?.data!!
            val uri: Uri = data?.data!!
            val uriString: String = uri.toString()
            if (uriString.startsWith("content://")) {
                var myCursor: Cursor? = null
                try {
                    // Setting the PDF to the TextView
                    myCursor = requireContext()!!.contentResolver.query(uri, null, null, null, null)
                    if (myCursor != null && myCursor.moveToFirst()) {
                        pdfName =
                            myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                        body.setText("Selected File: " + pdfName)
                        yesBtn.setText("Save")
                    }
                } finally {
                    myCursor?.close()
                }
            }

            imagePath = PathUtil.getDriveFilePath(requireContext(), uri);
//            imagePath=getRealPathFromURI(fileUri)
        }
    }

    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    fun getMd5Hash(str: String): String? {
        val md: MessageDigest = MessageDigest.getInstance("MD5")
        val thedigest: ByteArray = md.digest(str.toByteArray(charset("UTF-8")))
        val hexString = java.lang.StringBuilder()
        for (i in thedigest.indices) {
            val hex = Integer.toHexString(0xFF and thedigest[i].toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }
        return hexString.toString().toUpperCase()
    }
}

interface onClick {
    fun onCancelClick(data: RecomdersModel.Data?)
}
