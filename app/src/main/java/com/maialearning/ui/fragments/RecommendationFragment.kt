package com.maialearning.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.DownloadManager
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
import com.google.android.material.tabs.TabLayout
import com.google.gson.JsonArray
import com.maialearning.R
import com.maialearning.databinding.LayoutTeacherBinding
import com.maialearning.databinding.RecommendationLayoutBinding
import com.maialearning.model.*
import com.maialearning.ui.adapter.RecommenderAdapter
import com.maialearning.ui.adapter.RecommenderCollegeAdapter
import com.maialearning.ui.adapter.SelectTeacherAdapter
import com.maialearning.ui.adapter.SelectUniversityAdapter
import com.maialearning.util.*
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.viewmodel.HomeViewModel
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.ext.isInt
import java.io.*


class RecommendationFragment : Fragment(), onClick {
    private lateinit var mBinding: RecommendationLayoutBinding
    private val homeModel: HomeViewModel by viewModel()
    private lateinit var progress: Dialog
    private var list: ArrayList<TeacherListModelItem> = ArrayList()
    private var univlist: ArrayList<UniversitiesModel> = ArrayList()
    private var recCollegeList: ArrayList<RecCollegeModel.CollegeDetails?> = ArrayList()
    private var selectedList: ArrayList<TeacherListModelItem> = ArrayList()
    private var selectedUnivList: ArrayList<UniversitiesModel> = ArrayList()
    private var selectedUcasList: ArrayList<TeacherListModelItem> = ArrayList()
    private var jsonDeadline: JsonArray = JsonArray()
    private lateinit var adapter: RecommenderAdapter
    private lateinit var adapterStudent: RecommenderCollegeAdapter
    private var typeRecs = REC_TYPE_RECOMENDATION
    private var typeCollege = TYPE_COLLEGE_WITHOUT
    private var page: Int = 1
    private var requestListUpdate: ArrayList<RecomdersModel.Data?>? = ArrayList()
    private var requestList: ArrayList<RecomdersModel.Data?>? = ArrayList()
    private var isLoading = false
    private var requestListNew = ArrayList<RecomdersModel.Data?>()
    private var requestSListNew = ArrayList<RecCollegeModel.CollegeDetails?>()
    private var isAttached = false
    private var url: String? = null
    private var json: JSONObject? = null
    private var fileId: String = ""
    private var recoUpdate: Boolean = false
    private lateinit var tableLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        mBinding = RecommendationLayoutBinding.inflate(inflater, container, false)
        tableLayout = requireActivity().findViewById<TabLayout>(R.id.tabs)
        return mBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isAttached = true
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (tableLayout.selectedTabPosition == 4) {
            if (isAttached)
                setListeners()
            progress = showLoadingDialog(requireContext())
            progress.show()
            SharedHelper(requireContext()).id?.let { homeModel.getTeachers(it) }
            listUniversities()
            homeModel.getRecDeadline()
            mBinding.send.setOnClickListener {
                if (typeRecs == REC_TYPE_RECOMENDATION) {

                } else if (typeRecs == BOTH) {
                    if (mBinding.recLetter.isChecked) {
                        sendRecomendation(typeRecs)
                    }
                    if (mBinding.ucasLetter.isChecked) {
                        sendUCASRecomendation()
                    }
                } else {
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
            mBinding.recLetter.setOnClickListener {
                showRecs()
            }
            mBinding.recipentUcas.setOnClickListener {
                selectedUcasList.clear()
                listTeacher(REC_TYPE_UCAS)
            }
            mBinding.recipentUniversity.setOnClickListener {
                universititesBottomSheet()
            }
            mBinding.textAddFile.setOnClickListener {
                checkStoragePermissionAndOpenImageSelection()
            }
            mBinding.downloadSheet.setOnClickListener {
                homeModel.getBragSheet(SharedHelper(requireContext()).id ?: "")
            }
            progress.show()


            adapter =
                RecommenderAdapter(requireContext(), requestListNew, this, mBinding.requestList)
            mBinding.requestList.adapter = adapter
            adapter.setOnLoadMoreListener(object : OnLoadMoreListener {
                override fun onLoadMore() {
                    requestListNew.add(null)
                    isLoading = true
                    adapter.notifyItemInserted(requestListNew.size - 1)
                    Handler(Looper.getMainLooper()).postDelayed({
                        hitAPI(page.toString())

                    }, 2000)
                }
            })

            checkRecomType()
        }
    }

    private fun listUniversities() {
        homeModel.getUniversities(SharedHelper(requireContext()).id ?: "")
    }

    private fun checkRecomType() {
        homeModel.getRecomType(SharedHelper(requireContext()).schoolId ?: "")
    }

    fun hitAPI(page: String) {
        progress.show()
        homeModel.getRecommenders(SharedHelper(requireContext()).id ?: "", page)
    }

    fun hitAPISchoolRecomenders(page: String) {
        homeModel.getRecommendersCollege(SharedHelper(requireContext()).id ?: "", page)
    }

    private fun sendUCASRecomendation() {
        val teacherId = arrayListOf<String>()
        if (selectedUcasList.size == 0)
            return
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
        if (type == BOTH && typeCollege == TYPE_COLLEGE_WITHOUT) {
            if (mBinding.textDescription.text.toString().trim() == "" || selectedList.size == 0
            ) {
                return
            }
        }
        val teacherId = arrayListOf<String>()
        for (i in selectedList) {
            i.uid?.let { it1 -> teacherId.add(it1) }
        }
        if (typeCollege == TYPE_COLLEGE) {
            val collegeId = arrayListOf<String>()
            for (i in selectedUnivList) {
                i.id?.let { collegeId.add(it) }
            }
            if (mBinding.textDescription.text.toString().trim() != "" && teacherId.size
                > 0 && collegeId.size > 0
            ) {
                progress.show()

                val recmodel = RecModel(
                    mBinding.textDescription.text.toString(),
                    SharedHelper(requireContext()).id ?: "",
                    jsonDeadline.get(mBinding.typeValue.selectedItemPosition).asString,
                    teacherId, collegeId
                )
                homeModel.sendRecomTeachers(recmodel)
            }
        } else {
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

    }

    private fun showRecs() {
        context?.let {
            val both =
                SharedHelper(it).appResponse?.mlSchoolConfigData?.recommendation?.defaultListing?.both
            val recommendationLetterRequests =
                SharedHelper(it).appResponse?.mlSchoolConfigData?.recommendation?.defaultListing?.recommendationLetterRequests
            if (both == 1) {
                mBinding.recomendationSelection.visibility = View.VISIBLE
                if (mBinding.ucasLetter.isChecked) {
                    mBinding.recipentUcas.visibility = View.VISIBLE
                } else {
                    mBinding.recipentUcas.visibility = View.GONE
                }
                if (mBinding.recLetter.isChecked) {
                    mBinding.recipent.visibility = View.VISIBLE
                } else {
                    mBinding.recipent.visibility = View.GONE
                }
                if (!mBinding.recLetter.isChecked && !mBinding.ucasLetter.isChecked) {
                    mBinding.reqRecsCard.visibility = View.GONE
                } else {
                    mBinding.reqRecsCard.visibility = View.VISIBLE
                }
                typeRecs = BOTH
            } else {
                if (recommendationLetterRequests == 1) {
                    typeRecs = REC_TYPE_RECOMENDATION
                    mBinding.recipent.visibility = View.VISIBLE
                    mBinding.recipentUcas.visibility = View.GONE
                } else {

                    typeRecs = REC_TYPE_UCAS

                    mBinding.recipent.visibility = View.GONE
                    mBinding.recipentUcas.visibility = View.VISIBLE
                }
                mBinding.recomendationSelection.visibility = View.GONE
            }

        }

    }

    private fun initRecyclerView() {
        context?.let {
            adapterStudent =
                RecommenderCollegeAdapter(
                    it,
                    requestSListNew,
                    mBinding.requestList,
                    ::cancelUpdateClick
                )
        }
        mBinding.requestList.adapter = adapterStudent
        adapterStudent.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                requestSListNew.add(null)
                isLoading = true
                adapterStudent.notifyItemInserted(requestSListNew.size - 1)
                Handler(Looper.getMainLooper()).postDelayed({
                    hitAPISchoolRecomenders(page.toString())

                }, 2000)
            }
        })
    }

    var updateRecoBragId: String = ""
    fun cancelUpdateClick(data: RecCollegeModel.RecomenderName, cancel: Boolean) {
        if (cancel) {
            progress.show()
            homeModel.cancelRecommendRequest(data?.id.toString())
        } else {
            recoUpdate = true
            updateRecoBragId = "" + data.id
            checkStoragePermissionAndOpenImageSelection()
        }
    }

    private fun setListeners() {
        homeModel.bragSheetObserver.observe(requireActivity()) {
            val json = JSONObject(it.toString())
            fileId = json.getString("file_id")
            homeModel.downloadBragSheet(fileId, SharedHelper(requireContext()).id ?: "")
        }
        homeModel.downloadObserver.observe(requireActivity()) {
            progress.dismiss()
            var url = ""
            if (it.get(0).toString() != "") {
                url = it.get(0).toString()
                val manager =
                    requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

                val uri = Uri.parse(url.replace("\"", ""))
                val file =
                    File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path.toString() + "/" +  File(uri.path).name)

                val request = DownloadManager.Request(uri)
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                request.setAllowedOverRoaming(true)
                request.setDestinationUri(
                    Uri.fromFile(
                        //File(
//                            context?.getExternalFilesDir(
//                                Environment.DIRECTORY_DOWNLOADS
//                            ).toString(), File(uri.path).name
                    file


                    )
                )

                manager.enqueue(request)
            }
        }


        homeModel.typeObserver.observe(requireActivity()) {
            if ((it.get(0).toString()) == "0") {
//                Toast.makeText(context, "0", Toast.LENGTH_LONG).show()
                typeCollege = TYPE_COLLEGE
                mBinding.recipentUcas.visibility = View.GONE
                mBinding.recipentUniversity.visibility = View.VISIBLE
                mBinding.recomendationSelection.visibility = View.GONE
                initRecyclerView()
                progress.show()
                hitAPISchoolRecomenders(page.toString())
            } else {
                typeCollege = TYPE_COLLEGE_WITHOUT
                showRecs()
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
            context?.let {
                val adapter = ArrayAdapter(
                    it,
                    R.layout.spinner_text, array
                )
                mBinding.typeValue.adapter = adapter
            }

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
                requestListNew.removeAt(requestListNew.size - 1)
                adapter.notifyItemRemoved(requestListNew.size)
            }
            //for swipe refresh page
            if (totalPage != null) {
                if (page != null) {
                    adapter.addAllLis(requestList!!, totalPage, page)
                }
            }
            adapter.setLoaded()

        }
        homeModel.recSendObserver.observe(requireActivity()) {
            progress.dismiss()
            if (it.get(0).asString.replaceInvertedComas().equals("true"))
                context?.showToast(getString(R.string.recs_letter))
            else
                context?.showToast(it.get(0).asString.replaceInvertedComas())

            selectedUnivList.clear()
            selectedList.clear()
            mBinding.textDescription.setText("")
            mBinding.selectedTeachers.text = ""
            mBinding.selectedUniversity.text = ""
        }
        homeModel.recUCASObserver.observe(requireActivity()) {
            progress.dismiss()
            selectedUcasList.clear()
            mBinding.textDescription.setText("")
            mBinding.selectedTeachersUcas.text = ""
            if (it.get(0).asString.replaceInvertedComas().equals("true"))
                context?.showToast(getString(R.string.recs_letter))
            else
                context?.showToast(it.get(0).asString.replaceInvertedComas())
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
            val obj = JSONObject(it.toString())
            if (obj.get("file_status").toString() == "clean") {
                url?.let { it1 -> saveWork(0, json, it1) }
            } else {
                Toast.makeText(requireContext(), "Please check your File", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        homeModel.showError.observe(requireActivity()) {
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
                val key: String = x.next()
                val universityModel = UniversitiesModel()
                universityModel.id = key
                universityModel.name = json.optString(key)
                univlist.add(universityModel)
            }
            univlist.sortBy { it.name?.capitalize() }
            progress.dismiss()
        }
        homeModel.recommdersCollegeObserver.observe(requireActivity()) {
            val json = JSONObject(it.toString())
            val x = json.keys() as Iterator<String>
            val current = json.getJSONObject("pager").getString("current")
            page = current.toInt() + 1
            val last = json.getJSONObject("pager").getString("last")
            recCollegeList.clear()
            while (x.hasNext()) {
                val key: String = x.next()
                val recModel = RecCollegeModel()
                recModel.id = key
                val jsonObj = json.optJSONObject(key)
                var id: String? = null
                if (key.isInt()) {
                    id = key
                    val recommenderName = ArrayList<RecCollegeModel.RecomenderName>()
                    if (jsonObj.has("recommenders_name")) {
                        val y =
                            jsonObj.getJSONObject("recommenders_name").keys() as Iterator<String>
                        while (y.hasNext()) {
                            val keyY: String = y.next()
                            val jsonObjY =
                                jsonObj.getJSONObject("recommenders_name").optJSONObject(keyY)
                            var recName = ""
                            if (jsonObjY.has("done"))
                                recName = jsonObjY.optString("done")
                            else if (jsonObjY.has("pending"))
                                recName = jsonObjY.optString("pending")
                            recommenderName.add(
                                RecCollegeModel.RecomenderName(
                                    keyY,
                                    recName,
                                    jsonObjY.optString("preserved_data"),
                                    jsonObjY.optString("cancel"),
                                    jsonObjY.optString("reco_created"),
                                    jsonObjY.optString("req_fileid"),
                                    jsonObjY.optString("req_filename")
                                )
                            )
                        }
                    }
                    recModel.collegeDetails = RecCollegeModel.CollegeDetails(
                        academicYear = jsonObj.optString("academic_year"),
                        applicationMode = jsonObj.optString("applicationMode"),
                        collegeName = jsonObj.optString("college_name"),
                        collegeUnitId = jsonObj.optString("college_unit_id"),
                        notes = jsonObj.optString("notes"),
                        completed = jsonObj.optInt("completed"),
                        dueDate = jsonObj.optString("due_date"),
                        recoName = recommenderName,
                        id = recModel.id
                    )
                    recCollegeList.add(recModel.collegeDetails)
                    recCollegeList.sortBy {
                        it?.id?.toInt()
                    }
                }

            }
            if (isLoading) {
                isLoading = false
                requestSListNew.removeAt(requestSListNew.size - 1)
                adapterStudent.notifyItemRemoved(requestSListNew.size)
            }
            //for swipe refresh page
            adapterStudent.addAllLis(recCollegeList!!, last.toInt(), current.toInt())
            adapterStudent.setLoaded()
            progress.dismiss()
        }
    }

    private fun universititesBottomSheet() {
        selectedUnivList.clear()
        var type = REC_TYPE_RECOMENDATION
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
        sheetBinding.searchText.hint = "Search University"
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
        val filterList: ArrayList<UniversitiesModel> = ArrayList()
        for (member in univlist) {
            if ((member.name.lowercase().contains(name?.lowercase() ?: "")
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
        const val TYPE_COLLEGE = 0
        const val TYPE_COLLEGE_WITHOUT = 1
        const val REC_TYPE_UCAS = 2
        const val BOTH = 3
    }

    override fun onCancelClick(data: RecomdersModel.Data?) {
        progress.show()
        homeModel.cancelRecommendRequest(data?.nid.toString())
    }

    override fun onBragClick(data: RecomdersModel.Data?) {
        recoUpdate = true
        updateRecoBragId = "" + data?.nid
        checkStoragePermissionAndOpenImageSelection()
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
        var tvTitle = pdfDialog.findViewById(R.id.tvTitle) as TextView
        if (recoUpdate) {
            tvTitle.setText("Update Brag Sheet")
        }
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

//        homeModel.getDocumentPresignedObserver.observe(requireActivity()) {
////            progress.dismiss()
//            json = JSONObject(it.toString())
//            if (json?.optInt("exist") == 0) {
//                url = json?.optString("s3_url")
//                url?.let { it1 -> homeModel.uploadDoc(it1) }
//            } else if (json?.optInt("exist") == 1) {
//                saveWork(1, json, "")
//            }
//
//        }
//        homeModel.uploadImageObserver.observe(requireActivity()) {
//            url?.let { it1 -> homeModel.checkFileVirus(ANTI_VIRUS, it1) }
//        }
//        homeModel.fileVirusObserver.observe(requireActivity()) {
//            val obj = JSONObject(it.toString())
//            if (obj?.get("file_status").toString() == "clean") {
//                url?.let { it1 -> saveWork(0, json, it1) }
//            } else {
//                Toast.makeText(requireContext(), "Please check your File", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//        homeModel.showError.observe(requireActivity()) {
//            progress.dismiss()
//        }
    }

    private fun saveWork(exist: Int, json: JSONObject?, url: String) {
        if (json != null) {
            if (recoUpdate) {
                homeModel.uploadRecoBragsheet(
                    updateRecoBragId,
                    json.optString("filename"),
                    json.optString("path"),
                    json.optString("hash")
                )
            } else {
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
        }
        homeModel.saveDocumentBragsheetObserver.observe(requireActivity()) {
            context?.getString(R.string.updated)?.let { it1 -> context?.showToast(it1) }
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
        if (data!=null&&requestCode == REQUEST_CHOOSE_PDF_UPCOMING_DETAIL) {
            fileUri = data?.data!!
            val uri: Uri = data?.data!!
            val uriString: String = uri.toString()
            if (uriString.startsWith("content://")) {
                var myCursor: Cursor? = null
                try {
                    // Setting the PDF to the TextView
                    myCursor = requireContext().contentResolver.query(uri, null, null, null, null)
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

            imagePath = PathUtil.getDriveFilePath(requireContext(), uri)
//            imagePath=getRealPathFromURI(fileUri)
        }
    }


}

interface onClick {
    fun onCancelClick(data: RecomdersModel.Data?)
    fun onBragClick(data: RecomdersModel.Data?)
}
