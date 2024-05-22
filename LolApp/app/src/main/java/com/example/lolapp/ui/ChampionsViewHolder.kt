package com.example.lolapp.ui

import android.widget.TextView
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.lolapp.R
class ChampionsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    val champion_name: TextView = itemView.findViewById(R.id.txtChampionName)
    //val champion_icon: ImageView = itemView.findViewById(R.id.imgIcon)
    val recomended_roles: TextView = itemView.findViewById(R.id.txtRecomendedRoles) // textView?

}