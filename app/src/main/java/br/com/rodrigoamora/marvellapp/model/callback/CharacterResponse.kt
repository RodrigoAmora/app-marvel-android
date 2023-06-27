package br.com.rodrigoamora.marvellapp.model.callback

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("code")
    var code: Int,

    @SerializedName("data")
    var data: CharacterData
)