package com.example.letmeremember.helper

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.letmeremember.alarms.NotificationReceiver
import java.util.*

class Helper {

    fun createAlarm(context: Context,min:Long,cardName:String,requestCode:Int){
        var i = Intent(context, NotificationReceiver::class.java)
        i.putExtra("name",cardName)
        var pi = PendingIntent.getBroadcast(context, requestCode    , i, PendingIntent.FLAG_IMMUTABLE)
        var am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().timeInMillis + (min*60000), pi)
    }

    fun cancelAlarm(context: Context,requestCode:Int){
        var i = Intent(context, NotificationReceiver::class.java)
        var pi = PendingIntent.getBroadcast(context, requestCode, i, PendingIntent.FLAG_IMMUTABLE)
        var am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.cancel(pi)
        pi.cancel()
    }

    fun generateUniqueCode(length: Int): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        val sb = StringBuilder(length)
        val random = Random()

        while (sb.length < length) {
            val index = (random.nextFloat() * allowedChars.length).toInt()
            sb.append(allowedChars[index])
        }

        return sb.toString()
    }

}