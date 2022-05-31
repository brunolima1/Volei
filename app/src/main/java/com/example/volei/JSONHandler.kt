package com.example.volei

import com.example.volei.model.Partidas
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File

class JSONHandler {

    constructor(){

    }

    public fun createJSONFile(s: String){
        var gson = Gson()
        var jsonString: String = gson.toJson(Partidas(mutableListOf()))
        val file = File(s)
        file.writeText(jsonString)
    }

    public fun writeJSONtoFile(s:String, matchs : Partidas) {
        var gson = Gson()
        var jsonString: String = gson.toJson(matchs)
        val file = File(s)
        file.writeText(jsonString)
    }

    public fun readJSONfromFile(f:String): Partidas {
        var gson = Gson()

        val bufferedReader: BufferedReader = File(f).bufferedReader()

        val inputString = bufferedReader.use { it.readText() }

        //Convert the Json File to Gson Object
        var partidas: Partidas = gson.fromJson(inputString, Partidas::class.java)


        //stringBuilder = StringBuilder("Detalhes da Partida\n---------------------")
        //+Log.d("Kotlin",partidas.partidas.toString())
        //stringBuilder?.append("\nTime 1: " + partida.time1)
        //stringBuilder?.append("\nTime 1: " + partida.time1)
        //stringBuilder?.append("\nTime 2: " + partida.time2)
        //stringBuilder?.append("\nScore 1: " + partida.score1)
        //stringBuilder?.append("\nScore 2: " + partida.score2)


        return partidas
    }

    public fun fileExists(filePath: String): Boolean {
        val file = File(filePath)
        return file.exists() && !file.isDirectory
    }
}