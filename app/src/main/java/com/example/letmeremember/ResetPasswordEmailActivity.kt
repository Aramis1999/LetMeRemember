package com.example.letmeremember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

private lateinit var resetPasswordEmail: TextView
private lateinit var resetPasswordButton: Button
private lateinit var auth: FirebaseAuth
class ResetPasswordEmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password_email)
        resetPasswordEmail = findViewById(R.id.resetPasswordEmail)
        resetPasswordButton = findViewById(R.id.sendEmailButton)
        auth = FirebaseAuth.getInstance()
        resetPasswordButton.setOnClickListener {
            var email = resetPasswordEmail.text.toString()
            if(email.isEmpty()){
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show()
                            var intent = android.content.Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
            }
        }
    }
}