package com.example.flo

import android.content.Intent
import android.graphics.Color
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

class SongActivity : AppCompatActivity() {
    lateinit var binding : com.example.flo.databinding.ActivitySongBinding

    private var song : Song = Song()

    private lateinit var player : Player


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


        player = Player() //Player 클래스의 인스턴스 생성. 한마디로 쓰레드를 실행하기 위한 인스턴스 생성.
        player.start()  //start 메소드로 쓰레드 시작!


        binding.playerSongSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            // SeekBar의 값이 변경되었을 때!
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) { //seekbar를 터치 한 채로 스크롤하면서 시간 변경하는 중!! (seekbar의 값 변경중-ing!!)
                binding.playerTimeStartTv.text = String.format("%02d:%02d", progress*song.totalTime/1000/60, progress*song.totalTime/1000%60)

            }

            // 값을 변경하기 위해 유저가 터치했을 때
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            // 값을 변경한 후 터치를 때었을 때
            override fun onStopTrackingTouch(seekBar: SeekBar?) { //seekbar 값을 변경하다가 터치를 때는 순간의 progress 를 통해서 currentTime 계산 후 song에 대입!
                song.currentTime = (seekBar?.progress!!)*song.totalTime/1000
            }

        })




        binding.playerBtnDownIv.setOnClickListener {
            onBackPressed()
        }


        binding.playerBtnPlayIv.setOnClickListener {
            setIsPlaying(true)
            song.isPlaying = true
        }

        binding.playerBtnPauseIv.setOnClickListener {
            setIsPlaying(false)
            song.isPlaying = false
        }


        var indexRepeat : Int = 0
        binding.playerBtnRepeatIv.setOnClickListener {
            indexRepeat= setRepeatBtn(indexRepeat)
        }


        var indexRandom : Boolean = false
        binding.playerBtnRandomIv.setOnClickListener {
            indexRandom = setRandomBtn(indexRandom)
        }



    }

    private fun initSongActivity() {
        if(intent.hasExtra("title") && intent.hasExtra("singer") && intent.hasExtra("totalTime") && intent.hasExtra("currentTime") && intent.hasExtra("isPlaying")) {
//            Log.d("checking", ": ${intent.getStringExtra("title")}")
            song.title = intent.getStringExtra("title")!!
            song.singer = intent.getStringExtra("singer")!!
            song.totalTime = intent.getIntExtra("totalTime", 0)
            song.currentTime = intent.getIntExtra("currentTime", 0)
            song.isPlaying = intent.getBooleanExtra("isPlaying", false)
            binding.playerTitleTv.text = song.title
            binding.playerSingerTv.text = song.singer
            binding.playerTimeStartTv.text = String.format("%02d:%02d", song.currentTime / 60, song.currentTime % 60)
            binding.playerTimeEndTv.text = String.format("%02d:%02d", song.totalTime / 60, song.totalTime % 60)
            binding.playerSongSeekbar.progress = song.currentTime * 1000 / song.totalTime
            setIsPlaying(song.isPlaying)

        }
    }

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

    private fun checkIsPlaying(img: ImageView) : Boolean { //play 버튼이 visible 이면 true 리턴.
        if(img.visibility == View.VISIBLE) return false
        return true
    }

    override fun onBackPressed()
    {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("title", song.title)
        intent.putExtra("singer", song.singer)
        intent.putExtra("totalTime", song.totalTime)
        intent.putExtra("currentTime", song.currentTime)
        intent.putExtra("isPlaying", song.isPlaying)
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
                        break
                    }
                    if (song.isPlaying) { //노래가 실행 중일때만 쓰레드 실행. (seekbar 진행 + 시간초 증가)
                        Log.d("player", "player 쓰레드 잘 실행 중이다!")
                        sleep(1000)
                        song.currentTime++
//                <1번방법 : Handler>
                        Handler(Looper.getMainLooper()).post {
                            binding.playerSongSeekbar.progress = song.currentTime*1000/song.totalTime
                            binding.playerTimeStartTv.text = String.format("%02d:%02d", song.currentTime/60, song.currentTime%60)
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

    override fun onDestroy() { // 화면(SongActivity) 이 꺼질때 onDestroy 함수가 호출이 된다.
        player.interrupt() //오류를 내서 쓰레드를 종료 시켜 버리는 함수.
        super.onDestroy()
        Log.d("destroy", "쓰레드 종료 + 화면꺼짐")
    }
}
