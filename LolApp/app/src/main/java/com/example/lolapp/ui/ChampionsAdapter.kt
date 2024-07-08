package com.example.lolapp.ui

import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lolapp.R
import com.example.lolapp.model.Champion

class ChampionsAdapter : RecyclerView.Adapter<ChampionsViewHolder>(){
    private val items : MutableList<Champion> = ArrayList<Champion>()


    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): ChampionsViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.champion_item, parent, false)
        return ChampionsViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ChampionsViewHolder, position: Int){
        Log.d("Log_Main_Activity", "position: " + position)
        holder.champion_name.text = items[position].champion_name
        holder.recomended_roles.text = items[position].recomended_roles
        Glide.with(holder.itemView.context).load(items[position].profile_img).into(holder.champion_icon)

        holder.itemView.setOnClickListener(){
            val id = items[0].champion_name
            Log.d("Log_Main_Activity", "click item, id: {$id}")
            val intent = Intent(holder.itemView.context, ChampionDetailActivity::class.java)
            intent.putExtra("id", id)
            holder.itemView.context.startActivity(intent)
            Log.d("Log_Main_Activity", "context: ${it.context}")
        }

    }


    override fun getItemCount(): Int {
        Log.d("Log_Main_Activity", "Adapter getItemCount: " + items.size)
        return items.size
    }

    fun update(lista: MutableList<Champion>) {
        Log.d("Log_Main_Activity", "Adapter update, new list: ${lista.map { it.champion_name }}")
        items.clear()
        items.addAll(lista)
        Log.d("Log_Main_Activity", "Adapter update, new size: ${items.size}")
        notifyDataSetChanged()
    }



}