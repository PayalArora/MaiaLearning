package com.maialearning.ui.activity

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.GsonBuilder
import com.maialearning.R
import com.maialearning.databinding.*
import com.maialearning.model.*
import com.maialearning.network.BaseApplication
import com.maialearning.ui.adapter.*
import com.maialearning.ui.bottomsheets.ProfileFilter
import com.maialearning.ui.bottomsheets.SheetUniversityFilter
import com.maialearning.util.UNIV_LOGO_URL
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.replaceInvertedComas
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.FactSheetModel
import com.maialearning.viewmodel.HomeViewModel
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel


class UniversitiesActivity : FragmentActivity(), ClickFilters {
    private lateinit var binding: ActivityUniversitiesBinding
    private lateinit var toolbarBinding: Toolbar
    var dialog: BottomSheetDialog? = null
    var mainDialog: BottomSheetDialog? = null
    var dialogFacts: BottomSheetDialog? = null
    var model: CollegeFactSheetModel? = null
    var modelOther: FactsheetModelOther? = null
    private val mModel: FactSheetModel by viewModel()
    private lateinit var dialogP: Dialog
    private lateinit var universityName: TextView
    private lateinit var noData: TextView
    var listUni = ArrayList<UniersitiesListModel>()
    private val countries: ArrayList<FilterUSModelClass.CountryList> = ArrayList()
    private val region: ArrayList<String> = ArrayList()
    private val sportList: ArrayList<String> = ArrayList()
    private val selectivity: ArrayList<String> = ArrayList()
    private var savedCountry=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUniversitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbarBinding = binding.toolbar
        binding.toolbar.contentInsetStartWithNavigation = 0
        binding.toolbar.setNavigationIcon(getDrawable(R.drawable.ic_baseline_keyboard_arrow_left_24))
        binding.toolbar.title = getString(R.string.universities)
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.GONE
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility = View.VISIBLE
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_arrow).visibility = View.GONE
        toolbarBinding.setNavigationOnClickListener {
            finish()
        }
        initView()
        binding.toolbarProf.setOnClickListener {
            ProfileFilter(this, layoutInflater).showDialog()
        }
        mModel.getSaveCountry()
        observerInit()

    }



    private fun initView() {
        val tabArray = arrayOf(
            getString(R.string.search),
            getString(R.string.considering),
            getString(R.string.applying),
            getString(R.string.milestone),
            getString(R.string.recommendations),
            getString(R.string.decisions),
            getString(R.string.essays),
            getString(R.string.scholarships)
        )
        for (item in tabArray) {
            binding.tabs.addTab(binding.tabs.newTab().setText(item))

        }
        val fm: FragmentManager = supportFragmentManager
        val adapter = ViewStateAdapter(fm, lifecycle, binding.tabs, tabArray.size)

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.setText(tabArray[position])
        }.attach()

        toolbarBinding.findViewById<ImageView>(R.id.toolbar_arrow).apply {
            setOnClickListener {
                bottomSheetList()
//                bottomSheetWork()
            }
        }

        binding.addFab.setOnClickListener {
            bottomSheetAddUniv()

        }
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).setOnClickListener {
            univFilter()
        }
        binding.tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (binding.tabs.selectedTabPosition == 5) {
                    binding.addFab.visibility = View.GONE
                } else
                    binding.addFab.visibility = View.VISIBLE
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {
                if (binding.tabs.selectedTabPosition == 0) {


                }
            }
        })

//        TabLayoutMediator(mBinding.tabs, mBinding.viewPager) { tab, position ->
//        }.attach()
    }

    private fun bottomSheetList() {
        dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.uni_list_bottom, null)
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        val listing = view.findViewById<RecyclerView>(R.id.listing)
        val layout = view.findViewById<ConstraintLayout>(R.id.layout)
        DrawableCompat.setTint(layout.background, Color.parseColor("#E5E5E5"))

        listing.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var arrayList = arrayListOf<UniversitiesSearchModel>()
        //listing.adapter = UniFactAdapter(this, arrayList, ::click)
