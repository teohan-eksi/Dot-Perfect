package com.sweetsour.dotperfect

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log

class DotService : Service() {
    private val TAG = "DotService"
    private lateinit var dotIntent: Intent

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
        Log.d(TAG, "DotService onCreate")

        dotIntent = Intent(applicationContext, DotActivity::class.java)
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

        startActivity(dotIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "DotService onDestroy")
        //the switch is off, so the service will be closed and at the same time you should close
        //the dot activity.
        dotIntent.putExtra("isFinishActivity", true)
        startActivity(dotIntent)
        stopSelf()
    }
/*
    private fun createNotification(): Notification {
        val notificationChannelId = "myNotif"
        // depending on the Android API that we're dealing with we will have
        // to use a specific method to create the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                    NotificationManager
            val channel = NotificationChannel(
                notificationChannelId,
                "Endless Service notifications channel",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = "Endless Service channel"
                it.enableLights(true)
                it.lightColor = Color.RED
                it.enableVibration(true)
                it.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent: PendingIntent = Intent(this, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, 0)
        }
    }
 */

}