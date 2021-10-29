package com.example.flo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Adapter
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    private lateinit var bannerVp : BannerVp



    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

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



        binding.homeTodayMusic01Iv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment())
                .commitAllowingStateLoss()
        }


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

    override fun onStop() {
        //다른 액티비티가 화면을 완전히 가리게 되었을때 호출되는 함수!!
        bannerVp.interrupt()
        //HomeFragment 위에 다른 View가 올라왔을 때 bannerVp(워커스레드) 에 interrupt를 발생시켜서 쓰레드 종료시키기
        super.onStop()
        Log.d("stop", "stop")
    }


//    override fun onDestroy() {
//        //화면이 꺼질 때 호출되는 함수.
//        //HomeFragment 위에 SongActivity가 겹쳐지는 경우에는 호출되지 않는다!! (위에 덮어지는 느낌이여서)
//        super.onDestroy()
//        Log.d("cuttt", "홈 프레크먼트 destroy")
//    }
}