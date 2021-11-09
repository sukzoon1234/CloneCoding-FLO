package com.example.flo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding

class HomeTodayMusicRvAdapter(private val albumList : ArrayList<Album>) : RecyclerView.Adapter<HomeTodayMusicRvAdapter.ViewHolder>(){

    interface MyItemClickListener {
        fun onItemClick(album: Album)
        fun onPlayClick(album: Album)
    }

    //리스너 객체 참조를 저장하는 변수
    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener (itemClickListener: MyItemClickListener) {
        this.mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HomeTodayMusicRvAdapter.ViewHolder {
        val binding : ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeTodayMusicRvAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])
    }

    override fun getItemCount(): Int = albumList.size


    inner class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.homeTodayMusicAlbumLayout.setOnClickListener {
                mItemClickListener.onItemClick(albumList[adapterPosition])
                Log.d("앨범 init  ", "${adapterPosition}")
            }
            binding.homeTodayMusicPlayIv.setOnClickListener {
                mItemClickListener.onPlayClick(albumList[adapterPosition])
            }
        }
        fun bind (album: Album) {
            Log.d("앨범 bind  ", "${adapterPosition}")
            binding.homeTodayMusicTitleTv.text = album.title
            binding.homeTodayMusicSingerTv.text = album.singer
            binding.homeTodayMusicAlbumIv.setImageResource(album.albumImg!!)
        }
    }
}






































