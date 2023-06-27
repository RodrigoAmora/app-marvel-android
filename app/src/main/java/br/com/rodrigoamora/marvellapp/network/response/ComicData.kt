package br.com.rodrigoamora.marvellapp.network.response

import br.com.rodrigoamora.marvellapp.model.Comic
import com.google.gson.annotations.SerializedName

data class ComicData(
    @SerializedName("total")
    var total: Int,

    @SerializedName("results")
    var result: List<Comic>
)