package br.com.rodrigoamora.marvellapp.network.response

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("code")
    var code: Int,

    @SerializedName("data")
    var data: CharacterData,

    @SerializedName("status")
    var status: String,
)