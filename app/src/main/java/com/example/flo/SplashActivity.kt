package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //워커스레드는 뷰 렌더링 작업을 하지 못함.
        //핸들러를 사용해서 스레드에 메시지 전달 (우리는 메인 스레드로 전달 할 계획) (왜냐면 뷰 렌더링 작업은 메인 스레드만 가능하므로)
        //루퍼의 역할 : 받아온 메시지를 꺼내와서 어디(메인스레드 혹은 다른 스레드)에 전달해야할지 처리해주는 역할을 함
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}