package com.example.flo

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private lateinit var song : Song

    private lateinit var getResult : ActivityResultLauncher<Intent> //songActivity  호출할때 사용

    private lateinit var player : MainPlayer

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




        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == RESULT_OK) {
//                Log.d("check", "Hi: ${result.data?.getStringExtra("title")}")
                setPlayer(result.data!!) //intent 넘겨주기
            }
        }

        binding.mainPlayerLayout.setOnClickListener {
//            val totalTime = song.totalTime
//            val currentTime = song.currentTime
//            song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(), totalTime, currentTime, checkIsPlaying(binding.mainBtnMiniplayerPlayIv))
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("totalTime", song.totalTime)
            intent.putExtra("currentTime", song.currentTime)
            intent.putExtra("isPlaying", song.isPlaying)
            getResult.launch(intent)

        }



        binding.mainBtnMiniplayerPlayIv.setOnClickListener {
            song.isPlaying = true
            setIsPlaying(true)
        }

        binding.mainBtnMiniplayerPauseIv.setOnClickListener {
            song.isPlaying = false
            setIsPlaying(false)
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


    private fun setPlayer(data : Intent) {
        song.title = data.getStringExtra("title")!!
        song.singer = data.getStringExtra("singer")!!
        song.totalTime = data.getIntExtra("totalTime", 0)
        song.currentTime = data.getIntExtra("currentTime", 0)
        song.isPlaying = data.getBooleanExtra("isPlaying", false)
        Log.d("main", "(쓰레드) $song")
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainBottomSeekbar.progress = song.currentTime * 1000 / song.totalTime
        setIsPlaying(song.isPlaying)
    }

    private fun setIsPlaying(isPlaying: Boolean) {
        if(isPlaying == true) {
            binding.mainBtnMiniplayerPlayIv.visibility = View.GONE
            binding.mainBtnMiniplayerPauseIv.visibility = View.VISIBLE
        } else {
            binding.mainBtnMiniplayerPlayIv.visibility = View.VISIBLE
            binding.mainBtnMiniplayerPauseIv.visibility = View.GONE
        }
    }


    private fun initNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        song = Song("LILAC", "아이유(IU)", 214, 0, false) //Song 객체 초기화
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer

    }

    inner class MainPlayer : Thread() {
        override fun run() {
            try{
                while (true) {
                    if(song.currentTime >= song.totalTime){
                        break
                    }
                    if (song.isPlaying) {
                        Log.d("mainminiplayer", "mainminiplayer 쓰레드 잘 실행 중이다!")
                        sleep(1000)
                        song.currentTime++
                        Handler(Looper.getMainLooper()).post {
                            binding.mainBottomSeekbar.progress = song.currentTime * 1000 / song.totalTime
                        }
                    }
                }
            } catch (e : InterruptedException) {
                Log.d("mainplayer", "쓰레드 실행되다가 종료됐따!")
            }
        }
    }


    override fun onStart() { //SongActivity에 갔다가 왔을때,  onCreate()가 호출되는게 아니라 onRestart() -> onStart() 순으로 호출되므로 !!
        super.onStart()
        player = MainPlayer()
        player.start()
    }


    override fun onStop() {
        player.interrupt()
        super.onStop()
        Log.d("mainplayer", "mainPlayer 쓰레드 종료~!!!")
    }
}

