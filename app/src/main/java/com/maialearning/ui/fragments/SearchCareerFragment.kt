package com.maialearning.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maialearning.R
import com.maialearning.databinding.SearchCareerLayBinding
import com.maialearning.model.BrightOutlookModel
import com.maialearning.model.CareerClusterModel
import com.maialearning.model.CareerTopPickResponseItem
import com.maialearning.model.IndustryModel
import com.maialearning.ui.adapter.CareerCluster
import com.maialearning.ui.adapter.IndustryClusterAdapter
import com.maialearning.ui.adapter.SearchProgramAdapter
import com.maialearning.util.*
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.viewmodel.CareerViewModel
import org.json.JSONArray
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchCareerFragment(var type: String) : Fragment() {
    var dialog: BottomSheetDialog? = null
    private lateinit var mBinding: SearchCareerLayBinding
    private val careerViewModel: CareerViewModel by viewModel()
    private lateinit var progress: Dialog
    lateinit var gson: Gson
    private val listData: String
        get() = ("[{\"id\":\"72\",\"name\":\"Accommodation Food Services\"}" + ",{\"id\":\"56\",\"name\":\"Administrative Support Services\"}" +",{\"id\":\"11\",\"name\":\"Agriculture Forestry Fishing Hinting\"}" +
                ",{\"id\":\"71\",\"name\":\"Arts Entertainment Recreation\"}" +  ",{\"id\":\"23\",\"name\":\"Construction\"}" +  ",{\"id\":\"61\",\"name\":\"Educational Services\"}" + ",{\"id\":\"52\",\"name\":\"Finance Insurance\"}" +
                ",{\"id\":\"93\",\"name\":\"Government\"}" +",{\"id\":\"62\",\"name\":\"Health Care Social Assistance\"}" +",{\"id\":\"51\",\"name\":\"Information\"}" +",{\"id\":\"55\",\"name\":\"Management Companies Enterprise\"}" +
                ",{\"id\":\"31\",\"name\":\"Manufacturing\"}" +",{\"id\":\"21\",\"name\":\"Mining Quarrying Iol Gas Extraction\"}" +",{\"id\":\"81\",\"name\":\"Other Services Exc Pub Administration\"}" +",{\"id\":\"54\",\"name\":\"Professional Scientific Technical Service\"}" +
                ",{\"id\":\"53\",\"name\":\"Real Estate Rental Leasing\"}" + ",{\"id\":\"44\",\"name\":\"Real Trade\"}" +",{\"id\":\"94\",\"name\":\"Self Employed\"}" +",{\"id\":\"48\",\"name\":\"Transportation Warehousing\"}" +",{\"id\":\"22\",\"name\":\"Utilities\"}" +
                ",{\"id\":\"42\",\"name\":\"Wholesale Trade\"}]")
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
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        if (type == "list") {
            mBinding.searchLay.visibility = View.GONE
            mBinding.list.visibility = View.VISIBLE
            progress.show()
            SharedHelper(requireContext()).id?.let { careerViewModel.getCareerList(it) }
        } else if (type == "trafic") {
            mBinding.searchLay.visibility = View.GONE
            mBinding.text.visibility = View.GONE
            mBinding.cardView.visibility = View.GONE
            mBinding.list.visibility = View.VISIBLE

        } else {
            mBinding.searchLay.visibility = View.VISIBLE
            mBinding.list.visibility = View.GONE
            setSearchSpinner()
        }
        mBinding.searchLay.setOnClickListener {
            mBinding.searchLay.visibility = View.GONE
            mBinding.list.visibility = View.VISIBLE
        }
        mBinding.text2.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                progress.show()
                careerViewModel.getKeyboardSearch(mBinding.text2.text.toString().getURLSearch())
                return@OnEditorActionListener true
            }
            false
        })

    }

    private fun setSearchSpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_text, resources.getStringArray(R.array.SEARCH_ARRAY)
        )
        mBinding.spinner.adapter = adapter
        mBinding.spinner.setSelection(0)
        mBinding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (selectedItem == "Bright Outlook") {
                    setOutlookSpinner()
                } else if (selectedItem == "Career Clusters") {
                    mBinding.workLayout.visibility = View.GONE
                    mBinding.spinnerLay1.visibility = View.VISIBLE
                    progress.show()
                    careerViewModel.getCareerCluster(CAREER_AFFINITY)
                }else if (selectedItem == "Industry") {
                    setIndustrySpinner()
                }else if (selectedItem == "Work Values") {
                    setWorkSpinner()
                } else {
                    mBinding.text2.visibility = View.VISIBLE
                    mBinding.outSpinner.visibility = View.GONE
                }
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setWorkSpinner() {
        mBinding.spinnerLay1.visibility = View.GONE
        mBinding.workLayout.visibility = View.VISIBLE
        var url= BASE_URL+"get_work_values_career/1"
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
                if(!url.contains("first_value")){
                    url +=   "?first_value="+parent.getItemAtPosition(position).toString()
                }else if(!url.contains("second_value")){
                    url +=    "&second_value="+parent.getItemAtPosition(position).toString()
                }else if(!url.contains("third_value")){
                    url +=  "&third_value="+parent.getItemAtPosition(position).toString()
                }else{
                    url = url.replace(url.substring(url.indexOf("first_value="),url.indexOf("&")),"first_value="+ parent.getItemAtPosition(position).toString())
                }
                progress.show()
                careerViewModel.getWorkSearch(url)
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
                if(!url.contains("first_value")){
                    url +=   "?first_value="+parent.getItemAtPosition(position).toString()
                }else if(!url.contains("second_value")){
                    url +=   "&second_value="+parent.getItemAtPosition(position).toString()
                }else if (!url.contains("third_value")){
                    url +=  "&third_value="+parent.getItemAtPosition(position).toString()
                }else{
                    url =url.replace(url.substring(url.indexOf("second_value="),url.indexOf("&")),"second_value="+ parent.getItemAtPosition(position).toString())

                }
                progress.show()
                careerViewModel.getWorkSearch(url)
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
                if(!url.contains("first_value")){
                    url +=   "?first_value="+parent.getItemAtPosition(position).toString()
                }else if(!url.contains("second_value")){
                    url +=    "&second_value="+parent.getItemAtPosition(position).toString()
                }else if(!url.contains("third_value")){
                    url +=  "&third_value="+parent.getItemAtPosition(position).toString()
                }else{
                    url =url.replace(url.substring(url.indexOf("third_value="),url.length),"third_value="+ parent.getItemAtPosition(position).toString())

                }
                progress.show()
                careerViewModel.getWorkSearch(url)

            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    private fun setIndustrySpinner() {
        mBinding.workLayout.visibility = View.GONE
        mBinding.spinnerLay1.visibility = View.VISIBLE
        val list =ArrayList<IndustryModel>()
        val jsonArry = JSONArray(listData)
        for (i in 0 until jsonArry.length()) {
            val obj = jsonArry.getJSONObject(i)
            val user = IndustryModel( obj.getInt("id"), obj.getString("name"))
            list.add(user)
        }
        mBinding.text2.visibility = View.GONE
        mBinding.outSpinner.visibility = View.VISIBLE
        val adapter = IndustryClusterAdapter(requireContext(),list, ::clickIndustryItem)
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

    var arrayList: ArrayList<CareerTopPickResponseItem?>? = arrayListOf()
    var arrayListOut: ArrayList<BrightOutlookModel.Data?>? = arrayListOf()

    private fun initObserver() {
        careerViewModel.showError.observe(viewLifecycleOwner) {
            progress.dismiss()
        }
        careerViewModel.industryListObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            val list=ArrayList<String>()
            for (i in 0 until it.occupation.size){
                list.add(it.occupation[i].code.toString())
            }
            progress.show()
            careerViewModel.getCareerClusterListDetail(list)
        }
        careerViewModel.careerClusterListObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            val list=ArrayList<String>()
            for (i in 0 until it.occupation.size){
                list.add(it.occupation[i].code.toString())
            }
            progress.show()
            careerViewModel.getCareerClusterListDetail(list)
        }
        careerViewModel.careerClusterDetailObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            if (it.size> 0) {
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
            mBinding.listProgram.adapter =
                SearchProgramAdapter(requireContext(), null, arrayListOut!!, "", ::loadFragment)

        }

        careerViewModel.careerListObserver.observe(viewLifecycleOwner) {

            if (it.size() > 0) {
                arrayList = arrayListOf()

                for (i in it) {
                    val itModel = gson.fromJson(i, CareerTopPickResponseItem::class.java)
                    arrayList?.add(itModel)
                }
                arrayList?.sortBy { it?.title }

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
            mBinding.listProgram.adapter =
                SearchProgramAdapter(requireContext(), arrayList, null, "key", ::loadFragment)

        }
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
}