package com.maialearning.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.BannerOneBinding
import com.maialearning.databinding.BannerTwoBinding

class BannerTwoFragment(val positiion: Int) : Fragment(), OnItemClick {
    private lateinit var mBinding: BannerTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = BannerTwoBinding.inflate(inflater, container, false)
        if (positiion==1){
            mBinding.bannerText.visibility = View.VISIBLE
            mBinding.dismissLayout.visibility = View.GONE
           // mBinding.topLayout.background = resources.getDrawable(R.drawable.banner_two_curve)
        } else{
            mBinding.bannerText.visibility = View.GONE
            mBinding.dismissLayout.visibility = View.VISIBLE
          //  mBinding.topLayout.setBackgroundColor( resources.getColor(R.color.banner_orange))
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(positiion: Int) {

    }


}