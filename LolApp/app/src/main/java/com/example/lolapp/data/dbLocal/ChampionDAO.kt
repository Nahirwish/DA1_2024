package com.example.lolapp.data.dbLocal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChampionDAO {
    @Query("SELECT * FROM Champions")
    fun getAll(): List<ChampionLocal>
    @Query("SELECT * FROM Champions WHERE champion_name = :champion_name LIMIT 1")
    fun getByPK(champion_name: String): ChampionLocal
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg champion: ChampionLocal)
    @Delete
    fun delete(champion: ChampionLocal)
}