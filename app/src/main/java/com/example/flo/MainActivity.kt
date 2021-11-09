package com.example.flo

import android.content.ContentValues.TAG
import android.content.Context
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
import androidx.fragment.app.FragmentTransaction
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import kotlin.reflect.typeOf


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private lateinit var song : Song

    private lateinit var jsonSong : String // song 객체를 json 형태로 변환해서 저장 (string으로 저장)

    private var gson : Gson = Gson()

    private lateinit var getResult : ActivityResultLauncher<Intent> //songActivity  호출할때 사용

    private lateinit var player : MainPlayer

    var  mediaPlayer : MediaPlayer? = null //mediaPlayer 에 null 들어갈 수 있음.


    init { instance = this}
    companion object {
        //자신의 인스턴스를 반환하는 getInstance() 메소드 등록.
        //companion object는 인스턴스를 생성하지 않고, 바로 클래스의 내부에 접근 가능.
        //SongActivity에서 mediaPlayer 변수 끌어다가 쓸 수 있게 하려고 사용!
        private var instance : MainActivity? = null
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


        binding.mainPlayerLayout.setOnClickListener {

//            intent.putExtra("title", song.title)
//            intent.putExtra("singer", song.singer)
//            intent.putExtra("totalTime", song.totalTime)
//            intent.putExtra("currentTime", song.currentTime)
//            intent.putExtra("isPlaying", song.isPlaying)
//            intent.putExtra("musicFile", song.musicFile)

            val intent = Intent(this, SongActivity::class.java)
            jsonSong = gson.toJson(song)
            intent.putExtra("jsonSong", jsonSong)
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

//
//        val transaction : FragmentTransaction = activity!!.supportFragmentManager.beginTransaction().add(R.id.main_frm, AlbumFragment())
//        //add 는 fragment 추가. replace 는 fragment 교체
//
//        transaction.addToBackStack(null)
//        transaction.commitAllowingStateLoss()


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
//        song.title = data.getStringExtra("title")!!
//        song.singer = data.getStringExtra("singer")!!
//        song.totalTime = data.getIntExtra("totalTime", 0)
//        song.currentTime = data.getIntExtra("currentTime", 0)
//        song.isPlaying = data.getBooleanExtra("isPlaying", false)
//        song.musicFile = data.getStringExtra("musicFile")!!

        Log.d("intent에 담겨온 data", "$data") // intent에 담겨온 data: Intent { cmp=com.example.flo/.MainActivity (has extras) }

        jsonSong = data.getStringExtra("jsonSong")!!
        Log.d("jsonSong","$jsonSong") // jsonSong: {"currentTime":128872,"isPlaying":false,"musicFile":"lilac","singer":"아이유(IU)","title":"LILAC","totalTime":216000}

        val jsonObject = JSONObject(jsonSong)
        Log.d("jsonObject","$jsonObject")  // jsonObject: {"currentTime":68280,"isPlaying":true,"musicFile":"lilac","singer":"아이유(IU)","title":"LILAC","totalTime":216000}

        song = gson.fromJson(jsonSong, Song::class.java)
        Log.d("song객체", "$song")  // Song(title=LILAC, singer=아이유(IU), totalTime=216000, currentTime=128872, isPlaying=false, musicFile=lilac)



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

        val sharedPreferences = getSharedPreferences("jsonSong", MODE_PRIVATE)
        val jsonSong = sharedPreferences.getString("jsonSong", null)
        song = if (jsonSong == null) {
            Song("LILAC", "아이유(IU)", R.drawable.img_album_exp2,216000, 0, false, "lilac")
        } else {
            gson.fromJson(jsonSong, Song::class.java)
        }

        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainBottomSeekbar.progress = song.currentTime * 1000 / song.totalTime
        setIsPlaying(song.isPlaying)

        mediaPlayer = MediaPlayer.create(this, resources.getIdentifier(song.musicFile, "raw", this.packageName))
        mediaPlayer?.seekTo(song.currentTime)
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
        if (mediaPlayer == null) {
            initNavigation()
            Log.d("엥,,?","(쓰레드), 엥,,,?")
        }
        else {
            song.currentTime = mediaPlayer?.currentPosition!!
            binding.mainBottomSeekbar.progress = song.currentTime * 1000 / song.totalTime
        }
    }


    override fun onStart() { //SongActivity에 갔다가 왔을때,  onCreate()가 호출되는게 아니라 onRestart() -> onStart() 순으로 호출되므로 !!
        super.onStart()

        Log.d("mainplayer", "쓰레드 역전하고 탈출")

        player = MainPlayer()
        player.start()


        Log.d("mainplayer", "mainPlayer 쓰레드 onstart")


    }


    override fun onStop() {
        super.onStop()
        player.interrupt()
        Log.d("mainplayer", "mainPlayer 쓰레드 onstop~!!!")
    }

    override fun onDestroy() {
        super.onDestroy()

        song.isPlaying = false
        setIsPlaying(false)
        mediaPlayer?.pause()


        val sharedPreferences = getSharedPreferences("jsonSong", MODE_PRIVATE)
        val editor = sharedPreferences.edit()    //sharedPreferences 를 조작할 때 사용!!
//        //객체(song)를 json으로 변환해주는 중간다리 역할 : Gson!!!

        jsonSong = gson.toJson(song) //song 객체를 Gson 을 통해서 json 형태로 변환
        editor.putString("jsonSong", jsonSong)
        editor.apply()


        mediaPlayer?.release()
        mediaPlayer = null
        Log.d("mainplayer", "mainPlayer 쓰레드 ondestoy~!!!")
    }
}

