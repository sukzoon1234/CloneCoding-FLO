package com.example.flo

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {
    lateinit var binding : com.example.flo.databinding.ActivitySongBinding

    private var song : Song = Song()

    private lateinit var jsonSong : String // song 객체를 json 형태로 변환해서 저장 (string으로 저장)

    private var gson : Gson = Gson()

    private lateinit var player : Player

    private val mainActivity = MainActivity.getInstance() //MainActivity의 mediaPlayer 를 사용하기 위한 코드


    private var indexRepeat : Int = 0
    private var indexRandom : Boolean = false

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
        initSongActivity()


        Glide.with(this) // ImageView 모서리 Rounding
            .load(R.drawable.img_album_exp2)
            .transform( RoundedCorners(80))
            .into(binding.playerAlbumIv)





        binding.playerSongSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            // SeekBar의 값이 변경되었을 때!
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) { //seekbar를 터치 한 채로 스크롤하면서 시간 변경하는 중!! (seekbar의 값 변경중-ing!!)
                binding.playerTimeStartTv.text = String.format("%02d:%02d", progress*song.totalTime/1000000/60, progress*song.totalTime/1000000%60)

            }

            // 값을 변경하기 위해 유저가 터치했을 때
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            // 값을 변경한 후 터치를 때었을 때
            override fun onStopTrackingTouch(seekBar: SeekBar?) { //seekbar 값을 변경하다가 터치를 때는 순간의 progress 를 통해서 currentTime 계산 후 song에 대입!
                song.currentTime = (seekBar?.progress!!)*song.totalTime/1000
                mainActivity?.mediaPlayer?.seekTo(song.currentTime)
            }

        })




        binding.playerBtnDownIv.setOnClickListener {
            onBackPressed()
        }


        binding.playerBtnPlayIv.setOnClickListener {
            setIsPlaying(true)
            song.isPlaying = true
            mainActivity?.mediaPlayer?.start() //MainActivity의 mediaPlayer 를 이런 방식으로 사용.
        }

        binding.playerBtnPauseIv.setOnClickListener {
            setIsPlaying(false)
            song.isPlaying = false
            mainActivity?.mediaPlayer?.pause()
        }



        binding.playerBtnRepeatIv.setOnClickListener {
            indexRepeat= setRepeatBtn(indexRepeat)
        }



        binding.playerBtnRandomIv.setOnClickListener {
            indexRandom = setRandomBtn(indexRandom)
        }



    }

    private fun initSongActivity() {

//        song.title = intent.getStringExtra("title")!!
//        song.singer = intent.getStringExtra("singer")!!
//        song.totalTime = intent.getIntExtra("totalTime", 0)
//        song.currentTime = intent.getIntExtra("currentTime", 0)
//        song.isPlaying = intent.getBooleanExtra("isPlaying", false)
//        song.musicFile = intent.getStringExtra("musicFile")!!

        jsonSong = intent.getStringExtra("jsonSong")!!
        song = gson.fromJson(jsonSong, Song::class.java)

        Log.d("stop", "쓰레드  main으로부터 데이터 받기 성공")

        binding.playerTitleTv.text = song.title
        binding.playerSingerTv.text = song.singer
        binding.playerTimeStartTv.text = String.format("%02d:%02d", song.currentTime/1000/ 60, song.currentTime/1000 % 60)
        binding.playerTimeEndTv.text = String.format("%02d:%02d", song.totalTime/1000 / 60, song.totalTime/1000 % 60)
        binding.playerSongSeekbar.progress = song.currentTime * 1000 / song.totalTime
        setIsPlaying(song.isPlaying)

    }
