package com.example.flo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import android.view.MotionEvent

import android.view.View.OnTouchListener
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson


class AlbumFragment : Fragment() {

    lateinit var binding : FragmentAlbumBinding

    private val gson : Gson? = Gson()

    private lateinit var jsonAlbum : String

    private lateinit var album : Album

    override fun onCreate(savedInstanceState: Bundle?) { // 초기화 해야하는 리소스들을 여기서 초기화.
        // fragment 를 생성하면서 념거 준 값들이 있다면, 여기서 변수에 넣어주면 된다.
        //But, 여기서 UI 초기화는 불가!!!
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        initAlbum()

        initViewPager()

        binding.albumBtnArrowBackIv.setOnClickListener {  //왼쪽 상단 뒤로가기 버튼 클릭 시
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commitAllowingStateLoss()
            activity?.supportFragmentManager?.popBackStack("Album", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            //HomeFragment에서 AlbumFragment 를 add 할때 backstack 에다가 추가 한 놈을
            // 뒤로가기 버튼을 누를때에는 pop 해 줘야 함!!(backstack 으로부터 fragment 를 꺼내기)
        }



        return binding.root
    }

    fun initAlbum() {


        jsonAlbum = arguments?.getString("Album")!!
        album = gson?.fromJson(jsonAlbum, Album::class.java)!!

        binding.albumTitleTv.text = album.title
        binding.albumSingerTv.text = album.singer
        binding.albumDateTv.text = album.date
        binding.albumImgIv.setImageResource(album.albumImg!!)
    }


    fun initViewPager() {
        val albumVpAdapter = AlbumVpAdapter(this)
        binding.albumViewPager.adapter = albumVpAdapter

        val tabTextList = arrayListOf("수록곡", "상세정보", "영상")
        TabLayoutMediator(binding.albumTabLayout, binding.albumViewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

}