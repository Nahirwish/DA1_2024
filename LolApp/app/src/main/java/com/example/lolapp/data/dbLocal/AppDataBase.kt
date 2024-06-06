package com.example.lolapp.data.dbLocal

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

abstract class AppDataBase: RoomDatabase() {
    abstract fun championsDao: ChampionDAO


    companion object{
        @Volatile // Se puede acceder desde varios hilos de ejecucion
        private var _instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase = _instance?: synchronized(this){
            _instance?: buildDataBase(context)
        }

        private fun buildDataBase(context: Context): AppDataBase =Room.databaseBuilder(context, AppDataBase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
        }

        private fun clean(context: Context): CoroutineScope{
            launch.
        }
}
