package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class HomeTopVpAdapter(fragment : Fragment) : FragmentStateAdapter(fragment){
    private var fragmentList : ArrayList<Fragment> = ArrayList<Fragment>()
    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
        notifyItemInserted(fragmentList.size-1)
    }
}