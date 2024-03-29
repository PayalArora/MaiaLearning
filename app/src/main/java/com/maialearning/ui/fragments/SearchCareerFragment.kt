package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maialearning.R
import com.maialearning.databinding.CareerFilterBottomsheetBinding
import com.maialearning.databinding.SearchCareerLayBinding
import com.maialearning.databinding.SearchDetailBinding
import com.maialearning.databinding.UniversityFilterBinding
import com.maialearning.model.*
import com.maialearning.ui.adapter.*
import com.maialearning.util.*
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.viewmodel.CareerViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.collections.ArrayList


class SearchCareerFragment(var type: String) : Fragment() {
    var dialog: BottomSheetDialog? = null
    var dialogCareerFilterBottomsheetBinding:BottomSheetDialog?= null
    var dialogRegionTypeBottomsheetBinding:BottomSheetDialog?= null
    var dialogResultBottomsheetBinding:BottomSheetDialog?= null
    var statusdialog:BottomSheetDialog?= null
    var  usMilitaryBinding:BottomSheetDialog?= null
    var  clusterBinding:BottomSheetDialog?= null
    private lateinit var mBinding: SearchCareerLayBinding
    private val careerViewModel: CareerViewModel by viewModel()
    private lateinit var progress: Dialog
    lateinit var gson: Gson
    private val listData: String
        get() = ("[{\"id\":\"15\",\"name\":\"Transportation, Distribution & Logistics\"}" + ",{\"id\":\"1\",\"name\":\"Architecture & Construction\"}" + ",{\"id\":\"2\",\"name\":\"Arts, Audio/Video Technology & Communications\"}" +
                ",{\"id\":\"9\",\"name\":\"Agriculture, Food & Natural Resources\"}" + ",{\"id\":\"3\",\"name\":\"Business Management & Administration\"}" + ",{\"id\":\"4\",\"name\":\"Education & Training\"}" + ",{\"id\":\"10\",\"name\":\"Finance\"}" +
                ",{\"id\":\"11\",\"name\":\"Government & Public Administration\"}" + ",{\"id\":\"12\",\"name\":\"Health Science\"}" + ",{\"id\":\"5\",\"name\":\"Hospitality & Tourism\"}" + ",{\"id\":\"13\",\"name\":\"Human Services\"}" +
                ",{\"id\":\"16\",\"name\":\"Information Technology\"}" + ",{\"id\":\"14\",\"name\":\"Law, Public Safety, Corrections & Security\"}" + ",{\"id\":\"6\",\"name\":\"Manufacturing\"}" + ",{\"id\":\"7\",\"name\":\"Marketing\"}" +
                ",{\"id\":\"8\",\"name\":\"Science, Technology, Engineering & Mathematics\"}]")

    private val usData: String
        get() = ("[{\"key\":\"1\",\"name\":\"Army\"}" + ",{\"key\":\"2\",\"name\":\"Marine Corps\"}" + ",{\"key\":\"3\",\"name\":\"Air Force\"}" + ",{\"key\":\"4\",\"name\":\"Navy\"}" + ",{\"key\":\"5\",\"name\":\"Coast Guard\"}]")

    private lateinit var tableLayout: TabLayout
    private var adapType: Int = 1
    var arrayTopPick: ArrayList<RelatedCareerModel> = arrayListOf()
    var type_search:String?= null
    var response:ArrayList<CareerCategoryResponseItem>? = null
    var filtersFactsheetResponse:CareerFilterResponse? = null
    var military = ""
    var cluster = ""
    var status = arrayListOf<Region>()
    var regionLevel=""
    var regionCode=""
    var regionName=""
    var sessionDataResponse:SessionDataResponse?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gson = GsonBuilder().create()
        progress = showLoadingDialog(requireContext())

        mBinding = SearchCareerLayBinding.inflate(inflater, container, false)
        tableLayout = requireActivity().findViewById<TabLayout>(R.id.tabs)
        progress.show()
        SharedHelper(requireContext()).id?.let {
            careerViewModel.getStudentTopPick(it)
        }
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        mBinding.spinner.setOnClickListener {
            showBottomSheet()
        }
        mBinding.outSpinnerText.setOnClickListener {
            type_search?.let {
                if (it == "category"|| it == "pathway"){
                    response?.let { it1 -> showSearchResultBottomSheet(it, it1) }
                }
            }

        }
        mBinding.outSpinnerText4.setOnClickListener {
            filtersFactsheetResponse?.let { it1 ->
                if (!mBinding.outSpinnerText3.text.toString().equals(getString(R.string.select_region_type))) {
                    showRegionNameBottomSheet(mBinding.outSpinnerText3.text.toString(), it1)
                }

            }
        }
        mBinding.outSpinnerText1.setOnClickListener {
            val list: ArrayList<IndustryModel> = arrayListOf()
            val jsonArry = JSONArray(usData)
            for (i in 0 until jsonArry.length()) {
                val obj = jsonArry.getJSONObject(i)
                val user = IndustryModel(obj.getInt("key"), obj.getString("name"))
                list.add(user)
            }
            showUsMilitaryBottomSheet("us", list)
        }
        mBinding.outSpinnerText2.setOnClickListener {
            val list: ArrayList<IndustryModel> = arrayListOf()
            val jsonArry = JSONArray(listData)
            for (i in 0 until jsonArry.length()) {
                val obj = jsonArry.getJSONObject(i)
                val user = IndustryModel(obj.getString("id").toInt(), obj.getString("name"))
                list.add(user)
            }
            showClusterBottomSheet("cluster", list)
        }

        mBinding.outSpinnerStatus.setOnClickListener {
            context?.let { it1 -> showBottomSheetStatus(it1) }

        }
        mBinding.outSpinnerText3.setOnClickListener {
            context?.let { it1 -> showBottomSheetRegionType() }

        }
        mBinding.saveBtn.setOnClickListener {
            if (!mBinding.outSpinnerText4.isVisible) {
                val pager = "1"
                var url = pager.getUSIndustryNoService()
                if (checkNonNull(military)) {
                    url = "${getCareerUsSearch()}${military.getUSService()}"
                }
                if (checkNonNull(cluster)) {
                    url = "${getCareerUsSearch()}${cluster.getUSIndustryCluster()}"
                }

                for (i in status) {
                    when (i.reigon?.replaceInvertedComas()) {
                        "Enlisted" -> {
                            url = "$url&enlisted=1"
                        }
                        "Active Duty" -> {
                            url = "$url&active_duty=1"
                        }
                        "Reserve" -> {
                            url = "$url&reserve=1"
                        }
                        "Officer" -> {
                            url = "$url&officer=1"
                        }
                    }

                }

                progress.show()
                careerViewModel.getUSSearch(url)
            } else{
                if (!mBinding.savePref.isChecked){
                if (!checkNonNull(regionLevel)){
                    context?.showToast(getString(R.string.region_type))
                } else if (!checkNonNull(regionCode)){
                    context?.showToast(getString(R.string.region_name))
                } else {
                    progress.show()
                    careerViewModel.getCareerSearch(
                        "demand",
                        "null",
                        "0",
                        "30", regionLevel, regionCode
                    )
                }} else{
                    progress.show()
                    careerViewModel.careerPref(regionLevel, regionCode, regionName)
                }

            }

        }
        mBinding.removeButton.setOnClickListener {
            if (checkNonNull(regionCode)) {
                progress.show()
                careerViewModel.careerPref("null", "null", "null")
            }
        }

