package com.maialearning.ui.fragments

import ViewPagerAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.FragmentDashboardBinding
import com.maialearning.databinding.LayoutMessageBinding
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.databinding.MessageLayoutBinding
import com.maialearning.ui.activity.NewMessageActivity
import com.maialearning.ui.adapter.MessageAdapter
import com.maialearning.ui.adapter.NotesAdapter

class MessageFragment : Fragment(), OnItemClick {
    private lateinit var mBinding: MessageLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = MessageLayoutBinding.inflate(inflater, container, false)
        setAdapter()
          return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        val adapter = ViewPagerAdapter(requireContext(), this,
            3)
        mBinding.viewPager.adapter = adapter
//        TabLayoutMediator(mBinding.tabs, mBinding.viewPager) { tab, position ->
//        }.attach()
        mBinding.tabs.tabGravity = TabLayout.GRAVITY_FILL

        mBinding.addFab.setOnClickListener { startActivity(Intent(requireActivity(), NewMessageActivity::class.java)) }
   
    }

    override fun onClick(positiion: Int) {
       // loadFragment(MessageDetailFragment())
    }

}