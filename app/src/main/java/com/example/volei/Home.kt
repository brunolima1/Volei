package com.example.volei

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
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

class Home : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    private var play: Button? = null
    private var pause: Button? = null
    private var undo: Button? = null


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
        placarTextA = findViewById(R.id.teamApoints)
        placarTextB = findViewById(R.id.teamBpoints)
        setsTextA = findViewById(R.id.setTeamA)
        setsTextB = findViewById(R.id.setTeamB)
        setsGeral = findViewById(R.id.setCounter)
        val t2timeA : TextView = findViewById(R.id.teamAname)
        val t2timeB: TextView = findViewById(R.id.teamBname)

        val meter = findViewById<Chronometer>(R.id.textView2)

        json = JSONHandler()

        setsGeral!!.text = " 1"

//        historyButton.setOnClickListener {
//            startActivity(Intent(applicationContext, MatchesActivity::class.java))
//        }

        play!!.isEnabled = true;
        pause!!.isEnabled = false;


        play!!.setOnClickListener{
            meter.start()
            play!!.isEnabled = false;
            pause!!.isEnabled = true;
//            startActivity(Intent(applicationContext, Home::class.java))
        }

        pause!!.setOnClickListener{
            meter.stop()
            pause!!.isEnabled = false;
            play!!.isEnabled = true;
//            startActivity(Intent(applicationContext, Home::class.java))
        }

        undo!!.setOnClickListener{
            meter.stop();
            this.finish()
            startActivity(Intent(applicationContext, Home::class.java))
        }

        placarTextA!!.setOnClickListener {
            placarA +=1
            if(placarA < 10){
                placarTextA!!.text = "0$placarA"
            }
            else{
                placarTextA!!.text = placarA.toString()
            }

            val result: Int = checkPlacar()
            if(result != 0){
                endSet(result, t2timeA.text.toString(), t2timeB.text.toString(), placarA, placarB)
            }
        }
        placarTextB!!.setOnClickListener {
            placarB +=1
            if(placarB < 10){
                placarTextB!!.text = "0$placarB"
            }
            else{
                placarTextB!!.text = placarB.toString()
            }

            val result: Int = checkPlacar()
            if(result != 0){
                endSet(result, t2timeA.text.toString(), t2timeB.text.toString(), placarA, placarB)
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

    private fun endSet(result: Int, timeA: String, timeB: String, scoreA: Int, scoreB: Int){
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

    private fun endMatch(timeA: String, timeB: String, scoreA: Int, scoreB: Int){
        val partidas: Partidas = json!!.readJSONfromFile(cacheDir.absolutePath+"/PostJson.json")
        val partida = Partida(partidas.partidas.size, timeA, timeB, scoreA, scoreB)
        partidas.partidas.add(partida)
        json!!.writeJSONtoFile(cacheDir.absolutePath+"/PostJson.json", partidas)
        startActivity(Intent(applicationContext, Home::class.java))
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

}