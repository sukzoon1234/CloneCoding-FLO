package com.example.flo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()



        if(intent.hasExtra("title") && intent.hasExtra("singer") && intent.hasExtra("state")) {
            val title = intent.getStringExtra("title")
            val singer = intent.getStringExtra("singer")
            val state = intent.getIntExtra("state", 0)
            binding.mainMiniplayerTitleTv.text = title
            binding.mainMiniplayerSingerTv.text = singer
            if (state == View.VISIBLE) {
                binding.mainBtnMiniplayerPlayIv.visibility = View.VISIBLE
                binding.mainBtnMiniplayerPauseIv.visibility = View.GONE
            } else {
                binding.mainBtnMiniplayerPlayIv.visibility = View.GONE
                binding.mainBtnMiniplayerPauseIv.visibility = View.VISIBLE
            }
        }

        binding.mainPlayerLayout.setOnClickListener {
            val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(), binding.mainBtnMiniplayerPlayIv.visibility)
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("state", song.state)
            startActivity(intent)
            finish()
        }




        binding.mainBtnMiniplayerPlayIv.setOnClickListener {
            binding.mainBtnMiniplayerPauseIv.setVisibility(View.VISIBLE)
            binding.mainBtnMiniplayerPlayIv.setVisibility(View.GONE)
        }

        binding.mainBtnMiniplayerPauseIv.setOnClickListener {
            binding.mainBtnMiniplayerPlayIv.setVisibility(View.VISIBLE)
            binding.mainBtnMiniplayerPauseIv.setVisibility(View.GONE)
        }

        binding.mainBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

            }
            false
        }

    }

    private fun initNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

    }
}

