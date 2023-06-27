package br.com.rodrigoamora.marvellapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.rodrigoamora.marvellapp.database.dao.CharacterDao
import br.com.rodrigoamora.marvellapp.model.Character

@Database(entities = [Character::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

}