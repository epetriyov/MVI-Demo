package com.connect.android.client.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.connect.android.client.ConnectActivity
import com.connect.android.client.Constants.EXTRA_USER_ID
import com.connect.android.client.R
import com.connect.android.client.model.auth.AuthRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.android.ext.android.inject

class ConnectFirebaseService : FirebaseMessagingService() {

    val authRepo: AuthRepository by inject()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val intent = Intent(this, ConnectActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(EXTRA_USER_ID, remoteMessage.data["userId"])
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, "message_channel")
            .setContentTitle("Message")
            .setContentText(remoteMessage.data["message"])
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        authRepo.pushNotificationToken(token)
    }
}