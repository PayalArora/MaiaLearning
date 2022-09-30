package com.maialearning.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maialearning.R
import com.maialearning.databinding.ActivityCareerBinding
import com.maialearning.model.BrightOutlookModel
import com.maialearning.model.CareerTopPickResponseItem
import com.maialearning.model.CompareCareerBody
import com.maialearning.model.SelectedCareerResponse
import com.maialearning.ui.adapter.CareerCompareAdapter
import com.maialearning.ui.adapter.TraficStateAdapter
import com.maialearning.util.CARRER_URL
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.CareerViewModel
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TraficFragment : Fragment() {
    var dialog: BottomSheetDialog? = null
    private lateinit var binding: ActivityCareerBinding
    private lateinit var toolbarBinding: Toolbar
    private val careerViewModel: CareerViewModel by viewModel()
    var careerTopPickResponseItem = CareerTopPickResponseItem()
    private lateinit var progress: Dialog
    var itModel = SelectedCareerResponse()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityCareerBinding.inflate(inflater, container, false)
        toolbarBinding = binding.toolbar
        progress = showLoadingDialog(requireContext())
        binding.toolbar.contentInsetStartWithNavigation = 0
        binding.toolbar.setNavigationIcon(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_keyboard_arrow_left_24
            )
        )
        binding.toolbar.title = getString(R.string.air_trafic)
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.GONE
        toolbarBinding.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        careerTopPickResponseItem = arguments?.getSerializable("data") as CareerTopPickResponseItem

        binding.toolbarProf.setOnClickListener {
            //  ProfileFilter(this, layoutInflater).showDialog()
        }

        binding.addFab.setOnClickListener {
            bottomSheetCompareList()
        }

        progress.show()
        careerViewModel.getCareerListDetail("" + careerTopPickResponseItem.ccode, CARRER_URL)
        careerViewModel.careerListDetailObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            val gson = GsonBuilder().create()
            itModel = gson.fromJson(it, SelectedCareerResponse::class.java)
            initView()
        }
        return binding.root
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
        val adapter = TraficStateAdapter(fm, lifecycle, tabArray.size, itModel)

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

    private fun bottomSheetCompareList() {
        var onet_code = ArrayList<String>()
        var compareList = ArrayList<BrightOutlookModel.Data>()

        if (careerTopPickResponseItem != null) {
            careerTopPickResponseItem.ccode?.let { onet_code.add(it) }
        }
        var data=BrightOutlookModel.Data(careerTopPickResponseItem.ccode,careerTopPickResponseItem.title,
            careerTopPickResponseItem.education as ArrayList<String>,careerTopPickResponseItem.salary,onet_code,false
        )
        compareList.add(data)

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
        DrawableCompat.setTint(layout.background, Color.parseColor("#E5E5E5"))

        listing.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        close.setOnClickListener {
            dialog?.dismiss()
        }
        val body: CompareCareerBody = CompareCareerBody()
        body.onet_code = onet_code
        careerViewModel.compareCareers(body)
        progress.show()
        careerViewModel.careerComparisonsObserver.observe(requireActivity()) {
            Log.e("DATA", "" + it.toString())
            progress.dismiss()
            val gson = Gson()
            val json = gson.toJson(it)
            val resp = JSONObject(json)
            listing.adapter = CareerCompareAdapter(requireContext(), compareList, resp)
            dialog?.show()
        }
        dialog?.setContentView(view)

    }

}