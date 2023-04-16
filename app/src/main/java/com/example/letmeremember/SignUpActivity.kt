package com.example.letmeremember

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.letmeremember.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var userInput: EditText
private lateinit var nameInput: EditText
private lateinit var e_mailInput: EditText
private lateinit var password_Input: EditText
private lateinit var repeatPasswordInput: EditText
private lateinit var signUpCheckBox: CheckBox
private lateinit var buttonRegister: Button
private lateinit var auth: FirebaseAuth
class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        userInput = findViewById(R.id.signupUser)
        nameInput = findViewById(R.id.signupName)
        e_mailInput = findViewById(R.id.SignupEmail)
        password_Input = findViewById(R.id.signupPassword)
        repeatPasswordInput = findViewById(R.id.signupRepeatPassword)
        signUpCheckBox = findViewById(R.id.signupCheckbox)
        buttonRegister = findViewById(R.id.signupButton)

        buttonRegister.setOnClickListener {
            var user = userInput.text.toString()
            var name = nameInput.text.toString()
            var email = e_mailInput.text.toString()
            var password = password_Input.text.toString()
            var repeatPassword = repeatPasswordInput.text.toString()
            if (user.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != repeatPassword) {
                Log.i("tag", password)
                Log.i("tag", repeatPassword)
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!signUpCheckBox.isChecked) {
                Toast.makeText(this, "Please accept the terms and conditions", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // create user with email and password in firebase and upload the rest of the data to firestore
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("tag", "createUserWithEmail:success")
                        val newUser = User(user, name, email)
                        refUsers.child(auth.currentUser!!.uid).child("user").setValue(newUser)
                        val user = auth.currentUser
                        val intent = Intent(this, SuccessActivity::class.java)
                        intent.putExtra("user", user)
                        intent.putExtra("previousActivity", "SignUpActivity")
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("tag", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refUsers: DatabaseReference = database.getReference("users")
    }
}