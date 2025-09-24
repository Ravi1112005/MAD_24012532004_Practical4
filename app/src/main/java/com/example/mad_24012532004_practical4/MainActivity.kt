package com.example.mad_24012532004_practical4

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.TextClock
import android.widget.TextView
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
        textAlarmTime = findViewById<TextClock>(R.id.alarmTime)
        cardListAlarm.visibility = View.GONE

    }

    private fun showTimerDialog (){
        
    }

    private fun sendDialogDataToActivity(hour : Int, ){
        
    }
}