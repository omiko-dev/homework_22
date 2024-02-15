package com.example.homework_22

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        createChannel()
    }

    private fun createChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val postUpdateChannel =  NotificationChannel(
                "post_update_channel",
                "post update channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val postCreateChannel = NotificationChannel(
                "post_create_channel",
                "post update channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannels(listOf(postCreateChannel, postUpdateChannel))
        } else {
            Log.i("omiko", "error")
        }
    }
}