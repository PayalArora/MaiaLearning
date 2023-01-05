package com.maialearning.ui.acti


import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
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
import com.maialearning.parser.SearchParser
import com.maialearning.ui.adapter.*
import com.maialearning.ui.bottomsheets.ProfileFilter
import com.maialearning.ui.bottomsheets.SheetUniversityFilter
import com.maialearning.ui.fragments.ConsideringFragment
import com.maialearning.ui.model.AthleticAsociations
import com.maialearning.ui.model.ChildrenItem
import com.maialearning.ui.model.ResponseItem
import com.maialearning.util.*
import com.maialearning.util.prefhandler.SharedHelper
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
    private lateinit var dialogUni: Dialog
    private lateinit var universityName: TextView
    private lateinit var noData: TextView
    var listUni = ArrayList<UniersitiesListModel>()
    var listStates = ArrayList<KeyVal>()
    private val countries: ArrayList<FilterUSModelClass.CountryList> = ArrayList()
    private val region: ArrayList<String> = ArrayList()
    private val sportList: ArrayList<KeyVal> = ArrayList()
    private val selectivity: ArrayList<KeyVal> = ArrayList()
    private val campusAcivities: ArrayList<KeyVal> = ArrayList()
    private val diversities: ArrayList<KeyVal> = ArrayList()
    private val twoFourYear: ArrayList<KeyVal> = ArrayList()
    private val publicPrivate: ArrayList<KeyVal> = ArrayList()
    private val typeEnvironMent: ArrayList<KeyVal> = ArrayList()
    private val religious: ArrayList<KeyVal> = ArrayList()
    private val sizeList: ArrayList<KeyVal> = ArrayList()
    private val disciplines: ArrayList<KeyVal> = ArrayList()
    private val subDisciplines: ArrayList<KeyVal> = ArrayList()
    private val germanSubject: ArrayList<KeyVal> = ArrayList()
    private val germanModeAdmission: ArrayList<KeyVal> = ArrayList()
    private val germanAdmissionSem: ArrayList<KeyVal> = ArrayList()
    private val germanInstLang: ArrayList<KeyVal> = ArrayList()
    private val germanStudyMode: ArrayList<KeyVal> = ArrayList()
    private val areaStudy: ArrayList<KeyVal> = ArrayList()
    private val fieldStudy: ArrayList<KeyVal> = ArrayList()
    private val gbUniversityList: ArrayList<KeyVal> = ArrayList()
    private val gbCollegeList: ArrayList<KeyVal> = ArrayList()
    private val areaGBStudy: ArrayList<KeyVal> = ArrayList()
    private val programGBStudy: ArrayList<KeyVal> = ArrayList()
    private val fieldOfStudyAny: ArrayList<KeyVal> = ArrayList()
    private val fieldOfStudyProgramAny: ArrayList<KeyVal> = ArrayList()
    private val fieldOfStudyMazorAny: ArrayList<KeyVal> = ArrayList()
    var anyFilterArray = arrayOf("Country", "University List", "Field of Study", "Program", "Major")
    var filterArrayEuropean = arrayOf("Country", "University List", "Discipline", "Sub Discipline")
    var filterArrayDE = arrayOf(
        "Country",
        "University List",
        "Subject Group",
        "Area of Study",
        "Field of Study",
        "Mode of admission",
        "Admission Semester",
        "Instruction Language"
    )

    private var savedCountry = ""
    private var selectedList = ""
    var selType = 0
    private val listData: String
        get() = (" [\n" +
                "        {\n" +
                "                key: 'CCCAA',\n" +
                "                value: 'CCCAA'\n" +
                "        },\n" +
                "        {\n" +
                "                key: 'Independent',\n" +
                "                value: 'Independent'\n" +
                "        },\n" +
                "        {\n" +
                "            key: 'NAIA',\n" +
                "            value: 'NAIA',\n" +
                "            children: [\n" +
                "            {\n" +
                "                    key: 'NAIA Division I',\n" +
                "                    value: 'Division I'\n" +
                "            },\n" +
                "            {\n" +
                "                    key: 'NAIA Division II',\n" +
                "                    value: 'Division II'\n" +
                "            }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            key: 'NCAA',\n" +
                "            value: 'NCAA',\n" +
                "            children: [\n" +
                "            {\n" +
                "                    key: 'NCAA Division I',\n" +
                "                    value: 'Division I'\n" +
                "            },\n" +
                "            {\n" +
                "                    key: 'NCAA Division II',\n" +
                "                    value: 'Division II'\n" +
                "            },\n" +
                "            {\n" +
                "                    key: 'NCAA Division III',\n" +
                "                    value: 'Division III'\n" +
                "            }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            key: 'NCCAA',\n" +
                "            value: 'NCCAA',\n" +
                "            children: [\n" +
                "            {\n" +
                "                    key: 'NCCAA Division I',\n" +
                "                    value: 'Division I'\n" +
                "            },\n" +
                "            {\n" +
                "                    key: 'NCCAA Division II',\n" +
                "                    value: 'Division II'\n" +
                "            }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            key: 'NJCAA',\n" +
                "            value: 'NJCAA',\n" +
                "            children: [\n" +
                "            {\n" +
                "                    key: 'NJCAA Division I',\n" +
                "                    value: 'Division I'\n" +
                "            },\n" +
                "            {\n" +
                "                    key: 'NJCAA Division II',\n" +
                "                    value: 'Division II'\n" +
                "            },\n" +
                "            {\n" +
                "                    key: 'NJCAA Division III',\n" +
                "                    value: 'Division III'\n" +
                "            }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "                key: 'NWAC',\n" +
                "                value: 'NWAC'\n" +
                "        },\n" +
                "        {\n" +
                "                key: 'Other',\n" +
                "                value: 'Other'\n" +
                "        },\n" +
                "        {\n" +
                "                key: 'USCAA',\n" +
                "                value: 'USCAA'\n" +
                "        }\n" +
                "    ]")
    var sheetBindingUniv: UniversityFilterBinding? = null
    var university_list: ArrayList<UniversitiesSearchModel?> = ArrayList()
    var university_list_new: ArrayList<UniversitiesSearchModel?> = ArrayList()
    var adapterAddUniv: AddUniversiityAdapter? = null
    var selectedAddUniv = false
    var page: Int = 1
    private var isLoading = false
    //  private var isEuropean = false


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
        binding.toolbarProf.setOnClickListener {
            ProfileFilter(this, layoutInflater).showDialog()
        }
        resetFilters()
        dialogP = showLoadingDialog(this)
        dialogP.show()
        mModel.getSaveCountry()
        observerInit()

    }

    fun parseJSON(): AthleticAsociations {
        val athleticAsociations = AthleticAsociations()
        val items: List<String> = ArrayList()
        val jsonArray = JSONArray(listData)
        val list: ArrayList<ResponseItem> = arrayListOf()

        for (i in 0 until jsonArray.length()) {
            var childArr: ArrayList<ChildrenItem>? = null
            val jarray: JSONArray? = jsonArray.getJSONObject(i).optJSONArray("children")
            jarray?.let {
                childArr = arrayListOf()
                for (i in 0 until jarray.length()) {
                    val child = ChildrenItem(
                        jarray.getJSONObject(i).optString("value"),
                        jarray.getJSONObject(i).optString("key")
                    )
                    childArr?.add(child)
                }
            }
            val res = ResponseItem(
                jsonArray.getJSONObject(i).optString("value"),
                jsonArray.getJSONObject(i).optString("key"),
                childArr
            )
            list.add(res)
            athleticAsociations.response = list
        }
        return athleticAsociations
    }

    private fun resetFilters() {
        selectedListFilter.clear()
        selectedRegion.clear()
        selectedStateFilter.clear()
        selectedSelectivityFilter.clear()
        selectedCampusActivity = ""
        selectedSports = ""
        selectedDiversity = ""
        sat_erbw = ""
        sat_math = ""
        act = ""
        selectedTwoFour.clear()
        selectedPublicPrivate.clear()
        selectedTypeEnv.clear()
        selectedSize.clear()
        selectedReligious = ""
        selectedAthleticAsociations.clear()
        selectedDiscipline = ""
        selectedSubDiscipline.clear()
        selectedGermanSubject.clear()
        selectedAreaStudy.clear()
        selectedFieldSubject.clear()
        selectedModeAdmission = ""
        selectedModeStudy = ""
        selectedAdmissionSem = ""
        selectedInstructionLanguage = ""
        areaStudy.clear()
        fieldStudy.clear()
        selectedGbUniversity.clear()
        selectedFOS = ""
        selectedProgramAny = ""
        selectedMazorAny = ""
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
            }
        }

        binding.addFab.setOnClickListener {
            collegeList?.let {
                bottomSheetAddUniv()
            }

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
    public fun bottomSheetWork(
        get: UniversitiesSearchModel,
        position: Int,
        click: (position: Int, flag: Int?) -> Unit
    ) {
        dialogFacts = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.layout_uni_factsheets, null)
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        val factTabs = view.findViewById<TabLayout>(R.id.fact_tabs)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val close = view.findViewById<ImageView>(R.id.close)
        val imageUniv = view.findViewById<ImageView>(R.id.image)
        val tabArray: Array<String>
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
                getString(R.string.college_info),
                getString(R.string.prog_list),
                getString(R.string.admission),
                getString(R.string.notes)
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
            if (get.topPickFlag ?: 0 == 0) {
                get.topPickFlag = 1
                like.setImageResource(R.drawable.heart_filled)
                hitLikeWork(get.nid)
                click(position, get.topPickFlag)
            } else {
                get.topPickFlag = 0
                like.setImageResource(R.drawable.like)
                hitUnlikeWork(get.nid)
                click(position, get.topPickFlag)
            }
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
        dialogFacts?.setOnDismissListener {

        }
    }

    fun bottomSheetUk(
        collegeNid: String,
        topflag: Int?,
        collegeName: String?,
        position: Int,
        data: UkResponseModel.Data.CollegeData?,
        click: (position: Int, flag: Int?) -> Unit
    ) {
        var toppick = topflag
        dialogFacts = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.layout_uni_factsheets, null)
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        val factTabs = view.findViewById<TabLayout>(R.id.fact_tabs)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val close = view.findViewById<ImageView>(R.id.close)
        val imageUniv = view.findViewById<ImageView>(R.id.image)
        val tabArray: Array<String>
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
                getString(R.string.college_info),
                "Program List",
                getString(R.string.admission),
                getString(R.string.notes)
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
        universityName.setText(collegeName)
        like.setOnClickListener {
            if (toppick ?: 0 == 0) {
                like.setImageResource(R.drawable.heart_filled)
                toppick = 1
                click(position, 1)
                hitLikeWork(collegeNid)

            } else {
                like.setImageResource(R.drawable.like)
                toppick = 0
                click(position, 0)
                hitUnlikeWork(collegeNid)

            }
        }
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
//        like.setOnClickListener {
//            likeClick()
//        }
        dialogFacts?.setContentView(view)
        dialogP = showLoadingDialog(this)
        dialogP.show()
        if ((SharedHelper(this).country ?: "US").equals("US")) {
            mModel.getColFactSheet(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                collegeNid.toString()
            )
        } else {
            mModel.getColFactSheetOther(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                collegeNid.toString()
            )
        }
        Picasso.with(this)
            .load("${UNIV_LOGO_URL}${collegeNid?.toLowerCase()}/${collegeNid}/logo_sm.jpg")
            .error(R.drawable.static_coll).into(imageUniv)

        //  val likePic = view.findViewById<ImageView>(R.id.like)
        if (topflag == 1) {
            like.setImageResource(R.drawable.heart_filled)
        } else {
            like.setImageResource(R.drawable.like)
        }
    }

    fun bottomSheetEurope(
        collegeNid: String,
        topflag: Boolean?,
        collegeName: String?,
        position: Int,
        data: EuropeanUniList.CollegeList?,
        click: (position: Int, flag: Int?) -> Unit
    ) {
        var toppick = topflag
        dialogFacts = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.layout_uni_factsheets, null)
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        val factTabs = view.findViewById<TabLayout>(R.id.fact_tabs)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val close = view.findViewById<ImageView>(R.id.close)
        val imageUniv = view.findViewById<ImageView>(R.id.image)
        val tabArray: Array<String>
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
                getString(R.string.college_info),
                "Program List",
                getString(R.string.admission),
                getString(R.string.notes)
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
        universityName.setText(collegeName)
        like.setOnClickListener {
            if (toppick ?: false == false) {
                like.setImageResource(R.drawable.heart_filled)
                toppick = true
                click(position, 1)
                hitLikeWork(collegeNid)

            } else {
                like.setImageResource(R.drawable.like)
                toppick = false
                click(position, 0)
                hitUnlikeWork(collegeNid)

            }
        }
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
//        like.setOnClickListener {
//            likeClick()
//        }
        dialogFacts?.setContentView(view)
        dialogP = showLoadingDialog(this)
        dialogP.show()
        if ((SharedHelper(this).country ?: "US").equals("US")) {
            mModel.getColFactSheet(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                collegeNid.toString()
            )
        } else {
            mModel.getColFactSheetOther(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                collegeNid.toString()
            )
        }
        Picasso.with(this)
            .load("${UNIV_LOGO_URL}${collegeNid?.toLowerCase()}/${collegeNid}/logo_sm.jpg")
            .error(R.drawable.static_coll).into(imageUniv)

        //  val likePic = view.findViewById<ImageView>(R.id.like)
        if (topflag == true) {
            like.setImageResource(R.drawable.heart_filled)
        } else {
            like.setImageResource(R.drawable.like)
        }
    }

    fun bottomSheetGerman(
        collegeNid: String,
        topflag: Int?,
        collegeName: String?,
        position: Int,
        data: GermanUniversitiesResponse.Data.CollegeData?,
        click: (position: Int, flag: Int?) -> Unit
    ) {
        var toppick = topflag
        dialogFacts = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.layout_uni_factsheets, null)
        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))

        val factTabs = view.findViewById<TabLayout>(R.id.fact_tabs)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val close = view.findViewById<ImageView>(R.id.close)
        val imageUniv = view.findViewById<ImageView>(R.id.image)
        val tabArray: Array<String>
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
                getString(R.string.notes)
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
        universityName.setText(collegeName)
        like.setOnClickListener {
            if (toppick ?: 0 == 0) {
                like.setImageResource(R.drawable.heart_filled)
                toppick = 1
                click(position, 1)
                hitLikeWork(collegeNid)

            } else {
                like.setImageResource(R.drawable.like)
                toppick = 0
                click(position, 0)
                hitUnlikeWork(collegeNid)

            }
        }
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
//        like.setOnClickListener {
//            likeClick()
//        }
        dialogFacts?.setContentView(view)
        dialogP = showLoadingDialog(this)
        dialogP.show()
        if ((SharedHelper(this).country ?: "US").equals("US")) {
            mModel.getColFactSheet(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                collegeNid.toString()
            )
        } else {
            mModel.getColFactSheetOther(
                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
                collegeNid.toString()
            )
        }
        Picasso.with(this)
            .load("${UNIV_LOGO_URL}${collegeNid?.toLowerCase()}/${collegeNid}/logo_sm.jpg")
            .error(R.drawable.static_coll).into(imageUniv)

        //  val likePic = view.findViewById<ImageView>(R.id.like)
        if (topflag == 1) {
            like.setImageResource(R.drawable.heart_filled)
        } else {
            like.setImageResource(R.drawable.like)
        }
    }

