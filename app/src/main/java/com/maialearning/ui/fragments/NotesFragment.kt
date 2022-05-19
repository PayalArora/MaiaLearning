package com.maialearning.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maialearning.R
import com.maialearning.calbacks.OnItemClick
import com.maialearning.databinding.FragmentDashboardBinding
import com.maialearning.databinding.LayoutRecyclerviewBinding
import com.maialearning.ui.adapter.NotesAdapter

class NotesFragment : Fragment(), OnItemClick {
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
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        mBinding.recyclerList.adapter =NotesAdapter(this)
   
    }

    override fun onClick(positiion: Int) {
        loadFragment(NotesDetailFragment())
    }
    private fun loadFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment_content_dashboard, fragment)
        transaction?.addToBackStack("Notes Fragment")
        transaction?.commit()
    }
}