        if (type == "list") {
            mBinding.searchLay.visibility = View.GONE
            mBinding.cardView.visibility = View.GONE
            mBinding.list.visibility = View.VISIBLE
            if (tableLayout.selectedTabPosition == 1)
                progress.show()
            SharedHelper(requireContext()).id?.let { careerViewModel.getCareerList(it) }
        } else if (type == "trafic") {
            mBinding.searchLay.visibility = View.GONE
            mBinding.text.visibility = View.GONE
            mBinding.cardView.visibility = View.GONE
            mBinding.list.visibility = View.VISIBLE

        } else {
            mBinding.searchLay.visibility = View.VISIBLE
            mBinding.cardView.visibility = View.VISIBLE
            mBinding.list.visibility = View.GONE
         //   setSearchSpinner()

        }
        mBinding.searchLay.setOnClickListener {
            mBinding.searchLay.visibility = View.GONE
            mBinding.list.visibility = View.VISIBLE
        }
        mBinding.text2.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                progress.show()
                careerViewModel.getCareerSearch(
                    "keyword",
                    mBinding.text2.text.toString(),
                    "0",
                    "30", null, null
                )
                //  careerViewModel.getKeyboardSearch(mBinding.text2.text.toString().getURLSearch())
                return@OnEditorActionListener true
            }
            false
        })

        val fab = activity?.findViewById<RelativeLayout>(R.id.add_fab)
        fab?.setOnClickListener {
            if (adapType == 1)
                bottomSheetCompareSearch()
            else
                bottomSheetCompareList()
        }
    }

    private fun showBottomSheetStatus(con: Context) {
         statusdialog = BottomSheetDialog(con)
        var reigonList = arrayListOf<Region>()
        val sheetBinding: UniversityFilterBinding = UniversityFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        statusdialog?.setContentView(sheetBinding.root)
        sheetBinding.clearText.setText(con.resources.getString(R.string.done))
        sheetBinding.search.visibility = View.GONE
        sheetBinding.filters.setText(getString(R.string.careers))
        statusdialog?.show()

        sheetBinding.spinnerLay.visibility  = View.GONE
        sheetBinding.invisibleLay.visibility  = View.GONE
        sheetBinding.backBtn.setOnClickListener { statusdialog?.dismiss() }

        sheetBinding.clearText.setOnClickListener {
            var selectedStatus = arrayListOf<Region>()
            var selectedRegion = arrayListOf<String>()
            for (i in status){
                if (i.checked ){
                    selectedStatus.add(i)
                    selectedRegion.add(i.reigon.toString())
                }
            }
            status.clear()
            status.addAll(selectedStatus)
            mBinding.outSpinnerStatus.setText(
                android.text.TextUtils.join(
                    ", ",
                    selectedRegion
                )
            )
            statusdialog?.dismiss()
        }

            for (i in con.resources.getStringArray(R.array.us_military_status)) {
                reigonList.add(Region(i, false))
            }
            sheetBinding.reciepentList.adapter =
                ReigonAdapter(reigonList, ::selectedStatus)
            sheetBinding.spinnerLay.visibility = View.GONE

    }

    private fun selectedStatus(arrayList: ArrayList<Region>) {
     status = arrayList

    }

    private fun showBottomSheet() {
         dialogCareerFilterBottomsheetBinding = BottomSheetDialog(requireContext())

        val sheetBinding: CareerFilterBottomsheetBinding = CareerFilterBottomsheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialogCareerFilterBottomsheetBinding?.setContentView(sheetBinding.root)
        dialogCareerFilterBottomsheetBinding?.show()
        sheetBinding.search.visibility = View.GONE

       sheetBinding.backBtn.setOnClickListener {
           dialogCareerFilterBottomsheetBinding?.dismiss()
        }


        val adapter = CareerFilterAdapter(
            requireContext(),
            resources.getStringArray(R.array.SEARCH_ARRAY), ::clickMainDropdown
        )
        var decor = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        getDrawable(requireContext(),R.drawable.divider)?.let { decor.setDrawable(it) }
        sheetBinding.listing.addItemDecoration(decor)


        sheetBinding.listing.adapter = adapter


    }
    private fun showRegionNameBottomSheet(type: String, resp:CareerFilterResponse) {
        dialogResultBottomsheetBinding = BottomSheetDialog(requireContext())

        val sheetBinding: CareerFilterBottomsheetBinding = CareerFilterBottomsheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialogResultBottomsheetBinding?.setContentView(sheetBinding.root)
        dialogResultBottomsheetBinding?.show()

        sheetBinding. backBtn.setOnClickListener {
            dialogResultBottomsheetBinding?.dismiss()
        }
        sheetBinding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (sheetBinding.searchText.text.toString().trim() != "") {
                    val filterList =
                        findRegionName(sheetBinding.searchText.text.toString().trim(),type, resp)
                    setRegionNameAdapter(type,filterList, sheetBinding)
                } else {
                    setRegionNameAdapter(type,resp, sheetBinding)
                }

            }


        })

        var decor = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        getDrawable(requireContext(),R.drawable.divider)?.let { decor.setDrawable(it) }
        sheetBinding.listing.addItemDecoration(decor)
        setRegionNameAdapter(type,resp, sheetBinding)


    }

    private fun setRegionNameAdapter(type: String,resp:CareerFilterResponse, sheetBinding:CareerFilterBottomsheetBinding) {

        if (type == "State") {
            val adapter = resp.state?.let {
                StateAdapter(
                    requireContext(),
                    it, ::clickStateItem
                )
            }
            sheetBinding.listing.adapter = adapter
        } else if (type == "County") {
            val adapter = resp.county?.let {
                CountyAdapter(
                    requireContext(),
                    it, ::clickCountyItem
                )
            }
            sheetBinding.listing.adapter = adapter
        }else if (type == "Metropolitan Area") {
            val adapter = resp.msa?.let {
                MsaAdapter(
                    requireContext(),
                    it, ::clickMsaItem
                )

            }
            sheetBinding.listing.adapter = adapter
        }
    }

    private fun findRegionName(name: String, type: String, resp: CareerFilterResponse): CareerFilterResponse {
        var response = CareerFilterResponse(resp.county,resp.msa,resp.state)
        if (type == "State") {
            val filterList:  ArrayList<StateItem> = ArrayList()
            for (member in response.state!!) {
                if ((member.name?.lowercase()?.contains(name?.lowercase() ?: "")
                        ?: false) ?: false
                ) {
                    filterList.add(member)
                    // return member when name found
                }
            }
            response.state= filterList
        } else if (type == "County") {
            val filterList:  ArrayList<CountyItem> = ArrayList()
            for (member in response.county!!) {
                if ((member.name?.lowercase()?.contains(name?.lowercase() ?: "")
                        ?: false) ?: false
                ) {
                    filterList.add(member)
                    // return member when name found
                }
            }
            response.county= filterList
        }else if (type == "Metropolitan Area") {
            val filterList:  ArrayList<MsaItem> = ArrayList()
            for (member in response.msa!!) {
                if ((member.name?.lowercase()?.contains(name?.lowercase() ?: "")
                        ?: false) ?: false
                ) {
                    filterList.add(member)
                    // return member when name found
                }
            }
            response.msa= filterList
        }
        return response
    }

    private fun clickStateItem(stateItem: StateItem) {
        dialogResultBottomsheetBinding?.dismiss()
        mBinding.outSpinnerText4.text = stateItem.name
        stateItem?.id?.let {  regionCode =it
        }
        stateItem?.name?.let { regionName = it }
    }
    private fun clickCountyItem(countyItem: CountyItem) {
        dialogResultBottomsheetBinding?.dismiss()
        mBinding.outSpinnerText4.text = countyItem.name
        countyItem?.id?.let {  regionCode =it }
        countyItem?.name?.let { regionName = it }
    }
    private fun clickMsaItem(msaItem: MsaItem) {
        dialogResultBottomsheetBinding?.dismiss()
        mBinding.outSpinnerText4.text = msaItem.name
        msaItem?.id?.let {  regionCode =it }
        msaItem?.name?.let { regionName = it }
    }

    private fun showSearchResultBottomSheet(type: String, resp: ArrayList<CareerCategoryResponseItem>) {
        dialogResultBottomsheetBinding = BottomSheetDialog(requireContext())

        val sheetBinding: CareerFilterBottomsheetBinding = CareerFilterBottomsheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialogResultBottomsheetBinding?.setContentView(sheetBinding.root)
        dialogResultBottomsheetBinding?.show()
        if (type == "Keyword")
        sheetBinding.search.visibility = View.GONE

        sheetBinding. backBtn.setOnClickListener {
            dialogResultBottomsheetBinding?.dismiss()
        }
        sheetBinding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (sheetBinding.searchText.text.toString().trim() != "") {
                    val filterList =
                        findData(sheetBinding.searchText.text.toString().trim(), resp)
                    val adapter = SearchCareerClusterAdapter(
                        requireContext(),
                        filterList, ::clickMainDropDownResult
                    )


                    sheetBinding.listing.adapter = adapter
                } else {
                    val adapter = SearchCareerClusterAdapter(
                        requireContext(),
                        resp, ::clickMainDropDownResult
                    )


                    sheetBinding.listing.adapter = adapter
                }

            }


        })

        var decor = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        getDrawable(requireContext(),R.drawable.divider)?.let { decor.setDrawable(it) }
        sheetBinding.listing.addItemDecoration(decor)
        val adapter = SearchCareerClusterAdapter(
            requireContext(),
            resp, ::clickMainDropDownResult
        )
        sheetBinding.listing.adapter = adapter


    }
    private fun showUsMilitaryBottomSheet(type: String, resp: ArrayList<IndustryModel>) {
        usMilitaryBinding = BottomSheetDialog(requireContext())

        val sheetBinding: CareerFilterBottomsheetBinding = CareerFilterBottomsheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        usMilitaryBinding?.setContentView(sheetBinding.root)
        usMilitaryBinding?.show()
        sheetBinding. backBtn.setOnClickListener {
            usMilitaryBinding?.dismiss()
        }
        sheetBinding.search.visibility = View.GONE

        var decor = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        getDrawable(requireContext(),R.drawable.divider)?.let { decor.setDrawable(it) }
        sheetBinding.listing.addItemDecoration(decor)
        val adapter = IndustryClusterAdapter(
            requireContext(),
            resp, ::clickMilitaryService, type
        )
        sheetBinding.listing.adapter = adapter


    }
    private fun showClusterBottomSheet(type: String, resp: ArrayList<IndustryModel>) {
        clusterBinding = BottomSheetDialog(requireContext())

        val sheetBinding: CareerFilterBottomsheetBinding = CareerFilterBottomsheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        clusterBinding?.setContentView(sheetBinding.root)
        clusterBinding?.show()
        sheetBinding. backBtn.setOnClickListener {
            clusterBinding?.dismiss()
        }
        sheetBinding.search.visibility = View.GONE

        var decor = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        getDrawable(requireContext(),R.drawable.divider)?.let { decor.setDrawable(it) }
        sheetBinding.listing.addItemDecoration(decor)
        val adapter = IndustryClusterAdapter(
            requireContext(),
            resp, ::clickMilitaryService, type
        )
        sheetBinding.listing.adapter = adapter


    }
    private fun showBottomSheetRegionType() {
        dialogRegionTypeBottomsheetBinding = BottomSheetDialog(requireContext())

        val sheetBinding: CareerFilterBottomsheetBinding = CareerFilterBottomsheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialogRegionTypeBottomsheetBinding?.setContentView(sheetBinding.root)
        dialogRegionTypeBottomsheetBinding?.show()
        sheetBinding.search.visibility = View.GONE

        sheetBinding.backBtn.setOnClickListener {
            dialogRegionTypeBottomsheetBinding?.dismiss()
        }

        val adapter = CareerFilterAdapter(
            requireContext(),
            resources.getStringArray(R.array.REGION_TYPE), ::clickRegionType
        )
        var decor = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        getDrawable(requireContext(),R.drawable.divider)?.let { decor.setDrawable(it) }
        sheetBinding.listing.addItemDecoration(decor)
        sheetBinding.listing.adapter = adapter
    }

    private fun clickRegionType(s: String) {
        mBinding.outSpinnerText3.text = s
        if (s == "State")
            regionLevel = "state"
        else if (s == "County")
            regionLevel = "county"
        else {
            regionLevel = "msa"
        }
        dialogRegionTypeBottomsheetBinding?.dismiss()

    }


    private fun findData(name: String, resp: ArrayList<CareerCategoryResponseItem>): ArrayList<CareerCategoryResponseItem> {
        val filterList:  ArrayList<CareerCategoryResponseItem> = ArrayList()
        for (member in resp) {
            if ((member.name?.lowercase()?.contains(name?.lowercase() ?: "")
                    ?: false) ?: false
            ) {
                filterList.add(member)
                // return member when name found
            }
        }
        return filterList
    }

    private fun clickMainDropDownResult(s: String, item:CareerCategoryResponseItem) {
        dialogResultBottomsheetBinding?.dismiss()
        mBinding.outSpinnerText.text = item.name
        progress.show()
        type_search?.let {
            careerViewModel.getCareerSearch(
                it,
                s,
                "0",
                "30", null,null
            )
        }
    }

    private fun clickMainDropdown(selectedItem: String) {
        mBinding.outSpinner.visibility = View.GONE
        mBinding.outSpinnerText.visibility = View.GONE
        mBinding.text2.visibility = View.GONE
        mBinding.outSpinnerText1.visibility = View.GONE
        mBinding.outSpinnerText2.visibility = View.GONE
        mBinding.outSpinnerStatus.visibility = View.GONE
        mBinding.outSpinnerText3.visibility = View.GONE
        mBinding.outSpinnerText4.visibility = View.GONE
        mBinding.savePref.visibility = View.GONE
        mBinding.removeButton.visibility = View.GONE
        mBinding.saveBtn.visibility = View.GONE
        dialogCareerFilterBottomsheetBinding?.dismiss()
        mBinding.spinner.text = selectedItem
            if (selectedItem == "Category") {
                mBinding.outSpinnerText.visibility = View.VISIBLE
                progress.show()
                careerViewModel.getCareerCategories()
                mBinding.outSpinnerText.text = getString(R.string.select_category)
            } else if (selectedItem == "Pathway") {
                mBinding.outSpinnerText.visibility = View.VISIBLE
                progress.show()
                careerViewModel.getCareerPathways()
                mBinding.outSpinnerText.text = getString(R.string.select_pathway)
            } else if (selectedItem == "Keyword") {
                mBinding.outSpinner.visibility = View.GONE
                mBinding.outSpinnerText.visibility = View.GONE
                mBinding.text2.visibility = View.VISIBLE
                mBinding.saveBtn.visibility = View.GONE
                type_search = "Keyword"
                // progress.show()
                //careerViewModel.getCareerPathways()
            } else if (selectedItem == "U.S. Military") {
                mBinding.workLayout.visibility = View.GONE
                mBinding.three.visibility = View.GONE
                mBinding.two.visibility = View.INVISIBLE
                mBinding.spinnerLay1.visibility = View.VISIBLE
                mBinding.outSpinnerText.visibility = View.GONE
                mBinding.outSpinnerText2.visibility = View.VISIBLE
                mBinding.outSpinnerStatus.visibility = View.VISIBLE
                mBinding.outSpinnerText1.visibility = View.VISIBLE
                mBinding.saveBtn.visibility = View.VISIBLE

                mBinding.outSpinnerText1.setText(getString(R.string.select_military))
                mBinding.outSpinnerText2.setText(getString(R.string.select_cluster))
                mBinding.outSpinnerStatus.setText(getString(R.string.select_status))
                type_search = "Military"
               // mBinding.outSpinner.visibility = View.VISIBLE
               // setUsSpinner()
            }else if (selectedItem == "High Demand Jobs") {
                mBinding.workLayout.visibility = View.GONE
                mBinding.three.visibility = View.GONE
                mBinding.two.visibility = View.INVISIBLE
                mBinding.spinnerLay1.visibility = View.VISIBLE
                mBinding.outSpinnerText.visibility = View.GONE
                mBinding.saveBtn.visibility = View.VISIBLE
                mBinding.outSpinnerText3.visibility = View.VISIBLE
                mBinding.outSpinnerText4.visibility = View.VISIBLE
                mBinding.outSpinnerText3.setText(getString(R.string.select_region_type))
                mBinding.outSpinnerText4.setText(getString(R.string.select_region_name))
                mBinding.savePref.visibility = View.VISIBLE
                mBinding.removeButton.visibility = View.VISIBLE
                // mBinding.outSpinner.visibility = View.VISIBLE
                // setUsSpinner()
                progress.show()
                careerViewModel.getCareerFilters()
                type_search = "demand"
                if (checkNonNull(regionLevel)){
                    when(regionLevel){
                        "state"->{
                            mBinding.outSpinnerText3.setText("State")
                        }
                        "county"->{
                            mBinding.outSpinnerText3.setText("County")
                        }
                        "msa"->{
                            mBinding.outSpinnerText3.setText("Metropolitan Area")
                        }
                    }
                }
                if (checkNonNull(regionName)){
                    mBinding.outSpinnerText4.setText(regionName)
                }
            }
 }


