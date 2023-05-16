package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.GsonBuilder
import com.maialearning.R
import com.maialearning.databinding.CareerFilterBottomsheetBinding
import com.maialearning.databinding.SearchDetailBinding
import com.maialearning.databinding.SelectRegionInterestSheetBinding
import com.maialearning.databinding.SummaryFactsheetLayoutBinding
import com.maialearning.model.*
import com.maialearning.ui.adapter.*
import com.maialearning.util.*
import com.maialearning.viewmodel.SummaryModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt


class SummaryNewFragment(var response_regional: CareerFactsheetResponse?,var response_national: CareerFactsheetResponse?, var sessionDataResponse: SessionDataResponse?) : Fragment() {

    var dialog: BottomSheetDialog? = null
    var dialogRegionNameBottomsheetBinding: BottomSheetDialog? = null
    var selectRegionInterestSheetBinding: SelectRegionInterestSheetBinding? = null
    var selectRegionInterestSheet: BottomSheetDialog? = null
    private val careerViewModel: SummaryModel by viewModel()
    var response:CareerFactsheetResponse? = null
    var regionLevel=""
    var regionCode=""
    var regionName=""
    private lateinit var progress: Dialog
    var filtersFactsheetResponse:CareerFilterResponse? = null

    private lateinit var mBinding: SummaryFactsheetLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    val arrColor = arrayOf(R.color.color_14,R.color.color_19,R.color.color_25,R.color.color_35,R.color.color_45,R.color.color_55,R.color.color_65,R.color.dashed_color)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = SummaryFactsheetLayoutBinding.inflate(inflater, container, false)


        observers()
        return mBinding.root

    }
    private fun initPieChart( entries:ArrayList<PieEntry>, label:String) {

        val pieChart =  mBinding.pieChartView

        // on below line we are setting user percent value,
        // setting description as enabled and offset for pie chart
        pieChart.setUsePercentValues(true)
        pieChart.getDescription().setEnabled(false)
       // pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        // on below line we are setting drag for our pie chart
        pieChart.setDragDecelerationFrictionCoef(0.95f)

        // on below line we are setting hole
        // and hole color for pie chart
       // pieChart.setDrawHoleEnabled(true)
        pieChart.setHoleColor(Color.WHITE)

        // on below line we are setting circle color and alpha
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)

        // on  below line we are setting hole radius

        pieChart.setHoleRadius(77f)
        pieChart.setTransparentCircleRadius(61f)

        // on below line we are setting center text
        pieChart.setDrawCenterText(true)

        // on below line we are setting
        // rotation for our pie chart
        pieChart.setRotationAngle(270f)

        // enable rotation of the pieChart by touch
        pieChart.setRotationEnabled(true)
        pieChart.setHighlightPerTapEnabled(true)

        // on below line we are setting animation for our pie chart
        pieChart.animateY(1400, Easing.getEasingFunctionFromOption(Easing.EasingOption.EaseInOutQuad))

        // on below line we are disabling our legend for pie chart
        pieChart.legend.isEnabled = false
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(12f)


        // on below line we are creating array list and
        // adding data to it to display in pie chart



        // on below line we are setting pie data set
        val dataSet = PieDataSet(entries, "Mobile OS")

        // on below line we are setting icons.
        dataSet.setDrawIcons(false)


        // on below line we are setting slice for pie
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 0f

//        // add a lot of colors to list
       val colors: ArrayList<Int> = ArrayList()
