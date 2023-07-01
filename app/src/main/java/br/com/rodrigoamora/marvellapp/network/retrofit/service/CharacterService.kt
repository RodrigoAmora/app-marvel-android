package br.com.rodrigoamora.marvellapp.network.retrofit.service

import br.com.rodrigoamora.marvellapp.network.response.CharacterResponse
import br.com.rodrigoamora.marvellapp.network.response.ComicResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CharacterService {

    @GET("characters")
    fun getCharacters(@Query("ts") ts: Int,
                      @Query("apikey") apikey: String,
                      @Query("hash") hash: String): Call<CharacterResponse>

    @GET("characters")
    fun getCharacterByName(@Query("ts") ts: Int,
                            @Query("apikey") apikey: String,
                            @Query("hash") hash: String,
                            @Query("name") name: String): Call<CharacterResponse>

    @GET("characters/{characterID}/comics")
    fun getComicsOfCharacters(@Path("characterID") characterID: Int,
                              @Query("ts") ts: Int,
                              @Query("apikey") apikey: String,
                              @Query("hash") hash: String): Call<ComicResponse>

}