//private fun setSearchSpinner() {
//    val adapter = ArrayAdapter(
//        requireContext(),
//        R.layout.spinner_text, resources.getStringArray(R.array.SEARCH_ARRAY)
//    )
//    mBinding.spinner.adapter = adapter
//    mBinding.spinner.setSelection(0)
//    mBinding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//        override fun onItemSelected(
//            parent: AdapterView<*>,
//            view: View?,
//            position: Int,
//            id: Long
//        ) {
//            val selectedItem = parent.getItemAtPosition(position).toString()
//            mBinding.outSpinner.visibility = View.VISIBLE
//            mBinding.text2.visibility = View.GONE
//            if (selectedItem == "Category") {
//                progress.show()
//                careerViewModel.getCareerCategories()
//            } else if (selectedItem == "Pathway") {
//                progress.show()
//                careerViewModel.getCareerPathways()
//            } else if (selectedItem == "Keyword") {
//                mBinding.outSpinner.visibility = View.GONE
//                mBinding.text2.visibility = View.VISIBLE
//                // progress.show()
//                //careerViewModel.getCareerPathways()
//            } else if (selectedItem == "U.S. Military") {
//                mBinding.workLayout.visibility = View.VISIBLE
//                mBinding.three.visibility = View.GONE
//                mBinding.two.visibility = View.INVISIBLE
//                mBinding.spinnerLay1.visibility = View.VISIBLE
//                mBinding.outSpinner.visibility = View.VISIBLE
//                setUsSpinner()
//            }
//        } // to close the onItemSelected
//
//        override fun onNothingSelected(parent: AdapterView<*>?) {}
//    }
//}

