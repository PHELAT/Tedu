package com.phelat.tedu.analytics

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class TeduFCMService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        when (message.data[TYPE]) {
            TYPE_NOTIFICATION -> handleTypeNotification(message)
        }
    }

    private fun handleTypeNotification(remoteMessage: RemoteMessage) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            `package` = packageName
            data = getString(R.string.deeplink_start).toUri()
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = remoteMessage.notification?.channelId ?: return
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(remoteMessage.notification?.title ?: "")
            .setContentText(remoteMessage.notification?.body ?: "")
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.notificationChannels.find { channel -> channel.id == channelId }
                ?: run {
                    val channel = NotificationChannel(
                        channelId,
                        remoteMessage.data[TYPE_NOTIFICATION_CHANNEL_ID],
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    notificationManager.createNotificationChannel(channel)
                }
        }

        val currentTime = System.currentTimeMillis()
        val notificationId = (currentTime xor currentTime ushr LONG_USHR).toInt()

        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    companion object {
        private const val TYPE = "type"
        private const val TYPE_NOTIFICATION = "type_notification"
        private const val TYPE_NOTIFICATION_CHANNEL_ID = "type_notification_channel_id"
        private const val LONG_USHR = 32
    }
}