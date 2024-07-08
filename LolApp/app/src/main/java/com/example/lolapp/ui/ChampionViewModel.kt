package com.example.lolapp.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lolapp.data.ChampionsRepository
import com.example.lolapp.model.Champion
import com.example.lolapp.model.ChampionDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class ChampionViewModel : ViewModel(){
    val champRepo: ChampionsRepository = ChampionsRepository()
    var champion: MutableLiveData<ChampionDetail> = MutableLiveData<ChampionDetail>()

    private val coroutineContext: CoroutineContext = newSingleThreadContext("champs")
    private val scope = CoroutineScope(coroutineContext)

    fun init(name: String, context: Context){
        scope.launch {
            kotlin.runCatching {
                champRepo.getChampion(name, context)
            }.onSuccess {
                champion.postValue(it ?: ChampionDetail())
            }.onFailure {
                val champ = ChampionDetail()
                champ.champion_name = "Error"
                champ.recomended_roles = ""
                champion.postValue(champ)
            }
        }
    }

    fun addFavorite(id: String){
        scope.launch {
            kotlin.runCatching {
                champRepo.addFavorite(id)
            }.onSuccess {
                Log.d("Log_Main_Activity", "addFavorite en ChampionViewModel Success")
            }.onFailure {e->
                Log.d("Log_Main_activity", "addFavorite en ChampionViewModel Failure ${e.message}")
            }
        }
    }

    fun removeFavorite(id: String){
        scope.launch {
            kotlin.runCatching {
                champRepo.removeFavorite(id)
            }.onSuccess {
                Log.d("Log_Main_Activity", "removeFavorite en ChampionViewModel Success")
            }.onFailure { e ->
                Log.d("Log_Main_Activity", "removeFavorite en ChampionViewModel Failure ${e.message}" )
            }
        }
    }
}