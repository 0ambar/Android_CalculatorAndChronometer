package com.example.calculator

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity

class Chronometer : AppCompatActivity() {

    private lateinit var chronometer: Chronometer
    private var running = false
    private var pauseOffset: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chronometer)

        chronometer = findViewById(R.id.chronometer)
        val btnStart: Button = findViewById(R.id.btn_start)
        val btnPause: Button = findViewById(R.id.btn_pause)
        val btnReset: Button = findViewById(R.id.btn_reset)
        val btnReturn: Button = findViewById(R.id.btn_return)

        btnStart.setOnClickListener {
            if (!running) {
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
                chronometer.start()
                running = true
            }
        }

        btnPause.setOnClickListener {
            if (running) {
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
                chronometer.stop()
                running = false
            }
        }

        btnReset.setOnClickListener {
            chronometer.base = SystemClock.elapsedRealtime()
            pauseOffset = 0
            chronometer.stop()
            running = false
        }

        btnReturn.setOnClickListener {
            finish()
        }
    }
}
