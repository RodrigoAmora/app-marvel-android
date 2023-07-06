package br.com.rodrigoamora.marvellapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.rodrigoamora.marvellapp.model.Character

@Dao
interface CharacterDao {
    @Query("SELECT * FROM Character order by Character.name asc")
    fun findAll(): LiveData<List<Character>>

    @Insert(onConflict = REPLACE)
    fun save(character: Character)

    @Insert(onConflict = REPLACE)
    fun save(characters: List<Character>)

    @Delete
    fun remove(character: Character)

    @Query("DELETE FROM Character")
    fun removeAll()
}