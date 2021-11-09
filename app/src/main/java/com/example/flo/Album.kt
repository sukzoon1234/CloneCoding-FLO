package com.example.flo

data class Album(
    val title : String? = "",
    val singer : String? = "",
    val date : String? = "",
    val albumImg : Int? = null,
    val songList : ArrayList<Song>? = null
)
