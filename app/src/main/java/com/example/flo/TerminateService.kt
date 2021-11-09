package com.example.flo

import android.app.Service

import android.content.Intent

import android.os.IBinder

class TerminateService : Service() {  //앱 강제종료 했을 때!!
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent) { //핸들링 하는 부분
        stopSelf() //서비스 종료
    }
}
