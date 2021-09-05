//EditText에 아이디를 입력하고, button을 클릭하면 TextView의 글이 변경되는 코드

package com.example.textviewkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.textviewkt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) { //앱이 최초로 실행될 때 수행되는 구문

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnGetText.setOnClickListener {
            val title = binding.etId.text.toString()
            binding.tvResult.text= title
        }
    }
}

