package com.example.volei

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.volei.model.Partida
import com.example.volei.model.Partidas
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File


class Tela2Activity : AppCompatActivity() {

    private var stringBuilder:StringBuilder?=null
    var setsA: Int = 0
    var setsB: Int = 0

    var placarA : Int = 0
    var placarB : Int = 0

    private var placarTextA: TextView? = null
    private var placarTextB: TextView? = null

    private val backToprevious = Intent(applicationContext, MainActivity::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela2)

        placarTextA = findViewById(com.example.volei.R.id.placarA)
        placarTextB = findViewById(com.example.volei.R.id.placarB)
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
            placarTextA!!.text = placarA.toString()

            var result: Int = checkPlacar()
            if(result != 0){
                endSet(result, t2timeA.text.toString(), t2timeB.text.toString(), placarA, placarB)
            }
        }
        marcapontoB.setOnClickListener(){
            placarB +=1
            placarTextB!!.text =placarB.toString()

            var result: Int = checkPlacar()
            if(result != 0){
                endSet(result, t2timeA.text.toString(), t2timeB.text.toString(), placarA, placarB)
            }
        }
        if(!fileExists(cacheDir.absolutePath+"/PostJson.json")){
            createJSONFile(cacheDir.absolutePath+"/PostJson.json")
        }
    }

    private fun endSet(result: Int, timeA: String, timeB: String, scoreA: Int, scoreB: Int){
        if(result == 1){
            setsA += 1
        }
        else if(result == 2){
            setsB += 1;
        }

        if(setsA == 3 || setsB == 3){
            endMatch(timeA, timeB, scoreA, scoreB)
        }
        placarA = 0
        placarTextA!!.text = placarA.toString()

        placarB = 0
        placarTextB!!.text = placarB.toString()
    }

    private fun endMatch(timeA: String, timeB: String, scoreA: Int, scoreB: Int){
        var partidas: Partidas = readJSONfromFile(cacheDir.absolutePath+"/PostJson.json")
        var partida = Partida(partidas.partidas.size, timeA, timeB, scoreA, scoreB)
        partidas.partidas.add(partida)
        writeJSONtoFile(cacheDir.absolutePath+"/PostJson.json", partidas)
        startActivity(backToprevious)
    }

    private fun checkPlacar(): Int {
        if(placarA >= 25 && (placarA - placarB) >= 2){
            return 1
        }
        else if(placarB >= 25 && (placarB - placarA) >= 2){
            return 2
        }
        return 0;
    }

    private fun createJSONFile(s: String){
        var gson = Gson()
        var jsonString: String = gson.toJson(Partidas(mutableListOf()))
        val file = File(s)
        file.writeText(jsonString)
    }

    private fun writeJSONtoFile(s:String, matchs : Partidas) {
        var gson = Gson()
        var jsonString: String = gson.toJson(matchs)
        val file = File(s)
        file.writeText(jsonString)
    }

    private fun readJSONfromFile(f:String): Partidas {
        var gson = Gson()

        val bufferedReader: BufferedReader = File(f).bufferedReader()

        val inputString = bufferedReader.use { it.readText() }

        //Convert the Json File to Gson Object
        var partidas: Partidas = gson.fromJson(inputString, Partidas::class.java)

        /*
        stringBuilder = StringBuilder("Detalhes da Partida\n---------------------")
        +Log.d("Kotlin",partida.id.toString())
        stringBuilder?.append("\nTime 1: " + partida.time1)
        stringBuilder?.append("\nTime 2: " + partida.time2)
        stringBuilder?.append("\nScore 1: " + partida.score1)
        stringBuilder?.append("\nScore 2: " + partida.score2)
         */

        return partidas
    }

    private fun fileExists(filePath: String): Boolean {
        val file = File(filePath)
        return file.exists() && !file.isDirectory
    }
}