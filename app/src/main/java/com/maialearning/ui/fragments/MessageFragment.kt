package com.maialearning.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.FragmentDashboardBinding
import com.maialearning.databinding.LayoutMessageBinding
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.ui.adapter.MessageAdapter
import com.maialearning.ui.adapter.NotesAdapter

class MessageFragment : Fragment(), OnItemClick {
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
        loadFragment(MessageDetailFragment())
    }
    private fun loadFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment_content_dashboard, fragment)
        transaction?.addToBackStack("Message Fragment")
        transaction?.commit()
    }
}