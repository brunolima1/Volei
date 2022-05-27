package com.example.volei

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class Tela2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela2)
        val backToprevious = Intent(applicationContext,MainActivity::class.java)
        var placarA : Int = 0;
        var placarB : Int = 0;
        var placarTextA: TextView = findViewById(R.id.placarA)
        var placarTextB: TextView =findViewById(R.id.placarB)
        var marcapontoA: Button = findViewById(R.id.pontoA)
        var marcapontoB: Button = findViewById(R.id.pontoB)
        var backbutton : Button = findViewById(R.id.backtostart)
        var t2timeA : TextView = findViewById(R.id.t2TimeA)
        var t2timeB: TextView = findViewById(R.id.t2timeB)
        var vPassadoTimA = intent.getStringExtra("nA")
        var vPassadoTimB = intent.getStringExtra("nB")
        t2timeA.text=vPassadoTimA.toString()
        t2timeB.text=vPassadoTimB.toString()
        backbutton.setOnClickListener(){
            startActivity(backToprevious)
        }
        marcapontoA.setOnClickListener(){
            placarA +=1
            val placarAStr: String = placarA.toString()
            placarTextA.text = placarAStr
        }
        marcapontoB.setOnClickListener(){
            placarB +=1
            val placarBStr: String = placarB.toString()
            placarTextB.text =placarBStr
        }
    }
}