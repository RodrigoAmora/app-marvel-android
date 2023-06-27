package br.com.rodrigoamora.marvellapp.network.retrofit.service

import br.com.rodrigoamora.marvellapp.network.response.CharacterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CharacterService {

    @GET("public/characters")
    fun getCharacters(@Query("ts") ts: Int, @Query("apikey") apikey: String, @Query("hash") hash: String): Call<CharacterResponse>

    @GET("public/characters")
    fun getCharacterByName(@Query("ts") ts: Int,
                            @Query("apikey") apikey: String,
                            @Query("hash") hash: String,
                            @Query("name") name: String): Call<CharacterResponse>

}