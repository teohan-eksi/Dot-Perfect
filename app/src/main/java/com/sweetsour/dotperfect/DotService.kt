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

        val dotViewGroup = layoutInflater.inflate(R.layout.dot_layout, null)

        var layoutType: Int
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            layoutType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        }else{
            layoutType = WindowManager.LayoutParams.TYPE_PHONE
            //TYPE_SYSTEM_OVERLAY -> touching the dot dont work on any screen, just for showing stuff
            //TYPE_SYSTEM_ALERT, TYPE_PHONE -> touching the dot works on any screen but needs
                //FLAG_NOT_FOCUSABLE for showing the keyboard and back press functionality
                //because if it has the focus, they dont work automatically.
        }

        val dotLayoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutType,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        //dotLayoutParams.gravity = Gravity.CENTER
        dotLayoutParams.x = 0
        dotLayoutParams.y = 0

        windowManager.addView(dotViewGroup, dotLayoutParams)

        //dot button
        val dotPerfect: Button = dotViewGroup.findViewById(R.id.dotPerfect)
        dotPerfect.setOnClickListener {
            Log.d(TAG, "dot perfect tap")
        }
        dotPerfect.setBackgroundColor(Color.argb(150, 0, 51, 53))
        //Color.parseColor("#AARRGGBB") also works

        /*
        dotIntent.flags =
            //If a task is already running for the activity you are now starting,
            //  that task is brought to the foreground with its last state restored
            //  and the activity receives the new intent in onNewIntent()
            Intent.FLAG_ACTIVITY_NEW_TASK or
            //the activity will not be launched if it is already running at the top of the stack.
            //  if an intent that launched an activity resend again, that intent will be delivered
            //  to the current instance of the activity's onNewIntent()
            Intent.FLAG_ACTIVITY_SINGLE_TOP
            //FLAG_ACTIVITY_CLEAR_TOP -> If the activity being started is already running in the
            //  current task, then instead of launching a new instance of that activity,
            //  all of the other activities on top of it are destroyed and this intent is
            //  delivered to the resumed instance of the activity (now on top), through onNewIntent().
            //FLAG_ACTIVITY_NO_HISTORY -> the new activity is not kept in the history stack.
            //  As soon as the user navigates away from it, the activity is finished.
        dotIntent.putExtra("isFinishActivity", false)
        */

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        stopSelf()
    }
}

//this works... Since this activity extends AppCompatActivity which is a base class for
//activities that wish to use some of the newer platform features on older Android devices,
//above approaches don't work on it.
//Use "support" methods in AppCompat activities.
//this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

//window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
