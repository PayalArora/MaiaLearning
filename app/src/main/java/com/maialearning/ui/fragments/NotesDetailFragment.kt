package com.maialearning.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maialearning.R
import com.maialearning.databinding.FragmentDashboardBinding
import com.maialearning.databinding.LayoutNotesBinding

class NotesDetailFragment : Fragment() {
    private lateinit var notesBinding: LayoutNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        notesBinding = LayoutNotesBinding.inflate(inflater, container, false)
        return notesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}