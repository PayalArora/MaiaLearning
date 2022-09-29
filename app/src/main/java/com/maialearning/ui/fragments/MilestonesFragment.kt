package com.maialearning.ui.fragments

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import com.maialearning.R
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.model.ItaskItem
import com.maialearning.ui.adapter.MilestonesAdapter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.HomeViewModel
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MilestonesFragment : Fragment() {
    private lateinit var mBinding: LayoutRecyclerviewBinding
    private val homeModel: HomeViewModel by viewModel()
    private lateinit var progress: Dialog
    var itask: List<ItaskItem?>? = null
    var pos: Int = -1


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


    private fun setAdapter(itask: List<ItaskItem?>?) {
        mBinding.recyclerList.adapter = MilestonesAdapter(itask, ::dotsClick)

    }

    private fun dotsClick(id: Int, it: View) {
        menuPopUp(id, it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        progress = showLoadingDialog(requireContext())
        progress.show()
        homeModel.getMilestonesID()
        homeModel.getMilestoneIDObserver.observe(requireActivity()) {
            val json = JSONObject(it.toString())
            val x = json.keys() as Iterator<String>
            var idMile: String = ""
            while (x.hasNext()) {
                val key: String = x.next()
                if (json.optString(key).toString().equals("Milestone")) {
                    idMile = key
                }
            }
            homeModel.getMilestones(idMile)
        }
        homeModel.getMilestonesObserver.observe(requireActivity()) {
            progress.dismiss()
            itask = it.itask
            setAdapter(itask)
        }
        homeModel.checkItaskObserver.observe(requireActivity()) {
            progress.dismiss()
            if (pos != -1) {
                itask?.get(pos)?.status = 1
                mBinding.recyclerList.adapter?.notifyDataSetChanged()
            }
        }
        homeModel.uncheckItaskObserver.observe(requireActivity()) {
            progress.dismiss()
            if (pos != -1) {
                itask?.get(pos)?.status = 0
                mBinding.recyclerList.adapter?.notifyDataSetChanged()
            }
        }
        homeModel.showError.observe(requireActivity()){
            progress.dismiss()
        }
    }

    private fun setListeners() {

    }

    private fun menuPopUp(position: Int, it: View?) {
        val popupMenu = PopupMenu(requireContext(), it)
        popupMenu.inflate(R.menu.popup_complete)
        popupMenu.show()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true)
        };
        val menu: Menu = popupMenu!!.menu
        val item = menu.findItem(R.id.option_comp)
        if (itask?.get(position)?.status == 1) {
            item.title = "Mark Incomplete"
        } else {
            item.title = "Mark Complete"
        }
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.option_comp -> {
                    pos = position
                    if (itask?.get(position)?.status == 1) {
                        progress.show()
                        itask?.get(position)!!.nid?.let { it1 -> homeModel.uncheckItask(it1) }
                    } else {
                        progress.show()
                        itask?.get(position)?.nid?.let { it1 ->
                            SharedHelper(requireContext()).id?.let { it2 ->
                                homeModel.checkItask(
                                    it1,
                                    it2
                                )
                            }
                        }
                    }
                }
            }
            true
        })
    }


}
