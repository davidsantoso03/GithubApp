package com.hfad.githubapp.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.hfad.githubapp.R
import com.hfad.githubapp.ui.MainActivity
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object{
        const val TYPE_REPEATING = "RepeatingAlarm"
        const val EXTRA_MESSAGE  ="message"
        const val EXTRA_TYPE = "type"

        private const val  ID_REPEATING = 100
        private const val  TIME_FORMAT = "09:00"
    }


    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE)
        val msg =intent.getStringExtra(EXTRA_MESSAGE)
            if (msg != null) {
                showReminderNotification(context,msg)
            }


    }

    fun setRepeatingReminder(context: Context,type:String,msg: String){
        val alarmManager  = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(EXTRA_MESSAGE, msg)
        intent.putExtra(EXTRA_TYPE,type)

        val timeArray = TIME_FORMAT.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND,0)

        val pendingIntent  = PendingIntent.getBroadcast(context, ID_REPEATING,intent,0)
        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent)

        Toast.makeText(context, "Daily Reminder Is ON", Toast.LENGTH_SHORT).show()
    }

    fun destroyAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlarmReceiver::class.java)
        val reqCode = ID_REPEATING
        val pendingIntent = PendingIntent.getBroadcast(context, reqCode,intent,0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context,"Daily reminder is OFF",Toast.LENGTH_SHORT).show()
    }

    private fun showReminderNotification(context: Context, msg: String?) {
        val channel_id = "channel_3"
        val channel_name = "alrmManager"

        val intent = Intent(context,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_ONE_SHOT)
        val notificationManagerCompat = context.getSystemService((Context.NOTIFICATION_SERVICE)) as  NotificationManager


        val builder = NotificationCompat.Builder(context,channel_id)
            .setSmallIcon(R.drawable.ic_baseline_alarm_black_24dp)
                .setContentTitle("Github Daily Reminder")
                .setContentText(msg)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
             val channelNotif = NotificationChannel(channel_id, channel_name,NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(channel_id)
            notificationManagerCompat.createNotificationChannel(channelNotif)

        }
        val notification  =builder.build()
        notificationManagerCompat.notify(100,notification)
 


    }


}