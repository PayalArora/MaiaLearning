package com.maialearning.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maialearning.R
import com.maialearning.databinding.SearchCareerLayBinding
import com.maialearning.model.CareerTopPickResponseItem
import com.maialearning.ui.adapter.SearchProgramAdapter
import com.maialearning.util.SEARCH_KEYWORD
import com.maialearning.util.getURLSearch
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.util.showToast
import com.maialearning.viewmodel.CareerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchCareerFragment(var type: String) : Fragment() {
    var dialog: BottomSheetDialog? = null
    private lateinit var mBinding: SearchCareerLayBinding
    private val careerViewModel: CareerViewModel by viewModel()
    private lateinit var progress: Dialog
    lateinit var gson: Gson
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

    }

    var arrayList: ArrayList<CareerTopPickResponseItem> = arrayListOf()

    private fun initObserver() {
        careerViewModel.careerListObserver.observe(viewLifecycleOwner) {

            if (it.size() > 0) {
                arrayList = arrayListOf()

                for (i in it) {
                    val itModel = gson.fromJson(i, CareerTopPickResponseItem::class.java)
                    arrayList.add(itModel)
                }
                arrayList.sortBy { it.title }

                mBinding.listProgram.adapter =
                    SearchProgramAdapter(requireContext(), arrayList, ::loadFragment)


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
            if (it.career== null){
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
                arrayList = arrayListOf<CareerTopPickResponseItem>()

                for (i in it) {
                    val itModel = gson.fromJson(i, CareerTopPickResponseItem::class.java)
                    arrayList.add(itModel)
                }
                arrayList.sortBy { it.title }

                Log.e("Data", " " + arrayList?.get(0).title)
            } else {
                mBinding.list.visibility = View.GONE
                mBinding.searchLay.visibility = View.VISIBLE
                context?.showToast(getString(R.string.no_data_found))
            }
            mBinding.listProgram.adapter =
                SearchProgramAdapter(requireContext(), arrayList, ::loadFragment)

        }
    }

    private fun loadFragment(position: Int) {
        if (type != "trafic") {
            val fragment = TraficFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            var bundle = Bundle()
            bundle.putSerializable("data", arrayList.get(position))
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