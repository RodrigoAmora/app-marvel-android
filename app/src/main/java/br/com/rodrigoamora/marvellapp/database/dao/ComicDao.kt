package br.com.rodrigoamora.marvellapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.model.Comic

@Dao
interface ComicDao {
    @Query("SELECT * FROM Comic")
    fun findAll(): LiveData<List<Comic>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(character: Comic)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(characters: List<Comic>)

    @Delete
    fun remove(character: Comic)

    @Query("DELETE FROM Comic")
    fun removeAll()
}