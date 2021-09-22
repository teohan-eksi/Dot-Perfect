package com.sweetsour.dotperfect

import android.content.Context
import android.graphics.PixelFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager

class DotActivity : AppCompatActivity() {
    private val TAG = "DotActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate DotActivity")

        // They don't work
        //window.requestFeature(Window.FEATURE_NO_TITLE)
        //requestWindowFeature(Window.FEATURE_NO_TITLE)
        //window.setTitle("My title")
        //window.setFeatureInt(Window.FEATURE_NO_TITLE, Window.FEATURE_NO_TITLE)
        //this.supportActionBar?.hide()
        //this.setSupportActionBar(null)

        //it works
        //title = ""
        //window.setUiOptions()


        //window flags
        //FLAG_NOT_TOUCH_MODAL -> Window flag: even when this window is focusable
        //  (its FLAG_NOT_FOCUSABLE is not set), allow any pointer events
        //  outside of the window to be sent to the windows behind it.
        //FLAG_SECURE -> Window flag: treat the content of the window as secure,
        //  preventing it from appearing in screenshots or from being viewed
        //  on non-secure displays.
        //FLAG_SHOW_WALLPAPER -> Window flag: ask that the system wallpaper
        //  be shown behind your window.
        //FLAG_WATCH_OUTSIDE_TOUCH -> Window flag: if you have set FLAG_NOT_TOUCH_MODAL,
        //  you can set this flag to receive a single special MotionEvent with
        //  the action MotionEvent.ACTION_OUTSIDE for touches that occur outside of your window.
        window.addFlags(
            //when this flag is added, the dot doesn't close on outside touches.
            //and outside touches do actually work.
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        )
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        setContentView(R.layout.activity_dot)
    }


    //override back press to disable closing the dot
    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "onBackPressed")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}