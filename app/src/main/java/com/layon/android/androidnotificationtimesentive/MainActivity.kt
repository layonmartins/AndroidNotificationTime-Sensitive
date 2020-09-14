package com.layon.android.androidnotificationtimesentive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //enabled the marquee
        textView.setSelected(true)

        buttonPost.setOnClickListener(View.OnClickListener {
            //need play sound?
            var playsound = if(checkBox_play_sound.isChecked) true else false

            //need vibrate?
            var vibrate = if(checkBox_vibrate.isChecked) true else false

            //need vibrate?
            var delay = if(checkBox_delay.isChecked) 5000 else 0

            ForegroundService.startService(this, "Foreground Service is running...",
            playsound, vibrate, delay)
        })
        buttonCancel.setOnClickListener(View.OnClickListener {
            ForegroundService.stopService(this, "Foreground Service is stopping...")
        })
    }
}