//    private fun setUsSpinner() {
//        val list = ArrayList<IndustryModel>()
//        val jsonArry = JSONArray(usData)
//        for (i in 0 until jsonArry.length()) {
//            val obj = jsonArry.getJSONObject(i)
//            val user = IndustryModel(obj.getInt("key"), obj.getString("name"))
//            list.add(user)
//        }
//        mBinding.outSpinner.visibility = View.VISIBLE
//        val adapter = IndustryClusterAdapter(requireContext(), list, ::clickUSItem)
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//        mBinding.outSpinner.setAdapter(
//            NothingSelectedSpinnerAdapter(
//                adapter,
//                R.layout.nothing_adapter_us_military,  // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
//                requireContext()
//            )
//        )
////        val adapter = ArrayAdapter(
////            requireContext(),
////            R.layout.spinner_text, list
////        )
//        // mBinding.outSpinner.adapter = adapter
//        mBinding.outSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                progress.show()
//                if (position > 0) {
//                    careerViewModel.getUSSearch(list.get(position).id.toString().getUSIndustry("1"))
//                } else {
//                    careerViewModel.getUSSearch("1".getUSIndustryNoService())
//                }
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//        }
//    }

    private fun setWorkSpinner() {
        mBinding.spinnerLay1.visibility = View.GONE
        mBinding.workLayout.visibility = View.VISIBLE
        var url = BASE_URL + "get_work_values_career/1"
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_text, resources.getStringArray(R.array.WORK_ARRAY)
        )
        mBinding.spinnerOne.adapter = adapter
        mBinding.spinnerTwo.adapter = adapter
        mBinding.spinnerThree.adapter = adapter
        mBinding.spinnerOne.setSelection(0)
        mBinding.spinnerOne.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {

                if (!url.contains("first_value")) {
                    if (parent.selectedItemPosition != 0)
                        url += "?first_value=" + parent.getItemAtPosition(position).toString()
                } else if (!url.contains("second_value")) {
                    if (parent.selectedItemPosition != 0)
                        url += "&second_value=" + parent.getItemAtPosition(position).toString()
                } else if (!url.contains("third_value")) {
                    if (parent.selectedItemPosition != 0)
                        url += "&third_value=" + parent.getItemAtPosition(position).toString()
                } else {
                    if (parent.selectedItemPosition != 0)
                        url = url.replace(
                            url.substring(url.indexOf("first_value="), url.indexOf("&")),
                            "first_value=" + parent.getItemAtPosition(position).toString()
                        )
                }
                if (parent.selectedItemPosition != 0) {
                    if (!progress.isShowing)
                        progress.show()
                    careerViewModel.getWorkSearch(url)
                }
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        mBinding.spinnerTwo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!url.contains("first_value")) {
                    if (parent.selectedItemPosition != 0)
                        url += "?first_value=" + parent.getItemAtPosition(position).toString()
                } else if (!url.contains("second_value")) {
                    if (parent.selectedItemPosition != 0)
                        url += "&second_value=" + parent.getItemAtPosition(position).toString()
                } else if (!url.contains("third_value")) {
                    if (parent.selectedItemPosition != 0)
                        url += "&third_value=" + parent.getItemAtPosition(position).toString()
                } else {
                    if (parent.selectedItemPosition != 0)
                        url = url.replace(
                            url.substring(url.indexOf("second_value="), url.indexOf("&")),
                            "second_value=" + parent.getItemAtPosition(position).toString()
                        )

                }
                if (parent.selectedItemPosition != 0) {
                    if (!progress.isShowing)
                        progress.show()
                    careerViewModel.getWorkSearch(url)
                }
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        mBinding.spinnerThree.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!url.contains("first_value")) {
                    if (parent.selectedItemPosition != 0)
                        url += "?first_value=" + parent.getItemAtPosition(position).toString()
                } else if (!url.contains("second_value")) {
                    if (parent.selectedItemPosition != 0)
                        url += "&second_value=" + parent.getItemAtPosition(position).toString()
                } else if (!url.contains("third_value")) {
                    if (parent.selectedItemPosition != 0)
                        url += "&third_value=" + parent.getItemAtPosition(position).toString()
                } else {
                    if (parent.selectedItemPosition != 0)
                        url = url.replace(
                            url.substring(url.indexOf("third_value="), url.length),
                            "third_value=" + parent.getItemAtPosition(position).toString()
                        )

                }
                if (parent.selectedItemPosition != 0) {
                    if (!progress.isShowing)
                        progress.show()
                    careerViewModel.getWorkSearch(url)
                }

            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setClusterAdapter(it: CareerClusterModel?) {
        mBinding.text2.visibility = View.GONE
        mBinding.outSpinner.visibility = View.VISIBLE
        val adapter = CareerCluster(requireContext(), it?.careerCluster!!, ::clickClusterItem)
        mBinding.outSpinner.adapter = adapter
        mBinding.outSpinner.setSelection(0)

    }

    private fun clickUSItem(item: IndustryModel) {
        //  progress.show()
        // careerViewModel.getUSSearch(item.id.toString().getUSIndustry())
    }

    private fun clickIndustryItem(item: IndustryModel) {
        progress.show()
        careerViewModel.getIndustryList(item.id.toString().getURLIndustry())
    }

    private fun clickClusterItem(item: CareerClusterModel.CareerCluster) {
        progress.show()
        careerViewModel.getCareerClusterList(item.code!!.getURLCluster())
    }

    private fun setOutlookSpinner() {
        mBinding.workLayout.visibility = View.GONE
        mBinding.text2.visibility = View.GONE
        mBinding.spinnerLay1.visibility = View.VISIBLE
        mBinding.outSpinner.visibility = View.VISIBLE
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_text, resources.getStringArray(R.array.OUT_ARRAY)
        )
        mBinding.outSpinner.adapter = adapter
        mBinding.outSpinner.setSelection(0)
        mBinding.outSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                when (selectedItem) {
                    "All" -> {
                        progress.show()
                        careerViewModel.getCareerBright("all")
                    }
                    "Grow Rapidly" -> {
                        progress.show()
                        careerViewModel.getCareerBright("grow")
                    }
                    "Many Openings" -> {
                        progress.show()
                        careerViewModel.getCareerBright("openings")
                    }
                    "New & Emerging" -> {
                        progress.show()
                        careerViewModel.getCareerBright("emerging")
                    }
                }
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    private fun setCategorySpinner(resp: ArrayList<CareerCategoryResponseItem>, type: String) {
        mBinding.workLayout.visibility = View.GONE
        mBinding.text2.visibility = View.GONE
        mBinding.spinnerLay1.visibility = View.VISIBLE
        mBinding.outSpinner.visibility = View.VISIBLE

        val adapter = CategoriesAdapter(requireContext(), resp, ::clickCategoryItem)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        mBinding.outSpinner.setAdapter(
            NothingSelectedSpinnerAdapter(
                adapter,
                R.layout.nothing_adapter_category,  // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                requireContext()
            )
        )

        //  mBinding.outSpinner.setSelection(0)
        mBinding.outSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    progress.show()
                    careerViewModel.getCareerSearch(
                        type,
                        resp.get(position - 1).id.toString(),
                        "0",
                        "30",null, null
                    )
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

    var arrayList: ArrayList<CareerTopPickResponseItem?>? = arrayListOf()
    var arrayListOut: ArrayList<BrightOutlookModel.Data?>? = arrayListOf()
    var arrayListUs: ArrayList<CareerUSModel.Data?>? = arrayListOf()

    private fun initObserver() {
        careerViewModel.getSessionObserver.observe(viewLifecycleOwner){
            progress.dismiss()
            sessionDataResponse = it
            it.data?.users?.careerRegionPreference?.regionCode?.let { it1 ->
                regionCode = it1
                regionLevel = it.data?.users?.careerRegionPreference?.regionLevel.toString()
                regionName = it.data?.users?.careerRegionPreference?.regionName.toString()
            }
        }
        careerViewModel.getCareerFilterObserver.observe(viewLifecycleOwner){
            progress.dismiss()
            filtersFactsheetResponse = it
        }
        careerViewModel.unlikeObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            SharedHelper(requireContext()).id?.let {
                careerViewModel.getStudentTopPick(it)
            }
        }
        careerViewModel.addFavObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            SharedHelper(requireContext()).id?.let {
                careerViewModel.getStudentTopPick(it)
            }
        }
        careerViewModel.topPickObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            progress.show()
            careerViewModel.getCareerSessionData()
            arrayTopPick = arrayListOf()
            if (it != null) {
                for (i in it) {
                    val toppick = gson.fromJson(i, RelatedCareerModel::class.java)
                    arrayTopPick.add(toppick)
                }

            }
            careerViewModel.careerPrefObserver.observe(viewLifecycleOwner){
                progress.dismiss()
                val obj = JSONObject(it.toString())
                if (obj.optJSONObject("data").optJSONObject("career_region_preference")!= null){
                    if (!checkNonNull(obj.optJSONObject("data").optJSONObject("career_region_preference").optString("region_code"))) {
                        regionLevel = ""
                        regionName = ""
                        regionCode = ""
                        mBinding.outSpinnerText3.setText(getString(R.string.select_region_type))
                        mBinding.outSpinnerText4.setText(getString(R.string.select_region_name))
                        mBinding.savePref.isChecked = false
                    }
                    else {
                        regionCode = obj.optJSONObject("data").optJSONObject("career_region_preference").optString("region_code")
                        regionLevel = obj.optJSONObject("data").optJSONObject("career_region_preference").optString("region_level")
                        regionName= obj.optJSONObject("data").optJSONObject("career_region_preference").optString("region_name")
                        if (!checkNonNull(regionLevel)) {
                            context?.showToast(getString(R.string.region_type))
                        } else if (!checkNonNull(regionCode)) {
                            context?.showToast(getString(R.string.region_name))
                        } else {
                            progress.show()
                            careerViewModel.getCareerSearch(
                                "demand",
                                "null",
                                "0",
                                "30", regionLevel, regionCode
                            )
                        }
                    }

                }
                }

        }
        careerViewModel.getCareerCategoryObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            //setCategorySpinner(it, "category")
            response = it
            type_search = "category"
        }

        careerViewModel.getCareerPathwayObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
           // setCategorySpinner(it, "pathway")
            response = it
            type_search = "pathway"
        }
        careerViewModel.getCareerSearchObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            if (it.size > 0) {
                mBinding.list.visibility = View.VISIBLE
                mBinding.searchLay.visibility = View.GONE
                for (item in arrayTopPick) {
                    for (i in it) {
                        if (item.code == i.onetId) {
                            i.isfav = true
                            i.nid = item.nid
                        }
                        type_search?.let {
                            i.search_type = it
                        }
                    }
                }

                mBinding.listProgram.adapter =
                    SearchCareerAdapter(
                        requireContext(),
                        it,
                        ::clickCategorySearchLike,
                        ::itemClick,::openFactsheet
                    )
               // mBinding.listProgram.scrollToPosition(0)
            } else {
                mBinding.list.visibility = View.GONE
                mBinding.searchLay.visibility = View.VISIBLE
                context?.showToast(getString(R.string.no_data_found))
            }

            //setCategorySpinner(it)
        }
        careerViewModel.careerUsObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            if (it.data.size > 0) {
                mBinding.list.visibility = View.VISIBLE
                mBinding.searchLay.visibility = View.GONE
                arrayListUs = arrayListOf<CareerUSModel.Data?>()
                arrayListUs?.addAll(it.data)
                arrayListUs?.sortBy { it?.title }
                Log.e("Data", " " + arrayListUs?.get(0)?.title)
            } else {
                mBinding.list.visibility = View.GONE
                mBinding.searchLay.visibility = View.VISIBLE
                context?.showToast(getString(R.string.no_data_found))
            }
            mBinding.listProgram.adapter =
                UsCareerAdapter(requireContext(), arrayListUs!!)

        }
        careerViewModel.showError.observe(viewLifecycleOwner) {
            progress.dismiss()
        }
        careerViewModel.industryListObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            val list = ArrayList<String>()
            for (i in 0 until it.occupation.size) {
                list.add(it.occupation[i].code.toString())
            }
            progress.show()
            careerViewModel.getCareerClusterListDetail(list)
        }
        careerViewModel.careerClusterListObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            val list = ArrayList<String>()
            for (i in 0 until it.occupation.size) {
                list.add(it.occupation[i].code.toString())
            }
            progress.show()
            careerViewModel.getCareerClusterListDetail(list)
        }
        careerViewModel.careerClusterDetailObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            if (it.size > 0) {
                mBinding.list.visibility = View.VISIBLE
                mBinding.searchLay.visibility = View.GONE
                arrayListOut = arrayListOf<BrightOutlookModel.Data?>()
                arrayListOut?.addAll(it)
                arrayListOut?.sortBy { it?.title }
                Log.e("Data", " " + arrayListOut?.get(0)?.title)
            } else {
                mBinding.list.visibility = View.GONE
                mBinding.searchLay.visibility = View.VISIBLE
                context?.showToast(getString(R.string.no_data_found))
            }
            adapType = 1
            mBinding.listProgram.adapter =
                SearchProgramAdapter(requireContext(), null, arrayListOut!!, "", ::loadFragment)

        }
        careerViewModel.careerClusterObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            setClusterAdapter(it)
        }
        careerViewModel.brightOutObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            if (it.data.size > 0) {
                mBinding.list.visibility = View.VISIBLE
                mBinding.searchLay.visibility = View.GONE
                arrayListOut = arrayListOf<BrightOutlookModel.Data?>()
                arrayListOut?.addAll(it.data)
                arrayListOut?.sortBy { it?.title }
                Log.e("Data", " " + arrayListOut?.get(0)?.title)
            } else {
                mBinding.list.visibility = View.GONE
                mBinding.searchLay.visibility = View.VISIBLE
                context?.showToast(getString(R.string.no_data_found))
            }
            adapType = 1
            mBinding.listProgram.adapter =
                SearchProgramAdapter(requireContext(), null, arrayListOut!!, "", ::loadFragment)

        }
        careerViewModel.workObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            if (it.size() > 0) {
                mBinding.list.visibility = View.VISIBLE
                mBinding.searchLay.visibility = View.GONE
                arrayList = arrayListOf<CareerTopPickResponseItem?>()

                for (i in it.getAsJsonArray("data")) {
                    val itModel = gson.fromJson(i, CareerTopPickResponseItem::class.java)
                    arrayList?.add(itModel)
                }
                arrayList?.sortBy { it?.title }

                Log.e("Data", " " + arrayList?.get(0)?.title)
            } else {
                mBinding.list.visibility = View.GONE
                mBinding.searchLay.visibility = View.VISIBLE
                context?.showToast(getString(R.string.no_data_found))
            }
            adapType = 2
            mBinding.listProgram.adapter =
                SearchProgramAdapter(requireContext(), arrayList, null, "key", ::loadFragment)

        }
        careerViewModel.careerListObserver.observe(viewLifecycleOwner) {

            if (it.size() > 0) {
                arrayList = arrayListOf()

                for (i in it) {
                    val itModel = gson.fromJson(i, CareerTopPickResponseItem::class.java)
                    arrayList?.add(itModel)
                }
                arrayList?.sortBy { it?.title }
                adapType = 2
                mBinding.listProgram.adapter =
                    SearchProgramAdapter(requireContext(), arrayList, null, "key", ::loadFragment)


                // val listing = itModel.careerTopPickResponse
                Log.e("Data", " " + arrayList?.size)
            }
            progress.dismiss()
        }
        careerViewModel.careerKeyboardObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            val codeList = arrayListOf<String>()
            it.career?.let { it1 ->
                for (i in it1) {
                    i?.code?.let { it2 -> codeList.add(it2) }
                }
                if (codeList.size > 0) {
                    progress.show()
                    careerViewModel.getKeywoardSearchDetail(SEARCH_KEYWORD, codeList)
                }
            }
            if (it.career == null) {
                mBinding.list.visibility = View.GONE
                mBinding.searchLay.visibility = View.VISIBLE
                context?.showToast(getString(R.string.no_data_found))
            }


        }
        careerViewModel.careerKeyboardDetailObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            if (it.size() > 0) {
                mBinding.list.visibility = View.VISIBLE
                mBinding.searchLay.visibility = View.GONE
                arrayList = arrayListOf<CareerTopPickResponseItem?>()

                for (i in it) {
                    val itModel = gson.fromJson(i, CareerTopPickResponseItem::class.java)
                    arrayList?.add(itModel)
                }
                arrayList?.sortBy { it?.title }

                Log.e("Data", " " + arrayList?.get(0)?.title)
            } else {
                mBinding.list.visibility = View.GONE
                mBinding.searchLay.visibility = View.VISIBLE
                context?.showToast(getString(R.string.no_data_found))
            }
            adapType = 2
            mBinding.listProgram.adapter =
                SearchProgramAdapter(requireContext(), arrayList, null, "key", ::loadFragment)

        }
    }

    private fun itemClick(url: String?, title: String?) {
        if (url != null && title != null)
            bottomSheetDetail(title, url)
    }


    private fun loadFragment(position: Int) {
        if (type != "trafic") {
            val fragment = TraficFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            val bundle = Bundle()
            bundle.putSerializable("data", arrayList?.get(position))
            fragment.arguments = bundle
            transaction.add(R.id.nav_host_fragment_content_dashboard, fragment)
            transaction.addToBackStack("name")
            transaction.commit()
        } else if(type==""){
            val fragment = SearchDetailFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            val bundle = Bundle()
            bundle.putSerializable("data", arrayList?.get(position))
            bundle.putString("title", response?.get(position)?.name)
            fragment.arguments = bundle
            transaction.add(R.id.nav_host_fragment_content_dashboard, fragment)
            transaction.addToBackStack("name")
            transaction.commit()
        }
//        (requireContext() as CareerActivity).dialog
//        dialog = BottomSheetDialog(requireContext())
//        val view = layoutInflater.inflate(R.layout.compare_careers, null)
////        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
//
//        val listing = view.findViewById<RecyclerView>(R.id.listing)
//        val layout = view.findViewById<ConstraintLayout>(R.id.layout)
//        val close = view.findViewById<RelativeLayout>(R.id.close)
//        DrawableCompat.setTint(layout.background, Color.parseColor("#E5E5E5"))
//
//        listing.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
//        listing.adapter = CareerCompareAdapter(requireContext())
//        close.setOnClickListener {
//            dialog?.dismiss()
//        }
//
//        dialog?.setContentView(view)
//        dialog?.show()
    }

    private fun bottomSheetDetail(name: String, url: String) {

        val dialog = BottomSheetDialog(requireContext())

        val sheetBinding: SearchDetailBinding = SearchDetailBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.name.text = name
        getLifecycle().addObserver(sheetBinding.youtubePlayerView);
        val videoId = extractYoutubeId(url)
        sheetBinding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                videoId?.let { youTubePlayer.cueVideo(it, 0f) }
            }
        })
        dialog.show()


        sheetBinding.close.setOnClickListener {
            dialog?.dismiss()
        }
        sheetBinding.saveBtn.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun bottomSheetCompareSearch() {
        var onet_code = ArrayList<String>()
        var compareList = ArrayList<BrightOutlookModel.Data>()
        compareList.clear()
        onet_code.clear()

        if (arrayListOut != null) {
            for (i in arrayListOut?.indices!!) {
                if (arrayListOut?.get(i)?.selected == true) {
                    arrayListOut?.get(i)?.ccode?.let {
                        onet_code.add(it)
                        compareList.add(arrayListOut?.get(i)!!)
                    }

                }
            }
        }

        if (compareList.size < 1) {
            Toast.makeText(activity, "Please select careers first", Toast.LENGTH_SHORT).show()
            return
        }

        dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.compare_careers, null)
