package com.example.volei

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.volei.databinding.ActivityHomeBinding
import com.example.volei.model.Partida
import com.example.volei.model.Partidas
import com.example.volei.model.Pontuacao
import org.w3c.dom.Text

class Home : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    private var running: Boolean? = false
    private var paused: Boolean? = true

    private var play: Button? = null
    private var pause: Button? = null
    private var undo: Button? = null
    private var restart: MenuItem? = null

    private var setsA: Int = 0
    private var setsB: Int = 0

    private var placarA : Int = 0
    private var placarB : Int = 0

    private var placarTextA: TextView? = null
    private var placarTextB: TextView? = null
    private var setsTextA: TextView? = null
    private var setsTextB: TextView? = null
    private var setsGeral: TextView? = null

    private var json: JSONHandler? = null
    private val historicoPlacar = MutableList(1) { Pontuacao(placarA, placarB, setsA, setsB) }

    private var meter: Chronometer? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_lista_partidas
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        play = findViewById(R.id.play)
        pause = findViewById(R.id.pause)
        undo = findViewById(R.id.undo)
        restart = findViewById(R.id.action_settings)
        placarTextA = findViewById(R.id.teamApoints)
        placarTextB = findViewById(R.id.teamBpoints)
        setsTextA = findViewById(R.id.setTeamA)
        setsTextB = findViewById(R.id.setTeamB)
        setsGeral = findViewById(R.id.setCounter)

        meter = findViewById<Chronometer>(R.id.textView2)

        json = JSONHandler()

        val t2timeA : TextView = findViewById(R.id.teamAname)
        val t2timeB: TextView = findViewById(R.id.teamBname)

        setsGeral!!.text = " 1"

        play!!.isEnabled = true
        pause!!.isEnabled = false
        undo!!.isEnabled = false


        play!!.setOnClickListener{
            if(!running!! && paused!!)
                startMatch(t2timeA,t2timeB)
            else
                continueMatch()

            undo!!.isEnabled = true
            refreshScreen()
        }

        pause!!.setOnClickListener{
            stopMatch()
        }

        undo!!.setOnClickListener {
            val lastIndex: Int = historicoPlacar.lastIndex
            if(lastIndex > 0) {
                historicoPlacar.removeAt(lastIndex)
                val previousScore: Pontuacao = historicoPlacar[lastIndex - 1]
                placarA = previousScore.score1
                placarB = previousScore.score2
                setsA = previousScore.sets1
                setsB = previousScore.sets2
                updateTexts()
                if(historicoPlacar.size == 1)
                undo!!.isEnabled = false
            }
        }

        placarTextA!!.setOnClickListener {
            if(running!! && !paused!!){
                placarA +=1
                addPoint()
                refreshScreen()
                val result: Int = checkPlacar()
                if(result != 0){
                    endSet(result, t2timeA, t2timeB, placarA, placarB)
                }
            }
        }

        placarTextB!!.setOnClickListener {
            if(running!! && !paused!!){
                placarB +=1
                addPoint()
                refreshScreen()
                val result: Int = checkPlacar()
                if(result != 0){
                    endSet(result, t2timeA, t2timeB, placarA, placarB)
                }
            }
        }
        if(!json!!.fileExists(cacheDir.absolutePath+"/PostJson.json")){
            json!!.createJSONFile(cacheDir.absolutePath+"/PostJson.json")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @SuppressLint("SetTextI18n")
    private fun endSet(result: Int, timeA: TextView, timeB: TextView, scoreA: Int, scoreB: Int){
        if(result == 1){
            setsA += 1
            setsTextA!!.text = setsA.toString()
        }
        else if(result == 2){
            setsB += 1
            setsTextB!!.text = setsB.toString()
        }

        if(setsA == 3 || setsB == 3){
            endMatch(timeA, timeB, scoreA, scoreB)
        }
        placarA = 0
        placarTextA!!.text = "0$placarA"

        placarB = 0
        placarTextB!!.text = "0$placarB"

        setsGeral!!.text = " " + (setsA + setsB + 1).toString()
    }
    private fun startMatch(timeA: TextView, timeB: TextView){
        meter!!.start()
        play!!.isEnabled = false
        pause!!.isEnabled = true
        placarA = 0;
        placarB = 0;
        setsA = 0;
        setsB = 0;
        timeA.text = "TIME A"
        timeB.text = "TIME B"
        running = true
        paused = false
    }

    private fun continueMatch(){
        play!!.isEnabled = false
        pause!!.isEnabled = true
        running = true
        paused = false
        meter!!.start()
    }

    private fun stopMatch(){
        meter!!.stop()
        play!!.isEnabled = true
        pause!!.isEnabled = false
        running = true
        paused = true
    }

    private fun refreshScreen(){
        if(placarA < 10){
            placarTextA!!.text = "0$placarA"
        }
        else{
            placarTextA!!.text = placarA.toString()
        }

        if(placarB < 10){
            placarTextB!!.text = "0$placarB"
        }
        else{
            placarTextB!!.text = placarB.toString()
        }

        setsGeral!!.text = " " + (setsA + setsB + 1).toString()

    }

    private fun endMatch(timeA: TextView, timeB: TextView, scoreA: Int, scoreB: Int){
        val partidas: Partidas = json!!.readJSONfromFile(cacheDir.absolutePath+"/PostJson.json")
        val partida = Partida(partidas.partidas.size, timeA.text.toString(), timeB.text.toString(), scoreA, scoreB)
        partidas.partidas.add(partida)
        json!!.writeJSONtoFile(cacheDir.absolutePath+"/PostJson.json", partidas)
        meter!!.setBase(SystemClock.elapsedRealtime())

        startMatch(timeA,timeB)
        refreshScreen()
//        startActivity(Intent(applicationContext, Home::class.java))
    }

    private fun checkPlacar(): Int {
        if(placarA >= 25 && (placarA - placarB) >= 2){
            return 1
        }
        else if(placarB >= 25 && (placarB - placarA) >= 2){
            return 2
        }
        return 0
    }

    private fun addPoint() {
        if(historicoPlacar.size >= 10) {
            historicoPlacar.removeAt(0)
        }
        historicoPlacar.add(Pontuacao(placarA, placarB, setsA, setsB))
        undo!!.isEnabled = true
    }

    @SuppressLint("SetTextI18n")
    private fun updateTexts() {
        if(placarA < 10){
            placarTextA!!.text = "0$placarA"
        }
        else{
            placarTextA!!.text = placarA.toString()
        }
        if(placarB < 10){
            placarTextB!!.text = "0$placarB"
        }
        else{
            placarTextB!!.text = placarB.toString()
        }
        setsTextA!!.text = setsA.toString()
        setsTextB!!.text = setsB.toString()
        setsGeral!!.text = " " + (setsA + setsB + 1).toString()
    }
}