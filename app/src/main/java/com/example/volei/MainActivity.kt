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


class MainActivity : AppCompatActivity() {

    private var stringBuilder:StringBuilder?=null
    var setsA: Int = 0
    var setsB: Int = 0

    var placarA : Int = 0
    var placarB : Int = 0

    private var placarTextA: TextView? = null
    private var placarTextB: TextView? = null
    private var setsTextA: TextView? = null
    private var setsTextB: TextView? = null
    private var setsGeral: TextView? = null

    private var json: JSONHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placarTextA = findViewById(com.example.volei.R.id.teamApoints)
        placarTextB = findViewById(com.example.volei.R.id.teamBpoints)
        setsTextA = findViewById(com.example.volei.R.id.setTeamA)
        setsTextB = findViewById(com.example.volei.R.id.setTeamB)
        setsGeral = findViewById(com.example.volei.R.id.setCounter)
        val t2timeA : TextView = findViewById(R.id.teamAname)
        val t2timeB: TextView = findViewById(R.id.teamBname)

        val historyButton: Button = findViewById(R.id.history)

        json = JSONHandler()

        setsGeral!!.text = " ${0}";

        historyButton.setOnClickListener(){
            startActivity(Intent(applicationContext, MatchesActivity::class.java))
        }
        placarTextA!!.setOnClickListener(){
            placarA +=1
            if(placarA < 10){
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
            setsTextA!!.text = setsA.toString()
        }
        else if(result == 2){
            setsB += 1;
            setsTextB!!.text = setsB.toString()
        }

        if(setsA == 3 || setsB == 3){
            endMatch(timeA, timeB, scoreA, scoreB)
        }
        placarA = 0
        placarTextA!!.text = "0$placarA"

        placarB = 0
        placarTextB!!.text = "0$placarB"

        setsGeral!!.text = " " + (setsA + setsB + 1).toString();
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