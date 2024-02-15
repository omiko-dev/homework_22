package com.example.homework_22.presentation.service

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.homework_22.R
import com.example.homework_22.presentation.screen.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val id = message.data["id"] ?: ""
        val desc = message.data["desc"] ?: ""
        val title = message.data["title"] ?: ""

        if(message.notification?.channelId == ChannelId.POST_CREATE_CHANNEL.id){
            postCreatePushNotification(title = title, desc = desc, id = id)
        }else{
            postUpdatePushNotification(title = title, desc = desc, id = id)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    private fun postCreatePushNotification(title: String, desc: String, id: String) {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            action = "OPEN_FRAGMENT"
            putExtra("POST_ID", id)
        }

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, ChannelId.POST_CREATE_CHANNEL.id)
            .setSmallIcon(R.drawable.ic_bell)
            .setContentTitle(title)
            .setContentText(desc)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(applicationContext).notify(1, notification)
        }
    }

    private fun postUpdatePushNotification(title: String, desc: String, id: String) {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            action = "OPEN_FRAGMENT"
            putExtra("POST_ID", id)
        }

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, ChannelId.POST_UPDATE_CHANNEL.id)
            .setSmallIcon(R.drawable.ic_bell)
            .setContentTitle(title)
            .setContentText(desc)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(applicationContext)
                .notify(2, notification)
        }
    }

    enum class ChannelId(val id: String){
        POST_UPDATE_CHANNEL("post_update_channel"),
        POST_CREATE_CHANNEL("post_create_channel")
    }
}