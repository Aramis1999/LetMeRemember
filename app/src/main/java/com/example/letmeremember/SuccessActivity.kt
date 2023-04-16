package com.example.letmeremember

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SuccessActivity : AppCompatActivity() {
    private lateinit var successButton: TextView
    private lateinit var successText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        val intent = intent
        successText = findViewById(R.id.tvSuccess)
        if(intent.getStringExtra("previousActivity") == "SignUpActivity"){
            successText.text = resources.getString(R.string.successMessage)
        }else{
            successText.text = resources.getString(R.string.successResetMessage)
        }
        successButton = findViewById(R.id.successButton)
        val string = resources.getString(R.string.successButton)
        val mSpannableString = SpannableString(string)
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        successButton.text = mSpannableString

        successButton.setOnClickListener {
            var intent = android.content.Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}