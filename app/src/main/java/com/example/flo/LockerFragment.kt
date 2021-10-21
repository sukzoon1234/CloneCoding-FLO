package com.example.flo

import android.os.Bundle
import android.os.LocaleList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator


class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerVpAdapter = LockerVpAdapter(this)
        binding.lockerViewPager.adapter = lockerVpAdapter

        val tabTextList = arrayListOf("저장한 곡", "음악파일")

        TabLayoutMediator (binding.lockerTabLayout, binding.lockerViewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()

        return binding.root

    }


}