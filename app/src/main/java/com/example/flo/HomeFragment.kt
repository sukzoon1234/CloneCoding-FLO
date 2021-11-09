package com.example.flo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.flo.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    private lateinit var bannerVp : BannerVp

    private var albumList = ArrayList<Album>()

    private val gson : Gson = Gson()

    private lateinit var jsonAlbum : String

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initViewPager()

        initTodayMusicRecyclerView()



//        binding.homeTodayMusic01Iv.setOnClickListener {
//            (context as MainActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.main_frm, AlbumFragment())
//                .commitAllowingStateLoss()
//        }


        return binding.root
    }


    inner class BannerVp : Thread() {

        private var index = 0
        private val numOfBanners = 5

        override fun run() {
            while (true) {
                try{
                    sleep(4500)
                    index++

                    Handler(Looper.getMainLooper()).post {

                        binding.homeTopVp.setCurrentItem(index, true)
                        binding.homeBannerViewpager.setCurrentItem(index, true)
                    }
                    if(index == numOfBanners) {
                        index = 0
                    }
                } catch (e : InterruptedException) {
                    Log.d("interrupt", "Banner 쓰레드 정상 종료!")
                }
            }
        }
    }

    fun initTodayMusicRecyclerView() {
        albumList.apply {
            add(Album("IU 5th Album 'LILAC'", "아이유 (IU)", "021.03.25 | 정규 | 댄스 팝",  R.drawable.img_album_exp2,
                arrayListOf(Song("LILAC", "아이유(IU)", R.drawable.img_album_exp2,216000, 0, false, "lilac"),
                    Song("LILAC", "아이유(IU)", R.drawable.img_album_exp2,216000, 0, false, "lilac"),
                    Song("LILAC", "아이유(IU)", R.drawable.img_album_exp2,216000, 0, false, "lilac"))
            ))
            add(Album("Butter", "방탄소년단","2021.05.21 | 싱글 | 댄스",  R.drawable.img_album_butter_bts, null))
            add(Album("LUCKYNUMBERS", "다이나믹 듀오", "2013.07.01 | 정규 | 힙합,랩,팝 랩", R.drawable.img_album_luckynumbers_dinamicduo, null))
            add(Album("치맛바람", "브레이브걸스", "2021.06.17 | 미니 | 댄스 팝", R.drawable.img_album_skirtwind_bravegirls, null))
            add(Album("신호등", "이무진", "2021.05.14 | 싱글 | 락", R.drawable.img_album_trafficlight_mujin, null))
            add(Album("Next Level", "aespa", "2021.05.17 | 싱글 | 댄스 팝", R.drawable.img_album_nextlevel_aespa, null))
            add(Album("너를 생각해", "주시크 (Joosiq)", "2021.09.22 | 싱글 | 인디 팝,알앤비", R.drawable.img_album_thinkyou_joosiq, null))
        }

        val todayMusicAdapter = HomeTodayMusicRvAdapter(albumList)

        binding.homeTodayMusicRecyclerView.adapter = todayMusicAdapter

        todayMusicAdapter.setMyItemClickListener(object : HomeTodayMusicRvAdapter.MyItemClickListener {
            override fun onItemClick(album: Album) { //앨범 클릭했을 때

                val transaction : FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, AlbumFragment().apply {
                        jsonAlbum = gson.toJson(album)
                        arguments = Bundle().apply {
                            putString("Album", jsonAlbum)
                        }
                    })
                //add 는 fragment 추가. replace 는 fragment 교체
                transaction.addToBackStack("Album")
                transaction.commitAllowingStateLoss()
            }

            override fun onPlayClick(album : Album) { // 작은 플레이버튼 클릭했을 때

            }
        })
    }


    fun initViewPager() {
        val topAdapter = HomeTopVpAdapter(this)
        topAdapter.addFragment(HomeTopFragment(R.drawable.img_default_4_x_1))
        topAdapter.addFragment(HomeTopFragment(R.drawable.img_default_4_x_1))
        topAdapter.addFragment(HomeTopFragment(R.drawable.img_default_4_x_1))
        topAdapter.addFragment(HomeTopFragment(R.drawable.img_default_4_x_1))
        topAdapter.addFragment(HomeTopFragment(R.drawable.img_default_4_x_1))

        binding.homeTopVp.adapter = topAdapter

        TabLayoutMediator(binding.homeTopTabLayout, binding.homeTopVp) { tab, position ->}.attach()



        val bannerAdapter = HomeBannerVpAdapter(this)

        bannerAdapter.addFragment(HomeBannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(HomeBannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(HomeBannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(HomeBannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(HomeBannerFragment(R.drawable.img_home_viewpager_exp))

        binding.homeBannerViewpager.adapter = bannerAdapter



        bannerVp = BannerVp()
        bannerVp.start()
    }

    override fun onStop() {
        super.onStop()
        bannerVp.interrupt()
        //HomeFragment 위에 다른 View가 올라왔을 때 bannerVp(워커스레드) 에 interrupt를 발생시켜서 쓰레드 종료시키기
        Log.d("쓰레드", "HomeFragement onStop ")
    }




//    override fun onDestroy() {
//        //화면이 꺼질 때 호출되는 함수.
//        //HomeFragment 위에 SongActivity가 겹쳐지는 경우에는 호출되지 않는다!! (위에 덮어지는 느낌이여서)
//        super.onDestroy()
//        Log.d("cuttt", "홈 프레크먼트 destroy")
//    }
}