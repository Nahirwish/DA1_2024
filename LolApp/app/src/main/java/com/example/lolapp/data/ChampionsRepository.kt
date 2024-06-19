package com.example.lolapp.data

import android.content.Context
import com.example.lolapp.model.Champion
import com.example.lolapp.model.ChampionDetail
import android.util.Log

class ChampionsRepository {

    suspend fun getChampions(context: Context) : ArrayList<Champion>{
        Log.d("Log_Main_Activity", "Repository llama al dataSource")
        return ChampionsDataSource.getChampions(context)
    }

    suspend fun getChampion(name: String) : ChampionDetail?{
        return ChampionsDataSource.getChampion(name)
    }
}