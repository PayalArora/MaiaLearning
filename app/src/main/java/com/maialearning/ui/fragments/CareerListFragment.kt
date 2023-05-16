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
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maialearning.R
import com.maialearning.databinding.*
import com.maialearning.model.*
import com.maialearning.parser.CareerListParser
import com.maialearning.ui.adapter.*
import com.maialearning.util.*
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.viewmodel.CareerViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.collections.ArrayList


class CareerListFragment() : Fragment() {
    var dialog: BottomSheetDialog? = null
    private lateinit var mBinding: LayoutCareerListBinding
    private val careerViewModel: CareerViewModel by viewModel()
    private lateinit var progress: Dialog
    lateinit var gson: Gson

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
    val mediatorLiveData: MediatorLiveData<String> =  MediatorLiveData<String>()

    val liveData1: MutableLiveData<String> = MutableLiveData<String>();
    val liveData2:  MutableLiveData<String> = MutableLiveData<String>();
    var  responseCareerList: CareerListResponse? = null
    var count = 0

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

        mBinding = LayoutCareerListBinding.inflate(inflater, container, false)
        tableLayout = requireActivity().findViewById<TabLayout>(R.id.tabs)
        val fab = activity?.findViewById<RelativeLayout>(R.id.add_fab)
        fab?.visibility = View.GONE
        progress.show()
        SharedHelper(requireContext()).id?.let {
            careerViewModel.getStudentTopPick(it)
        }
        careerViewModel.getCareerList()
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }

    var arrayList: ArrayList<CareerTopPickResponseItem?>? = arrayListOf()



    private fun initObserver() {
        mediatorLiveData.addSource(liveData1, {
            println("payal 4")
            count ++
            mediatorLiveData.setValue(it);
        }
        );
        mediatorLiveData.addSource(liveData2,
            {
                println("payal 5")
                count ++
                mediatorLiveData.setValue(it);
            }
        );
        mediatorLiveData.observe(viewLifecycleOwner,{ s ->
            if (count>1) {
                responseCareerList?.careerList?.let {
                    val firstListIds = arrayTopPick.map { it.code }
                    responseCareerList?.careerList =
                        it.filter { it.onetId in firstListIds } as ArrayList<CareerListResponse.CareerItem>

                    responseCareerList?.careerList?.sortBy { it.title }
                    println("career 1 ${responseCareerList?.careerList?.size}")
                    mBinding.listProgram.adapter =
                        ListCareerAdapter(
                            requireContext(),
                            responseCareerList?.careerList,
                            ::itemClick, ::openFactsheet
                        )
                }
                progress.dismiss()
//                lifecycleScope.launch(Dispatchers.Main) {
//                delay(8000)
//                  }


            }
        })
        careerViewModel.getCareerFilterObserver.observe(viewLifecycleOwner){
            progress.dismiss()
            filtersFactsheetResponse = it
        }

        careerViewModel.topPickObserver.observe(viewLifecycleOwner) {

            arrayTopPick = arrayListOf()
            if (it != null) {
                for (i in it) {
                    val toppick = gson.fromJson(i, RelatedCareerModel::class.java)
                    arrayTopPick.add(toppick)
                }

            }
            liveData2.postValue("q")
            println("payal 2")
        }

        careerViewModel.showError.observe(viewLifecycleOwner) {
            progress.dismiss()
        }

//        careerViewModel.careerListObserver.observe(viewLifecycleOwner) {
//
//            if (it.size() > 0) {
//                arrayList = arrayListOf()
//
//                for (i in it) {
//                    val itModel = gson.fromJson(i, CareerTopPickResponseItem::class.java)
//                    arrayList?.add(itModel)
//                }
//                arrayList?.sortBy { it?.title }
//                adapType = 2
////                mBinding.listProgram.adapter =
////                    SearchProgramAdapter(requireContext(), arrayList, null, "key", ::loadFragment)
//
//
//                // val listing = itModel.careerTopPickResponse
//                Log.e("Data", " " + arrayList?.size)
//            }
//            progress.dismiss()
//        }
        careerViewModel.careerObserver.observe(viewLifecycleOwner){
          //  progress.dismiss()
          responseCareerList = CareerListParser(it).parse()
            liveData1.postValue("q")
            println("payal 3")

        }
    }

    private fun itemClick(url: String?, title: String?) {
        if (url != null && title != null)
            bottomSheetDetail(title, url)
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

    fun openFactsheet(careerSearchResponseItem: CareerListResponse.CareerItem, position: Int) {
        val fragment = SearchDetailFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
       val item = CareerSearchResponseItem(careerSearchResponseItem.videoUrl, careerSearchResponseItem.description,
       careerSearchResponseItem.averageSalary.toString(), careerSearchResponseItem.title, careerSearchResponseItem.onetId,"",false, careerSearchResponseItem.nid,"" )
        bundle.putSerializable("data", item)
        bundle.putString("title",careerSearchResponseItem.title)
        fragment.arguments = bundle
        transaction.add(R.id.nav_host_fragment_content_dashboard, fragment)
        transaction.addToBackStack("name")
        transaction.commit()
    }
}




