package br.com.rodrigoamora.marvellapp.network.retrofit.webclient

import br.com.rodrigoamora.marvellapp.network.apikey.MarvelApiKey
import br.com.rodrigoamora.marvellapp.network.response.ComicResponse
import br.com.rodrigoamora.marvellapp.network.retrofit.service.CharacterService
import br.com.rodrigoamora.marvellapp.network.retrofit.service.ComicService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicWebClient(
    private val characterService: CharacterService,
    private val comicService: ComicService
) {
    private fun<T> executeRequest(
        call: Call<T>,
        completion: (result: T?) -> Unit,
        failure: (errorCode: Int) -> Unit
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                when (val responseCode = response.code()) {
                    in 200..299 -> {
                        completion(response.body())
                    }
                    else -> {
                        failure(responseCode)
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                failure(call.hashCode())
            }
        })
    }

    fun getComicsByCharacterId(characterId: Int,
                             completion: (comicResponse: ComicResponse?) -> Unit,
                             failure: (errorCode: Int) -> Unit) {
        executeRequest(
            characterService.getComicsOfCharacterId(characterId,1, MarvelApiKey.API_KEY, MarvelApiKey.MD5_HASH),
                completion = { comicsResponse ->
                    comicsResponse?.let {
                        completion(it)
                    }
                },
                failure = { errorCode ->  failure(errorCode) }
        )
    }

}