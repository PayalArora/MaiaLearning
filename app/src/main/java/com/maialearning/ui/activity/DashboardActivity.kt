package com.maialearning.ui.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast

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
import com.maialearning.util.prefhandler.SharedHelper
import com.maialearning.util.showLoadingDialog
import com.maialearning.viewmodel.DashboardViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    public lateinit var toolbarBinding: Toolbar
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private lateinit var dialog: Dialog
    lateinit var profileFilter: ProfileFilter

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
            profileFilter = ProfileFilter(this, layoutInflater)
            profileFilter.showDialog()
        }

        dashboardViewModel.getJwtToken()
        dashboardViewModel.jwtObserver.observe(this) {
            SharedHelper(this).jwtToken = it

        }
        dashboardViewModel.showLoading.observe(this) {
            if (it == true) {
                dialog = showLoadingDialog(this)
            } else {
                dialog.dismiss()
            }
        }
        dashboardViewModel.showError.observe(this) {
            dialog.dismiss()
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        loadFragment(HomeFragment())
        if (SharedHelper(this).picture != null && SharedHelper(this).picture?.length!! > 5) {
            Picasso.with(this).load(SharedHelper(this).picture).into(binding.toolbarProf)
        }
        setListeners()
    }

    private fun setListeners() {
        binding.contentDashboard.navigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    loadFragment(HomeFragment())
                    binding.toolbar.title = ""
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility =
                        View.VISIBLE
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility =
                        View.GONE
                }
                R.id.menu_shortcuts -> {
                    binding.toolbar.title = getString(R.string.shortcuts)
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.GONE
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility =
                        View.GONE
                    loadFragment(ShortcutFragment())
                }
                R.id.menu_notes -> {
                    binding.toolbar.title = getString(R.string.notes)
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.GONE
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility =
                        View.GONE
                    loadFragment(NotesFragment())
                }
                R.id.menu_messages -> {
                    binding.toolbar.title = getString(R.string.messanger)
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_maia).visibility = View.GONE
                    toolbarBinding.findViewById<ImageView>(R.id.toolbar_messanger).visibility =
                        View.VISIBLE
                    loadFragment(MessageFragment())

                }
            }
            true
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        toolbarBinding.visibility = View.VISIBLE
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_content_dashboard, fragment)
        transaction.commit()
    }

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_CHOOSE_PHOTO = 2

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            profileFilter.setData(requestCode, data)
        } else if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            profileFilter.setData(requestCode, data)
        }
    }

}