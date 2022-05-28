package com.example.volei

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.volei.model.Partida
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File


class Tela2Activity : AppCompatActivity() {

    private var stringBuilder:StringBuilder?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela2)
        val backToprevious = Intent(applicationContext,MainActivity::class.java)
        var placarA : Int = 0;
        var placarB : Int = 0;
        val placarTextA: TextView = findViewById(R.id.placarA)
        val placarTextB: TextView =findViewById(R.id.placarB)
        val marcapontoA: Button = findViewById(R.id.pontoA)
        val marcapontoB: Button = findViewById(R.id.pontoB)
        val backbutton : Button = findViewById(R.id.backtostart)
        val t2timeA : TextView = findViewById(R.id.t2TimeA)
        val t2timeB: TextView = findViewById(R.id.t2timeB)
        val vPassadoTimA = intent.getStringExtra("nA")
        val vPassadoTimB = intent.getStringExtra("nB")
        t2timeA.text=vPassadoTimA.toString()
        t2timeB.text=vPassadoTimB.toString()
        backbutton.setOnClickListener(){
            startActivity(backToprevious)
        }
        marcapontoA.setOnClickListener(){
            placarA +=1
            val placarAStr: String = placarA.toString()
            placarTextA.text = placarAStr

            CheckPlacar(placarA, placarB)
        }
        marcapontoB.setOnClickListener(){
            placarB +=1
            val placarBStr: String = placarB.toString()
            placarTextB.text =placarBStr
        }

        var partida = Partida(0, "Brasil", "Japão", 3, 2)
        writeJSONtoFile(cacheDir.absolutePath+"/PostJson.json", partida)
        var details: String = readJSONfromFile(cacheDir.absolutePath+"/PostJson.json")

    }

    private fun CheckPlacar(placar1 : Int, placar2 : Int){
        if(placar1 >= 25){
            if((placar1 - placar2) >= 2){

            }
        }
    }

    private fun writeJSONtoFile(s:String, match : Partida) {
        var gson = Gson()
        var jsonString:String = gson.toJson(match)
        val file= File(s)
        file.writeText(jsonString)
    }

    private fun readJSONfromFile(f:String): String {
        var gson = Gson()

        val bufferedReader: BufferedReader = File(f).bufferedReader()

        val inputString = bufferedReader.use { it.readText() }

        //Convert the Json File to Gson Object
        var partida = gson.fromJson(inputString, Partida::class.java)
        //Initialize the String Builder
        stringBuilder = StringBuilder("Detalhes da Partida\n---------------------")
        +Log.d("Kotlin",partida.id.toString())
        stringBuilder?.append("\nTime 1: " + partida.time1)
        stringBuilder?.append("\nTime 2: " + partida.time2)
        stringBuilder?.append("\nScore 1: " + partida.score1)
        stringBuilder?.append("\nScore 2: " + partida.score2)

        return stringBuilder.toString()
    }
}