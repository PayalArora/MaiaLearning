package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
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
import com.maialearning.model.*
import com.maialearning.ui.adapter.*
import com.maialearning.util.*
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.viewmodel.CareerViewModel
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList


class SearchCareerFragment(var type: String) : Fragment() {
    var dialog: BottomSheetDialog? = null
    var dialogCareerFilterBottomsheetBinding:BottomSheetDialog?= null
    var dialogResultBottomsheetBinding:BottomSheetDialog?= null
    private lateinit var mBinding: SearchCareerLayBinding
    private val careerViewModel: CareerViewModel by viewModel()
    private lateinit var progress: Dialog
    lateinit var gson: Gson
    private val listData: String
        get() = ("[{\"id\":\"72\",\"name\":\"Accommodation Food Services\"}" + ",{\"id\":\"56\",\"name\":\"Administrative Support Services\"}" + ",{\"id\":\"11\",\"name\":\"Agriculture Forestry Fishing Hinting\"}" +
                ",{\"id\":\"71\",\"name\":\"Arts Entertainment Recreation\"}" + ",{\"id\":\"23\",\"name\":\"Construction\"}" + ",{\"id\":\"61\",\"name\":\"Educational Services\"}" + ",{\"id\":\"52\",\"name\":\"Finance Insurance\"}" +
                ",{\"id\":\"93\",\"name\":\"Government\"}" + ",{\"id\":\"62\",\"name\":\"Health Care Social Assistance\"}" + ",{\"id\":\"51\",\"name\":\"Information\"}" + ",{\"id\":\"55\",\"name\":\"Management Companies Enterprise\"}" +
                ",{\"id\":\"31\",\"name\":\"Manufacturing\"}" + ",{\"id\":\"21\",\"name\":\"Mining Quarrying Iol Gas Extraction\"}" + ",{\"id\":\"81\",\"name\":\"Other Services Exc Pub Administration\"}" + ",{\"id\":\"54\",\"name\":\"Professional Scientific Technical Service\"}" +
                ",{\"id\":\"53\",\"name\":\"Real Estate Rental Leasing\"}" + ",{\"id\":\"44\",\"name\":\"Real Trade\"}" + ",{\"id\":\"94\",\"name\":\"Self Employed\"}" + ",{\"id\":\"48\",\"name\":\"Transportation Warehousing\"}" + ",{\"id\":\"22\",\"name\":\"Utilities\"}" +
                ",{\"id\":\"42\",\"name\":\"Wholesale Trade\"}]")

