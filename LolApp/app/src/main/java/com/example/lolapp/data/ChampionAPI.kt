package com.example.lolapp.data

import com.example.lolapp.model.Champion
import com.example.lolapp.model.ChampionDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface ChampionAPI {
    //@GET("/champions")
    @GET("/")
    fun getChampions(
        //@Query("page") page: Int,
        //@Query("lang") lang: String,
        //@Query("size") size: Int
    ): Call<ArrayList<Champion>>

    //@GET("/champions/{champion_name}")
    @GET("/")
    fun getChampion(
        //@Path("champion_name") name: String
    ): Call<ChampionDetail>
}
