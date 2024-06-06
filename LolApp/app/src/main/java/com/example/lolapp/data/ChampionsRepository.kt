package com.example.lolapp.data

import com.example.lolapp.model.Champion

class ChampionsRepository {

    suspend fun getChampions() : ArrayList<Champion>{
        return ChampionsDataSource.Companion.getChampions()
    }

    suspend fun getChampion(name: String) : Champion{
        return ChampionsDataSource.Companion.getChampion(name)
    }
}