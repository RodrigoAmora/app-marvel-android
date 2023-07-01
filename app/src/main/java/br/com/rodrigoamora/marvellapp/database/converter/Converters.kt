package br.com.rodrigoamora.marvellapp.database.converter

import androidx.room.TypeConverter
import br.com.rodrigoamora.marvellapp.model.Thumbnail
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun thumbnailToJson(value: Thumbnail): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToThumbnail(value: String): Thumbnail = Gson().fromJson(value, Thumbnail::class.java)
}