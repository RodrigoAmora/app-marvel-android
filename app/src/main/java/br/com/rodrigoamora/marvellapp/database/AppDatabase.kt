package br.com.rodrigoamora.marvellapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.rodrigoamora.marvellapp.database.dao.CharacterDao
import br.com.rodrigoamora.marvellapp.database.dao.ComicDao
import br.com.rodrigoamora.marvellapp.model.Character
import br.com.rodrigoamora.marvellapp.model.Comic
import br.com.rodrigoamora.marvellapp.model.Thumbnail

@Database(entities = [Character::class, Comic::class, Thumbnail::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun comicDao(): ComicDao

}