package com.layon.android.androidnotificationtimesentive

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class FullScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)

        window.decorView.apply{
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        //hide the navigation bar
        //val decorView = window.decorView
        //val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        //| View.SYSTEM_UI_FLAG_FULLSCREEN;
        //| View.SYSTEM_UI_FLAG_FULLSCREEN;
        //decorView.systemUiVisibility = uiOptions

        //change statusbar color
        //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //window.statusBarColor = Color.WHITE
        //}

        Log.i("layonf", "create incoming call")
    }
}