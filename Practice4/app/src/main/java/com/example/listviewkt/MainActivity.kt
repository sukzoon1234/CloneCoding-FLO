package com.example.listviewkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.listviewkt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    var userList = arrayListOf<User>(
        User(R.drawable.android, "권석준", "23", "나는쭌갓", 1),
        User(R.drawable.android, "이명수", "22", "나는돼지", 2),
        User(R.drawable.android, "김윤찬", "24", "나는찬" ,3),
        User(R.drawable.android, "강승구", "25", "나는좆구", 4),
        User(R.drawable.android, "김진현", "21", "나는진매", 5),
        User(R.drawable.android, "권석준", "23", "나는쭌갓", 6),
        User(R.drawable.android, "이명수", "22", "나는돼지", 7),
        User(R.drawable.android, "김윤찬", "24", "나는찬", 8),
        User(R.drawable.android, "강승구", "25", "나는좆구", 9),
        User(R.drawable.android, "김진현", "21", "나는진매", 10)
    )

    override fun onCreate(savedInstanceState: Bundle?) { // 액티비티의 실행 시작 지점!
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val item = arrayOf("사과", "배", "딸기", "키위", "석준")
//        //context란 한 애기비티의 모든 정보를 담고있다.
//        binding.listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, item)
//        //listview는 adapter를 반드시 연결해야만 사용이 가능하다


        //안드로이드에서 기본 제공하는 ArrayAdapter 대신 직접 Custom Adapter (UserAdapter)를 만들기!!
        val userAdapter = UserAdapter(this, userList)
        binding.listView.adapter = userAdapter

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectItem = parent.getItemAtPosition(position) as User
            Toast.makeText(this, selectItem.name + "님은 " + selectItem.num + "번째 입니다.", Toast.LENGTH_SHORT).show()
        }
    }
}