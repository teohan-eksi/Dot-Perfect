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

        val serviceIntent = Intent(applicationContext, DotService::class.java)

        val showDotSwitch: SwitchCompat = findViewById(R.id.showDotSwitch)
        showDotSwitch.setOnCheckedChangeListener{_ , isChecked ->
            if(isChecked){
                Log.d("MainActivity", "showDotSwitch is checked")
                //serviceIntent.putExtra("letsDestroy", false)
                startService(serviceIntent)
            }else{
                Log.d("MainActivity", "showDotSwitch is not checked")
                //serviceIntent.putExtra("letsDestroy", true)
                stopService(serviceIntent)
            }
        }
    }
}