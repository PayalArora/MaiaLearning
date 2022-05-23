package com.maialearning.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.maialearning.R
import com.maialearning.databinding.FragmentDashboardBinding
import com.maialearning.databinding.LayoutNotesBinding

class NotesDetailActivity : AppCompatActivity() {
    private lateinit var notesBinding: LayoutNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notesBinding = LayoutNotesBinding.inflate(layoutInflater)
        setContentView(notesBinding.root)
        notesBinding.toolbar.backBtn.setOnClickListener { finish() }
        notesBinding.toolbar.textTitle.setText(getString(R.string.note))

    }


}