package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentHomeTopBinding

class HomeTopFragment(val imgsrc : Int) : Fragment() {
    lateinit var binding : FragmentHomeTopBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeTopBinding.inflate(inflater, container, false)

        binding.homeTopIv.setImageResource(imgsrc)


        return binding.root
    }
}