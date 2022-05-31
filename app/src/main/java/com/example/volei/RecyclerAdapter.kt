package com.example.volei

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_historico, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name1!!.text = list!![position].time1
        holder.name2!!.text = list!![position].time2
        holder.set1!!.text = list!![position].score1.toString()
        holder.set2!!.text = list!![position].score2.toString()
    }

    override fun getItemCount(): Int {
        return list!!.size
    }
}