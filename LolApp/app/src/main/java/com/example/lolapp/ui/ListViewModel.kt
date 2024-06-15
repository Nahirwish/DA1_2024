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

class ListViewModel: ViewModel() {
    val champRepo : ChampionsRepository = ChampionsRepository()
    var champions : MutableLiveData<ArrayList<Champion>> = MutableLiveData<ArrayList<Champion>>()



    private val coroutineContext: CoroutineContext = newSingleThreadContext("champs")
    private val scope = CoroutineScope(coroutineContext)

    fun init(context: Context){
        scope.launch {
            kotlin.runCatching {
                champRepo.getChampions(context)
            }.onSuccess {
                Log.d("Log_Main_Activity", "Champions onSucces")
                champions.postValue(it)
            }.onFailure {
                Log.e("Log_Main_Activity", "Champions error"+ it)
                champions.postValue(ArrayList<Champion>())
            }

        }

    }





}