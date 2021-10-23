package com.example.flo

import android.os.Bundle
import android.view.*
import android.widget.Adapter
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

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

        TabLayoutMediator(binding.homeTopTabLayout, binding.homeTopVp) { tab, position ->

        }.attach()






        val bannerAdapter = HomeBannerVpAdapter(this)
        bannerAdapter.addFragment(HomeBannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(HomeBannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(HomeBannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(HomeBannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(HomeBannerFragment(R.drawable.img_home_viewpager_exp))

        binding.homeBannerViewpager.adapter = bannerAdapter




        binding.homeTodayMusic01Iv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment())
                .commitAllowingStateLoss()
        }

        return binding.root
    }

}