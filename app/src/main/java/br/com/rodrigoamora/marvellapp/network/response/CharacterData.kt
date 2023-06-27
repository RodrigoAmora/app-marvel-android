package br.com.rodrigoamora.marvellapp.network.response

import br.com.rodrigoamora.marvellapp.model.Character
import com.google.gson.annotations.SerializedName

data class CharacterData (
    @SerializedName("results")
    var result: List<Character>
)