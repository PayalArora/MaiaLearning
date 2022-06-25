package com.maialearning.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.maialearning.R
import com.maialearning.databinding.ActivityDashboardBinding
import com.maialearning.ui.bottomsheets.ProfileFilter
import com.maialearning.ui.fragments.HomeFragment
import com.maialearning.ui.fragments.MessageFragment
import com.maialearning.ui.fragments.NotesFragment
import com.maialearning.ui.fragments.ShortcutFragment

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    public lateinit var toolbarBinding: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        toolbarBinding = binding.toolbar
        binding.toolbar.title = ""
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.VISIBLE
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility = View.GONE
        toolbarBinding.findViewById<ImageView>(R.id.toolbar_prof).setOnClickListener {
            ProfileFilter(this, layoutInflater).showDialog()
        }

        loadFragment(HomeFragment())
        setListeners()
    }
    private fun setListeners() {
        binding.contentDashboard.navigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    loadFragment(HomeFragment())
                    binding.toolbar.title = ""
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.VISIBLE
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility = View.GONE
                }
                R.id.menu_shortcuts -> {
                    binding.toolbar.title =getString(R.string.shortcuts)
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.GONE
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility = View.GONE
                    loadFragment(ShortcutFragment())
                }
                R.id.menu_notes -> {
                    binding.toolbar.title =getString(R.string.notes)
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.GONE
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility = View.GONE
                    loadFragment(NotesFragment())
                }
                R.id.menu_messages -> {
                    binding.toolbar.title =getString(R.string.messanger)
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.GONE
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility = View.VISIBLE
                    loadFragment(MessageFragment())

                }
            }
            true
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        toolbarBinding.visibility=View.VISIBLE
    }
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_content_dashboard, fragment)
        transaction.commit()
    }
}