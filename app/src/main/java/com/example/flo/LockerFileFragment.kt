package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLockerFileBinding

class LockerFileFragment : Fragment() {
    lateinit var binding : FragmentLockerFileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerFileBinding.inflate(inflater, container, false)

        return binding.root
    }
}