package br.com.rodrigoamora.marvellapp.network.retrofit.service

import br.com.rodrigoamora.marvellapp.network.response.ComicResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicService {

    @GET("public/comics")
    fun getComics(@Query("ts") ts: Int,
                  @Query("apikey") apikey: String,
                  @Query("hash") hash: String): Call<ComicResponse>

}