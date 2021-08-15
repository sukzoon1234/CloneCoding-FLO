package com.example.listviewkt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class UserAdapter (val context: Context, val UserList: ArrayList<User>) : BaseAdapter() {

    //ListView에서 View를 가지고 왔을 때 어떻게 뿌려주는지 구현하는 부분
    //xml 파일의 View와 데이터를 연결하는 핵심 역할을 하는 메소드
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
       //LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다.
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item_user, null)

        //User.kt에 있는 클래스 모델대로 선언
        //위에서 생성된 view를 list_item_user.xml 파일의 각 View와 연결하는 과정.
        val profile = view.findViewById<ImageView>(R.id.iv_profile)
        val name = view.findViewById<TextView>(R.id.tv_name)
        val age = view.findViewById<TextView>(R.id.tv_age)
        val greeting = view.findViewById<TextView>(R.id.tv_greeting)
        val num = view.findViewById<ConstraintLayout>(R.id.user_num)

        val user = UserList[position]

        profile.setImageResource(user.profile)
        name.text = user.name
        age.text = user.age
        greeting.text = user.greeting
        num.id = user.num

        return view
    }

    //ListView에 속한 item의 전체 수를 반환
    override fun getCount(): Int {
        return UserList.size
    }

    //해당 위치의 item을 반환하는 메소드.
    override fun getItem(position: Int): Any {
        return UserList[position]
    }

    //해당 위치의 item id를 반환하는 메소드.
    override fun getItemId(position: Int): Long {
        return 0
    }
}