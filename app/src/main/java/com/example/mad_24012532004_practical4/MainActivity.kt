package com.example.mad_24012532004_practical4

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    lateinit var cardListAlarm: MaterialCardView
    lateinit var btnCreateAlarm: MaterialButton
    lateinit var btnCancelAlarm: MaterialButton
    lateinit var textAlarmTime: TextView

    private var calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cardListAlarm = findViewById<MaterialCardView>(R.id.card2)
        btnCreateAlarm = findViewById<MaterialButton>(R.id.create_button)
        btnCancelAlarm = findViewById<MaterialButton>(R.id.cancel_button)
        textAlarmTime = findViewById<TextView>(R.id.alarmTime)
        cardListAlarm.visibility = View.GONE

        btnCreateAlarm.setOnClickListener {
            showTimerDialog()
        }
        btnCancelAlarm.setOnClickListener {
            val stopServiceIntent = Intent(this, AlarmService::class.java)
            stopService(stopServiceIntent)

            val alarmIntent = Intent(this, AlarmBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 234324243, alarmIntent, PendingIntent.FLAG_IMMUTABLE)
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)

            Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show()
            cardListAlarm.visibility = View.GONE
        }
    }

    private fun showTimerDialog (){
        val mTimePicker: TimePickerDialog
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val picker = TimePickerDialog(
            this,
            {tp,sHour,sMinute-> sendDialogDataToActivity(sHour,sMinute)}
            ,hour,minute,false
        )
        picker.show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun sendDialogDataToActivity(hour : Int, minute : Int){
        val alarmCalendar = Calendar.getInstance()
        val year: Int = alarmCalendar.get(Calendar.YEAR)
        val month: Int = alarmCalendar.get(Calendar.MONTH)
        val day: Int = alarmCalendar.get(Calendar.DATE)
        alarmCalendar.set(year,month,day,hour,minute,0)
        textAlarmTime.text = SimpleDateFormat("hh:mm ss a").format(alarmCalendar.time)
        cardListAlarm.visibility = View.VISIBLE
        setAlarm(alarmCalendar.timeInMillis,"Start")
    }


    private fun setAlarm (millisTime : Long, action : String){
        val intent = Intent(this, AlarmBroadcastReceiver::class.java)
        intent.putExtra("Service1", action)

        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 234324243, intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        if (action == "Start"){
            if (alarmManager.canScheduleExactAlarms()){
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    millisTime,
                    pendingIntent
                )
                Toast.makeText(this,"Start Alarm", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))

            }
        }

    }
}