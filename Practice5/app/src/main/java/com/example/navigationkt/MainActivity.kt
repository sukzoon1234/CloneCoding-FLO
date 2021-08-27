package com.example.navigationkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.navigationkt.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //네비게이션 뷰를 클릭을 했을때 네비게이션 아이템들을 selected 할수 있는 listener들을 상속 해야한다.
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNavi.setOnClickListener {
            binding.layoutDrawer.openDrawer(GravityCompat.START) // START는 left, END는 right 랑 같은 말!
            //한마디로 위 코드는 왼쪽에서부터 drawerLayout을 열어 준다는 뜻!
        }

        binding.naviView.setNavigationItemSelectedListener(this)
        //이 코드가 있어야 네비게이션의 각 item들이 클릭 가능한 상태로 바뀐다.
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //네비게이션 메뉴 아이템 클릭 시 수행하는 메소드
        when (item.itemId)   //네비게이션의 아이템을 클릭할 때 마다 item에 값이 들어가게 된다.
        //when구문은 다른 언어의 Switch구문과 동일하다.
        {
            R.id.access -> Toast.makeText(applicationContext, "접근성", Toast.LENGTH_SHORT).show()
            R.id.email -> Toast.makeText(applicationContext, "이메일", Toast.LENGTH_SHORT).show()
            R.id.message -> Toast.makeText(applicationContext, "메시지", Toast.LENGTH_SHORT).show()
        }
        binding.layoutDrawer.closeDrawers() // 네비게이션 뷰 닫기
        return false
    }

    override fun onBackPressed() { //스마트폰의 뒤로가기 버튼 실행
        if(binding.layoutDrawer.isDrawerOpen(GravityCompat.START)) {
            binding.layoutDrawer.closeDrawers()
        }
        else {
            super.onBackPressed()
        }
    }
}