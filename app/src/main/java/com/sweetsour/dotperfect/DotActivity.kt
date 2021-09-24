package com.sweetsour.dotperfect

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DotActivity : AppCompatActivity() {
    private val TAG = "DotActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        // They don't work
        //window.requestFeature(Window.FEATURE_NO_TITLE)
        //requestWindowFeature(Window.FEATURE_NO_TITLE)
        //window.setTitle("My title")
        //window.setFeatureInt(Window.FEATURE_NO_TITLE, Window.FEATURE_NO_TITLE)
        //this.supportActionBar?.hide()
        //this.setSupportActionBar(null)

        //this works... Since this activity extends AppCompatActivity which is a base class for
        //activities that wish to use some of the newer platform features on older Android devices,
        //above approaches don't work on it. 
        //Use "support" methods in AppCompat activities.
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

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
            //and outside touches do work but only in the application context.
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
            //WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
            //WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        //window.setType(WindowManager.LayoutParams.TYPE_)
        //Tried for outside touch but they didn't work:
        //TYPE_APPLICATION_ATTACHED_DIALOG
        //TYPE_APPLICATION_OVERLAY
        //TYPE_SYSTEM_DIALOG
        //TYPE_SYSTEM_OVERLAY n/a
        //TYPE_SYSTEM_ALERT n/a
        //TYPE_PHONE n/a
        //TYPE_TOAST n/a

        window.setFormat(PixelFormat.TRANSLUCENT)

        //it works
        window.setGravity(Gravity.START or Gravity.TOP)

/*
        window.setHideOverlayWindows()
        window.setLocalFocus()

        window.container
        window.context
        window.currentFocus
        window.decorView
        window.windowStyle
        window.setUiOptions()
        window.attributes
        */

        setContentView(R.layout.activity_dot)

        //dot button
        val dotPerfect: Button = findViewById(R.id.dotPerfect)
        dotPerfect.setOnClickListener {
            Log.d(TAG, "dot perfect tap")
        }

        //the constraint background is completely transparent,
        //set the dot bg here for ui visibility. Later let the user tweak it by setting color value
        dotPerfect.setBackgroundColor(Color.argb(150, 0, 51, 53))
        //Color.parseColor("#AARRGGBB") also works

    }

    //since FLAG_ACTIVITY_SINGLE_TOP is set in calling intent, while this activity is running,
    //new coming intents pass through this method.
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent")
        if (intent != null && intent.getBooleanExtra("isFinishActivity", false)){
            finish()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(TAG, "dispatchTouchEvent: $ev")
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "onTouchEvent: $event")
        return super.onTouchEvent(event)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
            Log.d(TAG, "has focus? $hasFocus")
            super.onWindowFocusChanged(hasFocus)
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