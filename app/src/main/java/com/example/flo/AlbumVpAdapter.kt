package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumVpAdapter (fragment:Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            1 -> AlbumDetailFragment()
            2 -> AlbumVideoFragment()
            else -> AlbumIncludedFragment()
        }
    }

}