package com.example.volei

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.volei.model.Partida
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonstart: Button = findViewById(R.id.startpartida)
        val timeA : EditText = findViewById(R.id.timeA)
        val timeB : EditText = findViewById(R.id.timeB)


        buttonstart.setOnClickListener(){
                val gotoScreen2 = Intent(applicationContext, Tela2Activity::class.java)
                val nomeTimeA = timeA.text.toString()
                val nomeTimeB = timeB.text.toString()
                val params1 = Bundle()
                val params2 = Bundle()
                params1.putString("nA", nomeTimeA)
                params2.putString("nB", nomeTimeB)
                gotoScreen2.putExtras(params1)
                gotoScreen2.putExtras(params2)
                startActivity(gotoScreen2)
        }
    }

}