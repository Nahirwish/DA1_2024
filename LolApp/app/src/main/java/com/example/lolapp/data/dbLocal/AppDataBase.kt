package com.example.lolapp.data.dbLocal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities =[ChampionLocal::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase: RoomDatabase() {
    abstract fun championsDao(): ChampionDAO


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

        suspend fun clean(context: Context){
            CoroutineScope(Dispatchers.IO).launch {
                getInstance(context).clearAllTables()
            }

        }
}
