package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.maialearning.model.GermanUniversitiesResponse
import com.maialearning.model.UniversitiesSearchModel
import com.maialearning.model.UniversitySearchPayload
import com.maialearning.parser.SearchParser
import com.maialearning.ui.adapter.GermanFactAdapter
import com.maialearning.ui.adapter.UniFactAdapter
import com.maialearning.util.OnLoadMoreListener
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {
    private lateinit var mBinding: SearchLayoutBinding
    private val homeModel: HomeViewModel by viewModel()
    private lateinit var progress: Dialog
    var page: Int = 1
    var universityListUpdate: ArrayList<UniversitiesSearchModel?>? = ArrayList()
    var universityList: ArrayList<UniversitiesSearchModel?>? = ArrayList()
    var university_listNew = ArrayList<UniversitiesSearchModel?>()

    var germanListUpdate: ArrayList<GermanUniversitiesResponse.Data.CollegeData?>? = ArrayList()
    var germanList: ArrayList<GermanUniversitiesResponse.Data.CollegeData?>? = ArrayList()
    var german_listNew = ArrayList<GermanUniversitiesResponse.Data.CollegeData?>()
    private var isLoading = false

    lateinit var adapter: UniFactAdapter
    lateinit var germanAdapter: GermanFactAdapter
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
        if (!(SharedHelper(requireContext()).country ?: "US").equals("DE")) {
            adapter = UniFactAdapter(
                requireContext(),
                university_listNew,
                ::click,
                mBinding.rvUniv
            )
            mBinding.rvUniv.adapter = adapter
            adapter.setOnLoadMoreListener(object : OnLoadMoreListener {
                override fun onLoadMore() {
                    university_listNew.add(null)
                    isLoading = true
                    adapter.notifyItemInserted(university_listNew.size - 1)
                    Handler(Looper.getMainLooper()).postDelayed({
                        hitAPI(page, "")

                    }, 2000)
                }
            })
        } else {
            germanAdapter = GermanFactAdapter(
                requireContext(),
                german_listNew,
                ::click,
                mBinding.rvUniv
            )
            mBinding.rvUniv.adapter = germanAdapter
            germanAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
                override fun onLoadMore() {
                    german_listNew.add(null)
                    isLoading = true
                    germanAdapter.notifyItemInserted(german_listNew.size - 1)
                    Handler(Looper.getMainLooper()).postDelayed({
                        hitAPI(page, "")

                    }, 2000)
                }
            })
        }



        homeModel.searchUniversityObserver.observe(requireActivity()) {
            if ((SharedHelper(requireContext()).country ?: "US").equals("DE")) {
                val univ = SearchParser(it).parseGermanJson()
                page = (univ.pager!!.current!! + 1)
                val totalPage = univ.pager?.total
                val last = univ.pager?.last
                progress.dismiss()
                germanList?.addAll(univ.data?.collegeData!!)
                germanListUpdate?.addAll(univ.data?.collegeData!!)
                if (isLoading) {
                    isLoading = false
                    german_listNew.removeAt(german_listNew.size - 1)
                    germanAdapter.notifyItemRemoved(german_listNew.size)
                }
                //for swipe refresh page
                if (totalPage != null) {
                    if (last != null) {
                        germanAdapter.addAllLis(germanList!!, totalPage.toInt(), last)
                    }
                }
                germanAdapter.setLoaded()
            } else {
                val univ = SearchParser(it).parseJson()
                page = (univ.pager!! + 1)
                val totalPage = univ.totalRecords
                val last = univ.last
                progress.dismiss()
                universityList?.addAll(univ.university_list!!)
                universityListUpdate?.addAll(univ.university_list!!)
                if (isLoading) {
                    isLoading = false
                    university_listNew.removeAt(university_listNew.size - 1)
                    adapter.notifyItemRemoved(university_listNew.size)
                }
                //for swipe refresh page
                if (totalPage != null) {
                    if (last != null) {
                        adapter.addAllLis(universityList!!, totalPage, last)
                    }
                }
                adapter.setLoaded()
            }
        }

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
        if (!isLoading)
            progress.show()
        val payload = UniversitySearchPayload()
        val country = SharedHelper(requireContext()).country ?: "US"
        payload.student_uid = SharedHelper(requireContext()).id.toString()
        payload.country = country
        payload.pager = pageNo
        payload.search = search
        payload.sort_parameter = "college_name"
        payload.sort_order = "asc"
        universityList?.clear()
        germanList?.clear()
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
        likeWork(universityListUpdate?.get(position))
    }

    fun likeWork(get: UniversitiesSearchModel?) {
        if (get?.topPickFlag == 0) {
            hitLikeWork(get)
        } else {
            hitUnlikeWork(get)
        }
    }

    private fun hitUnlikeWork(get: UniversitiesSearchModel?) {
        progress.show()
        SharedHelper(requireContext()).id?.let {
            get?.nid?.let { it1 ->
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