    private val usData: String
        get() = ("[{\"key\":\"1\",\"name\":\"Army\"}" + ",{\"key\":\"2\",\"name\":\"Marine Corps\"}" + ",{\"key\":\"3\",\"name\":\"Air Force\"}" + ",{\"key\":\"4\",\"name\":\"Navy\"}" + ",{\"key\":\"5\",\"name\":\"Coast Guard\"}]")
    private lateinit var tableLayout: TabLayout
    private var adapType: Int = 1
    var arrayTopPick: ArrayList<RelatedCareerModel> = arrayListOf()
    var type_search:String?= null
    var response:ArrayList<CareerCategoryResponseItem>? = null

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
                    "30"
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
    private fun showSearchResultBottomSheet(type: String, resp: ArrayList<CareerCategoryResponseItem>) {
        dialogResultBottomsheetBinding = BottomSheetDialog(requireContext())

        val sheetBinding: CareerFilterBottomsheetBinding = CareerFilterBottomsheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialogResultBottomsheetBinding?.setContentView(sheetBinding.root)
        dialogResultBottomsheetBinding?.show()
        if (type == "Search by Keyword")
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
                "30"
            )
        }
    }

    private fun clickMainDropdown(selectedItem: String) {
        mBinding.outSpinner.visibility = View.GONE
        mBinding.outSpinnerText.visibility = View.GONE
            mBinding.text2.visibility = View.GONE
        dialogCareerFilterBottomsheetBinding?.dismiss()
        mBinding.spinner.text = selectedItem
            if (selectedItem == "Search by Category") {
                mBinding.outSpinnerText.visibility = View.VISIBLE
                progress.show()
                careerViewModel.getCareerCategories()
                mBinding.outSpinnerText.text = getString(R.string.select_category)
            } else if (selectedItem == "Search by Pathway") {
                mBinding.outSpinnerText.visibility = View.VISIBLE
                progress.show()
                careerViewModel.getCareerPathways()
                mBinding.outSpinnerText.text = getString(R.string.select_pathway)
            } else if (selectedItem == "Search by Keyword") {
                mBinding.outSpinner.visibility = View.GONE
                mBinding.outSpinnerText.visibility = View.GONE
                mBinding.text2.visibility = View.VISIBLE
                // progress.show()
                //careerViewModel.getCareerPathways()
            } else if (selectedItem == "Search by U.S. Military") {
                mBinding.workLayout.visibility = View.VISIBLE
                mBinding.three.visibility = View.GONE
                mBinding.two.visibility = View.INVISIBLE
                mBinding.spinnerLay1.visibility = View.VISIBLE
                mBinding.outSpinner.visibility = View.VISIBLE
                setUsSpinner()
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
//            if (selectedItem == "Search by Category") {
//                progress.show()
//                careerViewModel.getCareerCategories()
//            } else if (selectedItem == "Search by Pathway") {
//                progress.show()
//                careerViewModel.getCareerPathways()
//            } else if (selectedItem == "Search by Keyword") {
//                mBinding.outSpinner.visibility = View.GONE
//                mBinding.text2.visibility = View.VISIBLE
//                // progress.show()
//                //careerViewModel.getCareerPathways()
//            } else if (selectedItem == "Search by U.S. Military") {
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

    private fun setUsSpinner() {
        val list = ArrayList<IndustryModel>()
        val jsonArry = JSONArray(usData)
        for (i in 0 until jsonArry.length()) {
            val obj = jsonArry.getJSONObject(i)
            val user = IndustryModel(obj.getInt("key"), obj.getString("name"))
            list.add(user)
        }
        mBinding.outSpinner.visibility = View.VISIBLE
        val adapter = IndustryClusterAdapter(requireContext(), list, ::clickUSItem)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        mBinding.outSpinner.setAdapter(
            NothingSelectedSpinnerAdapter(
                adapter,
                R.layout.nothing_adapter_us_military,  // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                requireContext()
            )
        )
//        val adapter = ArrayAdapter(
//            requireContext(),
//            R.layout.spinner_text, list
//        )
        // mBinding.outSpinner.adapter = adapter
        mBinding.outSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                progress.show()
                if (position > 0) {
                    careerViewModel.getUSSearch(list.get(position).id.toString().getUSIndustry("1"))
                } else {
                    careerViewModel.getUSSearch("1".getUSIndustryNoService())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

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

    private fun setIndustrySpinner() {
        mBinding.workLayout.visibility = View.GONE
        mBinding.spinnerLay1.visibility = View.VISIBLE
        val list = ArrayList<IndustryModel>()
        val jsonArry = JSONArray(listData)
        for (i in 0 until jsonArry.length()) {
            val obj = jsonArry.getJSONObject(i)
            val user = IndustryModel(obj.getInt("id"), obj.getString("name"))
            list.add(user)
        }
        mBinding.text2.visibility = View.GONE
        mBinding.outSpinner.visibility = View.VISIBLE
        val adapter = IndustryClusterAdapter(requireContext(), list, ::clickIndustryItem)
        mBinding.outSpinner.adapter = adapter
        mBinding.outSpinner.setSelection(0)

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
                        "30"
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
            arrayTopPick = arrayListOf()
            if (it != null) {
                for (i in it) {
                    val toppick = gson.fromJson(i, RelatedCareerModel::class.java)
                    arrayTopPick.add(toppick)
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
                    }
                }
                mBinding.listProgram.adapter =
                    SearchCareerAdapter(
                        requireContext(),
                        it,
                        ::clickCategorySearchLike,
                        ::itemClick,::openFactsheet
                    )
                mBinding.listProgram.scrollToPosition(0)
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
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(sheetBinding.videoView)
        mediaController.setMediaPlayer(sheetBinding.videoView)
        sheetBinding.videoView.setVideoURI(Uri.parse(url))
        sheetBinding.videoView.requestFocus();//give focus to a specific view

        sheetBinding.videoView.start()
        sheetBinding.videoView.setMediaController(mediaController)
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
}


