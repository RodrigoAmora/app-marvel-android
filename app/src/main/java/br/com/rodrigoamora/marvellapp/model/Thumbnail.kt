package br.com.rodrigoamora.marvellapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    foreignKeys = [ForeignKey(
        entity = Character::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("thumbnailId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Thumbnail(
    @PrimaryKey(autoGenerate = true)
    var thumbnailId: Long = 0,

    @SerializedName("extension")
    var extension: String,

    @SerializedName("path")
    var path: String
)