//    public fun bottomSheetGerman(get: GermanUniversitiesResponse.Data.CollegeData) {
//        dialogFacts = BottomSheetDialog(this)
//        val view = layoutInflater.inflate(R.layout.layout_uni_factsheets, null)
//        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
//
//        val factTabs = view.findViewById<TabLayout>(R.id.fact_tabs)
//        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
//        val close = view.findViewById<ImageView>(R.id.close)
//        val imageUniv = view.findViewById<ImageView>(R.id.image)
//        val tabArray: Array<String>
//        if ((SharedHelper(this).country ?: "US") == "US") {
//            tabArray = arrayOf(
//                getString(R.string.overview),
//                getString(R.string.community),
//                getString(R.string.admission),
//                getString(R.string.cost_),
//                getString(R.string.degree),
//                getString(R.string.var_sport),
//                getString(R.string.transfer),
//                getString(R.string.notes),
//                getString(R.string.campus_service)
//            )
//        } else {
//            tabArray = arrayOf(
//                getString(R.string.overview),
//                "Program List",
//                getString(R.string.admission),
//                getString(R.string.notes)
//            )
//        }
//        for (item in tabArray) {
//            factTabs.addTab(factTabs.newTab().setText(item))
//        }
//        close.setOnClickListener {
//            dialogFacts?.dismiss()
//        }
//        val fm: FragmentManager = supportFragmentManager
//        val adapter = ViewStateFactAdapter(fm, lifecycle, tabArray.size, this)
//        viewPager.adapter = adapter
//        TabLayoutMediator(factTabs, viewPager) { tab, position ->
//            tab.text = tabArray[position]
//        }.attach()
//        val like = view.findViewById<ImageView>(R.id.like)
//        universityName = view.findViewById<TextView>(R.id.university)
//        universityName.setText(get.collegeName)
//        noData = view.findViewById<TextView>(R.id.no_data)
//        factTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                if (factTabs.selectedTabPosition != 0)
//                    noData.visibility = View.GONE
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//
//            }
//
//        }
//        )
//        like.setOnClickListener {
//            likeClick()
//        }
//        dialogFacts?.setContentView(view)
//        dialogP = showLoadingDialog(this)
//        dialogP.show()
//        if ((SharedHelper(this).country ?: "US").equals("US")) {
//            mModel.getColFactSheet(
//                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
//                get.collegeNid.toString()
//            )
//        } else {
//            mModel.getColFactSheetOther(
//                "Bearer " + SharedHelper(BaseApplication.applicationContext()).authkey,
//                get.collegeNid.toString()
//            )
//        }
//        Picasso.with(this)
//            .load("${UNIV_LOGO_URL}${get.collegeNid?.toLowerCase()}/${get.collegeNid}/logo_sm.jpg")
//            .error(R.drawable.static_coll).into(imageUniv)
//
//        //  val likePic = view.findViewById<ImageView>(R.id.like)
//
//        if (get.topPickFlag == 1) {
//            like.setImageResource(R.drawable.heart_filled)
//        } else {
//            like.setImageResource(R.drawable.like)
//        }
//    }

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

    var flagImg: ImageView? = null
    private fun bottomSheetAddUniv() {
        val dialog = BottomSheetDialog(this)
        val sheetBinding: LayoutUniversityBinding = LayoutUniversityBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        selectedAddUniv = true
        sheetBinding.close.setOnClickListener {
            selectedAddUniv = false
            dialog.dismiss()
        }
        dialog.setOnDismissListener {
            selectedAddUniv = false
        }
        university_list_new?.clear()
        university_list?.clear()

        adapterAddUniv =
            university_list_new?.let { AddUniversiityAdapter(it, sheetBinding.reciepentList, this) }
        sheetBinding.reciepentList.adapter = adapterAddUniv
        adapterAddUniv?.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                university_list_new.add(null)
                isLoading = true
                adapterAddUniv?.notifyItemInserted((university_list_new?.size ?: 0) - 1)
                Handler(Looper.getMainLooper()).postDelayed({
                    for (i in countries) {
                        if (i.select) {
                            getSelectedCountryUniversities(i.code)
                        }
                    }

                }, 2000)
            }
        })
        sheetBinding.save.setOnClickListener {
            selectedAddUniv = false
            //refreshTab(dialog)
            //TODO https://maia2-staging-backend.maialearning.com/ajs-services/top-picks
//            {"student_id":"9375","college_id":"400352","applying_flag":null}
            for (i in university_list?.indices) {
                if (university_list.get(i)?.added == true) {
                    SharedHelper(this).id?.let { it1 ->
                        university_list.get(i)!!.nid?.let { it2 ->
                            mModel.addUniversties(
                                it1,
                                it2
                            )
                        }
                    }
                }
            }
            dialog.dismiss()
        }
        sheetBinding.country.setOnClickListener {
            flagImg = sheetBinding.flagImg
            if (countries.size > 0) {
                SheetUniversityFilter(this, layoutInflater).showDialog(
                    countries,
                    this,
                    sheetBinding.flagImg, selectedAddUniv
                )
            } else {
                dialogP = showLoadingDialog(this)
                dialogP.show()
                mModel.getFilterCollege()
            }
        }
    }

    private fun univFilter() {
        mainDialog = BottomSheetDialog(this)

        sheetBindingUniv = UniversityFilterBinding.inflate(layoutInflater)
        sheetBindingUniv?.root?.minimumHeight =
            ((Resources.getSystem().displayMetrics.heightPixels))
        mainDialog?.setContentView(sheetBindingUniv!!.root)
        sheetBindingUniv?.search?.visibility = View.GONE
        sheetBindingUniv?.filters?.setText(resources.getString(R.string.filters))
        sheetBindingUniv?.clearBtn?.visibility = View.VISIBLE
        dialogP = showLoadingDialog(this)
        dialogP.show()
        mModel.getFilterCollege()
        mainDialog?.show()
        sheetBindingUniv!!.clearText.setOnClickListener {
            for (i in countries) {
                if (i.select) {
                    SharedHelper(this).country = i.code
                }
            }

            if (savedCountry != SharedHelper(this).country) {
                // change single done
                dialogP = showLoadingDialog(this)
                dialogP.show()
                mModel.setSaveCountry()
                listUni.clear()
                SharedHelper(this).country?.let { savedCountry = it }
            } else {
                //  resetFilters()
                initView()
            }

            mainDialog?.dismiss()
        }
        sheetBindingUniv!!.backBtn.setOnClickListener {
//            if (savedCountry != SharedHelper(this).country) {
//                dialogP = showLoadingDialog(this)
//                dialogP.show()
//                mModel.setSaveCountry()
//                listUni.clear()
//            } else {
//             //   initView()
//            }
            // resetFilters()
            // initView()
            mainDialog?.dismiss()
        }
        sheetBindingUniv!!.clearBtn.setOnClickListener {
            resetFilters()
            initView()
            mainDialog?.dismiss()
        }

        Log.e("Selected Country >>", SharedHelper(this).country.toString())
        setFilters()
    }

    private fun setFilters() {
        if (binding.viewPager.currentItem == 0) {
            if ((SharedHelper(this).country ?: "US") == "US") {
                val typeSize =
                    selectedTwoFour.size + selectedPublicPrivate.size + selectedTypeEnv.size + selectedSize.size
                var arr: Array<Int> = arrayOf(
                    0,
                    selectedRegion.size,
                    selectedStateFilter.size,
                    selectedListFilter.size,
                    typeSize,
                    selectedSelectivityFilter.size,
                    0,
                    selectedAthleticAsociations.size,
                    0
                )
                sheetBindingUniv?.reciepentList?.adapter =
                    UnivFilterAdapter(resources.getStringArray(R.array.UnivFilters), this, arr)
            } else if ((SharedHelper(this).country ?: "US") == "DE") {
                var deFilterArray = resources.getStringArray(R.array.DEFilters)
                val selectedCount: Array<Int> =
                    arrayOf(0, selectedListFilter.size, 0, 0, 0, 0, 0, 0, 0)
                var subject_group = "Subject Group"
                for (j in selectedGermanSubject) {
                    if (checkNonNull(j)) {
                        for (i in germanSubject) {
                            if (i.key == j) {
                                subject_group = i.value
                            }
                        }
                    }
                }
                deFilterArray[2] = subject_group

                var area_of_study = "Area of Study"
                for (j in selectedAreaStudy) {
                    if (checkNonNull(j)) {
                        for (i in areaStudy) {
                            if (i.key == j) {
                                area_of_study = i.value
                            }
                        }
                    }
                }
                deFilterArray[3] = area_of_study

                var fos = "Field of Study"
                for (j in selectedFieldSubject) {
                    if (checkNonNull(j)) {
                        for (i in fieldStudy) {
                            if (i.key == j) {
                                fos = i.value
                            }
                        }
                    }
                }
                deFilterArray[4] = fos

                var mode_admission = "Mode of admission"
                if (checkNonNull(selectedModeAdmission)) {
                    for (i in germanModeAdmission) {
                        if (i.key == selectedModeAdmission) {
                            mode_admission = i.value
                        }
                    }
                }
                deFilterArray[5] = mode_admission

                var mode_study = "Mode of study"
                if (checkNonNull(selectedModeStudy)) {
                    for (i in germanStudyMode) {
                        if (i.key == selectedModeStudy) {
                            mode_study = i.value
                        }
                    }
                }
                deFilterArray[6] = mode_study

                var adm_sem = "Admission Semester"
                if (checkNonNull(selectedAdmissionSem)) {
                    for (i in germanAdmissionSem) {
                        if (i.key == selectedAdmissionSem) {
                            adm_sem = i.value
                        }
                    }
                }
                deFilterArray[7] = adm_sem

                var inst_lang = "Instruction Language"
                if (checkNonNull(selectedInstructionLanguage)) {
                    for (i in germanInstLang) {
                        if (i.key == selectedInstructionLanguage) {
                            inst_lang = i.value
                        }
                    }
                }
                deFilterArray[8] = inst_lang



                sheetBindingUniv?.reciepentList?.adapter = UnivFilterAdapter(
                    deFilterArray,
                    this, selectedCount
                )


            } else if ((SharedHelper(this).country ?: "US") == "GB") {
                sheetBindingUniv?.reciepentList?.adapter =
                    UnivFilterAdapter(resources.getStringArray(R.array.GBFilters), this)
            } else if (SharedHelper(this).continent == "EU") {
                val selectedCount: Array<Int> = arrayOf(0, selectedListFilter.size, 0, 0)
                var discipline = "Discipline"
                if (checkNonNull(selectedDiscipline)) {
                    for (i in disciplines) {
                        if (i.key == selectedDiscipline) {
                            discipline = i.value

                        }
                    }
                }
                filterArrayEuropean[2] = discipline

                var sub_discipline = "Sub Discipline"
                for (j in selectedSubDiscipline) {
                    if (checkNonNull(j)) {
                        for (i in subDisciplines) {
                            if (i.key == j) {
                                sub_discipline = i.value
                            }
                        }
                    }
                }
                filterArrayEuropean[3] = sub_discipline

                sheetBindingUniv?.reciepentList?.adapter =
                    UnivFilterAdapter(filterArrayEuropean, this, selectedCount)
            } else {

                dialogUni = showLoadingDialog(this)
                dialogUni.show()
                SharedHelper(this).id?.let { mModel.getUniversityList("1", it) }

            }
        }
    }

    private fun countryFilter(flagImg: ImageView) {
        SheetUniversityFilter(this, layoutInflater).showDialog(
            countries,
            this,
            flagImg, false
        )
    }

    override fun onClick(positiion: Int, type: Int, flagImg: ImageView?) {
        selType = positiion
        println("TYPE" + selType)
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
                if ((SharedHelper(this).country ?: "US") == "US") {
                    SheetUniversityFilter(this, layoutInflater).regionFilter(
                        region,
                        View.VISIBLE,
                        resources.getString(R.string.reigon),
                        positiion
                    )
                } else if (SharedHelper(this).continent == "EU" || SharedHelper(this).country == "DE" || SharedHelper(
                        this
                    ).country == "GB"
                ) {
                    universityList()
                } else {
                    if (listUni.size > 0) {
                        universityList()
                    } else {
                        dialogP.show()
                        mModel.getFosOther()
                    }
                }
            } else if (positiion == 2) {
                if ((SharedHelper(this).country ?: "US") == "US") {
                    if (listStates.size > 0) {
                        SheetUniversityFilter(this, layoutInflater).stateFilter(
                            View.VISIBLE,
                            resources.getString(R.string.list),
                            listStates, View.GONE, positiion
                        )
                    }
                } else if (SharedHelper(this).country == "DE") {
                    disciplineFilter("Subject")
                } else if (SharedHelper(this).country == "GB") {

                } else if (SharedHelper(this).continent == "EU") {
                    disciplineFilter("Discipline")
                } else {
                    if (listUni.size > 0) {
                        dialogP.show()
                        mModel.getFosOther()
                    } else {
                        if (fieldOfStudyProgramAny.size > 0)
                            disciplineFilter("ProgramAny")
                    }

                }
            } else if (positiion == 3) {
                if ((SharedHelper(this).country ?: "US") == "US") {
                    universityList()
                } else if (SharedHelper(this).continent == "EU") {
                    disciplineFilter("Subdiscipline")
                } else if (SharedHelper(this).country == "DE") {
                    if (areaStudy != null && areaStudy.size > 0) {
                        disciplineFilter("Area of Study")
                    }
                } else if (SharedHelper(this).country == "GB") {
                    disciplineFilter("University")
                } else {
                    if (listUni.size > 0) {
                        if (fieldOfStudyProgramAny.size > 0)
                            disciplineFilter("ProgramAny")
                    } else {
                        if (fieldOfStudyMazorAny.size > 0)
                            disciplineFilter("MazorAny")
                    }

                }
            } else if (positiion == 4) {
                if ((SharedHelper(this).country ?: "US") == "US") {
                    typeFilter()
                } else if (SharedHelper(this).country == "DE") {
                    if (fieldStudy != null && fieldStudy.size > 0) {
                        disciplineFilter("Field of Study")
                    }
                } else if (SharedHelper(this).country == "GB") {
                    disciplineFilter("Subject")
                } else {
                    if (listUni.size > 0) {
                        if (fieldOfStudyMazorAny.size > 0)
                            disciplineFilter("MazorAny")
                    }
                }
            } else if (positiion == 5) {
                if ((SharedHelper(this).country ?: "US") == "US") {
                    SheetUniversityFilter(this, layoutInflater).stateFilter(
                        View.VISIBLE,
                        resources.getString(R.string.selectivity), selectivity,
                        View.GONE, positiion
                    )
                } else if (SharedHelper(this).country == "DE") {
                    disciplineFilter("Mode of Admission")
                } else if (SharedHelper(this).country == "GB") {
                    disciplineFilter("Area")
                }
            } else if (positiion == 6) {
                if ((SharedHelper(this).country ?: "US") == "US") {
                    SheetUniversityFilter(this, layoutInflater).regionFilter(
                        region,
                        View.VISIBLE,
                        resources.getString(R.string.programs),
                        positiion
                    )
                } else if (SharedHelper(this).country == "DE") {
                    disciplineFilter("Mode of Study")
                } else if (SharedHelper(this).country == "GB") {
                    disciplineFilter("Program")
                }
            } else if (positiion == 7) {
                if ((SharedHelper(this).country ?: "US") == "US") {
                    sportsFilter()
                } else if (SharedHelper(this).country == "DE") {
                    disciplineFilter("Admission Semester")
                } else if (SharedHelper(this).country == "GB") {
                    disciplineFilter("Colleges")
                }
            } else if (positiion == 8) {
                if ((SharedHelper(this).country ?: "US") == "US") {
                    moreFilter()
                } else if (SharedHelper(this).country == "DE") {
                    disciplineFilter("Instruction Language")
                }
            }
        } else if (type == 1) {
            if (positiion == 2) {
//                if (countries.size > 0) {
//                    flagImg?.let { countryFilter(it) }
//                } else {
//                    dialogP = showLoadingDialog(this)
//                    dialogP.show()
//                    mModel.getFilterCollege()
//                }
                disciplineFilter("Country")
            }
        }
    }

    private fun universityList() {
        if (listUni.size > 0) {
            for (i in listUni) {
                if (selectedListFilter.contains(i.id)) {
                    i.selected = true
                } else {
                    i.selected = false
                }
            }
            SheetUniversityFilter(this, layoutInflater).selectRegionFilter(
                View.VISIBLE,
                resources.getString(R.string.list),
                listUni, View.GONE
            )

        } else {
            dialogP = showLoadingDialog(this)
            dialogP.show()
            SharedHelper(this).id?.let { mModel.getUniversityList("1", it) }
        }
    }

    private fun disciplineFilter(type: String) {
        val dialog = BottomSheetDialog(this)
        val sheetBinding: UniversityFilterBinding =
            UniversityFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.search.visibility = View.GONE
        sheetBinding.filters.setText(type)
        dialog.show()


        sheetBinding.spinnerLay.visibility = View.GONE
        sheetBinding.invisibleLay.visibility = View.GONE

        sheetBinding.backBtn.setOnClickListener { dialog.dismiss() }
        sheetBinding.clearText.setText(resources.getString(R.string.done))
        sheetBinding.clearText.setOnClickListener {
            var ar = filterArrayEuropean
            if (SharedHelper(this).country == "DE") {
                ar = resources.getStringArray(R.array.DEFilters)
            } else if (SharedHelper(this).country == "GB") {
                ar = resources.getStringArray(R.array.GBFilters)
            } else if (SharedHelper(this).country == "EU") {
                ar = filterArrayEuropean

            } else {
                ar = anyFilterArray
            }


            dialog.dismiss()
            if (type.equals("Discipline")) {
                selectedDiscipline = ""
                selectedSubDiscipline.clear()
                for (i in disciplines.indices) {
                    if (disciplines.get(i).checked) {
                        selectedDiscipline = disciplines.get(i).key
                        selectedSubDiscipline.add(disciplines.get(i).key)
                        if (SharedHelper(this).continent == "EU") {
                            ar[selType] = disciplines.get(i).value
//                            val selectedCount: Array<Int> = arrayOf(0, selectedListFilter.size, 0, 0)
//                             sheetBindingUniv?.reciepentList?.adapter =
//                                UnivFilterAdapter(ar, this)
                            //  filterArrayEuropean = ar

                        }
                    }
                }

                if (checkNonNull(selectedDiscipline)) {
                    dialogP.show()

                    mModel.getSubDiscipline(selectedDiscipline)
                }


            } else if (type.equals("Subdiscipline")) {
                selectedSubDiscipline.clear()
                for (i in subDisciplines.indices) {
                    if (subDisciplines.get(i).checked) {
                        selectedSubDiscipline.add(subDisciplines.get(i).key)
                        if (SharedHelper(this).continent == "EU") {
                            ar[selType] = subDisciplines.get(i).value
                            sheetBindingUniv?.reciepentList?.adapter =
                                UnivFilterAdapter(ar, this)
                        }

                    }
                }
            } else if (type.equals("Subject")) {
                selectedGermanSubject.clear()
                for (i in germanSubject.indices) {
                    if (germanSubject.get(i).checked) {
                        selectedGermanSubject.add(germanSubject.get(i).key)
                        ar[selType] = germanSubject.get(i).value
                        sheetBindingUniv?.reciepentList?.adapter =
                            UnivFilterAdapter(ar, this)
                    }
                }
                if (selectedGermanSubject != null && selectedGermanSubject.size > 0) {
                    dialogP.show()
                    if (SharedHelper(this).country == "GB") {
                        areaGBStudy.clear()
                        programGBStudy.clear()
                        mModel.getGbSubchild(selectedGermanSubject.get(0), true)
                    } else
                        mModel.getGermanSubSubject(selectedGermanSubject.get(0), true)
                }

            } else if (type.equals("Mode of Admission")) {
                selectedModeAdmission = ""
                for (i in germanModeAdmission.indices) {
                    if (germanModeAdmission.get(i).checked) {
                        selectedModeAdmission = germanModeAdmission.get(i).key
                        ar[selType] = germanModeAdmission.get(i).value
                        sheetBindingUniv?.reciepentList?.adapter =
                            UnivFilterAdapter(ar, this)
                    }
                }
            } else if (type.equals("Mode of Study")) {
                selectedModeStudy = ""
                for (i in germanStudyMode.indices) {
                    if (germanStudyMode.get(i).checked) {
                        selectedModeStudy = germanStudyMode.get(i).key
                        ar[selType] = germanStudyMode.get(i).value
                        sheetBindingUniv?.reciepentList?.adapter =
                            UnivFilterAdapter(ar, this)
                    }
                }
            } else if (type.equals("Admission Semester")) {
                selectedAdmissionSem = ""
                for (i in germanAdmissionSem.indices) {
                    if (germanAdmissionSem.get(i).checked) {
                        selectedAdmissionSem = germanAdmissionSem.get(i).key
                        ar[selType] = germanAdmissionSem.get(i).value
                        sheetBindingUniv?.reciepentList?.adapter =
                            UnivFilterAdapter(ar, this)
                    }
                }
            } else if (type.equals("Instruction Language")) {
                selectedInstructionLanguage = ""
                for (i in germanInstLang.indices) {
                    if (germanInstLang.get(i).checked) {
                        selectedInstructionLanguage = germanInstLang.get(i).key
                        ar[selType] = germanInstLang.get(i).value
                        sheetBindingUniv?.reciepentList?.adapter =
                            UnivFilterAdapter(ar, this)
                    }
                }
            } else if (type.equals("Area of Study")) {
                for (i in areaStudy.indices) {
                    if (areaStudy.get(i).checked) {
                        selectedAreaStudy.clear()
                        selectedAreaStudy.add(areaStudy.get(i).key)
                        ar[selType] = areaStudy.get(i).value
                        sheetBindingUniv?.reciepentList?.adapter =
                            UnivFilterAdapter(ar, this)
                    }
                }
                if (selectedAreaStudy != null && selectedAreaStudy.size > 0) {
                    dialogP.show()
                    mModel.getGermanSubSubject(selectedAreaStudy.get(0), false)
                }
            } else if (type.equals("Field of Study")) {
                for (i in fieldStudy.indices) {
                    if (fieldStudy.get(i).checked) {
                        selectedFieldSubject.clear()
                        selectedFieldSubject.add(fieldStudy.get(i).key)
                        ar[selType] = fieldStudy.get(i).value
                        sheetBindingUniv?.reciepentList?.adapter =
                            UnivFilterAdapter(ar, this)
                    }
                }
            } else if (type.equals("University")) {
                for (i in gbUniversityList.indices) {
                    if (gbUniversityList.get(i).checked) {
//                        selectedGbUniversity.clear()
                        selectedGbUniversity.add(gbUniversityList.get(i).key)
                        ar[selType] = gbUniversityList.get(i).value
                        sheetBindingUniv?.reciepentList?.adapter =
                            UnivFilterAdapter(ar, this)
                    }
                }
            } else if (type.equals("Colleges")) {
                for (i in gbCollegeList.indices) {
                    if (gbCollegeList.get(i).checked) {
                        selectedGbCollege = ""
                        selectedGbCollege = gbCollegeList.get(i).key
                        ar[selType] = gbCollegeList.get(i).value
                        sheetBindingUniv?.reciepentList?.adapter =
                            UnivFilterAdapter(ar, this)
                    }
                }
            } else if (type.equals("Area")) {
                for (i in areaGBStudy.indices) {
                    if (areaGBStudy.get(i).checked) {
                        selectedAreaStudy.clear()
                        selectedAreaStudy.add(areaGBStudy.get(i).key)
                        ar[selType] = areaGBStudy.get(i).value
                        sheetBindingUniv?.reciepentList?.adapter =
                            UnivFilterAdapter(ar, this)
                    }
                }
                if (selectedAreaStudy != null && selectedAreaStudy.size > 0) {
                    dialogP.show()
                    mModel.getGbSubchild(selectedAreaStudy.get(0), false)
                }
            } else if (type.equals("Program")) {
                for (i in programGBStudy.indices) {
                    if (programGBStudy.get(i).checked) {
                        selectedFieldSubject.clear()
                        selectedFieldSubject.add(programGBStudy.get(i).key)
                        ar[selType] = programGBStudy.get(i).value
                        sheetBindingUniv?.reciepentList?.adapter =
                            UnivFilterAdapter(ar, this)
                    }
                }
            } else if (type.equals("FOS")) {
                for (i in fieldOfStudyAny.indices) {
                    if (fieldOfStudyAny.get(i).checked) {
                        if (fieldOfStudyAny.get(i).key != selectedFOS) {
                            selectedProgramAny = ""
                            selectedMazorAny = ""
                            ar[selType + 1] = "Program"
                            ar[selType + 2] = "Mazor"
                        }
                        selectedFOS = ""
                        selectedFOS = fieldOfStudyAny.get(i).key
                        ar[selType] = fieldOfStudyAny.get(i).value
                        sheetBindingUniv?.reciepentList?.adapter =
                            UnivFilterAdapter(ar, this)
                    }
                }
                dialogP.show()
                mModel.getFosOtherchild(selectedFOS, true)
            } else if (type.equals("ProgramAny")) {
                for (i in fieldOfStudyProgramAny.indices) {
                    if (fieldOfStudyProgramAny.get(i).checked) {
                        if (fieldOfStudyProgramAny.get(i).key != selectedProgramAny) {
                            selectedMazorAny = ""
                            ar[selType + 1] = "Mazor"
                        }
                        selectedProgramAny = ""
                        selectedProgramAny = fieldOfStudyProgramAny.get(i).key
                        ar[selType] = fieldOfStudyProgramAny.get(i).value
                        sheetBindingUniv?.reciepentList?.adapter =
                            UnivFilterAdapter(ar, this)
                    }
                }
                dialogP.show()
                mModel.getFosOtherchild(selectedProgramAny, false)
            } else if (type.equals("MazorAny")) {
                for (i in fieldOfStudyMazorAny.indices) {
                    if (fieldOfStudyMazorAny.get(i).checked) {
                        selectedMazorAny = ""
                        selectedMazorAny = fieldOfStudyMazorAny.get(i).key
                        ar[selType] = fieldOfStudyMazorAny.get(i).value
                        sheetBindingUniv?.reciepentList?.adapter =
                            UnivFilterAdapter(ar, this)
                    }
                }

            }
            //initView()
            // mainDialog?.dismiss()
            setFilters()
        }


        sheetBinding.spinnerLay.visibility = View.GONE
        if (type.equals("Discipline")) {
            for (i in disciplines) {
                if (selectedDiscipline == i.key) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                DiversityAdapter(disciplines, this)
        } else if (type.equals("Subdiscipline") && subDisciplines != null && subDisciplines.size > 0) {
            for (i in subDisciplines) {
                if (selectedSubDiscipline.contains(i.key)) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                DiversityAdapter(subDisciplines, this)
        } else if (type.equals("Subject") && germanSubject != null && germanSubject.size > 0) {
            for (i in germanSubject) {
                if (selectedGermanSubject.contains(i.key)) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                DiversityAdapter(germanSubject, this)
        } else if (type.equals("Mode of Admission") && germanModeAdmission != null && germanModeAdmission.size > 0) {
            for (i in germanModeAdmission) {
                if (selectedModeAdmission.contains(i.key)) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                DiversityAdapter(germanModeAdmission, this)
        } else if (type.equals("Mode of Study") && germanStudyMode != null && germanStudyMode.size > 0) {
            for (i in germanStudyMode) {
                if (selectedModeStudy.contains(i.key)) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                DiversityAdapter(germanStudyMode, this)
        } else if (type.equals("Admission Semester") && germanAdmissionSem != null && germanAdmissionSem.size > 0) {
            for (i in germanAdmissionSem) {
                if (selectedAdmissionSem.contains(i.key)) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                DiversityAdapter(germanAdmissionSem, this)
        } else if (type.equals("Instruction Language") && germanInstLang != null && germanInstLang.size > 0) {
            for (i in germanInstLang) {
                if (selectedInstructionLanguage.contains(i.key)) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                DiversityAdapter(germanInstLang, this)
        } else if (type.equals("Area of Study") && areaStudy != null && areaStudy.size > 0) {
            for (i in areaStudy) {
                if (selectedAreaStudy.contains(i.key)) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                DiversityAdapter(areaStudy, this)
        } else if (type.equals("Field of Study") && fieldStudy != null && fieldStudy.size > 0) {
            for (i in fieldStudy) {
                if (selectedFieldSubject.contains(i.key)) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                DiversityAdapter(fieldStudy, this)
        } else if (type.equals("University") && gbUniversityList != null && gbUniversityList.size > 0) {
            for (i in gbUniversityList) {
                if (selectedGbUniversity.contains(i.key)) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                MultiSelectionAdapter(gbUniversityList, this)
        } else if (type.equals("Colleges") && gbCollegeList != null && gbCollegeList.size > 0) {
            for (i in gbCollegeList) {
                if (selectedGbCollege.contains(i.key)) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                DiversityAdapter(gbCollegeList, this)
        } else if (type.equals("Area") && areaGBStudy != null && areaGBStudy.size > 0) {
            for (i in areaGBStudy) {
                if (selectedAreaStudy.contains(i.key)) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                DiversityAdapter(areaGBStudy, this)
        } else if (type.equals("Program") && programGBStudy != null && programGBStudy.size > 0) {
            for (i in programGBStudy) {
                if (selectedFieldSubject.contains(i.key)) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                MultiSelectionAdapter(programGBStudy, this)
        } else if (type.equals("FOS") && fieldOfStudyAny != null && fieldOfStudyAny.size > 0) {
            sheetBinding.filters.text = "Field of Study"
            for (i in fieldOfStudyAny) {
                if (selectedFOS.contains(i.key)) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                DiversityAdapter(fieldOfStudyAny, this)
        } else if (type.equals("ProgramAny") && fieldOfStudyProgramAny != null && fieldOfStudyProgramAny.size > 0) {
            sheetBinding.filters.text = "Program"
            for (i in fieldOfStudyProgramAny) {
                if (selectedProgramAny.contains(i.key)) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                DiversityAdapter(fieldOfStudyProgramAny, this)
        } else if (type.equals("MazorAny") && fieldOfStudyMazorAny != null && fieldOfStudyMazorAny.size > 0) {
            sheetBinding.filters.text = "Mazor"
            for (i in fieldOfStudyMazorAny) {
                if (selectedMazorAny.contains(i.key)) {
                    i.checked = true
                } else {
                    i.checked = false
                }
            }
            sheetBinding.reciepentList.adapter =
                DiversityAdapter(fieldOfStudyMazorAny, this)
        }



        sheetBinding.close.setOnClickListener {
            sheetBinding.searchText.setText("")
        }
    }


    fun sportsFilter(

    ) {
        val dialog = BottomSheetDialog(this)
        val sheetBinding: SportsFilterBinding = SportsFilterBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.search.visibility = View.GONE
        sheetBinding.filters.setText(title)
        dialog.show()
        val list = parseJSON().response

        list?.let {
            for (i in it) {
                if (selectedAthleticAsociations.contains(i.key)) {
                    i.checked = true
                } else {
                    i.children?.let {
                        for (j in it) {
                            i.checked = true
                            if (selectedAthleticAsociations.contains(j.key)) {
                                j.checked = true
                            } else {
                                i.checked = false
                                j.checked = false
                            }
                        }
                    }
                }

            }
            sheetBinding.reciepentList.adapter =
                SportsFilterAdapter(
                    it
                )
        }

        sheetBinding.spinnerLay.visibility = View.VISIBLE

        sheetBinding.backBtn.setOnClickListener {
            dialog.dismiss()
        }

        sheetBinding.clearText.setOnClickListener {
            if (sheetBinding.spinner.selectedItemPosition > 1)
                selectedSports =
                    sportList.get(sheetBinding.spinner.selectedItemPosition - 1).key
            else
                selectedSports = ""
            if (sheetBinding.rbFemale.isChecked) {
                selectedParticipants = getString(R.string.women)
            } else if (sheetBinding.rbMale.isChecked) {
                selectedParticipants = getString(R.string.male)
            } else {
                selectedParticipants = ""
            }
            selectedAthleticAsociations.clear()
            list?.let {
                for (i in it) {
                    if (i.children != null && i.children.size > 0) {
                        for (j in i.children) {
                            if (j.checked) {
                                j.key?.let { it1 -> selectedAthleticAsociations.add(it1) }
                            }
                        }
                    } else {
                        if (i.checked) {
                            i.key?.let { it1 -> selectedAthleticAsociations.add(it1) }
                        }
                    }
                }
            }

            dialog.dismiss()
            //  initView()
            // mainDialog?.dismiss()
        }

        sheetBinding.close.setOnClickListener {
            sheetBinding.searchText.setText("")
        }
        val adapter =
            MoreFilterSpinnerAdapter(this, sportList, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sheetBinding.spinner.setAdapter(
            NothingSelectedSpinnerAdapter(
                adapter,
                R.layout.nothing_adapter_sports,  // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                this
            )
        )

        for (i in sportList.indices) {
            if (sportList.get(i).key == selectedSports) {
                println("position ${i}")
                sheetBinding.spinner.setSelection(i + 1)
            }
        }
        if (selectedSports.isNullOrEmpty()) {
            sheetBinding.rbMale.isChecked = false
            sheetBinding.rbFemale.isChecked = false
        } else if (selectedSports == getString(R.string.women)) {
            sheetBinding.rbMale.isChecked = false
            sheetBinding.rbFemale.isChecked = true
        } else {
            sheetBinding.rbMale.isChecked = true
            sheetBinding.rbFemale.isChecked = false
        }


        if (selectedSports.isNullOrEmpty()) {
            sheetBinding.spinner.setSelection(0)
        }

        sheetBinding.spinnerLay.setOnClickListener { sheetBinding.spinner.performClick() }

        sheetBinding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    // println("position ${sheetBinding.spinner.selectedItemPosition -1}")
                    if (position > 1) {
                        sheetBinding.participantLay.visibility = View.VISIBLE
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

    }

    fun selectedReigon(list: ArrayList<Region>) {

    }

    override fun onDiversityClick(position: Int) {
        //   selectedDiversity = diversities.get(position).key
    }

    override fun onTypeClick(position: Int, type: String) {

    }


    private fun moreFilter() {
        var local_act = ""
        var local_math = ""
        var local_erbw = ""

        val dialog = BottomSheetDialog(this)
        val sheetBinding: MoreSheetBinding = MoreSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.filters.setText(getString(R.string.more))
        sheetBinding.backTxt.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        sheetBinding.clearText.setOnClickListener {
            for (i in diversities) {
                if (i.checked)
                    selectedDiversity = i.key
            }
            if (sheetBinding.spinner.selectedItemPosition > 1)
                selectedCampusActivity =
                    campusAcivities.get(sheetBinding.spinner.selectedItemPosition - 1).key
            else
                selectedCampusActivity = ""

            sat_erbw = local_erbw
            sat_math = local_math
            act = local_act
            dialog.dismiss()
            // initView()
            //mainDialog?.dismiss()
        }
        for (i in diversities) {
            i.checked = i.key == selectedDiversity
        }
        if (!act.isNullOrEmpty()) {
            sheetBinding.seekAct.values =
                mutableListOf(act.split(" - ")[0].toFloat(), act.split(" - ")[1].toFloat())
            sheetBinding.maxAct.text = "${sheetBinding.seekAct.values[1].toInt()}"
            sheetBinding.minAct.text = "${sheetBinding.seekAct.values[0].toInt()}"
            sheetBinding.actCross.visibility = View.VISIBLE
            local_act = act
        } else {
            sheetBinding.seekAct.values = mutableListOf(1F, 1F)
        }
        if (!sat_math.isNullOrEmpty()) {
            sheetBinding.seekMath.values = mutableListOf(
                sat_math.split(" - ")[0].toFloat(),
                sat_math.split(" - ")[1].toFloat()
            )
            sheetBinding.maxMath.text = "${sheetBinding.seekMath.values[1].toInt()}"
            sheetBinding.minMath.text = "${sheetBinding.seekMath.values[0].toInt()}"
            sheetBinding.mathCross.visibility = View.VISIBLE
            local_math = sat_math
        } else {
            sheetBinding.seekMath.values = mutableListOf(200F, 200F)
        }
        if (!sat_erbw.isNullOrEmpty()) {
            sheetBinding.seekErbw.values = mutableListOf(
                sat_erbw.split(" - ")[0].toFloat(),
                sat_erbw.split(" - ")[1].toFloat()
            )
            sheetBinding.maxErbw.text = "${sheetBinding.seekErbw.values[1].toInt()}"
            sheetBinding.minErbw.text = "${sheetBinding.seekErbw.values[0].toInt()}"
            sheetBinding.erbwCross.visibility = View.VISIBLE
            local_erbw = sat_erbw
        } else {
            sheetBinding.seekErbw.values = mutableListOf(200F, 200F)
        }

        sheetBinding.diversityList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        sheetBinding.diversityList.adapter = DiversityAdapter(diversities, this)

        val adapter =
            MoreFilterSpinnerAdapter(
                this,
                campusAcivities,
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
        for (i in campusAcivities.indices) {
            if (campusAcivities.get(i).key == selectedCampusActivity) {
                println("position ${i}")
                sheetBinding.spinner.setSelection(i + 1)
            }
        }
        if (selectedCampusActivity.isNullOrEmpty()) {
            sheetBinding.spinner.setSelection(0)
        }
        sheetBinding.campusActivity.setOnClickListener { sheetBinding.spinner.performClick() }

        sheetBinding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    // println("position ${sheetBinding.spinner.selectedItemPosition -1}")

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        sheetBinding.seekAct.addOnChangeListener { slider, value, fromUser ->
            sheetBinding.minAct.setText("${slider.values[0].toInt()}")
            sheetBinding.maxAct.setText("${slider.values[1].toInt()}")
            local_act = "${sheetBinding.minAct.text} - ${sheetBinding.maxAct.text}"
            sheetBinding.maxAct.setText("${slider.values[1].toInt()}")
            sheetBinding.actCross.visibility = View.VISIBLE
        }
        sheetBinding.actCross.setOnClickListener {
            sheetBinding.minAct.setText("1")
            sheetBinding.seekAct.values = mutableListOf(1F, 1F)
            sheetBinding.actCross.visibility = View.GONE
            local_act = ""
            sheetBinding.minAct.setText("1")
            sheetBinding.maxAct.setText("36")
        }

        sheetBinding.seekMath.addOnChangeListener { slider, value, fromUser ->
            sheetBinding.minMath.setText("${slider.values[0].toInt()}")
            sheetBinding.maxMath.setText("${slider.values[1].toInt()}")
            local_math = "${sheetBinding.minMath.text} - ${sheetBinding.maxMath.text}"
            sheetBinding.mathCross.visibility = View.VISIBLE
        }
        sheetBinding.mathCross.setOnClickListener {
            sheetBinding.minMath.setText("1")
            sheetBinding.seekMath.values = mutableListOf(200F, 200F)
            sheetBinding.mathCross.visibility = View.GONE
            local_math = ""
            sheetBinding.minMath.setText("200")
            sheetBinding.maxMath.setText("800")
        }
        sheetBinding.seekErbw.addOnChangeListener { slider, value, fromUser ->
            sheetBinding.minErbw.setText("${slider.values[0].toInt()}")
            sheetBinding.maxErbw.setText("${slider.values[1].toInt()}")
            local_erbw = "${sheetBinding.minErbw.text} - ${sheetBinding.maxErbw.text}"
            sheetBinding.erbwCross.visibility = View.VISIBLE
        }
        sheetBinding.erbwCross.setOnClickListener {
            sheetBinding.minErbw.setText("1")
            sheetBinding.seekErbw.values = mutableListOf(200F, 200F)
            sheetBinding.erbwCross.visibility = View.GONE
            local_erbw = ""
            sheetBinding.minErbw.setText("200")
            sheetBinding.maxErbw.setText("800")
        }
    }

    private fun typeFilter() {
        val dialog = BottomSheetDialog(this)
        val sheetBinding: TypeSheetBinding = TypeSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        sheetBinding.filters.setText(getString(R.string.type))
        dialog.show()
        for (i in twoFourYear) {
            i.checked = selectedTwoFour.contains(i.key)
        }
        for (i in publicPrivate) {
            i.checked = selectedPublicPrivate.contains(i.key)
        }
        for (i in typeEnvironMent) {
            i.checked = selectedTypeEnv.contains(i.key)
        }
        for (i in sizeList) {
            i.checked = selectedSize.contains(i.key)
        }

        sheetBinding.clearText.setOnClickListener {
            selectedTwoFour.clear()
            selectedPublicPrivate.clear()
            selectedTypeEnv.clear()
            selectedSize.clear()
            selectedReligious = ""
            for (i in twoFourYear.indices) {
                if (twoFourYear.get(i).checked) {
                    selectedTwoFour.add(twoFourYear.get(i).key)
                }
            }
            for (i in publicPrivate.indices) {
                if (publicPrivate.get(i).checked) {
                    selectedPublicPrivate.add(publicPrivate.get(i).key)
                }
            }
            for (i in typeEnvironMent.indices) {
                if (typeEnvironMent.get(i).checked) {
                    selectedTypeEnv.add(typeEnvironMent.get(i).key)
                }
            }
            for (i in sizeList.indices) {
                if (sizeList.get(i).checked) {
                    selectedSize.add(sizeList.get(i).key)
                }
            }
            if (sheetBinding.spinner.selectedItemPosition > 1)
                selectedReligious =
                    religious.get(sheetBinding.spinner.selectedItemPosition - 1).key
            else
                selectedReligious = ""

            dialog.dismiss()
            //initView()
            // mainDialog?.dismiss()
        }
        sheetBinding.backTxt.setOnClickListener {
            selectedTwoFour.clear()
            selectedPublicPrivate.clear()
            selectedTypeEnv.clear()
            selectedSize.clear()
            selectedReligious = ""
            dialog.dismiss()
        }

        sheetBinding.twoFourList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        sheetBinding.publicList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        sheetBinding.sizeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        sheetBinding.typeEnvList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        sheetBinding.twoFourList.adapter = TypeFilterAdapter(twoFourYear, "2-4", this)
        sheetBinding.publicList.adapter = TypeFilterAdapter(publicPrivate, "Public", this)
        sheetBinding.typeEnvList.adapter = TypeFilterAdapter(typeEnvironMent, "Type", this)
        sheetBinding.sizeList.adapter = TypeFilterAdapter(sizeList, "Size", this)


        val adapter =
            MoreFilterSpinnerAdapter(this, religious, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sheetBinding.spinner.setAdapter(
            NothingSelectedSpinnerAdapter(
                adapter,
                R.layout.nothing_adapter_type,  // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                this
            )
        )
        if (selectedReligious.isNullOrEmpty()) {
            sheetBinding.spinner.setSelection(0)
        } else {
            for (i in religious.indices) {
                if (religious.get(i).key == selectedReligious) {
                    println("position ${i}")
                    sheetBinding.spinner.setSelection(i + 1)
                }
            }
        }
        sheetBinding.religous.setOnClickListener { sheetBinding.spinner.performClick() }
        sheetBinding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    // println("position ${sheetBinding.spinner.selectedItemPosition -1}")

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

    }

    fun refreshTab(dialog: BottomSheetDialog) {
        binding.tabs.selectTab(binding.tabs.getTabAt(0))
        //mainDialog?.dismiss()
        dialog?.dismiss()
//        binding.tabs.selectTab(binding.tabs.getTabAt(0))
//        if (savedCountry != SharedHelper(this).country) {
//            dialogP = showLoadingDialog(this)
//            dialogP.show()
//            mModel.setSaveCountry()
//            listUni.clear()
//        } else {
//             initView()
//        }
    }

    fun countryFilterDone(country: String) {
        if (selectedAddUniv) {
            dialogP.dismiss()
            page = 1
            university_list?.clear()
            university_list_new?.clear()
            getSelectedCountryUniversities(country)
        }
    }

    private fun getSelectedCountryUniversities(country: String) {
        val payload = UniversitySearchPayload()
        // val country = SharedHelper(this).country
        if (country != null) {
            payload.country = country
            payload.pager = page
        }
        university_list.clear()

        if (euCountries.contains(country)) {
            mModel.euroUniversities(payload)
        } else {
            mModel.addUniversityCollSearch(payload)
        }
    }

    fun listFilterDone(list: MutableList<UniersitiesListModel>, dialog: BottomSheetDialog) {
        selectedListFilter.clear()
        for (i in list) {
            if (i.selected)
                selectedListFilter.add(i.id.toString())
        }
        // mainDialog?.dismiss()
        setFilters()
        dialog.dismiss()
        // initView()
    }

    private fun likeClick() {
        //  binding.tabs.selectTab(binding.tabs.getTabAt(1))

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
        homeModel.likeObserver.observe(this) {
            dialogP.dismiss()
        }
        homeModel.unlikeObserver.observe(this) {
            dialogP.dismiss()
        }

        homeModel.showError.observe(this) {
            dialogP.dismiss()
            Log.e("Error", "err" + it)
            if (!it.isNullOrEmpty())
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        mModel.getSaveCountryObserver.observe(this) {
            dialogP.dismiss()
            initView()
        }
        mModel.saveCountryObserver.observe(this) {
            dialogP.dismiss()
            if (it.get(0).toString() != null || it.get(0).toString() != "") {
                savedCountry = it.get(0).toString().replaceInvertedComas().replace("\\", "")
                SharedHelper(this).country = savedCountry
                initView()
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
            val jsonSelectivity = JSONObject(it.toString()).getJSONObject("selectivity")

            val keyList = ArrayList<String>()
            val jsonArray = JSONArray()
            val x = json.keys() as Iterator<String>
            val xRegion = jsonRegion.keys() as Iterator<String>
            val xSelect = jsonSelectivity.keys() as Iterator<String>
            val jsonArrayRegion = JSONArray()
            while (xRegion.hasNext()) {
                val key: String = xRegion.next()
                jsonArrayRegion.put(jsonRegion.get(key))
            }

            while (xSelect.hasNext()) {
                val key: String = xSelect.next()
                selectivity.add(KeyVal(key, jsonSelectivity.get(key).toString(), false))
            }
            while (x.hasNext()) {
                val key: String = x.next()
                jsonArray.put(json.get(key))
                keyList.add(key)
            }
            for (i in 0 until jsonArray.length()) {
                countries.add(
                    FilterUSModelClass.CountryList(
                        keyList[i], jsonArray.get(i).toString(), false
                    )
                )
            }
            for (i in 0 until jsonArrayRegion.length()) {
                region.add(jsonArrayRegion.get(i).toString())
            }

            listStates = arrayListOf()
            //States
            val jsonStates: JSONObject? = JSONObject(it.toString()).optJSONObject("states")
            jsonStates.let {
                val keys = jsonStates?.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
                    if (key != "0")
                        listStates.add(KeyVal(key, jsonStates.getString(key), false))
                }
            }

            //More Campus Activities
            campusAcivities.clear()
            val jsonCampusAcivities: JSONObject? =
                JSONObject(it.toString()).optJSONObject("campus_activies")
            jsonCampusAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    campusAcivities.add(KeyVal(key, it.getString(key), false))
                }
            }

            //More Diversities Activities
            diversities.clear()
            val jsonDiversityAcivities: JSONObject? =
                JSONObject(it.toString()).optJSONObject("diversity")
            jsonDiversityAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    diversities.add(KeyVal(key, it.getString(key), false))
                }
            }

            //Type of school Activities
            twoFourYear.clear()
            val jsontwoFourAcivities: JSONObject? =
                JSONObject(it.toString()).optJSONObject("2_4_year")
            jsontwoFourAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    twoFourYear.add(KeyVal(key, it.getString(key), false))
                }
            }

            //Type of school Activities
            publicPrivate.clear()
            val jsonPublicAcivities: JSONObject? =
                JSONObject(it.toString()).optJSONObject("public_private")
            jsonPublicAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    publicPrivate.add(KeyVal(key, it.getString(key), false))
                }
            }

            //Type of school Activities
            typeEnvironMent.clear()
            val jsontypeEnvAcivities: JSONObject? =
                JSONObject(it.toString()).optJSONObject("type_of_env")
            jsontypeEnvAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    typeEnvironMent.add(
                        KeyVal(
                            key,
                            it.getJSONObject(key).optString("name"),
                            false
                        )
                    )
                }
            }


//            //Type of school Activities
//            fieldOfStudyAny.clear()
//            val jsonFOS: JSONObject? =
//                JSONObject(it.toString()).optJSONObject("foslevel1")
//            jsonFOS?.let {
//                val keys = it.keys() as Iterator<String>
//                while (keys.hasNext()) {
//                    val key = keys.next()
////                    if (key != "0")
//                    fieldOfStudyAny.add(
//                        KeyVal(
//                            key,
//                            it.optString(key),
//                            false
//                        )
//                    )
//                }
//            }


            sizeList.clear()
            val jsonSizeAcivities: JSONObject? =
                JSONObject(it.toString()).optJSONObject("size")
            jsonSizeAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    sizeList.add(KeyVal(key, it.getString(key), false))
                }
            }

            religious.clear()
            val jsonReligiousAcivities: JSONObject? =
                JSONObject(it.toString()).optJSONObject("religious")
            jsonReligiousAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    religious.add(KeyVal(key, it.getString(key), false))
                }
            }

            val jsonSportsAcivities: JSONObject? =
                JSONObject(it.toString()).optJSONObject("sport_list")
            jsonSportsAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    sportList.add(KeyVal(key, it.getString(key), false))
                }
            }

            disciplines.clear()
            val jsondisciplineAcivities: JSONObject? =
                JSONObject(it.toString()).optJSONObject("unicas_discipline")
            jsondisciplineAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
                    disciplines.add(KeyVal(key, it.getString(key), false))
                }
            }

            germanSubject.clear()
            var jsonSubject: JSONObject? =
                JSONObject(it.toString()).optJSONObject("german_subjectlevel1")
            if (SharedHelper(this).country == "GB") {
                jsonSubject = JSONObject(it.toString()).optJSONObject("ucas_subjectlevel1")
            }

            jsonSubject?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
                    germanSubject.add(KeyVal(key, it.getString(key), false))
                }

            }

            gbUniversityList.clear()
            val jsonGbUniv: JSONObject? =
                JSONObject(it.toString()).optJSONObject("ucas_college_list")
            jsonGbUniv?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
                    gbUniversityList.add(KeyVal(key, it.getString(key), false))
                }

            }
            val jsonGbColl: JSONObject? =
                JSONObject(it.toString()).optJSONObject("ucas_college_type")
            gbCollegeList.clear()
            jsonGbColl?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
                    gbCollegeList.add(KeyVal(key, it.getString(key), false))
                }

            }

            val jsonModeAdmission: JSONObject? =
                JSONObject(it.toString()).optJSONObject("german_mode_of_admission")
            jsonModeAdmission?.let {
                val keys = it.keys() as Iterator<String>
                germanModeAdmission.clear()
                while (keys.hasNext()) {
                    val key = keys.next()
                    germanModeAdmission.add(KeyVal(key, it.getString(key), false))
                }

            }


            val jsonStudyMode: JSONObject? =
                JSONObject(it.toString()).optJSONObject("german_study_mode")
            jsonStudyMode?.let {
                val keys = it.keys() as Iterator<String>
                germanStudyMode.clear()
                while (keys.hasNext()) {
                    val key = keys.next()
                    germanStudyMode.add(KeyVal(key, it.getString(key), false))
                }
            }


            val jsonAdmissionSem: JSONObject? =
                JSONObject(it.toString()).optJSONObject("german_admission_semester")
            jsonAdmissionSem?.let {
                val keys = it.keys() as Iterator<String>
                germanAdmissionSem.clear()
                while (keys.hasNext()) {
                    val key = keys.next()
                    germanAdmissionSem.add(KeyVal(key, it.getString(key), false))
                }
            }


            val jsonInstLang: JSONObject? =
                JSONObject(it.toString()).optJSONObject("german_instruction_language")
            jsonInstLang?.let {
                val keys = it.keys() as Iterator<String>
                germanInstLang.clear()
                while (keys.hasNext()) {
                    val key = keys.next()
                    germanInstLang.add(KeyVal(key, it.getString(key), false))
                }
            }
            if (selectedAddUniv) {
                flagImg?.let { it1 ->
                    SheetUniversityFilter(this, layoutInflater).showDialog(
                        countries,
                        this,
                        it1, selectedAddUniv
                    )
                }
            }
        }
        disciplines.sortBy { it.key }


        mModel.factSheetOtherObserver.observe(this) {
            dialogP.dismiss()
            loadDataOther(it)
            dialogFacts?.show()
        }


        mModel.subDisciplineObserver.observe(this) {
            dialogP.dismiss()
            subDisciplines.clear()
            val jsondisciplineAcivities: JSONObject? =
                JSONObject(it.toString())
            jsondisciplineAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    subDisciplines.add(KeyVal(key, it.getString(key), false))
                }
            }
        }
        mModel.areaStudyObserver.observe(this) {
            dialogP.dismiss()
            val jsondisciplineAcivities: JSONObject? =
                JSONObject(it.toString())
            jsondisciplineAcivities?.let {
                val keys = it.keys() as Iterator<String>
                areaStudy.clear()
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    areaStudy.add(KeyVal(key, it.getString(key), false))
                }
            }
        }


        fieldStudy.clear()
        mModel.fieldStudyObserver.observe(this) {
            dialogP.dismiss()
            val jsondisciplineAcivities: JSONObject? =
                JSONObject(it.toString())
            jsondisciplineAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    fieldStudy.add(KeyVal(key, it.getString(key), false))
                }
            }
        }


        areaGBStudy.clear()
        mModel.gbAreaStudy.observe(this) {
            dialogP.dismiss()
            val jsondisciplineAcivities: JSONObject? =
                JSONObject(it.toString())
            jsondisciplineAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    areaGBStudy.add(KeyVal(key, it.getString(key), false))
                }
            }
        }

        areaGBStudy.clear()
        mModel.gbProgramObserver.observe(this) {
            dialogP.dismiss()
            val jsondisciplineAcivities: JSONObject? =
                JSONObject(it.toString())
            jsondisciplineAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    areaGBStudy.add(KeyVal(key, it.getString(key), false))
                }
            }
        }
        programGBStudy.clear()
        mModel.gbProgramObserver.observe(this) {
            dialogP.dismiss()
            val jsondisciplineAcivities: JSONObject? =
                JSONObject(it.toString())
            jsondisciplineAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    programGBStudy.add(KeyVal(key, it.getString(key), false))
                }
            }
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
                        varList[i],
                        CollegeFactSheetModel.VarsityAthleticSports1.Teams1.Baseball(
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
                    CollegeFactSheetModel.DegreeMajors1(
                        array,
                        itModel.degreeMajors.programOffered
                    )
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
            if (this::dialogUni.isInitialized)
                dialogUni.dismiss()
            listUni.clear()
            for (i in 0 until it.size()) {
                val objectProgram = it.get(i).asJsonObject
                val json = JSONObject(it.get(i).toString())

                val arrayProgram = json.optJSONArray("universities")
                if (arrayProgram != null) {
                    for (i in 0 until arrayProgram.length()) {
                        if (arrayProgram.optJSONObject(i).optString("country")
                                .equals(SharedHelper(this).country)
                        ) {
                            listUni.add(
                                UniersitiesListModel(
                                    objectProgram.getAsJsonPrimitive("id").toString()
                                        .replace("\"", ""),
                                    objectProgram.getAsJsonPrimitive("name").toString()
                                        .replace("\"", ""),
                                    "",
                                    objectProgram.getAsJsonPrimitive("type").toString(),
                                    objectProgram.getAsJsonPrimitive("created").toString(),
                                    objectProgram.getAsJsonPrimitive("changed").toString(),
                                    "",
                                    objectProgram.getAsJsonPrimitive("status").toString(),
                                    "",
                                    objectProgram.getAsJsonPrimitive("district_scope")
                                        .toString(),
                                    false
                                )
                            )
                            break
                        }
                    }
                }

            }
            if ((SharedHelper(this).country ?: "US") == "US" || (SharedHelper(this).country
                    ?: "US") == "GB" || (SharedHelper(this).country ?: "US") == "DE"
                || (SharedHelper(this).continent ?: "US") == "EU"
            )
                SheetUniversityFilter(this, layoutInflater).selectRegionFilter(
                    View.VISIBLE,
                    resources.getString(R.string.list),
                    listUni, View.GONE
                )
            else {
                if (listUni.size == 0)
                    anyFilterArray = arrayOf("Country", "Field of Study", "Program", "Major")
                if (anyFilterArray.size == 4) {
                    val selectedCount: Array<Int> = arrayOf(0, selectedListFilter.size, 0, 0)
                    sheetBindingUniv?.reciepentList?.adapter =
                        UnivFilterAdapter(anyFilterArray, this, selectedCount)
                } else {
                    val selectedCount: Array<Int> = arrayOf(0, selectedListFilter.size, 0, 0, 0)
                    sheetBindingUniv?.reciepentList?.adapter =
                        UnivFilterAdapter(anyFilterArray, this, selectedCount)
                }
            }
        }
        mModel.otherFosObserver.observe(this) {
            dialogP.dismiss()
            fieldOfStudyAny.clear()
            val jsondisciplineAcivities: JSONObject? =
                JSONObject(it.toString())
            jsondisciplineAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    fieldOfStudyAny.add(KeyVal(key, it.getString(key), false))
                }
            }
            if (fieldOfStudyAny.size > 0)
                disciplineFilter("FOS")
        }

        mModel.fosChildOther.observe(this) {
            dialogP.dismiss()
            fieldOfStudyProgramAny.clear()
            fieldOfStudyMazorAny.clear()
            val jsondisciplineAcivities: JSONObject? =
                JSONObject(it.toString())
            jsondisciplineAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    fieldOfStudyProgramAny.add(KeyVal(key, it.getString(key), false))
                }
            }


        }

        mModel.fosChildMazor.observe(this) {
            dialogP.dismiss()
            fieldOfStudyMazorAny.clear()
            val jsondisciplineAcivities: JSONObject? =
                JSONObject(it.toString())
            jsondisciplineAcivities?.let {
                val keys = it.keys() as Iterator<String>
                while (keys.hasNext()) {
                    val key = keys.next()
//                    if (key != "0")
                    fieldOfStudyMazorAny.add(KeyVal(key, it.getString(key), false))
                }
            }
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

        mModel.addSearchUniversityObserver.observe(this) {
            dialogP.dismiss()
            if ((SharedHelper(this).country ?: "US") == "DE") {
                val univ = SearchParser(it).parseGermanJson()
                page = (univ.pager!!.current!! + 1)
                val totalPage = univ.pager?.total
                val last = univ.pager?.last
                dialogP.dismiss()

                for (item in univ.data?.collegeData!!) {
                    item.courseList.sortBy { it.courseName }
                    var universitiesSearchModel = UniversitiesSearchModel()
                    universitiesSearchModel.collegeName = item.collegeName
                    universitiesSearchModel.nid = item.collegeNid
                    collegeList?.let {
                        for (i in it) {
                            if (i.university_nid == universitiesSearchModel.nid || i.universityNid == universitiesSearchModel.nid) {
                                universitiesSearchModel.added = true
                            }
                        }
                    }

                    university_list?.add(universitiesSearchModel)
                }


                if (isLoading) {
                    isLoading = false
                    university_list_new?.removeAt(university_list_new!!.size - 1)
                    university_list_new?.let { it1 -> adapterAddUniv?.notifyItemRemoved(it1.size) }
                }
                //for swipe refresh page
                if (totalPage != null) {
                    if (last != null) {
                        adapterAddUniv?.addAllLis(university_list!!, totalPage.toInt(), last, false)
                    }
                }
                adapterAddUniv?.setLoaded()
            } else if ((SharedHelper(this).country ?: "US") == "GB") {
                val univ = SearchParser(it).parseUkJson()
                page = (univ.pager!!.current!! + 1)
                val totalPage = univ.pager?.total
                val last = univ.pager?.last
                dialogP.dismiss()
                for (item in univ.data?.collegeData!!) {
                    item.courseList.sortBy { it.courseName }
                    item.courseList.sortBy { it.courseName }
                    var universitiesSearchModel = UniversitiesSearchModel()
                    universitiesSearchModel.collegeName = item.collegeName
                    universitiesSearchModel.nid = item.collegeNid
                    university_list?.add(universitiesSearchModel)
                }
//                ukList?.addAll(univ.data?.collegeData!!)
//                ukListUpdate?.addAll(univ.data?.collegeData!!)

                if (isLoading) {
                    isLoading = false
                    university_list_new?.removeAt(university_list_new!!.size - 1)
                    university_list_new?.let { it1 -> adapterAddUniv?.notifyItemRemoved(it1.size) }
                }
                //for swipe refresh page
                if (totalPage != null) {
                    if (last != null) {
                        adapterAddUniv?.addAllLis(university_list!!, totalPage.toInt(), last, false)
                    }
                }
                adapterAddUniv?.setLoaded()

            } else if (euCountries.contains(SharedHelper(this).country ?: "US")) {
                val univ = SearchParser(it).parseEuropeanJson()
                page = (univ.pager!! + 1)
                val totalPage = univ.totalRecords
                val last = univ.last
                dialogP.dismiss()
                for (item in univ.collegeList) {
                    var universitiesSearchModel = UniversitiesSearchModel()
                    universitiesSearchModel.collegeName = item.collegeName
                    universitiesSearchModel.nid = item.collegeNid
                    collegeList?.let {
                        for (i in it) {
                            if (i.university_nid == universitiesSearchModel.nid || i.universityNid == universitiesSearchModel.nid) {
                                universitiesSearchModel.added = true
                            }
                        }
                    }
                    university_list?.add(universitiesSearchModel)
                }

                if (isLoading) {
                    isLoading = false
                    university_list_new?.removeAt(university_list_new!!.size - 1)
                    university_list_new?.let { it1 -> adapterAddUniv?.notifyItemRemoved(it1.size) }
                }
                //for swipe refresh page
                if (totalPage != null) {
                    if (last != null) {
                        adapterAddUniv?.addAllLis(university_list!!, totalPage.toInt(), last, false)
                    }
                }
                adapterAddUniv?.setLoaded()
            } else {
                val univ = SearchParser(it).parseJson()
                page = (univ.pager!! + 1)
                val totalPage = univ.totalRecords
                val last = univ.last
                dialogP.dismiss()
                university_list?.addAll(univ.university_list!!)

                if (isLoading) {
                    isLoading = false
                    university_list_new?.removeAt(university_list_new?.size - 1)
                    university_list_new?.let { it1 -> adapterAddUniv?.notifyItemRemoved(it1.size) }
                }
                //for swipe refresh page
                if (totalPage != null) {
                    if (last != null) {
                        adapterAddUniv?.addAllLis(university_list!!, totalPage.toInt(), last, false)
                    }
                }
                adapterAddUniv?.setLoaded()
            }
        }
    }

    fun bottomSheetCourseList(
        get: UkResponseModel.Data.CollegeData?,
        listGerman: GermanUniversitiesResponse.Data.CollegeData?,
        type: String
    ) {
        val dialog = BottomSheetDialog(this)
        val sheetBinding: CoursesListBinding = CoursesListBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        if (type == "uk") {
            sheetBinding.name.text = get?.collegeName
            get?.courseList?.sortBy { it.courseName }
            sheetBinding.recyclerList.adapter = CoursesAdapter(
                this,
                get?.courseList, null, type
            )
        } else {
            listGerman?.courseList?.sortBy { it.courseName }
            sheetBinding.name.text = listGerman?.collegeName
            sheetBinding.recyclerList.adapter = CoursesAdapter(
                this,
                null, listGerman?.courseList, type
            )
        }
        sheetBinding.back.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()


    }

    private fun hitUnlikeWork(nid: String?) {
        dialogP.show()
        SharedHelper(this).id?.let {
            nid?.let { it1 ->
                homeModel.hitunlike(
                    it,
                    it1
                )
            }
        }
    }

    private fun hitLikeWork(nid: String?) {
        dialogP.show()

        SharedHelper(this).id?.let {
            nid?.let { it1 ->
                homeModel.hitlike(
                    it,
                    it1
                )
            }
        }
    }

    fun reigonFilterDone(list: ArrayList<Region>, dialog: BottomSheetDialog) {
        selectedRegion.clear()
        for (i in list) {
            if (i.checked)
                i.reigon?.let { selectedRegion.add(it) }
        }
        // mainDialog?.dismiss()
        dialog.dismiss()
        // initView()


    }

    fun stateFilterDone(list: MutableList<KeyVal>, dialog: BottomSheetDialog) {
        selectedStateFilter.clear()
        for (i in list) {
            if (i.checked)
                selectedStateFilter.add(i.key)
        }
        // mainDialog?.dismiss()
        dialog.dismiss()
        // initView()
    }

    fun selectivityFilterDone(list: ArrayList<KeyVal>, dialog: BottomSheetDialog) {
        selectedSelectivityFilter.clear()
        for (i in list) {
            if (i.checked)
                selectedSelectivityFilter.add(i.key)
        }
        // mainDialog?.dismiss()
        dialog.dismiss()
        // initView()
    }

    companion object Filters {
        var euCountries: ArrayList<String> = arrayListOf()
        var selectedRegion: ArrayList<String> = arrayListOf()
        var selectedListFilter: ArrayList<String> = arrayListOf()
        var selectedStateFilter: ArrayList<String> = arrayListOf()
        var selectedSelectivityFilter: ArrayList<String> = arrayListOf()
        var selectedCampusActivity: String = ""
        var selectedDiversity: String = ""
        var selectedSports: String = ""
        var selectedParticipants: String = ""
        var sat_erbw: String = ""
        var sat_math: String = ""
        var act: String = ""
        var selectedTwoFour: ArrayList<String> = arrayListOf()
        var selectedPublicPrivate: ArrayList<String> = arrayListOf()
        var selectedTypeEnv: ArrayList<String> = arrayListOf()
        var selectedSize: ArrayList<String> = arrayListOf()
        var selectedReligious: String = ""
        var selectedAthleticAsociations: ArrayList<String> = arrayListOf()
        var selectedDiscipline: String = ""
        var selectedSubDiscipline: ArrayList<String> = arrayListOf()
        var selectedGermanSubject: ArrayList<String> = arrayListOf()
        var selectedAreaStudy: ArrayList<String> = arrayListOf()
        var selectedFieldSubject: ArrayList<String> = arrayListOf()
        var selectedModeAdmission: String = ""
        var selectedModeStudy: String = ""
        var selectedAdmissionSem: String = ""
        var selectedInstructionLanguage = ""
        var selectedGbUniversity: ArrayList<String> = arrayListOf()
        var selectedGbCollege = ""
        var selectedProgramAny = ""
        var selectedMazorAny = ""
        var selectedFOS: String = ""
        var collegeList: java.util.ArrayList<ConsiderModel.Data>? = null

    }
}

interface ClickFilters {
    fun onClick(positiion: Int, type: Int, flagImg: ImageView?)
    fun onDiversityClick(position: Int)
    fun onTypeClick(position: Int, type: String)
}


