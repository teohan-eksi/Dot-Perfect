package com.sweetsour.dotperfect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class DotActivity : AppCompatActivity() {
    private val TAG = "DotActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dot)

        Log.d(TAG, "onCreate DotActivity")
    }
}