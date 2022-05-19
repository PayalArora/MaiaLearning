package com.maialearning.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.maialearning.R
import com.maialearning.databinding.ActivityDashboardBinding
import com.maialearning.ui.fragments.HomeFragment
import com.maialearning.ui.fragments.MessageFragment
import com.maialearning.ui.fragments.NotesFragment
import com.maialearning.ui.fragments.ShortcutFragment

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        loadFragment(HomeFragment())
        setListeners()
    }
    private fun setListeners() {
        binding.contentDashboard.navigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    loadFragment(HomeFragment())
                }
                R.id.menu_shortcuts -> {
                    loadFragment(ShortcutFragment())
                }
                R.id.menu_notes -> {
                    loadFragment(NotesFragment())
                }
                R.id.menu_messages -> {
                    loadFragment(MessageFragment())

                }
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_content_dashboard, fragment)
        transaction.commit()
    }
}