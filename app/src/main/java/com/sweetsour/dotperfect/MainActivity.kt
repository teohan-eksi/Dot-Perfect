package com.sweetsour.dotperfect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val showDotSwitch: SwitchCompat = findViewById(R.id.showDotSwitch)

        showDotSwitch.setOnCheckedChangeListener{_ , isChecked ->
            if(isChecked){
                Log.d("MainActivity", "showDotSwitch is checked")
            }else{
                Log.d("MainActivity", "showDotSwitch is not checked")
            }
        }
    }
}