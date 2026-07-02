package com.example.ucbposb

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Change Status Bar Color
        @Suppress("DEPRECATION")
        window.statusBarColor = ContextCompat.getColor(this, R.color.ucb_red)


        @Suppress("DEPRECATION")
        window.navigationBarColor = ContextCompat.getColor(this, R.color.ucb_red)

        setContentView(R.layout.activity_main)
    }
}