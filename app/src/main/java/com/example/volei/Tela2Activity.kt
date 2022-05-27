package com.example.volei

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson




class Tela2Activity : AppCompatActivity() {

    data class Student (
        var name: String? = null,
        var address: String? = null) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela2)
        val backbutton : Button = findViewById(R.id.backtostart)
        val t2timeA : TextView = findViewById(R.id.t2TimeA)
        val t2timeB: TextView = findViewById(R.id.t2timeB)
        val vPassadoTimA = intent.getStringExtra("nA")
        val vPassadoTimB = intent.getStringExtra("nB")
        t2timeA.text= vPassadoTimA.toString()
        t2timeB.text=vPassadoTimB.toString()
        backbutton.setOnClickListener(){
            startActivity(intent)
        }
        
        val student = Student("Alex", "Rome")
    }
}