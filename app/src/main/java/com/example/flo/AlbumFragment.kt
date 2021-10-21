package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import android.view.MotionEvent

import android.view.View.OnTouchListener




class AlbumFragment : Fragment() {

    lateinit var binding : FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)



        val albumVpAdapter = AlbumVpAdapter(this)
        binding.albumViewPager.adapter = albumVpAdapter

        val tabTextList = arrayListOf("수록곡", "상세정보", "영상")
        TabLayoutMediator(binding.albumTabLayout, binding.albumViewPager) { tab, position ->
                    tab.text = tabTextList[position]
        }.attach()









        return binding.root
    }


}