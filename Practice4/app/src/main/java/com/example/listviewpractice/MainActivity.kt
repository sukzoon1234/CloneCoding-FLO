package com.example.listviewpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import com.example.listviewpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    var userList = arrayListOf<User>(
        User(R.drawable.person, "권석준", 23, "나는쭌갓"),
        User(R.drawable.person, "이명수", 22, "나는돼지"),
        User(R.drawable.person, "김윤찬", 24, "나는찬"),
        User(R.drawable.person, "강승구", 25, "나는좆구"),
        User(R.drawable.person, "김진현", 21, "나는진매"),
        User(R.drawable.person, "권석준", 23, "나는쭌갓"),
        User(R.drawable.person, "이명수", 22, "나는돼지"),
        User(R.drawable.person, "김윤찬", 24, "나는찬"),
        User(R.drawable.person, "강승구", 25, "나는좆구"),
        User(R.drawable.person, "김진현", 21, "나는진매")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userAdapter: UserAdapter = UserAdapter(this, userList)
        binding.listView.adapter = userAdapter

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectItem = parent.getItemAtPosition(position) as User
            Toast.makeText(this, selectItem.name + "입니다", Toast.LENGTH_SHORT).show()
        }
    }
}