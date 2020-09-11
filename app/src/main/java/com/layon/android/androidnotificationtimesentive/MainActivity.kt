package com.layon.android.androidnotificationtimesentive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.setSelected(true)

        buttonPost.setOnClickListener(View.OnClickListener {
            ForegroundService.startService(this, "Foreground Service is running...")
        })
        buttonCancel.setOnClickListener(View.OnClickListener {
            ForegroundService.stopService(this, "Foreground Service is stopping...")
        })
    }
}