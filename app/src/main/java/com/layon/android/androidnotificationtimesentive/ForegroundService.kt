package com.layon.android.androidnotificationtimesentive

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.*
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.postDelayed


class ForegroundService : Service() {

    private val CHANNEL_ID = "Time-Sentive Notification in Kotlin"
    private val NOTIFICATION_ID = 1
    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null

    //function to start and stop the foregroundservice
    companion object {
        fun startService(context: Context, message: String, playsound: Boolean, vibrate: Boolean,
        delay: Int) {
            //startService with a delay
            Handler(Looper.getMainLooper()).postDelayed({
                val startIntent = Intent(context, ForegroundService::class.java)
                startIntent.putExtra("playsound", playsound)
                startIntent.putExtra("vibrate", vibrate)
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                ContextCompat.startForegroundService(context, startIntent)
                Log.d("layonf", "startService")
            }, delay.toLong()) // 5s
        }
        fun stopService(context: Context, message: String) {
            val stopIntent = Intent(context, ForegroundService::class.java)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            context.stopService(stopIntent)
            Log.d("layonf", "stopService")
        }
    }

    override fun onCreate() {
        super.onCreate()
        //get media player
        mediaPlayer = MediaPlayer.create(this, R.raw.shapeofyou)
        mediaPlayer?.setOnPreparedListener {
            println("PLAY")
        }
        //get vibrator
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    fun playsound(play: Boolean){
        Log.d("layonf", "playsound")
        mediaPlayer?.setLooping(true)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, start: Int): Int {
        Log.d("layonf", "onStartCommand")
        //Do heavy work on a background thread

        //need play sound?
        val playsound = intent?.getBooleanExtra("playsound", false)
        if(playsound!!) {
            Log.d("layonf", "play")
            mediaPlayer?.setLooping(true)
            mediaPlayer?.start()
        }

        //need vibrate?
        val vibrate = intent?.getBooleanExtra("vibrate", false)
        if(vibrate!!) {
            Log.d("layonf", "vibrate")
            
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator?.vibrate(VibrationEffect.createWaveform(
                    longArrayOf(1500L, 800L, 800L), 0))
            } else {
                vibrator?.vibrate(5000)
            }
        }

        //create channel
        createNotificationChannel()

        //return the postNotification to finish
        return postNotification()

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("layonf", "onDestroy")
        //stop the play if need
        mediaPlayer?.stop()
        //stop the vibrate if need
        vibrator?.cancel()
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
        Log.d("layonf", "postNotification")

        //create the fullScreen Intents
        val fullScreenIntent = Intent(this, FullScreenActivity::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
            fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Time-Sentive Notification")
            .setContentText("An example of how to show an Android time-sentive notification in kotlin")
            .setSmallIcon(R.drawable.ic_baseline_new_releases_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .addAction(R.drawable.ic_baseline_phone_enabled_24,
                getActionText("Action1", Color.RED), null)
            .addAction(R.drawable.ic_baseline_phone_disabled_24,
                getActionText("Action2", Color.GREEN), null)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .build()

        startForeground(NOTIFICATION_ID, notification)
        return START_NOT_STICKY
    }

    //this method return a spannable the set background color on action button name
    private fun getActionText(string : String, color : Int): Spannable? {
        val spannable: Spannable = SpannableString(string)
        if (VERSION.SDK_INT >= VERSION_CODES.N_MR1) {
            spannable.setSpan(
                ForegroundColorSpan(color), 0, spannable.length, 0
            )
        }
        return spannable
    }
}