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

    private var json: JSONHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela2)
        val backToprevious = Intent(applicationContext, MainActivity::class.java)

        placarTextA = findViewById(com.example.volei.R.id.teamApoints)
        placarTextB = findViewById(com.example.volei.R.id.teamBpoints)
        //val marcapontoA: Button = findViewById(R.id.teamApoints)
        //val marcapontoB: Button = findViewById(R.id.teamBpoints)
        //val backbutton : Button = findViewById(R.id.backtostart)
        val t2timeA : TextView = findViewById(R.id.NomeTimeA)
        val t2timeB: TextView = findViewById(R.id.NomeTimeB)
        val vPassadoTimA = intent.getStringExtra("nA")
        val vPassadoTimB = intent.getStringExtra("nB")
        t2timeA.text=vPassadoTimA.toString()
        t2timeB.text=vPassadoTimB.toString()

        json = JSONHandler()

        //backbutton.setOnClickListener(){
        //    startActivity(backToprevious)
        //}
        placarTextA!!.setOnClickListener(){
            placarA +=1
            if(placarB < 10){
                placarTextA!!.text = "0$placarA"
            }
            else{
                placarTextA!!.text = placarA.toString()
            }

            var result: Int = checkPlacar()
            if(result != 0){
                endSet(result, t2timeA.text.toString(), t2timeB.text.toString(), placarA, placarB)
            }
        }
        placarTextB!!.setOnClickListener(){
            placarB +=1
            if(placarB < 10){
                placarTextB!!.text = "0$placarB"
            }
            else{
                placarTextB!!.text = placarB.toString()
            }

            var result: Int = checkPlacar()
            if(result != 0){
                endSet(result, t2timeA.text.toString(), t2timeB.text.toString(), placarA, placarB)
            }
        }
        if(!json!!.fileExists(cacheDir.absolutePath+"/PostJson.json")){
            json!!.createJSONFile(cacheDir.absolutePath+"/PostJson.json")
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
        placarTextA!!.text = "0" + placarA.toString()

        placarB = 0
        placarTextB!!.text = "0" + placarB.toString()
    }

    private fun endMatch(timeA: String, timeB: String, scoreA: Int, scoreB: Int){
        var partidas: Partidas = json!!.readJSONfromFile(cacheDir.absolutePath+"/PostJson.json")
        var partida = Partida(partidas.partidas.size, timeA, timeB, scoreA, scoreB)
        partidas.partidas.add(partida)
        json!!.writeJSONtoFile(cacheDir.absolutePath+"/PostJson.json", partidas)
        startActivity(Intent(applicationContext, MainActivity::class.java))
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
}