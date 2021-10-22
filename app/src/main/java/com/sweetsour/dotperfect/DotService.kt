package com.sweetsour.dotperfect

import android.annotation.SuppressLint
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
import android.app.Notification
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginRight
import androidx.core.view.marginStart

class DotService : Service() {
    private val TAG = "DotService"
    private lateinit var windowManager: WindowManager
    private lateinit var dotViewGroup: ViewGroup
    private var isRunning = false //to check if the service is running.

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        isRunning = true

        /*//intent can be null if another compound didn't start the service
        if(intent != null){
            letsDestroy = intent.getBooleanExtra("letsDestroy", false)
            Log.d(TAG, "letsDestroy: $letsDestroy")
            if(letsDestroy) {
                //dont create another instance of service and stop the current one
                stopSelf()
                return super.onStartCommand(intent, flags, startId)
            }else{
                Log.d(TAG, "onStartCommand returns START_STICKY")
                return START_STICKY
            }
        }else{
            Log.d(TAG, "intent is null, call super method")
            return super.onStartCommand(intent, flags, startId)
        }*/

        //It's advised to do initial UI operations and showing notifications here.
        showNotification()
        showDotOnScreen()

        //if the service gets killed by the OS, this will restart it.
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun showDotOnScreen(){
        //put the dot on top of the window hierarchy through window manager.
        windowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE)
                as WindowManager
        val layoutInflater = baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater

        //inflate the layout
        dotViewGroup = layoutInflater.inflate(R.layout.dot_layout, null) as ViewGroup

        //set version specific layout type. It's important for compatibility and function.
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
            //allow any pointer events OUTSIDE of the window to be sent to the
            //windows behind it. Otherwise it will consume all pointer events itself,
            //regardless of whether they are inside of the window.
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    //this window won't ever get key input focus, so the user can NOT send key
                    //or other button events to it. Those will instead go to whatever focusable
                    //window is BEHIND it. This flag will also enable FLAG_NOT_TOUCH_MODAL whether
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


        //finally, add the layout to the screen
        windowManager.addView(dotViewGroup, dotLayoutParams)
        Log.d(TAG, "dotViewGroup added")

        //setup dot funcionality
        setupDot(dotLayoutParams)

    }


    private fun showNotification() {
        /*// The PendingIntent to launch our activity if the user selects this notification
        val contentIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, LocalServiceActivities.Controller::class.java), 0
        )*/

        //TODO: Builder is deprecated, implement compatibility for higher versions.
        //Set the info for the views that show in the notification panel.
        val notification: Notification = Notification.Builder(this)
            .setSmallIcon(R.color.teal_200) // the status icon
            .setTicker("Dot is Shown.") // the status text
            .setWhen(System.currentTimeMillis()) // the time stamp
            .setContentTitle("Dot Perfect") // the label of the entry
            .setContentText("") // the contents of the entry
            //.setContentIntent(contentIntent) // The intent to send when the entry is clicked
            .build()

        notification.flags = Notification.FLAG_FOREGROUND_SERVICE or
                //clear all function doesn't clear the notification.
                Notification.FLAG_NO_CLEAR

        //deprecated in 26, implement NotificationChannel#getImportance() for that sdks
        notification.priority = Notification.PRIORITY_MIN //show only on status bar spinner thingy.

        startForeground(101, notification)

        /* // Send the notification.
        val mNM: NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNM.notify(101, notification)*/
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupDot(lp: WindowManager.LayoutParams){

        var isUpdate = true
        val dotPerfect: Button = dotViewGroup.findViewById(R.id.dotPerfect)
        var previousX: Int = 0
        var previousY: Int = 0

        dotPerfect.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, e: MotionEvent?): Boolean {
                when(e?.action){
                    MotionEvent.ACTION_DOWN -> {
                        Log.d(TAG, "action down")
                        Log.d(TAG,"event (x, y): ${e.x}, ${e.y}")
                        Log.d(TAG,"dotViewGroup (x, y): ${lp.x}, ${lp.y}")

                        previousX = e.x.toInt()
                        previousY = e.y.toInt()

                        /*if(e.x >= DOT_HE && e.y <= DOT_HE){
                            lp.x += e.x.toInt()
                            lp.y -= e.y.toInt()
                        }else if(e.x <= DOT_HE && e.y <= DOT_HE){
                            lp.x -= e.x.toInt()
                            lp.y -= e.y.toInt()
                        }else if(e.x <= DOT_HE && e.y >= DOT_HE){
                            lp.x -= e.x.toInt()
                            lp.y += e.y.toInt()
                        }else{
                            lp.x += e.x.toInt()
                            lp.y += e.y.toInt()
                        }
                        windowManager.updateViewLayout(dotViewGroup, lp)*/
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if(isUpdate) {
                            /*Log.d(TAG,"event (x, y): ${e.x}, ${e.y}")
                            Log.d(TAG,"dot (x, y): ${lp.x}, ${lp.y}")
                            lp.x = e.x.toInt()
                            lp.y = e.y.toInt()
                            windowManager.updateViewLayout(dotViewGroup, lp)
                            Log.d(TAG, "moved dot (x, y): ${lp.x}, ${lp.y}")
                            isUpdate = false*/
                        }
                        Log.d(TAG,"event (x, y): ${e.x}, ${e.y}")
                        Log.d(TAG, "previous (x,y): ${previousX}, ${previousY}")
                        Log.d(TAG,"dotViewGroup (x, y): ${lp.x}, ${lp.y}")

                        lp.x += e.x.toInt() - previousX
                        lp.y += e.y.toInt() - previousY
                        Log.d(TAG, "change vector: ${lp.x}, ${lp.y}")

                        previousX = e.x.toInt()
                        previousY = e.y.toInt()

                        windowManager.updateViewLayout(dotViewGroup, lp)
                    }
                    MotionEvent.ACTION_UP -> {
                    }
                }

                v?.performClick()

                return true
            }
        })

        dotPerfect.setBackgroundColor(Color.argb(200, 0, 51, 53))
        //Color.parseColor("#AARRGGBB") also works
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        /*if(letsDestroy){
            Log.d(TAG, "super.onDestroy and stopSelf will be called.")
            //remove view
            windowManager.removeView(dotViewGroup)
            Log.d(TAG, "view removed")
            super.onDestroy()
            stopSelf()
        }else{
            Log.d(TAG, "onDestroy was called but silenced.")
        }*/
        super.onDestroy()
        //stopSelf()
    }

    //clearing the recents window won't have any effect to the service
    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.d(TAG, "onTaskRemoved, isRunning: $isRunning")
        if(isRunning){
            //do nothing and dont call the super method
        }else{
            super.onTaskRemoved(rootIntent)
        }
    }
}

//this is here for a reminder... If an activity extends AppCompatActivity which is a base class for
//activities that wish to use some of the newer platform features on older Android devices,
//Use "support" methods in AppCompat activities.
//this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE) -> removes title
