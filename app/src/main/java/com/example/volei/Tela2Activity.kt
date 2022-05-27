package com.example.volei

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text

class Tela2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela2)
        val t2timeA : TextView = findViewById(R.id.t2TimeA)
        val t2timeB: TextView = findViewById(R.id.t2timeB)
        val vPassadoTimA = intent.getStringArrayExtra("nA")
        val vPassadoTimB = intent.getStringArrayExtra("nB")
        t2timeA.text= vPassadoTimA.toString()
        t2timeB.text=vPassadoTimB.toString()
    }
}