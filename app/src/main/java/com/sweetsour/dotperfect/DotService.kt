package com.sweetsour.dotperfect

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class DotService : Service() {
    private val TAG = "DotService"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //return super.onStartCommand(intent, flags, startId)
        Log.d(TAG, "onStartCommand")

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")

        //put the dot on top of the window hierarchy through window manager.
        val windowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE)
                as WindowManager
        val layoutInflater = baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater

        //inflate the layout
        val dotViewGroup = layoutInflater.inflate(R.layout.dot_layout, null)

        //set version specific layout type
        var layoutType: Int
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            layoutType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        }else{
            layoutType = WindowManager.LayoutParams.TYPE_PHONE // works cool
            //TYPE_SYSTEM_OVERLAY -> touching the dot dont work on any screen, just for showing stuff
            //TYPE_SYSTEM_ALERT, TYPE_PHONE -> touching the dot works on any screen but needs
                //FLAG_NOT_FOCUSABLE for showing the keyboard and back press functionality;
        }

        val dotLayoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutType,
                  //even when this window is focusable (its FLAG_NOT_FOCUSABLE is not set),
                    //allow any pointer events outside of the window to be sent to the
                    //windows behind it. Otherwise it will consume all pointer events itself,
                    //regardless of whether they are inside of the window.
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    //this window won't ever get key input focus, so the user can not send key
                    //or other button events to it. Those will instead go to whatever focusable
                    //window is behind it. This flag will also enable FLAG_NOT_TOUCH_MODAL whether
                    //or not that is explicitly set. Setting this flag also implies that the
                    //window will not need to interact with a soft input method,
                    //so it will be Z-ordered and positioned independently of any active
                    //input method (typically this means it gets Z-ordered on top of the input method,
                    //so it can use the full screen for its content and cover the input method if needed.
                    //You can use FLAG_ALT_FOCUSABLE_IM to modify this behavior.
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        //the dot and status bar doesn't overlap even without this set up true here
        //but just in case. Crazy things may happen.
        dotViewGroup.fitsSystemWindows = true

        //set initial position of the dot.
        dotLayoutParams.gravity = Gravity.TOP or Gravity.END

        //finally, add the layout to the screen
        windowManager.addView(dotViewGroup, dotLayoutParams)

        //dot button
        val dotPerfect: Button = dotViewGroup.findViewById(R.id.dotPerfect)
        dotPerfect.setOnClickListener {
            Log.d(TAG, "dot perfect tap")
        }
        dotPerfect.setBackgroundColor(Color.argb(150, 0, 51, 53))
        //Color.parseColor("#AARRGGBB") also works
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        stopSelf()
    }
}

//this is here for a reminder... If an activity extends AppCompatActivity which is a base class for
//activities that wish to use some of the newer platform features on older Android devices,
//Use "support" methods in AppCompat activities.
//this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE) -> removes title
