package br.com.rodrigoamora.marvellapp.network.response

import com.google.gson.annotations.SerializedName

data class ComicResponse(
    @SerializedName("code")
    var code: Int,

    @SerializedName("data")
    var data: ComicData,

    @SerializedName("status")
    var status: String,
)