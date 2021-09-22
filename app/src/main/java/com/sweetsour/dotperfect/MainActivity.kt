package com.sweetsour.dotperfect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val showDotSwitch: SwitchCompat = findViewById(R.id.showDotSwitch)
        showDotSwitch.setOnCheckedChangeListener{_ , isChecked ->
            if(isChecked){
                Log.d("MainActivity", "showDotSwitch is checked")
                //create an intent to start the service
                val startServiceIntent = Intent(applicationContext, DotService::class.java )
                startService(startServiceIntent)
            }else{
                Log.d("MainActivity", "showDotSwitch is not checked")
                //stopSelf()
                stopService(Intent(applicationContext, DotService::class.java))
            }
        }
    }
}