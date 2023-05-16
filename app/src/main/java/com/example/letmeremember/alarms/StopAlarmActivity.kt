package com.example.letmeremember.alarms

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.letmeremember.R
import com.example.letmeremember.activity_menu_grupos
import com.google.firebase.database.*
import kotlin.system.exitProcess


lateinit var stopAlarmButton: Button
lateinit var stopAlarmAndLeaveButton: Button
lateinit var alarmTitle: TextView
private var key: String? = null
private var statusPercent = "0"
class StopAlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_alarm)
        val data=intent.extras
        val name=data?.getString("name")
        key =data?.getString("key")

        stopAlarmButton = findViewById(R.id.stopAlarm)
        alarmTitle = findViewById(R.id.alarmTitle)
        stopAlarmAndLeaveButton = findViewById(R.id.stopAlarmAndLeaveButton)
        alarmTitle.text = getString(R.string.AlarmGroup)+" "+name
        var mp : MediaPlayer = MediaPlayer.create(applicationContext, R.raw.rain_alarm)
        mp.start()
        stopAlarmButton.setOnClickListener {
            mp.stop()

            Log.d("KEY",key.toString())

            /*START
            refGroup.child("groups").child(key.toString())
               .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            statusPercent = dataSnapshot.child("percent").getValue(String::class.java)
                        }

                        Log.d("percentStatus",statusPercent.toString())

                        if(statusPercent.toString() == "0") statusPercent = "60"
                        if(statusPercent.toString() == "60") statusPercent = "100"


                        if(statusPercent != "0") refGroup.child(key.toString()).child("percent").setValue(statusPercent)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        println("Fallo la lectura: " + databaseError.code)
                    }
                })
            //END

             */
            refGroup.child(key.toString()).child("percent").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Verifica si existe algún valor en la ubicación
                    if (dataSnapshot.exists()) {
                        // Obtén el valor deseado utilizando el método getValue() en el DataSnapshot
                        statusPercent = dataSnapshot.getValue(String::class.java).toString()

                        // Hacer algo con el valor obtenido
                        Log.d("percentStatus",statusPercent)

                        when (statusPercent) {
                            "0" -> {
                                statusPercent = "60"
                            }
                            "60" -> {
                                statusPercent = "100"
                            }
                        }


                        if(statusPercent != "0") refGroup.child(key.toString()).child("percent").setValue(statusPercent)

                    } else {
                        println("No se encontró ningún valor en la ubicación.")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Manejar cualquier error que ocurra durante la recuperación del valor
                    println("Error al recuperar el valor: ${databaseError.message}")
                }
            })

            //var porcentStatus = refGroup.child(key.toString()).child("percent").get()


            var i = Intent(this, activity_menu_grupos::class.java)
            startActivity(i)
            finish()
        }

        stopAlarmAndLeaveButton.setOnClickListener {
            mp.stop()
            exitProcess(0)
        }
    }

    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refGroup: DatabaseReference = database.getReference("groups")
    }
}