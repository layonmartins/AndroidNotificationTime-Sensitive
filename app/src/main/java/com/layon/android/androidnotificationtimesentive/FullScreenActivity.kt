package com.layon.android.androidnotificationtimesentive

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_full_screen.*


class FullScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)

        window.decorView.apply{
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        Log.i("layonf", "create incoming call")

        fullscreenactivity.setOnClickListener({
            Log.i("layonf", "touch")
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        })
    }
}