package com.example.lolapp.data

import com.example.lolapp.model.Champion
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ChampionAPI {
    @GET("/champions/lang/size") // Endpoint
    fun getChampions(
        @Query("page") page: Int,
        @Query("lang") lang: String,
        @Query("size") size: Int
    ): Call<ArrayList<Champion>>
}
