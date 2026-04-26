package com.onpe.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.onpe.app.ui.actas.ActasFragment
import com.onpe.app.ui.participacion.ParticipacionFragment
import com.onpe.app.ui.presidencial.PresidencialFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PresidencialFragment())
                .commit()
        }

        bottomNav.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_presidencial  -> PresidencialFragment()
                R.id.nav_actas         -> ActasFragment()
                R.id.nav_participacion -> ParticipacionFragment()
                else                   -> PresidencialFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            true
        }
    }
}