//        close.setOnClickListener {
//            dialog.dismiss()
//        }

        dialog?.setContentView(view)
        dialog?.show()
    }

    fun click() {}
    public fun bottomSheetWork(get: UniversitiesSearchModel) {
        dialogFacts = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.layout_uni_factsheets, null)
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        val factTabs = view.findViewById<TabLayout>(R.id.fact_tabs)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val close = view.findViewById<ImageView>(R.id.close)
        val imageUniv = view.findViewById<ImageView>(R.id.image)
        var tabArray: Array<String>
        if ((SharedHelper(this).country ?: "US") == "US") {
            tabArray = arrayOf(
                getString(R.string.overview),
                getString(R.string.community),
                getString(R.string.admission),
                getString(R.string.cost_),
                getString(R.string.degree),
                getString(R.string.var_sport),
                getString(R.string.transfer),
                getString(R.string.notes),
                getString(R.string.campus_service)
            )
        } else {
            tabArray = arrayOf(
                getString(R.string.overview),
                "Program List",
                getString(R.string.admission),
                getString(R.string.campus_service)
            )
        }
        for (item in tabArray) {
            factTabs.addTab(factTabs.newTab().setText(item))
        }
        close.setOnClickListener {
            dialogFacts?.dismiss()
        }
        val fm: FragmentManager = supportFragmentManager
        val adapter = ViewStateFactAdapter(fm, lifecycle, tabArray.size, this)
        viewPager.adapter = adapter
        TabLayoutMediator(factTabs, viewPager) { tab, position ->
            tab.text = tabArray[position]
        }.attach()
        val like = view.findViewById<ImageView>(R.id.like)
        universityName = view.findViewById<TextView>(R.id.university)
        universityName.setText(get.collegeName)
        noData = view.findViewById<TextView>(R.id.no_data)
        factTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (factTabs.selectedTabPosition != 0)
                    noData.visibility = View.GONE
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        }
        )
        like.setOnClickListener {
            likeClick()
        }
        dialogFacts?.setContentView(view)
        dialogP = showLoadingDialog(this)
        dialogP.show()
        if ((SharedHelper(this).country ?: "US").equals("US")) {
            mModel.getColFactSheet(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                get.unitid.toString()
            )
        } else {
            mModel.getColFactSheetOther(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                get.nid.toString()
            )
        }
        Picasso.with(this)
            .load("${UNIV_LOGO_URL}${get.countryCode?.toLowerCase()}/${get.unitid}/logo_sm.jpg")
            .error(R.drawable.static_coll).into(imageUniv)

        //  val likePic = view.findViewById<ImageView>(R.id.like)

        if (get.topPickFlag == 1) {
            like.setImageResource(R.drawable.heart_filled)
        } else {
            like.setImageResource(R.drawable.like)
        }

