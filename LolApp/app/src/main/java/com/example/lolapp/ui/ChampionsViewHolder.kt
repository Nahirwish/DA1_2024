package com.example.lolapp.ui

import android.widget.TextView
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.lolapp.R
class ChampionsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    var champion_name: TextView = itemView.findViewById(R.id.txtChampionName)
    var champion_icon: ImageView = itemView.findViewById(R.id.imgIcon)
    var recomended_roles: TextView = itemView.findViewById(R.id.txtRecomendedRoles)

}