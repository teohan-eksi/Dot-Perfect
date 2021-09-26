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

        //this works... Since this activity extends AppCompatActivity which is a base class for
        //activities that wish to use some of the newer platform features on older Android devices,
        //above approaches don't work on it. 
        //Use "support" methods in AppCompat activities.
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        window.addFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        )
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
        }else{
            //window.setType(WindowManager.LayoutParams.TYPE_PHONE)
            window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)
        }

        window.setFormat(PixelFormat.TRANSLUCENT)

        //it works
        window.setGravity(Gravity.START or Gravity.CENTER)

        setContentView(R.layout.activity_dot)

        //dot button
        val dotPerfect: Button = findViewById(R.id.dotPerfect)
        dotPerfect.setOnClickListener {
            Log.d(TAG, "dot perfect tap")
        }


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