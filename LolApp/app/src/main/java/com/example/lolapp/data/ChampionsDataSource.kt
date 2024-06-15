package com.example.lolapp.data

import android.content.Context
import android.util.Log
import com.example.lolapp.data.dbLocal.AppDataBase
import com.example.lolapp.data.dbLocal.ChampionLocal
import com.example.lolapp.data.dbLocal.toChampionList
import com.example.lolapp.data.dbLocal.toChampionLocalList
import com.example.lolapp.model.Champion
import com.example.lolapp.model.ChampionDetail
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import retrofit2.create
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class ChampionsDataSource {
    companion object{
        private val API_BASE_URL_LISTA = "https://run.mocky.io/v3/3bb6960c-155f-4b59-810c-b718f7378b96" //es-mx?page=0&size=10"
        private val API_BASE_URL_DETAIL = "https://run.mocky.io/v3/6b10bbf9-c36a-4d29-bcd7-cd53efa5352f"
        //private var page = 0
        //private val lang = "es-mx"
        //private var size = 10
        private val api_lista : ChampionAPI
        private val api_detail: ChampionAPI
        private val db = FirebaseFirestore.getInstance()

        init {
            api_lista = Retrofit.Builder()
                .baseUrl(API_BASE_URL_LISTA)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ChampionAPI::class.java)

            api_detail = Retrofit.Builder()
                .baseUrl(API_BASE_URL_DETAIL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ChampionAPI::class.java)
        }


        suspend fun getChampions(context: Context): ArrayList<Champion> {
            // Recupero lista de la base local
            var db = AppDataBase.getInstance(context)
            var championLocal = db.championsDao().getAll()
            if (championLocal.size > 0){
                Log.d("Log_Main_Activity", "Devuelvo la lista local")
                return championLocal.toChampionList() as ArrayList<Champion>
            }


            delay(5000)
            var result = api_lista.getChampions().execute()
            // Recupero de la API
            return if (result.isSuccessful) {
                Log.d("Log_Main_Activity", "DataSource successfull")
                var cList = result.body()?: ArrayList<Champion>()
                if (cList.size > 0){
                    db.championsDao().save(*cList.toChampionLocalList().toTypedArray())
                }
                cList
            } else {
                Log.e("Lol_Main_Activity", "Error llamando a la api" + result.message())
                ArrayList<Champion>()
            }

        }

        suspend fun getChampion(name: String): ChampionDetail?{
            var champ = suspendCoroutine<ChampionDetail?> { continuation ->
                db.collection("champions").document(name).get().addOnCompleteListener{
                    if (it.isSuccessful){
                        var c = it.result.toObject(ChampionDetail::class.java)
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
            var result = api_detail.getChampion().execute()
            if (result.isSuccessful) {
                Log.d("Log_Main_Activity", "Champion data source is successful")
                val champion = result.body()
                if( champion != null){
                    db.collection("champions").document(name).set(champion)
                }
                return champion
            }
            else{
                Log.e("Log_Main_Activity", "Champ data source, Error llamando la api" + result.message())
                return null
            }
        }


    }
}