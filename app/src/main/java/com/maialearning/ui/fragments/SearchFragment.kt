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
import com.maialearning.model.*
import com.maialearning.parser.SearchParser
import com.maialearning.ui.adapter.EuropeanFactAdapter
import com.maialearning.ui.adapter.GermanFactAdapter
import com.maialearning.ui.adapter.UkFactAdapter
import com.maialearning.ui.adapter.UniFactAdapter
import com.maialearning.util.OnLoadMoreListener
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.replaceInvertedComas
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.HomeViewModel
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {
    private lateinit var mBinding: SearchLayoutBinding
    private val homeModel: HomeViewModel by viewModel()
    private lateinit var progress: Dialog
    var page: Int = 1
    private var universityListUpdate: ArrayList<UniversitiesSearchModel?>? = null
    private var universityList: ArrayList<UniversitiesSearchModel?>? = null
    private var universityListNew: ArrayList<UniversitiesSearchModel?> = arrayListOf()

    private var germanListUpdate: ArrayList<GermanUniversitiesResponse.Data.CollegeData?>? = null
    private var germanList: ArrayList<GermanUniversitiesResponse.Data.CollegeData?>? = null
    private lateinit var germanListNew: ArrayList<GermanUniversitiesResponse.Data.CollegeData?>

    private var euroListUpdate: ArrayList<EuropeanUniList.CollegeList?>? = null
    private var euroList: ArrayList<EuropeanUniList.CollegeList?>? = null
    private lateinit var euroListNew : ArrayList<EuropeanUniList.CollegeList?>

    private var ukListUpdate: ArrayList<UkResponseModel.Data.CollegeData?>? = null
    private var ukList: ArrayList<UkResponseModel.Data.CollegeData?>? = null
    private lateinit var ukListNew : ArrayList<UkResponseModel.Data.CollegeData?>
    private var euCountries = arrayListOf<String>()
    private var isLoading = false
    private var isEuropean = false
    lateinit var adapter: UniFactAdapter
    lateinit var germanAdapter: GermanFactAdapter
    lateinit var euroAdapter: EuropeanFactAdapter
    lateinit var ukAdapter: UkFactAdapter

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
        adapter = UniFactAdapter(
            requireContext(),
            universityListNew,
            ::click,
            mBinding.rvUniv
        )
        if (euCountries.isEmpty()) {
        homeModel.getCountriesContinentBased("EU")}
        else {
            universitySearch()
        }
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment: SupportMapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.map, mapFragment)
            .commit()
        observers()

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

    private fun observers() {
        if (euCountries.isEmpty()) {
            homeModel.getcontinentFilter.observe(viewLifecycleOwner) {
                val json = JSONObject(it.toString())
                val x = json.keys() as Iterator<String>
                while (x.hasNext()) {
                    euCountries.add(x.next().replaceInvertedComas())
                }
                universitySearch()
            }
        }

        homeModel.searchUniversityObserver.observe(requireActivity()) {
            if ((SharedHelper(requireContext()).country ?: "US") == "DE") {
                val univ = SearchParser(it).parseGermanJson()
                page = (univ.pager!!.current!! + 1)
                val totalPage = univ.pager?.total
                val last = univ.pager?.last
                progress.dismiss()

               for (item in univ.data?.collegeData!!){
                   item.courseList.sortBy { it.courseName }
                   germanList?.add(item)
                   germanListUpdate?.add(item)
               }


                if (isLoading) {
                    isLoading = false
                    germanListNew.removeAt(germanListNew.size - 1)
                    germanAdapter.notifyItemRemoved(germanListNew.size)
                }
                //for swipe refresh page
                if (totalPage != null) {
                    if (last != null) {
                        germanAdapter.addAllLis(germanList!!, totalPage.toInt(), last)
                    }
                }
                germanAdapter.setLoaded()
            } else if ((SharedHelper(requireContext()).country ?: "US") == "GB") {
                val univ = SearchParser(it).parseUkJson()
                page = (univ.pager!!.current!! + 1)
                val totalPage = univ.pager?.total
                val last = univ.pager?.last
                progress.dismiss()
                for (item in univ.data?.collegeData!!){
                    item.courseList.sortBy { it.courseName }
                    ukList?.add(item)
                    ukListUpdate?.add(item)
                }
//                ukList?.addAll(univ.data?.collegeData!!)
//                ukListUpdate?.addAll(univ.data?.collegeData!!)
                if (isLoading) {
                    isLoading = false
                    ukListNew.removeAt(ukListNew.size - 1)
                    ukAdapter.notifyItemRemoved(ukListNew.size)
                }
                //for swipe refresh page
                if (totalPage != null) {
                    if (last != null) {
                        ukAdapter.addAllLis(ukList!!, totalPage.toInt(), last)
                    }
                }
                ukAdapter.setLoaded()

            } else if (isEuropean) {
                val univ = SearchParser(it).parseEuropeanJson()
                page = (univ.pager!! + 1)
                val totalPage = univ.totalRecords
                val last = univ.last
                progress.dismiss()
                euroList?.addAll(univ.collegeList)
                euroListUpdate?.addAll(univ.collegeList)
                if (isLoading) {
                    isLoading = false
                    euroListNew.removeAt(euroListNew.size - 1)
                    euroAdapter.notifyItemRemoved(euroListNew.size)
                }
                //for swipe refresh page
                if (totalPage != null) {
                    if (last != null) {
                        euroAdapter.addAllLis(euroList!!, totalPage.toInt(), last)
                    }
                }
               euroAdapter.setLoaded()
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
                    universityListNew.removeAt(universityListNew.size - 1)
                    adapter.notifyItemRemoved(universityListNew.size)
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
        }
        homeModel.unlikeObserver.observe(requireActivity()) {
            progress.dismiss()
        }
        homeModel.showError.observe(requireActivity()) {
            progress.dismiss()
            Log.e("Error", "err" + it)
        }
    }

    private fun universitySearch() {
        if ((SharedHelper(requireContext()).country ?: "US") == "DE") {
            germanListUpdate = ArrayList()
            germanList = ArrayList()
            germanListNew = ArrayList<GermanUniversitiesResponse.Data.CollegeData?>()
            isEuropean = false
            germanAdapter = GermanFactAdapter(
                requireContext(),
                germanListNew,
                ::clickEuropean,
                mBinding.rvUniv
            )
            mBinding.rvUniv.adapter = germanAdapter
            germanAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
                override fun onLoadMore() {
                    germanListNew.add(null)
                    isLoading = true
                    germanAdapter.notifyItemInserted(germanListNew.size - 1)
                    Handler(Looper.getMainLooper()).postDelayed({
                        hitAPI(page, "")

                    }, 2000)
                }
            })
        } else if ((SharedHelper(requireContext()).country ?: "US").equals("GB")) {
            ukListUpdate= ArrayList()
            ukList = ArrayList()
            ukListNew = ArrayList<UkResponseModel.Data.CollegeData?>()
            ukAdapter = UkFactAdapter(
                requireContext(),
                ukListNew,
                ::clickEuropean,
                mBinding.rvUniv
            )
            mBinding.rvUniv.adapter = ukAdapter
            ukAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
                override fun onLoadMore() {
                    ukListNew.add(null)
                    isLoading = true
                    ukAdapter.notifyItemInserted(ukListNew.size - 1)
                    Handler(Looper.getMainLooper()).postDelayed({
                        hitAPI(page, "")

                    }, 2000)
                }
            })
        } else if (euCountries.contains(SharedHelper(requireContext()).country ?: "US")) {
            euroListUpdate = ArrayList()
            euroList = ArrayList()
            euroListNew = ArrayList()
            isEuropean = true
            euroAdapter = EuropeanFactAdapter(
                requireContext(),
                euroListNew,
                ::clickEuropean,
                mBinding.rvUniv
            )
            mBinding.rvUniv.adapter = euroAdapter
            euroAdapter.setOnLoadMoreListener(object : OnLoadMoreListener {
                override fun onLoadMore() {
                    euroListNew.add(null)
                    isLoading = true
                    euroAdapter.notifyItemInserted(euroListNew.size - 1)
                    Handler(Looper.getMainLooper()).postDelayed({
                        hitAPI(page, "")

                    }, 2000)
                }
            })
        } else {
            universityListUpdate = ArrayList()
            universityList = ArrayList()
            universityListNew = ArrayList()
            isEuropean = false
            adapter = UniFactAdapter(
                requireContext(),
                universityListNew,
                ::click,
                mBinding.rvUniv
            )
            mBinding.rvUniv.adapter = adapter
            adapter.setOnLoadMoreListener(object : OnLoadMoreListener {
                override fun onLoadMore() {
                    universityListNew.add(null)
                    isLoading = true
                    adapter.notifyItemInserted(universityListNew.size - 1)
                    Handler(Looper.getMainLooper()).postDelayed({
                        hitAPI(page, "")

                    }, 2000)
                }
            })
        }
        hitAPI(page, "")

    }

    private fun clickEuropean(id: String?, boolean: Boolean) {
        if (boolean)
        hitLikeWork(id)
        else
            hitUnlikeWork(id)
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
        euroList?.clear()
        ukList?.clear()
        if (isEuropean) {
            homeModel.euroUniversities(payload)
        } else {
            homeModel.searchUniversities(payload)
        }
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
            hitLikeWork(get?.nid)
        } else {
            hitUnlikeWork(get?.nid)
        }
    }

    private fun hitUnlikeWork(nid: String?) {
        progress.show()
        SharedHelper(requireContext()).id?.let {
            nid?.let { it1 ->
                homeModel.hitunlike(
                    it,
                    it1
                )
            }
        }
    }

    private fun hitLikeWork(nid:String?) {
        progress.show()

        SharedHelper(requireContext()).id?.let {
            nid?.let { it1 ->
                homeModel.hitlike(
                    it,
                    it1
                )
            }
        }
    }
}