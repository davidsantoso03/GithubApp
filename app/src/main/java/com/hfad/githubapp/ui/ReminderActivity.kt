package com.hfad.githubapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.hfad.githubapp.R
import com.hfad.githubapp.alarm.AlarmReceiver

class ReminderActivity : AppCompatActivity() {

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mSharedPreferences: SharedPreferences

    companion object{

        const val NAME_PREFS = "SettingPrefs"

        private const val REPEATING_DAILY = "RepeatingDaily"

    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_reminder)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "Settings"

        alarmReceiver = AlarmReceiver()

        mSharedPreferences = getSharedPreferences(NAME_PREFS,Context.MODE_PRIVATE)

        val sw1 = findViewById<Switch>(R.id.switch1)

        sw1.isChecked = mSharedPreferences.getBoolean(REPEATING_DAILY,false)

        sw1.setOnCheckedChangeListener { _, isChecked ->

           if (isChecked) alarmReceiver.setRepeatingReminder(this, AlarmReceiver.TYPE_REPEATING,getString(R.string.reminder_msg)) else
           {
               alarmReceiver.destroyAlarm(this)
           }
            setSharedPreference(isChecked)

        }


    }


    private fun setSharedPreference(value: Boolean) {

        val editor = mSharedPreferences.edit()

        editor.putBoolean(REPEATING_DAILY,value)

        editor.apply()

    }


}