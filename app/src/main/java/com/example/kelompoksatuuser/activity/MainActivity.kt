package com.example.kelompoksatuuser.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kelompoksatuuser.R
import com.example.kelompoksatuuser.dashboard.DashboardFragment
import com.example.kelompoksatuuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dashboard = DashboardFragment()

        makeCurrentFragment(dashboard)
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home_menu -> makeCurrentFragment(dashboard)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_content, fragment)
            commit()
        }
    }
}