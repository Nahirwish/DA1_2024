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
    var items : MutableList<Champion> = ArrayList<Champion>()

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
            var id = items[position].champion_name
            val intent = Intent(holder.itemView.context, ChampionDetailActivity::class.java)
            intent.putExtra("id", id)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        Log.d("Log_Main_Activity", "Size: " + items.size)
        return items.size
    }

    fun update(lista: MutableList<Champion>) {
        items = lista
        this.notifyDataSetChanged()
    }



}