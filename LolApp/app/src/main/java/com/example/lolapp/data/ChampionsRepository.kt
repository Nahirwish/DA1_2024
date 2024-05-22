package com.example.lolapp.data

import com.example.lolapp.model.Champion

class ChampionsRepository {

    suspend fun getChampions() : ArrayList<Champion>{
        return ChampionsDataSource.Companion.getChampions()
    }

}