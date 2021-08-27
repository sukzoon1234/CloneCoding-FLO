package com.example.listviewpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.listviewpractice.databinding.ListItemUserBinding

class UserAdapter (val context: Context, val userList: ArrayList<User>) : BaseAdapter() {
    override fun getCount(): Int {
        return userList.size
    }

    override fun getItem(position: Int): Any {
        return userList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val binding = ListItemUserBinding.inflate(LayoutInflater.from(context))
        //val view: View = LayoutInflater.from(context).inflate(R.layout.list_item_user, null)

        val view: View = binding.root

        val profile = binding.ivProfile
        val name = binding.tvName
        val greet = binding.tvGreet
        val age = binding.tvAge

        val user = userList[position]

        profile.setImageResource(user.profile)
        name.text = user.name
        greet.text = user.greet
        age.text = user.age.toString()

        return view
    }
}