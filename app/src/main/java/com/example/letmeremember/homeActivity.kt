package com.example.letmeremember

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

lateinit var loginButton:Button;
lateinit var signUpButton:Button;
private lateinit var auth: FirebaseAuth;
private lateinit var authStateListener: FirebaseAuth.AuthStateListener

class homeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        auth= FirebaseAuth.getInstance()
        loginButton=findViewById(R.id.loginButtonHome)
        signUpButton=findViewById(R.id.signupHomeButton)

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        this.checkUser()
    }

    override fun onResume() {
        super.onResume()
        if (::authStateListener.isInitialized) {
            auth.addAuthStateListener(authStateListener)
        }
    }

    override fun onPause() {
        super.onPause()
        if (::authStateListener.isInitialized) {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    private fun checkUser() {
        val user = auth.currentUser
        if (user != null) {
            val intent = Intent(this, activity_menu_grupos::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
            finish()
        }
    }
}