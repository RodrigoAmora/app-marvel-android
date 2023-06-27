package br.com.rodrigoamora.marvellapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Character(
    @PrimaryKey
    var id: Int,
    var name: String,
    var description: String
    //var thumbnail: Thumbnail
): Serializable
