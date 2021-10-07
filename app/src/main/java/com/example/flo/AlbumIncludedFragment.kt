package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumIncludedBinding

class AlbumIncludedFragment: Fragment() {
    lateinit var binding : FragmentAlbumIncludedBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumIncludedBinding.inflate(inflater, container, false)

        binding.albumIncludedScrollView.setNestedScrollingEnabled(false);


        return binding.root
    }
}