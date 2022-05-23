package com.maialearning.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.ui.activity.MessageDetailActivity
import com.maialearning.ui.adapter.MessageAdapter

class MessageListFragment : Fragment(), OnItemClick {
    private lateinit var mBinding: LayoutRecyclerviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = LayoutRecyclerviewBinding.inflate(inflater, container, false)
         mBinding.layoutRecycler.setBackgroundColor(getResources().getColor(R.color.white))

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        mBinding.recyclerList.adapter =MessageAdapter(this)
   
    }

    override fun onClick(positiion: Int) {
     val intent = Intent(requireActivity(), MessageDetailActivity::class.java)
        startActivity(intent)
    }

}