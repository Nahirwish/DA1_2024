package com.example.lolapp.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lolapp.data.ChampionsRepository
import com.example.lolapp.model.Champion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class ChampionViewModel : ViewModel(){
    val champRepo: ChampionsRepository = ChampionsRepository()
    var champion: MutableLiveData<Champion> = MutableLiveData<Champion>()

    private val coroutineContext: CoroutineContext = newSingleThreadContext("champs")
    private val scope = CoroutineScope(coroutineContext)

    fun init(name: String, context: Context){
        scope.launch {
            kotlin.runCatching {
                champRepo.getChampion(name) // ????????
            }.onSuccess {
                champion.postValue(it ?: Champion(it.champion_name, it.recomended_roles, it.profile_img))
            }.onFailure {
                val champ = Champion()
                champ.champion_name = "Error"
                champ.recomended_roles = ArrayList()
                champion.postValue(Champion())
            }
        }
    }
}