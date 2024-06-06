package com.example.lolapp.data.dbLocal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Champions")
data class ChampionLocal (
    @PrimaryKey var champion_name : String,
    var recomended_roles : ArrayList<String>,
    var profile_img : String
)
