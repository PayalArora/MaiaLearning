package com.maialearning.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.GsonBuilder
import com.maialearning.R
import com.maialearning.databinding.ActivityCareerBinding
import com.maialearning.model.CareerFactsheetResponse
import com.maialearning.model.CareerSearchResponseItem
import com.maialearning.model.SessionDataResponse
import com.maialearning.ui.adapter.CareerFactsheetStateAdapter
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.CareerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchDetailFragment : Fragment() {
    var dialog: BottomSheetDialog? = null
    private lateinit var binding: ActivityCareerBinding
    private lateinit var toolbarBinding: Toolbar
    private val careerViewModel: CareerViewModel by viewModel()
    var careerSearchResponseItem:CareerSearchResponseItem?= null
    var sessionDataResponse:SessionDataResponse?= null
    private lateinit var progress: Dialog
    var itModel : CareerFactsheetResponse? = null
    var itModelNational : CareerFactsheetResponse? = null
    var regionName = ""
    var regionLevel = "nation"
    var regionCode = "0"

    val mediatorLiveData: MediatorLiveData<CareerFactsheetResponse> =  MediatorLiveData<CareerFactsheetResponse>()

    val liveData1:MutableLiveData<CareerFactsheetResponse>  = MutableLiveData<CareerFactsheetResponse>();
    val liveData2: MutableLiveData<CareerFactsheetResponse> = MutableLiveData<CareerFactsheetResponse>();



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityCareerBinding.inflate(inflater, container, false)
        toolbarBinding = binding.toolbar
        val toolbar: Toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.visibility = View.VISIBLE
        progress = showLoadingDialog(requireContext())
        binding.toolbar.contentInsetStartWithNavigation = 0
        binding.toolbar.setNavigationIcon(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_keyboard_arrow_left_24
            )
        )
        binding.toolbar.title =  arguments?.getString("title")
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.GONE
        toolbarBinding.setNavigationOnClickListener {
            requireActivity().onBackPressed()
            toolbar.visibility = View.GONE
        }
        arguments?.getSerializable("data")?.let {
            careerSearchResponseItem = it as CareerSearchResponseItem
        }

        binding.toolbarProf.setOnClickListener {
            //  ProfileFilter(this, layoutInflater).showDialog()
        }

//        binding.addFab.setOnClickListener {
//            bottomSheetCompareList()
//        }

        progress.show()
        careerViewModel.getCareerSessionData()
       // careerViewModel.getCareerFactsheetDetail("" + careerSearchResponseItem?.onetId,"nation","0")
        observers()
        return binding.root
    }
    private fun observers(){
        mediatorLiveData.addSource(liveData1, {
            mediatorLiveData.setValue(it);
            }
        );
        mediatorLiveData.addSource(liveData2,
            {
                mediatorLiveData.setValue(it);
            }
        );
        mediatorLiveData.observe(viewLifecycleOwner,{ s ->
            initView()
        })
        careerViewModel.getSessionObserver.observe(viewLifecycleOwner){
            progress.dismiss()
            progress.show()
            sessionDataResponse = it
            sessionDataResponse?.onet_id = careerSearchResponseItem?.onetId
            it.data?.users?.careerRegionPreference?.regionCode?.let {it1->
                careerViewModel.getCareerFactsheetDetail("" + careerSearchResponseItem?.onetId,it?.data?.users?.careerRegionPreference?.regionLevel,it1)
            }
            careerViewModel.getCareerFactsheetDetailNational("" + careerSearchResponseItem?.onetId,"nation","0")
        }
        careerViewModel.careerListFactsheetObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            val gson = GsonBuilder().create()
            itModel = gson.fromJson(it, CareerFactsheetResponse::class.java)
            liveData1.postValue(itModel)
          //  initView()
        }
        careerViewModel.careerListFactsheetNationalObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            val gson = GsonBuilder().create()
            itModelNational = gson.fromJson(it, CareerFactsheetResponse::class.java)
            liveData2.postValue(itModelNational)
           // initView()
        }
    }


    private fun initView() {
        val tabArray = arrayOf(
            getString(R.string.summary),
            getString(R.string.rel_car),
            getString(R.string.salaries)
        )
        for (item in tabArray) {
            binding.tabs.addTab(binding.tabs.newTab().setText(item))

        }
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val adapter = CareerFactsheetStateAdapter(fm, lifecycle, tabArray.size, itModel,itModelNational, sessionDataResponse)

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.setText(tabArray[position])
        }.attach()


        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (binding.tabs.selectedTabPosition != 0 && binding.tabs.selectedTabPosition != 1) {
                    binding.addFab.visibility = View.GONE
                } else
                    binding.addFab.visibility = View.GONE
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

//        TabLayoutMediator(mBinding.tabs, mBinding.viewPager) { tab, position ->
//        }.attach()
    }

//    private fun bottomSheetCompareList() {
//        var onet_code = ArrayList<String>()
//        var compareList = ArrayList<BrightOutlookModel.Data>()
//
//        if (careerTopPickResponseItem != null) {
//            careerTopPickResponseItem.onetId?.let { onet_code.add(it) }
//        }
//        var data=BrightOutlookModel.Data(careerTopPickResponseItem.onetId,careerTopPickResponseItem.title,
//            careerTopPickResponseItem.education as ArrayList<String>,careerTopPickResponseItem.salary,onet_code,null,false
//        )
//        compareList.add(data)
//
//        if (compareList.size < 1) {
//            Toast.makeText(activity, "Please select careers first", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        dialog = BottomSheetDialog(requireContext())
//        val view = layoutInflater.inflate(R.layout.compare_careers, null)
//        view.minimumHeight = ((Resources.getSystem().displayMetrics.heightPixels))
//
//        val listing = view.findViewById<RecyclerView>(R.id.listing)
//        val layout = view.findViewById<ConstraintLayout>(R.id.layout)
//        val close = view.findViewById<RelativeLayout>(R.id.close)
//        DrawableCompat.setTint(layout.background, resources.getColor(R.color.white_1, getActivity()?.getTheme()))
//
//        listing.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
//        close.setOnClickListener {
//            dialog?.dismiss()
//        }
//        val body: CompareCareerBody = CompareCareerBody()
//        body.onet_code = onet_code
//        careerViewModel.compareCareers(body)
//        progress.show()
//        careerViewModel.careerComparisonsObserver.observe(requireActivity()) {
//            Log.e("DATA", "" + it.toString())
//            progress.dismiss()
//            val gson = Gson()
//            val json = gson.toJson(it)
//            val resp = JSONObject(json)
//            listing.adapter = CareerCompareAdapter(requireContext(), compareList, resp)
//            dialog?.show()
//        }
//        dialog?.setContentView(view)
//
//    }

}