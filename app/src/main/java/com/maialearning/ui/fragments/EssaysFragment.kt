package com.maialearning.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.model.DataItem
import com.maialearning.model.RecomdersModel
import com.maialearning.ui.adapter.EssayAdapter
import com.maialearning.ui.adapter.RecommenderAdapter
import com.maialearning.util.OnLoadMoreListener
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.FactSheetModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EssaysFragment : Fragment() {

    private lateinit var mBinding: LayoutRecyclerviewBinding
    private val mModel: FactSheetModel by viewModel()
    private var page: Int = 1
    private lateinit var dialogP: Dialog
    private lateinit var adapter: EssayAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = LayoutRecyclerviewBinding.inflate(inflater, container, false)
        return mBinding.root
    }
    private var isLoading = false

    private fun setAdapter() {
//        mBinding.recyclerList.adapter = EssayAdapter()
        adapter = EssayAdapter(requestListNew)
        mBinding.recyclerList.adapter = adapter
        adapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                requestListNew.add(null)
                isLoading = true
                adapter.notifyItemInserted(requestListNew.size - 1)
                Handler(Looper.getMainLooper()).postDelayed({
                    hitAPI(page.toString())

                }, 2000)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogP = showLoadingDialog(requireContext())
//        dialogP.show()

        setListeners()
        setAdapter()
        hitAPI(page.toString())
        observers()
    }
    private var requestListNew = ArrayList<DataItem>()

    private fun observers() {
        mModel.collegeEssayObserver.observe(requireActivity()) {
//            dialogP.dismiss()
            Log.e("Response: ","?>> "+it.data?.size)
            requestListNew= it.data as ArrayList<DataItem>
            page = ((it.page!!?.toInt() ?: 0) + 1)
            adapter.notifyDataSetChanged()
            if (isLoading) {
                isLoading = false
                requestListNew.removeAt(requestListNew.size - 1)
                adapter.notifyItemRemoved(requestListNew.size)
            }
        }
    }

    private fun hitAPI(page: String) {
        SharedHelper(requireContext()).id?.let {
            mModel.getCollegeEssays(
                it,
                page.toString(),
                "essay_colleges",
                "asc"
            )
        }
    }

    private fun setListeners() {

    }
}