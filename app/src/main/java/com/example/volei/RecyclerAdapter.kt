package com.example.volei

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.volei.model.Partida

class RecyclerAdapter(_list: MutableList<Partida>) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {
    private var list : MutableList<Partida>? = _list

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name1 : TextView? = null
        var name2 : TextView? = null
        var set1 : TextView? = null
        var set2 : TextView? = null

        init {
            name1 = view.findViewById(R.id.time1)
            name2 = view.findViewById(R.id.time2)
            set1 = view.findViewById(R.id.set1)
            set2 = view.findViewById(R.id.set2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_historico, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var stringBuilder = StringBuilder()

        for (item: Partida in list!!) {
            stringBuilder.append(item.time1 + "\n\n")
        }
        holder.name1!!.text = stringBuilder.toString()
        stringBuilder.clear()

        for (item: Partida in list!!) {
            stringBuilder.append(item.time2 + "\n\n")
        }
        holder.name2!!.text = stringBuilder.toString()
        stringBuilder.clear()

        for (item: Partida in list!!) {
            stringBuilder.append(item.score1.toString() + "\n\n")
        }
        holder.set1!!.text = stringBuilder.toString()
        stringBuilder.clear()

        for (item: Partida in list!!) {
            stringBuilder.append(item.score2.toString() + "\n\n")
        }
        holder.set2!!.text = stringBuilder.toString()
        Log.d("Debug", """${list!![position].id} - ${list!![position].time1} - ${list!![position].score1} - ${list!![position].time2} - ${list!![position].score2}""")
    }

    override fun getItemCount(): Int {
        return list!!.size
    }
}