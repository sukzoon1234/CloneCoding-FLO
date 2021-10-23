package com.example.intentkt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.intentkt.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.hasExtra("msg")) {
            val getMsg = intent.getStringExtra("msg")
            binding.tvGetMsg.text = getMsg
        }
        binding.btnToMain.setOnClickListener {
            val subIntent = Intent(this, MainActivity::class.java)
            startActivity(subIntent)
        }
    }
}