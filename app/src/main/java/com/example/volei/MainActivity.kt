package com.example.volei

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonstart: Button = findViewById(R.id.startpartida)
        val timeA : EditText = findViewById(R.id.timeA)
        val timeB : EditText = findViewById(R.id.timeB)

        buttonstart.setOnClickListener(){
            val intent = Intent(applicationContext,Tela2Activity::class.java)
            val nomeTimeA = timeA.text.toString()
            val nomeTimeB = timeB.text.toString()
            val params1= Bundle()
            val params2= Bundle()
            params1.putString("nA",nomeTimeA)
            params2.putString("nB",nomeTimeB)
            intent.putExtras(params1)
            intent.putExtras(params2)
            startActivity(intent)


        }

    }
}