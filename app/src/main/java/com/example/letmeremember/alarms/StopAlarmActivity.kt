package com.example.letmeremember.alarms

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.letmeremember.R
import com.example.letmeremember.activity_menu_grupos
import kotlin.system.exitProcess

lateinit var stopAlarmButton: Button
lateinit var stopAlarmAndLeaveButton: Button
lateinit var alarmTitle: TextView
class StopAlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_alarm)
        val data=intent.extras
        val name=data?.getString("name")
        stopAlarmButton = findViewById(R.id.stopAlarm)
        alarmTitle = findViewById(R.id.alarmTitle)
        stopAlarmAndLeaveButton = findViewById(R.id.stopAlarmAndLeaveButton)
        alarmTitle.text = getString(R.string.AlarmGroup)+" "+name
        var mp : MediaPlayer = MediaPlayer.create(applicationContext, R.raw.rain_alarm)
        mp.start()
        stopAlarmButton.setOnClickListener {
            mp.stop()
            var i = Intent(this, activity_menu_grupos::class.java)
            startActivity(i)
            finish()
        }

        stopAlarmAndLeaveButton.setOnClickListener {
            mp.stop()
            exitProcess(0)
        }
    }
}