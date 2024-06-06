package com.example.lolapp.data

import android.content.Context
import android.util.Log
import com.example.lolapp.data.dbLocal.AppDataBase
import com.example.lolapp.data.dbLocal.ChampionLocal
import com.example.lolapp.data.dbLocal.toChampionList
import com.example.lolapp.model.Champion
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import retrofit2.create
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class ChampionsDataSource {
    companion object{
        private val API_BASE_URL = "https://league-of-legends-champions.p.rapidapi.com" //es-mx?page=0&size=10"
        private var page = 0
        private val lang = "es-mx"
        private var size = 10
        private val api : ChampionAPI
        private val db = FirebaseFirestore.getInstance()

        init {
            api = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ChampionAPI::class.java)
        }

        suspend fun getChampions(context: Context): ArrayList<Champion> {

            var db = AppDataBase.getInstance(context)
            var championLocal = db.championsDao().getAll()
            if (championLocal.size > 0){
                Log.d("Log_Main_Activity", "Devuelvo la lista local")
                return championLocal.toChampionList()
            }


            delay(5000)
            var result = api.getChampions(page, lang, size).execute()

            return if (result.isSuccessfull) {
                Log.d("Log_Main_Activity", "DataSource successfull")
                var cList = result.body()?: ArrayList<Champion>()
                if (cList.size > 0){
                    db.championsDao().save(*cList.toChampionLocalList().toTypeArray)
                }
            } else {
                Log.e("Lol_Main_Activity", "Error llamando a la api" + result.message())
                ArrayList<Champion>()
            }

        }

        suspend fun getChampion(name: String): Champion{
            var champ = suspendCoroutine<Champion?> {continuation ->
                db.collection("champions").document(name).get().addOnCompleteListener{
                    if (it.isSuccessful){
                        var c = it.result.toObject(Champion::class.java)
                        continuation.resume(c)
                    }
                    else{
                        continuation.resume(null)
                    }
                }

            }
            if (champ != null){
                return champ
            }

            delay(5000)
            var result = api.getChampions(name).execute()
            if (result.isSuccessfull) {
                Log.d("Log_Main_Activity", "Champion data source is successful")
                val champions = result.body() ?: return null
                val champion = champions.singleOrNull()
                champ.champion_splash
                db.collection("universities").document(name).set(champ)
                return champ
            }
            else{
                Log.e("Log_Main_Activity", "Champ data source, Error llamando la api" + result.message())
                return null
            }
        }


    }
}