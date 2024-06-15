package com.example.lolapp.data

import android.content.Context
import com.example.lolapp.model.Champion
import com.example.lolapp.model.ChampionDetail

class ChampionsRepository {

    suspend fun getChampions(context: Context) : ArrayList<Champion>{
        return ChampionsDataSource.Companion.getChampions(context)
    }

    suspend fun getChampion(name: String) : ChampionDetail?{
        return ChampionsDataSource.Companion.getChampion(name)
    }
}