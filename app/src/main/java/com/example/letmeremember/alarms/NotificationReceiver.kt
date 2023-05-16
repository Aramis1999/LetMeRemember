package com.example.letmeremember.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import com.example.letmeremember.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var i = Intent(context, StopAlarmActivity::class.java)
        i.putExtra("name",intent.getStringExtra("name"))
        i.putExtra("key",intent.getStringExtra("key"))
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }
}
