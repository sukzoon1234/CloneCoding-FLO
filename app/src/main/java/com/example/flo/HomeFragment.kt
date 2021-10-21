package com.example.flo

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


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