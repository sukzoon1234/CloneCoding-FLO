package com.example.intentkt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.intentkt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, SubActivity::class.java)
        binding.btnA.setOnClickListener {
            //다음 화면으로 이동하기 위한 intent 객체 생성
            val msg: String = binding.etSendMsg.text.toString()
            intent.putExtra("msg", msg)
            startActivity(intent)
        }
        binding.btnB.setOnClickListener {
            startActivity(intent)
        }
    }
}