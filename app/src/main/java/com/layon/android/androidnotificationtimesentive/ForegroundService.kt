package com.layon.android.androidnotificationtimesentive

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class ForegroundService : Service() {

    private val CHANNEL_ID = "Time-Sentive Notification in Kotlin"
    private val NOTIFICATION_ID = 1

    //function to start and stop the foregroundservice
    companion object {
        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, ForegroundService::class.java)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            ContextCompat.startForegroundService(context, startIntent)
        }
        fun stopService(context: Context, message: String) {
            val stopIntent = Intent(context, ForegroundService::class.java)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            context.stopService(stopIntent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, start: Int): Int {
        //Do heavy work on a background thread
        createNotificationChannel()
        return postNotification()

    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Time-sentive Notification",
            NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    private fun postNotification(): Int {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Time-Sentive Notification")
            .setContentText("An example of how to show an Android time-sentive notification in kotlin")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(NOTIFICATION_ID, notification)
        return START_NOT_STICKY
    }
}