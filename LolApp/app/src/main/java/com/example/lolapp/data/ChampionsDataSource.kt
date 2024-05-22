package com.example.lolapp.data

import android.util.Log
import com.example.lolapp.model.Champion
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ChampionsDataSource {
    companion object{
        private val API_BASE_URL = "https://league-of-legends-champions.p.rapidapi.com" //es-mx?page=0&size=10"
        private val api : ChampionAPI

        init {
            api = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ChampionAPI :: classs.java)
        }

        suspend fun getChampions(): ArrayList<Champion> {
            var result = api.getChampions().execute()

            return if (result.isSuccessfull) {
                Log.d("Log_Main_Activity", "DataSource successfull")
                result.body() ?: ArrayList<Champion>()
            } else {
                Log.e("Lol_Main_Activity", "Error llamando a la api" + result.message())
                ArrayList<Champion>()
            }

        }
    }
}