//        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        val listing = view.findViewById<RecyclerView>(R.id.listing)
        val layout = view.findViewById<ConstraintLayout>(R.id.layout)
        val close = view.findViewById<RelativeLayout>(R.id.close)
        DrawableCompat.setTint(layout.background, resources.getColor(R.color.white_1))

        listing.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        close.setOnClickListener {
            dialog?.dismiss()
        }
        val body: CompareCareerBody = CompareCareerBody()
        body.onet_code = onet_code
        dialog?.show()
        progress.show()
        careerViewModel.compareCareers(body)

        careerViewModel.careerComparisonsObserver.observe(viewLifecycleOwner) {
            Log.e("DATA", "" + it.toString())
            progress.dismiss()
            val gson = Gson()
            val json = gson.toJson(it)
            val resp = JSONObject(json)
            listing.adapter = CareerCompareAdapter(requireContext(), compareList, resp)
            (listing.adapter as CareerCompareAdapter).notifyDataSetChanged()
        }
        dialog?.setContentView(view)
        dialog?.setOnDismissListener {
            if (view.parent != null) {
                val parentViewGroup = view.parent as ViewGroup?
                parentViewGroup?.removeAllViews();
            }
        }
    }

    private fun bottomSheetCompareList() {
        var onet_code = ArrayList<String>()
        var compareList = ArrayList<CareerTopPickResponseItem>()
        var compareListNew = ArrayList<BrightOutlookModel.Data>()
        compareList.clear()
        onet_code.clear()
        compareListNew.clear()
        if (arrayList != null) {
            for (i in arrayList?.indices!!) {
                if (arrayList?.get(i)?.selected == true) {
                    arrayList?.get(i)?.ccode?.let {
                        onet_code.add(it)
                        compareList.add(arrayList?.get(i)!!)
                    }

                }
            }
        }

        if (compareList.size < 1) {
            Toast.makeText(activity, "Please select careers first", Toast.LENGTH_SHORT).show()
            return
        }

        dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.compare_careers, null)