//    }

    private fun setRepeatBtn(indexRepeat : Int) : Int {
        when (indexRepeat) {
                0 -> binding.playerBtnRepeatIv.setImageResource(R.drawable.btn_playlist_repeat_on)
                1 -> binding.playerBtnRepeatIv.setImageResource(R.drawable.btn_playlist_repeat_on1)
                2 -> binding.playerBtnRepeatIv.setImageResource(R.drawable.btn_playlist_repeat_playlist)
                3 -> binding.playerBtnRepeatIv.setImageResource(R.drawable.nugu_btn_repeat_inactive)
            }
        var tmp = indexRepeat

        if (tmp == 4) return 0
        return ++tmp
    }

    private fun setRandomBtn(indexRandom : Boolean) : Boolean {
        if(indexRandom == false) {
            binding.playerBtnRandomIv.setImageResource(R.drawable.btn_playlist_random_on)
            return true
        } else {
            binding.playerBtnRandomIv.setImageResource(R.drawable.nugu_btn_random_inactive)
            return false
        }
    }


    private fun setIsPlaying(isPlaying: Boolean) {
        if(isPlaying == true) {
            binding.playerBtnPlayIv.visibility = View.GONE
            binding.playerBtnPauseIv.visibility = View.VISIBLE
        } else {
            binding.playerBtnPlayIv.visibility = View.VISIBLE
            binding.playerBtnPauseIv.visibility = View.GONE
        }
    }


    override fun onBackPressed()
    {

//        intent.putExtra("title", song.title)
//        intent.putExtra("singer", song.singer)
//        intent.putExtra("totalTime", song.totalTime)
//        intent.putExtra("currentTime", song.currentTime)
//        intent.putExtra("isPlaying", song.isPlaying)
//        intent.putExtra("musicFile", song.musicFile)
        intent = Intent(this, MainActivity::class.java)
        jsonSong = gson.toJson(song)
        intent.putExtra("jsonSong", jsonSong)
        setResult(RESULT_OK, intent)
        finish()
        Log.d("back", "(쓰레드)뒤로가기 실행")
    }



    //inner 클래스로 선언해야 내부클래스로 선언 되면서 전역변수로 선언된 변수들을 사용할 수 있음.
    inner class Player : Thread() { // 쓰레드를 상속받음으로써 Player 클래스를 워커스레드로 활용 할 계획.

        override fun run() {
        //위에서 Player 클래스의 인스턴스(player)를 만들고, player.start() 를 실행하면 쓰레드가 실행되면서 run() 함수가 실행된다.
        //그리고 run 안의 코드가 끝나야 스레드가 종료된다.

            try {
                while (true) {
                    if (song.currentTime >= song.totalTime) { //노래가 끝나면 run 함수가 종료되면서 쓰레드도 종료!
                        if (indexRepeat == 2) {
                            mainActivity?.mediaPlayer?.seekTo(0)
                            mainActivity?.mediaPlayer?.start()
                            song.currentTime =0
                            continue
                        }
                        song.currentTime = 0
                        song.isPlaying = false
                        binding.playerSongSeekbar.progress = 0
                        mainActivity?.mediaPlayer?.pause()
                        mainActivity?.mediaPlayer?.seekTo(0)
                        Handler(Looper.getMainLooper()).post {
                            setIsPlaying(false)
                        }
                    }
                    if (song.isPlaying) { //노래가 실행 중일때만 쓰레드 실행. (seekbar 진행 + 시간초 증가)
                        Log.d("player", "player 쓰레드 잘 실행 중이다!")
                        sleep(100)
                        song.currentTime = song.currentTime + 100
//                <1번방법 : Handler>
                        Handler(Looper.getMainLooper()).post {
                            binding.playerSongSeekbar.progress = song.currentTime*1000/song.totalTime
                            binding.playerTimeStartTv.text = String.format("%02d:%02d", song.currentTime/1000/60, song.currentTime/1000%60)
                        }
//                        //<2번방법>
//                        runOnUiThread {
//                            binding.playerSongSeekbar.progress = song.currentTime*1000/song.totalTime
//                            binding.playerTimeStartTv.text = String.format("%02d:%02d", song.currentTime/60, song.currentTime%60)
//                        }
                    }
                }
            } catch (e : InterruptedException) { // try 구문 안에서  catch(...) 의 ... 라는 오류가 나면 앱이 오류가 나는 대신에 catch 구문을 실행한다.
                Log.d("interrupt", "Player 쓰레드 정상 종료!")
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        song.currentTime = mainActivity?.mediaPlayer?.currentPosition!!
        binding.playerSongSeekbar.progress = song.currentTime * 1000 / song.totalTime
    }

    override fun onStart() {
        super.onStart()
        Log.d("stop", "쓰레드  화면 onstart")

        player = Player() //Player 클래스의 인스턴스 생성. 한마디로 쓰레드를 실행하기 위한 인스턴스 생성.
        player.start()  //start 메소드로 쓰레드 시작!
    }


    override fun onStop() {
        super.onStop()
        Log.d("stop", "쓰레드  화면 onStop")
        player.interrupt() // 쓰레드 종료



    }


    override fun onDestroy() { // 화면(SongActivity) 이 꺼질때 onDestroy 함수가 호출이 된다.
        super.onDestroy()


//        // 데이터를 내부 저장소 어딘가에 저장하는 놈.
//        // 간단한 설정값들을 저장 해 놓을때 매우 유용.
//        // 중요한 데이터라면 당연히 서버나 데이터베이스에 파일로 저장해야 함.
        val sharedPreferences = getSharedPreferences("jsonSong", MODE_PRIVATE)
        val editor = sharedPreferences.edit()    //sharedPreferences 를 조작할 때 사용!!
//        //객체(song)를 json으로 변환해주는 중간다리 역할 : Gson!!!
        jsonSong = gson.toJson(song) //song 객체를 Gson 을 통해서 json 형태로 변환
        editor.putString("jsonSong", jsonSong)
        editor.apply()

        Log.d("destroy", "쓰레드 종료 + 화면꺼짐")


    }
}
 // !!!!!!!!!!!!!  SongActivity에서 바탕화면 나가서 강제종료하는거랑, MainActivity로 가는거랑 코드적으로 구분지어서 코딩 해야돼!!!ㅜㅠ
//////////////////////////////