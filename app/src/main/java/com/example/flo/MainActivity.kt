package com.example.flo

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.media.MediaPlayer
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
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private lateinit var song : Song

    private lateinit var getResult : ActivityResultLauncher<Intent> //songActivity  호출할때 사용

    private lateinit var player : MainPlayer

    lateinit var  mediaPlayer : MediaPlayer

    private var gson : Gson = Gson()

    init {instance = this}
    companion object {
        //자신의 인스턴스를 반환하는 getInstance() 메소드 등록.
        //companion object는 인스턴스르 생성하지 않고, 바로 사용할 수 있는 프로퍼티와 메소드의 집합이다.
        //SongActivity에서 mediaPlayer 변수 끌어다가 쓸 수 있게 하려고 사용!
        private var instance : MainActivity?=null
        fun getInstance():MainActivity? {
            return instance
        }
    }



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

        mediaPlayer = MediaPlayer.create(this, resources.getIdentifier(song.musicFile, "raw", this.packageName))

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
            intent.putExtra("musicFile", song.musicFile)
            getResult.launch(intent)

        }



        binding.mainBtnMiniplayerPlayIv.setOnClickListener {
            song.isPlaying = true
            setIsPlaying(true)
            mediaPlayer?.start()
        }

        binding.mainBtnMiniplayerPauseIv.setOnClickListener {
            song.isPlaying = false
            setIsPlaying(false)
            mediaPlayer?.pause()
//            Log.d("aaa", "${song.currentTime}")
//            Log.d("aaa", "${mediaPlayer?.currentPosition}")
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
        song.musicFile = data.getStringExtra("musicFile")!!

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

        Log.d("mainplayer", "메인화면 다시 createview")
        song = Song("LILAC", "아이유(IU)", 216000, 0, false, "lilac") //Song 객체 초기화
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer

    }

    inner class MainPlayer : Thread() {
        override fun run() {
            try{
                while (true) {
                    if (song.currentTime >= song.totalTime) {
                        song.currentTime = 0
                        song.isPlaying = false
                        binding.mainBottomSeekbar.progress = 0
                        mediaPlayer?.pause()
                        mediaPlayer?.seekTo(0)
                        Handler(Looper.getMainLooper()).post {
                            setIsPlaying(false)
                        }
                    }

                    if (song.isPlaying) {
                        Log.d("mainminiplayer", "mainminiplayer 쓰레드 잘 실행 중이다!")
                        sleep(100)
                        song.currentTime = song.currentTime + 100
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


    override fun onRestart() {
        super.onRestart()
        song.currentTime = mediaPlayer?.currentPosition
        binding.mainBottomSeekbar.progress = song.currentTime * 1000 / song.totalTime
    }


    override fun onStart() { //SongActivity에 갔다가 왔을때,  onCreate()가 호출되는게 아니라 onRestart() -> onStart() 순으로 호출되므로 !!
        super.onStart()

        Log.d("mainplayer", "쓰레드 역전하고 탈출")
        player = MainPlayer()
        player.start()


        Log.d("mainplayer", "mainPlayer 쓰레드 onstart")

//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
//        val jsonSong = sharedPreferences.getString("song", null)
//        song = if (jsonSong == null) {
//            Song("LILAC", "아이유(IU)", 216000, 0, false, "lilac")
//        } else {
//            gson.fromJson(jsonSong, Song::class.java)
//        }

    }


    override fun onStop() {
        super.onStop()
        player.interrupt()
        Log.d("mainplayer", "mainPlayer 쓰레드 종료~!!!")
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

