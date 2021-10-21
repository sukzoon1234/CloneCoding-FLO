package com.example.flo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding : ActivitySongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.apply { //Status Bar 투명하게
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }



        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this) // ImageView 모서리 Rounding
            .load(R.drawable.img_album_exp2)
            .transform( RoundedCorners(80))
            .into(binding.playerAlbumIv)


        if(intent.hasExtra("title") && intent.hasExtra("singer") && intent.hasExtra("state")) {
            val title = intent.getStringExtra("title")
            val singer = intent.getStringExtra("singer")
            val state = intent.getIntExtra("state", 0)
            binding.playerTitleTv.text = title
            binding.playerSingerTv.text = singer
            if (state == View.VISIBLE) {
                binding.playerBtnPlayIv.visibility = View.VISIBLE
                binding.playerBtnPauseIv.visibility = View.GONE
            } else {
                binding.playerBtnPlayIv.visibility = View.GONE
                binding.playerBtnPauseIv.visibility = View.VISIBLE
            }

        }


        binding.playerBtnDownIv.setOnClickListener {
            onBackPressed()
        }


        binding.playerBtnPlayIv.setOnClickListener {
            binding.playerBtnPauseIv.setVisibility(View.VISIBLE)
            binding.playerBtnPlayIv.setVisibility(View.GONE)
        }

        binding.playerBtnPauseIv.setOnClickListener {
            binding.playerBtnPlayIv.setVisibility(View.VISIBLE)
            binding.playerBtnPauseIv.setVisibility(View.GONE)
        }





        var indexRepeat : Int = 0
        binding.playerBtnRepeatIv.setOnClickListener {
            when (indexRepeat) {
                0 -> binding.playerBtnRepeatIv.setImageResource(R.drawable.btn_playlist_repeat_on)
                1 -> binding.playerBtnRepeatIv.setImageResource(R.drawable.btn_playlist_repeat_on1)
                2 -> binding.playerBtnRepeatIv.setImageResource(R.drawable.btn_playlist_repeat_playlist)
                3 -> binding.playerBtnRepeatIv.setImageResource(R.drawable.nugu_btn_repeat_inactive)
            }
            if (indexRepeat == 4) indexRepeat = 0
            else indexRepeat++
        }

        var indexRandom : Boolean = false
        binding.playerBtnRandomIv.setOnClickListener {
            if(indexRandom == false) {
                binding.playerBtnRandomIv.setImageResource(R.drawable.btn_playlist_random_on)
                indexRandom = true
            } else {
                binding.playerBtnRandomIv.setImageResource(R.drawable.nugu_btn_random_inactive)
                indexRandom = false
            }
        }



    }
    override fun onBackPressed()
    {
        val song = Song(binding.playerTitleTv.text.toString(), binding.playerSingerTv.text.toString(), binding.playerBtnPlayIv.visibility)
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("title", song.title)
        intent.putExtra("singer", song.singer)
        intent.putExtra("state", song.state)
        startActivity(intent)
        finish()
    }
}