//        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        val listing = view.findViewById<RecyclerView>(R.id.listing)
        val layout = view.findViewById<ConstraintLayout>(R.id.layout)
        val close = view.findViewById<RelativeLayout>(R.id.close)
        DrawableCompat.setTint(layout.background, resources.getColor(R.color.white_1))

        listing.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        listing.setHasFixedSize(true)
        close.setOnClickListener {
            dialog?.dismiss()
        }
        dialog?.show()
        val body: CompareCareerBody = CompareCareerBody()
        body.onet_code = onet_code
        progress.show()
        careerViewModel.compareCareers(body)

        careerViewModel.careerComparisonsObserver.observe(viewLifecycleOwner) {
            Log.e("DATA", "" + it.toString())
            progress.dismiss()
            val gson = Gson()
            val json = gson.toJson(it)
            val resp = JSONObject(json)
            for (item in compareList) {
                val model = BrightOutlookModel.Data(
                    item.ccode,
                    item.title,
                    item.education as ArrayList<String>,
                    item.salary,
                    item.brightOutlook as ArrayList<String>,
                    item.nid,
                    item.selected
                )
                compareListNew.add(model)
            }

            listing.adapter = CareerCompareAdapter(requireContext(), compareListNew, resp)


        }
        dialog?.setContentView(view)
        dialog?.setOnDismissListener {
            if (view.parent != null) {
                val parentViewGroup = view.parent as ViewGroup?
                parentViewGroup?.removeAllViews();
            }
        }
    }

    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

    fun clickCategorySearchLike(
        code: String?,
        title: String?, type: Boolean?
    ) {
        SharedHelper(requireContext()).id?.let {
            code?.let { it1 ->
                if (title != null) {
                    progress.show()
                    if (type == true) {
                        careerViewModel.addFavCareer(it1, it, title)
                    } else {
                        val deleteContent = DeleteContent()
                        deleteContent.nid = code
                        careerViewModel.unLikeWork(deleteContent)
                    }
                }
            }
        }


    }


    fun clickCategoryItem(careerCategoryResponseItem: CareerCategoryResponseItem) {

    }
    fun openFactsheet(careerSearchResponseItem: CareerSearchResponseItem, position: Int) {
        val fragment = SearchDetailFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putSerializable("data", careerSearchResponseItem)
        bundle.putString("title",careerSearchResponseItem.title)
        fragment.arguments = bundle
        transaction.add(R.id.nav_host_fragment_content_dashboard, fragment)
        transaction.addToBackStack("name")
        transaction.commit()
    }
    fun clickMilitaryService(industryModel: IndustryModel, type: String) {
        if (type == "us") {
            military = industryModel.id.toString()
           usMilitaryBinding?.dismiss()
            mBinding.outSpinnerText1.setText(industryModel.name)
//            progress.show()
//            careerViewModel.getUSSearch(industryModel.id.toString().getUSIndustry("1"))
        } else if (type == "cluster"){
            mBinding.outSpinnerText2.setText(industryModel.name)
            cluster = industryModel.id.toString()
         clusterBinding?.dismiss()
//            progress.show()
//           careerViewModel.getUSSearch(industryModel.id.toString().getUSIndustryCluster("1"))
        }


//                if (position > 0) {
//                    careerViewModel.getUSSearch(list.get(position).id.toString().getUSIndustry("1"))
//                } else {
//                    careerViewModel.getUSSearch("1".getUSIndustryNoService())
//                }
    }
}




