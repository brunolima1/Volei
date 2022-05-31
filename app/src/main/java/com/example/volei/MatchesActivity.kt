package com.example.volei

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.volei.model.Partida

class MatchesActivity : AppCompatActivity() {

    private var matches: MutableList<Partida>? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches)

        val buttonBack: Button = findViewById(R.id.backToMain)
        recyclerView = findViewById(R.id.List)
        val json = JSONHandler()
        matches = json.readJSONfromFile(cacheDir.absolutePath+"/PostJson.json").partidas



        buttonBack.setOnClickListener {
            val goToMain = Intent(applicationContext, MainActivity::class.java)
            startActivity(goToMain)
        }
        setAdapter()
    }

    private fun setAdapter(){
        val adapter = recyclerAdapter(matches!!)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = adapter
    }
}