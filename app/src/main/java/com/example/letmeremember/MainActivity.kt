package com.example.letmeremember

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var logOutButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logOutButton = findViewById(R.id.logOutButton)
        logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut().also {
                val intent = Intent(this@MainActivity, homeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}