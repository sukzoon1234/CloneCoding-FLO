package com.example.navigationpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.navigationpractice.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNavi.setOnClickListener{
            binding.drawerLayout.openDrawer(GravityCompat.START )
        }
        binding.naviView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            R.id.origin -> Toast.makeText(applicationContext, "기본",Toast.LENGTH_SHORT).show()
            R.id.social -> Toast.makeText(applicationContext, "소셜",Toast.LENGTH_SHORT).show()
            R.id.promotion -> Toast.makeText(applicationContext, "프로모션",Toast.LENGTH_SHORT).show()
        }
        binding.drawerLayout.closeDrawers()
        return false
    }

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawers()
        }
        else {
            super.onBackPressed()
        }
    }
}