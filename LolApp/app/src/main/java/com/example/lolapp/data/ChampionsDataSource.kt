package com.example.lolapp.data

import android.content.Context
import android.util.JsonReader
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
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import retrofit2.create
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import java.io.InputStreamReader
import java.io.InputStream
import kotlin.coroutines.coroutineContext



class ChampionsDataSource {
    companion object {
        private val API_BASE_URL_LISTA = "https://run.mocky.io/v3/103df2f4-3e8b-4d3c-b86d-278229121af4/"
        private val API_BASE_URL_DETAIL = "https://run.mocky.io/v3/cdf3bbe8-1d88-4660-be0c-35c68dcb6f85/"
        //private var page = 0
        //private val lang = "es-mx"
        //private var size = 10
        private var api_lista: ChampionAPI
        private var api_detail: ChampionAPI
        private val db = FirebaseFirestore.getInstance()

        init {
            try {
                val gson = GsonBuilder()
                    .serializeNulls()
                    .serializeSpecialFloatingPointValues()
                    .setLenient()
                    .create()
                api_lista = Retrofit.Builder()
                    .baseUrl(API_BASE_URL_LISTA)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build().create(ChampionAPI::class.java)

                api_detail = Retrofit.Builder()
                    .baseUrl(API_BASE_URL_DETAIL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build().create(ChampionAPI::class.java)
            }
            catch (e :Exception){
                Log.e("Log_Main_Activity", e.message!!)
            }
            finally {
                val gson = GsonBuilder()
                    .serializeNulls()
                    .serializeSpecialFloatingPointValues()
                    .setLenient()
                    .create()
                api_lista = Retrofit.Builder()
                    .baseUrl(API_BASE_URL_LISTA)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build().create(ChampionAPI::class.java)

                api_detail = Retrofit.Builder()
                    .baseUrl(API_BASE_URL_DETAIL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build().create(ChampionAPI::class.java)
            }
            }



        suspend fun getChampions(context: Context): ArrayList<Champion> {
            Log.d("Log_Main_Activity", "se ejecuto getChamps en DataSource")
            // Recupero lista de la base local
            var db = AppDataBase.getInstance(context)
            var championLocal = db.championsDao().getAll()
            if (championLocal.size > 0){
                Log.d("Log_Main_Activity", "Devuelvo la lista local")
                return championLocal.toChampionList() as ArrayList<Champion>
            }

            delay(5000)
            val inputStream = context.assets.open("champions_list.json")
            val reader = InputStreamReader(inputStream)
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Champion>>() {}.type
            val champions: ArrayList<Champion> = gson.fromJson(reader, type)
            reader.close()

            Log.d("Log_Main_Activity", "Ds Response body: ${champions.size}")
            if (champions.isNotEmpty()) {
                db.championsDao().save(*champions.toChampionLocalList().toTypedArray())
            }
            return champions
        }

            /*var result = api_lista.getChampions().execute()
            Log.d("Log_Main_Activity", "Raw response: ${result.raw()}")
            // Recupero de la API
            return if (result.isSuccessful) {
                val jsonResponse = result.body()
                Log.d("Log_Main_Activity", "Ds Response body: ${result.body()?.toString()}")
                var cList = jsonResponse ?: ArrayList<Champion>()
                Log.d("Log_Main_Activity", "Ds Response body: ${cList.size}")
                if (cList.size > 0){
                    db.championsDao().save(*cList.toChampionLocalList().toTypedArray())
                }
                cList
            } else {
                Log.e("Lol_Main_Activity", "Error llamando a la api" + result.message())
                ArrayList<Champion>()
            }

        }*/

        suspend fun getChampion(name: String, context: Context): ChampionDetail?{

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

            val inputStream = context.assets.open("champion_detail.json")
            val reader = InputStreamReader(inputStream)
            val gson = Gson()
            val championDetail: ChampionDetail = gson.fromJson(reader, ChampionDetail::class.java)
            reader.close()

            Log.d("Log_Main_Activity", "Champion data source is successful")
            db.collection("champions").document(name).set(championDetail)

            return championDetail
        }

        /*var result = api_detail.getChampion().execute()
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
    }*/

        suspend fun addFavorite(id: String){
            db.collection("favoritos").document("favoritos").set(id)
                .addOnSuccessListener {
                    Log.d("Log_Main_Activity", "Se agrego a favoritos")
                }
                .addOnFailureListener {e ->
                    Log.e("Log_Main_Activity", "Error guardando favorito" + e.message)
                }
        }



    }
}