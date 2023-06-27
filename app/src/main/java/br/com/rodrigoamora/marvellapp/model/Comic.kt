package br.com.rodrigoamora.marvellapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Comic(
    @PrimaryKey
    @SerializedName("id")
    var id: Int,

    @SerializedName("title")
    var title: String
): Serializable