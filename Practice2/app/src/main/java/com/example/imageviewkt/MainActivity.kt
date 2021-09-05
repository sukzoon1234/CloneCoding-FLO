package com.example.imageviewkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.imageviewkt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var check: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToast.setOnClickListener {
            if(check == 0) {
                binding.ivProfile.setImageResource(R.drawable.robot)   //res 폴더를 R 이라고 표현한다.
                check = 1
            }
            else {
                binding.ivProfile.setImageResource(R.drawable.android)   //res 폴더를 R 이라고 표현한다.
                check = 0
            }
            Toast.makeText( this@MainActivity, "버튼이 클릭 되었습니다.", Toast.LENGTH_SHORT).show()

        }
    }
}