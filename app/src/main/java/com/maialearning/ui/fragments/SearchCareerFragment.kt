package com.maialearning.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.GsonBuilder
import com.maialearning.R
import com.maialearning.databinding.SearchCareerLayBinding
import com.maialearning.model.CareerTopPickResponse
import com.maialearning.model.CareerTopPickResponseItem
import com.maialearning.model.CollegeFactSheetModel
import com.maialearning.ui.adapter.SearchProgramAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.CareerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchCareerFragment(var type: String) : Fragment() {
    var dialog: BottomSheetDialog? = null
    private lateinit var mBinding: SearchCareerLayBinding
    private val careerViewModel: CareerViewModel by viewModel()
    private lateinit var progress: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            careerViewModel.getKeyboardSearch("it")
        }
        mBinding.searchLay.setOnClickListener {
            mBinding.searchLay.visibility = View.GONE
            mBinding.list.visibility = View.VISIBLE
        }
        mBinding.listProgram.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        mBinding.listProgram.adapter = SearchProgramAdapter(requireContext(), ::loadFragment)

    }
    var arrayList: ArrayList<CareerTopPickResponseItem> = ArrayList()

    private fun initObserver() {
        careerViewModel.careerListObserver.observe(viewLifecycleOwner) {
            progress.dismiss()
            if (it.size() > 0) {
                arrayList= ArrayList()
                val gson = GsonBuilder().create()
                for(i in it){
                    val itModel = gson.fromJson(i, CareerTopPickResponseItem::class.java)
                    arrayList.add(itModel)
                }
                arrayList.sortBy { it.title }

                mBinding.listProgram.adapter = SearchProgramAdapter(requireContext(), arrayList,::loadFragment)


               // val listing = itModel.careerTopPickResponse
                Log.e("Data"," "+arrayList?.size)
            }
        }
    }

    private fun loadFragment(position: Int) {
        if (type != "trafic") {
            val fragment=TraficFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            var bundle=Bundle()
            bundle.putSerializable("data",arrayList.get(position))
            fragment.arguments=bundle
            transaction.add(R.id.nav_host_fragment_content_dashboard,fragment)
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