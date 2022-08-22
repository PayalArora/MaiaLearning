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
import com.maialearning.model.NotesModel
import com.maialearning.util.DESCRIPTION
import com.maialearning.util.TITLE
import com.maialearning.util.formateDateFromstring

class NotesDetailActivity : AppCompatActivity() {
    private lateinit var notesBinding: LayoutNotesBinding
    private var notesModel: NotesModel.NotesModelItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notesBinding = LayoutNotesBinding.inflate(layoutInflater)
        setContentView(notesBinding.root)
        notesBinding.toolbar.backBtn.setOnClickListener { finish() }
        notesBinding.toolbar.optionMenu.visibility=View.GONE
        notesBinding.toolbar.textTitle.setText(getString(R.string.note))
        intent.extras.let {
            notesBinding.textTitl.setText(it?.getString(TITLE))
            notesBinding.textDescription.setText(Html.fromHtml(it?.getString(DESCRIPTION)))

            notesModel= it?.getSerializable("DATA") as NotesModel.NotesModelItem?
            notesBinding.textDate.text =
                formateDateFromstring("MM/dd/YYYY","MMM dd yyyy, hh:mm a",notesModel?.noteCreationDate )

            if(!notesModel?.filename.isNullOrEmpty()){
                notesBinding.fileLay.visibility=View.VISIBLE
                notesBinding.docName.text=notesModel?.filename
            }
        }

    }


}