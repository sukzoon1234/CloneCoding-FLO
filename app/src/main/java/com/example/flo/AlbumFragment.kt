package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {

    lateinit var binding : FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        initFragment()

        binding.albumTopStickyView.bringToFront();

        binding.albumStickyScrollView.setNestedScrollingEnabled(false);

        binding.albumIncludedBtn.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.album_bottom_frm, AlbumIncludedFragment())
                .commitAllowingStateLoss()
        }
        binding.albumDetailBtn.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.album_bottom_frm, AlbumDetailFragment())
                .commitAllowingStateLoss()
        }
        binding.albumVideoBtn.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.album_bottom_frm, AlbumVideoFragment())
                .commitAllowingStateLoss()
        }





        return binding.root
    }

    private fun initFragment() {
        childFragmentManager.beginTransaction().replace(R.id.album_bottom_frm, AlbumIncludedFragment())
            .commitAllowingStateLoss()
    }

}