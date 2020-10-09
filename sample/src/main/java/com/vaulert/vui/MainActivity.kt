package com.vaulert.vui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 导致报红 implementation project(':vui')
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        countDownButton.setOnClickListener {
            countDownButton.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownButton.removeRunnable()
    }
}