//        likePic.setOnClickListener {
//            if (get.topPickFlag == 1) {
//                hitUnlikeWork(get)
//                likePic.setImageResource(R.drawable.heart_filled)
//            } else {
//                hitLikeWork(get)
//                likePic.setImageResource(R.drawable.like)
//            }
//        }
//        homeModel.unlikeObserver.observe(this) {
//            dialogP.dismiss()
////            hitAPI(page)
//        }
//        homeModel.showError.observe(this) {
//            dialogP.dismiss()
//            Log.e("Error", "err" + it)
//        }
    }

    private val homeModel: HomeViewModel by viewModel()

    private fun hitLikeWork(get: UniversitiesSearchModel) {
        dialogP.show()
        SharedHelper(this).id?.let {
            get.nid?.let { it1 ->
                homeModel.hitlike(
                    it,
                    it1
                )
            }
        }
    }

    private fun hitUnlikeWork(get: UniversitiesSearchModel) {
        dialogP.show()
        SharedHelper(this).id?.let {
            get.nid?.let { it1 ->
                homeModel.hitunlike(
                    it,
                    it1
                )
            }
        }
    }


    private fun bottomSheetAddUniv() {
        val dialog = BottomSheetDialog(this)
        val sheetBinding: LayoutUniversityBinding = LayoutUniversityBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.close.setOnClickListener {
            refreshTab()
            dialog.dismiss()
        }

        sheetBinding.reciepentList.adapter = AddUniversiityAdapter(this)
        sheetBinding.save.setOnClickListener {
            refreshTab()
            dialog.dismiss()
        }
        sheetBinding.country.setOnClickListener {
            if (countries.size > 0) {
                SheetUniversityFilter(this, layoutInflater).showDialog(countries, this, sheetBinding.flagImg)
            } else {
                dialogP = showLoadingDialog(this)
                dialogP.show()
                mModel.getFilterCollege()
            }
        }
    }

    private fun univFilter() {
        mainDialog = BottomSheetDialog(this)

        val sheetBinding: UniversityFilterBinding = UniversityFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        mainDialog?.setContentView(sheetBinding.root)
        sheetBinding.search.visibility = View.GONE
        sheetBinding.filters.setText(resources.getString(R.string.filters))
        dialogP = showLoadingDialog(this)
        dialogP.show()
        mModel.getFilterCollege()
        mainDialog?.show()
        sheetBinding.clearText.setOnClickListener {
            if(savedCountry!=SharedHelper(this).country){
                dialogP = showLoadingDialog(this)
                dialogP.show()
                mModel.setSaveCountry()
            }
            mainDialog?.dismiss()
        }
        sheetBinding.backBtn.setOnClickListener {
            if(savedCountry!=SharedHelper(this).country){
                dialogP = showLoadingDialog(this)
                dialogP.show()
                mModel.setSaveCountry()
            }
            mainDialog?.dismiss()
        }
        sheetBinding.reciepentList.adapter =
            UnivFilterAdapter(resources.getStringArray(R.array.UnivFilters), this)

    }

    private fun countryFilter( flagImg:ImageView) {
        SheetUniversityFilter(this, layoutInflater).showDialog(
            countries,
            this,
           flagImg
        )
    }

    override fun onClick(positiion: Int, type: Int, flagImg:ImageView?) {
        if (type == 2) {
            if (positiion == 0) {
                if (countries.size > 0) {
                    flagImg?.let { countryFilter(it) }
                } else {
                    dialogP = showLoadingDialog(this)
                    dialogP.show()
                    mModel.getFilterCollege()
                }
            } else if (positiion == 1) {
                SheetUniversityFilter(this, layoutInflater).regionFilter(region,
                    View.VISIBLE,
                    resources.getString(R.string.reigon),
                    positiion
                )
            } else if (positiion == 2) {
                if (listUni.size > 0) {
                    SheetUniversityFilter(this, layoutInflater).selectRegionFilter(
                        View.VISIBLE,
                        resources.getString(R.string.list),
                        listUni, View.GONE
                    )

                } else {
                    dialogP = showLoadingDialog(this)
                    dialogP.show()
                    mModel.getUniversityList("Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey)

                }
            } else if (positiion == 3) {
                typeFilter()
            } else if (positiion == 4) {
                SheetUniversityFilter(this, layoutInflater).regionFilter(selectivity,
                    View.GONE,
                    resources.getString(R.string.selectivity),
                    positiion
                )
            } else if (positiion == 5) {
                SheetUniversityFilter(this, layoutInflater).regionFilter(region,
                    View.VISIBLE,
                    resources.getString(R.string.programs),
                    positiion
                )
            } else if (positiion == 6) {
                SheetUniversityFilter(this, layoutInflater).regionFilter(sportList,
                    View.GONE,
                    resources.getString(R.string.sports),
                    positiion, View.VISIBLE
                )
            } else if (positiion == 7) {
                moreFilter()
            }
        }

    }


    private fun moreFilter() {
        val dialog = BottomSheetDialog(this)
        val sheetBinding: MoreSheetBinding = MoreSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.filters.setText(getString(R.string.more))
        sheetBinding.backTxt.setOnClickListener { dialog.dismiss() }
        dialog.show()
        sheetBinding.clearText.setOnClickListener { dialog.dismiss() }
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.capmpus_activity,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //sheetBinding.spinner.setPrompt("Campus Activities")

        sheetBinding.spinner.setAdapter(
            NothingSelectedSpinnerAdapter(
                adapter,
                R.layout.nothing_adapter,  // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                this
            )
        )
        sheetBinding.campusActivity.setOnClickListener { sheetBinding.spinner.performClick() }


    }

    private fun typeFilter() {
        val dialog = BottomSheetDialog(this)
        val sheetBinding: TypeSheetBinding = TypeSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.filters.setText(getString(R.string.type))
        dialog.show()
        sheetBinding.clearText.setOnClickListener { dialog.dismiss() }
        sheetBinding.backTxt.setOnClickListener { dialog.dismiss() }
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.religious_affilation,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sheetBinding.spinner.setAdapter(
            NothingSelectedSpinnerAdapter(
                adapter,
                R.layout.nothing_adapter,  // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                this
            )
        )
        sheetBinding.religous.setOnClickListener { sheetBinding.spinner.performClick() }


    }

    fun refreshTab() {

        binding.tabs.selectTab(binding.tabs.getTabAt(0))

    }

    private fun likeClick() {
        binding.tabs.selectTab(binding.tabs.getTabAt(1))
        dialogFacts?.let { it.dismiss() }
        dialog?.let { it.dismiss() }
    }

    private fun loadData(model: CollegeFactSheetModel) {
        this.model = model
    }

    private fun loadDataOther(model: FactsheetModelOther) {
        this.modelOther = model
    }

    fun getDataOther(): FactsheetModelOther? {
        return modelOther
    }

    fun getData(): CollegeFactSheetModel? {

        return model
    }
    private fun observerInit() {

        mModel.getSaveCountryObserver.observe(this) {
            dialogP.dismiss()
            initView()
        }
        mModel.saveCountryObserver.observe(this) {
        if(it.get(0).toString() !=null ||  it.get(0).toString() !=""){
            savedCountry=it.get(0).toString().replaceInvertedComas().replace("\\","")
            SharedHelper(this).country = savedCountry
        }
        }
        mModel.countryFilterObserver.observe(this) {
            dialogP.dismiss()
            countries.clear()
            sportList.clear()
            region.clear()
            selectivity.clear()
            val json = JSONObject(it.toString()).getJSONObject("country_list")
            val jsonRegion = JSONObject(it.toString()).getJSONObject("region")
            val jsonSports = JSONObject(it.toString()).getJSONObject("sport_list")
            val jsonSelectivity = JSONObject(it.toString()).getJSONObject("selectivity")
            val keyList = ArrayList<String>()
            val jsonArray = JSONArray()
            val jsonArraySports = JSONArray()
            val jsonArraySelect = JSONArray()
            val x = json.keys() as Iterator<String>
            val xSports = jsonSports.keys() as Iterator<String>
            val xRegion = jsonRegion.keys() as Iterator<String>
            val xSelect = jsonSelectivity.keys() as Iterator<String>
            val jsonArrayRegion = JSONArray()
            while (xRegion.hasNext()) {
                val key: String = xRegion.next()
                jsonArrayRegion.put(jsonRegion.get(key))
            }
            while (xSports.hasNext()) {
                val key: String = xSports.next()
                jsonArraySports.put(jsonSports.get(key))

            }
            while (xSelect.hasNext()) {
                val key: String = xSelect.next()
                jsonArraySelect.put(jsonSelectivity.get(key))

            }
            while (x.hasNext()) {
                val key: String = x.next()
                jsonArray.put(json.get(key))
                keyList.add(key)
            }
            for (i in 0 until jsonArray.length()) {
                countries.add(
                    FilterUSModelClass.CountryList(
                        keyList[i], jsonArray.get(i).toString(),false
                    )
                )
            }
            for (i in 0 until jsonArrayRegion.length()) {
                region.add(jsonArrayRegion.get(i).toString())
            }
            for (i in 0 until jsonArraySports.length()) {
                sportList.add(jsonArraySports.get(i).toString())
            }
            for (i in 0 until jsonArraySelect.length()) {
                selectivity.add(jsonArraySelect.get(i).toString())
            }
        }
        mModel.factSheetOtherObserver.observe(this) {
            dialogP.dismiss()
            loadDataOther(it)
            dialogFacts?.show()
        }


        mModel.factSheetObserver.observe(this) {
            dialogP.dismiss()
            val gson = GsonBuilder().create()
            val itModel = gson.fromJson(it, CollegeFactSheetModel::class.java)

            val array: ArrayList<CollegeFactSheetModel.DegreeMajors1.Majors1> = ArrayList()
            val json =
                JSONObject(it.toString()).getJSONObject("degree_majors").getJSONObject("majors")
            val keyList = ArrayList<String>()
            val x = json.keys() as Iterator<String>
            val jsonArray = JSONArray()
            while (x.hasNext()) {
                val key: String = x.next().toString()
                jsonArray.put(json.get(key))
                keyList.add(key)
            }
            for (i in 0 until jsonArray.length()) {
                val objectProgram = jsonArray.getJSONObject(i)
                array.add(
                    CollegeFactSheetModel.DegreeMajors1.Majors1(
                        keyList[i], CollegeFactSheetModel.DegreeMajors1.Majors1.AnimalSciences(
                            objectProgram.getInt("Associate Degree"),
                            objectProgram.getInt("Master Degree"),
                            objectProgram.getInt("Bachelor Degree"),
                            objectProgram.getInt("Doctorate Degree"),
                            objectProgram.getInt("count"),
                            objectProgram.getString("description")
                        )
                    )
                )

            }
            val varArray: ArrayList<CollegeFactSheetModel.VarsityAthleticSports1.Teams1> =
                ArrayList()
            val jsonVar =
                JSONObject(it.toString()).getJSONObject("varsity_athletic_sports")
                    .getJSONObject("teams")
            val jsonServices =
                JSONObject(it.toString()).getJSONObject("services")
            val varList = ArrayList<String>()
            val x1 = jsonVar.keys() as Iterator<String>
            val jsonVarArray = JSONArray()
            while (x1.hasNext()) {
                val key: String = x1.next().toString()
                jsonVarArray.put(jsonVar.get(key))
                varList.add(key)
            }
            val serList = ArrayList<String>()
            val x2 = jsonServices.keys() as Iterator<String>
            val jsonSerArray = JSONArray()
            while (x2.hasNext()) {
                val key: String = x2.next().toString()
                jsonSerArray.put(jsonServices.get(key))
                serList.add(key)
            }

            for (i in 0 until jsonVarArray.length()) {
                val objectProgram = jsonVarArray.getJSONObject(i)
                varArray.add(
                    CollegeFactSheetModel.VarsityAthleticSports1.Teams1(
                        varList[i], CollegeFactSheetModel.VarsityAthleticSports1.Teams1.Baseball(
                            objectProgram.getString("men"),
                            objectProgram.getString("women")
                        )
                    )
                )
            }
            itModel.varsityAthleticSports1 = CollegeFactSheetModel.VarsityAthleticSports1(
                varArray,
                itModel.varsityAthleticSports.athleticAssociation
            )
            if (itModel.degreeMajors.programOffered != null) {
                itModel.degreeMajors1 =
                    CollegeFactSheetModel.DegreeMajors1(array, itModel.degreeMajors.programOffered)
                loadData(itModel)
                universityName.text = itModel.basicInfo.name
            } else {
                if (binding.tabs.selectedTabPosition == 0)
                    noData.visibility = View.VISIBLE
            }

            dialogFacts?.show()

        }
        mModel.showError.observe(this) {
            dialogP.dismiss()
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        mModel.listObserver.observe(this) {
            dialogP.dismiss()
            for (i in 0 until it.size()) {
                val objectProgram = it.get(i).asJsonObject
                listUni.add(
                    UniersitiesListModel(
                        objectProgram.getAsJsonPrimitive("id").toString().replace("\"", ""),
                        objectProgram.getAsJsonPrimitive("name").toString().replace("\"", ""),
                        "",
                        objectProgram.getAsJsonPrimitive("type").toString(),
                        objectProgram.getAsJsonPrimitive("created").toString(),
                        objectProgram.getAsJsonPrimitive("changed").toString(),
                        "",
                        objectProgram.getAsJsonPrimitive("status").toString(),
                        "",
                        objectProgram.getAsJsonPrimitive("district_scope").toString()
                    )
                )
            }
//            val sortedList: MutableList<UniersitiesListModel> =
//               listUni.groupBy { it.name }
//                    .values
//                    .map {
//                        it.reduce {
//                                acc, item -> UniersitiesListModel(item.name)
//                        }
//                    }.sortedWith(compareBy { it.name }).toMutableList()
            SheetUniversityFilter(this, layoutInflater).selectRegionFilter(
                View.VISIBLE,
                resources.getString(R.string.list),
                listUni, View.GONE
            )
        }
//        mModel.countryObserver.observe(this) {
//            Log.e("Response: ", " $it")
//            dialogP.dismiss()
//            val iter: Iterator<String> = it.keySet().iterator()
//            while (iter.hasNext()) {
//                val key = iter.next()
//                try {
//                    val countryData =
//                        CountryData(key, it.get(key).toString().replace("\"", ""), false)
//                    countries.add(countryData)
//                } catch (e: JSONException) {
//                    // Something went wrong!
//                }
//            }
//            if (countries.size > 0)
//                countryFilter()
////            if (profileResponse.info?.country != null) {
////                for (i in countries.indices) {
////                    if (profileResponse.info?.country == countries.get(i).name) {
////                        sheetBinding.spinner.setSelection(i)
////                    }
////                }
////            }
//        }
    }
}

interface ClickFilters {
    fun onClick(positiion: Int, type: Int, flagImg: ImageView?)
}

