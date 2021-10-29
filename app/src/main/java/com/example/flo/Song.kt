package com.example.flo

data class Song(
    var title : String = "",
    var singer : String = "",
    var totalTime : Int = 0,
    var currentTime : Int = 0,
    var isPlaying : Boolean = false
)
