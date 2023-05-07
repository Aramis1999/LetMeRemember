package com.example.letmeremember.helper

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.letmeremember.alarms.NotificationReceiver
import java.util.*

class Helper {

    fun createAlarm(context: Context,time:Long){
        var i = Intent(context, NotificationReceiver::class.java)
        var pi = PendingIntent.getBroadcast(context, 111    , i, PendingIntent.FLAG_IMMUTABLE)
        var am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().timeInMillis + time, pi)
    }

}