package com.maialearning.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.MessageLayoutBinding
import com.maialearning.databinding.ProfileViewpagerBinding
import com.maialearning.ui.activity.LoginActivity
import com.maialearning.ui.activity.NewMessageActivity
import com.maialearning.ui.bottomsheets.PrimaryEmail
import com.maialearning.ui.bottomsheets.ProfileFilter
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.viewmodel.LoginNewModel
import com.maialearning.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.viewModel
import javax.inject.Inject

class ProfileFragment : Fragment(), OnItemClick {
    private lateinit var mBinding: ProfileViewpagerBinding
//    private val profileModel: ProfileViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = ProfileViewpagerBinding.inflate(inflater, container, false)
        profileWork()
        return mBinding.root
    }

    private fun profileWork() {
//        profileModel.observer.observe(viewLifecycleOwner, {
//
//        })
        //profileModel.getProfile()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.emailLay.setOnClickListener {
            PrimaryEmail(requireActivity(), layoutInflater).showDialog()
        }
        mBinding.logoutLay.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, which -> logout() })
                .setNegativeButton("No", null)
                .show() }
    }
    private fun logout() {
        SharedHelper(requireContext()).login = "0"
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // To clean up all activities
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onClick(positiion: Int) {

    }


}