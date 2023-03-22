package com.maialearning.ui.fragments

import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maialearning.R
import com.maialearning.calbacks.OnItemClickType
import com.maialearning.databinding.CommentsSheetBinding
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.model.NotesModel
import com.maialearning.ui.activity.NotesDetailActivity
import com.maialearning.ui.adapter.CommentAdapter
import com.maialearning.ui.adapter.NotesAdapter
import com.maialearning.util.DESCRIPTION
import com.maialearning.util.TITLE
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesFragment : Fragment(), OnItemClickType {
    private lateinit var mBinding: LayoutRecyclerviewBinding
    private lateinit var dialogP: Dialog
    private val homeModel: HomeViewModel by viewModel()
    private var notesModel:NotesModel? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogP = showLoadingDialog(requireContext())
        dialogP.show()
        SharedHelper(requireContext()).id?.let {
            val userid = SharedHelper(requireContext()).uuid!!
            homeModel.getNotes(userid)
        }

        setAdapter()
        initObserver()
    }
    private fun initObserver() {
        homeModel.notesObserver.observe(requireActivity()) {
            it?.let {
                dialogP?.dismiss()
                notesModel = it
                it.data?.let {mBinding.recyclerList.adapter =NotesAdapter(this,it)  }
            }
        }
    }
    private fun setAdapter() {
       mBinding.recyclerList.adapter =NotesAdapter(this, null)
   
    }

    override fun onClickOpt(positiion: Int, type: String) {
        if (type == "comment"){
            bottomSheetComment()
        }
        else if (type == "root"){
            var intent = Intent(requireActivity(), NotesDetailActivity::class.java)
            intent.putExtra(TITLE, notesModel?.data?.get(positiion)?.title)
            intent.putExtra(DESCRIPTION, notesModel?.data?.get(positiion)?.description )
            notesModel?.data?.get(positiion)?.let {  intent.putExtra("DATA",it)}

            startActivity(intent)
        }
    }
    private fun bottomSheetComment() {
        val dialog = BottomSheetDialog(requireContext())
        val sheetBinding: CommentsSheetBinding = CommentsSheetBinding.inflate(layoutInflater)
        sheetBinding.root.minimumHeight =( (Resources.getSystem().displayMetrics.heightPixels))
        dialog.setContentView(sheetBinding.root)
        dialog.show()
        sheetBinding.close.setOnClickListener {
            dialog.dismiss()
        }
        sheetBinding.commentList.adapter = CommentAdapter(this)
    }

    override fun onClick(positiion: Int) {

    }
    private fun loadFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment_content_dashboard, fragment)
        transaction?.addToBackStack("Notes Fragment")
        transaction?.commit()
    }
}