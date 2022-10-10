package com.maialearning.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maialearning.R

import com.maialearning.databinding.PortfolioProfileFragmentBinding
import com.maialearning.model.ProfileResponse
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.PortfolioViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel


class PortfolioProfileFragment : Fragment() {
    private val portfolioModel: PortfolioViewModel by viewModel()
    private lateinit var progress: Dialog
    private lateinit var mBinding: PortfolioProfileFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = PortfolioProfileFragmentBinding.inflate(inflater, container, false)

        progress = showLoadingDialog(requireContext())
        progress.show()
//        SharedHelper(requireContext()).id?.let { portfolioModel.getProfile(it) }
        observers()
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.addMore.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(
                R.id.nav_host_fragment_content_dashboard,
                PortfolioFilledProfileFragment()
            )
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
    }


    private fun observers() {
        portfolioModel.showError.observe(requireActivity()) {
            progress.dismiss()
        }
        portfolioModel.observer.observe(requireActivity()) {
            progress.dismiss()
            setData(it)
        }
    }

    private fun setData(profileResponse: ProfileResponse) {
        mBinding.nameText.setText(profileResponse.info?.firstName)
        mBinding.fullAddressTxt.setText(profileResponse.info?.address)
        mBinding.fullAddressTxt.setText(profileResponse.info?.address)
        mBinding.cityTxt.setText(profileResponse.info?.locality)
        mBinding.yourEmail.setText(profileResponse.info?.mail)
        mBinding.yourPhone.setText(profileResponse.info?.primaryPhone)
        Picasso.with(requireContext()).load(profileResponse.info?.profilePic).into(mBinding.profImg)
    }
}
