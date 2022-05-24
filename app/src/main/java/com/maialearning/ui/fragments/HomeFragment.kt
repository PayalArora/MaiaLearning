package com.maialearning.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maialearning.R
import com.maialearning.databinding.FragmentDashboardBinding
import com.maialearning.ui.activity.UniversitiesActivity

class HomeFragment : Fragment() {
    private lateinit var dashboardBinding: FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)

        return dashboardBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        dashboardBinding.universities.setOnClickListener {
            var intent = Intent(requireContext(), UniversitiesActivity::class.java)
            startActivity(intent)

        }
    }

}