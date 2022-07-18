package com.maialearning.ui.activity

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.maialearning.R
import com.maialearning.databinding.FragmentDashboardBinding
import com.maialearning.databinding.LayoutNotesBinding
import com.maialearning.util.DESCRIPTION
import com.maialearning.util.TITLE

class NotesDetailActivity : AppCompatActivity() {
    private lateinit var notesBinding: LayoutNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notesBinding = LayoutNotesBinding.inflate(layoutInflater)
        setContentView(notesBinding.root)
        notesBinding.toolbar.backBtn.setOnClickListener { finish() }
        notesBinding.toolbar.textTitle.setText(getString(R.string.note))
        intent.extras.let {
            notesBinding.textTitl.setText(it?.getString(TITLE))
            notesBinding.textDescription.setText(Html.fromHtml(it?.getString(DESCRIPTION))
            )
        }

    }


}