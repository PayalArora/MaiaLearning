package com.maialearning.ui.fragments

import ViewPagerAdapter
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.MessageLayoutBinding
import com.maialearning.model.ConsiderModel
import com.maialearning.ui.activity.NewMessageActivity
import com.maialearning.ui.adapter.ViewMessageAdapter
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.MessageViewModel
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageFragment : Fragment(), OnItemClick {
    private val messageViewModel: MessageViewModel by viewModel()
    private lateinit var mBinding: MessageLayoutBinding
    private lateinit var dialog: Dialog
    var inboxResponse:JsonObject? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = MessageLayoutBinding.inflate(inflater, container, false)

          return mBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()

    }



    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setAdapter() {
        var tabArray = arrayOf(getString(R.string.inbox),
            getString(R.string.sent),
            getString(R.string.trash))
        for (item in tabArray) {
            mBinding.tabs.addTab(mBinding.tabs.newTab().setText(item))

        }
        loadFragment(MessageListFragment())
        //val fm: FragmentManager = requireActivity().supportFragmentManager
//        val adapter = ViewMessageAdapter(fm, lifecycle,
//            tabArray.size)
        //mBinding.viewPager.adapter = adapter
      //  mBinding.viewPager.isUserInputEnabled = false
//        TabLayoutMediator(mBinding.tabs, mBinding.viewPager) { tab, position ->
//            tab.setText(tabArray[position])
//        }.attach()
        mBinding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        loadFragment(MessageListFragment())
                    }
                    1 -> {
                        loadFragment(MessageSentFragment())
                    }
                    2 -> {
                        loadFragment(MessageTrashFragment())
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        mBinding.tabs.tabGravity = TabLayout.GRAVITY_FILL

        mBinding.addFab.setOnClickListener { startActivity(Intent(requireActivity(), NewMessageActivity::class.java)) }
   
    }

    private fun loadFragment(fragment: Fragment?) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        fragment?.let { transaction.add(R.id.host_nav, it) }
        transaction.commit()
    }

    override fun onClick(positiion: Int) {
       // loadFragment(MessageDetailFragment())
    }

}