//        colors.add(resources.getColor(R.color.purple_200))
//        colors.add(resources.getColor(R.color.yellow))
//        colors.add(resources.getColor(R.color.red))
        for ( i in arrColor){
            colors.add(resources.getColor(i))
        }

        // on below line we are setting colors.
        dataSet.colors = colors

        // on below line we are setting pie data set
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)

        pieChart.setDrawSliceText(false);
        data.setDrawValues(false);
        pieChart.setCenterText(label);
        pieChart.setCenterTextSize(18f);
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD)
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setData(data)
//
        // undo all highlights
        pieChart.highlightValues(null)

        // loading chart
        pieChart.invalidate()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress = showLoadingDialog(requireContext())
        //progress.show()
        careerViewModel.getCareerFilters()
        sessionDataResponse?.data?.users?.careerRegionPreference?.regionLevel?.let {
            sessionDataResponse?.data?.users?.careerRegionPreference?.regionName?.let { it1 ->
                regionCode = sessionDataResponse?.data?.users?.careerRegionPreference?.regionCode!!
                regionLevel =
                    sessionDataResponse?.data?.users?.careerRegionPreference?.regionLevel!!
                regionName = sessionDataResponse?.data?.users?.careerRegionPreference?.regionName!!
            }}
      setData()
        click()

    }

    private fun click() {
        mBinding.gender.text = getString(R.string.age)
        mBinding.genderTitle.visibility = View.VISIBLE
        mBinding.ageTitle.visibility = View.GONE
        mBinding.rbAge.setOnClickListener {
            val label = getString(R.string.age_label)
            mBinding.gender.text = getString(R.string.age)
            mBinding.genderTitle.visibility = View.VISIBLE
            mBinding.ageTitle.visibility = View.GONE
            var res :ArrayList<CareerFactsheetResponse.AgeBreakdownItem>?= null
            val entriesAgeBreakdown: ArrayList<PieEntry> = ArrayList()
            if (mBinding.rbRegion.isChecked) {
                res =response_regional?.ageBreakdown
                }
            else {
                res =response_national?.ageBreakdown
            }
            res?.let {
                for (i in it) {
                    entriesAgeBreakdown.add(PieEntry(i?.percent?.toFloat() ?: 0F))
                }
            }
             initPieChart(entriesAgeBreakdown, label)
            mBinding.ageList.adapter =
                AgeBackgroundAdapter(res, arrColor)

        }
        mBinding.rbRaceEthnicity.setOnClickListener {
            mBinding.age.text = getString(R.string.race_ethnicity)
            mBinding.genderTitle.visibility = View.GONE
            mBinding.ageTitle.visibility = View.VISIBLE
            var res :ArrayList<CareerFactsheetResponse.RaceEthnicityBreakdownItem>?= null
            val entriesAgeBreakdown: ArrayList<PieEntry> = ArrayList()
            if (mBinding.rbRegion.isChecked) {
                res =response_regional?.raceEthnicityBreakdown
            }
            else {
                res =response_national?.raceEthnicityBreakdown
            }
            res?.let {
                for (i in it) {
                    entriesAgeBreakdown.add(PieEntry(i?.percent?.toFloat() ?: 0F))
                }
            }
            val label = getString(R.string.race_label)
             initPieChart(entriesAgeBreakdown, label)
            mBinding.ageList.adapter =
                RaceEthicityAdapter(res, arrColor)

        }
        mBinding.rbGender.setOnClickListener {
            mBinding.gender.text = getString(R.string.gender)
            mBinding.genderTitle.visibility = View.VISIBLE
            mBinding.ageTitle.visibility = View.GONE
            val label = getString(R.string.gender_label)

            var res :ArrayList<CareerFactsheetResponse.GenderBreakdownItem>?= null
            val entriesAgeBreakdown: ArrayList<PieEntry> = ArrayList()
            if (mBinding.rbRegion.isChecked) {
                res =response_regional?.genderBreakdown
            }
            else {
                res =response_national?.genderBreakdown
            }
            res?.let {
                for (i in it) {
                    entriesAgeBreakdown.add(PieEntry(i?.percent?.toFloat() ?: 0F))
                }
            }
             initPieChart(entriesAgeBreakdown, label)
            mBinding.ageList.adapter =
                GenderAdapter(res, arrColor)

        }
        mBinding.rbEducation.setOnClickListener {
            mBinding.genderTitle.visibility = View.GONE
            mBinding.ageTitle.visibility = View.VISIBLE
            mBinding.age.text = getString(R.string.education_level)
            val entriesAgeBreakdown: ArrayList<PieEntry> = ArrayList()
            var res :ArrayList<CareerFactsheetResponse.EducationBreakdownItem>?= null
            if (mBinding.rbRegion.isChecked) {
                res =response_regional?.educationBreakdown
            }
            else {
                res =response_national?.educationBreakdown
            }
            res?.let {
                for (i in it) {
                    entriesAgeBreakdown.add(PieEntry(i?.percent?.toFloat() ?: 0F))
                }
            }
            val label = getString(R.string.edu_label)
             initPieChart(entriesAgeBreakdown, label)
            mBinding.ageList.adapter =
                EducationAdapter(res, arrColor)

        }
        mBinding.rbNation.setOnClickListener {
            if (mBinding.rbEducation.isChecked){
                mBinding.ageList.adapter = EducationAdapter(response_national?.educationBreakdown, arrColor)

                response_national?.educationBreakdown?.let {
                    val entriesAgeBreakdown: ArrayList<PieEntry> = ArrayList()
                    for (i in it) {
                        entriesAgeBreakdown.add(PieEntry(i?.percent?.toFloat() ?: 0F))
                    }
                    val label = getString(R.string.edu_label)
                     initPieChart(entriesAgeBreakdown, label)
                }

            } else if (mBinding.rbGender.isChecked){
                response_national?.genderBreakdown?.let {
                    val entriesAgeBreakdown: ArrayList<PieEntry> = ArrayList()
                    for (i in it) {
                        entriesAgeBreakdown.add(PieEntry(i?.percent?.toFloat() ?: 0F))
                    }
                    val label = getString(R.string.gender_label)
                     initPieChart(entriesAgeBreakdown, label)
                }
                mBinding.ageList.adapter = GenderAdapter(response_national?.genderBreakdown, arrColor)
            }else if (mBinding.rbAge.isChecked){
                response_national?.ageBreakdown?.let {
                    val entriesAgeBreakdown: ArrayList<PieEntry> = ArrayList()
                    for (i in it) {
                        entriesAgeBreakdown.add(PieEntry(i?.percent?.toFloat() ?: 0F))
                    }
                    val label = getString(R.string.age_label)
                     initPieChart(entriesAgeBreakdown, label)
                }
                mBinding.ageList.adapter = AgeBackgroundAdapter(response_national?.ageBreakdown, arrColor)
            }else if (mBinding.rbRaceEthnicity.isChecked){
                response_national?.raceEthnicityBreakdown?.let {
                    val entriesAgeBreakdown: ArrayList<PieEntry> = ArrayList()
                    for (i in it) {
                        entriesAgeBreakdown.add(PieEntry(i?.percent?.toFloat() ?: 0F))
                    }
                    val label = getString(R.string.race_label)
                     initPieChart(entriesAgeBreakdown, label)
                }
                mBinding.ageList.adapter = RaceEthicityAdapter(response_national?.raceEthnicityBreakdown, arrColor)
            }

        }
        mBinding.rbRegion.setOnClickListener {
            if (mBinding.rbEducation.isChecked){
                response_regional?.educationBreakdown?.let {
                    val entriesAgeBreakdown: ArrayList<PieEntry> = ArrayList()
                    for (i in it) {
                        entriesAgeBreakdown.add(PieEntry(i?.percent?.toFloat() ?: 0F))
                    }
                    val label = getString(R.string.edu_label)
                     initPieChart(entriesAgeBreakdown, label)
                }
                mBinding.ageList.adapter = EducationAdapter(response_regional?.educationBreakdown, arrColor)
            } else if (mBinding.rbGender.isChecked){
                response_regional?.genderBreakdown?.let {
                    val entriesAgeBreakdown: ArrayList<PieEntry> = ArrayList()
                    for (i in it) {
                        entriesAgeBreakdown.add(PieEntry(i?.percent?.toFloat() ?: 0F))
                    }
                    val label = getString(R.string.gender_label)
                     initPieChart(entriesAgeBreakdown, label)
                }
                mBinding.ageList.adapter = GenderAdapter(response_regional?.genderBreakdown, arrColor)
            }else if (mBinding.rbAge.isChecked){
                response_regional?.ageBreakdown?.let {
                    val entriesAgeBreakdown: ArrayList<PieEntry> = ArrayList()
                    for (i in it) {
                        entriesAgeBreakdown.add(PieEntry(i?.percent?.toFloat() ?: 0F))
                    }
                    val label = getString(R.string.age_label)
                     initPieChart(entriesAgeBreakdown, label)
                }
                mBinding.ageList.adapter = AgeBackgroundAdapter(response_regional?.ageBreakdown, arrColor)
            }else if (mBinding.rbRaceEthnicity.isChecked){
                response_regional?.raceEthnicityBreakdown?.let {
                    val entriesAgeBreakdown: ArrayList<PieEntry> = ArrayList()
                    for (i in it) {
                        entriesAgeBreakdown.add(PieEntry(i?.percent?.toFloat() ?: 0F))
                    }
                    val label = getString(R.string.race_label)
                     initPieChart(entriesAgeBreakdown, label)
                }
                mBinding.ageList.adapter = RaceEthicityAdapter(response_regional?.raceEthnicityBreakdown, arrColor)
            }

        }
    }

    private fun setData() {
        response = response_regional?.let {
            response_regional
        } ?: response_national
        if (checkNonNull(response?.title)) {
            mBinding.title.text = "What is ${response?.title}"
        }
        if (checkNonNull(response?.description)) {
            mBinding.description.text = "${response?.description}"
        }
        mBinding.videoView.setOnClickListener {
            if (response?.videoUrl != null && response?.title != null)
                bottomSheetDetail(response?.title!!, response?.videoUrl!!)
        }
        mBinding.changeRegion.setOnClickListener {
            bottomSheetChangeRegion()
        }
        response?.videoUrl?.let {
            val videoId = extractYoutubeId(response?.videoUrl)

            val img_url =
                "http://img.youtube.com/vi/" + videoId.toString() + "/0.jpg" // this is link which will give u thumnail image of that video
            Picasso.with(context)
                .load(img_url)
                .error(R.drawable.static_coll).into(mBinding.videoView)

        }

        mBinding.careerClusterList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.careerClusterList.adapter = FactsheetCareerClusterAdapter(response?.categories)

        mBinding.pathwayList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.pathwayList.adapter = FactsheetPathwayAdapter(response?.pathways)

        mBinding.actView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.actView.adapter =
            WorkActivityCareerAdapter(response?.workActivities as ArrayList<String>?)

        mBinding.relatedCareersList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.relatedCareersList.adapter =
            RelatedAdapter(response?.relatedCareers as ArrayList<CareerFactsheetResponse.RelatedCareersItem>?)
        if (mBinding.rbRegion.isChecked)
            mBinding.ageList.adapter =
                AgeBackgroundAdapter(response_regional?.ageBreakdown, arrColor)
        else
            mBinding.ageList.adapter =
                AgeBackgroundAdapter(response_national?.ageBreakdown, arrColor)
        val entriesAgeBreakdown: ArrayList<PieEntry> = ArrayList()
        response_national?.ageBreakdown?.let {
            for (i in it) {
                entriesAgeBreakdown.add(PieEntry(i?.percent?.toFloat() ?: 0F))
            }
        }
        val label = getString(R.string.age_label)
         initPieChart(entriesAgeBreakdown, label)

        mBinding.txtRegion.text ="${getString(R.string.select_region)}"

        setDemographics(response_regional, response_national)
    }

    private fun observers() {
        careerViewModel.careerListFactsheetObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            selectRegionInterestSheet?.dismiss()
            val gson = GsonBuilder().create()
            response_regional = gson.fromJson(it, CareerFactsheetResponse::class.java)
            setData()
            //  initView()
        }
    careerViewModel.getCareerFilterObserver.observe(viewLifecycleOwner){
        progress.dismiss()
        filtersFactsheetResponse = it
    }
        careerViewModel.careerPrefObserver.observe(viewLifecycleOwner){
            progress.dismiss()
            selectRegionInterestSheet?.dismiss()
            val obj = JSONObject(it.toString())
            if (obj.optJSONObject("data").optJSONObject("career_region_preference")!= null){
                if (!checkNonNull(obj.optJSONObject("data").optJSONObject("career_region_preference").optString("region_code"))) {
                    regionLevel = ""
                    regionName = ""
                    regionCode = ""
                    response_regional = null
                }
                else {
                    regionCode = obj.optJSONObject("data").optJSONObject("career_region_preference").optString("region_code")
                    regionLevel = obj.optJSONObject("data").optJSONObject("career_region_preference").optString("region_level")
                    regionName= obj.optJSONObject("data").optJSONObject("career_region_preference").optString("region_name")
                    if (!checkNonNull(regionLevel)) {
                      //  context?.showToast(getString(R.string.region_type))
                    } else if (!checkNonNull(regionCode)) {
                      //  context?.showToast(getString(R.string.region_name))
                    } else {
                        progress.show()
                   regionCode?.let {it1->
                            careerViewModel.getCareerFactsheetDetail("" + sessionDataResponse?.onet_id,regionLevel,it1)
                        }

                    }
                }

            }
        }
    }

    private fun setDemographics(regionalData: CareerFactsheetResponse?, nationalData: CareerFactsheetResponse?) {

        var totalEmployment = 0
        var regionalDiversityPercent:Double = 0.0
        var nationalDiversityPercent:Double = 0.0
        var racialDiversityRegional = 0
        var racialDiversityNational = 0
        var retiringSoonRegional = 0
        var retiringSoonNational = 0
        var genderDiversityRegional = 0
        var genderDiversityNational = 0
        var maxRacialDiversity = 0
        var maxRetiringSoon = 0
        var maxGenderDiversity = 0
        totalEmployment=  regionalData?.totalEmployment?.let {
            regionalData?.totalEmployment
        } ?: nationalData?.totalEmployment?:0

      regionalData?.totalEmployment?.let {
             regionalDiversityPercent = regionalData.raceEthnicityBreakdown?.let { it1 ->
                 getDiversityPercent(
                     it1, regionalData.totalEmployment?:0)
             }?:0.0
        }
        nationalData?.totalEmployment?.let {
            nationalDiversityPercent = nationalData.raceEthnicityBreakdown?.let { it1 ->
                getDiversityPercent(
                    it1, nationalData.totalEmployment?:0)
            }?:0.0
        }
        racialDiversityRegional = getPercentage(regionalDiversityPercent, totalEmployment)

        racialDiversityNational = getPercentage(nationalDiversityPercent, totalEmployment)
        maxRacialDiversity = getMaxValue(racialDiversityRegional, racialDiversityNational)

        retiringSoonRegional =
            regionalData?.let { it.percentRetiringSoon?.
                let { it1 -> getPercentage(it1, totalEmployment) } }?:0

        retiringSoonNational =
            nationalData?.let { it.percentRetiringSoon?.let { it1 -> getPercentage(it1, totalEmployment) } }?:0

        genderDiversityRegional =
            regionalData?.percentFemale?.let { this.getPercentage(it, totalEmployment) }?:0

        genderDiversityNational =
            nationalData?.percentFemale?.let { this.getPercentage(it, totalEmployment) }?:0

        maxRetiringSoon = this.getMaxValue(retiringSoonRegional, retiringSoonNational)
        maxGenderDiversity = this.getMaxValue(genderDiversityRegional, genderDiversityNational)

        mBinding.retiringAverageProgress.max = maxRetiringSoon
        mBinding.retiringAverageProgress.progress = retiringSoonNational

        mBinding.retiringStateProgress.max = maxRetiringSoon
        mBinding.retiringStateProgress.progress = retiringSoonRegional

        mBinding.racialAverageProgress.max = maxRacialDiversity
        mBinding.racialAverageProgress.progress = racialDiversityNational

        mBinding.racialStateProgress.max = maxRacialDiversity
        mBinding.racialStateProgress.progress = racialDiversityRegional

        mBinding.genderAverageProgress.max = maxGenderDiversity
        mBinding.genderAverageProgress.progress = genderDiversityRegional

        mBinding.genderStateProgress.max = maxGenderDiversity
        mBinding.genderStateProgress.progress = genderDiversityNational


regionLevel?.let {
regionName?.let {it1->
    mBinding.txtRegion.text =
        "${getString(R.string.default_selection)}${it.toUpperCase()} ${getString(R.string.space)} ${it1.toUpperCase()}"
    mBinding.radioRegion.visibility = View.VISIBLE
    mBinding.rbRegion.setText(it1.toUpperCase())
    mBinding.rbNation.setText("US")
    mBinding.retiringState.text = "${it1.toUpperCase()}"
    mBinding.retiringDescription.text =
        "Retirement risk is ${
            getStatus(
                retiringSoonNational,
                retiringSoonRegional
            )
        } in ${it1.toUpperCase()}. The national average for an area of this size is ${
            format(
                retiringSoonNational
            )
        } employees 55 or older, while there are  ${format(retiringSoonRegional)} in ${it1.toUpperCase()}"

    mBinding.racialState.text = "${it1.toUpperCase()}"
    mBinding.racialDescription.text =
        "Racial Diversity is ${
            getStatus(
                racialDiversityNational,
                racialDiversityNational
            )
        } in ${it1.toUpperCase()}. The national average for an area of this size is ${
            format(
                racialDiversityNational
            )
        } racially diverse employees, while there are  ${format(racialDiversityRegional)} in ${it1.toUpperCase()}"
    mBinding.genderState.text = "${it1.toUpperCase()}"
    mBinding.genderDescription.text =
        "Gender Diversity is ${
            getStatus(
                genderDiversityNational,
                genderDiversityRegional
            )
        } in ${it1.toUpperCase()}. The national average for an area of this size is ${
            format(
                genderDiversityNational
            )
        } female employees, while there are  ${format(genderDiversityRegional)} in ${it1.toUpperCase()}"

}}
        if (regionalData == null){
            mBinding.genderStateProgress.visibility = View.GONE
            mBinding.racialStateProgress.visibility = View.GONE
            mBinding.retiringStateProgress.visibility = View.GONE
            mBinding.racialState.visibility = View.GONE
            mBinding.genderState.visibility = View.GONE
            mBinding.retiringState.visibility = View.GONE
            mBinding.retiringDescription.text =
                 "The national average is ${format(retiringSoonNational)} employees 55 or older"

            mBinding.racialDescription.text =
                "The national average is ${format(racialDiversityNational)} racially diverse employees"

            mBinding.genderDescription.text =
                "The national average is an ${format(genderDiversityNational)} female employees"

        }


    }

    fun getDiversityPercent(
        ethnicityBreakdown: ArrayList<CareerFactsheetResponse.RaceEthnicityBreakdownItem>,
        totalEmployment: Int
    ): Double {
        var jobs = 0
        for (i in ethnicityBreakdown){
            jobs += i.jobs?:0
       }
        println("jobs : "+ (jobs))
        println("diversitypercent : "+ ((jobs.toDouble()*100 /totalEmployment.toDouble()) ))
        var res: Double =  ((jobs.toDouble()*100 /totalEmployment.toDouble()) )
        if (res.isNaN()){
            res = 0.0
        }
        return res
    }

   fun getPercentage(percent: Double, employment: Int): Int {
       var percentage:Double = (percent/ 100) * employment
       println("getPercentage : "+ ((percent/ 100) * employment))
       if (percentage.isNaN()){
           percentage =0.0
       }
       println("percent : "+ percentage.roundToInt())
        return percentage.roundToInt()
    }

   fun getMaxValue(regional: Int, national: Int): Int {
       println("Reg "+regional)
       println("Nat "+national)
       if ( regional > national * 2) {
           return regional
       }
       else {
           return national * 2
       }
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

    private fun bottomSheetChangeRegion() {
        var selectedAdapter = 0

        selectRegionInterestSheet = BottomSheetDialog(requireContext())

        selectRegionInterestSheetBinding= SelectRegionInterestSheetBinding.inflate(layoutInflater)
        selectRegionInterestSheetBinding?.root?.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        selectRegionInterestSheetBinding?.root?.let { selectRegionInterestSheet?.setContentView(it) }

        selectRegionInterestSheet?.show()

       // val adapter = RegionTypeAdapter(requireContext(), resources.getStringArray(R.array.REGION_TYPE), ::clickRegion)
        val adapter = ArrayAdapter(
          requireContext(),
            R.layout.spinner_region_type,  resources.getStringArray(R.array.REGION_TYPE_UNSET)
        )
        selectRegionInterestSheetBinding?.regionTypeSpinner?.setAdapter(
            NothingSelectedSpinnerAdapter(
                adapter,
                R.layout.nothing_adapter_region,  // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                requireContext()
            )
        )
        selectRegionInterestSheetBinding?.regionTypeSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (  selectRegionInterestSheetBinding?.regionTypeSpinner?.selectedItemPosition == 1){
                    selectRegionInterestSheetBinding?.outSpinnerText4?.text = getString(R.string.enter_name)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
//    }
        when(regionLevel){
            "state"-> {
                selectRegionInterestSheetBinding?.regionTypeSpinner?.setSelection(2)
                selectRegionInterestSheetBinding?.outSpinnerText4?.text = regionName
            }
            "msa"-> {
                selectRegionInterestSheetBinding?.regionTypeSpinner?.setSelection(3)
                selectRegionInterestSheetBinding?.outSpinnerText4?.text = regionName
            }
            "county" -> {
                selectRegionInterestSheetBinding?.regionTypeSpinner?.setSelection(4)
                selectRegionInterestSheetBinding?.outSpinnerText4?.text = regionName
            }
            else ->{
                selectRegionInterestSheetBinding?.regionTypeSpinner?.setSelection(1)
                selectRegionInterestSheetBinding?.outSpinnerText4?.text = getString(R.string.enter_name)
            }
        }

        selectRegionInterestSheetBinding?.outSpinnerText4?.setOnClickListener {
            if (selectRegionInterestSheetBinding?.regionTypeSpinner?.selectedItemPosition != 0) {
                filtersFactsheetResponse?.let { it1 ->
                    showRegionNameBottomSheet(
                        selectRegionInterestSheetBinding?.regionTypeSpinner?.selectedItem.toString(),
                        it1
                    )
                }
            }
        }

        selectRegionInterestSheetBinding?.backBtn?.setOnClickListener {
            selectRegionInterestSheet?.dismiss()
        }
        selectRegionInterestSheetBinding?.saveBtn?.setOnClickListener {
            if (selectRegionInterestSheetBinding?.savePref?.isChecked == false){
                if (!checkNonNull(regionName)){
                   selectRegionInterestSheet?.dismiss()
                } else if (!checkNonNull(regionCode)){
                    selectRegionInterestSheet?.dismiss()
                } else {
                    progress.show()
                    regionCode?.let {it1->
                        careerViewModel.getCareerFactsheetDetail("" + sessionDataResponse?.onet_id,regionLevel,it1)
                    }

                }} else{
                progress.show()
                careerViewModel.careerPref(regionLevel, regionCode, regionName)
            }
        }

    }
    fun getStatus(nationalCount:Int, regionalCount:Int):String{
        var percent = 0
        if (nationalCount!=0) {
                percent = ((regionalCount - nationalCount)  * 100 / nationalCount)
        }
        if (percent>20){
            return "high"
        } else if (percent>10){
            return "above average"
        }else if (percent < -20){
            return "below average"
        } else{
            return "average"
        }
//        const index =
//        percent > 20
//        ? 'CAREER.FACTSHEET.DEMOGRAPHICS_TEXT.INDEX.HIGH'
//        : percent > 10
//        ? 'CAREER.FACTSHEET.DEMOGRAPHICS_TEXT.INDEX.ABOVE_AVERAGE'
//        : percent < -20
//        ? 'CAREER.FACTSHEET.DEMOGRAPHICS_TEXT.INDEX.LOW'
//        : percent < -10
//        ? 'CAREER.FACTSHEET.DEMOGRAPHICS_TEXT.INDEX.BELOW_AVERAGE'
//        : 'CAREER.FACTSHEET.DEMOGRAPHICS_TEXT.INDEX.AVERAGE';
    }



    private fun showRegionNameBottomSheet(type: String, resp:CareerFilterResponse) {
        if (type == "State")
            regionLevel = "state"
        else if (type == "County")
            regionLevel = "county"
        else if (regionLevel == "Metropolitan Area"){
            regionLevel = "msa"
        }
        

        dialogRegionNameBottomsheetBinding = BottomSheetDialog(requireContext())

        val sheetBinding: CareerFilterBottomsheetBinding = CareerFilterBottomsheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
        dialogRegionNameBottomsheetBinding?.setContentView(sheetBinding.root)
        dialogRegionNameBottomsheetBinding?.show()

        sheetBinding. backBtn.setOnClickListener {
            dialogRegionNameBottomsheetBinding?.dismiss()
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
        AppCompatResources.getDrawable(requireContext(), R.drawable.divider)?.let { decor.setDrawable(it) }
        sheetBinding.listing.addItemDecoration(decor)
        setRegionNameAdapter(type,resp, sheetBinding)


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
        } else{
        }
    }
    private fun clickStateItem(stateItem: StateItem) {
        dialogRegionNameBottomsheetBinding?.dismiss()
        selectRegionInterestSheetBinding?.outSpinnerText4?.text= stateItem.name
        stateItem?.id?.let {  regionCode =it
        }
        stateItem?.name?.let { regionName = it }

    }
    private fun clickCountyItem(countyItem: CountyItem) {
        dialogRegionNameBottomsheetBinding?.dismiss()
        selectRegionInterestSheetBinding?.outSpinnerText4?.text = countyItem.name
        countyItem?.id?.let {  regionCode =it }
        countyItem?.name?.let { regionName = it }
    }
    private fun clickMsaItem(msaItem: MsaItem) {
        dialogRegionNameBottomsheetBinding?.dismiss()
        selectRegionInterestSheetBinding?.outSpinnerText4?.text = msaItem.name
        msaItem?.id?.let {  regionCode =it }
        msaItem?.name?.let { regionName = it }
    }

}
