package com.example.volei

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.volei.model.Partida

class recyclerAdapter : RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private var list : MutableList<Partida>? = null;

    constructor(_list : MutableList<Partida>){
        this.list = _list
    }

    class MyViewHolder : RecyclerView.ViewHolder {
        var nameTxt : TextView? = null

        constructor(view : View) : super(view) {
            nameTxt = view.findViewById(R.id.time1)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): recyclerAdapter.MyViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_historico, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: recyclerAdapter.MyViewHolder, position: Int) {
        var name: String = list!![position].time1
        holder.nameTxt!!.text = name
    }

    override fun getItemCount(): Int {
        return list!!.size
    }
}