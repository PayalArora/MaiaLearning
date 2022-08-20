package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.databinding.SearchLayoutBinding
import com.maialearning.model.UniversitiesSearchModel
import com.maialearning.model.UniversitySearchPayload
import com.maialearning.parser.SearchParser
import com.maialearning.ui.adapter.UniFactAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {
    private lateinit var mBinding: SearchLayoutBinding
    private val homeModel: HomeViewModel by viewModel()
    private lateinit var progress: Dialog
    var page: Int = 1
    var universityList: ArrayList<UniversitiesSearchModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = SearchLayoutBinding.inflate(inflater, container, false)
        progress = showLoadingDialog(requireContext())

        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment: SupportMapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.map, mapFragment)
            .commit()
        hitAPI(page, "")

        homeModel.searchUniversityObserver.observe(requireActivity()) {
            val univ = SearchParser(it).parseJson()
            progress.dismiss()
            //    mBinding.swipyrefreshlayout.isRefreshing=false
            universityList = univ.university_list

            mBinding.rvUniv.adapter =
                univ.university_list?.let { it1 -> UniFactAdapter(requireContext(), it1, ::click) }
        }
//        mBinding.swipyrefreshlayout.setOnRefreshListener(object :
//            SwipyRefreshLayout.OnRefreshListener {
//            override fun onRefresh(direction: SwipyRefreshLayoutDirection?) {
//                if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
//                    page = page + 1
//                    hitAPI(page)
//                }
//                }else if(direction == SwipyRefreshLayoutDirection.TOP){
//                    if(page!=1 && page>1){
//                        page=page-1
//                        hitAPI(page)
//                    }
//                }
//            }
//
//        })
        // bottomSheetList()

        homeModel.likeObserver.observe(requireActivity()) {
            progress.dismiss()
//            hitAPI(page)
        }
        homeModel.unlikeObserver.observe(requireActivity()) {
            progress.dismiss()
//            hitAPI(page)
        }
        homeModel.showError.observe(requireActivity()) {
            progress.dismiss()
            Log.e("Error", "err" + it)
        }

        mBinding.searchText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                page = 1
                hitAPI(page, mBinding.searchText.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
        mBinding.close.setOnClickListener {
            mBinding.searchText.setText("")
            page = 1
            hitAPI(page, "")
        }
    }

    private fun hitAPI(pageNo: Int, search: String) {
        progress.show()
        val payload = UniversitySearchPayload()
        val country = SharedHelper(requireContext()).country?: "US"
        payload.student_uid = SharedHelper(requireContext()).id.toString()
        payload.country = country
        payload.pager = pageNo
        payload.search = search
        payload.sort_parameter = "college_name"
        payload.sort_order = "asc"
        universityList?.clear()
        homeModel.searchUniversities(payload)
    }

    private fun bottomSheetList() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.search_bottom_sheet, null)
        view.minimumHeight = Resources.getSystem().displayMetrics.heightPixels / 3
//

        val listing = view.findViewById<RecyclerView>(R.id.listing)
//        val bottomSheet = view.findViewById<View>(R.id.listing)
//        val mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
//        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        listing.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
//        listing.adapter = UniFactAdapter(requireContext(), ::click)
//        close.setOnClickListener {
//            dialog.dismiss()
//        }

        dialog.setContentView(view)
        dialog.show()
    }

    fun click(position: Int) {
        likeWork(universityList!!.get(position))
    }

    fun likeWork(get: UniversitiesSearchModel) {
        if (get.topPickFlag == 0) {
            hitLikeWork(get)
        } else {
            hitUnlikeWork(get)
        }
    }

    private fun hitUnlikeWork(get: UniversitiesSearchModel) {
        progress.show()
        SharedHelper(requireContext()).id?.let {
            get.nid?.let { it1 ->
                homeModel.hitunlike(
                    it,
                    it1
                )
            }
        }
    }

    private fun hitLikeWork(get: UniversitiesSearchModel) {
        progress.show()

        SharedHelper(requireContext()).id?.let {
            get.nid?.let { it1 ->
                homeModel.hitlike(
                    it,
                    it1
                )
            }
        }
    }
}