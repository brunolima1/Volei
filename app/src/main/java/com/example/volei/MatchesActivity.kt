package com.example.volei

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MatchesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches)

        val buttonBack: Button = findViewById(R.id.backToMain)

        buttonBack.setOnClickListener {
            val goToMain = Intent(applicationContext, MainActivity::class.java)

            startActivity(goToMain)
        }
    }
}