package com.example.letmeremember.alarms

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.letmeremember.R

lateinit var stopAlarmButton: Button
class StopAlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_alarm)
        stopAlarmButton = findViewById(R.id.stopAlarm)
        var mp : MediaPlayer = MediaPlayer.create(applicationContext, R.raw.rain_alarm)
        mp.start()
        stopAlarmButton.setOnClickListener {
            mp.stop()
            finish()
        }
    }
}